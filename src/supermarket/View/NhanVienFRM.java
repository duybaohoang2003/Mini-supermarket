/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package supermarket.View;

import DAO.NhanVienDAO;
import Model.NhanVien;
import com.toedter.calendar.JDateChooser;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.ResultSet;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 *
 * @author Bao Hoang
 */
public class NhanVienFRM extends JPanel implements ActionListener {

    private JTextField txtId, txtName, txtSDT, txtDiaChi, txtUserName, txtPassWord, txtTimKiem;

    JDateChooser JdateNamsinh;
//    private JSpinner spQuyen;
    private JComboBox<String> cbGioitinh;
    private JComboBox<String> cbChucVu;
    private JComboBox<String> cbBoPhan;
    private JButton btnAdd, btnClear, btnEdit, btnDelete, btnTimKiem, btnExcel;
    private JTable nhanVienTable;
    private JLabel lbTitle, lbId, lbName, lbGioiTinh, lbNamSinh, lbSDT, lbDiaChi, lbUserName, lbPassWord, lbQuyen, lbChucVu, lbBoPhan, lbTimKiem;
    private JScrollPane jscrollPaneNhanvien;
    private JTable NhanvienTable;
    private DefaultTableModel NhanvienTableModel;
    private final String[] column = new String[]{
        "Id", "Tên nhân vien", "Giới tính", "Năm Sinh",  "Số điện thoại", "Bộ phận", "Chức vụ", "UserName", "Password"
    };
    private final Object data = new Object[][]{};
    int idbp = 0;
    int idcv = 0;
    String formattedDate = "";
    Date selectedDate=null;

