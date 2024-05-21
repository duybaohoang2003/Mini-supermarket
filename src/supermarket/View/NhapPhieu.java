/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package supermarket.View;

/**
 *
 * @author DELL
 */
import Dao.ChiTietPNDAO;
import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;
import Dao.HangHoaDao;
import Dao.PhieuNhapDAO;
import Model.SessionManager;
import Model.chitietphieunhap;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.text.DecimalFormat;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import supermarket.SetIcon;
import java.util.Date;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import Model.phieunhap;

public class NhapPhieu extends JPanel {
    private DefaultTableModel model;
    private DefaultTableModel model2;
    private JTable table; 
    private JTable table2; 
    
    private JLabel lbTimKiem;
    private JLabel lbSoLuong;
    private JLabel lbMaPN;
    private JLabel lbNCC;
    private JLabel lbTongtien;
    private JLabel lbSotien;
    private JLabel lblDonGia;
    private JLabel lblMaPN;
    
    
    private JTextField txtTimKiem;
    private JTextField txtSoLuong;
    private JComboBox<String> cbbNCC;
    private JComboBox<String> cbbNhomhang;
    
    
    private JSpinner SpnSoLuong;
    private JSpinner SpnDongia;
    
    private JButton btnTimKiem;
    private JButton btnSoLuong;
    private JButton btnSuasoluong;
    private JButton btnXoasanpham;
    private JButton btnNhaphang;
    private JButton btnCapNhat;
    
    private JPanel mainPanel;
    private JPanel panel1;
    private JPanel panel2;
    
    private HangHoaDao HangHoaDao;
    private PhieuNhapDAO PNDAO;
    private ChiTietPNDAO chiTietPNDAO;
    private phieunhap phieuNhap;
    private String Namehanghoa;
    private String Nhomghang;

    public NhapPhieu() {
        initComponents();
    }

    private void initComponents() {
        PNDAO = new PhieuNhapDAO();
        Namehanghoa="";
        Nhomghang="";
        DecimalFormat decimalFormat = new DecimalFormat("#,##0");
        String PathThem = "D:\\on_java\\Supermarket\\icon\\plus.png";
        String PathDelete = "D:\\on_java\\Supermarket\\icon\\delete.png";
        String PathUpdate="D:\\on_java\\Supermarket\\icon\\updated.png";
        String PathSearch="D:\\on_java\\Supermarket\\icon\\search.png";
        String PathSua="D:\\on_java\\Supermarket\\icon\\pen.png";
        String PathNhap="D:\\on_java\\Supermarket\\icon\\add-file.png";
        String PathEx="D:\\on_java\\Supermarket\\icon\\add-file.png";

        ImageIcon IconEx = SetIcon.SetSize(PathEx, 20, 20);
        ImageIcon IconNhap = SetIcon.SetSize(PathNhap, 20, 20);
        ImageIcon IconThem = SetIcon.SetSize(PathThem, 20, 20);
        ImageIcon IconDelete = SetIcon.SetSize(PathDelete, 20, 20);
        ImageIcon IconUpdate = SetIcon.SetSize(PathUpdate, 20, 20);
        ImageIcon IconSearch = SetIcon.SetSize(PathSearch, 20, 20);
        ImageIcon IconSua = SetIcon.SetSize(PathSua, 20, 20);

        // Tạo panel chính
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(1, 2)); // Sử dụng GridLayout để chia panel chính thành 2 cột

        // Tạo panel 1 và panel 2
        panel1 = new JPanel();
        panel1.setBackground(Color.WHITE);
        panel2 = new JPanel();
        panel2.setBackground(Color.PINK);

        // Thêm panel1 và panel2 vào panel chính
        mainPanel.add(panel1);
        mainPanel.add(panel2);
        
        //Thành phần trong panel 1
        SpringLayout layout1 = new SpringLayout();
        panel1.setLayout(layout1);

