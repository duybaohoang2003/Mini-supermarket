package supermarket.View;

import Dao.ChiTietPNDAO;
import Dao.HangHoaDao;
import Model.phieunhap;
import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;
import javax.swing.border.Border;
import Dao.PhieuNhapDAO;
import com.mysql.cj.result.Row;
import com.toedter.calendar.JDateChooser;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.DocumentFilter;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import supermarket.SetIcon;






public class GUIphieunhap extends JPanel {
    private DefaultTableModel model;
    private JTable table; 
    
    private JButton btnDelete;
    private JButton btnXemChiTiet;
    private JButton btnXuatEX;
    private JButton btnTimKiem;
    private JButton btnTimNgay;
    
    
    private JTextField txtTimkiem;
    
    JDateChooser ngaytruoc,ngaysau;

    
    private JLabel lblTungay;
    private JLabel lblDenngay;
    private JLabel lblTongtien;
    
    
    
    private PhieuNhapDAO PNDAO;
    
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
    public GUIphieunhap() {
        initComponents();
        
    }

    private void initComponents() {
        PNDAO =new PhieuNhapDAO();
        
        DecimalFormat decimalFormat = new DecimalFormat("#,##0");
        String PathDelete = "D:\\on_java\\Supermarket\\icon\\delete.png";
        String PathSearch="D:\\on_java\\Supermarket\\icon\\search.png";
        String PathEx="D:\\on_java\\Supermarket\\icon\\excel.png";
        String PathDetail="D:\\on_java\\Supermarket\\icon\\visual.png";
        String PathSearchDate="D:\\on_java\\Supermarket\\icon\\calendar.png";

        ImageIcon IconSearchDate = SetIcon.SetSize(PathSearchDate, 20, 20);
        ImageIcon IconDetail = SetIcon.SetSize(PathDetail, 20, 20);
        ImageIcon IconEx = SetIcon.SetSize(PathEx, 20, 20);
        ImageIcon IconDelete = SetIcon.SetSize(PathDelete, 20, 20);
        ImageIcon IconSearch = SetIcon.SetSize(PathSearch, 20, 20);
        
        JPanel panelMain=new JPanel();
        panelMain.setLayout(new BorderLayout());
        Border border = BorderFactory.createLineBorder(Color.BLACK);
        JPanel panel1=new JPanel();
        JPanel panel2=new JPanel();
        JPanel panel3=new JPanel();
        panel1.setBorder(border);

//        panel1.setBackground(Color.orange);
//        panel2.setBackground(Color.BLUE);
        

        panelMain.add(panel1,BorderLayout.NORTH);
        panelMain.add(panel2,BorderLayout.CENTER);
        panelMain.add(panel3,BorderLayout.SOUTH);
        
        
        //panel1
        panel1.setPreferredSize(new Dimension(0, 70));
//        panel2.setPreferredSize(new Dimension(0, 600));
        panel3.setPreferredSize(new Dimension(0, 40));
        SpringLayout layout1 = new SpringLayout();
        panel1.setLayout(layout1);

        // Tạo panel11 và panel12 với kích thước đã chỉ định
        JPanel panel11 = new JPanel();
        panel11.setPreferredSize(new Dimension(350, 100));
        JPanel panel12 = new JPanel();
        panel12.setPreferredSize(new Dimension(810, 100));
        panel11.setBorder(border);
        panel12.setBorder(border);
//        panel11.setBackground(Color.MAGENTA); // Màu nền của panel11
//        panel12.setBackground(Color.YELLOW); // Màu nền của panel12

        // Thêm panel11 và panel12 vào panel1
        panel1.add(panel11);
        panel1.add(panel12);
        layout1.putConstraint(SpringLayout.WEST, panel11, 10, SpringLayout.WEST, panel1); // Cách lề trái của panel1 10 pixel
        layout1.putConstraint(SpringLayout.EAST, panel12, -10, SpringLayout.EAST, panel1); // Cách lề phải của panel1 10 pixel
        layout1.putConstraint(SpringLayout.NORTH, panel11, 0, SpringLayout.NORTH, panel1); // Panel11 sát với lề trên của panel1
        layout1.putConstraint(SpringLayout.NORTH, panel12, 0, SpringLayout.NORTH, panel1); // Panel12 sát với lề trên của panel1
        layout1.putConstraint(SpringLayout.SOUTH, panel11, 0, SpringLayout.SOUTH, panel1); // Panel11 sát với lề dưới của panel1
        layout1.putConstraint(SpringLayout.SOUTH, panel12, 0, SpringLayout.SOUTH, panel1); // Panel12 sát với lề dưới của panel1
        
        //panel11
        btnDelete=new JButton("Xóa phiếu");
        btnDelete.setIcon(IconDelete);
        btnDelete.setVerticalTextPosition(SwingConstants.BOTTOM); // Đặt vị trí của chữ ở dưới ảnh
        btnDelete.setHorizontalTextPosition(SwingConstants.CENTER); // Đặt vị trí của chữ ở giữa theo chiều ngang
        btnXemChiTiet=new JButton("Xem chi tiết");
        btnXemChiTiet.setIcon(IconDetail);
        btnXemChiTiet.setVerticalTextPosition(SwingConstants.BOTTOM); // Đặt vị trí của chữ ở dưới ảnh
        btnXemChiTiet.setHorizontalTextPosition(SwingConstants.CENTER); // Đặt vị trí của chữ ở giữa theo chiều ngang
        btnXuatEX=new JButton("Xuất Excel");
        btnXuatEX.setIcon(IconEx);
        btnXuatEX.setVerticalTextPosition(SwingConstants.BOTTOM); // Đặt vị trí của chữ ở dưới ảnh
        btnXuatEX.setHorizontalTextPosition(SwingConstants.CENTER); // Đặt vị trí của chữ ở giữa theo chiều ngang
        panel11.setLayout(new BoxLayout(panel11, BoxLayout.X_AXIS));

        // Tạo một Box để chứa các thành phần và căn giữa
        Box boxPanel11 = Box.createHorizontalBox();
        boxPanel11.add(Box.createHorizontalGlue()); // Thêm khoảng trống linh hoạt vào đầu
        boxPanel11.add(btnDelete);
        boxPanel11.add(Box.createHorizontalGlue());
        boxPanel11.add(btnXemChiTiet);
        boxPanel11.add(Box.createHorizontalGlue());
        boxPanel11.add(btnXuatEX);
        boxPanel11.add(Box.createHorizontalGlue()); // Thêm khoảng trống linh hoạt vào cuối

        // Thêm Box đã tạo vào panel11
        panel11.add(boxPanel11);


        //panel12
        txtTimkiem=new JTextField();
        btnTimKiem=new JButton("");
        btnTimKiem.setIcon(IconSearch);

        ngaytruoc=new JDateChooser();
        ngaytruoc.setDateFormatString("dd-MM-yyyy");
        lblTungay=new JLabel("Từ ngày:");
        lblDenngay=new JLabel("Đến ngày:");
        ngaysau=new JDateChooser();
        ngaysau.setDateFormatString("dd-MM-yyyy");
        btnTimNgay=new JButton("");
        btnTimNgay.setIcon(IconSearchDate);

        panel12.setLayout(new BoxLayout(panel12, BoxLayout.X_AXIS));
        panel12.add(txtTimkiem);
        panel12.add(btnTimKiem);
        panel12.add(Box.createHorizontalGlue()); // Thêm khoảng trống linh hoạt giữa các thành phần
        panel12.add(lblTungay);
        panel12.add(ngaytruoc);
        panel12.add(lblDenngay);
        panel12.add(ngaysau);
        panel12.add(btnTimNgay);
        
        // Thiết lập kích thước cho các thành phần trong panel11
        btnDelete.setPreferredSize(new Dimension(100, 30));
        btnXemChiTiet.setPreferredSize(new Dimension(120, 30));
        btnXuatEX.setPreferredSize(new Dimension(100, 30));

        // Thiết lập kích thước cho các thành phần trong panel12
        txtTimkiem.setPreferredSize(new Dimension(150, 0));
        btnTimKiem.setPreferredSize(new Dimension(100, 30));
        ngaytruoc.setPreferredSize(new Dimension(150, 30));
        lblTungay.setPreferredSize(new Dimension(60, 30));
        lblDenngay.setPreferredSize(new Dimension(60, 30));
        ngaysau.setPreferredSize(new Dimension(150, 30));
        btnTimNgay.setPreferredSize(new Dimension(80, 30));
        // Xác định kích thước tối đa cho các JTextField trong panel12
        ngaytruoc.setMaximumSize(new Dimension(150, 30));
        ngaysau.setMaximumSize(new Dimension(150, 30));
        txtTimkiem.setMaximumSize(new Dimension(150, 30));
        
        
        ((AbstractDocument) txtTimkiem.getDocument()).setDocumentFilter(new DocumentFilter() {
            private final Pattern pattern = Pattern.compile("\\d*");

            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                Matcher matcher = pattern.matcher(text);
                if (!matcher.matches()) {
                    return;
                }
                super.replace(fb, offset, length, text, attrs);
            }
        });
        
