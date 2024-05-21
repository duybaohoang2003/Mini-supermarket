/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package supermarket.View;

import DAO.QuanLyDonHangDAO;
import Model.KhachHang;
import com.toedter.calendar.JDateChooser;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeListenerProxy;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableColumn;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import supermarket.SetIcon;

/**
 *
 * @author ASUS
 */
public class QuanLyDonHang extends JPanel implements ActionListener {

    private JTable DonHangtTable;
    private JScrollPane jScrollPaneDonHangTable;
    private DefaultTableModel DonHangtTableModel;
    private final String[] columnName = new String[]{
        "Id", "Khách hàng", "Nhân viên", "Tổng tiền", "Thời gian"
    };
    private final Object data = new Object[][]{};
    //chi tiet san pham
    private JTable SanPhamTable;
    private JScrollPane jScrollPaneSanPhamTable;
    private DefaultTableModel SanPhamTableModel;
    private final String[] columnName1 = new String[]{
        "Tên hàng", "Số lượng", "Giá bán", "Thành tiền"
    };
    private final Object data1 = new Object[][]{};
    int SelectRow = -1;
    int id = 0;

    private JButton btnExcel, btnTim, btnAll, btnXoa;
    //chi tiet don hang
    private JLabel lbTong, lbDoanhThu;
    //header
    private JTextField txtTimHoaDon;
    JDateChooser TuNgay, DenNgay;
    JLabel lbTuNgay, lbDenNgay, lbTim;

    Date selectedDateTuNgay = null;
    Date selectedDateDenNgay = null;

    String formattedDateTuNgay = "";
    String formattedDateDenNgay = "";

