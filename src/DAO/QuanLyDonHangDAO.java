/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Date;

/**
 *
 * @author ASUS
 */
public class QuanLyDonHangDAO {

    private Connection con;

    public QuanLyDonHangDAO() {
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

    public ResultSet GetDonHang() {
        ResultSet rs = null;
        String sql = "SELECT K.TENKHACHHANG, N.TENNV, P.TONGTIEN, P.NGAYXUAT, P.ID FROM PHIEUXUAT AS P, KHACHHANG AS K, NHANVIEN AS N WHERE P.STATUS=0 AND P.ID_KHACHHANG=K.ID AND P.ID_NHANVIEN=N.ID ORDER BY P.ID ASC";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rs;
    }

    public ResultSet GetSanPham(int id) {
        ResultSet rs = null;
        String sql = "SELECT H.TENHANGHOA, CP.SOLUONG, CP.DONGIA FROM chitietphieuxuat AS CP, HANGHOA AS H WHERE ID_PHIEUXUAT =? AND H.Id=CP.Id_HangHoa";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rs;
    }

    public ResultSet SelectTheoNgay(Date tungay, Date DenNgay) {
        ResultSet rs = null;
        String sql = "SELECT K.TENKHACHHANG, N.TENNV, P.TONGTIEN, P.NGAYXUAT, P.ID FROM PHIEUXUAT AS P, KHACHHANG AS K, NHANVIEN AS N WHERE P.STATUS=0 AND P.ID_KHACHHANG=K.ID AND P.ID_NHANVIEN=N.ID AND NGAYXUAT BETWEEN ? AND ? ORDER BY P.ID ASC ";
        try {

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setDate(1, tungay);
            ps.setDate(2, DenNgay);
            rs = ps.executeQuery();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return rs;
    }

    public int XoaDonHang(int id) {
        int success = 0;
        String sql = "DELETE FROM PHIEUXUAT WHERE ID=?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            success = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return success;
    }

    public ResultSet TimKiem(String name) {
        ResultSet rs = null;
        String sql = "SELECT K.TENKHACHHANG, N.TENNV, P.TONGTIEN, P.NGAYXUAT, P.ID FROM PHIEUXUAT AS P, KHACHHANG AS K, NHANVIEN AS N WHERE P.STATUS=0 AND P.ID_KHACHHANG=K.ID AND P.ID_NHANVIEN=N.ID AND K.TENKHACHHANG LIKE ? ORDER BY P.ID ASC";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, "%" + name + "%");
            rs = ps.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rs;
    }

}
