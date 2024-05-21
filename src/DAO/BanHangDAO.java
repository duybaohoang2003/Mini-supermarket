/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 *
 * @author ASUS
 */
public class BanHangDAO {

    private Connection con;

    public BanHangDAO() {
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

    public ResultSet getTenKH() {
        ResultSet rs = null;

        String sql = "SELECT TENKHACHHANG,ID FROM KHACHHANG";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return rs;
    }

    public ResultSet getNhanSu() {
        ResultSet rs = null;
        String sql = "SELECT TENNV,ID FROM NHANVIEN WHERE ID_BOPHAN=1";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
        } catch (Exception e) {
        }
        return rs;
    }

    public ResultSet getMaHang() {
        ResultSet rs = null;
        String sql = "SELECT ID FROM HANGHOA ORDER BY ID ASC";
        try {
            PreparedStatement ps = con.prepareStatement(sql);

            rs = ps.executeQuery();

        } catch (Exception e) {
        }
        return rs;
    }

    public ResultSet getInfoProduct(int id) {
        ResultSet rs = null;
        String sql = "SELECT TENHANGHOA, DONGIA, SOLUONG FROM HANGHOA WHERE ID=?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
        } catch (Exception e) {
        }
        return rs;
    }

    public ResultSet getInfoKH(String Name) {
        ResultSet rs = null;

        String sql = "SELECT ID,SODIENTHOAI, DIACHI FROM KHACHHANG WHERE TENKHACHHANG=?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, Name);
            rs = ps.executeQuery();
        } catch (Exception e) {
        }

        return rs;
    }

    public ResultSet getInfoNS(String Name) {
        ResultSet rs = null;
        String sql = "SELECT ID FROM NHANVIEN WHERE TENNV=?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, Name);
            rs = ps.executeQuery();
        } catch (Exception e) {
        }
        return rs;
    }

    //insert table chitietphieuxuat
    public int ThemCTPX(int idhh, int sl, float dongia) {
        int success = 0;
        int IDPX = 0;
        ThemPhieuXuat();
        String sqlId = "SELECT ID FROM PHIEUXUAT WHERE STATUS=1";
        try {
            PreparedStatement ps = con.prepareStatement(sqlId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                IDPX = rs.getInt("ID");
            }
        } catch (Exception e) {
        }
        //thực hiện thêm vào bảng chi tiết phiếu xuất
        String sql = "INSERT INTO CHITIETPHIEUXUAT(ID_PHIEUXUAT, ID_HANGHOA, SOLUONG, DONGIA,STATUS) VALUES(?,?,?,?,1)";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, IDPX);
            ps.setInt(2, idhh);
            ps.setInt(3, sl);
            ps.setFloat(4, dongia);
            success = ps.executeUpdate();

        } catch (Exception e) {
        }
        return success;
    }

    public int SuaCTPX(int idhh, int sl) {
        int success = 0;
        String sql = "UPDATE CHITIETPHIEUXUAT SET SOLUONG=? WHERE ID_HANGHOA=?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, sl);
            ps.setInt(2, idhh);
            success = ps.executeUpdate();
        } catch (Exception e) {
        }
        return success;
    }

    public int XoaCTPX(int idhh) {
        int success = 0;
        String sql = "DELETE FROM CHITIETPHIEUXUAT WHERE ID_HANGHOA=? AND STATUS=1";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, idhh);
            success = ps.executeUpdate();
        } catch (Exception e) {
        }
        return success;
    }

    public void ThemPhieuXuat() {
        ResultSet ketqua;
        //QUY ƯỚC STATUs = 0 TỨC LÀ PHIẾU XUẤT ĐÃ HOÀN THÀNH, STATUS = 1 LÀ PHIẾU XUẤT VẪN ĐANG ĐƯỢC XỬ LÝ
        String kiemtra = "SELECT * FROM PHIEUXUAT WHERE STATUS = 1";
        try {
            PreparedStatement ps = con.prepareStatement(kiemtra);
            ketqua = ps.executeQuery();
            if (!ketqua.next()) {//tức là không có phiếu xuất nào đang được xử lý
                String sql = "INSERT INTO phieuxuat(Id_NhanVien, Id_KhachHang, NgayXuat, TongTien, STATUS) VALUES (1,1,?,0,1)";
                try {
                    PreparedStatement pss = con.prepareStatement(sql);
                    //lấy ngày xuất là ngày tháng năm hiện tại của hệ thống
                    LocalDateTime currentDate = LocalDateTime.now();
                    int year = currentDate.getYear();
                    int month = currentDate.getMonthValue();
                    int day = currentDate.getDayOfMonth();
                    int hour = currentDate.getHour();
                    int minute = currentDate.getMinute();
                    int second = currentDate.getSecond();

                    String formattedDate = String.format("%d-%02d-%02d %02d:%02d:%02d", year, month, day, hour, minute, second);
                    //thêm ngày xuất
                    pss.setString(1, formattedDate);
                    pss.executeUpdate();

                } catch (Exception e) {
                }
            }
        } catch (Exception e) {
        }
    }

    public int LuuPhieuXuat(int idnv, int idkh, float tong) {
        int success = 0;
        String sql = "UPDATE PHIEUXUAT SET ID_NHANVIEN=?, ID_KHACHHANG=?, TONGTIEN=?, STATUS =0 WHERE STATUS=1";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, idnv);
            ps.setInt(2, idkh);
            ps.setFloat(3, tong);
            success = ps.executeUpdate();
            String sql1 = "UPDATE CHITIETPHIEUXUAT SET STATUS = 0 WHERE STATUS=1";
            try {
                PreparedStatement pss = con.prepareStatement(sql1);
                pss.executeUpdate();
            } catch (Exception e) {
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return success;
    }

    public void XoaPhieuXuat() {
        String sql = "DELETE FROM PHIEUXUAT WHERE STATUS=1";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.executeUpdate();
            String sql1 = "DELETE FROM CHITIETPHIEUXUAT WHERE STATUS=1";
            try {
                PreparedStatement pss = con.prepareStatement(sql1);
                pss.executeUpdate();
            } catch (Exception e) {
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    //LẤY ID PHIẾU XUẤT ĐỂ IN HÓA ĐƠN
    public ResultSet getIDPX(){
        ResultSet rs=null;
        String sql = "SELECT ID FROM PHIEUXUAT WHERE STATUS =1";
        try{
            PreparedStatement ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
        }catch(Exception e){}
        return rs;
    }

}
