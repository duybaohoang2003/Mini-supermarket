/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package supermarket.View;

import DAO.KhachHangDAO;
import Model.KhachHang;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author Bao Hoang
 */
public class KhachHangFRM extends JPanel implements ActionListener {

    private JTextField txtName, txtDiaChi, txtSDT, txtId, txtTimKiem;
    private JComboBox<String> cbGioiTinh;
    private JLabel lbName, lbGioiTinh, lbDiaChi, lbSDT, lbTitle, lbId, lbTimKiem;
    private JButton btnAdd, btnEdit, btnDelete, btnClear, btnTimKiem, btnExcel;

    private JTable khachHangTable;
    private JScrollPane jscrollPaneKhachHang;
    private DefaultTableModel KhachHangTableModel;
    //chứa các tên cột trong một bảng dữ liệu
    private final String[] column = new String[]{
        "Id", "Tên Khách Hàng", "Giới Tính", "Địa Chỉ", "Số Điện Thoại"
    };
    private final Object data = new Object[][]{};

    public KhachHangFRM() {

        ImageIcon icon = new ImageIcon("D:\\on_java\\Supermarket\\icon\\id.png");
        ImageIcon iconName = new ImageIcon("D:\\on_java\\Supermarket\\icon\\name.png");
        ImageIcon iconGender = new ImageIcon("D:\\on_java\\Supermarket\\icon\\gender.png");
        ImageIcon iconAddress = new ImageIcon("D:\\on_java\\Supermarket\\icon\\address.png");
        ImageIcon iconPhone = new ImageIcon("D:\\on_java\\Supermarket\\icon\\phone1.png");
        ImageIcon iconAdd = new ImageIcon("D:\\on_java\\Supermarket\\icon\\btnAdd.png");
        ImageIcon iconEdit = new ImageIcon("D:\\on_java\\Supermarket\\icon\\btnEdit.png");
        ImageIcon iconDelete = new ImageIcon("D:\\on_java\\Supermarket\\icon\\btnDelete.png");
        ImageIcon iconClear = new ImageIcon("D:\\on_java\\Supermarket\\icon\\btnClear.png");
        ImageIcon iconSearch = new ImageIcon("D:\\on_java\\Supermarket\\icon\\btnSearch.png");
        ImageIcon iconExcel = new ImageIcon("D:\\on_java\\Supermarket\\icon\\btnExcel.png");

        txtId = new JTextField(5);
        txtId.setEditable(false);
        txtName = new JTextField(20);
        txtDiaChi = new JTextField(20);
        txtSDT = new JTextField(15);
        txtTimKiem = new JTextField(20);

        lbTitle = new JLabel("QUẢN LÝ KHÁCH HÀNG");
        Font font = new Font("Arial", Font.BOLD | Font.ITALIC, 28);
        lbTitle.setFont(font);

        lbId = new JLabel("Id", icon, SwingConstants.LEFT);
        lbName = new JLabel("Tên khách hàng", iconName, SwingConstants.LEFT);
        lbGioiTinh = new JLabel("Giới Tính", iconGender, SwingConstants.LEFT);
        lbDiaChi = new JLabel("Địa Chỉ", iconAddress, SwingConstants.LEFT);
        lbSDT = new JLabel("Số điện thoại", iconPhone, SwingConstants.LEFT);
        lbTimKiem = new JLabel("Tìm Kiếm");

        cbGioiTinh = new JComboBox<>();
        cbGioiTinh.addItem("Nam");
        cbGioiTinh.addItem("Nữ");
        cbGioiTinh.setPreferredSize(new Dimension(100, 20));

        btnAdd = new JButton(iconAdd);
        btnAdd.setPreferredSize(new Dimension(70, 30));
        btnEdit = new JButton(iconEdit);
        btnEdit.setPreferredSize(new Dimension(70, 30));
        btnDelete = new JButton(iconDelete);
        btnDelete.setPreferredSize(new Dimension(70, 30));
        btnClear = new JButton(iconClear);
        btnClear.setPreferredSize(new Dimension(70, 30));
        btnTimKiem = new JButton(iconSearch);
        btnTimKiem.setPreferredSize(new Dimension(70, 30));
        btnExcel = new JButton(iconExcel);
        btnExcel.setPreferredSize(new Dimension(70, 30));

        khachHangTable = new JTable();
        jscrollPaneKhachHang = new JScrollPane();
        KhachHangTableModel = new DefaultTableModel((Object[][]) data, column);
        khachHangTable.setModel(KhachHangTableModel);
        jscrollPaneKhachHang.setViewportView(khachHangTable);
        jscrollPaneKhachHang.setPreferredSize(new Dimension(1100, 300));
        SpringLayout layout = new SpringLayout();
        setLayout(layout);
        InputView(layout);
        TableView(layout);
        setVisible(true);
        displayCustomer();
    }

