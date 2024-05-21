/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author ASUS
 */
public class HangHoa {
    private int Id;
    private int Id_NhomHang;
    private String TenHangHoa;
    private String DVT;
    private Float DonGia;
    
    public HangHoa(){
        
    }

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public int getId_NhomHang() {
        return Id_NhomHang;
    }

    public void setId_NhomHang(int Id_NhomHang) {
        this.Id_NhomHang = Id_NhomHang;
    }

    public String getTenHangHoa() {
        return TenHangHoa;
    }

    public void setTenHangHoa(String TenHangHoa) {
        this.TenHangHoa = TenHangHoa;
    }

    public String getDVT() {
        return DVT;
    }

    public void setDVT(String DVT) {
        this.DVT = DVT;
    }

    public Float getDonGia() {
        return DonGia;
    }

    public void setDonGia(Float DonGia) {
        this.DonGia = DonGia;
    }
    
}