        //panel2
        JPanel pnTable = new JPanel();
        pnTable.setLayout(new BorderLayout());
        model = new DefaultTableModel();
        model.addColumn("Id");
        model.addColumn("Nhân viên");
        model.addColumn("Nhà Cung cấp");
        model.addColumn("Ngày nhập");
        model.addColumn("Tổng tiền");
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        pnTable.add(scrollPane, BorderLayout.CENTER); // Đặt scrollPane vào vị trí CENTER của pnTable
        panel2.setLayout(new BorderLayout());
        panel2.add(pnTable, BorderLayout.CENTER); // Đặt pnTable vào vị trí CENTER của panel2
        table.setModel(model);
        
        //panel3
        SpringLayout layout3 = new SpringLayout();
        panel3.setLayout(layout3);
        lblTongtien=new JLabel("");
        lblTongtien.setFont(new Font("Arial", Font.BOLD, 16));
        panel3.add(lblTongtien);

        // Thiết lập ràng buộc cho lbTongtien
        layout3.putConstraint(SpringLayout.WEST, lblTongtien, 10, SpringLayout.WEST, panel3);
        layout3.putConstraint(SpringLayout.NORTH, lblTongtien, 10, SpringLayout.NORTH, panel3);  
        
        
        String idphieu = "";
        for (phieunhap phieuNhap : PNDAO.getPhieuNhapList(idphieu)) {
            Object[] rowData = new Object[5];
            rowData[0] = phieuNhap.getId();
            rowData[1] = PNDAO.SelectTenNV(phieuNhap.getIdNhanVien()); // Thay thế bằng dữ liệu tương ứng trong đối tượng phieunhap
            rowData[2] = PNDAO.SelectTenNCC(phieuNhap.getIdNCC()); // Thay thế bằng dữ liệu tương ứng trong đối tượng phieunhap
            rowData[3] = dateFormat.format(phieuNhap.getNgayNhap()); // Thay thế bằng dữ liệu tương ứng trong đối tượng phieunhap
            rowData[4] = decimalFormat.format(phieuNhap.getTongTien()); // Thay thế bằng dữ liệu tương ứng trong đối tượng phieunhap
            model.addRow(rowData);
        }
        lblTongtien.setText("Tổng tiền : "+decimalFormat.format(tinhTongThanhTien(table))+" VND");
        
