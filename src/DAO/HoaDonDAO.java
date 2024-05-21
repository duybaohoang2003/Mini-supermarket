/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author ASUS
 */
public class HoaDonDAO {

    private Connection con;

    public HoaDonDAO() {
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

    public ResultSet GetHoaDon(int id) {
        ResultSet rs = null;
        String sql = "SELECT ID_KHACHHANG, ID_NHANVIEN, NGAYXUAT, TONGTIEN FROM PHIEUXUAT WHERE ID=?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            rs=ps.executeQuery();
        } catch (Exception e) {
        }
        return rs;
    }
    
    public ResultSet getInfoKH(int id) {
        ResultSet rs = null;

        String sql = "SELECT TENKHACHHANG,SODIENTHOAI, DIACHI FROM KHACHHANG WHERE ID=?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
        } catch (Exception e) {
        }
        return rs;
    }
    public ResultSet getNhanSu(int id) {
        ResultSet rs = null;
        String sql = "SELECT TENNV FROM NHANVIEN WHERE ID=?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
        } catch (Exception e) {
        }
        return rs;
    }
    
    public ResultSet GetHangHoa(int id){
        ResultSet rs = null;
        String sql = "SELECT H.TENHANGHOA, CP.SOLUONG, CP.DONGIA FROM chitietphieuxuat AS CP, HANGHOA AS H WHERE ID_PHIEUXUAT =? AND H.Id=CP.Id_HangHoa";
        try{
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            rs=ps.executeQuery();
        }catch(Exception e){
            e.printStackTrace();
        }
        return rs;
    }
}
