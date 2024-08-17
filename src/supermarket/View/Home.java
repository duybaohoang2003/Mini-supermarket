/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package supermarket.View;

import DAO.LoginDAO;
import DAO.PhanQuyenDAO;
import Model.SessionManager;
import assets.SpringUtilities;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import supermarket.SetIcon;

/**
 *
 * @author ASUS
 */
public class Home extends JFrame {

    private JButton btnNhapKho, btnPhanQuyen, btnBanHang;
    private JPanel tabPanel, contentPanel;
    private JComboBox<String> cbQuanLy, cbDangXuat;
    private PhanQuyenDAO p = new PhanQuyenDAO();

    public Home() {
        setTitle("Siêu thị mini");
        setSize(1190, 1000);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        String PathQuanLy = "D:\\on_java\\Supermarket\\icon\\quanly.png";
        String PathBanHang = "D:\\on_java\\Supermarket\\icon\\banhang.png";
        String PathNhapKho = "D:\\on_java\\Supermarket\\icon\\nhapkho.png";
        String PathBaoCao = "D:\\on_java\\Supermarket\\icon\\baocao.png";
        String PathPhanQuyen = "D:\\on_java\\Supermarket\\icon\\phanquyen.png";

        ImageIcon IconQuanLy = SetIcon.SetSize(PathQuanLy, 20, 20);
        ImageIcon IconBanHang = SetIcon.SetSize(PathBanHang, 20, 20);
        ImageIcon IconNhapKho = SetIcon.SetSize(PathNhapKho, 20, 20);
        ImageIcon IconBaoCao = SetIcon.SetSize(PathBaoCao, 20, 20);
        ImageIcon IconPhanQuyen = SetIcon.SetSize(PathPhanQuyen, 20, 20);

        // Tạo các button trên thanh tab
        btnBanHang = new JButton("Bán hàng - hóa đơn");
        btnPhanQuyen = new JButton("Phân quyền");
        btnNhapKho = new JButton("Nhập kho");

        cbQuanLy = new JComboBox<>();
        cbQuanLy.setPreferredSize(new Dimension(200, 30));
        cbQuanLy.addItem("Quản lý nhân viên");
        cbQuanLy.addItem("Quản lý hàng hóa");
        cbQuanLy.addItem("Quản lý đơn hàng");
        cbQuanLy.addItem("Quản lý khách hàng");
        cbQuanLy.addItem("Quản lý nhập kho");
        cbQuanLy.addItem("Quản lý nhà cung cấp");

        cbDangXuat = new JComboBox<>();
        cbDangXuat.setPreferredSize(new Dimension(150, 20));
        if (SessionManager.getUserName() == null) {
            cbQuanLy.setEnabled(false);
            btnBanHang.setEnabled(false);
            btnNhapKho.setEnabled(false);
            btnPhanQuyen.setEnabled(false);
            dispose();
            cbDangXuat.addItem("Đăng nhập");
        } else {
            int quyen = -1;
            ResultSet rs = p.getQuyen(SessionManager.getUserName());
            try {
                if (rs.next()) {
                    quyen = rs.getInt("QUYEN");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            if (quyen == 1) {
                cbQuanLy.setEnabled(true);
                btnBanHang.setEnabled(true);
                btnNhapKho.setEnabled(true);
                btnPhanQuyen.setEnabled(true);
                cbDangXuat.addItem("Xin chào," + SessionManager.getUserName());
                cbDangXuat.addItem("Đăng xuất");
            } else {
                cbQuanLy.setEnabled(false);
                btnBanHang.setEnabled(true);
                btnNhapKho.setEnabled(true);
                btnPhanQuyen.setEnabled(false);
                cbDangXuat.addItem("Xin chào," + SessionManager.getUserName());
                cbDangXuat.addItem("Đăng xuất");
            }

        }

        //btnQuanLy.setIcon(IconQuanLy);
        btnBanHang.setIcon(IconBanHang);
        btnNhapKho.setIcon(IconNhapKho);
        btnPhanQuyen.setIcon(IconPhanQuyen);

        // Tạo panel cho thanh tab
        tabPanel = new JPanel();
        tabPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        tabPanel.add(cbQuanLy);
        tabPanel.add(btnBanHang);
        tabPanel.add(btnPhanQuyen);
        tabPanel.add(btnNhapKho);
        JLabel kc = new JLabel();
        kc.setPreferredSize(new Dimension(370, 1));
        tabPanel.add(kc);
        tabPanel.add(cbDangXuat);

        cbQuanLy.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selectedOption = (String) cbQuanLy.getSelectedItem();
                if (selectedOption.equals("Quản lý nhân viên")) {
                    showContent(new NhanVienFRM());
                } else if (selectedOption.equals("Quản lý hàng hóa")) {
                    showContent(new form());
                } else if (selectedOption.equals("Quản lý đơn hàng")) {
                    showContent(new QuanLyDonHang());
                } else if (selectedOption.equals("Quản lý khách hàng")) {
                    showContent(new KhachHangFRM());
                } else if (selectedOption.equals("Quản lý nhập kho")) {
                    showContent(new GUIphieunhap());
                } else {
                    showContent(new nhacungcap());
                }
            }
        });

        cbDangXuat.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selectedOption = (String) cbDangXuat.getSelectedItem();
                if (selectedOption.equals("Đăng xuất")) {
                    DangXuat();
                } else if (selectedOption.equals("Đăng nhập")) {
                    LoginFRM loginForm = new LoginFRM(); // Tạo và hiển thị form đăng nhập
                    LoginDAO controller = new LoginDAO(loginForm);
                    loginForm.setVisible(true);
                }
            }
        });
        contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());

        // Thêm sự kiện cho các button trên thanh tab  
        btnNhapKho.addActionListener(e -> showContent(new NhapPhieu()));
        btnBanHang.addActionListener(e -> showContent(new BanHang()));
        btnPhanQuyen.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Hiển thị form phân quyền và đặt vị trí ở giữa màn hình
                PhanQuyen();
            }
        });

        // Thêm thanh tab và panel nội dung vào frame
        add(tabPanel, BorderLayout.NORTH);
        add(contentPanel, BorderLayout.CENTER);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void showContent(JPanel panel) {
        contentPanel.removeAll();
        contentPanel.setLayout(new BorderLayout());
        contentPanel.add(panel, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    public void DangXuat() {
        // Thêm mã xử lý để đăng xuất và quay lại màn hình đăng nhập ở đây
        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn đăng xuất ?", "Xác nhận đăng xuất", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            SessionManager.clearSession();
            dispose(); // Đóng frame hiện tại
            LoginFRM loginForm = new LoginFRM(); // Tạo và hiển thị form đăng nhập
            LoginDAO controller = new LoginDAO(loginForm);
            loginForm.setVisible(true);
        }
    }

    public void PhanQuyen() {
        PhanQuyen p = new PhanQuyen();
        p.setLocationRelativeTo(null);
    }

    public static void main(String args[]) {
        Home home = new Home();
    }
}
