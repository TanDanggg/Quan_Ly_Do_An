package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import dao.DoAnDAO;
import dao.NoiThucTapDAO;
import dto.DoAn;
import dto.NoiThucTap;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Vector;

public class NoiThucTapPanel extends JPanel {
    private JTable noiThucTapTable;
    private DefaultTableModel tableModel;
    private JTextField addressField, timelineField, instructorField, searchField;
    private JButton viewButton, addButton, editButton, deleteButton, searchButton;
    private JComboBox<DoAn> projectComboBox;
    
    private DoAnDAO doAnDAO;
    private List<DoAn> doAnList;
    
    private NoiThucTapDAO noiThucTapDAO;

    public NoiThucTapPanel() {
    	
    	noiThucTapDAO = new NoiThucTapDAO();
    	
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
        tableModel.addColumn("Internship Address");
        tableModel.addColumn("Timeline");
        tableModel.addColumn("Instructor");
        tableModel.addColumn("Dự án");
        // Thêm các dòng dữ liệu từ CSDL vào bảng (sử dụng SQL query để lấy dữ liệu từ CSDL)

        noiThucTapTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(noiThucTapTable);
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

        addressField = new JTextField(25);
        timelineField = new JTextField(25);
        instructorField = new JTextField(25);
        projectComboBox = new JComboBox<>();
        projectComboBox.setPreferredSize(new Dimension(250, 30));
        
        pnInput.add(createInputPanel("Dự Án:", projectComboBox));

        pnInput.add(createInputPanel("Internship Address:", addressField));
        pnInput.add(createInputPanel("Timeline:", timelineField));
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
                // Xử lý khi nhấn nút "View"
            	updateTableData();
            	clearInputFields();
            }
        });

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Xử lý khi nhấn nút "Add"
            	addNoiThucTap();
            }
        });

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Xử lý khi nhấn nút "Edit"
            	editNoiThucTap();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Xử lý khi nhấn nút "Delete"
            	deleteNoiThucTap();
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
        
        noiThucTapTable.getSelectionModel().addListSelectionListener((event) -> {
            fillInputFieldsFromSelectedRow();
        });
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
    
    // Helper function to find DoAn object by ID in the doAnList
    private DoAn findDoAnByID(int projectID) {
        for (DoAn doAn : doAnList) {
            if (doAn.getId() == projectID) {
                return doAn;
            }
        }
        return null; // Return null if the project is not found
    }

    private JPanel createInputPanel(String labelText, JComponent jComponent) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel lblInput = new JLabel(labelText);
        lblInput.setPreferredSize(new Dimension(150, 20));
        panel.add(lblInput);
        panel.add(jComponent);
        return panel;
    }
    
 // Hàm này để thêm nơi thực tập
    private void addNoiThucTap() {
        String address = addressField.getText();
        String timeline = timelineField.getText();
        String instructor = instructorField.getText();
        DoAn selectedProject = (DoAn) projectComboBox.getSelectedItem();

        NoiThucTap noiThucTap = new NoiThucTap(0, selectedProject.getId(), address, timeline, instructor);

        boolean success = noiThucTapDAO.addNoiThucTap(noiThucTap);
        if (success) {
            updateTableData();
            JOptionPane.showMessageDialog(null, "Đã thêm nơi thực tập thành công.");
            clearInputFields();
        } else {
            JOptionPane.showMessageDialog(null, "Thêm nơi thực tập không thành công. Vui lòng thử lại.");
        }
    }

    // Hàm này để sửa thông tin nơi thực tập
    private void editNoiThucTap() {
        int selectedRow = noiThucTapTable.getSelectedRow();
        if (selectedRow != -1) {
            int id = (int) noiThucTapTable.getValueAt(selectedRow, 0);
            String address = addressField.getText();
            String timeline = timelineField.getText();
            String instructor = instructorField.getText();
            DoAn selectedProject = (DoAn) projectComboBox.getSelectedItem();

            NoiThucTap noiThucTap = new NoiThucTap(id, selectedProject.getId(), address, timeline, instructor);

            boolean success = noiThucTapDAO.updateNoiThucTap(noiThucTap);
            if (success) {
                updateTableData();
                JOptionPane.showMessageDialog(null, "Đã cập nhật nơi thực tập thành công.");
                clearInputFields();
            } else {
                JOptionPane.showMessageDialog(null, "Cập nhật nơi thực tập không thành công. Vui lòng thử lại.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn một nơi thực tập để sửa.");
        }
    }

    // Hàm này để xóa nơi thực tập
    private void deleteNoiThucTap() {
        int selectedRow = noiThucTapTable.getSelectedRow();
        if (selectedRow != -1) {
            int id = (int) noiThucTapTable.getValueAt(selectedRow, 0);

            int confirm = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn xóa nơi thực tập này?",
                    "Xác nhận Xóa", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                boolean success = noiThucTapDAO.deleteNoiThucTap(id);
                if (success) {
                    updateTableData();
                    JOptionPane.showMessageDialog(null, "Đã xóa nơi thực tập thành công.");
                    clearInputFields();
                } else {
                    JOptionPane.showMessageDialog(null, "Xóa nơi thực tập không thành công. Vui lòng thử lại.");
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn một nơi thực tập để xóa.");
        }
    }
    
 // Hàm này để cập nhật dữ liệu trên bảng
    private void updateTableData() {
        List<NoiThucTap> noiThucTapList = noiThucTapDAO.getAllNoiThucTap();
        displayNoiThucTapList(noiThucTapList);
    }
    
 // Hàm này để hiển thị danh sách nơi thực tập lên bảng
    private void displayNoiThucTapList(List<NoiThucTap> noiThucTapList) {
        tableModel.setRowCount(0);

        for (NoiThucTap noiThucTap : noiThucTapList) {
            Object[] row = {noiThucTap.getID(), noiThucTap.getInternshipAddress(), noiThucTap.getTimeLine(),
                            noiThucTap.getInstructor(), noiThucTap.getID_Project()};
            tableModel.addRow(row);
        }
    }
    
 // Hàm này để xóa nội dung trên các trường nhập liệu
    private void clearInputFields() {
        addressField.setText("");
        timelineField.setText("");
        instructorField.setText("");
        projectComboBox.setSelectedIndex(0);
    }
    
 // Hàm này để lấy thông tin từ dòng được chọn trong bảng và đổ lên các trường nhập liệu
    private void fillInputFieldsFromSelectedRow() {
        int selectedRow = noiThucTapTable.getSelectedRow();
        if (selectedRow != -1) {
            int id = (int) noiThucTapTable.getValueAt(selectedRow, 0);
            String address = (String) noiThucTapTable.getValueAt(selectedRow, 1);
            String timeline = (String) noiThucTapTable.getValueAt(selectedRow, 2);
            String instructor = (String) noiThucTapTable.getValueAt(selectedRow, 3);
            int projectID = (int) noiThucTapTable.getValueAt(selectedRow, 4);

            addressField.setText(address);
            timelineField.setText(timeline);
            instructorField.setText(instructor);

            // Chọn đồ án trong ComboBox dựa trên projectID
            for (int i = 0; i < projectComboBox.getItemCount(); i++) {
                DoAn project = projectComboBox.getItemAt(i);
                if (project.getId() == projectID) {
                    projectComboBox.setSelectedItem(project);
                    break;
                }
            }
        }
    }
}
