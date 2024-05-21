/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package supermarket.View;

/**
 *
 * @author DELL
 */
import Dao.ChiTietPNDAO;
import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;
import Dao.PhieuNhapDAO;
import Model.chitietphieunhap;
import Model.phieunhap;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;
import javax.swing.JOptionPane;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;



public class Chitietphieunhap extends JFrame {
    private int IDMaPhieu;
    
    private PhieuNhapDAO PNDAO;
    
    private DefaultTableModel model;
    private JTable table; 
    private JLabel lbTieude;
    private JLabel lbMaphieu;
    private JLabel lbNCC;
    private JLabel lbNguoitao;
    private JLabel lbNgaytao;
    private JLabel lbTongtien;
    
    private JButton btnXuatPDF;

    public Chitietphieunhap(int IdMaPhieu) {
        this.IDMaPhieu=IdMaPhieu;
        initComponents();
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        
        DecimalFormat decimalFormat = new DecimalFormat("#,##0");
        PNDAO=new PhieuNhapDAO();
       // Thêm panel chính vào JFrame
        JPanel panelMain=new JPanel();
        JPanel panel1=new JPanel();
        JPanel panel2=new JPanel();
        JPanel panel3=new JPanel();
        JPanel panel4=new JPanel();


       
        SpringLayout layoutMain = new SpringLayout();
        panelMain.setLayout(layoutMain);
        panelMain.add(panel1);
        panelMain.add(panel2);
        panelMain.add(panel3);
        panelMain.add(panel4);
        panel2.setPreferredSize(new Dimension(0, 80));
        panel3.setPreferredSize(new Dimension(0, 270));
        
        layoutMain.putConstraint(SpringLayout.NORTH, panel1, 10, SpringLayout.NORTH, panelMain);
        layoutMain.putConstraint(SpringLayout.WEST, panel1, 10, SpringLayout.WEST, panelMain);
        layoutMain.putConstraint(SpringLayout.EAST, panel1, -10, SpringLayout.EAST, panelMain);

        layoutMain.putConstraint(SpringLayout.NORTH, panel2, 10, SpringLayout.SOUTH, panel1);
        layoutMain.putConstraint(SpringLayout.WEST, panel2, 10, SpringLayout.WEST, panelMain);
        layoutMain.putConstraint(SpringLayout.EAST, panel2, -10, SpringLayout.EAST, panelMain);

        layoutMain.putConstraint(SpringLayout.NORTH, panel3, 10, SpringLayout.SOUTH, panel2);
        layoutMain.putConstraint(SpringLayout.WEST, panel3, 10, SpringLayout.WEST, panelMain);
        layoutMain.putConstraint(SpringLayout.EAST, panel3, -10, SpringLayout.EAST, panelMain);

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
        lbTieude = new JLabel("CHI TIẾT PHIẾU NHẬP");
        lbTieude.setFont(new Font("Arial", Font.BOLD, 24));
        panel1.add(lbTieude, gbc);
        
        //panel2
        SpringLayout layout2 = new SpringLayout();
        panel2.setLayout(layout2);

        // Thiết lập các JLabel
        phieunhap phieuNhap=new phieunhap();
        phieuNhap=PNDAO.getPhieuNhap(IDMaPhieu);
        lbMaphieu = new JLabel("Mã phiếu: "+IDMaPhieu);
        lbNCC = new JLabel("Nhà cung cấp: "+PNDAO.SelectTenNCC(phieuNhap.getIdNCC()));
        lbNguoitao = new JLabel("Người tạo: "+PNDAO.SelectTenNV(phieuNhap.getIdNhanVien()));
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String ngayNhapFormatted = dateFormat.format(phieuNhap.getNgayNhap());
        lbNgaytao = new JLabel("Ngày tạo: " + ngayNhapFormatted);
        lbMaphieu.setPreferredSize(new Dimension(250, lbMaphieu.getPreferredSize().height));

        lbNguoitao.setPreferredSize(new Dimension(250, lbNguoitao.getPreferredSize().height));

        
        lbMaphieu.setFont(new Font("Arial", Font.BOLD, 14));
        lbNCC.setFont(new Font("Arial", Font.BOLD, 14));
        lbNguoitao.setFont(new Font("Arial", Font.BOLD, 14));
        lbNgaytao.setFont(new Font("Arial", Font.BOLD, 14));

        // Thêm các JLabel vào panel
        panel2.add(lbMaphieu);
        panel2.add(lbNCC);
        panel2.add(lbNguoitao);
        panel2.add(lbNgaytao);

        // Thiết lập các ràng buộc
        layout2.putConstraint(SpringLayout.WEST, lbMaphieu, 50, SpringLayout.WEST, panel2);
        layout2.putConstraint(SpringLayout.NORTH, lbMaphieu, 10, SpringLayout.NORTH, panel2);

        layout2.putConstraint(SpringLayout.WEST, lbNCC, 50, SpringLayout.EAST, lbMaphieu);
        layout2.putConstraint(SpringLayout.NORTH, lbNCC, 0, SpringLayout.NORTH, lbMaphieu);

        layout2.putConstraint(SpringLayout.WEST, lbNguoitao, 0, SpringLayout.WEST, lbMaphieu);
        layout2.putConstraint(SpringLayout.NORTH, lbNguoitao, 20, SpringLayout.SOUTH, lbMaphieu);
        layout2.putConstraint(SpringLayout.WEST, lbNguoitao, 50, SpringLayout.WEST, panel2);

        layout2.putConstraint(SpringLayout.WEST, lbNgaytao, 50, SpringLayout.EAST, lbNguoitao);
        layout2.putConstraint(SpringLayout.NORTH, lbNgaytao, 0, SpringLayout.NORTH, lbNguoitao);
        
        //panel3
        JPanel pnTable = new JPanel();
        pnTable.setLayout(new BorderLayout());
        model = new DefaultTableModel();
        model.addColumn("ID Hàng Hóa");
        model.addColumn("Hàng hóa");
        model.addColumn("Đơn giá");
        model.addColumn("Số lượng");
        model.addColumn("Thành tiền");
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        pnTable.add(scrollPane, BorderLayout.CENTER); // Đặt scrollPane vào vị trí CENTER của pnTable
        panel3.setLayout(new BorderLayout());
        panel3.add(pnTable, BorderLayout.CENTER);
        
        //panel4
        SpringLayout layout4 = new SpringLayout();
        panel4.setLayout(layout4);

        // Tạo JLabel và JButton
        lbTongtien = new JLabel("Tổng tiền:"+decimalFormat.format(phieuNhap.getTongTien())+" VND");
        btnXuatPDF = new JButton("Xuất EXCEL");

        // Thêm các thành phần vào panel
        panel4.add(lbTongtien);
        panel4.add(btnXuatPDF);

        // Thiết lập ràng buộc cho lbTongtien
        layout4.putConstraint(SpringLayout.WEST, lbTongtien, 10, SpringLayout.WEST, panel4);
        layout4.putConstraint(SpringLayout.NORTH, lbTongtien, 10, SpringLayout.NORTH, panel4);        

        // Thiết lập ràng buộc cho btnXuatPDF
        layout4.putConstraint(SpringLayout.EAST, btnXuatPDF, -10, SpringLayout.EAST, panel4);
        layout4.putConstraint(SpringLayout.NORTH, btnXuatPDF, 0, SpringLayout.NORTH, panel4);
        layout4.putConstraint(SpringLayout.SOUTH, btnXuatPDF, 0, SpringLayout.SOUTH, panel4);
        
        ChiTietPNDAO chiTietPNDAO = new ChiTietPNDAO();
        chiTietPNDAO.displayChiTietPN(table,model,IDMaPhieu);
        
        btnXuatPDF.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (table.getRowCount() > 0) {
                    exportToExcel();
                } else {
                    JOptionPane.showMessageDialog(null, "Chưa có hàng hóa nào");
                }
            }
        });
        
        add(panelMain);
        setVisible(true);

        setSize(800, 500);
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

        String filePath = "D:\\chitietphieu.xlsx";
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
