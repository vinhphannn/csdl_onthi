package on_thi_csdl2;

import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class GUI {

    // Text fields
    JTextField txtProductID = new JTextField();
    JTextField txtProductName = new JTextField();
    JTextField txtPrice = new JTextField();
    JTextField txtQuantity = new JTextField();
    JTextField txtDescription = new JTextField();

    // Buttons
    JButton btnThem = new JButton("THÊM");
    JButton btnXoa = new JButton("XÓA");
    JButton btnSua = new JButton("SỬA");
    JButton btnThoat = new JButton("THOÁT");
    JButton btnClear = new JButton("CLEAR");  // Nút Clear mới

    // ComboBox
    JComboBox<String> cbCateID = new JComboBox<>();

    // Table columns
    public String[] col = {"Mã sách", "Tên sách", "Mã thể loại", "Giá", "Số lượng", "Mô tả"};
    public DefaultTableModel model = new DefaultTableModel(col, 0);
    public JTable table = new JTable(model);

    // Database credentials
    String DB_URL = "jdbc:mysql://localhost:3306/csdl_sach";
    String USER_NAME = "root";
    String PASSWORD = "";

    private int index = -1;

    public void initUI() {
        // Frame
        JFrame frame = new JFrame("Quản lý sách");
        frame.setSize(830, 420);
        frame.setLayout(null);

        // Labels and text fields
        JLabel lbProductID = new JLabel("Mã sách:");
        lbProductID.setBounds(20, 20, 80, 25);
        frame.add(lbProductID);
        txtProductID.setBounds(100, 20, 80, 25);
        frame.add(txtProductID);

        JLabel lbProductName = new JLabel("Tên sách:");
        lbProductName.setBounds(20, 60, 100, 25);
        frame.add(lbProductName);
        txtProductName.setBounds(120, 60, 220, 25);
        frame.add(txtProductName);

        JLabel lbPrice = new JLabel("Giá:");
        lbPrice.setBounds(20, 100, 80, 25);
        frame.add(lbPrice);
        txtPrice.setBounds(100, 100, 80, 25);
        frame.add(txtPrice);

        JLabel lbQuantity = new JLabel("Số lượng:");
        lbQuantity.setBounds(20, 150, 80, 25);
        frame.add(lbQuantity);
        txtQuantity.setBounds(100, 150, 80, 25);
        frame.add(txtQuantity);

        JLabel lbDescription = new JLabel("Mô tả:");
        lbDescription.setBounds(20, 200, 80, 25);
        frame.add(lbDescription);
        txtDescription.setBounds(100, 200, 220, 25);
        frame.add(txtDescription);

        JLabel lbCateID = new JLabel("Mã thể loại:");
        lbCateID.setBounds(20, 250, 80, 25);
        frame.add(lbCateID);
        cbCateID.setBounds(100, 250, 150, 25);
        frame.add(cbCateID);

        // Table and scroll pane
        JScrollPane pane = new JScrollPane(table);
        JPanel panel = new JPanel();
        panel.add(pane);
        panel.setBounds(350, 20, 450, 300);
        frame.add(panel);

        // Buttons
        btnThem.setBounds(100, 300, 90, 25);
        frame.add(btnThem);

        btnSua.setBounds(230, 300, 90, 25);
        frame.add(btnSua);

        btnXoa.setBounds(100, 340, 90, 25);
        frame.add(btnXoa);

        btnThoat.setBounds(230, 340, 90, 25);
        frame.add(btnThoat);
        
        btnClear.setBounds(350, 340, 90, 25);
        frame.add(btnClear);

        // Button state
        btnXoa.setEnabled(false);
        btnSua.setEnabled(false);

        // Frame visibility
        frame.setVisible(true);

        // Table event listener
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                index = table.getSelectedRow();
                txtProductID.setText(table.getValueAt(index, 0).toString());
                txtProductName.setText(table.getValueAt(index, 1).toString());
                cbCateID.setSelectedItem(table.getValueAt(index, 2).toString());
                txtPrice.setText(table.getValueAt(index, 3).toString());
                txtQuantity.setText(table.getValueAt(index, 4).toString());
                txtDescription.setText(table.getValueAt(index, 5).toString());
                btnXoa.setEnabled(true);
                btnSua.setEnabled(true);
            }
        });

        // Add button event listener
        btnThem.addActionListener(e -> {
            try {
                if (validateInputs()) {
                    String query = "INSERT INTO sach (MaSach, TenSach, MaTheLoai, Gia, SoLuong, MoTa) VALUES (?, ?, ?, ?, ?, ?)";
                    try (Connection cnn = DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD);
                         PreparedStatement pstm = cnn.prepareStatement(query)) {
                        pstm.setInt(1, Integer.parseInt(txtProductID.getText().trim()));
                        pstm.setString(2, txtProductName.getText().trim());
                        pstm.setInt(3, Integer.parseInt(cbCateID.getSelectedItem().toString().trim()));
                        pstm.setDouble(4, Double.parseDouble(txtPrice.getText().trim()));
                        pstm.setInt(5, Integer.parseInt(txtQuantity.getText().trim()));
                        pstm.setString(6, txtDescription.getText().trim());
                        int result = pstm.executeUpdate();
                        if (result == 1) {
                            model.addRow(new Object[]{
                                txtProductID.getText(),
                                txtProductName.getText(),
                                cbCateID.getSelectedItem().toString(),
                                txtPrice.getText(),
                                txtQuantity.getText(),
                                txtDescription.getText()
                            });
                            clearForm();
                        }
                    }
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        });
        
        // Update button event listener
        btnSua.addActionListener(e -> {
            try {
                if (validateInputs()) {
                    String query = "UPDATE sach SET TenSach = ?, MaTheLoai = ?, Gia = ?, SoLuong = ?, MoTa = ? WHERE MaSach = ?";
                    try (Connection cnn = DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD);
                         PreparedStatement pstm = cnn.prepareStatement(query)) {
                        pstm.setString(1, txtProductName.getText().trim());
                        pstm.setInt(2, Integer.parseInt(cbCateID.getSelectedItem().toString().trim()));
                        pstm.setDouble(3, Double.parseDouble(txtPrice.getText().trim()));
                        pstm.setInt(4, Integer.parseInt(txtQuantity.getText().trim()));
                        pstm.setString(5, txtDescription.getText().trim());
                        pstm.setInt(6, Integer.parseInt(txtProductID.getText().trim()));
                        int result = pstm.executeUpdate();
                        if (result == 1) {
                            // Cập nhật lại bảng
                            model.setValueAt(txtProductName.getText(), index, 1);
                            model.setValueAt(cbCateID.getSelectedItem().toString(), index, 2);
                            model.setValueAt(txtPrice.getText(), index, 3);
                            model.setValueAt(txtQuantity.getText(), index, 4);
                            model.setValueAt(txtDescription.getText(), index, 5);
                            clearForm();  // Làm sạch form sau khi sửa
                        }
                    }
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        });
        
        // Delete button event listener
        btnXoa.addActionListener(e -> {
            try {
                int confirmation = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn xóa sản phẩm này?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
                if (confirmation == JOptionPane.YES_OPTION) {
                    String query = "DELETE FROM sach WHERE MaSach = ?";
                    try (Connection cnn = DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD);
                         PreparedStatement pstm = cnn.prepareStatement(query)) {
                        pstm.setInt(1, Integer.parseInt(txtProductID.getText().trim()));
                        int result = pstm.executeUpdate();
                        if (result == 1) {
                            // Xóa khỏi bảng trong UI
                            model.removeRow(index);
                            clearForm();  // Làm sạch form sau khi xóa
                            btnXoa.setEnabled(false);
                            btnSua.setEnabled(false);
                        }
                    }
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        });
        // Add button event listener for the Clear button
        btnClear.addActionListener(e -> {
            clearForm(); // Gọi phương thức clearForm để làm trống các trường nhập liệu
        });

        // Exit button event listener
        btnThoat.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(frame, "Bạn có muốn thoát?", "Xác nhận thoát", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });


        // Update and Delete button logic remains the same...

        // Load data to table and combobox
        loadData();
        loadComboboxData();
    }
        // Validate inputs
    private boolean validateInputs() throws Exception {
        if (txtProductID.getText().isEmpty()) throw new Exception("Product ID không được để trống!");
        if (!isNumeric(txtProductID.getText())) throw new Exception("Product ID phải là số!");

        if (txtProductName.getText().isEmpty()) throw new Exception("Product Name không được để trống!");

        if (txtPrice.getText().isEmpty()) throw new Exception("Price không được để trống!");
        if (!isDouble(txtPrice.getText())) throw new Exception("Price phải là một số thực!");

        if (txtQuantity.getText().isEmpty()) throw new Exception("Quantity không được để trống!");
        if (!isNumeric(txtQuantity.getText())) throw new Exception("Quantity phải là số!");

        if (txtDescription.getText().isEmpty()) throw new Exception("Description không được để trống!");

        if (cbCateID.getSelectedItem() == null) throw new Exception("Category ID không được để trống!");

        return true;
    }

    // Helper method to check if a string is an integer
    private boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // Helper method to check if a string is a double
    private boolean isDouble(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
        // Clear all form fields
    private void clearForm() {
        txtProductID.setText("");
        txtProductName.setText("");
        txtPrice.setText("");
        txtQuantity.setText("");
        txtDescription.setText("");
        cbCateID.setSelectedIndex(0); // Đặt lại combobox về giá trị mặc định, ví dụ chọn phần tử đầu tiên
    }



    // Load data to table
    public void loadData() {
        try (Connection cnn = DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD);
             Statement stm = cnn.createStatement()) {
            ResultSet rs = stm.executeQuery("SELECT MaSach, TenSach , MaTheLoai , Gia , SoLuong , MoTa FROM sach");
            model.setRowCount(0);
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("MaSach"),
                    rs.getString("TenSach"),
                    rs.getInt("MaTheLoai"),
                    rs.getDouble("Gia"),
                    rs.getInt("SoLuong"),
                    rs.getString("MoTa")
                });
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }

    // Load categories to combobox
    public void loadComboboxData() {
        try (Connection cnn = DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD);
             Statement stm = cnn.createStatement()) {
            ResultSet rs = stm.executeQuery("SELECT MaTheLoai FROM the_loai");
            cbCateID.removeAllItems();
            while (rs.next()) {
                cbCateID.addItem(rs.getString("MaTheLoai"));
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }
}