    public NhanVienFRM() {
//        setTitle("Quản Lý Nhân Viên");
//        setSize(1090, 700);
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        setLocationRelativeTo(null);
        txtId = new JTextField(10);
        txtId.setEditable(false);
        txtName = new JTextField(20);
        JdateNamsinh = new JDateChooser();
        JdateNamsinh.setDateFormatString("dd-MM-yyyy");
        txtSDT = new JTextField(20);
//        txtDiaChi = new JTextField(20);
        txtUserName = new JTextField(20);
        txtPassWord = new JTextField(20);
        txtTimKiem = new JTextField(20);

        ImageIcon iconId = new ImageIcon("D:\\on_java\\Supermarket\\icon\\id.png");
        ImageIcon iconName = new ImageIcon("D:\\on_java\\Supermarket\\icon\\name.png");
        ImageIcon iconGioiTinh = new ImageIcon("D:\\on_java\\Supermarket\\icon\\gender.png");
        ImageIcon iconNamSinh = new ImageIcon("D:\\on_java\\Supermarket\\icon\\namsinh.png");
        ImageIcon iconPhone = new ImageIcon("D:\\on_java\\Supermarket\\icon\\phone1.png");
//        ImageIcon iconAddress = new ImageIcon("D:\\on_java\\Supermarket\\icon\\address.png");
        ImageIcon iconUser = new ImageIcon("D:\\on_java\\Supermarket\\icon\\name.png");
        ImageIcon iconPass = new ImageIcon("D:\\on_java\\Supermarket\\icon\\password.png");
        ImageIcon iconId_CV = new ImageIcon("D:\\on_java\\Supermarket\\icon\\id.png");
        ImageIcon iconId_BP = new ImageIcon("D:\\on_java\\Supermarket\\icon\\id.png");
        ImageIcon iconAdd = new ImageIcon("D:\\on_java\\Supermarket\\icon\\btnAdd.png");
        ImageIcon iconEdit = new ImageIcon("D:\\on_java\\Supermarket\\icon\\btnEdit.png");
        ImageIcon iconDelete = new ImageIcon("D:\\on_java\\Supermarket\\icon\\btnDelete.png");
        ImageIcon iconClear = new ImageIcon("D:\\on_java\\Supermarket\\icon\\btnClear.png");
        ImageIcon iconSearch = new ImageIcon("D:\\on_java\\Supermarket\\icon\\btnSearch.png");
        ImageIcon iconExcel = new ImageIcon("D:\\on_java\\Supermarket\\icon\\btnExcel.png");

        lbTitle = new JLabel("QUẢN LÝ NHÂN VIÊN");
        Font font = new Font("Arial", Font.BOLD | Font.ITALIC, 28);
        lbTitle.setFont(font);
        lbId = new JLabel("ID", iconId, SwingConstants.LEFT);
        lbName = new JLabel("TÊN NHÂN VIÊN", iconName, SwingConstants.LEFT);
        lbGioiTinh = new JLabel("GIỚI TÍNH", iconGioiTinh, SwingConstants.LEFT);
        lbNamSinh = new JLabel("NĂM SINH", iconNamSinh, SwingConstants.LEFT);
        lbSDT = new JLabel("SỐ ĐIỆN THOẠI", iconPhone, SwingConstants.LEFT);
//        lbDiaChi = new JLabel("ĐỊA CHỈ", iconAddress, SwingConstants.LEFT);
        lbUserName = new JLabel("USERNAME", iconUser, SwingConstants.LEFT);
        lbPassWord = new JLabel("PASSWORD", iconPass, SwingConstants.LEFT);
//        lbQuyen = new JLabel("QUYỀN");
        lbChucVu = new JLabel("CHỨC VỤ", iconId_CV, SwingConstants.LEFT);
        lbBoPhan = new JLabel("BỘ PHẬN", iconId_BP, SwingConstants.LEFT);
        lbTimKiem = new JLabel("TÌM KIẾM");

        btnAdd = new JButton(iconAdd);
        btnAdd.setPreferredSize(new Dimension(70, 30)); // width và height là kích thước mong muốn

        btnEdit = new JButton(iconEdit);
        btnEdit.setPreferredSize(new Dimension(70, 30));

        btnDelete = new JButton(iconDelete);
        btnDelete.setPreferredSize(new Dimension(70, 30));

        btnClear = new JButton(iconClear);
        btnClear.setPreferredSize(new Dimension(70, 30));

        btnTimKiem = new JButton(iconSearch);
        btnTimKiem.setPreferredSize(new Dimension(70, 30));

        btnExcel = new JButton(iconExcel);
        btnExcel.setPreferredSize(new Dimension(70, 30));

        cbGioitinh = new JComboBox<>();
        cbGioitinh.addItem("Nam");
        cbGioitinh.addItem("Nữ");
        cbGioitinh.setPreferredSize(new Dimension(100, 20));

        cbChucVu = new JComboBox<>();
        cbChucVu.setPreferredSize(new Dimension(200, 20));

        // Gắn JComboBox cbChucVu vào giao diện
        cbBoPhan = new JComboBox<>();

        cbBoPhan.setPreferredSize(new Dimension(200, 20));

        JdateNamsinh.setPreferredSize(new Dimension(200,20));
        NhanvienTable = new JTable();
        jscrollPaneNhanvien = new JScrollPane();
        NhanvienTableModel = new DefaultTableModel((Object[][]) data, column);
        NhanvienTable.setModel(NhanvienTableModel);
        jscrollPaneNhanvien.setViewportView(NhanvienTable);
        jscrollPaneNhanvien.setPreferredSize(new Dimension(1100, 400));
        SpringLayout layout = new SpringLayout();
        setLayout(layout);
        InputView(layout);
        TableView(layout);
        DisplayData();
        DisplayBoPhan();
        DisplayChucVu();
        setVisible(true);
    }

