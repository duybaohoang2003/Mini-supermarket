/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.NhanVien;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


/**
 *
 * @author Bao Hoang
 */
public class NhanVienDAO {

    private Connection con;

    public NhanVienDAO() {
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

    public ResultSet getAllNV() {
        ResultSet rs = null;
        String sql = "SELECT * FROM nhanvien";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return rs;
    }

    // Phương thức để lấy tên chức vụ dựa trên id
    public String getTenChucVu(int idChucVu) {
        String tenChucVu = "";
        String sql = "SELECT TenChucVu FROM chucvu WHERE Id = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, idChucVu);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                tenChucVu = rs.getString("TenChucVu");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tenChucVu;
    }

    // Phương thức để lấy tên bộ phận dựa trên id
    public String getTenBoPhan(int idBoPhan) {
        String tenBoPhan = "";
        String sql = "SELECT TenBoPhan FROM bophan WHERE Id = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, idBoPhan);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                tenBoPhan = rs.getString("TenBoPhan");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tenBoPhan;
    }

    public int ThemNV(NhanVien nv) {
        int success = 0;
        String sql = "INSERT INTO nhanvien( TenNV, GioiTinh, NamSinh, SoDienThoai, Username, Password, Id_ChucVu, Id_BoPhan, Quyen) VALUES(?,?,?,?,?,?,?,?,?)";
        try {

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, nv.getUserName());
            ps.setInt(2, nv.getGioiTinh());
            ps.setString(3, nv.getNamSinh());
            ps.setString(4, nv.getSoDienThoai());
            ps.setString(5, nv.getUserName());
            ps.setString(6, nv.getPassword());
            ps.setInt(7, nv.getId_ChucVu());
            ps.setInt(8, nv.getId_BoPhan());
            ps.setInt(9, nv.getQuyen());

            success = ps.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
        return success;
    }

    public int SuaNV(int id, NhanVien nv) {
        int rowsUpdate = 0;

        String sql = "UPDATE nhanvien SET TenNV=?, GioiTinh=?, NamSinh=?, SoDienThoai=?, DiaChi=?, Username=?, Password=?, Id_ChucVu=?, Id_BoPhan=?, Quyen=? WHERE Id=?";
        try {

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, nv.getTenNV());
            ps.setInt(2, nv.getGioiTinh());
            ps.setString(3, nv.getNamSinh());
            ps.setString(4, nv.getSoDienThoai());
            ps.setString(5, nv.getDiaChi());
            ps.setString(6, nv.getUserName());
            ps.setString(7, nv.getPassword());
            ps.setInt(8, nv.getId_ChucVu());
            ps.setInt(9, nv.getId_BoPhan());
            ps.setInt(10, 0);
            ps.setInt(11, id);

            rowsUpdate = ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return rowsUpdate;
    }

    public int XoaNV(String id) {
        int success = 0;

        String sql = "DELETE FROM nhanvien WHERE ID=?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, id);

            success = ps.executeUpdate();

        } catch (Exception e) {
            System.out.println(e);
        }
        return success;
    }
    
    public ResultSet getBoPhan(){
        ResultSet rs = null;
        String sql = "SELECT TENBOPHAN,ID FROM BOPHAN";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
        } catch (Exception e) {
        }
        return rs;
    }
    
    public ResultSet getChucVu(){
        ResultSet rs = null;
        String sql = "SELECT TENCHUCVU,ID FROM CHUCVU";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
        } catch (Exception e) {
        }
        return rs;
    }

    public ResultSet GetIDBoPhan(String Ten) {
        ResultSet rs = null;
        String sql = "SELECT ID FROM BOPHAN WHERE TENBOPHAN=?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, Ten);
            rs = ps.executeQuery();

        } catch (Exception e) {
        }
        return rs;
    }

    public ResultSet GetIDChucVu(String Ten) {
        ResultSet rs = null;
        String sql = "SELECT ID FROM CHUCVU WHERE TENCHUCVU=?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, Ten);
            rs = ps.executeQuery();

        } catch (Exception e) {
        }
        return rs;
    }

    public boolean isSDTExists(String sdt) {
        boolean exists = false;
        try {
            String query = "SELECT COUNT(*) FROM nhanvien WHERE SoDienThoai = ?";
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

    public boolean checkUserName(String username) {
        boolean exists = false;
        try {
            String query = "SELECT COUNT(*) FROM nhanvien WHERE UserName = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, username);
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
    
    public ResultSet searchByName(String searchName) {
        ResultSet rs = null;
        try {
            String query = "SELECT * FROM nhanvien WHERE TenNV LIKE ?";
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1, "%" + searchName + "%");
            rs = preparedStatement.executeQuery();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return rs;
    }
}
