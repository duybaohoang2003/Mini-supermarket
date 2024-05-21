/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author DELL
 */
public class NhaCungCap {
    private int id;
    private String TenNCC;
    private String DiaChiNCC;
    private String DienThoaiNCC;

    public NhaCungCap( String TenNCC, String DiaChiNCC, String DienThoaiNCC) {
        this.TenNCC = TenNCC;
        this.DiaChiNCC = DiaChiNCC;
        this.DienThoaiNCC = DienThoaiNCC;
    }
    public NhaCungCap(){
        
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenNCC() {
        return TenNCC;
    }

    public void setTenNCC(String TenNCC) {
        this.TenNCC = TenNCC;
    }

    public String getDiaChiNCC() {
        return DiaChiNCC;
    }

    public void setDiaChiNCC(String DiaChiNCC) {
        this.DiaChiNCC = DiaChiNCC;
    }

    public String getDienThoaiNCC() {
        return DienThoaiNCC;
    }

    public void setDienThoaiNCC(String DienThoaiNCC) {
        this.DienThoaiNCC = DienThoaiNCC;
    }
}
