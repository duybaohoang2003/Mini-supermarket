/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

/**
 *
 * @author Bao Hoang
 */
import Model.KhachHang;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author ASUS
 */
public class KhachHangDAO {

    private Connection con;

    public KhachHangDAO() {
        String url = "jdbc:mysql://localhost:3306/test_supermarket";
        String dbClass = "com.mysql.cj.jdbc.Driver";
        String username = "root";
        String password = "";
        try {
            Class.forName(dbClass);
            con = DriverManager.getConnection(url, username, password);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ResultSet getAllKH() {
        ResultSet rs = null;
        String sql = "SELECT * FROM khachhang";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return rs;
    }

    public KhachHangDAO GetInfo(KhachHang s) {
        KhachHangDAO sv = new KhachHangDAO();
        String sql = "SELECT * FROM khachhang WHERE Id=?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, s.getId());

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    //neu tim thay id sinh vien
                    System.out.println("Tim thay");
                } else {
                    //neu khong tim thay id sinh vien
                    System.out.println("Tên đăng nhập hoặc mật khẩu không đúng!");
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return sv;
    }

    public int ThemKH(KhachHang sv) {
        int success = 0;
        if (isSDTExists(sv.getDienThoai())) {
            System.out.println("Số điện thoại đã tồn tại trong cơ sở dữ liệu.");
            return success;
        }

        String sql = "INSERT INTO khachhang(TenKhachHang, GioiTinh, DiaChi, SoDienThoai) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, sv.getName());
            ps.setInt(2, sv.getGioiTinh());
            ps.setString(3, sv.getDiaChi());
            ps.setString(4, sv.getDienThoai());

            success = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return success;
    }

    public int SuaKH(String Id, KhachHang sv) {
        int rowsUpdate = 0;
       

        String sql = "UPDATE khachhang SET TenKhachHang=?, GioiTinh=?, DiaChi=?, SoDienThoai=? WHERE Id=?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, sv.getName());
            ps.setInt(2, sv.getGioiTinh());
            ps.setString(3, sv.getDiaChi());
            ps.setString(4, sv.getDienThoai());
            ps.setInt(5, sv.getId());

            rowsUpdate = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rowsUpdate;
    }

    public int XoaKH(String id) {
        int success = 0;

        String sql = "DELETE FROM khachhang WHERE Id=?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, id);

            success = ps.executeUpdate();

        } catch (Exception e) {
            System.out.println(e);
        }
        return success;
    }

    public ResultSet searchByName(String searchName) {
        ResultSet rs = null;
        try {
            String query = "SELECT * FROM KhachHang WHERE TenKhachHang LIKE ?";
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1, "%" + searchName + "%");
            rs = preparedStatement.executeQuery();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return rs;
    }

    public boolean isSDTExists(String sdt) {
        boolean exists = false;
        try {
            String query = "SELECT COUNT(*) FROM khachhang WHERE SoDienThoai = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, sdt);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                if (count > 0) {
                    exists = true;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return exists;
    }
    
    public int ThemKHPX(String ten, String diachi, String dienthoai){
        int success=0;
        String sql = "INSERT INTO KHACHHANG(TENKHACHHANG, DIACHI, SODIENTHOAI) VALUES (?,?,?)";
        try{
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1,ten);
            ps.setString(2,diachi);
            ps.setString(3,dienthoai);
            success = ps.executeUpdate();
        }catch(Exception e){
            e.printStackTrace();
        }
        return success;
    }
}
