/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author ASUS
 */
public class PhanQuyenDAO {

    private Connection con;

    public PhanQuyenDAO() {
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

    public ResultSet getAccount() {
        ResultSet rs = null;
        String sql = "SELECT USERNAME FROM NHANVIEN";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return rs;
    }
    
    public ResultSet getQuyen(String username) {
        ResultSet rs = null;
        String sql = "SELECT QUYEN FROM NHANVIEN WHERE USERNAME=?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1,username);
            rs = ps.executeQuery();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return rs;
    }

    public int UpdateQuyen(String username, int quyen) {
        int success = 0;
        String sql = "UPDATE NHANVIEN SET QUYEN=? WHERE USERNAME=?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1,quyen);
            ps.setString(2, username);
            success = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return success;
    }
}
