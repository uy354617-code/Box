import javax.swing.*;
import java.awt.*;

public class Calculator extends JFrame {

    private JPanel numberPanel;
    private JTextField display;

    public Calculator() {

        // Window settings
        setTitle("Osman Calculator");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Set layout
        setLayout(new BorderLayout());

        // Set background
        getContentPane().setBackground(Color.DARK_GRAY);

        // Create display
        display = new JTextField("0");

        // Display font
        display.setFont(new Font("Arial", Font.BOLD, 35));

        // Align text to right
        display.setHorizontalAlignment(JTextField.RIGHT);

        // Make display non-editable
        display.setEditable(false);

        // Display colors
        display.setBackground(Color.DARK_GRAY);
        display.setForeground(Color.WHITE);

        // Add display
        add(display, BorderLayout.NORTH);

        // Create number panel
        numberPanel = new JPanel(new GridLayout(4, 3, 5, 5));

        // Number buttons
        String[] numbers = {
                "7", "8", "9",
                "4", "5", "6",
                "1", "2", "3",
                "0"
        };

        // Create buttons
        for (String number : numbers) {

            JButton button = new JButton(number);

            button.setFont(new Font("Arial", Font.BOLD, 22));

            numberPanel.add(button);

            button.addActionListener(e -> {

                if (display.getText().equals("0")) {

                    display.setText(number);

                } else {

                    display.setText(display.getText() + number);

                }
            });
        }

        // Add number panel
        add(numberPanel, BorderLayout.CENTER);

        // Show window
        setVisible(true);
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            Calculator calculator = new Calculator();
        });
    }
}