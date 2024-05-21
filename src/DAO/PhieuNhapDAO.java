/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dao;
import supermarket.View.GUIphieunhap;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import Model.phieunhap;
import java.util.Date;

public class PhieuNhapDAO {
    private Connection con;

    public PhieuNhapDAO() {
        // Kết nối đến cơ sở dữ liệu
        // Kết nối đến cơ sở dữ liệu
        String url = "jdbc:mysql://localhost:3306/test_supermarket";
        String dbClass = "com.mysql.cj.jdbc.Driver";
        String username = "root";
        String password = "";
        try {
            Class.forName(dbClass);
            con = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean xuLyPhieuNhap(int idProduct, int soluong, int Dongia) {
        boolean success = false; // Khởi tạo biến kết quả là false
        try {
            // Kiểm tra nếu có phiếu nhập chưa hoàn thành (status=0)
            PreparedStatement selectPhieuNhap = con.prepareStatement("SELECT * FROM phieunhap WHERE status = 0");
            ResultSet fetchPhieuNhap = selectPhieuNhap.executeQuery();

            // Nếu không có, tạo mới một phiếu nhập
            if (!fetchPhieuNhap.next()) {
                PreparedStatement insertPhieuNhap = con.prepareStatement("INSERT INTO phieunhap () VALUES ()", PreparedStatement.RETURN_GENERATED_KEYS);
                insertPhieuNhap.executeUpdate();
                ResultSet generatedKeys = insertPhieuNhap.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int phieuNhapId = generatedKeys.getInt(1);
                    System.out.println("Tạo PN thành công");
                } else {
                    throw new SQLException("Tạo phiếu nhập không thành công, không có ID được tạo ra.");
                }
                // Lấy thông tin phiếu nhập mới tạo
                selectPhieuNhap = con.prepareStatement("SELECT * FROM phieunhap WHERE status = 0");
                fetchPhieuNhap = selectPhieuNhap.executeQuery();
                fetchPhieuNhap.next();
            }

            // Kiểm tra mã hàng đã tồn tại trong chi tiết phiếu nhập chưa
            PreparedStatement checkMaHang = con.prepareStatement("SELECT * FROM chitietphieunhap WHERE id_hanghoa = ? AND id_phieunhap = ?");
            checkMaHang.setInt(1, idProduct);
            checkMaHang.setInt(2, fetchPhieuNhap.getInt("id"));
            ResultSet rsCheckMaHang = checkMaHang.executeQuery();

            // Nếu đã tồn tại, thông báo và không thêm mới
            if (rsCheckMaHang.next()) {
                JOptionPane.showMessageDialog(null, "Đã có");
            } else {
                // Nếu chưa tồn tại, thêm mới chi tiết phiếu nhập
                PreparedStatement insertCTPhieuNhap = con.prepareStatement("INSERT INTO chitietphieunhap (id_hanghoa,id_phieunhap,SoLuong,DonGia) VALUES (?, ?,?,?)");
                insertCTPhieuNhap.setInt(1, idProduct);
                insertCTPhieuNhap.setInt(2, fetchPhieuNhap.getInt("id"));
                insertCTPhieuNhap.setInt(3, soluong);
                insertCTPhieuNhap.setInt(4, Dongia);
                insertCTPhieuNhap.executeUpdate();
                JOptionPane.showMessageDialog(null, "Thêm thành công");
                success = true; // Đánh dấu thành công
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return success; // Trả về kết quả
    }
    public int PhieuChuaXuat() {
        int IdPhieu = -1; // Khởi tạo IdPhieu với giá trị mặc định

        try {
            String sql = "SELECT `Id` FROM `phieunhap` WHERE STATUS = 0";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            // Kiểm tra xem ResultSet có kết quả không
            if (rs.next()) {
                // Trích xuất giá trị Id từ ResultSet
                IdPhieu = rs.getInt("Id");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }

        return IdPhieu; // Trả về IdPhieu
    }
    
    public void updatePhieuNhap(phieunhap phieuNhap) {
    String sql = "UPDATE `phieunhap` SET `Id_NhanVien` = ?, `Id_NCC` = ?, `NgayNhap` = ?, `TongTien` = ?, `Status` = ? WHERE `Id` = ?";
    String sqlchitiet = "UPDATE `chitietphieunhap` SET `Status` = 1 where id_phieunhap=?";

    try {
        int id=phieuNhap.getId();
        int status=phieuNhap.getStatus();
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, phieuNhap.getIdNhanVien());
        ps.setInt(2, phieuNhap.getIdNCC());
        // Chuyển đổi java.util.Date thành java.sql.Date
        java.util.Date ngayNhapUtil = phieuNhap.getNgayNhap();
        java.sql.Date ngayNhapSql = new java.sql.Date(ngayNhapUtil.getTime());
        ps.setDate(3, ngayNhapSql);
        ps.setDouble(4, phieuNhap.getTongTien());
        ps.setInt(5, status);
        ps.setInt(6, id);
        int rowsUpdated = ps.executeUpdate(); // Kiểm tra số hàng đã được cập nhật

        if (rowsUpdated > 0) {
            // Cập nhật thành công, tiến hành cập nhật chi tiết phiếu nhập
            PreparedStatement psChitiet = con.prepareStatement(sqlchitiet);
            psChitiet.setInt(1, id);
            int rowsUpdatedChiTiet = psChitiet.executeUpdate(); // Kiểm tra số hàng đã được cập nhật

            if (rowsUpdatedChiTiet > 0) {
                JOptionPane.showMessageDialog(null, "Nhập phiếu thành công");
            } else {
                JOptionPane.showMessageDialog(null, "Cập nhật phiếu không thành công");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Cập nhật phiếu không thành công");
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Cập nhật phiếu không thành công");
        e.printStackTrace();
    }
}




    
    public int SelectIDNV(String name) {
        int IdNV = -1; // Khởi tạo IdPhieu với giá trị mặc định

        try {
            String sql = "SELECT `Id` FROM `nhanvien` WHERE TenNV=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();

            // Kiểm tra xem ResultSet có kết quả không
            if (rs.next()) {
                // Trích xuất giá trị Id từ ResultSet
                IdNV = rs.getInt("Id");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }

        return IdNV; // Trả về IdPhieu
    }
    
    public int SelectIDNCC(String name) {
        int IdNCC = -1; // Khởi tạo IdPhieu với giá trị mặc định

        try {
            String sql = "SELECT `Id` FROM `nhacungcap` WHERE TenNCC=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();

            // Kiểm tra xem ResultSet có kết quả không
            if (rs.next()) {
                // Trích xuất giá trị Id từ ResultSet
                IdNCC = rs.getInt("Id");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }

        return IdNCC; // Trả về IdPhieu
    }
    public String SelectTenNV(int id) {
        String IdNV = ""; // Khởi tạo IdPhieu với giá trị mặc định
        try {
            String sql = "SELECT `TenNV` FROM `nhanvien` WHERE id=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            // Kiểm tra xem ResultSet có kết quả không
            if (rs.next()) {
                // Trích xuất giá trị Id từ ResultSet
                IdNV = rs.getString("TenNV");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }

        return IdNV; // Trả về IdPhieu
    }
    
    public String SelectTenNCC(int id) {
        String IdNCC = ""; // Khởi tạo IdPhieu với giá trị mặc định

        try {
            String sql = "SELECT `TenNCC` FROM `nhacungcap` WHERE id=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            // Kiểm tra xem ResultSet có kết quả không
            if (rs.next()) {
                // Trích xuất giá trị Id từ ResultSet
                IdNCC = rs.getString("TenNCC");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }

        return IdNCC; // Trả về IdPhieu
    }
    
    public List<String> layDanhSachTenNhanVien() {
        List<String> danhSachTen = new ArrayList<>();

        String sql = "SELECT `TenNV` FROM `nhanvien`";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String tenNV = rs.getString("TenNV");
                danhSachTen.add(tenNV);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return danhSachTen;
    }
    
    public List<String> layDanhSachTenNCC() {
        List<String> danhSachTen = new ArrayList<>();

        String sql = "SELECT `TenNCC` FROM `nhacungcap`";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String tenNV = rs.getString("TenNCC");
                danhSachTen.add(tenNV);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return danhSachTen;
    }
    
    public List<String> layDanhSachTenNhomHang() {
        List<String> danhSachTen = new ArrayList<>();

        String sql = "SELECT * FROM `nhomhang`";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String tenNhomhang = rs.getString("TenNhomHang");
                danhSachTen.add(tenNhomhang);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return danhSachTen;
    }
    
    public List<phieunhap> getPhieuNhapList(String id) {
        List<phieunhap> phieuNhapList = new ArrayList<>();
        try {
            String sql = "SELECT * FROM `phieunhap` WHERE status=1";
        if (!id.isEmpty()) {
            sql += " AND id=?";
        }
        PreparedStatement ps = con.prepareStatement(sql);
        if (!id.isEmpty()) {
            ps.setString(1, id);
        }
            ResultSet rs = ps.executeQuery();
            DecimalFormat decimalFormat = new DecimalFormat("#,##0");
            while (rs.next()) {
                phieunhap phieuNhap = new phieunhap(
                    rs.getInt("Id"),
                    rs.getInt("Id_NhanVien"),
                    rs.getInt("Id_NCC"),
                    rs.getDate("NgayNhap"),
                    rs.getFloat("TongTien"),
                    rs.getInt("Status")
                );
                phieuNhapList.add(phieuNhap);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
        return phieuNhapList;
    }
    
    public List<phieunhap> getPhieuNhapListngay(Date Ngaytruoc, Date Ngaysau) {
        List<phieunhap> phieuNhapList = new ArrayList<>();
        try {
            String sql = "SELECT * FROM `phieunhap` WHERE status=1";
            if (Ngaytruoc != null && Ngaysau != null) {
                sql += " AND `NgayNhap` BETWEEN ? AND ?";
            }
            PreparedStatement ps = con.prepareStatement(sql);
            if (Ngaytruoc != null && Ngaysau != null) {
                ps.setDate(1, new java.sql.Date(Ngaytruoc.getTime()));
                ps.setDate(2, new java.sql.Date(Ngaysau.getTime()));
            }
            ResultSet rs = ps.executeQuery();
            DecimalFormat decimalFormat = new DecimalFormat("#,##0");
            while (rs.next()) {
                phieunhap phieuNhap = new phieunhap(
                    rs.getInt("Id"),
                    rs.getInt("Id_NhanVien"),
                    rs.getInt("Id_NCC"),
                    rs.getDate("NgayNhap"),
                    rs.getFloat("TongTien"),
                    rs.getInt("Status")
                );
                phieuNhapList.add(phieuNhap);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
        return phieuNhapList;
    }
    
    public phieunhap getPhieuNhap(int id) {
        phieunhap phieuNhap = null; // Khai báo biến phieuNhap trước khi sử dụng

        try {
            String sql = "SELECT * FROM `phieunhap` WHERE id=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            DecimalFormat decimalFormat = new DecimalFormat("#,##0");

            if (rs.next()) {
                // Gán giá trị cho biến phieuNhap nếu truy vấn thành công
                phieuNhap = new phieunhap(
                    rs.getInt("Id"),
                    rs.getInt("Id_NhanVien"),
                    rs.getInt("Id_NCC"),
                    rs.getDate("NgayNhap"),
                    rs.getFloat("TongTien"),
                    rs.getInt("Status")
                );
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }

        return phieuNhap; // Trả về giá trị của biến phieuNhap
    }

    
    public void xoaPhieuNhap(int idPhieuNhap) {
        try {
            // Xóa phiếu nhập
            PreparedStatement deletePhieuNhap = con.prepareStatement("DELETE FROM phieunhap WHERE id = ?");
            deletePhieuNhap.setInt(1, idPhieuNhap);
            int rowsAffectedPN = deletePhieuNhap.executeUpdate();

            if (rowsAffectedPN > 0) {
                JOptionPane.showMessageDialog(null, "Xóa phiếu nhập thành công");
            } else {
                JOptionPane.showMessageDialog(null, "Phiếu nhập không tồn tại");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    
    

}