    private void InputView(SpringLayout layout) {
        SpringLayout layoutInput = new SpringLayout();
        JPanel InputPanel = new JPanel();
        InputPanel.setLayout(layoutInput);
        InputPanel.setBackground(Color.PINK);
        InputPanel.setPreferredSize(new Dimension(1120, 350));
        this.add(InputPanel);
        InputPanel.setBorder(BorderFactory.createTitledBorder("Nhập thông tin"));

        layout.putConstraint(SpringLayout.NORTH, InputPanel, 0, SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.WEST, InputPanel, 20, SpringLayout.WEST, this);

        InputPanel.add(lbTitle);
        InputPanel.add(lbId);
        InputPanel.add(lbName);
        InputPanel.add(lbGioiTinh);
        InputPanel.add(lbNamSinh);
        InputPanel.add(lbSDT);
//        InputPanel.add(lbDiaChi);
        InputPanel.add(lbUserName);
        InputPanel.add(lbPassWord);
//        InputPanel.add(lbQuyen);
        InputPanel.add(lbChucVu);
        InputPanel.add(lbBoPhan);
        InputPanel.add(lbTimKiem);

        InputPanel.add(txtId);
        InputPanel.add(txtName);
        InputPanel.add(JdateNamsinh);
        InputPanel.add(txtSDT);
//        InputPanel.add(txtDiaChi);
        InputPanel.add(txtUserName);
        InputPanel.add(txtPassWord);
//        InputPanel.add(spQuyen);
        InputPanel.add(txtTimKiem);

        InputPanel.add(cbGioitinh);
        InputPanel.add(cbChucVu);
        InputPanel.add(cbBoPhan);

        InputPanel.add(btnAdd);
        InputPanel.add(btnEdit);
        InputPanel.add(btnDelete);
        InputPanel.add(btnClear);
        InputPanel.add(btnTimKiem);
        InputPanel.add(btnExcel);

        layoutInput.putConstraint(SpringLayout.WEST, lbTitle, 350, SpringLayout.WEST, InputPanel);
        layoutInput.putConstraint(SpringLayout.NORTH, lbTitle, 5, SpringLayout.NORTH, InputPanel);

        layoutInput.putConstraint(SpringLayout.WEST, lbId, 10, SpringLayout.WEST, InputPanel);
        layoutInput.putConstraint(SpringLayout.NORTH, lbId, 50, SpringLayout.NORTH, InputPanel);

        layoutInput.putConstraint(SpringLayout.WEST, txtId, 120, SpringLayout.WEST, InputPanel);
        layoutInput.putConstraint(SpringLayout.NORTH, txtId, 50, SpringLayout.NORTH, InputPanel);

        layoutInput.putConstraint(SpringLayout.WEST, lbName, 400, SpringLayout.WEST, InputPanel);
        layoutInput.putConstraint(SpringLayout.NORTH, lbName, 50, SpringLayout.NORTH, InputPanel);

        layoutInput.putConstraint(SpringLayout.WEST, txtName, 520, SpringLayout.WEST, InputPanel);
        layoutInput.putConstraint(SpringLayout.NORTH, txtName, 50, SpringLayout.NORTH, InputPanel);

        layoutInput.putConstraint(SpringLayout.WEST, lbGioiTinh, 750, SpringLayout.WEST, InputPanel);
        layoutInput.putConstraint(SpringLayout.NORTH, lbGioiTinh, 50, SpringLayout.NORTH, InputPanel);

        layoutInput.putConstraint(SpringLayout.WEST, cbGioitinh, 840, SpringLayout.WEST, InputPanel);
        layoutInput.putConstraint(SpringLayout.NORTH, cbGioitinh, 50, SpringLayout.NORTH, InputPanel);

        layoutInput.putConstraint(SpringLayout.WEST, lbNamSinh, 10, SpringLayout.WEST, InputPanel);
        layoutInput.putConstraint(SpringLayout.NORTH, lbNamSinh, 100, SpringLayout.NORTH, InputPanel);

        layoutInput.putConstraint(SpringLayout.WEST, JdateNamsinh, 120, SpringLayout.WEST, InputPanel);
        layoutInput.putConstraint(SpringLayout.NORTH, JdateNamsinh, 100, SpringLayout.NORTH, InputPanel);

        layoutInput.putConstraint(SpringLayout.WEST, lbSDT, 400, SpringLayout.WEST, InputPanel);
        layoutInput.putConstraint(SpringLayout.NORTH, lbSDT, 100, SpringLayout.NORTH, InputPanel);

        layoutInput.putConstraint(SpringLayout.WEST, txtSDT, 520, SpringLayout.WEST, InputPanel);
        layoutInput.putConstraint(SpringLayout.NORTH, txtSDT, 100, SpringLayout.NORTH, InputPanel);

//        layoutInput.putConstraint(SpringLayout.WEST, lbDiaChi, 750, SpringLayout.WEST, InputPanel);
//        layoutInput.putConstraint(SpringLayout.NORTH, lbDiaChi, 100, SpringLayout.NORTH, InputPanel);
//
//        layoutInput.putConstraint(SpringLayout.WEST, txtDiaChi, 840, SpringLayout.WEST, InputPanel);
//        layoutInput.putConstraint(SpringLayout.NORTH, txtDiaChi, 100, SpringLayout.NORTH, InputPanel);
        // END User Name

        layoutInput.putConstraint(SpringLayout.WEST, lbUserName, 10, SpringLayout.WEST, InputPanel);
        layoutInput.putConstraint(SpringLayout.NORTH, lbUserName, 150, SpringLayout.NORTH, InputPanel);

        layoutInput.putConstraint(SpringLayout.WEST, txtUserName, 120, SpringLayout.WEST, InputPanel);
        layoutInput.putConstraint(SpringLayout.NORTH, txtUserName, 150, SpringLayout.NORTH, InputPanel);

        layoutInput.putConstraint(SpringLayout.WEST, lbPassWord, 400, SpringLayout.WEST, InputPanel);
        layoutInput.putConstraint(SpringLayout.NORTH, lbPassWord, 150, SpringLayout.NORTH, InputPanel);

        layoutInput.putConstraint(SpringLayout.WEST, txtPassWord, 520, SpringLayout.WEST, InputPanel);
        layoutInput.putConstraint(SpringLayout.NORTH, txtPassWord, 150, SpringLayout.NORTH, InputPanel);

        layoutInput.putConstraint(SpringLayout.WEST, lbChucVu, 10, SpringLayout.WEST, InputPanel);
        layoutInput.putConstraint(SpringLayout.NORTH, lbChucVu, 200, SpringLayout.NORTH, InputPanel);

        layoutInput.putConstraint(SpringLayout.WEST, cbChucVu, 120, SpringLayout.WEST, InputPanel);
        layoutInput.putConstraint(SpringLayout.NORTH, cbChucVu, 200, SpringLayout.NORTH, InputPanel);

        layoutInput.putConstraint(SpringLayout.WEST, lbBoPhan, 400, SpringLayout.WEST, InputPanel);
        layoutInput.putConstraint(SpringLayout.NORTH, lbBoPhan, 200, SpringLayout.NORTH, InputPanel);

        layoutInput.putConstraint(SpringLayout.WEST, cbBoPhan, 520, SpringLayout.WEST, InputPanel);
        layoutInput.putConstraint(SpringLayout.NORTH, cbBoPhan, 200, SpringLayout.NORTH, InputPanel);

//        layoutInput.putConstraint(SpringLayout.WEST, lbQuyen, 750, SpringLayout.WEST, InputPanel);
//        layoutInput.putConstraint(SpringLayout.NORTH, lbQuyen, 200, SpringLayout.NORTH, InputPanel);
//        layoutInput.putConstraint(SpringLayout.WEST, spQuyen, 800, SpringLayout.WEST, InputPanel);
//        layoutInput.putConstraint(SpringLayout.NORTH, spQuyen, 200, SpringLayout.NORTH, InputPanel);
        layoutInput.putConstraint(SpringLayout.WEST, lbTimKiem, 300, SpringLayout.WEST, InputPanel);
        layoutInput.putConstraint(SpringLayout.NORTH, lbTimKiem, 250, SpringLayout.NORTH, InputPanel);

        layoutInput.putConstraint(SpringLayout.WEST, txtTimKiem, 400, SpringLayout.WEST, InputPanel);
        layoutInput.putConstraint(SpringLayout.NORTH, txtTimKiem, 250, SpringLayout.NORTH, InputPanel);

        layoutInput.putConstraint(SpringLayout.WEST, btnTimKiem, 650, SpringLayout.WEST, InputPanel);
        layoutInput.putConstraint(SpringLayout.NORTH, btnTimKiem, 250, SpringLayout.NORTH, InputPanel);

        layoutInput.putConstraint(SpringLayout.WEST, btnAdd, 250, SpringLayout.WEST, InputPanel);
        layoutInput.putConstraint(SpringLayout.NORTH, btnAdd, 290, SpringLayout.NORTH, InputPanel);

        layoutInput.putConstraint(SpringLayout.WEST, btnEdit, 70, SpringLayout.EAST, btnAdd);
        layoutInput.putConstraint(SpringLayout.NORTH, btnEdit, 290, SpringLayout.NORTH, InputPanel);

        layoutInput.putConstraint(SpringLayout.WEST, btnDelete, 70, SpringLayout.EAST, btnEdit);
        layoutInput.putConstraint(SpringLayout.NORTH, btnDelete, 290, SpringLayout.NORTH, InputPanel);

        layoutInput.putConstraint(SpringLayout.WEST, btnClear, 70, SpringLayout.EAST, btnDelete);
        layoutInput.putConstraint(SpringLayout.NORTH, btnClear, 290, SpringLayout.NORTH, InputPanel);

        layoutInput.putConstraint(SpringLayout.WEST, btnExcel, 70, SpringLayout.EAST, btnClear);
        layoutInput.putConstraint(SpringLayout.NORTH, btnExcel, 290, SpringLayout.NORTH, InputPanel);

    }