    public QuanLyDonHang() {

        SpringLayout layout = new SpringLayout();
        setLayout(layout);
        header(layout);
        TableView(layout);
        Sanpham(layout);
        //FooterView(layout);
        HienThiThongTin();
        DoanhThu();

        ListSelectionModel SelectionModel = DonHangtTable.getSelectionModel();
        SelectionModel.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    btnXoa.setEnabled(true);
                    SanPhamTableModel = (DefaultTableModel) SanPhamTable.getModel();
                    SanPhamTableModel.setRowCount(0);
                    //lấy vị trí hàng được chọn
                    SelectRow = DonHangtTable.getSelectedRow();
                    if (SelectRow != -1) {
                        id = Integer.parseInt(DonHangtTable.getValueAt(SelectRow, 0).toString());
                    }
                    HienThiSanPham();
                    double kq = Tong();
                    if (kq != 0) {
                        lbTong.setText("Tổng tiền " + kq + " VND");
                    } else {
                        lbTong.setText("Tổng tiền 0 VND");
                    }

                }
            }
        });
        //BẮT SỰ KIỆN NGƯỜI DÙNG CHỌN NGÀY
        TuNgay.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                GetTuNgay(evt);
            }
        });

        DenNgay.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                GetDenNgay(evt);
            }
        });

        txtTimHoaDon.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                search();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                search();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                // Không cần xử lý trong trường hợp này
            }
        });

        btnTim.addActionListener(this);
        btnExcel.addActionListener(this);
        btnAll.addActionListener(this);
        btnXoa.addActionListener(this);
    }

    private void header(SpringLayout layout) {
        SpringLayout layoutHeader = new SpringLayout();
        JPanel HeaderPanel = new JPanel();
        HeaderPanel.setLayout(layoutHeader);
        Border border = BorderFactory.createLineBorder(Color.BLACK);
        HeaderPanel.setBorder(border);
        HeaderPanel.setPreferredSize(new Dimension(1120, 80));
        this.add(HeaderPanel);

        layout.putConstraint(SpringLayout.WEST, HeaderPanel, 20, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, HeaderPanel, 10, SpringLayout.NORTH, this);

        lbTuNgay = new JLabel("Từ ngày");
        lbDenNgay = new JLabel("Đến ngày");
        lbTim = new JLabel("Tìm hóa đơn");
        btnExcel = new JButton();
        String pathExcel = "D:\\on_java\\Supermarket\\icon\\excel.png";
        ImageIcon iconExcel = SetIcon.SetSize(pathExcel, 30, 30);
        btnExcel.setPreferredSize(new Dimension(50, 50));
        btnExcel.setIcon(iconExcel);
        txtTimHoaDon = new JTextField(20);
        TuNgay = new JDateChooser();
        TuNgay.setPreferredSize(new Dimension(200, 20));
        DenNgay = new JDateChooser();
        DenNgay.setPreferredSize(new Dimension(200, 20));
        btnTim = new JButton();
        String pathSearch = "D:\\on_java\\Supermarket\\icon\\search.png";
        ImageIcon iconSearch = SetIcon.SetSize(pathSearch, 30, 30);
        btnTim.setPreferredSize(new Dimension(50, 50));
        btnTim.setIcon(iconSearch);
        //all
        btnAll = new JButton();
        String pathAll = "D:\\on_java\\Supermarket\\icon\\all.png";
        ImageIcon iconAll = SetIcon.SetSize(pathAll, 30, 30);
        btnAll.setPreferredSize(new Dimension(50, 50));
        btnAll.setIcon(iconAll);
        //xoa
        btnXoa = new JButton();
        String pathXoa = "D:\\on_java\\Supermarket\\icon\\xoa.png";
        ImageIcon iconXoa = SetIcon.SetSize(pathXoa, 30, 30);
        btnXoa.setPreferredSize(new Dimension(50, 50));
        btnXoa.setIcon(iconXoa);
        btnXoa.setEnabled(false);
        //
        HeaderPanel.add(lbTuNgay);
        HeaderPanel.add(lbDenNgay);
        HeaderPanel.add(txtTimHoaDon);
        HeaderPanel.add(lbTim);
        HeaderPanel.add(TuNgay);
        HeaderPanel.add(DenNgay);
        HeaderPanel.add(btnTim);
        HeaderPanel.add(btnAll);
        HeaderPanel.add(btnXoa);
        HeaderPanel.add(btnExcel);

        layoutHeader.putConstraint(SpringLayout.WEST, lbTim, 10, SpringLayout.WEST, HeaderPanel);
        layoutHeader.putConstraint(SpringLayout.NORTH, lbTim, 10, SpringLayout.NORTH, HeaderPanel);

        layoutHeader.putConstraint(SpringLayout.WEST, txtTimHoaDon, 120, SpringLayout.WEST, HeaderPanel);
        layoutHeader.putConstraint(SpringLayout.NORTH, txtTimHoaDon, 10, SpringLayout.NORTH, HeaderPanel);

        layoutHeader.putConstraint(SpringLayout.WEST, lbTuNgay, 10, SpringLayout.WEST, HeaderPanel);
        layoutHeader.putConstraint(SpringLayout.NORTH, lbTuNgay, 40, SpringLayout.NORTH, HeaderPanel);

        layoutHeader.putConstraint(SpringLayout.WEST, TuNgay, 120, SpringLayout.WEST, HeaderPanel);
        layoutHeader.putConstraint(SpringLayout.NORTH, TuNgay, 40, SpringLayout.NORTH, HeaderPanel);

        layoutHeader.putConstraint(SpringLayout.WEST, lbDenNgay, 350, SpringLayout.WEST, HeaderPanel);
        layoutHeader.putConstraint(SpringLayout.NORTH, lbDenNgay, 40, SpringLayout.NORTH, HeaderPanel);

        layoutHeader.putConstraint(SpringLayout.WEST, DenNgay, 440, SpringLayout.WEST, HeaderPanel);
        layoutHeader.putConstraint(SpringLayout.NORTH, DenNgay, 40, SpringLayout.NORTH, HeaderPanel);

        layoutHeader.putConstraint(SpringLayout.WEST, btnTim, 660, SpringLayout.WEST, HeaderPanel);
        layoutHeader.putConstraint(SpringLayout.NORTH, btnTim, 15, SpringLayout.NORTH, HeaderPanel);

        layoutHeader.putConstraint(SpringLayout.WEST, btnAll, 10, SpringLayout.EAST, btnTim);
        layoutHeader.putConstraint(SpringLayout.NORTH, btnAll, 15, SpringLayout.NORTH, HeaderPanel);

        layoutHeader.putConstraint(SpringLayout.WEST, btnXoa, 10, SpringLayout.EAST, btnAll);
        layoutHeader.putConstraint(SpringLayout.NORTH, btnXoa, 15, SpringLayout.NORTH, HeaderPanel);

        layoutHeader.putConstraint(SpringLayout.WEST, btnExcel, 10, SpringLayout.EAST, btnXoa);
        layoutHeader.putConstraint(SpringLayout.NORTH, btnExcel, 15, SpringLayout.NORTH, HeaderPanel);
    }

    protected void TableView(SpringLayout layout) {
        JLabel lbDs = new JLabel("Danh sách đơn hàng");
        Font font1 = new Font("Arial", Font.BOLD | Font.ITALIC, 28);
        lbDoanhThu = new JLabel();
        lbDoanhThu.setFont(font1);
        Font font = new Font("Arial", Font.BOLD | Font.ITALIC, 16);
        lbDs.setFont(font);
        DonHangtTable = new JTable();
        jScrollPaneDonHangTable = new JScrollPane();
        DonHangtTableModel = new DefaultTableModel((Object[][]) data, columnName);
        DonHangtTable.setModel(DonHangtTableModel);
        jScrollPaneDonHangTable.setViewportView(DonHangtTable);
        jScrollPaneDonHangTable.setPreferredSize(new Dimension(650, 420));
        DonHangtTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        SpringLayout layoutTable = new SpringLayout();
        JPanel TablePanel = new JPanel();
        TablePanel.setLayout(layoutTable);
        TablePanel.setPreferredSize(new Dimension(700, 500));
        this.add(TablePanel);
        Border border = BorderFactory.createLineBorder(Color.BLACK);
        TablePanel.setBorder(border);
        TablePanel.add(jScrollPaneDonHangTable);
        TablePanel.add(lbDs);
        TablePanel.add(lbDoanhThu);

        layout.putConstraint(SpringLayout.WEST, TablePanel, 20, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, TablePanel, 100, SpringLayout.NORTH, this);

        layoutTable.putConstraint(SpringLayout.WEST, lbDs, 10, SpringLayout.WEST, TablePanel);
        layoutTable.putConstraint(SpringLayout.NORTH, lbDs, 10, SpringLayout.NORTH, TablePanel);

        layoutTable.putConstraint(SpringLayout.WEST, jScrollPaneDonHangTable, 10, SpringLayout.WEST, TablePanel);
        layoutTable.putConstraint(SpringLayout.NORTH, jScrollPaneDonHangTable, 30, SpringLayout.NORTH, TablePanel);

        layoutTable.putConstraint(SpringLayout.WEST, lbDoanhThu, 10, SpringLayout.WEST, TablePanel);
        layoutTable.putConstraint(SpringLayout.NORTH, lbDoanhThu, 450, SpringLayout.NORTH, TablePanel);
    }

    protected void Sanpham(SpringLayout layout) {
        JLabel lbCt = new JLabel("Chi tiết đơn hàng");
        lbTong = new JLabel();
        Font font1 = new Font("Arial", Font.BOLD | Font.ITALIC, 18);
        lbTong.setFont(font1);

        Font font = new Font("Arial", Font.BOLD | Font.ITALIC, 18);
        lbCt.setFont(font);
        SanPhamTable = new JTable();
        jScrollPaneSanPhamTable = new JScrollPane();
        SanPhamTableModel = new DefaultTableModel((Object[][]) data1, columnName1);
        SanPhamTable.setModel(SanPhamTableModel);
        jScrollPaneSanPhamTable.setViewportView(SanPhamTable);
        jScrollPaneSanPhamTable.setPreferredSize(new Dimension(390, 420));
        SanPhamTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        SpringLayout layoutSanPham = new SpringLayout();
        JPanel SanPhamPanel = new JPanel();
        SanPhamPanel.setLayout(layoutSanPham);
        SanPhamPanel.setPreferredSize(new Dimension(410, 500));
        this.add(SanPhamPanel);
        Border border = BorderFactory.createLineBorder(Color.BLACK);
        SanPhamPanel.setBorder(border);
        SanPhamPanel.add(jScrollPaneSanPhamTable);
        SanPhamPanel.add(lbCt);
        SanPhamPanel.add(lbTong);

        layout.putConstraint(SpringLayout.WEST, SanPhamPanel, 730, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, SanPhamPanel, 100, SpringLayout.NORTH, this);

        layoutSanPham.putConstraint(SpringLayout.WEST, lbCt, 10, SpringLayout.WEST, SanPhamPanel);
        layoutSanPham.putConstraint(SpringLayout.NORTH, lbCt, 10, SpringLayout.NORTH, SanPhamPanel);

        layoutSanPham.putConstraint(SpringLayout.WEST, jScrollPaneSanPhamTable, 10, SpringLayout.WEST, SanPhamPanel);
        layoutSanPham.putConstraint(SpringLayout.NORTH, jScrollPaneSanPhamTable, 30, SpringLayout.NORTH, SanPhamPanel);

        layoutSanPham.putConstraint(SpringLayout.WEST, lbTong, 200, SpringLayout.WEST, SanPhamPanel);
        layoutSanPham.putConstraint(SpringLayout.NORTH, lbTong, 460, SpringLayout.NORTH, SanPhamPanel);

    }

    public void HienThiThongTin() {
        QuanLyDonHangDAO ql = new QuanLyDonHangDAO();
        DefaultTableModel model = (DefaultTableModel) DonHangtTable.getModel();
        model.setRowCount(0);
        ResultSet rs = ql.GetDonHang();
        Timestamp Ngay = null;
        try {
            while (rs.next()) {
                int id = rs.getInt("ID");
                String TenKH = rs.getString("TENKHACHHANG");
                String TenNV = rs.getString("TENNV");
                float tong = rs.getFloat("TONGTIEN");
                Ngay = rs.getTimestamp("NGAYXUAT");
                model.addRow(new Object[]{id, TenKH, TenNV, tong, Ngay});
            }
        } catch (Exception e) {
        }

    }

    public void HienThiSanPham() {
        QuanLyDonHangDAO ql = new QuanLyDonHangDAO();
        DefaultTableModel model = (DefaultTableModel) SanPhamTable.getModel();
        ResultSet rs = ql.GetSanPham(id);
        try {
            while (rs.next()) {
                String Ten = rs.getString("TENHANGHOA");
                int soluong = rs.getInt("SOLUONG");
                float dongia = rs.getFloat("DONGIA");
                double thanhtien = (double) soluong * dongia;
                model.addRow(new Object[]{Ten, soluong, dongia, thanhtien});
            }
        } catch (Exception e) {
        }

    }

    public void DoanhThu() {
        double tong = 0;
        DefaultTableModel model = (DefaultTableModel) DonHangtTable.getModel();
        if (selectedDateTuNgay == null || selectedDateDenNgay == null) {
            int rowCount = model.getRowCount();
            for (int i = 0; i < rowCount; i++) {
                String thanhtien = DonHangtTable.getValueAt(i, 3).toString();
                double tt = Double.parseDouble(thanhtien);
                tong += tt;
            }
            lbDoanhThu.setText("Doanh thu " + tong + " VND");
        } else {
            int rowCount = model.getRowCount();
            for (int i = 0; i < rowCount; i++) {
                String thanhtien = DonHangtTable.getValueAt(i, 3).toString();
                double tt = Double.parseDouble(thanhtien);
                tong += tt;
            }
            lbDoanhThu.setText("Doanh thu từ " + formattedDateTuNgay + "-" + formattedDateDenNgay + ": " + tong + " VND");
        }

    }

    public double Tong() {
        double tong = 0;
        DefaultTableModel model = (DefaultTableModel) SanPhamTable.getModel();
        int rowCount = model.getRowCount();
        for (int i = 0; i < rowCount; i++) {
            String thanhtien = SanPhamTable.getValueAt(i, 3).toString();
            double tt = Double.parseDouble(thanhtien);
            tong += tt;
        }

        return tong;
    }

    //search
    public void search() {
        String name = txtTimHoaDon.getText().trim();
        QuanLyDonHangDAO ql = new QuanLyDonHangDAO();
        DefaultTableModel model = (DefaultTableModel) DonHangtTable.getModel();
        model.setRowCount(0);
        Timestamp Ngay = null;
        ResultSet rs = ql.TimKiem(name);
        try {
            while (rs.next()) {
                int id = rs.getInt("ID");
                String TenKH = rs.getString("TENKHACHHANG");
                String TenNV = rs.getString("TENNV");
                float tong = rs.getFloat("TONGTIEN");
                Ngay = rs.getTimestamp("NGAYXUAT");
                model.addRow(new Object[]{id, TenKH, TenNV, tong, Ngay});
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton btn = (JButton) e.getSource();
        if (btn.equals(btnTim)) {
            btnTim_actionPerformed();
        } else if (btn.equals(btnAll)) {
            btnAll_actionPerformed();
        } else if (btn.equals(btnXoa)) {
            btnXoa_actionPerformed();
        } else {
            btnExcel_actionPerformed();
        }
    }

    public void GetTuNgay(PropertyChangeEvent evt) {
        if ("date".equals(evt.getPropertyName())) {
            selectedDateTuNgay = TuNgay.getDate();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
            formattedDateTuNgay = sdf.format(selectedDateTuNgay);
            //System.out.println(formattedDate);
        }
    }

    public void GetDenNgay(PropertyChangeEvent evt) {
        if ("date".equals(evt.getPropertyName())) {
            selectedDateDenNgay = DenNgay.getDate();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
            formattedDateDenNgay = sdf.format(selectedDateDenNgay);
            //System.out.println(formattedDate);
        }
    }

    public void btnTim_actionPerformed() {
        if (selectedDateTuNgay == null || selectedDateDenNgay == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn ngày!");
        } else {
            QuanLyDonHangDAO ql = new QuanLyDonHangDAO();
            java.sql.Date tungay = new java.sql.Date(selectedDateTuNgay.getTime());
            java.sql.Date denngay = new java.sql.Date(selectedDateDenNgay.getTime());
            ResultSet rs = ql.SelectTheoNgay(tungay, denngay);
            DefaultTableModel model = (DefaultTableModel) DonHangtTable.getModel();
            model.setRowCount(0);
            Timestamp Ngay = null;
            try {
                while (rs.next()) {
                    int id = rs.getInt("ID");
                    String TenKH = rs.getString("TENKHACHHANG");
                    String TenNV = rs.getString("TENNV");
                    float tong = rs.getFloat("TONGTIEN");
                    Ngay = rs.getTimestamp("NGAYXUAT");
                    model.addRow(new Object[]{id, TenKH, TenNV, tong, Ngay});
                }
                DoanhThu();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void btnAll_actionPerformed() {
        HienThiThongTin();
        selectedDateTuNgay = null;
        DoanhThu();
    }

    public void btnXoa_actionPerformed() {
        int idpx = 0;
        QuanLyDonHangDAO ql = new QuanLyDonHangDAO();

        if (SelectRow != -1) {
            // Hiển thị hộp thoại xác nhận
            int confirmed = JOptionPane.showConfirmDialog(this,
                    "Bạn có muốn xóa đơn hàng này không?", "Xác nhận xóa đơn hàng",
                    JOptionPane.YES_NO_OPTION);

            if (confirmed == JOptionPane.YES_OPTION) {
                // Nếu người dùng chọn YES, thực hiện xóa đơn hàng
                idpx = Integer.parseInt(DonHangtTable.getValueAt(SelectRow, 0).toString());
                int kq = ql.XoaDonHang(idpx);
                if (kq != 0) {
                    JOptionPane.showMessageDialog(this, "Xóa đơn hàng thành công");
                    HienThiThongTin();
                } else {
                    JOptionPane.showMessageDialog(this, "Xóa đơn hàng không thành công!");
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn dòng muốn xóa!");
        }
    }

    public void btnExcel_actionPerformed() {
        exportToExcel();
    }

    private void exportToExcel() {
        // Tạo workbook mới
        Workbook workbook = new XSSFWorkbook();

        // Tạo một trang tính mới
        Sheet sheet = workbook.createSheet("Data");

        // Lấy số dòng và số cột của bảng
        int rowCount = DonHangtTable.getRowCount();
        int columnCount = DonHangtTable.getColumnCount();

        // Ghi tên cột vào hàng đầu tiên
        Row headerRow = sheet.createRow(0);
        for (int col = 0; col < columnCount; col++) {
            headerRow.createCell(col).setCellValue(DonHangtTable.getColumnName(col));
        }

        // Ghi dữ liệu từ bảng vào file Excel
        for (int i = 0; i < rowCount; i++) {
            Row row = sheet.createRow(i + 1); // Bắt đầu từ hàng thứ hai sau header
            for (int j = 0; j < columnCount; j++) {
                Object value = DonHangtTable.getValueAt(i, j);
                Cell cell = row.createCell(j);
                if (value != null) {
                    cell.setCellValue(value.toString());
                }
            }
        }

        // Đường dẫn tới file Excel đích
        String filePath = "D:\\on_java\\Supermarket\\excel\\table_DonHang.xlsx";

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
