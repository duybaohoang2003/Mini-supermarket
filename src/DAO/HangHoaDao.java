/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dao;
import Model.HangHoa;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author DELL
 */
public class HangHoaDao {
    private Connection con;

    public HangHoaDao() {
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

    public void displayHangHoaTable(JTable table,String Tenhanghoa,String Nhomhang) {
        try {
            String sql = "SELECT * FROM `hanghoa` WHERE 1 ";
            String[] Tenhanghoas = Tenhanghoa.split(" ");
            if (!Tenhanghoa.isEmpty()) {
                for (String term : Tenhanghoas) {
                    sql += "AND TenHangHoa LIKE '%" + term + "%' ";
                }
            }
            if(!Nhomhang.isEmpty()){
                sql="SELECT * FROM `hanghoa`,nhomhang WHERE hanghoa.Id_NhomHang=nhomhang.Id and nhomhang.TenNhomHang=?";
            }
            PreparedStatement ps = con.prepareStatement(sql);
            if(!Nhomhang.isEmpty()){
                ps.setString(1, Nhomhang);
            }
            ResultSet rs = ps.executeQuery();
            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("Id");
            model.addColumn("Id_NhomHang");
            model.addColumn("TenHangHoa");
            model.addColumn("DVT");
            model.addColumn("DonGia");

            while (rs.next()) {
                Object[] rowData = new Object[5];
                rowData[0] = rs.getInt("Id");
                rowData[1] = rs.getInt("Id_NhomHang");
                rowData[2] = rs.getString("TenHangHoa");
                rowData[3] = rs.getString("DVT");
                rowData[4] = rs.getDouble("DonGia");
                model.addRow(rowData);
            }

            table.setModel(model);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void ThemHangHoa(HangHoa hanghoa) {
        try {
            // Thêm hàng hóa
            PreparedStatement insertHangHoa = con.prepareStatement("INSERT INTO `hanghoa`(`Id_NhomHang`, `TenHangHoa`, `DVT`, `DonGia`) VALUES ( ?, ?, ?, ?)");
            insertHangHoa.setInt(1, hanghoa.getId_NhomHang());
            insertHangHoa.setString(2, hanghoa.getTenHangHoa());
            insertHangHoa.setString(3, hanghoa.getDVT());
            insertHangHoa.setDouble(4, hanghoa.getDonGia());

            int rowsAffected = insertHangHoa.executeUpdate();

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "Thêm hàng hóa thành công");
            } else {
                JOptionPane.showMessageDialog(null, "Thêm hàng hóa không thành công");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
    public String SelectTenNhomHang(int id) {
        String IdNCC = ""; // Khởi tạo IdPhieu với giá trị mặc định

        try {
            String sql = "SELECT `TenNhomHang` FROM `nhomhang` WHERE id=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            // Kiểm tra xem ResultSet có kết quả không
            if (rs.next()) {
                // Trích xuất giá trị Id từ ResultSet
                IdNCC = rs.getString("TenNhomHang");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }

        return IdNCC; // Trả về IdPhieu
    }
    
    public int SelectidNhomHang(String id) {
        int IdNCC = -1; // Khởi tạo IdPhieu với giá trị mặc định

        try {
            String sql = "SELECT `id` FROM `nhomhang` WHERE TenNhomHang=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();

            // Kiểm tra xem ResultSet có kết quả không
            if (rs.next()) {
                // Trích xuất giá trị Id từ ResultSet
                IdNCC = rs.getInt("id");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }

        return IdNCC; // Trả về IdPhieu
    }
    public void suaHangHoa(HangHoa hanghoa) {
        try {
            // Cập nhật hàng hóa
            PreparedStatement updateHangHoa = con.prepareStatement("UPDATE `hanghoa` SET `Id_NhomHang`=?, `TenHangHoa`=?, `DVT`=?, `DonGia`=? WHERE `Id`=?");
            updateHangHoa.setInt(1, hanghoa.getId_NhomHang());
            updateHangHoa.setString(2, hanghoa.getTenHangHoa());
            updateHangHoa.setString(3, hanghoa.getDVT());
            updateHangHoa.setFloat(4, hanghoa.getDonGia());
            updateHangHoa.setInt(5, hanghoa.getId());

            int rowsAffected = updateHangHoa.executeUpdate();

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "Cập nhật thông tin hàng hóa thành công");
            } else {
                JOptionPane.showMessageDialog(null, "Cập nhật thông tin hàng hóa không thành công");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
