package main.Main;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Calculator {

    private static String currentInput = "";
    // TODO: make a list with all previous operations like this:

    private static Operation currentOperation = new Operation();

    public static void main(String[] args) {
    	
        JFrame frame = new JFrame();
        frame.setTitle("Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setSize(400, 600);
        
        ImageIcon icon = new ImageIcon(Calculator.class.getResource("/Calculator.png"));
    	frame.setIconImage(icon.getImage());

        JTextField screen = new JTextField();
        screen.setEditable(false);
        screen.setHorizontalAlignment(JTextField.RIGHT);
        screen.setFont(new Font("Arial", Font.BOLD, 24));
        screen.setPreferredSize(new Dimension(400, 80));
        screen.setFocusable(true);
        
        screen.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                char keyChar = e.getKeyChar();

                if (Character.isDigit(keyChar)) {
                    // números
                    currentInput += keyChar;
                    screen.setText(screen.getText() + keyChar);
                } else if ("+-*/".indexOf(keyChar) >= 0) {
                    // operadores
                    currentOperation.addToken(currentInput);
                    currentOperation.addToken(String.valueOf(keyChar));
                    currentInput = "";
                    screen.setText(screen.getText() + keyChar);
                } else if (keyChar == '\n') { // Enter = "="
                    // evaluar
                    if (!currentInput.isEmpty()) {
                        currentOperation.addToken(currentInput);
                    }
                    double result = Evaluate.evaluateTokens(currentOperation.getTokens());
                    screen.setText(String.valueOf(result));
                    currentOperation = new Operation();
                    currentInput = "";
                } else if (keyChar == 'c' || keyChar == 'C') {
                    // clear
                    screen.setText("");
                    currentInput = "";
                    currentOperation = new Operation();
                }
            }
        });


        JTextArea historyArea = new JTextArea();
        historyArea.setEditable(false);
        historyArea.setFont(new Font("Arial", Font.PLAIN, 16));
        
        JScrollPane scrollHistory = new JScrollPane(historyArea);
        scrollHistory.setPreferredSize(new Dimension(400, 150));

        JPanel buttonPanels = new JPanel();
        buttonPanels.setLayout(new GridLayout(4, 4, 5, 5));

        String[] buttons = {
            "7", "8", "9", "/",
            "4", "5", "6", "*",
            "1", "2", "3", "-",
            "0", "C", "=", "+"
        };

        for (String text : buttons) {
            JButton button = new JButton(text);
            button.setFont(new Font("Arial", Font.BOLD, 20));
            buttonPanels.add(button);

            button.addActionListener(e -> {
                // We use a REGEX to check if it's a number, the rest is basically the case handler
                if (text.matches("\\d|\\.")) {
                    currentInput += text;
                    screen.setText(screen.getText() + text);
                }
                // Operators
                else if (text.matches("[+\\-/*]")) {
                    currentOperation.addToken(currentInput);
                    currentOperation.addToken(text);
                    currentInput = "";
                    screen.setText(screen.getText() + text);
                }
                // Equals
                else if (text.equals("=")) {
                    if (!currentInput.isEmpty()) {
                        currentOperation.addToken(currentInput);
                    }

                    double result = Evaluate.evaluateTokens(currentOperation.getTokens());

                    if (Double.isNaN(result)) {
                        screen.setText("Error: undefined operation");
                    } else if (Double.isInfinite(result)) {
                        screen.setText("Error: cannot divide by zero");
                    } else {
                        screen.setText(String.valueOf(result));
                    }

                    Operation finalOp = currentOperation;
                    finalOp.addToken("=");
                    finalOp.addToken(String.valueOf(result));
                    

                    // Show on screen and history
                    screen.setText(String.valueOf(result));
                    historyArea.append(finalOp.toString() + "\n");

                    // Restart
                    currentOperation = new Operation();
                    currentInput = "";
                }
                // Clear
                else if (text.equals("C")) {
                    screen.setText("");
                }
            });
            
        }

        frame.setLayout(new BorderLayout(5, 5));
        frame.add(screen, BorderLayout.NORTH);
        frame.add(buttonPanels, BorderLayout.CENTER);
        frame.add(scrollHistory, BorderLayout.SOUTH);

        frame.setVisible(true);
    }
}
