/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package supermarket.View;

/**
 *
 * @author DELL
 */
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
import DAO.NhaCungCapDAO;
import Dao.HangHoaDao;
import Model.NhaCungCap;
import Model.HangHoa;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.border.Border;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class form extends JPanel {

    private DefaultTableModel model;
    private JTable table;
    private JLabel lbId;
    private JLabel lbIdNhomhang;
    private JLabel lbTen;
    private JLabel lbDVT;
    private JLabel lbDongia;

    private JTextField IdField;
    private JComboBox<String> cbbNhomhang;
    private JTextField TenField;
    private JTextField DVTField;
    private JTextField DongiaField;
    private JTextField searchField = new JTextField(15);

    private JButton btnThemButton;
    private JButton btnsuaButton;
    private JButton btnXoaButton;
    private JButton btnExcel;
    private JButton btnNhomHang;
    private JButton searchButton = new JButton("Tìm kiếm theo tên");
    private HangHoaDao hanghoaDAO;
    private HangHoa hanghoa;

    public form() {
        initComponents();
        fetchDataFromDatabase();

    }

    private void initComponents() {
        DecimalFormat decimalFormat = new DecimalFormat("#,##0");
        // Thêm panel chính vào JFrame
        JPanel panelMain = new JPanel();
        JPanel panel1 = new JPanel();
        JPanel panel2 = new JPanel();
        Border border = BorderFactory.createLineBorder(Color.BLACK); // Ví dụ: đường viền đen

// Đặt đường viền cho JPanel
        panel2.setBorder(border);
        JPanel panel3 = new JPanel();
        JPanel panel4 = new JPanel();

        SpringLayout layoutMain = new SpringLayout();
        panelMain.setLayout(layoutMain);
        panelMain.add(panel1);
        panelMain.add(panel2);
        panel2.setPreferredSize(new Dimension(0, 100));
        panelMain.add(panel3);
        panel3.setPreferredSize(new Dimension(0, 300));
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
        lbId = new JLabel("THÔNG TIN HÀNG HOÁ");
        lbId.setFont(new Font("Arial", Font.BOLD, 24));
        panel1.add(lbId, gbc);

        //panel2
        SpringLayout layout2 = new SpringLayout();
        panel2.setLayout(layout2);

        // Thiết lập các JLabel
        lbId = new JLabel("ID: ");
        lbIdNhomhang = new JLabel("ID nhóm hàng");
        lbTen = new JLabel("Tên: ");
        lbDVT = new JLabel("DVT: ");
        lbDongia = new JLabel("Đơn giá:");
        //Các text field
        IdField = new JTextField(15);
        IdField.setEnabled(false);
        cbbNhomhang = new JComboBox();
        cbbNhomhang.setPreferredSize(new Dimension(100, 20));
        hanghoaDAO = new HangHoaDao();
        for (String item : hanghoaDAO.layDanhSachTenNhomHang()) {
            cbbNhomhang.addItem(item);
        }
        TenField = new JTextField(15);
        DVTField = new JTextField(15);
        DongiaField = new JTextField(15);

        //nút bảng ID Nhóm Hàng
        btnNhomHang = new JButton("Bảng ID nhóm hàng");
        btnNhomHang.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Hiển thị form AddSupplier khi nút "Thêm" được nhấn
                new nhomhang();
            }
        });

        lbId.setFont(new Font("Arial", Font.BOLD, 14));
        lbIdNhomhang.setFont(new Font("Arial", Font.BOLD, 14));
        lbTen.setFont(new Font("Arial", Font.BOLD, 14));
        lbDVT.setFont(new Font("Arial", Font.BOLD, 14));
        lbDongia.setFont(new Font("Arial", Font.BOLD, 14));

        // Thêm các JLabel vào panel
        panel2.add(lbId);
        panel2.add(IdField);
        panel2.add(lbIdNhomhang);
        panel2.add(cbbNhomhang);
        panel2.add(lbTen);
        panel2.add(TenField);
        panel2.add(lbDVT);
        panel2.add(DVTField);
        panel2.add(lbDongia);
        panel2.add(DongiaField);
        panel2.add(btnNhomHang);

        // Thiết lập các ràng buộc
        layout2.putConstraint(SpringLayout.WEST, lbId, 50, SpringLayout.WEST, panel2);
        layout2.putConstraint(SpringLayout.NORTH, lbId, 10, SpringLayout.NORTH, panel2);

        layout2.putConstraint(SpringLayout.WEST, IdField, 120, SpringLayout.WEST, panel2);
        layout2.putConstraint(SpringLayout.NORTH, IdField, 10, SpringLayout.NORTH, panel2);

        layout2.putConstraint(SpringLayout.WEST, lbIdNhomhang, 500, SpringLayout.WEST, panel2);
        layout2.putConstraint(SpringLayout.NORTH, lbIdNhomhang, 10, SpringLayout.NORTH, panel2);

        layout2.putConstraint(SpringLayout.WEST, cbbNhomhang, 620, SpringLayout.WEST, panel2);
        layout2.putConstraint(SpringLayout.NORTH, cbbNhomhang, 10, SpringLayout.NORTH, panel2);

        layout2.putConstraint(SpringLayout.WEST, lbTen, 50, SpringLayout.WEST, panel2);
        layout2.putConstraint(SpringLayout.NORTH, lbTen, 40, SpringLayout.NORTH, panel2);

        layout2.putConstraint(SpringLayout.WEST, TenField, 120, SpringLayout.WEST, panel2);
        layout2.putConstraint(SpringLayout.NORTH, TenField, 40, SpringLayout.NORTH, panel2);

        layout2.putConstraint(SpringLayout.WEST, lbDVT, 500, SpringLayout.WEST, panel2);
        layout2.putConstraint(SpringLayout.NORTH, lbDVT, 40, SpringLayout.NORTH, panel2);

        layout2.putConstraint(SpringLayout.WEST, DVTField, 620, SpringLayout.WEST, panel2);
        layout2.putConstraint(SpringLayout.NORTH, DVTField, 40, SpringLayout.NORTH, panel2);

        layout2.putConstraint(SpringLayout.WEST, lbDongia, 50, SpringLayout.WEST, panel2);
        layout2.putConstraint(SpringLayout.NORTH, lbDongia, 70, SpringLayout.NORTH, panel2);

        layout2.putConstraint(SpringLayout.WEST, DongiaField, 120, SpringLayout.WEST, panel2);
        layout2.putConstraint(SpringLayout.NORTH, DongiaField, 70, SpringLayout.NORTH, panel2);

        layout2.putConstraint(SpringLayout.WEST, btnNhomHang, 620, SpringLayout.WEST, panel2);
        layout2.putConstraint(SpringLayout.NORTH, btnNhomHang, 70, SpringLayout.NORTH, panel2);

        //panel3
        JPanel pnTable = new JPanel();
        pnTable.setLayout(new BorderLayout());
        model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("ID nhóm hàng ");
        model.addColumn("Tên");
        model.addColumn("DVT");
        model.addColumn("Đơn giá");

        table = new JTable(model);

        table.setDefaultEditor(Object.class, null);

        // Thêm ListSelectionListener để xử lý sự kiện khi người dùng chọn một dòng trong bảng
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) { // Nếu có dòng được chọn
                    // Lấy dữ liệu từ dòng được chọn và hiển thị lên panel 2
                    IdField.setText(model.getValueAt(selectedRow, 0).toString());
                    cbbNhomhang.setSelectedItem(model.getValueAt(selectedRow, 1).toString());
                    TenField.setText(model.getValueAt(selectedRow, 2).toString());
                    DVTField.setText(model.getValueAt(selectedRow, 3).toString());
                    String dongiaStr = model.getValueAt(selectedRow, 4).toString();
                    dongiaStr = dongiaStr.replace(",", "");
                    DongiaField.setText(dongiaStr);
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);
        pnTable.add(scrollPane, BorderLayout.CENTER); // Đặt scrollPane vào vị trí CENTER của pnTable
        panel3.setLayout(new BorderLayout());
        panel3.add(pnTable, BorderLayout.CENTER);

        //panel4
        SpringLayout layout4 = new SpringLayout();
        panel4.setLayout(layout4);
        //panel4.setLayout(new FlowLayout(FlowLayout.RIGHT));

        // Tạo JLabel và JButton   
        //nút Thêm
        btnThemButton = new JButton("Thêm ");
        btnThemButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String Tenhanghoa = TenField.getText();
                String nhomHangSelected = (String) cbbNhomhang.getSelectedItem();
                int id_nhomhang = hanghoaDAO.SelectidNhomHang(nhomHangSelected);
                String Donvitinh = DVTField.getText();
                float dongia = Float.parseFloat(DongiaField.getText());
                hanghoa = new HangHoa();
                hanghoa.setId_NhomHang(id_nhomhang);
                hanghoa.setTenHangHoa(Tenhanghoa); // Gán giá trị của TenNCC cho trường TenHangHoa
                hanghoa.setDVT(Donvitinh); // Gán giá trị của DiaChi cho trường Dvt
                hanghoa.setDonGia(dongia);
                hanghoaDAO = new HangHoaDao();
                hanghoaDAO.ThemHangHoa(hanghoa);
                fetchDataFromDatabase();
            }
        });

        //nút Sửa thông tin
        btnsuaButton = new JButton("Chỉnh sửa ");
        btnsuaButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int id = Integer.parseInt(IdField.getText());
                String Tenhanghoa = TenField.getText();
                String nhomHangSelected = (String) cbbNhomhang.getSelectedItem();
                int id_nhomhang = hanghoaDAO.SelectidNhomHang(nhomHangSelected);
                String Donvitinh = DVTField.getText();
                Float dongia = Float.parseFloat(DongiaField.getText());
                hanghoa = new HangHoa();
                hanghoa.setId(id);
                hanghoa.setId_NhomHang(id_nhomhang);
                hanghoa.setTenHangHoa(Tenhanghoa); // Gán giá trị của TenNCC cho trường TenHangHoa
                hanghoa.setDVT(Donvitinh); // Gán giá trị của DiaChi cho trường Dvt
                hanghoa.setDonGia(dongia);
                hanghoaDAO = new HangHoaDao();
                hanghoaDAO.suaHangHoa(hanghoa);
                fetchDataFromDatabase();
            }
        });
        //nút Xoá
        btnXoaButton = new JButton("Xoá mặt hàng");
        btnXoaButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int confirmed = JOptionPane.showConfirmDialog(null,
                        "Bạn có chắc chắn muốn xoá?", "Xác nhận xoá",
                        JOptionPane.YES_NO_OPTION);

                if (confirmed == JOptionPane.YES_OPTION) {
                    nutXoathongtin(); // Gọi phương thức xóa nếu người dùng chọn YES
                }
            }
        });

        //nút Cập nhật bảng thông tin
        btnExcel = new JButton("Xuát Excel");
        btnExcel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                exportToExcel();
            }
        });
        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String tenHang = searchField.getText(); // Lấy tên hàng hóa từ text field
                timKiemTheoTen(tenHang); // Gọi phương thức tìm kiếm
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
        layout4.putConstraint(SpringLayout.WEST, btnThemButton, 780, SpringLayout.WEST, panel4);
        layout4.putConstraint(SpringLayout.NORTH, btnThemButton, 5, SpringLayout.NORTH, panel4);
        panel4.add(btnsuaButton);
        layout4.putConstraint(SpringLayout.WEST, btnsuaButton, 851, SpringLayout.WEST, panel4);
        layout4.putConstraint(SpringLayout.NORTH, btnsuaButton, 5, SpringLayout.NORTH, panel4);
        panel4.add(btnExcel);
        layout4.putConstraint(SpringLayout.WEST, btnExcel, 949, SpringLayout.WEST, panel4);
        layout4.putConstraint(SpringLayout.NORTH, btnExcel, 5, SpringLayout.NORTH, panel4);
        panel4.add(btnXoaButton);
        layout4.putConstraint(SpringLayout.WEST, btnXoaButton, 1035, SpringLayout.WEST, panel4);
        layout4.putConstraint(SpringLayout.NORTH, btnXoaButton, 5, SpringLayout.NORTH, panel4);
        table.setPreferredSize(new Dimension(1190, 400));
        panelMain.setPreferredSize(new Dimension(1190, 550));
        add(panelMain);