    private void TableView(SpringLayout layout) {
        SpringLayout layoutTable = new SpringLayout();
        JPanel TablePanel = new JPanel();
        TablePanel.setLayout(layoutTable);
        TablePanel.setPreferredSize(new Dimension(1120, 500));
        this.add(TablePanel);
        TablePanel.setBorder(BorderFactory.createTitledBorder("Thông tin nhân viên"));
        TablePanel.setBackground(Color.lightGray);

// Điều chỉnh kích thước cột
        TableColumnModel columnModel = NhanvienTable.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(30); // Điều chỉnh chiều rộng cho cột 0
        columnModel.getColumn(1).setPreferredWidth(150); // Điều chỉnh chiều rộng cho cột 1
        columnModel.getColumn(2).setPreferredWidth(50); // Điều chỉnh chiều rộng cho cột 1
        columnModel.getColumn(3).setPreferredWidth(80); // Điều chỉnh chiều rộng cho cột 1
        columnModel.getColumn(4).setPreferredWidth(140); // Điều chỉnh chiều rộng cho cột 1
        columnModel.getColumn(5).setPreferredWidth(70); // Điều chỉnh chiều rộng cho cột 1
        columnModel.getColumn(6).setPreferredWidth(60); // Điều chỉnh chiều rộng cho cột 1
        columnModel.getColumn(7).setPreferredWidth(70); // Điều chỉnh chiều rộng cho cột 1
        columnModel.getColumn(8).setPreferredWidth(70); // Điều chỉnh chiều rộng cho cột 1
//        columnModel.getColumn(9).setPreferredWidth(70); // Điều chỉnh chiều rộng cho cột 1

        TablePanel.add(jscrollPaneNhanvien);

        layout.putConstraint(SpringLayout.NORTH, TablePanel, 350, SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.WEST, TablePanel, 20, SpringLayout.WEST, this);

        layout.putConstraint(SpringLayout.NORTH, jscrollPaneNhanvien, 5, SpringLayout.NORTH, TablePanel);
        layout.putConstraint(SpringLayout.WEST, jscrollPaneNhanvien, 0, SpringLayout.WEST, TablePanel);

        ListSelectionModel selectionModel = NhanvienTable.getSelectionModel();
        selectionModel.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int selectedRow = NhanvienTable.getSelectedRow();
                    if (selectedRow != -1) {
                        // Lấy dữ liệu từ hàng được chọn
                        String Id = NhanvienTable.getValueAt(selectedRow, 0).toString();
                        String TenNhanVien = NhanvienTable.getValueAt(selectedRow, 1).toString();
                        String GioiTinh = NhanvienTable.getValueAt(selectedRow, 2).toString();
                        String NamSinh = NhanvienTable.getValueAt(selectedRow, 3).toString();
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//                        String DiaChi = NhanvienTable.getValueAt(selectedRow, 4).toString();
                        String SoDienThoai = NhanvienTable.getValueAt(selectedRow, 4).toString();
                        String BoPhan = NhanvienTable.getValueAt(selectedRow, 5).toString();
                        String ChucVu = NhanvienTable.getValueAt(selectedRow, 6).toString();
                        String UserName = NhanvienTable.getValueAt(selectedRow, 7).toString();
                        String PassWord = NhanvienTable.getValueAt(selectedRow, 8).toString();

                        // Hiển thị dữ liệu lên các ô textfield và combobox
                        txtId.setText(Id);
                        txtName.setText(TenNhanVien);
                        cbGioitinh.setSelectedItem(GioiTinh);
                        try {
                            Date date = dateFormat.parse(NamSinh);
                            JdateNamsinh.setDate(date);
                        } catch (ParseException ex) {
                            ex.printStackTrace();
                        }

//                        txtDiaChi.setText(DiaChi);
                        txtSDT.setText(SoDienThoai);
                        cbBoPhan.setSelectedItem(BoPhan);
                        cbChucVu.setSelectedItem(ChucVu);
                        txtUserName.setText(UserName);
                        txtPassWord.setText(PassWord);

                    }
                }
            }
        });
        cbBoPhan.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                GetInfoId(e);
            }
        });
        cbChucVu.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                GetInfoIdcv(e);
            }
        });

        btnEdit.addActionListener(this);
        btnAdd.addActionListener(this);
        btnDelete.addActionListener(this);
        btnClear.addActionListener(this);
        btnTimKiem.addActionListener(this);
        btnExcel.addActionListener(this);

    }

    // hiển thị dữ liệu từ sql lên bảng
    public void DisplayData() {

        DefaultTableModel NhanVienTableModel = (DefaultTableModel) NhanvienTable.getModel();
        NhanvienTableModel.setRowCount(0); // Xóa 
        NhanVienDAO nv = new NhanVienDAO();
        ResultSet rs = nv.getAllNV();
        try {
            while (rs.next()) {
                int Id = rs.getInt("Id");
                String TenNhanVien = rs.getString("TenNV");
                int GioiTinhInt = rs.getInt("GioiTinh");
                String GioiTinh = (GioiTinhInt == 1) ? "Nam" : "Nữ";
                String NamSinh = rs.getString("NamSinh");
                String SDT = rs.getString("SoDienThoai");
//                String DiaChi = rs.getString("DiaChi");
                int IdBoPhan = rs.getInt("Id_Bophan");
                int IdChucVu = rs.getInt("Id_ChucVu");

                // Lấy tên bộ phận và chức vụ dựa trên id
                String TenBoPhan = nv.getTenBoPhan(IdBoPhan);
                String TenChucVu = nv.getTenChucVu(IdChucVu);
                String UserName = rs.getString("UserName");
                String PassWord = rs.getString("PassWord");
                Object[] rowData = {Id, TenNhanVien, GioiTinh, NamSinh, SDT, TenBoPhan, TenChucVu, UserName, PassWord};
                NhanvienTableModel.addRow(rowData);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public void DisplayBoPhan() {
        NhanVienDAO nvD = new NhanVienDAO();
        ResultSet rs = nvD.getBoPhan();

        ArrayList<String> TenNS = new ArrayList<>();
        try {
            while (rs.next()) {
                String Ten = rs.getString("TENBOPHAN");
                TenNS.add(Ten);
            }
        } catch (Exception e) {
        }

        for (String name : TenNS) {
            cbBoPhan.addItem(name);
        }
    }

    public void DisplayChucVu() {
        NhanVienDAO nvD = new NhanVienDAO();
        ResultSet rs = nvD.getChucVu();

        ArrayList<String> TenNS = new ArrayList<>();
        try {
            while (rs.next()) {
                String Ten = rs.getString("TENCHUCVU");
                TenNS.add(Ten);
            }
        } catch (Exception e) {
        }

        for (String name : TenNS) {
            cbChucVu.addItem(name);
        }
    }

    // Thêm nhân viên
    public void btnAdd_actionperformed() {
        if (txtName.getText().isEmpty() 
                || txtSDT.getText().isEmpty() || txtUserName.getText().isEmpty() || txtPassWord.getText().isEmpty()
                || cbChucVu.getSelectedIndex() == -1 || cbBoPhan.getSelectedIndex() == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ thông tin nhân viên.");
            return; // Dừng việc thêm nếu thiếu thông tin
        }

        String sdt = txtSDT.getText();
        if (!sdt.matches("\\d{1,10}")) {
            JOptionPane.showMessageDialog(this, "Số điện thoại chỉ được nhập số và không quá 10 số.");
            txtSDT.setText("");
            return;
        }
        NhanVienDAO svD = new NhanVienDAO();

        // Kiểm tra xem số điện thoại đã tồn tại trong cơ sở dữ liệu hay chưa
        if (svD.isSDTExists(sdt)) {
            JOptionPane.showMessageDialog(this, "Số điện thoại đã tồn tại trong cơ sở dữ liệu.");
            txtSDT.setText("");
            return; // Dừng việc thêm nếu số điện thoại đã tồn tại
        }

        String username = txtUserName.getText();
        if (svD.checkUserName(username)) {
            JOptionPane.showMessageDialog(this, "UserName da ton tai");
            return;
        }
        // kiem tra xem các ô đã được nhập hết chưa
        NhanVien sv = new NhanVien();
        sv.setTenNV(txtName.getText());
        selectedDate = JdateNamsinh.getDate();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
        formattedDate = sdf.format(selectedDate);
        sv.setNamSinh(formattedDate);
//        sv.setDiaChi(txtDiaChi.getText());
        sv.setSoDienThoai(txtSDT.getText());
        sv.setId_ChucVu(idcv);
        sv.setId_BoPhan(idbp);
        sv.setUserName(txtUserName.getText());
        sv.setPassword(txtPassWord.getText());
        String gioitinh = cbGioitinh.getSelectedItem().toString();
        int gt;
        if (gioitinh == "Nam") {
            sv.setGioiTinh(1);
        } else {
            sv.setGioiTinh(0);
        }

        int rs = svD.ThemNV(sv);
        if (rs != 0) {
            JOptionPane.showMessageDialog(this, "Thêm thành công");
            btnClear_actionperformed();
            DisplayData();
        } else {
            JOptionPane.showMessageDialog(this, "Thêm không thành công");
        }
    }

    // sửa
    public void btnEdit_actionperformed() {
        if (txtName.getText().isEmpty() || txtDiaChi.getText().isEmpty()
                || txtSDT.getText().isEmpty() || txtUserName.getText().isEmpty() || txtPassWord.getText().isEmpty()
                || cbChucVu.getSelectedIndex() == -1 || cbBoPhan.getSelectedIndex() == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ thông tin nhân viên.");
            return; // Dừng việc thêm nếu thiếu thông tin
        }

        String sdt = txtSDT.getText();
        if (!sdt.matches("\\d{1,10}")) {
            JOptionPane.showMessageDialog(this, "Số điện thoại chỉ được nhập số và không quá 10 số.");
            txtSDT.setText("");
            return;
        }
        NhanVienDAO nvD = new NhanVienDAO();

//        if (nvD.isSDTExists(sdt)) {
//            JOptionPane.showMessageDialog(this, "Số điện thoại đã tồn tại trong cơ sở dữ liệu.");
//            txtSDT.setText("");
//            return; // Dừng việc thêm nếu số điện thoại đã tồn tại
//        }
        NhanVien nv = new NhanVien();
        int Id = Integer.parseInt(txtId.getText());
        nv.setTenNV(txtName.getText());
        String gioitinh = cbGioitinh.getSelectedItem().toString();
        int gt;
        if (gioitinh == "Nam") {
            nv.setGioiTinh(1);
        } else {
            nv.setGioiTinh(0);
        }
        selectedDate = JdateNamsinh.getDate();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
        formattedDate = sdf.format(selectedDate);
        nv.setNamSinh(formattedDate);
        nv.setDiaChi(txtDiaChi.getText());
        nv.setSoDienThoai(txtSDT.getText());
        nv.setUserName(txtUserName.getText());
        nv.setPassword(txtPassWord.getText());
        String tenchuvu = (String) cbChucVu.getSelectedItem();
        ResultSet rs1 = nvD.GetIDChucVu(tenchuvu);
        try {
            if (rs1.next()) {
                idcv = rs1.getInt("ID");
            }
        } catch (Exception ex) {
        }
        String tenbophan = (String) cbBoPhan.getSelectedItem();
        ResultSet rs2 = nvD.GetIDBoPhan(tenbophan);
        try {
            if (rs2.next()) {
                idbp = rs2.getInt("ID");
            }
        } catch (Exception ex) {
        }
        nv.setId_ChucVu(idcv);
        nv.setId_BoPhan(idbp);

        int rs = nvD.SuaNV(Id, nv);
        if (rs != 0) {
            JOptionPane.showMessageDialog(this, "Sua thanh cong");
            btnClear_actionperformed();
            DisplayData();
        } else {
            JOptionPane.showMessageDialog(this, "Sua khong thanh cong");
        }

    }

    // xóa
    public void btnDelete_actionperfomed() {
        String Id = txtId.getText();
        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa không ?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            NhanVienDAO nvD = new NhanVienDAO();
            int rs = nvD.XoaNV(Id);

            if (rs != 0) {
                JOptionPane.showMessageDialog(this, "Xóa thành công");
                txtId.setText("");
                txtName.setText("");
                cbGioitinh.setSelectedIndex(0);
                JdateNamsinh.setDate(null);
                txtDiaChi.setText("");
                txtSDT.setText("");
                txtUserName.setText("");
                txtPassWord.setText("");
                cbBoPhan.setSelectedIndex(0);
                cbChucVu.setSelectedIndex(0);

                DisplayData();
            } else {
                JOptionPane.showMessageDialog(this, "Xóa không thành công");
            }
        }
    }

    // clear
    public void btnClear_actionperformed() {
        txtId.setText("");
        txtName.setText("");
        cbGioitinh.setSelectedIndex(0);
        JdateNamsinh.setDate(null);
        txtDiaChi.setText("");
        txtSDT.setText("");
        txtUserName.setText("");
        txtPassWord.setText("");
        cbBoPhan.setSelectedIndex(0);
        cbChucVu.setSelectedIndex(0);

    }

    // tìm kiếm
    private void btnTimKiem_actionperformed() {
        String searchName = txtTimKiem.getText().trim();
        if (searchName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập tên khách hàng cần tìm kiếm");
            return;
        }

        DefaultTableModel tableModel = (DefaultTableModel) NhanvienTable.getModel();
        NhanVienDAO nvD = new NhanVienDAO();
        ResultSet rs = nvD.searchByName(searchName);

        // Duyệt qua tất cả các dòng trong bảng để tìm dòng có tên khách hàng trùng với tên cần tìm
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            String tenKhachHang = (String) tableModel.getValueAt(i, 1);
            // Nếu tên khách hàng trùng, chọn dòng đó trong bảng
            if (tenKhachHang.equalsIgnoreCase(searchName)) {
                NhanvienTable.setRowSelectionInterval(i, i);
                NhanvienTable.scrollRectToVisible(NhanvienTable.getCellRect(i, 0, true)); // Cuộn đến dòng đã chọn
                return; // Kết thúc phương thức sau khi đã chọn dòng
            }
        }

        // Nếu không tìm thấy tên khách hàng trong bảng, hiển thị thông báo
        JOptionPane.showMessageDialog(this, "Không tìm thấy nhân viên: " + searchName);
    }

    // xuất excel
    private void btnExcel_actionperformed() {
        exportToExcel();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton btn = (JButton) e.getSource();
        if (btn.equals(btnAdd)) {
            btnAdd_actionperformed();
        } else if (btn.equals(btnEdit)) {
            btnEdit_actionperformed();
        } else if (btn.equals(btnDelete)) {
            btnDelete_actionperfomed();
        } else if (btn.equals(btnTimKiem)) {
            btnTimKiem_actionperformed();
        } else if (btn.equals(btnExcel)) {
            btnExcel_actionperformed();
        } else {
            btnClear_actionperformed();
        }

    }

    public void GetInfoId(ItemEvent e) {

        if (e.getStateChange() == ItemEvent.SELECTED && e.getSource() == cbBoPhan) {
            String name = (String) cbBoPhan.getSelectedItem();
            NhanVienDAO nvD = new NhanVienDAO();
            ResultSet rs = nvD.GetIDBoPhan(name);
            try {
                if (rs.next()) {
                    idbp = rs.getInt("ID");
                }
            } catch (Exception ex) {
            }
        }
    }

    public void GetInfoIdcv(ItemEvent e) {

        if (e.getStateChange() == ItemEvent.SELECTED && e.getSource() == cbChucVu) {
            String name = (String) cbChucVu.getSelectedItem();
            NhanVienDAO nvD = new NhanVienDAO();
            ResultSet rs = nvD.GetIDChucVu(name);
            try {
                if (rs.next()) {
                    idcv = rs.getInt("ID");
                }
            } catch (Exception ex) {
            }
        }
    }

    // xuất excel
    private void exportToExcel() {
        // Tạo workbook mới
        Workbook workbook = new XSSFWorkbook();

        // Tạo một trang tính mới
        Sheet sheet = workbook.createSheet("Data");

        // Lấy số dòng và số cột của bảng
        int rowCount = NhanvienTableModel.getRowCount();
        int columnCount = NhanvienTableModel.getColumnCount();

        // Ghi tên cột vào hàng đầu tiên
        Row headerRow = sheet.createRow(0);
        for (int col = 0; col < columnCount; col++) {
            headerRow.createCell(col).setCellValue(NhanvienTableModel.getColumnName(col));
        }

        // Ghi dữ liệu từ bảng vào file Excel
        for (int i = 0; i < rowCount; i++) {
            Row row = sheet.createRow(i + 1); // Bắt đầu từ hàng thứ hai sau header
            for (int j = 0; j < columnCount; j++) {
                Object value = NhanvienTableModel.getValueAt(i, j);
                Cell cell = row.createCell(j);
                if (value != null) {
                    cell.setCellValue(value.toString());
                }
            }
        }

        // Đường dẫn tới file Excel đích
        String filePath = "D:\\on_java\\Supermarket\\excel\\table1.xlsx";

        // Lưu workbook vào một file
        try (FileOutputStream outputStream = new FileOutputStream(filePath)) {
            workbook.write(outputStream);
            JOptionPane.showMessageDialog(null, "Xuất file thành công.");
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi, không thể xuất file: " + e.getMessage());
        }
    }

}
