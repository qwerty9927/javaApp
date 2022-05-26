package task1.GUI;


import task1.BUS.*;
import task1.BUS.KhachHangBUS;
import task1.BUS.SanPhamBUS;
import task1.DTO.CTHDonDTO;
import task1.DTO.HoaDonDTO;
import task1.DTO.KhachHangDTO;
import task1.DTO.SanPhamDTO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Vector;

public class BanHangGUI extends JPanel{
    private SanPhamBUS spBUS = new SanPhamBUS();
    private HoaDonBUS hdBUS = new HoaDonBUS();
    private CTHDonBUS ctBUS = new CTHDonBUS();
    private KhachHangBUS khBUS = new KhachHangBUS();
    private JTextField txtTongTien,txtMaHD, txtMaNV, txtMaKH, txtTenKH;
    private ArrayList<CTHDonDTO> dsct = new ArrayList<>();
    private ArrayList<HoaDonDTO> dshd = new ArrayList<>();
    private ArrayList<KhachHangDTO> dskh = new ArrayList<>();
    private JPanel panelLoaiSP, panelBanHang, panelSP;
    private JScrollPane scrlSanPham, PaneSP;
    private String MaSP,TenSP,MoTa;
    private int TongTien = 0;
    private int Gia;
    private String kind = "1";
    public static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss dd-MM-yyyy");
    private JLabel[] lblLoaiSanPham;
    private JButton[] btnFunctions;
    private JTable TableSP, tableGioHang;
    private String defaultColor = "#004D40";
    private DefaultTableModel tmDanhSachSP, tmGioHang;
    private String[] ListIcon = {"GUI/image/icons8-beef-burger-64.png",
                                    "GUI/image/icons8-drink-60.png",
                                        "GUI/image/icons8-cake-64.png",
                                        };
    private String[] FunctionIcon = {"GUI/image/icons8-add-50.png",
            "GUI/image/icons8-edit-50.png",
            "GUI/image/icons8-remove-50.png",
            "GUI/image/icons8-paid-bill-50.png"};
    private String[] ListFunction = {"Thêm vào hóa đơn","Sửa số lượng","Xóa khỏi hóa đơn","Thanh Toán"};
    private String[] header = {"Hình","Mã Sản Phẩm","Tên Sản Phẩm","Giá","Số lượng"};
    private String headerGioHang[] = {"Mã sản phẩm","Tên","Giá","Số lượng"};
    private int ThanhChonSPHeight = 80, ThanhChonSpWidth = 80;
    private int txtInfoWidth =300, txtInfoHeight =50;
    private NumberFormat VND = NumberFormat.getCurrencyInstance(new Locale("vi","VN"));

