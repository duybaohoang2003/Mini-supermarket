/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package supermarket.View;

import DAO.PhanQuyenDAO;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.*;
import supermarket.SetIcon;
import java.sql.ResultSet;

/**
 *
 * @author ASUS
 */
public class PhanQuyen extends JFrame implements ActionListener {

    private JComboBox<String> cbTaiKhoan;
    private JRadioButton btAdmin, btNhanVien;
    private JLabel lbTaiKhoan, lbQuyen;
    private JButton btnLuu;
    private PhanQuyenDAO p = new PhanQuyenDAO();
    String username = "";
    int quyen = -1;

    public PhanQuyen() {
        setTitle("Phân quyền");
        setSize(350, 230);

        lbQuyen = new JLabel("Quyền");
        lbTaiKhoan = new JLabel("Tài khoản");
        cbTaiKhoan = new JComboBox<>();
        ResultSet rs = p.getAccount();
        try {
            while (rs.next()) {
                String username = rs.getString("USERNAME");
                cbTaiKhoan.addItem(username);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        cbTaiKhoan.setPreferredSize(new Dimension(200, 20));
        btAdmin = new JRadioButton("ADMIN");
        btNhanVien = new JRadioButton("NHÂN VIÊN");
        btnLuu = new JButton("Lưu");
        String PathLuu = "D:\\on_java\\Supermarket\\icon\\luu.png";
        ImageIcon icon = SetIcon.SetSize(PathLuu, 30, 30);
        btnLuu.setIcon(icon);

        cbTaiKhoan.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                getQuyen(e);
            }
        });

        SpringLayout layout = new SpringLayout();
        JPanel panel = new JPanel();
        panel.setLayout(layout);
        panel.add(cbTaiKhoan);
        panel.add(btAdmin);
        panel.add(btNhanVien);
        panel.add(lbQuyen);
        panel.add(lbTaiKhoan);
        panel.add(btnLuu);

        layout.putConstraint(SpringLayout.WEST, lbTaiKhoan, 20, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, lbTaiKhoan, 20, SpringLayout.NORTH, panel);

        layout.putConstraint(SpringLayout.WEST, lbQuyen, 20, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, lbQuyen, 50, SpringLayout.NORTH, panel);

        layout.putConstraint(SpringLayout.WEST, cbTaiKhoan, 80, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, cbTaiKhoan, 20, SpringLayout.NORTH, panel);

        layout.putConstraint(SpringLayout.WEST, btAdmin, 80, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, btAdmin, 50, SpringLayout.NORTH, panel);

        layout.putConstraint(SpringLayout.WEST, btNhanVien, 80, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, btNhanVien, 70, SpringLayout.NORTH, panel);

        layout.putConstraint(SpringLayout.WEST, btnLuu, 140, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, btnLuu, 120, SpringLayout.NORTH, panel);

        btnLuu.addActionListener(this);

        add(panel);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton btn = (JButton) e.getSource();
        if (btn.equals(btnLuu)) {
            btnLuu_actionperformed();
        }
    }

    public void getQuyen(ItemEvent e) {
        if (e.getStateChange() == ItemEvent.SELECTED && e.getSource() == cbTaiKhoan) {
            String Name = (String) cbTaiKhoan.getSelectedItem();
            int quyen = -1;
            ResultSet rs=p.getQuyen(Name);
            try {
                if(rs.next()){
                    quyen=rs.getInt("QUYEN");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            if(quyen==1){
                btAdmin.setSelected(true);
                btNhanVien.setSelected(false);
            }else{
                btNhanVien.setSelected(true);
                btAdmin.setSelected(false);
            }
        }
    }

    public void btnLuu_actionperformed() {
        String username = (String) cbTaiKhoan.getSelectedItem();
        int quyen = -1;
        if (btAdmin.isSelected()) {
            quyen = 1;
        } else {
            quyen = 0;
        }
        int rs = p.UpdateQuyen(username, quyen);
        if (rs != 0) {
            JOptionPane.showMessageDialog(this, "Cập nhật quyền thành công!");
        } else {
            JOptionPane.showMessageDialog(this, "Cập nhật quyền không thành công!");
        }
    }

}
