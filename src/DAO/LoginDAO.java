package DAO;

import Model.LoginView;
import Model.SessionManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import supermarket.View.Home;
import supermarket.View.LoginFRM;

public class LoginDAO {

    private LoginView model;
    private LoginFRM view;

    public LoginDAO(LoginFRM view) {
        this.view = view;
        view.addLoginListener(new LoginListener());
    }

    class LoginListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            try {
                model = view.getUser();
                if (checkUser(model)) {
                    // Lấy thông tin người dùng từ cơ sở dữ liệu
                    String userId = getUserIdFromDatabase(model.getUserName());
                    String username = model.getUserName();
                    
                    // Lưu thông tin người dùng vào session
                    SessionManager.setUserInfo(userId, username);
                    
                    // Chuyển đến trang chính
                    new Home();
                    view.dispose();
                } else {
                    view.showMessage("Không tồn tại tài khoản hoặc mật khẩu không đúng! Hãy thử lại");
                }
            } catch (Exception ex) {
                // Log or display a clear error message
                view.showMessage("An error occurred: " + ex.getMessage());
            }
        }
    }

    public boolean checkUser(LoginView user) throws Exception {
        
        // ket noi csdl
        String url = "jdbc:mysql://localhost:3306/test_supermarket";
        String dbClass = "com.mysql.cj.jdbc.Driver";
        String query = "SELECT * FROM nhanvien WHERE username = ? AND password = ?";

        try {
            Class.forName(dbClass);
            try (Connection con = DriverManager.getConnection(url, "root", "");
                 PreparedStatement pstmt = con.prepareStatement(query)) {

                pstmt.setString(1, user.getUserName());
                pstmt.setString(2, user.getPassword());

                try (ResultSet rs = pstmt.executeQuery()) {
                    return rs.next();
                }
            }
        } catch (Exception e) {
            throw e;
        }
    }
    
    private String getUserIdFromDatabase(String username) throws Exception {
        String url = "jdbc:mysql://localhost:3306/test_supermarket";
        String dbClass = "com.mysql.cj.jdbc.Driver";
        String query = "SELECT id FROM nhanvien WHERE username = ?";

        try {
            Class.forName(dbClass);
            try (Connection con = DriverManager.getConnection(url, "root", "");
                 PreparedStatement pstmt = con.prepareStatement(query)) {

                pstmt.setString(1, username);

                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        return rs.getString("id");
                    }
                }
            }
        } catch (Exception e) {
            throw e;
        }
        return null;
    }
}
