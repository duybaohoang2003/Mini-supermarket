/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author ASUS
 */
public class NhanVien {
    private int Id;
    private int Id_ChucVu;
    private int Id_BoPhan;
    private String TenNV;
    private int GioiTinh;
    private String NamSinh;
    private String SoDienThoai;
    private String DiaChi;
    private String UserName;
    private String Password;
    private int Quyen;
    
    public NhanVien(){
    
    }

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public int getId_ChucVu() {
        return Id_ChucVu;
    }

    public void setId_ChucVu(int Id_ChucVu) {
        this.Id_ChucVu = Id_ChucVu;
    }

    public int getId_BoPhan() {
        return Id_BoPhan;
    }

    public void setId_BoPhan(int Id_BoPhan) {
        this.Id_BoPhan = Id_BoPhan;
    }

    public String getTenNV() {
        return TenNV;
    }

    public void setTenNV(String TenNV) {
        this.TenNV = TenNV;
    }

    public int getGioiTinh() {
        return GioiTinh;
    }

    public void setGioiTinh(int GioiTinh) {
        this.GioiTinh = GioiTinh;
    }

    public String getNamSinh() {
        return NamSinh;
    }

    public void setNamSinh(String NamSinh) {
        this.NamSinh = NamSinh;
    }

    public String getSoDienThoai() {
        return SoDienThoai;
    }

    public void setSoDienThoai(String SoDienThoai) {
        this.SoDienThoai = SoDienThoai;
    }

    public String getDiaChi() {
        return DiaChi;
    }

    public void setDiaChi(String DiaChi) {
        this.DiaChi = DiaChi;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String UserName) {
        this.UserName = UserName;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }

    public int getQuyen() {
        return Quyen;
    }

    public void setQuyen(int Quyen) {
        this.Quyen = Quyen;
    }
    public String toString(){
        return TenNV;
    }
    
}
