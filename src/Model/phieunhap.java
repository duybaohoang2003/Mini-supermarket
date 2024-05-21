/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;
import java.util.Date;
/**
 *
 * @author DELL
 */
import java.util.Date;

public class phieunhap {
    private int id;
    private int idNhanVien;
    private int idNCC;
    private Date ngayNhap;
    private float tongTien;
    private int status;

    // Constructor
    public phieunhap(int id,int idNhanVien, int idNCC, Date ngayNhap, float tongTien, int status) {
        this.id=id;
        this.idNhanVien = idNhanVien;
        this.idNCC = idNCC;
        this.ngayNhap = ngayNhap;
        this.tongTien = tongTien;
        this.status = status;
    }
    public phieunhap(){
        
    }

    // Getter methods
    public int getId() {
        return id;
    }

    public int getIdNhanVien() {
        return idNhanVien;
    }

    public int getIdNCC() {
        return idNCC;
    }

    public Date getNgayNhap() {
        return ngayNhap;
    }

    public float getTongTien() {
        return tongTien;
    }
    
    public int getStatus() {
        return status;
    }

    // Setter methods (if needed)
    public void setId(int id) {
        this.id = id;
    }

    public void setIdNhanVien(int idNhanVien) {
        this.idNhanVien = idNhanVien;
    }

    public void setIdNCC(int idNCC) {
        this.idNCC = idNCC;
    }

    public void setNgayNhap(Date ngayNhap) {
        this.ngayNhap = ngayNhap;
    }

    public void setTongTien(float tongTien) {
        this.tongTien = tongTien;
    }
    
    public void setStatus(int status) {
        this.status = status;
    }

    // toString method
    @Override
    public String toString() {
        return "ID: " + id + ", Nhân viên: " + idNhanVien + ", Nhà cung cấp: " + idNCC + ", Ngày nhập: " + ngayNhap + ", Tổng tiền: " + tongTien;
    }
}

