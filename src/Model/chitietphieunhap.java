/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author DELL
 */
public class chitietphieunhap {
    private int idPhieuNhap;
    private int idHangHoa;
    private float donGia;
    private int soLuong;
    private int chietKhau;
    private int status;

    public chitietphieunhap() {
    }

    public chitietphieunhap(int idPhieuNhap, int idHangHoa, float donGia, int soLuong, int chietKhau, int status) {
        this.idPhieuNhap = idPhieuNhap;
        this.idHangHoa = idHangHoa;
        this.donGia = donGia;
        this.soLuong = soLuong;
        this.chietKhau = chietKhau;
        this.status = status;
    }

    public int getIdPhieuNhap() {
        return idPhieuNhap;
    }

    public void setIdPhieuNhap(int idPhieuNhap) {
        this.idPhieuNhap = idPhieuNhap;
    }

    public int getIdHangHoa() {
        return idHangHoa;
    }

    public void setIdHangHoa(int idHangHoa) {
        this.idHangHoa = idHangHoa;
    }

    public float getDonGia() {
        return donGia;
    }

    public void setDonGia(float donGia) {
        this.donGia = donGia;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public int getChietKhau() {
        return chietKhau;
    }

    public void setChietKhau(int chietKhau) {
        this.chietKhau = chietKhau;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
