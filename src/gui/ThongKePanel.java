package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import dao.DoAnDAO;
import dao.ThongKeDAO;
import dto.DoAn;
import dto.ThongKe;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Vector;

public class ThongKePanel extends JPanel {
    private JTable thongKeTable;
    private DefaultTableModel tableModel;
    private JTextField searchField, pointGVField, instructorField, pointHDField, pointHDCField, tongDiemField, pointTBField;
    private JButton searchButton, viewButton, addButton, editButton, deleteButton;
    private JComboBox<DoAn> projectComboBox;

    private DoAnDAO doAnDAO;
    private List<DoAn> doAnList;
    private ThongKeDAO thongKeDAO;

    public ThongKePanel() {
        thongKeDAO = new ThongKeDAO();

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
        tableModel.addColumn("Dự Án");
        tableModel.addColumn("Instructor");
        tableModel.addColumn("PointGV");
        tableModel.addColumn("PointHD");
        tableModel.addColumn("PointHDC");
        tableModel.addColumn("TongDiem");
        tableModel.addColumn("PointTB");

        thongKeTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(thongKeTable);
        splitPane.setLeftComponent(scrollPane);

        // Panel ở bên phải (FlowLayout)
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BorderLayout());

        JPanel pnNortOfRightPanel = new JPanel();
        pnNortOfRightPanel.setPreferredSize(new Dimension(0, 100));
        rightPanel.add(pnNortOfRightPanel, BorderLayout.NORTH);

        JPanel pnInput = new JPanel();
        pnInput.setLayout(new GridLayout(0, 1));
        rightPanel.add(pnInput, BorderLayout.CENTER);

        pointGVField = new JTextField(25);
        pointHDField = new JTextField(25);
        pointHDCField = new JTextField(25);
        tongDiemField = new JTextField(25);
        pointTBField = new JTextField(25);
        instructorField = new JTextField(25);
        projectComboBox = new JComboBox<>();
        projectComboBox.setPreferredSize(new Dimension(250, 30));

        pnInput.add(createInputPanel("Dự Án:", projectComboBox));
        pnInput.add(createInputPanel("PointGV:", pointGVField));
        pnInput.add(createInputPanel("Instructor:", instructorField));
        pnInput.add(createInputPanel("PointHD:", pointHDField));
        pnInput.add(createInputPanel("PointHDC:", pointHDCField));
        pnInput.add(createInputPanel("TongDiem:", tongDiemField));
        pnInput.add(createInputPanel("PointTB:", pointTBField));

