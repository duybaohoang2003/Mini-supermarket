/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package supermarket.View;

/**
 *
 * @author DELL
 */
import Model.NhaCungCap;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import Model.NhaCungCap;
import DAO.NhaCungCapDAO;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class nhacungcap extends JPanel {

    private DefaultTableModel model;
    private JTable table;
    private JLabel lbId;
    private JLabel lbTenNCC;
    private JLabel lbDiaChi;
    private JLabel lbSDT;

    private JTextField txtIdField;
    private JTextField txtTenField;
    private JTextField txtDiaChiField;
    private JTextField txtSDTField;
    private JTextField searchField = new JTextField(15);

    private JButton btnThemButton;
    private JButton btnsuaButton;
    private JButton btnXoaButton;
    private JButton btnXuatEX;
    private JButton searchButton = new JButton("Tìm kiếm theo tên");

    private NhaCungCap nhaCungCap;
    private NhaCungCapDAO NCCDAO;

    public nhacungcap() {
        initComponents();
        fetchDataFromDatabase();

    }

    private void initComponents() {
        // Thêm panel chính vào JFrame
        JPanel panelMain = new JPanel();
        JPanel panel1 = new JPanel();
        JPanel panel2 = new JPanel();
        JPanel panel3 = new JPanel();
        JPanel panel4 = new JPanel();

        SpringLayout layoutMain = new SpringLayout();
        panelMain.setLayout(layoutMain);
        panelMain.add(panel1);
        panelMain.add(panel2);
        panel2.setPreferredSize(new Dimension(0, 80));
        panelMain.add(panel3);
        panel3.setPreferredSize(new Dimension(0, 290));
        panelMain.add(panel4);

        layoutMain.putConstraint(SpringLayout.NORTH, panel1, 10, SpringLayout.NORTH, panelMain);
        layoutMain.putConstraint(SpringLayout.WEST, panel1, 10, SpringLayout.WEST, panelMain);
        layoutMain.putConstraint(SpringLayout.EAST, panel1, -10, SpringLayout.EAST, panelMain);

        // Đặt ràng buộc cho panel2
        layoutMain.putConstraint(SpringLayout.NORTH, panel2, 10, SpringLayout.SOUTH, panel1);
        layoutMain.putConstraint(SpringLayout.WEST, panel2, 10, SpringLayout.WEST, panelMain);
        layoutMain.putConstraint(SpringLayout.EAST, panel2, -10, SpringLayout.EAST, panelMain);

        // Đặt ràng buộc cho panel3
        layoutMain.putConstraint(SpringLayout.NORTH, panel3, 10, SpringLayout.SOUTH, panel2);
        layoutMain.putConstraint(SpringLayout.WEST, panel3, 10, SpringLayout.WEST, panelMain);
        layoutMain.putConstraint(SpringLayout.EAST, panel3, -10, SpringLayout.EAST, panelMain);

        // Đặt ràng buộc cho panel4
        layoutMain.putConstraint(SpringLayout.NORTH, panel4, 10, SpringLayout.SOUTH, panel3);
        layoutMain.putConstraint(SpringLayout.WEST, panel4, 10, SpringLayout.WEST, panelMain);
        layoutMain.putConstraint(SpringLayout.EAST, panel4, -10, SpringLayout.EAST, panelMain);
        layoutMain.putConstraint(SpringLayout.SOUTH, panel4, -10, SpringLayout.SOUTH, panelMain);

        //panel1
        panel1.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0; // Cho phép label mở rộng trên cả chiều ngang
        gbc.anchor = GridBagConstraints.CENTER; // Căn giữa
        lbId = new JLabel("THÔNG TIN NHÀ CUNG CẤP");
        lbId.setFont(new Font("Arial", Font.BOLD, 24));
        panel1.add(lbId, gbc);

        //panel2
        SpringLayout layout2 = new SpringLayout();
        panel2.setLayout(layout2);

        // Thiết lập các JLabel
        lbId = new JLabel("ID: ");
        lbTenNCC = new JLabel("Nhà cung cấp: ");
        lbDiaChi = new JLabel("Địa chỉ: ");
        lbSDT = new JLabel("Số điện thoại:");
        txtIdField = new JTextField(15);
        txtIdField.setEditable(false);
        txtTenField = new JTextField(15);
        txtDiaChiField = new JTextField(15);
        txtSDTField = new JTextField(15);

        lbId.setFont(new Font("Arial", Font.BOLD, 14));
        lbTenNCC.setFont(new Font("Arial", Font.BOLD, 14));
        lbDiaChi.setFont(new Font("Arial", Font.BOLD, 14));
        lbSDT.setFont(new Font("Arial", Font.BOLD, 14));

        // Thêm các JLabel vào panel
        panel2.add(lbId);
        panel2.add(txtIdField);
        panel2.add(lbTenNCC);
        panel2.add(txtTenField);
        panel2.add(lbDiaChi);
        panel2.add(txtDiaChiField);
        panel2.add(lbSDT);
        panel2.add(txtSDTField);

        // Thiết lập các ràng buộc
        layout2.putConstraint(SpringLayout.WEST, lbId, 50, SpringLayout.WEST, panel2);
        layout2.putConstraint(SpringLayout.NORTH, lbId, 10, SpringLayout.NORTH, panel2);

        layout2.putConstraint(SpringLayout.WEST, txtIdField, 120, SpringLayout.WEST, panel2);
        layout2.putConstraint(SpringLayout.NORTH, txtIdField, 10, SpringLayout.NORTH, panel2);

        layout2.putConstraint(SpringLayout.WEST, lbTenNCC, 500, SpringLayout.WEST, panel2);
        layout2.putConstraint(SpringLayout.NORTH, lbTenNCC, 10, SpringLayout.NORTH, panel2);

        layout2.putConstraint(SpringLayout.WEST, txtTenField, 620, SpringLayout.WEST, panel2);
        layout2.putConstraint(SpringLayout.NORTH, txtTenField, 10, SpringLayout.NORTH, panel2);

        layout2.putConstraint(SpringLayout.WEST, lbDiaChi, 50, SpringLayout.WEST, panel2);
        layout2.putConstraint(SpringLayout.NORTH, lbDiaChi, 40, SpringLayout.NORTH, panel2);

        layout2.putConstraint(SpringLayout.WEST, txtDiaChiField, 120, SpringLayout.WEST, panel2);
        layout2.putConstraint(SpringLayout.NORTH, txtDiaChiField, 40, SpringLayout.NORTH, panel2);

        layout2.putConstraint(SpringLayout.WEST, lbSDT, 500, SpringLayout.WEST, panel2);
        layout2.putConstraint(SpringLayout.NORTH, lbSDT, 40, SpringLayout.NORTH, panel2);

        layout2.putConstraint(SpringLayout.WEST, txtSDTField, 620, SpringLayout.WEST, panel2);
        layout2.putConstraint(SpringLayout.NORTH, txtSDTField, 40, SpringLayout.NORTH, panel2);

        //panel3
        JPanel pnTable = new JPanel();
        pnTable.setLayout(new BorderLayout());
        model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Tên nhà cung cấp");
        model.addColumn("Địa chỉ");
        model.addColumn("Số điện thoại");

        table = new JTable(model);

        table.setDefaultEditor(Object.class, null);

        // Thêm ListSelectionListener để xử lý sự kiện khi người dùng chọn một dòng trong bảng
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) { // Nếu có dòng được chọn
                    // Lấy dữ liệu từ dòng được chọn và hiển thị lên panel 2
                    txtIdField.setText(model.getValueAt(selectedRow, 0).toString());
                    txtTenField.setText(model.getValueAt(selectedRow, 1).toString());
                    txtDiaChiField.setText(model.getValueAt(selectedRow, 2).toString());
                    txtSDTField.setText(model.getValueAt(selectedRow, 3).toString());
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);
        pnTable.add(scrollPane, BorderLayout.CENTER); // Đặt scrollPane vào vị trí CENTER của pnTable
        panel3.setLayout(new BorderLayout());
        panel3.add(pnTable, BorderLayout.CENTER);

        //panel4
        SpringLayout layout4 = new SpringLayout();
        //panel4.setLayout(new FlowLayout(FlowLayout.RIGHT));
        panel4.setLayout(layout4);
        // Tạo JLabel và JButton

        //nút Thêm
        btnThemButton = new JButton("Thêm NCC");
        btnThemButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String TenNCC = txtTenField.getText();
                String DiaChi = txtDiaChiField.getText();
                String SoDienThoai = txtSDTField.getText();
                nhaCungCap = new NhaCungCap();
                nhaCungCap.setTenNCC(TenNCC);
                nhaCungCap.setDiaChiNCC(DiaChi);
                nhaCungCap.setDienThoaiNCC(SoDienThoai);
                NCCDAO = new NhaCungCapDAO();
                NCCDAO.ThemNhaCungCap(nhaCungCap);
                fetchDataFromDatabase();
            }
        });

        //nút Sửa thông tin
        btnsuaButton = new JButton("Sửa NCC");
        btnsuaButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int ID = Integer.parseInt(txtIdField.getText());
                String TenNCC = txtTenField.getText();
                String DiaChi = txtDiaChiField.getText();
                String SoDienThoai = txtSDTField.getText();
                nhaCungCap = new NhaCungCap();
                nhaCungCap.setId(ID);
                nhaCungCap.setTenNCC(TenNCC);
                nhaCungCap.setDiaChiNCC(DiaChi);
                nhaCungCap.setDienThoaiNCC(SoDienThoai);
                NCCDAO = new NhaCungCapDAO();
                NCCDAO.suaNCC(nhaCungCap);
                fetchDataFromDatabase();
            }
        });
        //nút Xoá
        btnXoaButton = new JButton("Xoá NCC");
        btnXoaButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int confirmed = JOptionPane.showConfirmDialog(null,
                        "Bạn có chắc chắn muốn xoá nhà cung cấp?", "Xác nhận xoá",
                        JOptionPane.YES_NO_OPTION);

                if (confirmed == JOptionPane.YES_OPTION) {
                    nutXoathongtin(); // Gọi phương thức xóa nếu người dùng chọn YES
                }
            }
        });

        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String keyword = searchField.getText(); // Lấy từ khóa tìm kiếm từ JTextField
                searchSupplierByName(keyword); // Gọi phương thức tìm kiếm theo tên
            }
        });
        btnXuatEX = new JButton("Xuất EXCEL");
        btnXuatEX.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                exportToExcel();
            }
        });
        // Thêm các thành phần vào panel
        panel4.add(searchField);
        layout4.putConstraint(SpringLayout.WEST, searchField, 10, SpringLayout.WEST, panel4);
        layout4.putConstraint(SpringLayout.NORTH, searchField, 7, SpringLayout.NORTH, panel4);
        panel4.add(searchButton);
        layout4.putConstraint(SpringLayout.WEST, searchButton, 185, SpringLayout.WEST, panel4);
        layout4.putConstraint(SpringLayout.NORTH, searchButton, 5, SpringLayout.NORTH, panel4);
        panel4.add(btnThemButton);
        layout4.putConstraint(SpringLayout.WEST, btnThemButton, 795, SpringLayout.WEST, panel4);
        layout4.putConstraint(SpringLayout.NORTH, btnThemButton, 5, SpringLayout.NORTH, panel4);
        panel4.add(btnsuaButton);
        layout4.putConstraint(SpringLayout.WEST, btnsuaButton, 891, SpringLayout.WEST, panel4);
        layout4.putConstraint(SpringLayout.NORTH, btnsuaButton, 5, SpringLayout.NORTH, panel4);
        panel4.add(btnXoaButton);
        layout4.putConstraint(SpringLayout.WEST, btnXoaButton, 979, SpringLayout.WEST, panel4);
        layout4.putConstraint(SpringLayout.NORTH, btnXoaButton, 5, SpringLayout.NORTH, panel4);
        panel4.add(btnXuatEX);
        layout4.putConstraint(SpringLayout.WEST, btnXuatEX, 1065, SpringLayout.WEST, panel4);
        layout4.putConstraint(SpringLayout.NORTH, btnXuatEX, 5, SpringLayout.NORTH, panel4);

        panelMain.setPreferredSize(new Dimension(1190, 500));
        add(panelMain);

    }

    private void fetchDataFromDatabase() {
        String url = "jdbc:mysql://localhost:3306/test_supermarket";
        String dbClass = "com.mysql.cj.jdbc.Driver";
        String username = "root";
        String password = "";

        try (Connection con = DriverManager.getConnection(url, username, password); Statement statement = con.createStatement()) {

            String query = "SELECT * FROM nhacungcap"; // Truy vấn dữ liệu từ bảng 'nhacungcap'
            try (ResultSet resultSet = statement.executeQuery(query)) {
                // Xóa dữ liệu cũ trong bảng
                model.setRowCount(0);

                while (resultSet.next()) {
                    String id = resultSet.getString("Id");
                    String tenNCC = resultSet.getString("TenNCC");
                    String diaChi = resultSet.getString("DiaChiNCC");
                    String sdt = resultSet.getString("DienThoaiNCC");

                    model.addRow(new Object[]{id, tenNCC, diaChi, sdt});
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void nutXoathongtin() {
        String url = "jdbc:mysql://localhost:3306/test_supermarket";
        String user = "root";
        String password = "";
        String selectedID = txtIdField.getText(); // Lấy ID được chọn từ JTextField

        // Câu truy vấn SQL để xóa thông tin nhà cung cấp dựa trên ID đã chọn
        String query = "DELETE FROM nhacungcap WHERE ID = ?";

        try (Connection conn = DriverManager.getConnection(url, user, password); PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, selectedID); // Thiết lập tham số cho câu truy vấn

            int rowsAffected = pstmt.executeUpdate(); // Thực thi câu truy vấn xóa và lấy số dòng bị ảnh hưởng

            if (rowsAffected > 0) {
                // Nếu có dòng bị ảnh hưởng (số dòng bị xóa > 0), thông báo xóa thành công
                System.out.println("Đã xóa thông tin nhà cung cấp với ID: " + selectedID);

                // Sau khi xóa thành công, bạn có thể cập nhật lại bảng hoặc giao diện để hiển thị dữ liệu còn lại
                fetchDataFromDatabase(); // Cập nhật lại dữ liệu trong bảng
            } else {
                System.out.println("Xóa thông tin nhà cung cấp không thành công.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void capNhatDuLieu() {
        String url = "jdbc:mysql://localhost:3306/test_supermarket";
        String user = "root";
        String password = "";

        try (Connection conn = DriverManager.getConnection(url, user, password); Statement statement = conn.createStatement()) {

            String query = "SELECT * FROM nhacungcap"; // Truy vấn dữ liệu từ bảng 'nhacungcap'
            try (ResultSet resultSet = statement.executeQuery(query)) {
                // Xóa dữ liệu cũ trong bảng
                model.setRowCount(0);

                while (resultSet.next()) {
                    String id = resultSet.getString("Id");
                    String tenNCC = resultSet.getString("TenNCC");
                    String diaChi = resultSet.getString("DiaChiNCC");
                    String sdt = resultSet.getString("DienThoaiNCC");

                    model.addRow(new Object[]{id, tenNCC, diaChi, sdt});
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void searchSupplierByName(String keyword) {
        String url = "jdbc:mysql://localhost:3306/test_supermarket";
        String user = "root";
        String password = "";

        try (Connection conn = DriverManager.getConnection(url, user, password); PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM nhacungcap WHERE TenNCC LIKE ?")) {

            // Thiết lập tham số cho câu truy vấn
            pstmt.setString(1, "%" + keyword + "%");

            try (ResultSet resultSet = pstmt.executeQuery()) {
                // Xóa dữ liệu cũ trong bảng
                model.setRowCount(0);

                while (resultSet.next()) {
                    String id = resultSet.getString("Id");
                    String tenNCC = resultSet.getString("TenNCC");
                    String diaChi = resultSet.getString("DiaChiNCC");
                    String sdt = resultSet.getString("DienThoaiNCC");

                    // Thêm hàng mới vào bảng cho mỗi kết quả tìm kiếm
                    model.addRow(new Object[]{id, tenNCC, diaChi, sdt});
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void exportToExcel() {
        // Tạo workbook mới
        Workbook workbook = new XSSFWorkbook();

        // Tạo một trang tính mới
        Sheet sheet = workbook.createSheet("Data");

        // Ghi dữ liệu từ bảng vào file Excel
        int rowCount = table.getRowCount();
        int columnCount = table.getColumnCount();

        org.apache.poi.ss.usermodel.Row headerRow = sheet.createRow(0);
        for (int i = 0; i < columnCount; i++) {
            String columnName = table.getColumnName(i);
            headerRow.createCell(i).setCellValue(columnName);
        }

        for (int i = 0; i < rowCount; i++) {
            org.apache.poi.ss.usermodel.Row row = sheet.createRow(i + 1); // Thêm 1 vào để bắt đầu từ dòng 1
            for (int j = 0; j < columnCount; j++) {
                Object value = table.getValueAt(i, j);
                Cell cell = row.createCell(j);
                if (value != null) {
                    cell.setCellValue(value.toString());
                }
            }
        }

        String filePath = "D:\\nhacungcap.xlsx";
        // Lưu workbook vào một file
        try (FileOutputStream outputStream = new FileOutputStream(filePath)) {
            workbook.write(outputStream);
            JOptionPane.showMessageDialog(null, "Data exported to Excel successfully.");
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error exporting data to Excel: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        new nhacungcap();
    }
}
