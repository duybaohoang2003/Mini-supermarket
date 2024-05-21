/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package supermarket.View;

/**
 *
 * @author Bao Hoang
 */
import DAO.LoginDAO;
import Model.LoginView;
import com.sun.tools.javac.Main;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;
import javax.swing.ImageIcon;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import supermarket.SetIcon;

public class LoginFRM extends JFrame implements ActionListener {

    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin;
    private LoginView model;
    private JPanel contentPanel;
    private JCheckBox chkShowPassword; // Thêm checkbox để hiện mật khẩu

    public LoginFRM() {
        super("Login Supermarket");
        txtUsername = new JTextField(20);
        txtPassword = new JPasswordField(20);
        txtPassword.setEchoChar('*');
        btnLogin = new JButton("Login");
        chkShowPassword = new JCheckBox("Show Password");

        // Tạo 1 backgroung panel tên là content
        BackgroundPanel content = new BackgroundPanel("super.jpg");
        content.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Tăng khoảng cách giữa các thành phần

//         Thay đổi màu sắc và font chữ
        content.setBackground(new Color(230, 230, 250)); // Màu lavender
        txtUsername.setFont(new Font("Arial", Font.PLAIN, 14));
        txtPassword.setFont(new Font("Arial", Font.PLAIN, 14));
        btnLogin.setFont(new Font("Arial", Font.BOLD, 16));
        btnLogin.setBackground(new Color(70, 130, 180)); // Màu steel blue
        btnLogin.setForeground(Color.WHITE);

        // tiêu đề của login
        JLabel titleLabel = new JLabel("Welcome Supermarket");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24)); // Thiết lập font chữ và kích thước
        titleLabel.setForeground(Color.BLACK); // Thiết lập màu chữ
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER; // Căn giữa theo chiều ngang
        content.add(titleLabel, gbc);

        String PathUser = "D:\\on_java\\Supermarket\\icon\\user.png";
        ImageIcon IconUser = SetIcon.SetSize(PathUser, 20, 20);
        JLabel userLabel = new JLabel("Username:", IconUser, JLabel.LEFT);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridwidth = 1; // chỉ chiếm 1 cột

        content.add(userLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        content.add(txtUsername, gbc);

        // Thay đổi kích thước của password
        String PathPassword = "D:\\on_java\\Supermarket\\icon\\pass.png";
        ImageIcon IconPassword = SetIcon.SetSize(PathPassword, 20, 20);
        JLabel passwordLabel = new JLabel("Password:", IconPassword, JLabel.LEFT);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridwidth = 1; // chỉ chiếm 1 cột
        content.add(passwordLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        content.add(txtPassword, gbc);

        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        content.add(chkShowPassword, gbc);

        // Thêm ActionListener cho checkbox
        chkShowPassword.addActionListener(this);
        
        String PathLogin = "D:\\on_java\\Supermarket\\icon\\login.png";
        ImageIcon IconLogin = SetIcon.SetSize(PathLogin, 20, 20);;

        btnLogin.setIcon(IconLogin);
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.CENTER;
        content.add(btnLogin, gbc);

        this.setContentPane(content);
        this.setSize(600, 300);
        // can giua man hinh
        this.setLocationRelativeTo(null);
//        this.pack();

        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == chkShowPassword) {
            if (chkShowPassword.isSelected()) {
                txtPassword.setEchoChar((char) 0);
            } else {
                txtPassword.setEchoChar('*');
            }
        }
    }

    // lấy thông tin đăng nhập từ giao diện
    public LoginView getUser() {
        model = new LoginView(txtUsername.getText(), new String(txtPassword.getPassword()));
        return model;
    }

    public void showMessage(String msg) {
        JOptionPane.showMessageDialog(this, msg);
    }

    public void addLoginListener(ActionListener log) {
        btnLogin.addActionListener(log);
    }

    public static void main(String args[]) {
        LoginFRM lg = new LoginFRM();
        LoginDAO controller = new LoginDAO(lg);
        lg.setVisible(true);
    }
}
