package gui;

import dao.DoAnDAO;
import dto.DoAn;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.toedter.calendar.JDateChooser;

public class DoAnPanel extends JPanel {
    private JTable doAnTable;
    private DefaultTableModel tableModel;
    private JTextField nameField, instructorField, searchField;
    private JDateChooser dateChooser;
    private JButton viewButton, addButton, editButton, deleteButton, searchButton;
    private DoAnDAO doAnDAO;

    public DoAnPanel() {
        doAnDAO = new DoAnDAO(); // Khởi tạo DAO

        setLayout(new BorderLayout());

        // Panel ở trên cùng (North)
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchField = new JTextField(15);
        searchButton = new JButton("Search");
        topPanel.add(createInputPanel("Search:", searchField));
        topPanel.add(searchButton);
        add(topPanel, BorderLayout.NORTH);

        // Chia màn hình thành hai phần, bên trái và bên phải
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        add(splitPane, BorderLayout.CENTER);

        // Bảng JTable ở bên trái
        tableModel = new DefaultTableModel();
        tableModel.addColumn("ID");
        tableModel.addColumn("Name");
        tableModel.addColumn("Deadline");
        tableModel.addColumn("Instructor");
        // Thêm các dòng dữ liệu từ CSDL vào bảng (sử dụng SQL query để lấy dữ liệu từ CSDL)
        updateTableData();

        doAnTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(doAnTable);
        splitPane.setLeftComponent(scrollPane);

        // Panel ở bên phải (FlowLayout)
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BorderLayout());

        JPanel pnNortOfRightPanel = new JPanel();
        pnNortOfRightPanel.setPreferredSize(new Dimension(0, 200));
        rightPanel.add(pnNortOfRightPanel, BorderLayout.NORTH);

        JPanel pnInput = new JPanel();
        pnInput.setLayout(new GridLayout(0, 1));
        rightPanel.add(pnInput, BorderLayout.CENTER);

        nameField = new JTextField(25);
        instructorField = new JTextField(25);
        dateChooser = new JDateChooser();
        dateChooser.setPreferredSize(nameField.getPreferredSize());

        pnInput.add(createInputPanel("Name:", nameField));
        pnInput.add(createInputPanel("Deadline:", dateChooser));
        pnInput.add(createInputPanel("Instructor:", instructorField));

        // Panel chứa các nút bấm
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setPreferredSize(new Dimension(0, 300));

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
                updateTableData();
            }
        });

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String deadline = convertDateToString(dateChooser.getDate());
                String instructor = instructorField.getText();

                if (name.isEmpty() || deadline.isEmpty() || instructor.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Vui lòng nhập đầy đủ thông tin đồ án.");
                } else {
                    boolean success = doAnDAO.addDoAn(name, deadline, instructor);
                    if (success) {
                        updateTableData();
                        JOptionPane.showMessageDialog(null, "Đã thêm đồ án thành công.");
                        clearInputFields();
                    } else {
                        JOptionPane.showMessageDialog(null, "Thêm đồ án không thành công. Vui lòng thử lại.");
                    }
                }
            }
        });

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = doAnTable.getSelectedRow();
                if (selectedRow != -1) {
                    int id = (int) doAnTable.getValueAt(selectedRow, 0);
                    String name = nameField.getText();
                    String deadline = convertDateToString(dateChooser.getDate());
                    String instructor = instructorField.getText();

                    if (name.isEmpty() || deadline.isEmpty() || instructor.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Vui lòng nhập đầy đủ thông tin đồ án.");
                    } else {
                        boolean success = doAnDAO.updateDoAn(id, name, deadline, instructor);
                        if (success) {
                            updateTableData();
                            JOptionPane.showMessageDialog(null, "Đã cập nhật đồ án thành công.");
                            clearInputFields();
                        } else {
                            JOptionPane.showMessageDialog(null, "Cập nhật đồ án không thành công. Vui lòng thử lại.");
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Vui lòng chọn một đồ án để sửa.");
                }
            }
        });

        // Thêm ListSelectionListener cho JTable
        doAnTable.getSelectionModel().addListSelectionListener(e -> {
            // Kiểm tra xem đã chọn một dòng hay chưa
            if (!e.getValueIsAdjusting()) {
                int selectedRow = doAnTable.getSelectedRow();
                if (selectedRow != -1) {
                    int id = (int) doAnTable.getValueAt(selectedRow, 0);
                    String name = (String) doAnTable.getValueAt(selectedRow, 1);
                    dateChooser.setDate(convertStringToDate((String) doAnTable.getValueAt(selectedRow, 2)));
                    String instructor = (String) doAnTable.getValueAt(selectedRow, 3);

                    // Đổ thông tin vào các trường nhập liệu
                    nameField.setText(name);
                    instructorField.setText(instructor);
                }
            }
        });

        doAnTable.getSelectionModel().addListSelectionListener(e -> {
            if (doAnTable.getSelectedRow() == -1) {
                clearInputFields();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = doAnTable.getSelectedRow();
                if (selectedRow != -1) {
                    int id = (int) doAnTable.getValueAt(selectedRow, 0);

                    int confirm = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn xóa đồ án này?", "Xác nhận Xóa", JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        boolean success = doAnDAO.deleteDoAn(id);
                        if (success) {
                            updateTableData();
                            JOptionPane.showMessageDialog(null, "Đã xóa đồ án thành công.");
                            clearInputFields();
                        } else {
                            JOptionPane.showMessageDialog(null, "Xóa đồ án không thành công. Vui lòng thử lại.");
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Vui lòng chọn một đồ án để xóa.");
                }
            }
        });
        
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Lấy từ khóa tìm kiếm từ ô nhập liệu
                String keyword = searchField.getText();

                // Thực hiện tìm kiếm đồ án
                List<DoAn> searchResult = doAnDAO.searchDoAn(keyword);

                // Cập nhật bảng dữ liệu với kết quả tìm kiếm
                updateTableData(searchResult);
            }
        });

        rightPanel.add(buttonPanel, BorderLayout.SOUTH);

        splitPane.setRightComponent(rightPanel);
        splitPane.setResizeWeight(0.5);
    }
    
    private void updateTableData(List<DoAn> doAnList) {
        // Xóa toàn bộ dữ liệu trong bảng
        tableModel.setRowCount(0);

        // Thêm dữ liệu mới vào bảng
        for (DoAn doAn : doAnList) {
            Object[] row = {doAn.getId(), doAn.getName(), doAn.getDeadline(), doAn.getInstructor()};
            tableModel.addRow(row);
        }
    }

    private void updateTableData() {
        // Xóa toàn bộ dữ liệu trong bảng
        tableModel.setRowCount(0);

        // Lấy danh sách đồ án từ CSDL và thêm vào bảng
        List<DoAn> doAnList = doAnDAO.getAllDoAn();
        for (DoAn doAn : doAnList) {
            Object[] row = {doAn.getId(), doAn.getName(), doAn.getDeadline(), doAn.getInstructor()};
            tableModel.addRow(row);
        }
    }

    private void clearInputFields() {
        nameField.setText("");
        dateChooser.setDate(null);
        instructorField.setText("");
    }

    private JPanel createInputPanel(String labelText, JComponent inputComponent) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel lblInput = new JLabel(labelText);
        lblInput.setPreferredSize(new Dimension(80, 20));
        panel.add(lblInput);
        panel.add(inputComponent);
        return panel;
    }

    private String convertDateToString(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return (date != null) ? sdf.format(date) : "";
    }

    private Date convertStringToDate(String dateString) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            return sdf.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
