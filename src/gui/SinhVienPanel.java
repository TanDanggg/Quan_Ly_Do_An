package gui;

import com.toedter.calendar.JDateChooser;

import dao.DoAnDAO;
import dao.SinhVienDAO;
import dto.DoAn;
import dto.SinhVien;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;
import java.util.List;

public class SinhVienPanel extends JPanel {
    private JTable sinhVienTable;
    private DefaultTableModel tableModel;
    private JTextField nameField, sexField, addressField, emailField, classField, searchField;
    private JDateChooser dobChooser;
    private JComboBox<DoAn> projectComboBox;
    private JButton viewButton, addButton, editButton, deleteButton, searchButton;
    
 // Các biến thêm vào để sử dụng DAO và lưu danh sách đồ án
    private DoAnDAO doAnDAO;
    private List<DoAn> doAnList;
    
 // Các biến thêm vào để lưu thông tin đang được chọn
    private int selectedRow = -1;

    public SinhVienPanel() {
        setLayout(new BorderLayout());

        // Panel phía Bắc (North)
        JPanel pnNorth = new JPanel(new FlowLayout());
        JLabel titleLabel = new JLabel("Quản lý Sinh viên");
        pnNorth.add(titleLabel);
        add(pnNorth, BorderLayout.NORTH);

        // Panel ở giữa (Center)
        JPanel pnCenter = new JPanel(new BorderLayout());

        // Panel phía Bắc của pnCenter chứa ô nhập liệu và nút tìm kiếm
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchField = new JTextField(15);
        searchButton = new JButton("Search");
        searchPanel.add(createInputPanel("Search:", searchField));
        searchPanel.add(searchButton);
        pnCenter.add(searchPanel, BorderLayout.NORTH);

        // Bảng JTable ở giữa của pnCenter
        tableModel = new DefaultTableModel();
        tableModel.addColumn("ID");
        tableModel.addColumn("Name");
        tableModel.addColumn("Date of Birth");
        tableModel.addColumn("Sex");
        tableModel.addColumn("Address");
        tableModel.addColumn("Email");
        tableModel.addColumn("Class");
        tableModel.addColumn("Project"); // Thêm cột đồ án
        // Thêm các dòng dữ liệu từ CSDL vào bảng (sử dụng SQL query để lấy dữ liệu từ CSDL)

        sinhVienTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(sinhVienTable);
        pnCenter.add(scrollPane, BorderLayout.CENTER);
        add(pnCenter, BorderLayout.CENTER);

        // Panel phía Nam (South)
        JPanel pnSouth = new JPanel(new BorderLayout());
        add(pnSouth, BorderLayout.SOUTH);

        // Panel trung tâm của pnSouth
        JPanel inputPanel = new JPanel(new GridLayout(0, 2));
        
        JPanel pnName = new JPanel(new FlowLayout());
        JPanel pnDob = new JPanel(new FlowLayout());
        JPanel pnSex = new JPanel(new FlowLayout());
        JPanel pnAddress = new JPanel(new FlowLayout());
        JPanel pnEmail = new JPanel(new FlowLayout());
        JPanel pnClass = new JPanel(new FlowLayout());
        JPanel pnProject = new JPanel(new FlowLayout());
        
        nameField = new JTextField(15);
        dobChooser = new JDateChooser();
        sexField = new JTextField(15);
        addressField = new JTextField(15);
        emailField = new JTextField(15);
        classField = new JTextField(15);
        projectComboBox = new JComboBox<>();
        
        dobChooser.setPreferredSize(nameField.getPreferredSize());
        projectComboBox.setPreferredSize(nameField.getPreferredSize());

        pnName.add(createInputPanel("Name:", nameField));
        pnDob.add(createInputPanel("Date of Birth:", dobChooser));
        pnSex.add(createInputPanel("Sex:", sexField));
        pnAddress.add(createInputPanel("Address:", addressField));
        pnEmail.add(createInputPanel("Email:", emailField));
        pnClass.add(createInputPanel("Class:", classField));
        pnProject.add(createInputPanel("Project:", projectComboBox));
        
        inputPanel.add(pnName);
        inputPanel.add(pnDob);
        inputPanel.add(pnSex);
        inputPanel.add(pnAddress);
        inputPanel.add(pnEmail);
        inputPanel.add(pnClass);
        inputPanel.add(pnProject);
        

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
            	
            	handleViewButton();
            }
        });

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Xử lý khi nhấn nút "Add"
            	handleAddButton();
            }
        });

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Xử lý khi nhấn nút "Edit"
            	handleEditButton();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Xử lý khi nhấn nút "Delete"
            	handleDeleteButton();
            }
        });

        pnSouth.add(buttonPanel, BorderLayout.SOUTH);
        buttonPanel.setPreferredSize(new Dimension(0, 100));
        
        sinhVienTable.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                selectedRow = sinhVienTable.getSelectedRow();
                setFieldsFromSelectedRow();
            }

            @Override
            public void mousePressed(MouseEvent e) {}

            @Override
            public void mouseReleased(MouseEvent e) {}

            @Override
            public void mouseEntered(MouseEvent e) {}

            @Override
            public void mouseExited(MouseEvent e) {}
        });
        
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Lấy từ khóa tìm kiếm từ ô nhập liệu
                String keyword = searchField.getText();

                // Thực hiện tìm kiếm sinh viên theo từ khóa
                SinhVienDAO sinhVienDAO = new SinhVienDAO();
                List<SinhVien> searchResult = sinhVienDAO.searchSinhVienByName(keyword);

                // Cập nhật bảng dữ liệu với kết quả tìm kiếm
                updateTable(searchResult);
            }
        });

        
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

    private JPanel createInputPanel(String labelText, JComponent inputComponent) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel lblTitle = new JLabel(labelText);
        lblTitle.setPreferredSize(new Dimension(100, 20));
        panel.add(lblTitle);
        panel.add(inputComponent);
        return panel;
    }
    
 // Hàm chuyển danh sách đồ án sang Vector<String> để sử dụng cho ComboBox
    private Vector<DoAn> convertDoAnListToVector(List<DoAn> doAnList) {
        Vector<DoAn> doAnVector = new Vector<>();
        for (DoAn doAn : doAnList) {
            doAnVector.add(doAn);
        }
        return doAnVector;
    }
    
 // Cập nhật bảng dữ liệu từ danh sách sinh viên
    private void updateTable(List<SinhVien> sinhVienList) {
        tableModel.setRowCount(0);
        for (SinhVien sinhVien : sinhVienList) {
            Vector<Object> rowData = new Vector<>();
            rowData.add(sinhVien.getID());
            rowData.add(sinhVien.getName());
            rowData.add(sinhVien.getDateOfBirth());
            rowData.add(sinhVien.getSex());
            rowData.add(sinhVien.getAddress());
            rowData.add(sinhVien.getEmail());
            rowData.add(sinhVien.getClassName());
            rowData.add(sinhVien.getProjectID()); // Thêm cột đồ án
            tableModel.addRow(rowData);
        }
    }
    
 // Hàm lấy thông tin từ bảng đổ vào các ô nhập liệu
    private void setFieldsFromSelectedRow() {
        if (selectedRow != -1) {
            nameField.setText(sinhVienTable.getValueAt(selectedRow, 1).toString());
            dobChooser.setDate((java.util.Date) sinhVienTable.getValueAt(selectedRow, 2));
            sexField.setText(sinhVienTable.getValueAt(selectedRow, 3).toString());
            addressField.setText(sinhVienTable.getValueAt(selectedRow, 4).toString());
            emailField.setText(sinhVienTable.getValueAt(selectedRow, 5).toString());
            classField.setText(sinhVienTable.getValueAt(selectedRow, 6).toString());

            // Get the ID of the selected project from the table
            int selectedProjectID = Integer.parseInt(sinhVienTable.getValueAt(selectedRow, 7).toString());

            // Find the corresponding DoAn object in the doAnList
            DoAn selectedProject = findDoAnByID(selectedProjectID);

            // Set the selected item in the projectComboBox
            if (selectedProject != null) {
                projectComboBox.setSelectedItem(selectedProject);
            } else {
                // Handle the case where the project is not found
                System.out.println("Project not found with ID: " + selectedProjectID);
            }
        }
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
    
 // Hàm xử lý sự kiện khi nhấn nút "View"
    private void handleViewButton() {
        // Lấy danh sách sinh viên từ CSDL
        SinhVienDAO sinhVienDAO = new SinhVienDAO();
        List<SinhVien> sinhVienList = sinhVienDAO.getAllSinhVien();

        // Cập nhật bảng dữ liệu
        updateTable(sinhVienList);
    }
    
 // Hàm xử lý sự kiện khi nhấn nút "Add"
    private void handleAddButton() {
        int selectedRow = sinhVienTable.getSelectedRow();

        // Lấy thông tin từ các ô nhập liệu
        String name = nameField.getText();
        java.util.Date dob = dobChooser.getDate();
        String sex = sexField.getText();
        String address = addressField.getText();
        String email = emailField.getText();
        String className = classField.getText();
        DoAn selectedProject = (DoAn) projectComboBox.getSelectedItem();

        SinhVien sv = new SinhVien(name, dob, sex, address, email, className, selectedProject.getId());

        // Thực hiện thêm sinh viên vào CSDL
        SinhVienDAO sinhVienDAO = new SinhVienDAO();
        boolean success = sinhVienDAO.addSinhVien(sv);

        // Hiển thị thông báo
        if (success) {
            JOptionPane.showMessageDialog(this, "Thêm sinh viên thành công.");
            handleViewButton(); // Cập nhật lại bảng dữ liệu sau khi thêm thành công
            clearInputFields();
        } else {
            JOptionPane.showMessageDialog(this, "Thêm sinh viên thất bại.");
        }
    }

    // Hàm xử lý sự kiện khi nhấn nút "Edit"
    private void handleEditButton() {
        int selectedRow = sinhVienTable.getSelectedRow();

        if (selectedRow != -1) {
            // Lấy thông tin từ các ô nhập liệu
            int id = Integer.parseInt(sinhVienTable.getValueAt(selectedRow, 0).toString());
            String name = nameField.getText();
            java.util.Date dob = dobChooser.getDate();
            String sex = sexField.getText();
            String address = addressField.getText();
            String email = emailField.getText();
            String className = classField.getText();
            DoAn selectedProject = (DoAn) projectComboBox.getSelectedItem();

            SinhVien sv = new SinhVien(id, name, dob, sex, address, email, className, selectedProject.getId());

            // Thực hiện sửa sinh viên trong CSDL
            SinhVienDAO sinhVienDAO = new SinhVienDAO();
            boolean success = sinhVienDAO.updateSinhVien(sv);

            // Hiển thị thông báo
            if (success) {
                JOptionPane.showMessageDialog(this, "Sửa thông tin sinh viên thành công.");
                handleViewButton(); // Cập nhật lại bảng dữ liệu sau khi sửa thành công
                clearInputFields();
            } else {
                JOptionPane.showMessageDialog(this, "Sửa thông tin sinh viên thất bại.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một dòng để sửa.");
        }
    }

    // Hàm xử lý sự kiện khi nhấn nút "Delete"
    private void handleDeleteButton() {
        int selectedRow = sinhVienTable.getSelectedRow();

        if (selectedRow != -1) {
            // Lấy ID của sinh viên từ dòng được chọn
            int id = Integer.parseInt(sinhVienTable.getValueAt(selectedRow, 0).toString());

            // Thực hiện xóa sinh viên từ CSDL
            SinhVienDAO sinhVienDAO = new SinhVienDAO();
            boolean success = sinhVienDAO.deleteSinhVien(id);

            // Hiển thị thông báo
            if (success) {
                JOptionPane.showMessageDialog(this, "Xóa sinh viên thành công.");
                handleViewButton(); // Cập nhật lại bảng dữ liệu sau khi xóa thành công
                clearInputFields();
            } else {
                JOptionPane.showMessageDialog(this, "Xóa sinh viên thất bại.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một dòng để xóa.");
        }
    }
    
 // Hàm xóa trắng các ô nhập liệu
    private void clearInputFields() {
        nameField.setText("");
        dobChooser.setDate(null);
        sexField.setText("");
        addressField.setText("");
        emailField.setText("");
        classField.setText("");
        projectComboBox.setSelectedIndex(0); // Chọn lại mục đầu tiên trong combobox
    }

}