//        setSize(1190, 500);
    }

    private void fetchDataFromDatabase() {
        String url = "jdbc:mysql://localhost:3306/test_supermarket";
        String dbClass = "com.mysql.cj.jdbc.Driver";
        String password = "";

        try (Connection conn = DriverManager.getConnection(url, "root", password); Statement statement = conn.createStatement()) {
            DecimalFormat decimalFormat = new DecimalFormat("#,##0");
            String query = "SELECT * FROM hanghoa"; // Truy vấn dữ liệu từ bảng 'hanghoa'
            try (ResultSet resultSet = statement.executeQuery(query)) {
                // Xóa dữ liệu cũ trong bảng
                model.setRowCount(0);

                while (resultSet.next()) {
                    String id = resultSet.getString("Id");
                    String ten = resultSet.getString("TenHangHoa");
                    String DVT = resultSet.getString("DVT");
                    String dongia = decimalFormat.format(resultSet.getInt("DonGia"));
                    String idnhomhang = hanghoaDAO.SelectTenNhomHang(Integer.parseInt(resultSet.getString("Id_NhomHang")));

                    model.addRow(new Object[]{id, idnhomhang, ten, DVT, dongia});
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void nutXoathongtin() {
        String url = "jdbc:mysql://localhost:3306/test_supermarket";
        String dbClass = "com.mysql.cj.jdbc.Driver";
        String password = "";
        String selectedID = IdField.getText(); // Lấy ID được chọn từ JTextField

        // Câu truy vấn SQL để xóa thông tin nhà cung cấp dựa trên ID đã chọn
        String query = "DELETE FROM hanghoa WHERE ID = ?";

        try (Connection conn = DriverManager.getConnection(url, "root", password); PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, selectedID); // Thiết lập tham số cho câu truy vấn

            int rowsAffected = pstmt.executeUpdate(); // Thực thi câu truy vấn xóa và lấy số dòng bị ảnh hưởng

            if (rowsAffected > 0) {
                // Nếu có dòng bị ảnh hưởng (số dòng bị xóa > 0), thông báo xóa thành công
                System.out.println("Đã xóa thông tin hàng hoá với ID: " + selectedID);

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
        String url = "jdbc:mysql://localhost:3306/supermarket";
        String user = "root";
        String password = "";

        try (Connection conn = DriverManager.getConnection(url, user, password); Statement statement = conn.createStatement()) {

            String query = "SELECT * FROM hanghoa"; // Truy vấn dữ liệu từ bảng 'nhacungcap'
            try (ResultSet resultSet = statement.executeQuery(query)) {
                // Xóa dữ liệu cũ trong bảng
                model.setRowCount(0);

                while (resultSet.next()) {
                    String id = resultSet.getString("Id");
                    String ten = resultSet.getString("TenHangHoa");
                    String DVT = resultSet.getString("DVT");
                    String dongia = resultSet.getString("DonGia");
                    String idnhomhang = resultSet.getString("Id_NhomHang");

                    model.addRow(new Object[]{id, idnhomhang, ten, DVT, dongia});
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void timKiemTheoTen(String tenHang) {
        String url = "jdbc:mysql://localhost:3306/test_supermarket";
        String dbClass = "com.mysql.cj.jdbc.Driver";
        String user = "root";
        String password = "";

        try (Connection conn = DriverManager.getConnection(url, user, password); PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM hanghoa WHERE TenHangHoa LIKE ?")) {

            pstmt.setString(1, "%" + tenHang + "%"); // Thiết lập tham số cho câu truy vấn, sử dụng LIKE để tìm kiếm một phần của tên

            try (ResultSet resultSet = pstmt.executeQuery()) {
                // Xóa dữ liệu cũ trong bảng
                model.setRowCount(0);

                while (resultSet.next()) {
                    String id = resultSet.getString("Id");
                    String idnhomhang = resultSet.getString("Id_NhomHang");
                    String ten = resultSet.getString("TenHangHoa");
                    String DVT = resultSet.getString("DVT");
                    String dongia = resultSet.getString("DonGia");

                    model.addRow(new Object[]{id, idnhomhang, ten, DVT, dongia});
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

        // Ghi chữ "Phiếu nhập" bên trên các nội dung
        org.apache.poi.ss.usermodel.Row titleRow = sheet.createRow(0);
        Cell titleCell = titleRow.createCell(0);
        titleCell.setCellValue("Phiếu nhập");

        // Ghi dữ liệu từ bảng vào file Excel
        int rowCount = table.getRowCount();
        int columnCount = table.getColumnCount();

        // Tạo hàng tiêu đề cho các cột
        org.apache.poi.ss.usermodel.Row headerRow = sheet.createRow(1); // Bắt đầu từ hàng 1
        for (int i = 0; i < columnCount; i++) {
            String columnName = table.getColumnName(i);
            headerRow.createCell(i).setCellValue(columnName);
        }

        for (int i = 0; i < rowCount; i++) {
            org.apache.poi.ss.usermodel.Row row = sheet.createRow(i + 2); // Bắt đầu từ hàng 2
            for (int j = 0; j < columnCount; j++) {
                Object value = table.getValueAt(i, j);
                Cell cell = row.createCell(j);
                if (value != null) {
                    cell.setCellValue(value.toString());
                }
            }
        }

        String filePath = "D:\\on_java\\Supermarket\\excel\\hanghoa.xlsx";
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
        new form();
    }
}
