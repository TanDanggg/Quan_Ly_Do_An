package gui;

import javax.swing.*;

import javax.swing.table.DefaultTableModel;

import dao.DoAnDAO;
import dao.GiangVienDAO;
import dto.DoAn;
import dto.GiangVien;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import java.util.Vector;

public class GiangVienPanel extends JPanel {
    private JTable giangVienTable;
    private DefaultTableModel tableModel;
    private JTextField nameField, levelField, positionField, sdtField, searchField;
    private JButton viewButton, addButton, editButton, deleteButton, searchButton;
    
    private JComboBox<DoAn> projectComboBox;
    
    private GiangVienDAO giangVienDAO;
    
    // Các biến thêm vào để sử dụng DAO và lưu danh sách đồ án
    private DoAnDAO doAnDAO;
    private List<DoAn> doAnList;

    public GiangVienPanel() {
    	giangVienDAO = new GiangVienDAO(); // Khởi tạo DAO
    	
        setLayout(new BorderLayout());

        // Panel phía Bắc (North)
        JPanel pnNorth = new JPanel(new FlowLayout());
        JLabel titleLabel = new JLabel("Quản lý Giảng viên");
        pnNorth.add(titleLabel);
        add(pnNorth, BorderLayout.NORTH);

        // Panel ở giữa (Center)
        JPanel pnCenter = new JPanel(new BorderLayout());

        // Panel phía Bắc của pnCenter chứa ô nhập liệu và nút tìm kiếm
        JPanel searchPanel = new JPanel(new FlowLayout());
        searchField = new JTextField(15);
        searchButton = new JButton("Search");
        searchPanel.add(createInputPanel("Search:", searchField));
        searchPanel.add(searchButton);
        pnCenter.add(searchPanel, BorderLayout.NORTH);

        // Bảng JTable ở giữa của pnCenter
        tableModel = new DefaultTableModel();
        tableModel.addColumn("ID");
        tableModel.addColumn("Name");
        tableModel.addColumn("Level");
        tableModel.addColumn("Position");
        tableModel.addColumn("SDT");
        tableModel.addColumn("Project");
        // Thêm các dòng dữ liệu từ CSDL vào bảng (sử dụng SQL query để lấy dữ liệu từ CSDL)

        giangVienTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(giangVienTable);
        pnCenter.add(scrollPane, BorderLayout.CENTER);
        add(pnCenter, BorderLayout.CENTER);

        // Panel phía Nam (South)
        JPanel pnSouth = new JPanel(new BorderLayout());
        add(pnSouth, BorderLayout.SOUTH);

        // Panel trung tâm của pnSouth
        JPanel inputPanel = new JPanel(new FlowLayout());
        nameField = new JTextField(15);
        levelField = new JTextField(15);
        positionField = new JTextField(15);
        sdtField = new JTextField(10);

        inputPanel.add(createInputPanel("Name:", nameField));
        inputPanel.add(createInputPanel("Level:", levelField));
        inputPanel.add(createInputPanel("Position:", positionField));
        inputPanel.add(createInputPanel("SDT:", sdtField));
        
     // Thêm JComboBox cho việc chọn đồ án
        projectComboBox = new JComboBox<>();
        inputPanel.add(createInputPanel("Đồ án:", projectComboBox));

        pnSouth.add(inputPanel, BorderLayout.CENTER);

        // Panel phía Nam của pnSouth
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        viewButton = new JButton("View");
        addButton = new JButton("Add");
        editButton = new JButton("Edit");
        deleteButton = new JButton("Delete");

        buttonPanel.add(viewButton);
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);