    public BanHangGUI(){
        init();
    }
    public void init(){
        this.setLayout(new BorderLayout());
        this.add(BorderLayout.WEST,ThanhChonSP());
        this.add(BorderLayout.CENTER,PaneSP());
        this.add(BorderLayout.EAST,ThanhThaoTac());
        this.add(BorderLayout.SOUTH,gioHang());
        this.setVisible(true);
    }
    //Thanh các thao tác nằm bên phải
    public JPanel ThanhThaoTac(){
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        txtMaHD = new JTextField(hdBUS.initMaHD());
        txtMaHD.setBounds(10,10,txtInfoWidth - 170,txtInfoHeight);
        txtMaHD.setBorder(BorderFactory.createTitledBorder("Mã hóa đơn: "));
        txtMaHD.setEnabled(false);

        txtMaNV = new JTextField(LoginGUI.manvTK);
        txtMaNV.setBounds(160,10,txtInfoWidth - 170,txtInfoHeight);
        txtMaNV.setBorder(BorderFactory.createTitledBorder("Mã Nhân viên: "));
        txtMaNV.setEnabled(false);

        txtMaKH = new JTextField();
        txtMaKH.setBounds(10,80,txtInfoWidth- 170,txtInfoHeight);
        txtMaKH.setBorder(BorderFactory.createTitledBorder("Nhập mã Khách hàng: "));

        //Tan
        txtMaKH.setEnabled(false);
        JButton btnShow = new JButton("...");
        btnShow.setBounds(160, 80, 50, 50);
        btnShow.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                SubKhachHangGUI snv = new SubKhachHangGUI(txtMaKH, txtTenKH);
            }
        });


        txtTenKH = new JTextField();
        txtTenKH.setBounds(10,150,txtInfoWidth,txtInfoHeight);
        txtTenKH.setBorder(BorderFactory.createTitledBorder("Tên khách hàng"));
        txtTenKH.setEnabled(false);

        txtTongTien = new JTextField("0");
        txtTongTien.setBounds(10,220,txtInfoWidth,txtInfoHeight);
        txtTongTien.setBorder(BorderFactory.createTitledBorder("Tổng tiền: "));
        txtTongTien.setEnabled(false);



        JPanel panelInfo = new JPanel();
        panelInfo.setBackground(Color.decode("#32a866"));
        panelInfo.setLayout(null);
        panelInfo.add(txtMaHD);
        panelInfo.add(txtMaNV);
        panelInfo.add(btnShow);
        panelInfo.add(txtTongTien);
        panelInfo.add(txtMaKH);
        panelInfo.add(txtTenKH);

        JPanel panelBtn = new JPanel();
        panelBtn.setLayout(new GridLayout(3,1,5,5));
        btnFunctions = new JButton[ListFunction.length];
        for(int i = 0; i<ListFunction.length;i++)
        {
            ImageIcon icon = new ImageIcon(FunctionIcon[i]);
            ImageIcon scaledIcon = new ImageIcon(icon.getImage().getScaledInstance(20,20, Image.SCALE_SMOOTH));
            btnFunctions[i]= new JButton(ListFunction[i],scaledIcon);
            btnFunctions[i].setAlignmentX(CENTER_ALIGNMENT);
            //0 = Thêm vào giỏ hàng, 1 = Sửa số lượng trên giỏ hàng, 2 = Xóa khỏi giỏ hàng,  3 = Thanh toán
            btnFunctions[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    //Nút thêm vào giỏ hàng
                    if(e.getSource()==btnFunctions[0]){
                        int sl = 0;
                        try{
                            int i = TableSP.getSelectedRow();
                            sl = Integer.parseInt(JOptionPane.showInputDialog(null, "Nhập số lượng sản phẩm :",JOptionPane.INFORMATION_MESSAGE));
                            MaSP = TableSP.getModel().getValueAt(i,1).toString();
                            TenSP = TableSP.getModel().getValueAt(i,2).toString();
                            Gia = Integer.parseInt(TableSP.getModel().getValueAt(i,3).toString());
                        }catch(IndexOutOfBoundsException ex)
                        {
                            JOptionPane.showMessageDialog(null, "Chưa chọn SP cần thêm");
                            return;
                        }catch(NumberFormatException ex) {
                            JOptionPane.showMessageDialog(null, "Chưa nhập số lượng cần thêm");
                            return;
                        }

                        //Kiểm tra đã có trong giỏ chưa
                        boolean flag = true;
                        for(CTHDonDTO sp : dsct )

                            if(sp.getMaSP().equals(MaSP)) //Có trong giỏ hàng
                            {
                                int old = sp.getSoLuong();
                                if(!spBUS.checkSL(MaSP, sl + old))
                                    return;
                                sp.setSoLuong(sl + old);
                                flag = false ;
                                break;
                            }

                        if(flag) {
                            if (!spBUS.checkSL(MaSP, sl))
                                return;
                            dsct.add(new CTHDonDTO(txtMaHD.getText(), MaSP, TenSP, sl, Gia));
                        }
                        capNhatGioHang(tmGioHang, dsct);
                        txtTongTien.setText(String.valueOf(sumHD()));
                    }
                    //Nút sửa số lượng trên giỏ hàng
                    if(e.getSource()==btnFunctions[1]){
                        try{
                            int i = tableGioHang.getSelectedRow();

                            String masp = tableGioHang.getModel().getValueAt(i,0).toString();
                            int sl = Integer.parseInt(JOptionPane.showInputDialog(null,"Nhập số lượng sản phẩm:"));
                            while(!spBUS.checkSL(masp,sl))
                                sl = Integer.parseInt(JOptionPane.showInputDialog(null,"Nhập số lượng sản phẩm:"));
                            for(CTHDonDTO ct : dsct) {
                                if (ct.getMaSP().equals(masp))
                                    ct.setSoLuong(sl);
                                capNhatGioHang(tmGioHang, dsct);
                                txtTongTien.setText(String.valueOf(sumHD()));
                            }
                        }catch (IndexOutOfBoundsException ex){
                            JOptionPane.showMessageDialog(null,"Chưa chọn sản phẩm cần sửa");
                        }
                    }
                    //Nút xóa trên giỏ hàng
                    if(e.getSource()==btnFunctions[2]){
                        {
                            try
                            {
                                int i = tableGioHang.getSelectedRow();
                                dsct.remove(i);
                                tmGioHang.removeRow(i);
                                txtTongTien.setText(String.valueOf(sumHD()));
                            }catch(IndexOutOfBoundsException ex){JOptionPane.showMessageDialog(null, "Vui lòng chọn SP cần xóa trên giỏ hàng");}
                        }
                    }
                    //Nút thanh toán
                    if(e.getSource()==btnFunctions[3]){
                        if(dsct.isEmpty())
                        {
                            JOptionPane.showMessageDialog(null, "Giỏ hàng chưa có hàng");
                            return;
                        }
                        if(txtMaKH.getText().isEmpty()){
                            JOptionPane.showMessageDialog(null,"Chưa nhập mã khách hàng");
                            return;
                        }
                        String maHD = txtMaHD.getText();
                        long tongTien = Integer.parseInt(txtTongTien.getText());
                        String maNV = txtMaNV.getText();
                        String maKH = txtMaKH.getText();
                        Date ngayHD = Timestamp.valueOf(LocalDateTime.now());
                        int dialogResult = JOptionPane.showConfirmDialog(null,"Tổng tiền: " +tongTien+"VND " +
                                                                                    "\nNgày tạo: " +ngayHD+
                                                                                    "\nXác nhận thanh toán?");
                        if(dialogResult==JOptionPane.YES_OPTION){
                            HoaDonDTO hd = new HoaDonDTO(maHD,maKH,maNV,ngayHD,tongTien);
                            dshd.add(hd);
                            try {
                                hdBUS.add(hd);
                                for(CTHDonDTO ct:dsct)
                                ctBUS.add(ct);
                                PrintBill bill = new PrintBill(hd,dsct);
                                bill.print();
                            } catch (SQLException ex) {
                                ex.printStackTrace();
                            }
                            JOptionPane.showMessageDialog(null,"Đã thanh toán thành công\n Tổng tiền: "+tongTien);

                            tmGioHang.setRowCount(0);
                            txtMaHD.setText(hdBUS.initMaHD());
                            txtTongTien.setText("0");
                            //Tan
                            for(CTHDonDTO i : dsct){
                                spBUS.updateSLSP(i.getMaSP(), i.getSoLuong());
                            }
                            ListLoaiSP(kind);
                            dsct.removeAll(dsct);
                        }
                        if (dialogResult == JOptionPane.NO_OPTION)
                            return;

                    }
                }
            });
            panelBtn.add(btnFunctions[i]);

        }


        panelBtn.setBackground(Color.decode(defaultColor));

        panel.add(BorderLayout.SOUTH,panelBtn);
        panel.add(BorderLayout.CENTER,panelInfo);

        return panel;
    }

    //Thanh chọn loại sản phẩm cần tìm nằm bên trái, vd: thức ăn, uống, bánh ngọt, kem,...
    public JPanel ThanhChonSP(){
        panelLoaiSP = new JPanel();
        panelLoaiSP.setLayout(new FlowLayout(1));
        panelLoaiSP.setBackground(Color.decode(defaultColor));
        lblLoaiSanPham = new JLabel[ListIcon.length];
        panelLoaiSP.setPreferredSize(new Dimension(ThanhChonSpWidth,ThanhChonSPHeight));
        for (int i = 0 ;i<ListIcon.length;i++){
            ImageIcon icon = new ImageIcon(ListIcon[i]);
            lblLoaiSanPham[i] = new JLabel(icon);
            lblLoaiSanPham[i].setPreferredSize(new Dimension(ThanhChonSpWidth,ThanhChonSPHeight));
            lblLoaiSanPham[i].setBackground(Color.decode(defaultColor));
            lblLoaiSanPham[i].setOpaque(true);
            lblLoaiSanPham[i].addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    super.mouseClicked(e);
                    //Loại sản phẩm, 1 = thức ăn, 2 = nước uống, 3 = bánh ngọt
                    if(e.getSource() == lblLoaiSanPham[0])
                    {
                        System.out.println("access food");
                        kind = "1";
                        ListLoaiSP("1");
                    }
                    if(e.getSource()==lblLoaiSanPham[1]){
                        System.out.println("accessed drink");
                        kind = "2";
                        ListLoaiSP("2");
                    }
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    super.mouseEntered(e);
                    for (int i = 0; i < lblLoaiSanPham.length; i++) {
                        if (e.getSource() == lblLoaiSanPham[i]) {
                            lblLoaiSanPham[i].setBackground(Color.decode("#009688"));
                            break;
                        }
                    }
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    super.mouseExited(e);
                    for (int i = 0; i < lblLoaiSanPham.length; i++) {
                        if (e.getSource() == lblLoaiSanPham[i]) {
                            lblLoaiSanPham[i].setBackground(Color.decode(defaultColor));
                            break;
                        }
                    }
                }
            });
            panelLoaiSP.add(lblLoaiSanPham[i]);
        }

        return panelLoaiSP;
    }

    public JScrollPane gioHang(){
        tmGioHang = new DefaultTableModel(headerGioHang,0);
        tableGioHang = new JTable(tmGioHang);
        JScrollPane scrl = new JScrollPane(tableGioHang,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrl.setPreferredSize(new Dimension(100,250));

        return scrl;
    }
    //Màn hình chọn sản phẩm
    public JScrollPane PaneSP(){

        //Tạo JTable
        tmDanhSachSP = new DefaultTableModel(header,0)
        //https://stackoverflow.com/questions/27542256/how-to-insert-image-from-database-to-jtable
        {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if(columnIndex == 0){
                    return Icon.class;
                }
                return super.getColumnClass(columnIndex);
            }
        };
        TableSP = new JTable(tmDanhSachSP);
        TableSP.setRowHeight(80);

        //Bỏ JTable vào Scroll Pane
        scrlSanPham = new JScrollPane(TableSP,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
       return scrlSanPham;
    }

    public void ListLoaiSP(String Loai){
        spBUS.ListLoaiSP(Loai);
        ArrayList<SanPhamDTO> sp = spBUS.getList();
        capNhatSanPham(tmDanhSachSP,sp);
    }

    public void capNhatSanPham(DefaultTableModel model , ArrayList<SanPhamDTO> sp) // Xuất ra Table từ ArrayList
    {
        Vector data;
        model.setRowCount(0);
        for(SanPhamDTO n:sp)
        {
            data = new Vector();
            ImageIcon icon = new ImageIcon("GUI/image/"+n.getUrlHinh());
            ImageIcon scaleIcon = new ImageIcon(icon.getImage().getScaledInstance(100, 80, Image.SCALE_SMOOTH));
            data.add(scaleIcon);
            data.add(n.getMaSP());
            data.add(n.getTenSP());
            data.add(n.getGia());
            data.add(n.getSoLuongSP());
            model.addRow(data);
        }
        TableSP.setModel(model);
    }

    public void capNhatGioHang(DefaultTableModel model , ArrayList<CTHDonDTO> ct) // Xuất ra Table từ ArrayList
    {
        Vector data;
        model.setRowCount(0);
        for(CTHDonDTO n:ct)
        {
            data = new Vector();
            data.add(n.getMaSP());
            data.add(n.getTenSP());
            data.add(n.getGia());
            data.add(n.getSoLuong());
            model.addRow(data);
        }
        tableGioHang.setModel(model);
    }

//    public void layTenKhachHang(String makh){
//        khBUS.listKHTheoMa(makh);
//        ArrayList<KhachHangDTO> dskh = khBUS.getList();
//        for(KhachHangDTO kh:dskh)
//            txtTenKH.setText(kh.getTen());
//    }

    public int sumHD()
    {
        int sum = 0;
        for(CTHDonDTO sp : dsct)
        {
            int sl = sp.getSoLuong();
            int gia = sp.getGia();
            sum += sl*gia;
        }
        return sum;
    }
}