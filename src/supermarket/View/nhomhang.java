/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package supermarket.View;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import Model.NhomHang;
import DAO.NhomHangDAO;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class nhomhang extends JFrame {
    private DefaultTableModel model;
    private JTable table;

    private JButton btnThem;
    private JButton btnSua;
    private JButton btnXoa;
    private JTextField txtID;
    private JTextField txtTennhomhang;
    private JLabel lblID;
    private JLabel lblTennhomhang;
    private NhomHang nhomhang;
    private NhomHangDAO nhomHangDAO;

    public nhomhang() {
        this.setSize(500, 400);

        // Tạo panel1 chứa các ô nhập và nhãn cho ID và Tên nhóm
        JPanel panel1 = new JPanel();
        panel1.setLayout(new FlowLayout(FlowLayout.CENTER));
        lblID = new JLabel("ID:");
        txtID = new JTextField(10);
        txtID.setEnabled(false);
        lblTennhomhang = new JLabel("Tên nhóm:");
        txtTennhomhang = new JTextField(20);
        panel1.add(lblID);
        panel1.add(txtID);
        panel1.add(lblTennhomhang);
        panel1.add(txtTennhomhang);
        panel1.setPreferredSize(new Dimension(380, 100));

        // Tạo panel chứa bảng và các nút chức năng
        JPanel pnTable = new JPanel();
        SpringLayout layout1 = new SpringLayout();
        pnTable.setLayout(layout1);
        pnTable.setPreferredSize(new Dimension(380, 250));

        table = new JTable();
        layout1.putConstraint(SpringLayout.WEST, table, 10, SpringLayout.WEST, pnTable);
        layout1.putConstraint(SpringLayout.NORTH, table, 10, SpringLayout.NORTH, pnTable);
        layout1.putConstraint(SpringLayout.EAST, table, -10, SpringLayout.EAST, pnTable);

        pnTable.setLayout(new BorderLayout());
        model = new DefaultTableModel();
        model.addColumn("ID nhóm hàng ");
        model.addColumn("Tên");

        table.setModel(model);
        table.setDefaultEditor(Object.class, null);

        TableColumn columnID = table.getColumnModel().getColumn(0);
        columnID.setPreferredWidth(30);

        fetchDataFromDatabase();

        JScrollPane scrollPane = new JScrollPane(table);
        pnTable.add(scrollPane, BorderLayout.CENTER); // Đặt scrollPane vào vị trí CENTER của pnTable
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) { // Nếu có dòng được chọn
                    // Lấy dữ liệu từ dòng được chọn và hiển thị lên panel 2
                    txtID.setText(model.getValueAt(selectedRow, 0).toString());
                    txtTennhomhang.setText(model.getValueAt(selectedRow, 1).toString());
                }
            }
        });

        // Tạo panel chứa các nút chức năng
        JPanel panelButtons = new JPanel(new FlowLayout());
        btnThem = new JButton("Thêm");
        btnSua = new JButton("Sửa");
        btnXoa = new JButton("Xóa");

        btnThem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String tennhomhang = txtTennhomhang.getText();
                nhomhang =new NhomHang();
                nhomhang.setTenNhomHang(tennhomhang);
                nhomHangDAO = new NhomHangDAO();
                nhomHangDAO.ThemNhomHang(nhomhang);
                fetchDataFromDatabase();
            }
        });

        btnSua.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id =Integer.parseInt(txtID.getText());
                String tennhomhang = txtTennhomhang.getText();
                nhomhang =new NhomHang();
                nhomhang.setTenNhomHang(tennhomhang);
                nhomhang.setId(id);
                nhomHangDAO = new NhomHangDAO();
                nhomHangDAO.suaNhomHang(nhomhang);
                fetchDataFromDatabase();
            }
        });

        btnXoa.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id =Integer.parseInt(txtID.getText());
                nhomHangDAO = new NhomHangDAO();
                nhomHangDAO.xoaNhomHang(id);
                fetchDataFromDatabase();
            }
        });

        panelButtons.add(btnThem);
        panelButtons.add(btnSua);
        panelButtons.add(btnXoa);

        // Thêm các panel vào JFrame
        setLayout(new BorderLayout());
        add(panel1, BorderLayout.NORTH);
        add(pnTable, BorderLayout.CENTER);
        add(panelButtons, BorderLayout.SOUTH);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void fetchDataFromDatabase() {
        String url = "jdbc:mysql://localhost:3306/test_supermarket";
        String dbClass = "com.mysql.cj.jdbc.Driver";
        String user = "root";
        String password = "";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement statement = conn.createStatement()) {

            String query = "SELECT * FROM nhomhang";
            try (ResultSet resultSet = statement.executeQuery(query)) {
                // Xóa dữ liệu cũ trong bảng
                model.setRowCount(0);

                while (resultSet.next()) {
                    String idnhomhang = resultSet.getString("Id");
                    String ten = resultSet.getString("TenNhomHang");

                    model.addRow(new Object[]{idnhomhang, ten});
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new nhomhang();
    }
}


