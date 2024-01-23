package gui;

import dao.TaiKhoanDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DangNhapGUI extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;

    private TaiKhoanDAO taiKhoanDAO;

    public DangNhapGUI() {
        taiKhoanDAO = new TaiKhoanDAO();

        // Thiết lập giao diện
        setTitle("Đăng Nhập");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        
        JPanel pnTitle = new JPanel(new FlowLayout());
        pnTitle.setPreferredSize(new Dimension(0, 50));
        JLabel lblTitle = new JLabel("Đăng nhập");
        pnTitle.add(lblTitle);
        
        JPanel pnInput = new JPanel(new GridLayout(2, 1));
        
        JPanel pnUsername = new JPanel(new FlowLayout());
        JPanel pnPassword = new JPanel(new FlowLayout());
        JPanel pnButton = new JPanel(new FlowLayout());
        pnButton.setPreferredSize(new Dimension(0, 50));
        pnInput.add(pnUsername);
        pnInput.add(pnPassword);
        pnInput.add(pnButton);

        JLabel usernameLabel = new JLabel("Tên đăng nhập:");
        JLabel passwordLabel = new JLabel("Mật khẩu:");
        passwordLabel.setPreferredSize(usernameLabel.getPreferredSize());

        usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);
        
        pnUsername.add(usernameLabel);
        pnUsername.add(usernameField);
        pnPassword.add(passwordLabel);
        pnPassword.add(passwordField);

        loginButton = new JButton("Đăng Nhập");
        
        pnButton.add(loginButton);

        panel.add(pnTitle, BorderLayout.NORTH);
        panel.add(pnInput, BorderLayout.CENTER);
        panel.add(pnButton, BorderLayout.SOUTH);

        add(panel);

        // Xử lý sự kiện nút đăng nhập
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                // Kiểm tra đăng nhập
                if (taiKhoanDAO.checkLogin(username, password)) {
                    // Điều hướng tới giao diện chính hoặc thực hiện các tác vụ sau khi đăng nhập thành công
                    new MainGUI();
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Sai tên đăng nhập hoặc mật khẩu!");
                }
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new DangNhapGUI().setVisible(true);
            }
        });
    }
}
