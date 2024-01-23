package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class MainGUI extends JFrame {

    private JPanel contentPanel;
    private JPanel currentPanel;
    private JPanel menuPanel;
    
 // Thêm mảng chứa các hình ảnh tương ứng với từng menu item
    private ImageIcon[] menuIcons = {
            createScaledImageIcon("img/student.png", 35, 35),
            createScaledImageIcon("img/presentation.png", 35, 35),
            createScaledImageIcon("img/checklist.png", 35, 35),
            createScaledImageIcon("img/office-building.png", 35, 35),
//            createScaledImageIcon("img/score.png", 35, 35)
    };

    public MainGUI() {
        initializeGUI();
        addMenu();
    }

    private void initializeGUI() {
        setTitle("Quản lý ứng dụng");
        setSize(1300, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());
        add(contentPanel);

        currentPanel = new JPanel();
        currentPanel.setLayout(new BorderLayout());
        contentPanel.add(currentPanel, BorderLayout.CENTER);

        menuPanel = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        menuPanel.setPreferredSize(new Dimension(180, getHeight()));
        menuPanel.setBackground(new Color(153, 255, 255)); // Đặt màu nền cho thanh menu trái
        contentPanel.add(menuPanel, BorderLayout.WEST);

        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    private ImageIcon createScaledImageIcon(String path, int width, int height) {
        ImageIcon icon;

        // Try loading the image using the class loader
        URL resource = getClass().getResource(path);
        if (resource != null) {
            icon = new ImageIcon(resource);
        } else {
            // If loading via class loader fails, try direct path
            icon = new ImageIcon(path);
        }

        // Scale the image
        Image image = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(image);
    }

    private void addMenu() {
        String[] menuItems = {"Quản lý Sinh viên", "Quản lý Giảng viên", "Quản lý Đồ án", "Nơi Thực tập"};

        for (int i = 0; i < menuItems.length; i++) {
            JLabel menuLabel = new JLabel(menuItems[i]);
            menuLabel.setPreferredSize(new Dimension(150, 60));
            menuLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            menuLabel.addMouseListener(new MenuMouseListener(menuItems[i]));
            menuLabel.setBackground(new Color(153, 255, 255));
            menuLabel.setOpaque(true);
            menuLabel.setBorder(new LineBorder(Color.BLACK));
            menuLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

            // Thêm icon vào JLabel
            if (i < menuIcons.length) {
                menuLabel.setIcon(menuIcons[i]);
            }

            menuPanel.add(menuLabel);
        }
    }

    private class MenuMouseListener extends java.awt.event.MouseAdapter {
        private String menuName;

        public MenuMouseListener(String menuName) {
            this.menuName = menuName;
        }

        @Override
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            switchPanel(menuName);
        }
    }

    private void switchPanel(String panelName) {
        if (currentPanel != null) {
            contentPanel.remove(currentPanel);
        }

        switch (panelName) {
            case "Quản lý Sinh viên":
                currentPanel = new SinhVienPanel();
                break;
            case "Quản lý Giảng viên":
                currentPanel = new GiangVienPanel();
                break;
            case "Quản lý Đồ án":
                currentPanel = new DoAnPanel();
                break;
            case "Nơi Thực tập":
                currentPanel = new NoiThucTapPanel();
                break;
////            case "Thống kê":
////                currentPanel = new ThongKePanel();
//                break;
            default:
                currentPanel = new JPanel();
        }

        contentPanel.add(currentPanel, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainGUI());
    }
}
