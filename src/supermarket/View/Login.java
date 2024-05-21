/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package supermarket.View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import supermarket.SetIcon;

/**
 *
 * @author ASUS
 */
public class Login extends JFrame implements ActionListener, ItemListener {

    private JButton btnLogin;
    private JTextField txtUserName;
    private JPasswordField txtPassword;
    private JLabel lbUserName, lbPassword, lbHienThi, lbThongTin;
    private JCheckBox ckHienThi;

    public Login() {
        setTitle("Đăng  nhập hệ thống");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        btnLogin = new JButton("Đăng nhập");
        txtUserName = new JTextField(15);
        txtPassword = new JPasswordField(15);
        lbUserName = new JLabel("Tên đăng nhập:");
        lbUserName.setForeground(Color.BLUE);
        lbPassword = new JLabel("Mật khẩu:");
        lbPassword.setForeground(Color.BLUE);
        lbHienThi = new JLabel("Hiển thị mật khẩu");
        lbHienThi.setForeground(Color.BLUE);

        String IconPath_info = "D:\\on_java\\Supermarket\\icon\\icon_info.png";

        ImageIcon IconInfo = SetIcon.SetSize(IconPath_info, 15, 15);
        lbThongTin = new JLabel("Thông tin đăng nhập", IconInfo, lbThongTin.LEFT);
        lbThongTin.setFont(new Font("Times News Roman", Font.PLAIN, 12));
        ckHienThi = new JCheckBox();

        String IconPath = "D:\\on_java\\Supermarket\\icon\\images_login1.png";
        ImageIcon IconLogin = SetIcon.SetSize(IconPath, 20, 20);
        btnLogin.setIcon(IconLogin);

        JPanel panel = new JPanel();
        SpringLayout layout = new SpringLayout();
        panel.setLayout(layout);

        panel.add(txtPassword);
        panel.add(txtUserName);
        panel.add(btnLogin);
        panel.add(lbHienThi);
        panel.add(lbPassword);
        panel.add(lbUserName);
        panel.add(lbThongTin);
        panel.add(ckHienThi);

        int labelWidth = 100;
        // Đặt kích thước cố định cho các label
        Dimension labelSize = new Dimension(labelWidth, lbUserName.getPreferredSize().height);
        lbUserName.setPreferredSize(labelSize);
        lbPassword.setPreferredSize(labelSize);

        layout.putConstraint(SpringLayout.WEST, lbThongTin, 5, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.WEST, lbUserName, 10, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, lbUserName, 10, SpringLayout.SOUTH, lbThongTin);
        layout.putConstraint(SpringLayout.WEST, txtUserName, 10, SpringLayout.EAST, lbUserName);
        layout.putConstraint(SpringLayout.NORTH, txtUserName, 10, SpringLayout.SOUTH, lbThongTin);

        layout.putConstraint(SpringLayout.WEST, lbPassword, 10, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, lbPassword, 10, SpringLayout.SOUTH, lbUserName);
        layout.putConstraint(SpringLayout.WEST, txtPassword, 10, SpringLayout.EAST, lbPassword);
        layout.putConstraint(SpringLayout.NORTH, txtPassword, 10, SpringLayout.SOUTH, txtUserName);

        layout.putConstraint(SpringLayout.WEST, ckHienThi, 90, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, ckHienThi, 10, SpringLayout.SOUTH, txtPassword);
        layout.putConstraint(SpringLayout.WEST, lbHienThi, 10, SpringLayout.EAST, ckHienThi);
        layout.putConstraint(SpringLayout.NORTH, lbHienThi, 10, SpringLayout.SOUTH, txtPassword);

        layout.putConstraint(SpringLayout.WEST, btnLogin, 90, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, btnLogin, 10, SpringLayout.SOUTH, lbHienThi);

        ckHienThi.addItemListener(this);
        
        setLocationRelativeTo(null);
        add(panel);
        setVisible(true);

    }

    public static void main(String args[]) {
        Login lg = new Login();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getSource() == ckHienThi) {
            if (ckHienThi.isSelected()) {
                // Hiển thị mật khẩu khi chọn ô hiển thị mật khẩu
                txtPassword.setEchoChar((char) 0); // Sử dụng kí tự null
            } else {
                // Che dấu mật khẩu khi bỏ chọn ô hiển thị mật khẩu
                txtPassword.setEchoChar('*'); // Sử dụng kí tự '*'
            }
        }
    }

    
}
