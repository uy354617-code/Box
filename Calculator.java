import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;

public class Calculator extends JFrame {

    // ==========================================
    // 1. DECLARE VARIABLES
    // ==========================================

    private JTextField display;

    // Stores the first number
    private BigDecimal firstOperand;

    // Stores the selected operator
    private String operator;

    // Checks whether the next digit starts a new number
    private boolean startNewNumber = true;

    // ==========================================
    // 2. CONSTRUCTOR
    // ==========================================

    public Calculator() {

        // Configure calculator window
        setTitle(" Calculator");
        setSize(400, 520);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Main layout
        setLayout(new BorderLayout(8, 8));

        getContentPane().setBackground(Color.DARK_GRAY);

        // ==========================================
        // 3. CREATE DISPLAY
        // ==========================================

        display = new JTextField("0");

        display.setFont(new Font("Arial", Font.BOLD, 35));
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setEditable(false);

        display.setBackground(Color.DARK_GRAY);
        display.setForeground(Color.WHITE);

        display.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));

        add(display, BorderLayout.NORTH);

        // ==========================================
        // 4. CREATE BUTTON PANEL
        // ==========================================

        JPanel buttonPanel = new JPanel(new GridLayout(5, 4, 5, 5));

        buttonPanel.setBackground(Color.DARK_GRAY);
        buttonPanel.setBorder(
                BorderFactory.createEmptyBorder(5, 5, 5, 5));

        // Calculator buttons
        String[] buttons = {
                "C", "⌫", "/", "*",
                "7", "8", "9", "-",
                "4", "5", "6", "+",
                "1", "2", "3", "=",
                "0", ".", "", ""
        };

        // ==========================================
        // 5. CREATE BUTTONS USING LOOP
        // ==========================================

        for (String text : buttons) {

            // Skip empty spaces
            if (text.isEmpty()) {
                buttonPanel.add(new JLabel());
                continue;
            }

            JButton button = new JButton(text);

            button.setFont(new Font("Arial", Font.BOLD, 22));
            button.setFocusPainted(false);

            // Number button color
            if (text.matches("[0-9.]")) {

                button.setBackground(Color.GRAY);
                button.setForeground(Color.WHITE);

            }

            // Clear and delete buttons
            else if (text.equals("C") || text.equals("⌫")) {

                button.setBackground(Color.RED);
                button.setForeground(Color.WHITE);

            }

            // Operator button color
            else {

                button.setBackground(Color.ORANGE);
                button.setForeground(Color.WHITE);

            }

            // Add button to panel
            buttonPanel.add(button);

            // Handle button clicks
            button.addActionListener(e -> handleButton(text));
        }

        // Add panel to window
        add(buttonPanel, BorderLayout.CENTER);

        // Display window
        setVisible(true);
    }

    // ==========================================
    // 6. HANDLE ALL BUTTON CLICKS
    // ==========================================

    private void handleButton(String value) {

        // Clear calculator
        if (value.equals("C")) {

            clearCalculator();
            return;
        }

        // Delete last digit
        if (value.equals("⌫")) {

            deleteLastDigit();
            return;
        }

        // Calculate result
        if (value.equals("=")) {

            calculate();
            return;
        }

        // Handle mathematical operators
        if (value.equals("+") ||
                value.equals("-") ||
                value.equals("*") ||
                value.equals("/")) {

            selectOperator(value);
            return;
        }

        // Handle decimal point
        if (value.equals(".")) {

            addDecimal();
            return;
        }

        // Handle numbers
        enterNumber(value);
    }

    // ==========================================
    // 7. ENTER NUMBERS
    // ==========================================

    private void enterNumber(String number) {

        if (startNewNumber) {

            display.setText(number);
            startNewNumber = false;

        } else {

            if (display.getText().equals("0")) {

                display.setText(number);

            } else {

                display.setText(display.getText() + number);

            }
        }
    }

    // ==========================================
    // 8. ADD DECIMAL POINT
    // ==========================================

    private void addDecimal() {

        if (startNewNumber) {

            display.setText("0.");
            startNewNumber = false;

        } else if (!display.getText().contains(".")) {

            display.setText(display.getText() + ".");
        }
    }

    // ==========================================
    // 9. SELECT OPERATOR
    // ==========================================

    private void selectOperator(String selectedOperator) {

        try {

            // If an operation is already pending, calculate it first
            if (operator != null && !startNewNumber) {

                calculate();

            }

            // Save first number
            firstOperand = new BigDecimal(display.getText());

            // Save operator
            operator = selectedOperator;

            // Prepare for second number
            startNewNumber = true;

        } catch (NumberFormatException ex) {

            display.setText("Error");
            resetCalculation();

        }
    }

    // ==========================================
    // 10. PERFORM CALCULATIONS
    // ==========================================

    private void calculate() {

        // Do nothing if no operator was selected
        if (operator == null || firstOperand == null) {
            return;
        }

        try {

            // Get second number
            BigDecimal secondOperand = new BigDecimal(display.getText());

            // Store calculation result
            BigDecimal result;

            // Perform selected operation
            switch (operator) {

                case "+":
                    result = firstOperand.add(secondOperand);
                    break;

                case "-":
                    result = firstOperand.subtract(secondOperand);
                    break;

                case "*":
                    result = firstOperand.multiply(secondOperand);
                    break;

                case "/":

                    // Prevent division by zero
                    if (secondOperand.compareTo(BigDecimal.ZERO) == 0) {

                        display.setText("Cannot divide by zero");

                        resetCalculation();
                        startNewNumber = true;
                        return;
                    }

                    result = firstOperand.divide(
                            secondOperand,
                            10,
                            java.math.RoundingMode.HALF_UP);

                    break;

                default:
                    return;
            }

            // Display result without unnecessary zeros
            display.setText(result.stripTrailingZeros().toPlainString());

            // Reset operation variables
            firstOperand = null;
            operator = null;

            // Next number starts a new calculation
            startNewNumber = true;

        } catch (ArithmeticException | NumberFormatException ex) {

            display.setText("Error");
            resetCalculation();
            startNewNumber = true;

        }
    }

    // ==========================================
    // 11. CLEAR CALCULATOR
    // ==========================================

    private void clearCalculator() {

        display.setText("0");

        firstOperand = null;
        operator = null;

        startNewNumber = true;
    }

    // ==========================================
    // 12. DELETE LAST DIGIT
    // ==========================================

    private void deleteLastDigit() {

        if (startNewNumber) {
            return;
        }

        String currentText = display.getText();

        if (currentText.length() > 1) {

            display.setText(
                    currentText.substring(0, currentText.length() - 1));

        } else {

            display.setText("0");
            startNewNumber = true;

        }
    }

    // ==========================================
    // 13. RESET CALCULATION VARIABLES
    // ==========================================

    private void resetCalculation() {

        firstOperand = null;
        operator = null;
    }

    // ==========================================
    // 14. MAIN METHOD
    // ==========================================

    public static void main(String[] args) {

        // Start Swing application safely
        SwingUtilities.invokeLater(() -> {

            new Calculator();

        });
    }
}