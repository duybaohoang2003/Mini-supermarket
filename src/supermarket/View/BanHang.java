/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package supermarket.View;

import DAO.BanHangDAO;
import DAO.HoaDonDAO;
import DAO.KhachHangDAO;
import Model.KhachHang;
import Model.HangHoa;
import Model.NhanVien;
import Model.HoaDonM;
import Model.SessionManager;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import supermarket.SetIcon;
import java.sql.ResultSet;
import java.util.ArrayList;
import assets.NumberInputField;
import java.awt.event.ItemEvent;
import java.sql.SQLException;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.util.List;

public class BanHang extends JPanel implements ActionListener {

    private JLabel lbTenKhachHang, lbDiaChi, lbKhachHang, lbDienThoai, lbThongTin, lbMaHang, lbGiaBan, lbSoLuong, lbThanhTien, lbHoaDon, lbTong;
    private JButton btnThem, btnSua, btnXoa, btnLuuKhacHang, btnLuuHoaDon, btnTaoHoaDonMoi, btnInHoaDon;
    private JTextField txtDiaChi, txtThanhTien, txtGiaBan, txtTong, txtTenKhachHang;
    private NumberInputField txtDienThoai;
    private JComboBox<Integer> cbMaHang;
    private JComboBox<String> cbTenKhachHang;
    private JSpinner spSoLuong;
    private JTable productTable;
    private JScrollPane jScrollPaneProductTable;
    private DefaultTableModel productTableModel;
    private final String[] columnName = new String[]{
        "IdHH", "Tên hàng", "Giá bán", "Số lượng", "Thành tiền"
    };
    private final Object data = new Object[][]{};
    HangHoa h = new HangHoa();
    NhanVien nv = new NhanVien();
    KhachHang kh = new KhachHang();
    HoaDonM hoadon = new HoaDonM();
    int IDPX;
    private int SelectRow;
    int soluongkiemtra = -1;

    public BanHang() {
        //label
        lbTenKhachHang = new JLabel("Tên khách hàng");
        lbDiaChi = new JLabel("Địa chỉ");
        lbDienThoai = new JLabel("Điện thoại");
        lbThongTin = new JLabel("Thông tin hàng hóa");
        lbMaHang = new JLabel("Mã hàng");
        lbGiaBan = new JLabel("Giá bán");
        lbSoLuong = new JLabel("Số lượng");
        lbThanhTien = new JLabel("Thành tiền");
        lbHoaDon = new JLabel("HÓA ĐƠN");
        lbKhachHang = new JLabel("Khách hàng");
        Font font = new Font("Arial", Font.BOLD | Font.ITALIC, 36);
        lbHoaDon.setFont(font);
        lbTong = new JLabel("Tổng");
        //button
        btnThem = new JButton("Thêm");
        btnSua = new JButton("Sửa");
        btnXoa = new JButton("Xóa");
        btnSua.setEnabled(false);
        btnXoa.setEnabled(false);
        btnLuuKhacHang = new JButton("Lưu khách hàng");
        btnLuuHoaDon = new JButton("Lưu hóa đơn");
        btnTaoHoaDonMoi = new JButton("Tạo hóa đơn mới");
        btnInHoaDon = new JButton("In hóa đơn");
        //jtextfield
        txtDiaChi = new JTextField(20);
        txtDienThoai = new NumberInputField(20);
        txtThanhTien = new JTextField(20);
        txtGiaBan = new JTextField(20);
        txtTenKhachHang = new JTextField(20);
        txtTong = new JTextField(20);
        txtTong.setEnabled(false);
        //combobox
        cbMaHang = new JComboBox<>();
        cbTenKhachHang = new JComboBox<>();
        cbTenKhachHang.setPreferredSize(new Dimension(200, 20));
        cbMaHang.setPreferredSize(new Dimension(200, 20));
        //table
        productTable = new JTable();
        jScrollPaneProductTable = new JScrollPane();
        //spinner
        SpinnerNumberModel spinnerModel = new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1);//không cho người dùng nhập số âm
        spSoLuong = new JSpinner(spinnerModel);
        spSoLuong.setPreferredSize(new Dimension(150, 20));
        //
        productTableModel = new DefaultTableModel((Object[][]) data, columnName);
        productTable.setModel(productTableModel);
        jScrollPaneProductTable.setViewportView(productTable);
        jScrollPaneProductTable.setPreferredSize(new Dimension(1050, 200));
        productTable.getColumnModel().getColumn(0).setMinWidth(0);
        productTable.getColumnModel().getColumn(0).setMaxWidth(0);
        productTable.getColumnModel().getColumn(0).setWidth(0);