        // Panel chứa thanh tìm kiếm
        JPanel pnTimKiem = new JPanel(new FlowLayout(FlowLayout.LEFT));
        txtTimKiem = new JTextField();
        cbbNhomhang=new JComboBox();
        cbbNhomhang.addItem("");
        for (String item : PNDAO.layDanhSachTenNhomHang()) {
            cbbNhomhang.addItem(item);
        }
        txtTimKiem.setPreferredSize(new Dimension(300, 30)); // Chiều rộng là 200 pixels, chiều cao là 30 pixels
        btnTimKiem = new JButton("Tìm kiếm");
        btnTimKiem.setIcon(IconSearch);
        pnTimKiem.add(txtTimKiem);
        pnTimKiem.add(btnTimKiem);
        pnTimKiem.add(cbbNhomhang);
        panel1.add(pnTimKiem);

        // Panel chứa bảng
        JPanel pnTable = new JPanel();
        pnTable.setLayout(new BorderLayout());
        model = new DefaultTableModel();
        model.addColumn("Id");
        model.addColumn("Id_NhomHang");
        model.addColumn("TenHangHoa");
        model.addColumn("DVT");
        model.addColumn("DonGia");
        table = new JTable(model);
        
        JScrollPane scrollPane = new JScrollPane(table);
        pnTable.add(scrollPane);
        panel1.add(pnTable);
        
        // Panel chứa số lượng
        JPanel pn3 = new JPanel();
        lbSoLuong = new JLabel("Số Lượng");
        SpinnerNumberModel modelSoluong = new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1);
        SpnSoLuong = new JSpinner(modelSoluong);
        SpnSoLuong.setPreferredSize(new Dimension(100, 30));
        btnSoLuong = new JButton("Thêm");
        btnSoLuong.setIcon(IconThem);
        lblDonGia=new JLabel("Đơn giá :");
        SpinnerNumberModel modelDonGia = new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1);
        SpnDongia=new JSpinner(modelDonGia);
        SpnDongia.setPreferredSize(new Dimension(200,30));
        pn3.add(lblDonGia);
        pn3.add(SpnDongia);
        pn3.add(lbSoLuong);
        pn3.add(SpnSoLuong);
        pn3.add(btnSoLuong);
        
        panel1.add(pn3);

        // Đặt ràng buộc cho panel chứa thanh tìm kiếm
        layout1.putConstraint(SpringLayout.WEST, pnTimKiem, 10, SpringLayout.WEST, panel1);
        layout1.putConstraint(SpringLayout.NORTH, pnTimKiem, 10, SpringLayout.NORTH, panel1);
        layout1.putConstraint(SpringLayout.EAST, pnTimKiem, -10, SpringLayout.EAST, panel1);

        // Đặt ràng buộc cho panel chứa bảng
        layout1.putConstraint(SpringLayout.NORTH, pnTable, 10, SpringLayout.SOUTH, pnTimKiem); // Cách bên dưới của pnTimKiem 10 pixels
        layout1.putConstraint(SpringLayout.WEST, pnTable, 10, SpringLayout.WEST, panel1);
        layout1.putConstraint(SpringLayout.EAST, pnTable, -10, SpringLayout.EAST, panel1); // Cách bên phải của panel1 10 pixels
        layout1.putConstraint(SpringLayout.SOUTH, pnTable, -10, SpringLayout.NORTH, pn3); // Cách bên trên của pnSoLuong 10 pixels

        // Đặt ràng buộc cho panel chứa số lượng
        layout1.putConstraint(SpringLayout.WEST, pn3, 10, SpringLayout.WEST, panel1); // Cách bên trái của panel1 10 pixels
        layout1.putConstraint(SpringLayout.SOUTH, pn3, -10, SpringLayout.SOUTH, panel1); // Cách bên dưới của panel1 10 pixels
        layout1.putConstraint(SpringLayout.EAST, pn3, -10, SpringLayout.EAST, panel1);
        
        //panel2
        SpringLayout layout2 = new SpringLayout();
        panel2.setLayout(layout2);

        JPanel panel21 = new JPanel();
        JPanel panel22 = new JPanel();
        panel22.setPreferredSize(new Dimension(0, 380));

        JPanel panel23 = new JPanel();
        JPanel panel24 = new JPanel();

        // Đặt màu nền cho từng panel