    private void InputView(SpringLayout layout) {
        // khởi tạo springlayout mới tên là layoutInput
        SpringLayout layoutInput = new SpringLayout();
        // tạo JPanel mưới tên InputPanel
        JPanel InputPanel = new JPanel();
        InputPanel.setLayout(layoutInput);
        InputPanel.setBackground(Color.pink);
        InputPanel.setPreferredSize(new Dimension(1120, 280));
        this.add(InputPanel);
        InputPanel.setBorder(BorderFactory.createTitledBorder("Nhập thông tin"));

        layout.putConstraint(SpringLayout.NORTH, InputPanel, 10, SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.WEST, InputPanel, 20, SpringLayout.WEST, this);

        InputPanel.add(lbTitle);
        InputPanel.add(lbId);
        InputPanel.add(lbName);
        InputPanel.add(lbGioiTinh);
        InputPanel.add(lbDiaChi);
        InputPanel.add(lbSDT);
        InputPanel.add(lbTimKiem);

        InputPanel.add(txtId);
        InputPanel.add(txtName);
        InputPanel.add(txtDiaChi);
        InputPanel.add(txtSDT);
        InputPanel.add(txtTimKiem);

        
        InputPanel.add(cbGioiTinh);

        InputPanel.add(btnAdd);
        InputPanel.add(btnEdit);
        InputPanel.add(btnDelete);
        InputPanel.add(btnClear);
        InputPanel.add(btnTimKiem);
        InputPanel.add(btnExcel);

        layoutInput.putConstraint(SpringLayout.WEST, lbTitle, 350, SpringLayout.WEST, InputPanel);
        layoutInput.putConstraint(SpringLayout.NORTH, lbTitle, 5, SpringLayout.NORTH, InputPanel);

        layoutInput.putConstraint(SpringLayout.WEST, lbId, 10, SpringLayout.WEST, InputPanel);
        layoutInput.putConstraint(SpringLayout.NORTH, lbId, 50, SpringLayout.NORTH, InputPanel);

        layoutInput.putConstraint(SpringLayout.WEST, txtId, 120, SpringLayout.WEST, InputPanel);
        layoutInput.putConstraint(SpringLayout.NORTH, txtId, 50, SpringLayout.NORTH, InputPanel);

        layoutInput.putConstraint(SpringLayout.WEST, lbName, 10, SpringLayout.WEST, InputPanel);
        layoutInput.putConstraint(SpringLayout.NORTH, lbName, 100, SpringLayout.NORTH, InputPanel);

        layoutInput.putConstraint(SpringLayout.WEST, txtName, 120, SpringLayout.WEST, InputPanel);
        layoutInput.putConstraint(SpringLayout.NORTH, txtName, 100, SpringLayout.NORTH, InputPanel);

        layoutInput.putConstraint(SpringLayout.WEST, lbGioiTinh, 400, SpringLayout.WEST, InputPanel);
        layoutInput.putConstraint(SpringLayout.NORTH, lbGioiTinh, 50, SpringLayout.NORTH, InputPanel);

        layoutInput.putConstraint(SpringLayout.WEST, cbGioiTinh, 500, SpringLayout.WEST, InputPanel);
        layoutInput.putConstraint(SpringLayout.NORTH, cbGioiTinh, 50, SpringLayout.NORTH, InputPanel);

        layoutInput.putConstraint(SpringLayout.WEST, lbDiaChi, 10, SpringLayout.WEST, InputPanel);
        layoutInput.putConstraint(SpringLayout.NORTH, lbDiaChi, 150, SpringLayout.NORTH, InputPanel);

        layoutInput.putConstraint(SpringLayout.WEST, txtDiaChi, 120, SpringLayout.WEST, InputPanel);
        layoutInput.putConstraint(SpringLayout.NORTH, txtDiaChi, 150, SpringLayout.NORTH, InputPanel);

        layoutInput.putConstraint(SpringLayout.WEST, lbSDT, 400, SpringLayout.WEST, InputPanel);
        layoutInput.putConstraint(SpringLayout.NORTH, lbSDT, 100, SpringLayout.NORTH, InputPanel);

        layoutInput.putConstraint(SpringLayout.WEST, txtSDT, 500, SpringLayout.WEST, InputPanel);
        layoutInput.putConstraint(SpringLayout.NORTH, txtSDT, 100, SpringLayout.NORTH, InputPanel);

        layoutInput.putConstraint(SpringLayout.WEST, btnAdd, 800, SpringLayout.WEST, InputPanel);
        layoutInput.putConstraint(SpringLayout.NORTH, btnAdd, 30, SpringLayout.NORTH, InputPanel);

        layoutInput.putConstraint(SpringLayout.WEST, btnEdit, 800, SpringLayout.WEST, InputPanel);
        layoutInput.putConstraint(SpringLayout.NORTH, btnEdit, 70, SpringLayout.NORTH, InputPanel);

        layoutInput.putConstraint(SpringLayout.WEST, btnDelete, 800, SpringLayout.WEST, InputPanel);
        layoutInput.putConstraint(SpringLayout.NORTH, btnDelete, 110, SpringLayout.NORTH, InputPanel);

        layoutInput.putConstraint(SpringLayout.WEST, btnClear, 800, SpringLayout.WEST, InputPanel);
        layoutInput.putConstraint(SpringLayout.NORTH, btnClear, 150, SpringLayout.NORTH, InputPanel);

        layoutInput.putConstraint(SpringLayout.WEST, btnExcel, 800, SpringLayout.WEST, InputPanel);
        layoutInput.putConstraint(SpringLayout.NORTH, btnExcel, 190, SpringLayout.NORTH, InputPanel);

        layoutInput.putConstraint(SpringLayout.WEST, lbTimKiem, 300, SpringLayout.WEST, InputPanel);
        layoutInput.putConstraint(SpringLayout.NORTH, lbTimKiem, 200, SpringLayout.NORTH, InputPanel);

        layoutInput.putConstraint(SpringLayout.WEST, txtTimKiem, 400, SpringLayout.WEST, InputPanel);
        layoutInput.putConstraint(SpringLayout.NORTH, txtTimKiem, 200, SpringLayout.NORTH, InputPanel);

        layoutInput.putConstraint(SpringLayout.WEST, btnTimKiem, 650, SpringLayout.WEST, InputPanel);
        layoutInput.putConstraint(SpringLayout.NORTH, btnTimKiem, 200, SpringLayout.NORTH, InputPanel);

    }