        SpringLayout layout = new SpringLayout();
        //add tên khách hàng vào combobox
        DisplayTenKH();
        DisplayMaHang();
        //sự kiện lấy giá từ combobox
        cbMaHang.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                GetPrice(e);
            }
        });
        //sự kiện lấy thông tin khách hàng từ tên khách hàng
        cbTenKhachHang.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                GetInFoKH(e);
            }
        });
        //Sự kiện tính tình của mỗi sản phẩm khi thay đổi số lượng
        spSoLuong.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                updateThanhTien();
            }
        });

        setLayout(layout);
        KhachHangView(layout);
        HangHoaView(layout);
        TableView(layout);
        //SỰ KIỆN NGUỜI DÙNG THAY ĐỔI SỐ LƯỢNG CỦA 1 SẢN PHẨM KHI ĐÃ THÊM VÀO BẢNG
        ListSelectionModel SelectionModel = productTable.getSelectionModel();
        SelectionModel.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    btnSua.setEnabled(true);
                    btnXoa.setEnabled(true);
                    //lấy vị trí hàng được chọn
                    SelectRow = productTable.getSelectedRow();
                    if (SelectRow != -1) {
                        String SoLuong = productTable.getValueAt(SelectRow, 3).toString();
                        int sl = Integer.parseInt(SoLuong);
                        spSoLuong.setValue(sl);
                        txtThanhTien.setText(productTable.getValueAt(SelectRow, 4).toString());
                    }
                }
            }
        });

        //actionlisener
        btnThem.addActionListener(this);
        btnSua.addActionListener(this);
        btnXoa.addActionListener(this);
        btnLuuHoaDon.addActionListener(this);
        btnTaoHoaDonMoi.addActionListener(this);
        btnInHoaDon.addActionListener(this);
        btnLuuKhacHang.addActionListener(this);
    }

    private void KhachHangView(SpringLayout layout) {
        SpringLayout layoutKhachHang = new SpringLayout();
        JPanel KhachHangPanel = new JPanel();
        KhachHangPanel.setLayout(layoutKhachHang);
        KhachHangPanel.setPreferredSize(new Dimension(1150, 200));
        this.add(KhachHangPanel);
        Border border = BorderFactory.createLineBorder(Color.BLACK);
        KhachHangPanel.setBorder(border);
        layout.putConstraint(SpringLayout.WEST, KhachHangPanel, 10, SpringLayout.WEST, this);

        String PathThem = "D:\\on_java\\Supermarket\\icon\\them.png";
        String PathLuu = "D:\\on_java\\Supermarket\\icon\\luu.png";

        ImageIcon IconThem = SetIcon.SetSize(PathThem, 20, 20);
        ImageIcon IconLuu = SetIcon.SetSize(PathLuu, 20, 20);

        btnTaoHoaDonMoi.setIcon(IconThem);
        btnLuuHoaDon.setIcon(IconLuu);
        btnLuuKhacHang.setIcon(IconLuu);

        btnTaoHoaDonMoi.setPreferredSize(new Dimension(200, 30));
        btnLuuHoaDon.setPreferredSize(new Dimension(200, 30));
        btnLuuKhacHang.setPreferredSize(new Dimension(200, 30));

        KhachHangPanel.add(lbTenKhachHang);
        KhachHangPanel.add(lbDiaChi);
        KhachHangPanel.add(lbDienThoai);
        KhachHangPanel.add(txtDiaChi);
        KhachHangPanel.add(txtDienThoai);
        KhachHangPanel.add(txtTenKhachHang);
        KhachHangPanel.add(cbTenKhachHang);
        KhachHangPanel.add(btnLuuHoaDon);
        KhachHangPanel.add(lbKhachHang);
        KhachHangPanel.add(btnLuuKhacHang);
        KhachHangPanel.add(btnTaoHoaDonMoi);
        KhachHangPanel.add(lbHoaDon);

        layoutKhachHang.putConstraint(SpringLayout.WEST, lbHoaDon, 500, SpringLayout.WEST, KhachHangPanel);
        layoutKhachHang.putConstraint(SpringLayout.NORTH, lbHoaDon, 10, SpringLayout.NORTH, KhachHangPanel);

        layoutKhachHang.putConstraint(SpringLayout.WEST, lbTenKhachHang, 50, SpringLayout.WEST, KhachHangPanel);
        layoutKhachHang.putConstraint(SpringLayout.NORTH, lbTenKhachHang, 70, SpringLayout.NORTH, KhachHangPanel);

        layoutKhachHang.putConstraint(SpringLayout.WEST, lbDiaChi, 50, SpringLayout.WEST, KhachHangPanel);
        layoutKhachHang.putConstraint(SpringLayout.NORTH, lbDiaChi, 100, SpringLayout.NORTH, KhachHangPanel);

        layoutKhachHang.putConstraint(SpringLayout.WEST, lbDienThoai, 500, SpringLayout.WEST, KhachHangPanel);
        layoutKhachHang.putConstraint(SpringLayout.NORTH, lbDienThoai, 70, SpringLayout.NORTH, KhachHangPanel);

        layoutKhachHang.putConstraint(SpringLayout.WEST, lbKhachHang, 500, SpringLayout.WEST, KhachHangPanel);
        layoutKhachHang.putConstraint(SpringLayout.NORTH, lbKhachHang, 100, SpringLayout.NORTH, KhachHangPanel);

        layoutKhachHang.putConstraint(SpringLayout.WEST, txtTenKhachHang, 200, SpringLayout.WEST, KhachHangPanel);
        layoutKhachHang.putConstraint(SpringLayout.NORTH, txtTenKhachHang, 70, SpringLayout.NORTH, KhachHangPanel);

        layoutKhachHang.putConstraint(SpringLayout.WEST, txtDiaChi, 200, SpringLayout.WEST, KhachHangPanel);
        layoutKhachHang.putConstraint(SpringLayout.NORTH, txtDiaChi, 100, SpringLayout.NORTH, KhachHangPanel);

        layoutKhachHang.putConstraint(SpringLayout.WEST, txtDienThoai, 20, SpringLayout.EAST, lbKhachHang);
        layoutKhachHang.putConstraint(SpringLayout.NORTH, txtDienThoai, 70, SpringLayout.NORTH, KhachHangPanel);

        layoutKhachHang.putConstraint(SpringLayout.WEST, cbTenKhachHang, 30, SpringLayout.EAST, lbDienThoai);
        layoutKhachHang.putConstraint(SpringLayout.NORTH, cbTenKhachHang, 100, SpringLayout.NORTH, KhachHangPanel);

        layoutKhachHang.putConstraint(SpringLayout.WEST, btnTaoHoaDonMoi, 850, SpringLayout.WEST, KhachHangPanel);
        layoutKhachHang.putConstraint(SpringLayout.NORTH, btnTaoHoaDonMoi, 60, SpringLayout.NORTH, KhachHangPanel);

        layoutKhachHang.putConstraint(SpringLayout.WEST, btnLuuHoaDon, 850, SpringLayout.WEST, KhachHangPanel);
        layoutKhachHang.putConstraint(SpringLayout.NORTH, btnLuuHoaDon, 20, SpringLayout.SOUTH, btnTaoHoaDonMoi);

        layoutKhachHang.putConstraint(SpringLayout.WEST, btnLuuKhacHang, 850, SpringLayout.WEST, KhachHangPanel);
        layoutKhachHang.putConstraint(SpringLayout.NORTH, btnLuuKhacHang, 20, SpringLayout.SOUTH, btnLuuHoaDon);
    }

    private void HangHoaView(SpringLayout layout) {
        SpringLayout layoutHangHoa = new SpringLayout();
        JPanel HangHoaPanel = new JPanel();
        HangHoaPanel.setLayout(layoutHangHoa);
        HangHoaPanel.setPreferredSize(new Dimension(1150, 150));
        this.add(HangHoaPanel);
        Border border = BorderFactory.createLineBorder(Color.BLACK);
        HangHoaPanel.setBorder(BorderFactory.createTitledBorder(border, "Thông tin sản phẩm"));

        layout.putConstraint(SpringLayout.WEST, HangHoaPanel, 10, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, HangHoaPanel, 201, SpringLayout.NORTH, this);

        String PathThem = "D:\\on_java\\Supermarket\\icon\\them.png";
        String PathSua = "D:\\on_java\\Supermarket\\icon\\sua.png";
        String PathXoa = "D:\\on_java\\Supermarket\\icon\\xoa.png";

        ImageIcon IconThem = SetIcon.SetSize(PathThem, 20, 20);
        ImageIcon IconSua = SetIcon.SetSize(PathSua, 20, 20);
        ImageIcon IconXoa = SetIcon.SetSize(PathXoa, 20, 20);

        btnThem.setIcon(IconThem);
        btnSua.setIcon(IconSua);
        btnXoa.setIcon(IconXoa);

        btnThem.setPreferredSize(new Dimension(100, 30));
        btnSua.setPreferredSize(new Dimension(100, 30));
        btnXoa.setPreferredSize(new Dimension(100, 30));

        HangHoaPanel.add(lbMaHang);
        HangHoaPanel.add(lbSoLuong);
        HangHoaPanel.add(lbGiaBan);
        HangHoaPanel.add(lbThanhTien);
        HangHoaPanel.add(txtThanhTien);
        HangHoaPanel.add(spSoLuong);
        HangHoaPanel.add(cbMaHang);
        HangHoaPanel.add(txtGiaBan);
        HangHoaPanel.add(btnThem);
        HangHoaPanel.add(btnSua);
        HangHoaPanel.add(btnXoa);

        layoutHangHoa.putConstraint(SpringLayout.WEST, lbMaHang, 50, SpringLayout.WEST, HangHoaPanel);
        layoutHangHoa.putConstraint(SpringLayout.NORTH, lbMaHang, 40, SpringLayout.NORTH, HangHoaPanel);

        layoutHangHoa.putConstraint(SpringLayout.WEST, cbMaHang, 20, SpringLayout.EAST, lbMaHang);
        layoutHangHoa.putConstraint(SpringLayout.NORTH, cbMaHang, 40, SpringLayout.NORTH, HangHoaPanel);

        layoutHangHoa.putConstraint(SpringLayout.WEST, lbGiaBan, 20, SpringLayout.EAST, cbMaHang);
        layoutHangHoa.putConstraint(SpringLayout.NORTH, lbGiaBan, 40, SpringLayout.NORTH, HangHoaPanel);

        layoutHangHoa.putConstraint(SpringLayout.WEST, txtGiaBan, 20, SpringLayout.EAST, lbGiaBan);
        layoutHangHoa.putConstraint(SpringLayout.NORTH, txtGiaBan, 40, SpringLayout.NORTH, HangHoaPanel);

        layoutHangHoa.putConstraint(SpringLayout.WEST, lbSoLuong, 20, SpringLayout.EAST, txtGiaBan);
        layoutHangHoa.putConstraint(SpringLayout.NORTH, lbSoLuong, 40, SpringLayout.NORTH, HangHoaPanel);

        layoutHangHoa.putConstraint(SpringLayout.WEST, spSoLuong, 20, SpringLayout.EAST, lbSoLuong);
        layoutHangHoa.putConstraint(SpringLayout.NORTH, spSoLuong, 40, SpringLayout.NORTH, HangHoaPanel);

        layoutHangHoa.putConstraint(SpringLayout.WEST, lbThanhTien, 20, SpringLayout.EAST, spSoLuong);
        layoutHangHoa.putConstraint(SpringLayout.NORTH, lbThanhTien, 40, SpringLayout.NORTH, HangHoaPanel);

        layoutHangHoa.putConstraint(SpringLayout.WEST, txtThanhTien, 20, SpringLayout.EAST, lbThanhTien);
        layoutHangHoa.putConstraint(SpringLayout.NORTH, txtThanhTien, 40, SpringLayout.NORTH, HangHoaPanel);

        layoutHangHoa.putConstraint(SpringLayout.WEST, btnThem, 400, SpringLayout.WEST, HangHoaPanel);
        layoutHangHoa.putConstraint(SpringLayout.NORTH, btnThem, 90, SpringLayout.NORTH, HangHoaPanel);

        layoutHangHoa.putConstraint(SpringLayout.WEST, btnSua, 90, SpringLayout.EAST, btnThem);
        layoutHangHoa.putConstraint(SpringLayout.NORTH, btnSua, 90, SpringLayout.NORTH, HangHoaPanel);

        layoutHangHoa.putConstraint(SpringLayout.WEST, btnXoa, 90, SpringLayout.EAST, btnSua);
        layoutHangHoa.putConstraint(SpringLayout.NORTH, btnXoa, 90, SpringLayout.NORTH, HangHoaPanel);
    }

    private void TableView(SpringLayout layout) {
        SpringLayout layoutTable = new SpringLayout();
        JPanel TablePanel = new JPanel();
        TablePanel.setLayout(layoutTable);
        TablePanel.setPreferredSize(new Dimension(1150, 250));
        this.add(TablePanel);
        Border border = BorderFactory.createLineBorder(Color.BLACK);
        TablePanel.setBorder(border);
        JLabel lbvnd = new JLabel("VND");

        String PathIn = "D:\\on_java\\Supermarket\\icon\\in.png";
        ImageIcon IconIn = SetIcon.SetSize(PathIn, 20, 20);
        btnInHoaDon.setIcon(IconIn);

        TablePanel.add(jScrollPaneProductTable);
        TablePanel.add(lbTong);
        TablePanel.add(txtTong);
        TablePanel.add(btnInHoaDon);
        TablePanel.add(lbvnd);

        layout.putConstraint(SpringLayout.WEST, TablePanel, 10, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, TablePanel, 350, SpringLayout.NORTH, this);

        layoutTable.putConstraint(SpringLayout.WEST, jScrollPaneProductTable, 40, SpringLayout.WEST, TablePanel);
        layoutTable.putConstraint(SpringLayout.NORTH, jScrollPaneProductTable, 0, SpringLayout.NORTH, TablePanel);

        layoutTable.putConstraint(SpringLayout.WEST, btnInHoaDon, 450, SpringLayout.WEST, TablePanel);
        layoutTable.putConstraint(SpringLayout.NORTH, btnInHoaDon, 200, SpringLayout.NORTH, TablePanel);

        layoutTable.putConstraint(SpringLayout.WEST, lbTong, 195, SpringLayout.EAST, btnInHoaDon);
        layoutTable.putConstraint(SpringLayout.NORTH, lbTong, 200, SpringLayout.NORTH, TablePanel);

        layoutTable.putConstraint(SpringLayout.WEST, txtTong, 40, SpringLayout.EAST, lbTong);
        layoutTable.putConstraint(SpringLayout.NORTH, txtTong, 200, SpringLayout.NORTH, TablePanel);

        layoutTable.putConstraint(SpringLayout.WEST, lbvnd, 10, SpringLayout.EAST, txtTong);
        layoutTable.putConstraint(SpringLayout.NORTH, lbvnd, 200, SpringLayout.NORTH, TablePanel);
    }

    public void DisplayTenKH() {
        KhachHang kh = new KhachHang();
        BanHangDAO bhD = new BanHangDAO();

        ResultSet rs = bhD.getTenKH();

        ArrayList<String> TenKH = new ArrayList<>();

        try {
            while (rs.next()) {
                String Name = rs.getString("TENKHACHHANG");
                TenKH.add(Name);
            }
        } catch (Exception e) {

        }
        for (String name : TenKH) {
            cbTenKhachHang.addItem(name);
        }
    }

    public void DisplayMaHang() {
        BanHangDAO bhD = new BanHangDAO();
        ResultSet rs = bhD.getMaHang();

        ArrayList<Integer> MaHang = new ArrayList<>();
        try {
            while (rs.next()) {
                int Id = rs.getInt("ID");
                MaHang.add(Id);
            }
        } catch (Exception e) {
        }

        for (int Id : MaHang) {
            cbMaHang.addItem(Id);
        }
    }

    public void GetPrice(ItemEvent e) {
        int Id = 0;
        String Gia = "";
        String TenHangHoa = "";
        if (e.getStateChange() == ItemEvent.SELECTED && e.getSource() == cbMaHang) {
            // Lấy mã hàng được chọn
            Id = (int) cbMaHang.getSelectedItem();
        }
        BanHangDAO bhD = new BanHangDAO();
        ResultSet rs = bhD.getInfoProduct(Id);
        try {
            while (rs.next()) {
                Gia = rs.getString("DONGIA");
                TenHangHoa = rs.getString("TENHANGHOA");
                soluongkiemtra = rs.getInt("SOLUONG");
            }
        } catch (Exception ev) {
        }
        txtGiaBan.setText(Gia);
        h.setId(Id);
        h.setTenHangHoa(TenHangHoa);
    }

    public void GetInFoKH(ItemEvent e) {
        int IdKH = 0;
        if (e.getStateChange() == ItemEvent.SELECTED && e.getSource() == cbTenKhachHang) {
            String Name = (String) cbTenKhachHang.getSelectedItem();
            BanHangDAO bhD = new BanHangDAO();
            ResultSet rs = bhD.getInfoKH(Name);

            try {
                if (rs.next()) { // Kiểm tra xem ResultSet có hàng nào không
                    String DiaChi = rs.getString("DIACHI");
                    String DienThoai = rs.getString("SODIENTHOAI");
                    IdKH = rs.getInt("ID");
                    if (!txtDienThoai.getText().isEmpty()) {
                        txtDienThoai.setText("");
                    }
                    txtDiaChi.setText(DiaChi);
                    txtDienThoai.setText(DienThoai);
                    txtTenKhachHang.setText(Name);
                } else {
                    txtDiaChi.setText("");
                    txtDienThoai.setText("");
                    txtTenKhachHang.setText("");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            } finally {
                try {
                    if (rs != null) {
                        rs.close();
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
        kh.setId(IdKH);
    }

    public void updateThanhTien() {
        int SoLuong = (int) spSoLuong.getValue();
        if (SoLuong > soluongkiemtra) {
            JOptionPane.showMessageDialog(this, "So luong het!");
        } else {
            Float Gia = Float.parseFloat(txtGiaBan.getText());
            double ThanhTien = (SoLuong * Gia);
            txtThanhTien.setText(String.valueOf(ThanhTien));
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton btn = (JButton) e.getSource();
        if (btn.equals(btnThem)) {
            btnThem_actionperformed();
        } else if (btn.equals(btnSua)) {
            btnSua_actionperformed();
        } else if (btn.equals(btnXoa)) {
            btnXoa_actionperformed();
        } else if (btn.equals(btnLuuHoaDon)) {
            btnLuuHoaDon_actionperformed();
        } else if (btn.equals(btnInHoaDon)) {
            btnInHoaDon_actionperformed();
        } else if (btn.equals(btnTaoHoaDonMoi)) {
            btnTaoHoaDonMoi_actionperformed();
        } else {
            btnLuuKhachHang_actionperformed();
        }
    }

    public void btnThem_actionperformed() {
        int SL = (int) spSoLuong.getValue();
        BanHangDAO bhD = new BanHangDAO();
        if (SL != 0) {
            String TenKH = h.getTenHangHoa();
            String GiaBan = txtGiaBan.getText();
            String SoLuong = spSoLuong.getValue().toString();
            int idhh = (int) cbMaHang.getSelectedItem();
            double ThanhTien = Double.parseDouble(txtThanhTien.getText());

            DefaultTableModel model = (DefaultTableModel) productTable.getModel();
            int rowCount = model.getRowCount();
            boolean productExists = false;

            // Kiểm tra xem sản phẩm đã tồn tại trong bảng hay không
            for (int row = 0; row < rowCount; row++) {
                int existingProductId = (int) model.getValueAt(row, 0);
                if (existingProductId == idhh) {
                    // Sản phẩm đã tồn tại, cập nhật số lượng và tính lại thành tiền
                    int existingQuantity = (int) model.getValueAt(row, 3);
                    int newQuantity = existingQuantity + SL;
                    double newTotalPrice = newQuantity * Double.parseDouble(GiaBan);
                    // Cập nhật số lượng và thành tiền trong bảng
                    model.setValueAt(newQuantity, row, 3);
                    model.setValueAt(newTotalPrice, row, 4);
                    setTotal();
                    bhD.SuaCTPX(idhh, newQuantity);
                    productExists = true;
                    break;
                }
            }
            // Nếu sản phẩm chưa tồn tại trong bảng, thêm mới
            if (!productExists) {
                model.addRow(new Object[]{idhh, TenKH, GiaBan, SL, ThanhTien});
                setTotal();
                // Gọi hàm thêm vào cơ sở dữ liệu
                int sl = Integer.parseInt(SoLuong);
                float dg = Float.parseFloat(GiaBan);

                int rs = bhD.ThemCTPX(h.getId(), sl, dg);
                if (rs != 0) {
                    JOptionPane.showMessageDialog(this, "Thêm thành công");
                } else {
                    JOptionPane.showMessageDialog(this, "Thêm không thành công");
                }

                spSoLuong.setValue(0);
                txtThanhTien.setText("");
            }

        } else {
            JOptionPane.showMessageDialog(this, "Số lượng không được để trống!");
        }
    }

    public void setTotal() {
        double tong = 0;
        DefaultTableModel model = (DefaultTableModel) productTable.getModel();
        int rowCount = model.getRowCount();
        for (int i = 0; i < rowCount; i++) {
            String thanhtien = productTable.getValueAt(i, 4).toString();
            double tt = Double.parseDouble(thanhtien);
            tong += tt;
        }
        txtTong.setEnabled(true);
        txtTong.setText(String.valueOf(tong));
    }

    public void btnSua_actionperformed() {
        int SL = (int) spSoLuong.getValue();
        if (SL != 0) {
            String TenKH = h.getTenHangHoa();
            String GiaBan = txtGiaBan.getText();
            String SoLuong = spSoLuong.getValue().toString();
            double ThanhTien = Double.parseDouble(txtThanhTien.getText());
            productTableModel = (DefaultTableModel) productTable.getModel();
            productTableModel.setValueAt(SL, SelectRow, 3);
            productTableModel.setValueAt(ThanhTien, SelectRow, 4);
            setTotal();
            int idhh = Integer.parseInt(productTable.getValueAt(SelectRow, 0).toString());
            BanHangDAO bhD = new BanHangDAO();
            int rs = bhD.SuaCTPX(idhh, SL);
            if (rs != 0) {
                JOptionPane.showMessageDialog(this, "Sửa thành công");

            } else {
                JOptionPane.showMessageDialog(this, "Sửa không thành công");
            }
            spSoLuong.setValue(0);
            txtThanhTien.setText("");
            btnSua.setEnabled(false);
            btnXoa.setEnabled(false);
        } else {
            JOptionPane.showMessageDialog(this, "Số lượng không được để trống!");
        }
    }

    public void btnXoa_actionperformed() {
        int confirmed = JOptionPane.showConfirmDialog(this,
                "Bạn có muốn xóa sản phẩm này không?", "Xác nhận xóa đơn hàng",
                JOptionPane.YES_NO_OPTION);
        if (confirmed == JOptionPane.YES_OPTION) {
            int idhh = Integer.parseInt(productTable.getValueAt(SelectRow, 0).toString());
            BanHangDAO bhD = new BanHangDAO();
            int rs = bhD.XoaCTPX(idhh);
            if (rs != 0) {
                JOptionPane.showMessageDialog(this, "Xóa thành công");

            } else {
                JOptionPane.showMessageDialog(this, "Xóa không thành công");
            }
            productTableModel = (DefaultTableModel) productTable.getModel();
            productTableModel.removeRow(SelectRow);
            setTotal();
            spSoLuong.setValue(0);
            txtThanhTien.setText("");
            btnXoa.setEnabled(false);
            btnSua.setEnabled(false);
        }

    }

    public void btnLuuHoaDon_actionperformed() {
        BanHangDAO bhD = new BanHangDAO();
        ResultSet rss = bhD.getIDPX();
        try {
            if (rss.next()) {
                IDPX = rss.getInt("ID");
            }
        } catch (Exception e) {
        }
        if (kh.getId() == 0) {
            JOptionPane.showMessageDialog(this, "Chưa lấy được id");
        } else {
            int rs = bhD.LuuPhieuXuat(Integer.parseInt(SessionManager.getUserId()), kh.getId(), Float.parseFloat(txtTong.getText()));
            if (rs != 0) {
                JOptionPane.showMessageDialog(this, "Lưu phiếu xuất thành công");
            } else {
                JOptionPane.showMessageDialog(this, "Lưu phiếu xuất không thành công");
            }
        }
        hoadon.setId(IDPX);
    }

    public void btnInHoaDon_actionperformed() {
        HoaDon hd = new HoaDon();
        hd.hienthiThongTin(hoadon.getId());
        btnTaoHoaDonMoi_actionperformed();
    }

    public void btnTaoHoaDonMoi_actionperformed() {
        txtTenKhachHang.setText("");
        txtDiaChi.setText("");
        txtDienThoai.setText("");
        txtGiaBan.setText("");
        txtThanhTien.setText("");
        txtTong.setText("");
        productTableModel = (DefaultTableModel) productTable.getModel();
        productTableModel.setRowCount(0);
        BanHangDAO bhD = new BanHangDAO();
        bhD.XoaPhieuXuat();
    }

    public void btnLuuKhachHang_actionperformed() {
        String dienthoai = txtDienThoai.getText();
        String ten = txtTenKhachHang.getText();
        String diachi = txtDiaChi.getText();
        boolean kt = false;
        KhachHangDAO khD = new KhachHangDAO();
        kt = khD.isSDTExists(dienthoai);
        if (kt == true) {
            JOptionPane.showMessageDialog(this, "Số điện thoại đã tồn tại trong cơ sở dữ liệu!");
        } else {
            int rs = khD.ThemKHPX(ten, diachi, dienthoai);
            if (rs != 0) {
                JOptionPane.showMessageDialog(this, "Thêm mới khách hàng thành công");
            } else {
                JOptionPane.showMessageDialog(this, "Thêm mới khách hàng không thành công");
            }
        }
        DisplayTenKH();

    }

}