//        panel21.setBackground(Color.yellow);
//        panel22.setBackground(Color.orange);
//        panel23.setBackground(Color.green);
//        panel24.setBackground(Color.darkGray);

        // Thêm các panel vào panel2
        panel2.add(panel21);
        panel2.add(panel22);
        panel2.add(panel23);
        panel2.add(panel24);

        // Đặt ràng buộc cho panel21
        layout2.putConstraint(SpringLayout.WEST, panel21, 10, SpringLayout.WEST, panel2);
        layout2.putConstraint(SpringLayout.NORTH, panel21, 10, SpringLayout.NORTH, panel2);
        layout2.putConstraint(SpringLayout.EAST, panel21, -10, SpringLayout.EAST, panel2);

        // Đặt ràng buộc cho panel22
        layout2.putConstraint(SpringLayout.WEST, panel22, 10, SpringLayout.WEST, panel2);
        layout2.putConstraint(SpringLayout.NORTH, panel22, 10, SpringLayout.SOUTH, panel21);
        layout2.putConstraint(SpringLayout.EAST, panel22, -10, SpringLayout.EAST, panel2);

        // Đặt ràng buộc cho panel23
        layout2.putConstraint(SpringLayout.WEST, panel23, 10, SpringLayout.WEST, panel2);
        layout2.putConstraint(SpringLayout.NORTH, panel23, 10, SpringLayout.SOUTH, panel22);
        layout2.putConstraint(SpringLayout.EAST, panel23, -10, SpringLayout.EAST, panel2);

        // Đặt ràng buộc cho panel24
        layout2.putConstraint(SpringLayout.WEST, panel24, 10, SpringLayout.WEST, panel2);
        layout2.putConstraint(SpringLayout.NORTH, panel24, 10, SpringLayout.SOUTH, panel23);
        layout2.putConstraint(SpringLayout.EAST, panel24, -10, SpringLayout.EAST, panel2);
        layout2.putConstraint(SpringLayout.SOUTH, panel24, -10, SpringLayout.SOUTH, panel2);
        