        // Panel chứa các nút bấm
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setPreferredSize(new Dimension(0, 200));

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
                clearInputFields();
            }
        });

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addThongKe();
            }
        });

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editThongKe();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteThongKe();
            }
        });

        rightPanel.add(buttonPanel, BorderLayout.SOUTH);

        splitPane.setRightComponent(rightPanel);
        splitPane.setResizeWeight(0.5);

        // Khởi tạo DoAnDAO và lấy danh sách đồ án
        doAnDAO = new DoAnDAO();
        doAnList = doAnDAO.getAllDoAn();

        // Set dữ liệu cho ComboBox đồ án
        setProjectComboBox(convertDoAnListToVector(doAnList));

        thongKeTable.getSelectionModel().addListSelectionListener((event) -> {
            fillInputFieldsFromSelectedRow();
        });
    }

    private void addThongKe() {
        // Thêm logic xử lý khi nhấn nút "Add ThongKe"
        // Lấy dữ liệu từ các trường nhập liệu
        int selectedProjectIndex = projectComboBox.getSelectedIndex();
        if (selectedProjectIndex != -1) {
            DoAn selectedDoAn = doAnList.get(selectedProjectIndex);
            String instructor = instructorField.getText();
            double pointGV = Double.parseDouble(pointGVField.getText());
            double pointHD = Double.parseDouble(pointHDField.getText());
            double pointHDC = Double.parseDouble(pointHDCField.getText());
            double tongDiem = Double.parseDouble(tongDiemField.getText());
            double pointTB = Double.parseDouble(pointTBField.getText());

            // Tạo đối tượng ThongKe và thêm vào CSDL
            ThongKe thongKe = new ThongKe(selectedDoAn.getId(), instructor, pointGV, pointHD, pointHDC, tongDiem, pointTB);
            boolean success = thongKeDAO.addThongKe(thongKe);

            if (success) {
                updateTableData();
                clearInputFields();
                JOptionPane.showMessageDialog(this, "Thêm thông tin thống kê thành công");
            } else {
                JOptionPane.showMessageDialog(this, "Thêm thông tin thống kê thất bại");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một dự án");
        }
    }

    private void editThongKe() {
        // Thêm logic xử lý khi nhấn nút "Edit ThongKe"
        int selectedRow = thongKeTable.getSelectedRow();
        if (selectedRow != -1) {
            int id = (int) thongKeTable.getValueAt(selectedRow, 0);
            int selectedProjectIndex = projectComboBox.getSelectedIndex();
            if (selectedProjectIndex != -1) {
                DoAn selectedDoAn = doAnList.get(selectedProjectIndex);
                String instructor = instructorField.getText();
                double pointGV = Double.parseDouble(pointGVField.getText());
                double pointHD = Double.parseDouble(pointHDField.getText());
                double pointHDC = Double.parseDouble(pointHDCField.getText());
                double tongDiem = Double.parseDouble(tongDiemField.getText());
                double pointTB = Double.parseDouble(pointTBField.getText());

                // Tạo đối tượng ThongKe và cập nhật vào CSDL
                ThongKe thongKe = new ThongKe(id, selectedDoAn.getId(), instructor, pointGV, pointHD, pointHDC, tongDiem, pointTB);
                boolean success = thongKeDAO.updateThongKe(thongKe);

                if (success) {
                    updateTableData();
                    clearInputFields();
                    JOptionPane.showMessageDialog(this, "Cập nhật thông tin thống kê thành công");
                } else {
                    JOptionPane.showMessageDialog(this, "Cập nhật thông tin thống kê thất bại");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một dự án");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một dòng để sửa");
        }
    }

    private void deleteThongKe() {
        // Thêm logic xử lý khi nhấn nút "Delete ThongKe"
        int selectedRow = thongKeTable.getSelectedRow();
        if (selectedRow != -1) {
            int id = (int) thongKeTable.getValueAt(selectedRow, 0);
            boolean success = thongKeDAO.deleteThongKe(id);

            if (success) {
                updateTableData();
                clearInputFields();
                JOptionPane.showMessageDialog(this, "Xóa thông tin thống kê thành công");
            } else {
                JOptionPane.showMessageDialog(this, "Xóa thông tin thống kê thất bại");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một dòng để xóa");
        }
    }

    private void updateTableData() {
        // Thêm logic để lấy dữ liệu từ CSDL và cập nhật bảng
        List<ThongKe> thongKeList = thongKeDAO.getAllThongKe();
        displayThongKeList(thongKeList);
    }

    private void displayThongKeList(List<ThongKe> thongKeList) {
        tableModel.setRowCount(0);

        for (ThongKe thongKe : thongKeList) {
            Object[] row = {thongKe.getID(), thongKe.getID_Project(), thongKe.getInstructor(),
                            thongKe.getPointGV(), thongKe.getPointHD(), thongKe.getPointHDC(),
                            thongKe.getTongDiem(), thongKe.getPointTB()};
            tableModel.addRow(row);
        }
    }

    private void clearInputFields() {
        pointGVField.setText("");
        pointHDField.setText("");
        pointHDCField.setText("");
        tongDiemField.setText("");
        pointTBField.setText("");
        instructorField.setText("");
        projectComboBox.setSelectedIndex(0);
    }
    
    private void fillInputFieldsFromSelectedRow() {
        int selectedRow = thongKeTable.getSelectedRow();
        if (selectedRow != -1) {
            int id = (int) thongKeTable.getValueAt(selectedRow, 0);
            int projectID = (int) thongKeTable.getValueAt(selectedRow, 1); // Giả sử cột 1 chứa ID dự án
            String instructor = (String) thongKeTable.getValueAt(selectedRow, 2);
            double pointGV = (double) thongKeTable.getValueAt(selectedRow, 3);
            double pointHD = (double) thongKeTable.getValueAt(selectedRow, 4);
            double pointHDC = (double) thongKeTable.getValueAt(selectedRow, 5);
            double tongDiem = (double) thongKeTable.getValueAt(selectedRow, 6);
            double pointTB = (double) thongKeTable.getValueAt(selectedRow, 7);

            // Lấy dự án tương ứng để hiển thị trong ComboBox
            for (int i = 0; i < projectComboBox.getItemCount(); i++) {
                DoAn project = projectComboBox.getItemAt(i);
                if (project.getId() == projectID) {
                    projectComboBox.setSelectedItem(project);
                    break;
                }
            }

            // Đổ dữ liệu vào các trường nhập liệu
            pointGVField.setText(String.valueOf(pointGV));
            pointHDField.setText(String.valueOf(pointHD));
            pointHDCField.setText(String.valueOf(pointHDC));
            tongDiemField.setText(String.valueOf(tongDiem));
            pointTBField.setText(String.valueOf(pointTB));
            instructorField.setText(instructor);
        }
    }

    private JPanel createInputPanel(String labelText, JComponent jComponent) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel lblInput = new JLabel(labelText);
        lblInput.setPreferredSize(new Dimension(150, 20));
        panel.add(lblInput);
        panel.add(jComponent);
        return panel;
    }

    public void setProjectComboBox(Vector<DoAn> projects) {
        DefaultComboBoxModel<DoAn> model = new DefaultComboBoxModel<>(projects);
        projectComboBox.setModel(model);
    }

    private Vector<DoAn> convertDoAnListToVector(List<DoAn> doAnList) {
        return new Vector<>(doAnList);
    }
}