        // Xử lý sự kiện nút
        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Xử lý khi nhấn nút "View"
            	updateTableData();
            }
        });

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Xử lý khi nhấn nút "Add"
            	addGiangVien();
            }
        });

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Xử lý khi nhấn nút "Edit"
            	editGiangVien();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Xử lý khi nhấn nút "Delete"
            	deleteGiangVien();
            }
        });
        
        giangVienTable.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Lấy dòng được chọn
                int selectedRow = giangVienTable.getSelectedRow();

                // Kiểm tra xem dòng được chọn có hợp lệ không
                if (selectedRow != -1) {
                    // Lấy thông tin của giảng viên từ dòng được chọn
                    int id = (int) giangVienTable.getValueAt(selectedRow, 0);
                    String name = giangVienTable.getValueAt(selectedRow, 1).toString();
                    String level = giangVienTable.getValueAt(selectedRow, 2).toString();
                    String position = giangVienTable.getValueAt(selectedRow, 3).toString();
                    int sdt = Integer.parseInt(giangVienTable.getValueAt(selectedRow, 4).toString());

                    // Lấy thông tin của dự án từ cột mới thêm vào
                    int selectedProjectID = Integer.parseInt(giangVienTable.getValueAt(selectedRow, 5).toString());
                    DoAn selectedProject = findDoAnByID(selectedProjectID);

                    // Hiển thị thông tin lên các ô nhập liệu
                    nameField.setText(name);
                    levelField.setText(level);
                    positionField.setText(position);
                    sdtField.setText(String.valueOf(sdt));
                    projectComboBox.setSelectedItem(selectedProject);
                }
            }

            // Các phương thức khác không cần thay đổi
            @Override
            public void mousePressed(MouseEvent e) {}

            @Override
            public void mouseReleased(MouseEvent e) {}

            @Override
            public void mouseEntered(MouseEvent e) {}

            @Override
            public void mouseExited(MouseEvent e) {}
        });

        pnSouth.add(buttonPanel, BorderLayout.SOUTH);
        buttonPanel.setPreferredSize(new Dimension(0, 100));
        
        // Khởi tạo DoAnDAO và lấy danh sách đồ án
        doAnDAO = new DoAnDAO();
        doAnList = doAnDAO.getAllDoAn();

        // Set dữ liệu cho ComboBox đồ án
        setProjectComboBox(convertDoAnListToVector(doAnList));
    }
    
    // Hàm này thêm dữ liệu vào ComboBox để chọn đồ án
    public void setProjectComboBox(Vector<DoAn> projects) {
        DefaultComboBoxModel<DoAn> model = new DefaultComboBoxModel<>(projects);
        projectComboBox.setModel(model);
    }
    
    // Hàm chuyển danh sách đồ án sang Vector<String> để sử dụng cho ComboBox
    private Vector<DoAn> convertDoAnListToVector(List<DoAn> doAnList) {
        Vector<DoAn> doAnVector = new Vector<>();
        for (DoAn doAn : doAnList) {
            doAnVector.add(doAn);
        }
        return doAnVector;
    }

    private JPanel createInputPanel(String labelText, JComponent inputComponent) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel lblTitle = new JLabel(labelText);
        panel.add(lblTitle);
        panel.add(inputComponent);
        return panel;
    }
    
    
    // Helper function to find DoAn object by ID in the doAnList
    private DoAn findDoAnByID(int projectID) {
        for (DoAn doAn : doAnList) {
            if (doAn.getId() == projectID) {
                return doAn;
            }
        }
        return null; // Return null if the project is not found
    }
    
 // Hàm này để thêm giảng viên
    private void addGiangVien() {
        String name = nameField.getText();
        String level = levelField.getText();
        String position = positionField.getText();
        int sdt = Integer.parseInt(sdtField.getText());
        DoAn selectedProject = (DoAn) projectComboBox.getSelectedItem();

        GiangVien giangVien = new GiangVien(0, name, level, position, sdt, selectedProject.getId()); // ID_Project để 0 vì chưa có thông tin

        boolean success = giangVienDAO.addGiangVien(giangVien);
        if (success) {
            updateTableData();
            JOptionPane.showMessageDialog(null, "Đã thêm giảng viên thành công.");
            clearInputFields();
        } else {
            JOptionPane.showMessageDialog(null, "Thêm giảng viên không thành công. Vui lòng thử lại.");
        }
    }

    // Hàm này để sửa thông tin giảng viên
    private void editGiangVien() {
        int selectedRow = giangVienTable.getSelectedRow();
        if (selectedRow != -1) {
            int id = (int) giangVienTable.getValueAt(selectedRow, 0);
            String name = nameField.getText();
            String level = levelField.getText();
            String position = positionField.getText();
            int sdt = Integer.parseInt(sdtField.getText());
            DoAn selectedProject = (DoAn) projectComboBox.getSelectedItem();

            GiangVien giangVien = new GiangVien(id, name, level, position, sdt, selectedProject.getId()); // ID_Project để 0 vì chưa có thông tin

            boolean success = giangVienDAO.updateGiangVien(giangVien);
            if (success) {
                updateTableData();
                JOptionPane.showMessageDialog(null, "Đã cập nhật giảng viên thành công.");
                clearInputFields();
            } else {
                JOptionPane.showMessageDialog(null, "Cập nhật giảng viên không thành công. Vui lòng thử lại.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn một giảng viên để sửa.");
        }
    }

    // Hàm này để xóa giảng viên
    private void deleteGiangVien() {
        int selectedRow = giangVienTable.getSelectedRow();
        if (selectedRow != -1) {
            int id = (int) giangVienTable.getValueAt(selectedRow, 0);

            int confirm = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn xóa giảng viên này?",
                    "Xác nhận Xóa", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                boolean success = giangVienDAO.deleteGiangVien(id);
                if (success) {
                    updateTableData();
                    JOptionPane.showMessageDialog(null, "Đã xóa giảng viên thành công.");
                    clearInputFields();
                } else {
                    JOptionPane.showMessageDialog(null, "Xóa giảng viên không thành công. Vui lòng thử lại.");
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn một giảng viên để xóa.");
        }
    }

    // Hàm này để hiển thị danh sách giảng viên lên bảng
    private void displayGiangVienList(List<GiangVien> giangVienList) {
        tableModel.setRowCount(0);

        for (GiangVien giangVien : giangVienList) {
            Object[] row = {giangVien.getId(), giangVien.getNameGV(), giangVien.getLevel(),
                             giangVien.getPosition(), giangVien.getSdt(), giangVien.getIdProject()};
            tableModel.addRow(row);
        }
    }
    
 // Hàm này để cập nhật dữ liệu trên bảng
    private void updateTableData() {
        List<GiangVien> giangVienList = giangVienDAO.getAllGiangVien();
        displayGiangVienList(giangVienList);
    }
    
 // Hàm cập nhật bảng dữ liệu từ danh sách giảng viên
    private void updateTable(List<GiangVien> giangVienList) {
        tableModel.setRowCount(0);
        for (GiangVien giangVien : giangVienList) {
            Vector<Object> rowData = new Vector<>();
            rowData.add(giangVien.getId());
            rowData.add(giangVien.getNameGV());
            rowData.add(giangVien.getLevel());
            rowData.add(giangVien.getPosition());
            rowData.add(giangVien.getSdt());

            // Thêm ID của dự án vào cột mới
            rowData.add(giangVien.getIdProject());

            tableModel.addRow(rowData);
        }
    }

    // Hàm này để xóa nội dung trên các trường nhập liệu
    private void clearInputFields() {
        nameField.setText("");
        levelField.setText("");
        positionField.setText("");
        sdtField.setText("");
        projectComboBox.setSelectedIndex(0);
    }
}
