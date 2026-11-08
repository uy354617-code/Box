import javax.swing.*;
import java.awt.*;

public class Calculator extends JFrame {

    // Declare display
    private JTextField display;

    public Calculator() {

        // Window title
        setTitle("Osman Calculator");

        // Window size
        setSize(400, 500);

        // Close operation
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Center window
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

        // Add display to window
        add(display, BorderLayout.NORTH);

        // Show window
        setVisible(true);
    }

    public static void main(String[] args) {

        Calculator calculator = new Calculator();

    }
}