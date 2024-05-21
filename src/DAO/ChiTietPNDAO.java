package Dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class ChiTietPNDAO {
    private Connection con;

    public ChiTietPNDAO() {
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

    public void displayChiTietPN(JTable table, DefaultTableModel model,int idPN) {
        try {
            String sql = "SELECT h.id, h.`TenHangHoa`, c.`DonGia`, c.`SoLuong` FROM `chitietphieunhap` c INNER JOIN `HangHoa` h ON c.`Id_HangHoa` = h.`Id` WHERE c.Id_PhieuNhap=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, idPN);
            ResultSet rs = ps.executeQuery();
            DecimalFormat decimalFormat = new DecimalFormat("#,##0");

            while (rs.next()) {
                Object[] rowData = new Object[5];
                String NameHangHoa = rs.getString("TenHangHoa");
                float donGia = rs.getFloat("DonGia");
                int soLuong = rs.getInt("SoLuong");
                double thanhTien = donGia * soLuong;
                rowData[0] = rs.getInt("id");
                rowData[1] = NameHangHoa;
                rowData[2] = decimalFormat.format(donGia); // Sửa lại đây để sử dụng biến donGia đã khai báo
                rowData[3] = decimalFormat.format(soLuong); // Sửa lại đây để sử dụng biến soLuong đã khai báo
                rowData[4] = decimalFormat.format(thanhTien);
                model.addRow(rowData);
            }

            table.setModel(model);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void xoaSanPham(int idHangHoa) {
        try {

            PreparedStatement selectPhieuNhap = con.prepareStatement("SELECT * FROM phieunhap WHERE status = 0");
            ResultSet fetchPhieuNhap = selectPhieuNhap.executeQuery();
            fetchPhieuNhap.next();
            // Xóa sản phẩm khỏi bảng chi tiết phiếu nhập
            PreparedStatement deleteCTPhieuNhap = con.prepareStatement("DELETE FROM chitietphieunhap WHERE Id_PhieuNhap=? AND Id_HangHoa=? and status=0");
            deleteCTPhieuNhap.setInt(1, fetchPhieuNhap.getInt("id"));
            deleteCTPhieuNhap.setInt(2, idHangHoa);
            int rowsAffected = deleteCTPhieuNhap.executeUpdate();

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "Xóa sản phẩm thành công");
            } else {
                JOptionPane.showMessageDialog(null, "Sản phẩm không tồn tại trong phiếu nhập");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void suaSanPham(int idHangHoa, int soluong, double dongia) {
        try {
            // Tắt autocommit trước khi thực hiện các thao tác trên cơ sở dữ liệu
            con.setAutoCommit(false);

            PreparedStatement selectPhieuNhap = con.prepareStatement("SELECT * FROM phieunhap WHERE status = 0");
            ResultSet fetchPhieuNhap = selectPhieuNhap.executeQuery();
            if (fetchPhieuNhap.next()) {
                int idPhieuNhap = fetchPhieuNhap.getInt("id");

                // Xóa sản phẩm khỏi bảng chi tiết phiếu nhập
                PreparedStatement updateCTPhieuNhap = con.prepareStatement("UPDATE chitietphieunhap SET DonGia=?, SoLuong=? WHERE Id_PhieuNhap=? AND Id_HangHoa=?");
                updateCTPhieuNhap.setDouble(1, dongia);
                updateCTPhieuNhap.setInt(2, soluong);
                updateCTPhieuNhap.setInt(3, idPhieuNhap);
                updateCTPhieuNhap.setInt(4, idHangHoa);
                int rowsAffected = updateCTPhieuNhap.executeUpdate();

                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(null, "Sửa sản phẩm thành công");
                } else {
                    JOptionPane.showMessageDialog(null, "Sản phẩm không tồn tại trong phiếu nhập");
                }

                con.commit(); // Commit thủ công sau mỗi thao tác thành công
            } else {
                JOptionPane.showMessageDialog(null, "Không có phiếu nhập nào có trạng thái chưa hoàn thành");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public float getTotalAmount() {
        float totalAmount = 0;
        String query = "SELECT SUM(DonGia * SoLuong) AS TongTien FROM chitietphieunhap WHERE Status = 0";

        try (PreparedStatement statement = con.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                totalAmount = resultSet.getFloat("TongTien");
            }
        } catch (SQLException e) {
            e.printStackTrace(); // hoặc xử lý ngoại lệ theo cách khác, ví dụ, thông báo lỗi
        }

        return totalAmount;
    }


    
}