    private void TableView(SpringLayout layout) {
        SpringLayout layoutTable = new SpringLayout();
        JPanel TablePanel = new JPanel();
        TablePanel.setLayout(layoutTable);
        TablePanel.setBackground(Color.lightGray);
        TablePanel.setPreferredSize(new Dimension(1120, 350));
        this.add(TablePanel);
        TablePanel.setBorder(BorderFactory.createTitledBorder("Thông tin khach hàng"));

        TablePanel.add(jscrollPaneKhachHang);

        layout.putConstraint(SpringLayout.NORTH, TablePanel, 300, SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.WEST, TablePanel, 20, SpringLayout.WEST, this);

        layout.putConstraint(SpringLayout.NORTH, jscrollPaneKhachHang, 5, SpringLayout.NORTH, TablePanel);
        layout.putConstraint(SpringLayout.WEST, jscrollPaneKhachHang, 0, SpringLayout.WEST, TablePanel);

        btnAdd.addActionListener(this);
        btnEdit.addActionListener(this);
        btnDelete.addActionListener(this);
        btnClear.addActionListener(this);
        btnTimKiem.addActionListener(this);
        btnExcel.addActionListener(this);
        
        //ListSelectionModel này chịu trách nhiệm cho việc quản lý việc chọn các hàng trong bảng.
        ListSelectionModel selectionModel = khachHangTable.getSelectionModel();
        selectionModel.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int selectedRow = khachHangTable.getSelectedRow();
                    if (selectedRow != -1) {
                        // Lấy dữ liệu từ hàng được chọn
                        String Id = khachHangTable.getValueAt(selectedRow, 0).toString();
                        String TenKhachHang = khachHangTable.getValueAt(selectedRow, 1).toString();
                        String GioiTinh = khachHangTable.getValueAt(selectedRow, 2).toString();
                        String DiaChi = khachHangTable.getValueAt(selectedRow, 3).toString();
                        String SoDienThoai = khachHangTable.getValueAt(selectedRow, 4).toString();

                        // Hiển thị dữ liệu lên các ô textfield và combobox
                        txtId.setText(Id);
                        txtName.setText(TenKhachHang);
                        cbGioiTinh.setSelectedItem(GioiTinh);
                        txtDiaChi.setText(DiaChi);
                        txtSDT.setText(SoDienThoai);
                    }
                }
            }
        });

    }

    // hiển thị dữ liệu ra bảng
    public void displayCustomer() {
        DefaultTableModel tableModel = (DefaultTableModel) khachHangTable.getModel();
        tableModel.setRowCount(0); // Xóa tất cả các dòng hiện có trong bảng

        KhachHangDAO svD = new KhachHangDAO();
        ResultSet rs = svD.getAllKH();
        try {
            while (rs.next()) {
                int Id = rs.getInt("Id");
                String TenKhachHang = rs.getString("TenKhachHang");
                int GioiTinhInt = rs.getInt("GioiTinh");
                String GioiTinh = (GioiTinhInt == 1) ? "Nam" : "Nữ";
                String DiaChi = rs.getString("DiaChi");
                String SoDienThoai = rs.getString("SoDienThoai");

                Object[] rowData = {Id, TenKhachHang, GioiTinh, DiaChi, SoDienThoai};
                tableModel.addRow(rowData); // Thêm dòng mới vào bảng
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // nút Thêm
    public void btnAdd_actionperformed() {
        // Kiểm tra xem các JTextField có được nhập đầy đủ thông tin hay không
        if (txtName.getText().isEmpty() || txtDiaChi.getText().isEmpty() || txtSDT.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin khách hàng.");
            return; // Dừng việc thêm nếu thiếu thông tin
        }

        String sdt = txtSDT.getText();
        if (!sdt.matches("\\d{1,10}")) {
            JOptionPane.showMessageDialog(this, "Số điện thoại chỉ được nhập số và không quá 10 số.");
            txtSDT.setText("");
            return;
        }
        KhachHangDAO svD = new KhachHangDAO();

        // Kiểm tra xem số điện thoại đã tồn tại trong cơ sở dữ liệu hay chưa
        if (svD.isSDTExists(sdt)) {
            JOptionPane.showMessageDialog(this, "Số điện thoại đã tồn tại trong cơ sở dữ liệu.");
            return; // Dừng việc thêm nếu số điện thoại đã tồn tại
        }

        KhachHang sv = new KhachHang();
        sv.setName(txtName.getText());
        String gioitinh= cbGioiTinh.getSelectedItem().toString();
        sv.setDiaChi(txtDiaChi.getText());
        sv.setDienThoai(txtSDT.getText());
        int gt;
        if (gioitinh == "Nam") {
            sv.setGioiTinh(1);
        } else {
            sv.setGioiTinh(0);
        }
        int rs = svD.ThemKH(sv);
        if (rs != 0) {
            JOptionPane.showMessageDialog(this, "Thêm thành công");
            btnClear_actionperformed();
            displayCustomer();
        } else {
            JOptionPane.showMessageDialog(this, "Thêm không thành công");
        }
    }

    
    // nút sửa
    public void btnEdit_actionperformed() {
        KhachHang sv = new KhachHang();
        String Id = txtId.getText();
        sv.setId(Integer.parseInt(txtId.getText()));
        sv.setName(txtName.getText());
        String gioitinh= cbGioiTinh.getSelectedItem().toString(); // Lấy giá trị được chọn từ combobox
        sv.setDiaChi(txtDiaChi.getText());
        sv.setDienThoai(txtSDT.getText());
        int gt;
        if (gioitinh == "Nam") {
            sv.setGioiTinh(1);
        } else {
            sv.setGioiTinh(0);
        }
        KhachHangDAO svD = new KhachHangDAO();
        int rs = svD.SuaKH(Id, sv);
        if (rs != 0) {
            JOptionPane.showMessageDialog(this, "Sửa thành công");
            btnClear_actionperformed();
            displayCustomer();
        } else {
            JOptionPane.showMessageDialog(this, "Sửa không thành công");
        }
    }

    
    //nút xóa
    public void btnDelete_actionperformed() {
        // Lấy ID của khách hàng cần xóa từ ô văn bản
        String Id = txtId.getText();

        // Tạo một thông điệp xác nhận
        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);

        // Kiểm tra xem người dùng đã xác nhận xóa hay không
        if (confirm == JOptionPane.YES_OPTION) {
            // Nếu người dùng đồng ý xóa, thực hiện xóa khách hàng
            KhachHangDAO svD = new KhachHangDAO();
            int rs = svD.XoaKH(Id);

            // Kiểm tra kết quả của việc xóa
            if (rs != 0) {
                // Nếu xóa thành công, cập nhật giao diện và hiển thị thông báo
                JOptionPane.showMessageDialog(this, "Xóa thành công");
                txtId.setText("");
                txtName.setText("");
                cbGioiTinh.setSelectedIndex(0);
                txtDiaChi.setText("");
                txtSDT.setText("");
                displayCustomer();
            } else {
                // Nếu xóa không thành công, hiển thị thông báo tương ứng
                JOptionPane.showMessageDialog(this, "Xóa không thành công");
            }
        }
    }

    
    // nút làm mới
    public void btnClear_actionperformed() {
        txtId.setText("");
        txtName.setText("");
        cbGioiTinh.setSelectedIndex(0); // Đặt lại combobox về giá trị mặc định
        txtDiaChi.setText("");
        txtSDT.setText("");
    }

    // xuất excel
    private void btnExcel_actionperformed() {
        exportToExcel();
    }

    // tìm kiếm
    private void btnTimKiem_actionperformed() {
        String searchName = txtTimKiem.getText().trim();
        if (searchName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập tên khách hàng cần tìm kiếm");
            return;
        }

        DefaultTableModel tableModel = (DefaultTableModel) khachHangTable.getModel();
        KhachHangDAO khachHangDAO = new KhachHangDAO();
        ResultSet rs = khachHangDAO.searchByName(searchName);

        // Duyệt qua tất cả các dòng trong bảng để tìm dòng có tên khách hàng trùng với tên cần tìm
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            String tenKhachHang = (String) tableModel.getValueAt(i, 1);
            // Nếu tên khách hàng trùng, chọn dòng đó trong bảng
            if (tenKhachHang.equalsIgnoreCase(searchName)) {
                khachHangTable.setRowSelectionInterval(i, i);
                khachHangTable.scrollRectToVisible(khachHangTable.getCellRect(i, 0, true)); // Cuộn đến dòng đã chọn
                return; // Kết thúc phương thức sau khi đã chọn dòng
            }
        }

        // Nếu không tìm thấy tên khách hàng trong bảng, hiển thị thông báo
        JOptionPane.showMessageDialog(this, "Không tìm thấy khách hàng: " + searchName);
    }

    public void actionPerformed(ActionEvent e) {
        JButton btn = (JButton) e.getSource();
        if (btn.equals(btnAdd)) {
            btnAdd_actionperformed();
        } else if (btn.equals(btnEdit)) {
            btnEdit_actionperformed();
        } else if (btn.equals(btnDelete)) {
            btnDelete_actionperformed();
        } else if (btn.equals(btnTimKiem)) {
            btnTimKiem_actionperformed();
        } else if (btn.equals(btnExcel)) {
            btnExcel_actionperformed();
        } else {
            btnClear_actionperformed();
        }

    }

    // xuất excel
    private void exportToExcel() {
        // Tạo workbook mới
        Workbook workbook = new XSSFWorkbook();

        // Tạo một trang tính mới
        Sheet sheet = workbook.createSheet("Data");

        // Lấy số dòng và số cột của bảng
        int rowCount = khachHangTable.getRowCount();
        int columnCount = khachHangTable.getColumnCount();

        // Ghi tên cột vào hàng đầu tiên
        Row headerRow = sheet.createRow(0);
        for (int col = 0; col < columnCount; col++) {
            headerRow.createCell(col).setCellValue(khachHangTable.getColumnName(col));
        }

        // Ghi dữ liệu từ bảng vào file Excel
        for (int i = 0; i < rowCount; i++) {
            Row row = sheet.createRow(i + 1); // Bắt đầu từ hàng thứ hai sau header
            for (int j = 0; j < columnCount; j++) {
                Object value = khachHangTable.getValueAt(i, j);
                Cell cell = row.createCell(j);
                if (value != null) {
                    cell.setCellValue(value.toString());
                }
            }
        }

        // Đường dẫn tới file Excel đích
        String filePath = "D:\\on_java\\Supermarket\\excel\\table_KhachHang.xlsx";

        // Lưu workbook vào một file
        try (FileOutputStream outputStream = new FileOutputStream(filePath)) {
            workbook.write(outputStream);
            JOptionPane.showMessageDialog(null, "Xuất file thành công.");
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi, không thể xuất file: " + e.getMessage());
        }
    }
}