//        panel21
        lbMaPN = new JLabel("Mã Phiếu Nhập:");
        lbNCC = new JLabel("Nhà cung cấp:");
        if(PNDAO.PhieuChuaXuat()==-1){
            lblMaPN = new JLabel("Chưa có phiếu");
        }else{
            lblMaPN = new JLabel(""+PNDAO.PhieuChuaXuat());
        }
        cbbNCC = new JComboBox();
        // Thêm dữ liệu từ một mảng
        for (String item : PNDAO.layDanhSachTenNCC()) {
            cbbNCC.addItem(item);
        }

        panel21.setLayout(new GridBagLayout()); // Sử dụng GridBagLayout để có thể điều chỉnh linh hoạt kích thước và vị trí của các thành phần

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5); // Khoảng cách giữa các thành phần
        gbc.fill = GridBagConstraints.HORIZONTAL; // Đảm bảo rằng thành phần sẽ mở rộng ngang

        panel21.add(lbMaPN, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0; // Mở rộng theo chiều ngang
        panel21.add(lblMaPN, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0; 
        panel21.add(lbNCC, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0; // Mở rộng theo chiều ngang
        panel21.add(cbbNCC, gbc);



        
        //panel22
        model2 = new DefaultTableModel();
        model2.addColumn("ID Hàng Hóa");
        model2.addColumn("Hàng hóa");
        model2.addColumn("Đơn giá");
        model2.addColumn("Số lượng");
        model2.addColumn("Thành tiền");
        table2 = new JTable(model2);
        int columnIndex = 0; // Ví dụ, giả sử cột "ID Hàng Hóa" là cột đầu tiên

        // Ẩn cột "ID Hàng Hóa"
        table2.getColumnModel().getColumn(columnIndex).setMinWidth(0);
        table2.getColumnModel().getColumn(columnIndex).setMaxWidth(0);
        table2.getColumnModel().getColumn(columnIndex).setWidth(0);
        JScrollPane scrollPane2 = new JScrollPane(table2);
        panel22.setLayout(new BorderLayout()); // Đặt layout cho panel22
        panel22.add(scrollPane2, BorderLayout.CENTER);
        
        //panel23
        btnSuasoluong=new JButton("Sửa số lượng");
        btnSuasoluong.setIcon(IconSua);
        btnXoasanpham=new JButton("Xóa Sản phẩm");
        btnXoasanpham.setIcon(IconDelete);
        btnCapNhat=new JButton("Cập nhật");
        btnCapNhat.setIcon(IconUpdate);
        btnCapNhat.setEnabled(false);
        panel23.setLayout(new FlowLayout(FlowLayout.RIGHT));
        panel23.add(btnSuasoluong); 
        panel23.add(btnCapNhat);
        panel23.add(btnXoasanpham); 
        
        //panel24
        lbTongtien = new JLabel("Tổng tiền");
        lbSotien = new JLabel("Số tiền");
        // Thiết lập font cho lbTongtien
        Font fontTongtien = new Font("Arial", Font.BOLD, 18); // Ví dụ: Arial, đậm, cỡ 18
        lbTongtien.setFont(fontTongtien);

        // Thiết lập font cho lbSotien
        Font fontSotien = new Font("Arial", Font.BOLD, 18); // Ví dụ: Arial, đậm, cỡ 18
        lbSotien.setFont(fontSotien);
        btnNhaphang = new JButton("Nhập hàng");
        btnNhaphang.setIcon(IconNhap);

        panel24.add(lbTongtien);
        panel24.add(lbSotien);
        panel24.add(btnNhaphang);
        SpringLayout layout24 = new SpringLayout();
        panel24.setLayout(layout24);

        // Đặt ràng buộc cho lbTongtien
        layout24.putConstraint(SpringLayout.WEST, lbTongtien, 10, SpringLayout.WEST, panel24);
        layout24.putConstraint(SpringLayout.NORTH, lbTongtien, 10, SpringLayout.NORTH, panel24);

        // Đặt ràng buộc cho lbSotien
        layout24.putConstraint(SpringLayout.WEST, lbSotien, 10, SpringLayout.EAST, lbTongtien); 
        layout24.putConstraint(SpringLayout.VERTICAL_CENTER, lbSotien, 0, SpringLayout.VERTICAL_CENTER, lbTongtien);

        // Đặt ràng buộc cho btnNhaphang
        layout24.putConstraint(SpringLayout.EAST, btnNhaphang, -10, SpringLayout.EAST, panel24); 
        layout24.putConstraint(SpringLayout.VERTICAL_CENTER, btnNhaphang, 0, SpringLayout.VERTICAL_CENTER, panel24);

        // Đặt ràng buộc cho lbTongtien
        //thêm sản phẩm
        
        btnSoLuong.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Lấy số lượng từ JTextField txtSoLuong
                int soLuong = (int) SpnSoLuong.getValue();
                int Dongia = (int) SpnDongia.getValue();
                float thanhtien=Dongia*soLuong;
                // Kiểm tra xem JTextField có được điền dữ liệu hay không
                if (soLuong == 0 || Dongia==0) {
                    JOptionPane.showMessageDialog(null, "Vui lòng nhập số lượng.");
                } else {
                    // Lấy ID của dòng đã chọn từ bảng
                    int selectedRow = table.getSelectedRow();
                    if (selectedRow != -1) {
                        // Lấy giá trị của cột ID từ hàng đã chọn
                        int idValue = Integer.parseInt(table.getValueAt(selectedRow, 0).toString());
                        String  NameHangHoa = table.getValueAt(selectedRow, 2).toString();
                        // Thực hiện thêm dữ liệu vào chi tiết phiếu nhập với ID và số lượng đã lấy được
                        PNDAO = new PhieuNhapDAO();
                        boolean blMahang =PNDAO.xuLyPhieuNhap(idValue,soLuong,Dongia);
                        if(blMahang==true){
                            Object[] data = {idValue,NameHangHoa,decimalFormat.format(Dongia), decimalFormat.format(soLuong),decimalFormat.format(thanhtien)};
                            model2.addRow(data);
                        }
                        
                    }
//                    resetPanel();
                }
                double tongThanhTien = tinhTongThanhTien(table2);
                lbSotien.setText(decimalFormat.format(tongThanhTien)+" VND");
                lblMaPN.setText(""+PNDAO.PhieuChuaXuat());
                btnNhaphang.setEnabled(true);
                SpnDongia.setValue(0);
                SpnSoLuong.setValue(0);
            }
        });
        //xóa sản phẩm
        // Thêm ActionListener cho nút btnXoasanpham
        btnXoasanpham.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int choice = JOptionPane.showConfirmDialog(null, "Bạn có chắc muốn xóa không?", "Xác nhận", JOptionPane.YES_NO_OPTION);
                if (choice == JOptionPane.YES_OPTION) {
                    int selectedRow = table2.getSelectedRow();

                    // Kiểm tra xem có hàng được chọn không
                    if (selectedRow != -1) {
                        int idValue = Integer.parseInt(table2.getValueAt(selectedRow, 0).toString());
                        // Xóa dòng khỏi mô hình bảng
                        chiTietPNDAO = new ChiTietPNDAO();
                        chiTietPNDAO.xoaSanPham(idValue);
                        model2.removeRow(selectedRow);
                    } else {
                        JOptionPane.showMessageDialog(null, "Vui lòng chọn dòng để xóa.", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                    }
                    double tongThanhTien = tinhTongThanhTien(table2);
                    lbSotien.setText(decimalFormat.format(tongThanhTien)+" VND");
                }
            }
        });
        
        btnTimKiem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Nhomghang="";
                Namehanghoa=txtTimKiem.getText();
                HangHoaDao = new HangHoaDao();
                HangHoaDao.displayHangHoaTable(table,Namehanghoa,Nhomghang);
            }
        });
        
        cbbNhomhang.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                txtTimKiem.setText("");
                Namehanghoa=txtTimKiem.getText();
                Nhomghang=(String) cbbNhomhang.getSelectedItem();
                HangHoaDao.displayHangHoaTable(table,Namehanghoa,Nhomghang);
                
            }
        });
        
        btnSuasoluong.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Lấy hàng được chọn trong bảng
                int selectedRow = table2.getSelectedRow();
                if (selectedRow != -1) {
                    btnSuasoluong.setEnabled(false);
                    btnCapNhat.setEnabled(true);
                    btnSoLuong.setEnabled(false);
                    btnXoasanpham.setEnabled(false);
                    int idValue = Integer.parseInt(table.getValueAt(selectedRow, 0).toString());
                    // Hiển thị thông tin của hàng được chọn lên các thành phần giao diện người dùng
                    Object id_hanghoa = table2.getValueAt(selectedRow, 0);
                    Object donGia = table2.getValueAt(selectedRow, 2);
                    Object soLuong = table2.getValueAt(selectedRow, 3);

                    // Hiển thị dữ liệu của dòng được chọn
                    int soLuongInt = Integer.parseInt(soLuong.toString().replace(",", ""));
                    double donGiaDouble = Double.parseDouble(donGia.toString().replace(",", ""));
                    SpnSoLuong.setValue(soLuongInt);
                    SpnDongia.setValue(donGiaDouble);
                } else {
                    JOptionPane.showMessageDialog(null, "Vui lòng chọn một hàng để sửa.");
                }
            }
        });

        // Lắng nghe sự kiện khi nút cập nhật được nhấn
        btnCapNhat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnSuasoluong.setEnabled(true);
                btnCapNhat.setEnabled(false);
                btnSoLuong.setEnabled(true);
                btnXoasanpham.setEnabled(true);
                // Lấy thông tin mới từ các thành phần giao diện người dùng
                int selectedRow = table2.getSelectedRow();
                if (selectedRow != -1) {
                    try {
                        int idValue = Integer.parseInt(table2.getValueAt(selectedRow, 0).toString());
                        // Loại bỏ dấu phẩy và chuyển đổi chuỗi thành số double
                        String dongiaStr = SpnDongia.getValue().toString().replace(",", "");
                        double dongiaDouble = Double.parseDouble(dongiaStr);
                        int soLuong = (int) SpnSoLuong.getValue();
                        double thanhtien = dongiaDouble * soLuong;

                        // Format đơn giá với phân cách hàng nghìn
                        DecimalFormat df = new DecimalFormat("#,###.##");
                        String formattedDonGia = df.format(dongiaDouble);

                        // Cập nhật dữ liệu của dòng đã chọn trong model của bảng
                        model2.setValueAt(formattedDonGia, selectedRow, 2);
                        model2.setValueAt(decimalFormat.format(soLuong), selectedRow, 3);
                        model2.setValueAt(decimalFormat.format(thanhtien), selectedRow, 4);
                        // Cập nhật lại hiển thị của bảng
                        table2.repaint();
                        resetSpinners();
                        chiTietPNDAO = new ChiTietPNDAO();
                        chiTietPNDAO.suaSanPham(idValue, soLuong, dongiaDouble);
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Vui lòng nhập đúng định dạng cho giá và số lượng.");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Vui lòng chọn một hàng để cập nhật.");
                }
                double tongThanhTien = tinhTongThanhTien(table2);
                lbSotien.setText(decimalFormat.format(tongThanhTien)+" VND");
                btnNhaphang.setEnabled(true);
            }
        });
        btnNhaphang.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int choice = JOptionPane.showConfirmDialog(null, "Bạn có chắc muốn nhập hàng?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        
                // Kiểm tra lựa chọn của người dùng
                if (choice == JOptionPane.YES_OPTION) {
                    int ID=PNDAO.PhieuChuaXuat();
                    int IdNV= Integer.parseInt(SessionManager.getUserId());
                    String TenNCC = (String) cbbNCC.getSelectedItem();
                    int IdNCC=PNDAO.SelectIDNCC(TenNCC);
                    Date NgayNhap=new Date();
                    float TongTien=chiTietPNDAO.getTotalAmount();
                    int status=1;
                    phieuNhap = new phieunhap();
                    phieuNhap.setId(ID);
                    phieuNhap.setIdNhanVien(IdNV);
                    phieuNhap.setIdNCC(IdNCC);
                    phieuNhap.setNgayNhap(NgayNhap);
                    phieuNhap.setTongTien(TongTien);
                    phieuNhap.setStatus(status);
                    // Lấy thông tin mới từ các thành phần giao diện người dùng
                    PNDAO.updatePhieuNhap(phieuNhap);
                    DefaultTableModel model = (DefaultTableModel) table2.getModel();
                    model.setRowCount(0); // Xóa tất cả các dòng trong mô hình
                    lblMaPN = new JLabel("Chưa có phiếu");
                }
                
            }
        });



        // Hiển thị dữ liệu từ HangHoaDAO lên bảng
        HangHoaDao = new HangHoaDao();
        HangHoaDao.displayHangHoaTable(table,Namehanghoa,Nhomghang);
        
        // Hiển thị dữ liệu từ CHitietPNDAO lên bảng
        chiTietPNDAO = new ChiTietPNDAO();
        chiTietPNDAO.displayChiTietPN(table2,model2,PNDAO.PhieuChuaXuat());
        double tongThanhTien = tinhTongThanhTien(table2);
        lbSotien.setText(decimalFormat.format(tongThanhTien)+" VND");
        if (table2.getRowCount() == 0) {
            btnNhaphang.setEnabled(false);
            System.out.println(table2.getRowCount());
        } else {
            btnNhaphang.setEnabled(true);
        }
        
        
        // Thêm panel chính vào JFrame
        add(mainPanel);
        mainPanel.setPreferredSize(new Dimension(1190, 600));
    }
    
    
    private void resetSpinners() {
        // Đặt lại giá trị mặc định cho spnDongia và spnSoluong
        SpnDongia.setValue(0);
        SpnSoLuong.setValue(0);
    }
    public double tinhTongThanhTien(JTable table) {
        double tongThanhTien=0;
        int rowCount = table.getRowCount();

        for (int i = 0; i < rowCount; i++) {
            double thanhTien = Double.parseDouble(table.getValueAt(i, 4).toString().replace(",", ""));
            tongThanhTien += thanhTien;
        }

        return tongThanhTien;
    }
    
    private void resetTable() {
        // Remove all rows from the model
        model.setRowCount(0);

        // Add new data (if needed)
        // For example:
        HangHoaDao = new HangHoaDao();
        HangHoaDao.displayHangHoaTable(table,Namehanghoa,Nhomghang);
    }


}
