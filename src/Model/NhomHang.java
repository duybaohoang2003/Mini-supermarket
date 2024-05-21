/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author DELL
 */
public class NhomHang {
    private int id;
    private String TenNhomHang;

    public NhomHang(int id, String TenNhomHang) {
        this.id = id;
        this.TenNhomHang = TenNhomHang;
    }
    public NhomHang(){
        
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenNhomHang() {
        return TenNhomHang;
    }

    public void setTenNhomHang(String TenNhomHang) {
        this.TenNhomHang = TenNhomHang;
    }
    
    
}