        ngaysau.getDateEditor().addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if ("date".equals(evt.getPropertyName())) {
                    Date selectedDate = ngaysau.getDate();
                    if (selectedDate != null && selectedDate.after(Calendar.getInstance().getTime())) {
                        // Nếu ngày chọn sau ngày hiện tại, đặt lại thành ngày hiện tại
                        ngaysau.setDate(Calendar.getInstance().getTime());
                    }
                }
            }
        });
        
        ngaytruoc.getDateEditor().addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if ("date".equals(evt.getPropertyName())) {
                    Date selectedDate = ngaytruoc.getDate();
                    if (selectedDate != null && selectedDate.after(Calendar.getInstance().getTime())) {
                        // Nếu ngày chọn sau ngày hiện tại, đặt lại thành ngày hiện tại
                        ngaytruoc.setDate(Calendar.getInstance().getTime());
                    }
                }
            }
        });
        
        btnTimKiem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.setRowCount(0);
                String idphieu = txtTimkiem.getText();
                for (phieunhap phieuNhap : PNDAO.getPhieuNhapList(idphieu)) {
                    Object[] rowData = new Object[5];
                    rowData[0] = phieuNhap.getId();
                    rowData[1] = PNDAO.SelectTenNV(phieuNhap.getIdNhanVien()); // Thay thế bằng dữ liệu tương ứng trong đối tượng phieunhap
                    rowData[2] = PNDAO.SelectTenNCC(phieuNhap.getIdNCC()); // Thay thế bằng dữ liệu tương ứng trong đối tượng phieunhap
                    rowData[3] = dateFormat.format(phieuNhap.getNgayNhap()); // Thay thế bằng dữ liệu tương ứng trong đối tượng phieunhap
                    rowData[4] = decimalFormat.format(phieuNhap.getTongTien()); // Thay thế bằng dữ liệu tương ứng trong đối tượng phieunhap
                    model.addRow(rowData);
                }
                lblTongtien.setText("Tổng tiền : "+decimalFormat.format(tinhTongThanhTien(table))+" VND");
            }
        });
        
        btnTimNgay.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.setRowCount(0);
                DateFormat outputDateFormat = new SimpleDateFormat("dd-MM-yyyy");
                Date NgaytruocDate = ngaytruoc.getDate();
                String ngayTruocFormatted = outputDateFormat.format(NgaytruocDate);
                Date NgaysauDate = ngaysau.getDate();
                String ngaySauFormatted = outputDateFormat.format(NgaysauDate);
                for (phieunhap phieuNhap : PNDAO.getPhieuNhapListngay(NgaytruocDate,NgaysauDate)) {
                    Object[] rowData = new Object[5];
                    rowData[0] = phieuNhap.getId();
                    rowData[1] = PNDAO.SelectTenNV(phieuNhap.getIdNhanVien()); // Thay thế bằng dữ liệu tương ứng trong đối tượng phieunhap
                    rowData[2] = PNDAO.SelectTenNCC(phieuNhap.getIdNCC()); // Thay thế bằng dữ liệu tương ứng trong đối tượng phieunhap
                    rowData[3] = dateFormat.format(phieuNhap.getNgayNhap()); // Thay thế bằng dữ liệu tương ứng trong đối tượng phieunhap
                    rowData[4] = decimalFormat.format(phieuNhap.getTongTien()); // Thay thế bằng dữ liệu tương ứng trong đối tượng phieunhap
                    model.addRow(rowData);
                }
                lblTongtien.setText("Từ ngày "+ngayTruocFormatted+" đến ngày "+ngaySauFormatted+" Tổng tiền là : "+decimalFormat.format(tinhTongThanhTien(table))+" VND");
            }
        });
        
        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();

                // Kiểm tra xem có hàng được chọn không
                if (selectedRow != -1) {
                    int idValue = Integer.parseInt(table.getValueAt(selectedRow, 0).toString());
                    // Xóa dòng khỏi mô hình bảng
                    PNDAO = new PhieuNhapDAO();
                    PNDAO.xoaPhieuNhap(idValue);
                    model.removeRow(selectedRow);
                } else {
                    JOptionPane.showMessageDialog(null, "Vui lòng chọn dòng để xóa.", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                }
                lblTongtien.setText("Tổng tiền : "+decimalFormat.format(tinhTongThanhTien(table))+" VND");
            }
        });
        
        
        btnXuatEX.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (table.getRowCount() > 0) {
                    exportToExcel();
                } else {
                    JOptionPane.showMessageDialog(null, "Chưa có hàng hóa nào");
                }
            }
        });

        
        btnXemChiTiet.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();

                // Kiểm tra xem có hàng được chọn không
                if (selectedRow != -1) {
                    int IdMaPhieu=Integer.parseInt(table.getValueAt(selectedRow, 0).toString());
                    Chitietphieunhap chitietFrame = new Chitietphieunhap(IdMaPhieu);
                    chitietFrame.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, "Vui lòng chọn dòng để xóa.", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
        
        


        add(panelMain);
        panelMain.setPreferredSize(new Dimension(1180, 600));
    }
    public double tinhTongThanhTien(JTable table) {
        double tongThanhTien=0;
        int rowCount = table.getRowCount();

        for (int i = 0; i < rowCount; i++) {
            double thanhTien = Double.parseDouble(table.getValueAt(i, 4).toString().replace(",", ""));
            tongThanhTien += thanhTien;
        }

        return tongThanhTien;
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

        // Thêm tổng tiền vào dòng cuối cùng của sheet Excel
        org.apache.poi.ss.usermodel.Row totalRow = sheet.createRow(rowCount + 2); // Bắt đầu từ hàng rowCount + 2
        totalRow.createCell(0).setCellValue("Total");
        totalRow.createCell(columnCount - 1).setCellValue(tinhTongThanhTien(table));

        String filePath = "D:\\on_java\\Supermarket\\excel\\table.xlsx";
        // Lưu workbook vào một file
        try (FileOutputStream outputStream = new FileOutputStream(filePath)) {
            workbook.write(outputStream);
            JOptionPane.showMessageDialog(null, "Data exported to Excel successfully.");
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error exporting data to Excel: " + e.getMessage());
        }
    }



}
