/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import Model.NhaCungCap;
import Model.NhomHang;

/**
 *
 * @author DELL
 */
public class NhomHangDAO {
    private Connection con;

    public NhomHangDAO() {
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
    
    
    public void ThemNhomHang(NhomHang nhomHang) {
        try {
            // Thêm nhóm hàng
            PreparedStatement insertNhomHang = con.prepareStatement("INSERT INTO `nhomhang`(`TenNhomHang`) VALUES (?)");
            insertNhomHang.setString(1, nhomHang.getTenNhomHang());

            int rowsAffected = insertNhomHang.executeUpdate();

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "Thêm nhóm hàng thành công");
            } else {
                JOptionPane.showMessageDialog(null, "Thêm nhóm hàng không thành công");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void suaNhomHang(NhomHang nhomHang) {
        try {
            // Cập nhật nhóm hàng
            PreparedStatement updateNhomHang = con.prepareStatement("UPDATE `nhomhang` SET `TenNhomHang`=? WHERE `Id`=?");
            updateNhomHang.setString(1, nhomHang.getTenNhomHang());
            updateNhomHang.setInt(2, nhomHang.getId());

            int rowsAffected = updateNhomHang.executeUpdate();

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "Cập nhật thông tin nhóm hàng thành công");
            } else {
                JOptionPane.showMessageDialog(null, "Cập nhật thông tin nhóm hàng không thành công");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void xoaNhomHang(int id) {
        try {
            // Xóa nhóm hàng
            PreparedStatement deleteNhomHang = con.prepareStatement("DELETE FROM `nhomhang` WHERE `Id`=?");
            deleteNhomHang.setInt(1, id);

            int rowsAffected = deleteNhomHang.executeUpdate();

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "Xóa nhóm hàng thành công");
            } else {
                JOptionPane.showMessageDialog(null, "Xóa nhóm hàng không thành công");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    


}
