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

/**
 *
 * @author DELL
 */
public class NhaCungCapDAO {
    private Connection con;

    public NhaCungCapDAO() {
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

    public void ThemNhaCungCap(NhaCungCap nhacungcap) {
        try {
            // Thêm nhà cung cấp
            PreparedStatement insertNhaCungCap = con.prepareStatement("INSERT INTO `nhacungcap`(`TenNCC`, `DiaChiNCC`, `DienThoaiNCC`) VALUES ( ?, ?, ?)");
            insertNhaCungCap.setString(1, nhacungcap.getTenNCC());
            insertNhaCungCap.setString(2, nhacungcap.getDiaChiNCC());
            insertNhaCungCap.setString(3, nhacungcap.getDienThoaiNCC());

            int rowsAffected = insertNhaCungCap.executeUpdate();

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "Thêm nhà cung cấp thành công");
            } else {
                JOptionPane.showMessageDialog(null, "Thêm nhà cung cấp không thành công");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void suaNCC(NhaCungCap nhacungcap) {
        try {
            // Cập nhật nhà cung cấp
            PreparedStatement updateNhaCungCap = con.prepareStatement("UPDATE `nhacungcap` SET `TenNCC`=?, `DiaChiNCC`=?, `DienThoaiNCC`=? WHERE `Id`=?");
            updateNhaCungCap.setString(1, nhacungcap.getTenNCC());
            updateNhaCungCap.setString(2, nhacungcap.getDiaChiNCC());
            updateNhaCungCap.setString(3, nhacungcap.getDienThoaiNCC());
            updateNhaCungCap.setInt(4, nhacungcap.getId());

            int rowsAffected = updateNhaCungCap.executeUpdate();

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "Cập nhật thông tin nhà cung cấp thành công");
            } else {
                JOptionPane.showMessageDialog(null, "Cập nhật thông tin nhà cung cấp không thành công");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
