import javax.swing.*;
import java.awt.*;
import java.util.*;

class Student {
    String name;
    int marks;

    Student(String name, int marks) {
        this.name = name;
        this.marks = marks;
    }
}

public class StudentGradeTrackerGUI {

    static ArrayList<Student> list = new ArrayList<>();

    public static void main(String[] args) {

        // Colors
        Color bgColor = new Color(245, 247, 250);
        Color blue1 = new Color(0, 102, 204);
        Color blue2 = new Color(0, 120, 215);

        JFrame frame = new JFrame("Student Grade Tracker");
        frame.setSize(500, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout(10, 10));

        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        mainPanel.setBackground(bgColor);

        // ===== HEADING =====
        JLabel heading = new JLabel("Student Grade Tracker", JLabel.CENTER);
        heading.setFont(new Font("Segoe UI", Font.BOLD, 22));
        heading.setForeground(blue1);

        // ===== FORM PANEL =====
        JPanel formPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        formPanel.setBackground(bgColor);

        JLabel nameLabel = new JLabel("Student Name:");
        JTextField nameField = new JTextField();

        JLabel marksLabel = new JLabel("Marks:");
        JTextField marksField = new JTextField();

        // Text styling
        nameLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        marksLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        formPanel.add(nameLabel);
        formPanel.add(nameField);
        formPanel.add(marksLabel);
        formPanel.add(marksField);

        // ===== BUTTON PANEL =====
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setBackground(bgColor);

        JButton addBtn = new JButton("Add Student");
        JButton reportBtn = new JButton("Show Report");

        // Blue buttons
        addBtn.setBackground(blue1);
        addBtn.setForeground(Color.WHITE);
        addBtn.setFocusPainted(false);

        reportBtn.setBackground(blue2);
        reportBtn.setForeground(Color.WHITE);
        reportBtn.setFocusPainted(false);

        buttonPanel.add(addBtn);
        buttonPanel.add(reportBtn);

        // ===== OUTPUT AREA =====
        JTextArea output = new JTextArea();
        output.setEditable(false);
        output.setFont(new Font("Monospaced", Font.PLAIN, 14));
        output.setMargin(new Insets(50, 20, 200, 20));

        JScrollPane scrollPane = new JScrollPane(output);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Report"));

        // ===== ADD FUNCTION =====
        addBtn.addActionListener(e -> {
            String name = nameField.getText().trim();
            String marksText = marksField.getText().trim();

            if (name.isEmpty() || marksText.isEmpty()) {
                output.setText("⚠ Please fill all fields!");
                return;
            }

            try {
                int marks = Integer.parseInt(marksText);

                if (marks < 0 || marks > 100) {
                    output.setText("⚠ Marks must be between 0-100");
                    return;
                }

                list.add(new Student(name, marks));

                nameField.setText("");
                marksField.setText("");

                output.setText("✅ Student added successfully!");
            } catch (Exception ex) {
                output.setText("⚠ Enter valid numeric marks!");
            }
        });

        // ===== REPORT FUNCTION =====
        reportBtn.addActionListener(e -> {
            if (list.size() == 0) {
                output.setText("No data available!");
                return;
            }

            int sum = 0;
            int max = Integer.MIN_VALUE;
            int min = Integer.MAX_VALUE;

            StringBuilder report = new StringBuilder();
            report.append("----- Student Report -----\n\n");

            for (Student s : list) {
                report.append(String.format("%-15s : %3d\n", s.name, s.marks));
                sum += s.marks;
                max = Math.max(max, s.marks);
                min = Math.min(min, s.marks);
            }

            double avg = (double) sum / list.size();

            report.append("\n----------------------------\n");
            report.append("Average : ").append(avg);
            report.append("\nHighest : ").append(max);
            report.append("\nLowest  : ").append(min);

            output.setText(report.toString());
        });

        // ===== TOP PANEL (heading + form) =====
        JPanel topPanel = new JPanel(new BorderLayout(5, 5));
        topPanel.setBackground(bgColor);

        topPanel.add(heading, BorderLayout.NORTH);
        topPanel.add(formPanel, BorderLayout.CENTER);

        // ===== ADD TO MAIN PANEL =====
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        mainPanel.add(scrollPane, BorderLayout.SOUTH);

        frame.add(mainPanel);
        frame.setVisible(true);
    }
}