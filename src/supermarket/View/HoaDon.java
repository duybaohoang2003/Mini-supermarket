/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package supermarket.View;

import DAO.HoaDonDAO;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import Model.HoaDonM;
import java.sql.Timestamp;
import java.sql.ResultSet;

/**
 *
 * @author ASUS
 */
public class HoaDon extends JFrame {

    private JLabel lbTenKhachHang, lbDiaChi, lbSDT, lbNhanVien, lbNgay, lbHoaDon, lbTong, lbVND;
    private JTable HoaDonTable;
    private JScrollPane jScrollPaneHoaDonTable;
    private DefaultTableModel HoaDonTableModel;
    private final String[] columnName = new String[]{
        "Tên hàng", "Số lượng", "Giá bán", "Thành tiền"
    };
    private final Object data = new Object[][]{};
    int idnv;
    int idkh;

    HoaDonM hoadon = new HoaDonM();

    public HoaDon() {
        lbTenKhachHang = new JLabel("Tên khách hàng");
        lbDiaChi = new JLabel("Địa chỉ");
        lbSDT = new JLabel("Điện thoại");
        lbNhanVien = new JLabel("Nhân viên");
        lbNgay = new JLabel();
        lbHoaDon = new JLabel("Hóa đơn");
        lbTong = new JLabel();
        lbVND = new JLabel("VND");
        Font font = new Font("Arial", Font.BOLD | Font.ITALIC, 26);
        lbHoaDon.setFont(font);
        HoaDonTable = new JTable();
        jScrollPaneHoaDonTable = new JScrollPane();
        HoaDonTableModel = new DefaultTableModel((Object[][]) data, columnName);
        HoaDonTable.setModel(HoaDonTableModel);
        jScrollPaneHoaDonTable.setViewportView(HoaDonTable);
        HoaDonTable.setBorder(BorderFactory.createEmptyBorder());
        jScrollPaneHoaDonTable.setBorder(BorderFactory.createEmptyBorder());
        jScrollPaneHoaDonTable.setPreferredSize(new Dimension(500, 200));;

        SpringLayout layout = new SpringLayout();
        JPanel panel = new JPanel();
        panel.setSize(550, 450);
        panel.setLayout(layout);

        panel.add(lbDiaChi);
        panel.add(lbHoaDon);
        panel.add(lbNgay);
        panel.add(lbNhanVien);
        panel.add(lbSDT);
        panel.add(lbTenKhachHang);
        panel.add(lbTong);
        panel.add(lbVND);
        panel.add(jScrollPaneHoaDonTable);

        layout.putConstraint(SpringLayout.WEST, lbHoaDon, 200, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, lbHoaDon, 10, SpringLayout.NORTH, this);

        layout.putConstraint(SpringLayout.WEST, lbNgay, 200, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, lbNgay, 50, SpringLayout.NORTH, this);

        layout.putConstraint(SpringLayout.WEST, lbNhanVien, 200, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, lbNhanVien, 70, SpringLayout.NORTH, this);

        layout.putConstraint(SpringLayout.WEST, lbTenKhachHang, 20, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, lbTenKhachHang, 100, SpringLayout.NORTH, this);

        layout.putConstraint(SpringLayout.WEST, lbDiaChi, 20, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, lbDiaChi, 120, SpringLayout.NORTH, this);

        layout.putConstraint(SpringLayout.WEST, lbSDT, 300, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, lbSDT, 100, SpringLayout.NORTH, this);

        layout.putConstraint(SpringLayout.WEST, jScrollPaneHoaDonTable, 20, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, jScrollPaneHoaDonTable, 170, SpringLayout.NORTH, this);

        layout.putConstraint(SpringLayout.WEST, lbTong, 370, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, lbTong, 380, SpringLayout.NORTH, this);

        layout.putConstraint(SpringLayout.WEST, lbVND, 500, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, lbVND, 380, SpringLayout.NORTH, this);

        add(panel);
        setSize(551, 451);
        setVisible(true);
    }

    public void hienthiThongTin(int id) {
        Timestamp Ngay = null;
        Float Tong = 0.0f;
        String Ten = "";
        String DiaChi = "";
        String SDT = "";
        String NhanVien = "";
        ResultSet rs;
        HoaDonDAO hd = new HoaDonDAO();
        rs = hd.GetHoaDon(id);
        try {
            if (rs.next()) {
                Ngay = rs.getTimestamp("NGAYXUAT");
                Tong = rs.getFloat("TONGTIEN");
                idnv = rs.getInt("ID_NHANVIEN");
                idkh = rs.getInt("ID_KHACHHANG");
            }
        } catch (Exception e) {
        }
        lbNgay.setText("Ngày: " + Ngay);
        lbTong.setText("Tổng: " + Tong);
        //lấy thông tin khách hàng
        ResultSet rss;
        rss = hd.getInfoKH(idkh);
        try {
            if (rss.next()) {
                Ten = rss.getString("TENKHACHHANG");
                DiaChi = rss.getString("DIACHI");
                SDT = rss.getString("SODIENTHOAI");
            }
        } catch (Exception e) {
        }
        lbTenKhachHang.setText("Tên khách hàng: " + Ten);
        lbDiaChi.setText("Địa chỉ: " + DiaChi);
        lbSDT.setText("Điện thoại: " + SDT);
        //Lấy thông tin nhân viên
        ResultSet rsss;
        rsss = hd.getNhanSu(idnv);
        try {
            if (rsss.next()) {
                NhanVien = rsss.getString("TENNV");
            }
        } catch (Exception e) {
        }
        lbNhanVien.setText("Tên nhân viên: " + NhanVien);
        //DO DU LIEU LEN BANG
        DefaultTableModel model = (DefaultTableModel)HoaDonTable.getModel();
        ResultSet kq;
        kq=hd.GetHangHoa(id);
        try {
            while(kq.next()){
                String TenHangHoa=kq.getString("TENHANGHOA");
                int SoLuong = kq.getInt("SOLUONG");
                float DonGia = kq.getFloat("DONGIA");
                float ThanhTien = (float)SoLuong*DonGia;
                model.addRow(new Object[]{TenHangHoa, SoLuong, DonGia, ThanhTien}); // Thêm dòng mới vào bảng
            }
        } catch (Exception e) {
        }
    }
    public static void main(String args[]){
        HoaDon hd = new HoaDon();
    }
}
