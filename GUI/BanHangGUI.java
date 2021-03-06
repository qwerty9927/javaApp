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
    private String[] ListFunction = {"Th??m v??o h??a ????n","S???a s??? l?????ng","X??a kh???i h??a ????n","Thanh To??n"};
    private String[] header = {"H??nh","M?? S???n Ph???m","T??n S???n Ph???m","Gi??","S??? l?????ng"};
    private String headerGioHang[] = {"M?? s???n ph???m","T??n","Gi??","S??? l?????ng"};
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
    //Thanh c??c thao t??c n???m b??n ph???i
    public JPanel ThanhThaoTac(){
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        txtMaHD = new JTextField(hdBUS.initMaHD());
        txtMaHD.setBounds(10,10,txtInfoWidth - 170,txtInfoHeight);
        txtMaHD.setBorder(BorderFactory.createTitledBorder("M?? h??a ????n: "));
        txtMaHD.setEnabled(false);

        txtMaNV = new JTextField(LoginGUI.manvTK);
        txtMaNV.setBounds(160,10,txtInfoWidth - 170,txtInfoHeight);
        txtMaNV.setBorder(BorderFactory.createTitledBorder("M?? Nh??n vi??n: "));
        txtMaNV.setEnabled(false);

        txtMaKH = new JTextField();
        txtMaKH.setBounds(10,80,txtInfoWidth- 170,txtInfoHeight);
        txtMaKH.setBorder(BorderFactory.createTitledBorder("Nh???p m?? Kh??ch h??ng: "));

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
        txtTenKH.setBorder(BorderFactory.createTitledBorder("T??n kh??ch h??ng"));
        txtTenKH.setEnabled(false);

        txtTongTien = new JTextField("0");
        txtTongTien.setBounds(10,220,txtInfoWidth,txtInfoHeight);
        txtTongTien.setBorder(BorderFactory.createTitledBorder("T???ng ti???n: "));
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
            //0 = Th??m v??o gi??? h??ng, 1 = S???a s??? l?????ng tr??n gi??? h??ng, 2 = X??a kh???i gi??? h??ng,  3 = Thanh to??n
            btnFunctions[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    //N??t th??m v??o gi??? h??ng
                    if(e.getSource()==btnFunctions[0]){
                        int sl = 0;
                        try{
                            int i = TableSP.getSelectedRow();
                            sl = Integer.parseInt(JOptionPane.showInputDialog(null, "Nh???p s??? l?????ng s???n ph???m :",JOptionPane.INFORMATION_MESSAGE));
                            MaSP = TableSP.getModel().getValueAt(i,1).toString();
                            TenSP = TableSP.getModel().getValueAt(i,2).toString();
                            Gia = Integer.parseInt(TableSP.getModel().getValueAt(i,3).toString());
                        }catch(IndexOutOfBoundsException ex)
                        {
                            JOptionPane.showMessageDialog(null, "Ch??a ch???n SP c???n th??m");
                            return;
                        }catch(NumberFormatException ex) {
                            JOptionPane.showMessageDialog(null, "Ch??a nh???p s??? l?????ng c???n th??m");
                            return;
                        }

                        //Ki???m tra ???? c?? trong gi??? ch??a
                        boolean flag = true;
                        for(CTHDonDTO sp : dsct )

                            if(sp.getMaSP().equals(MaSP)) //C?? trong gi??? h??ng
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
                    //N??t s???a s??? l?????ng tr??n gi??? h??ng
                    if(e.getSource()==btnFunctions[1]){
                        try{
                            int i = tableGioHang.getSelectedRow();

                            String masp = tableGioHang.getModel().getValueAt(i,0).toString();
                            int sl = Integer.parseInt(JOptionPane.showInputDialog(null,"Nh???p s??? l?????ng s???n ph???m:"));
                            while(!spBUS.checkSL(masp,sl))
                                sl = Integer.parseInt(JOptionPane.showInputDialog(null,"Nh???p s??? l?????ng s???n ph???m:"));
                            for(CTHDonDTO ct : dsct) {
                                if (ct.getMaSP().equals(masp))
                                    ct.setSoLuong(sl);
                                capNhatGioHang(tmGioHang, dsct);
                                txtTongTien.setText(String.valueOf(sumHD()));
                            }
                        }catch (IndexOutOfBoundsException ex){
                            JOptionPane.showMessageDialog(null,"Ch??a ch???n s???n ph???m c???n s???a");
                        }
                    }
                    //N??t x??a tr??n gi??? h??ng
                    if(e.getSource()==btnFunctions[2]){
                        {
                            try
                            {
                                int i = tableGioHang.getSelectedRow();
                                dsct.remove(i);
                                tmGioHang.removeRow(i);
                                txtTongTien.setText(String.valueOf(sumHD()));
                            }catch(IndexOutOfBoundsException ex){JOptionPane.showMessageDialog(null, "Vui l??ng ch???n SP c???n x??a tr??n gi??? h??ng");}
                        }
                    }
                    //N??t thanh to??n
                    if(e.getSource()==btnFunctions[3]){
                        if(dsct.isEmpty())
                        {
                            JOptionPane.showMessageDialog(null, "Gi??? h??ng ch??a c?? h??ng");
                            return;
                        }
                        if(txtMaKH.getText().isEmpty()){
                            JOptionPane.showMessageDialog(null,"Ch??a nh???p m?? kh??ch h??ng");
                            return;
                        }
                        String maHD = txtMaHD.getText();
                        long tongTien = Integer.parseInt(txtTongTien.getText());
                        String maNV = txtMaNV.getText();
                        String maKH = txtMaKH.getText();
                        Date ngayHD = Timestamp.valueOf(LocalDateTime.now());
                        int dialogResult = JOptionPane.showConfirmDialog(null,"T???ng ti???n: " +tongTien+"VND " +
                                                                                    "\nNg??y t???o: " +ngayHD+
                                                                                    "\nX??c nh???n thanh to??n?");
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
                            JOptionPane.showMessageDialog(null,"???? thanh to??n th??nh c??ng\n T???ng ti???n: "+tongTien);

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

    //Thanh ch???n lo???i s???n ph???m c???n t??m n???m b??n tr??i, vd: th???c ??n, u???ng, b??nh ng???t, kem,...
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
                    //Lo???i s???n ph???m, 1 = th???c ??n, 2 = n?????c u???ng, 3 = b??nh ng???t
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
    //M??n h??nh ch???n s???n ph???m
    public JScrollPane PaneSP(){

        //T???o JTable
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

        //B??? JTable v??o Scroll Pane
        scrlSanPham = new JScrollPane(TableSP,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
       return scrlSanPham;
    }

    public void ListLoaiSP(String Loai){
        spBUS.ListLoaiSP(Loai);
        ArrayList<SanPhamDTO> sp = spBUS.getList();
        capNhatSanPham(tmDanhSachSP,sp);
    }

    public void capNhatSanPham(DefaultTableModel model , ArrayList<SanPhamDTO> sp) // Xu???t ra Table t??? ArrayList
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

    public void capNhatGioHang(DefaultTableModel model , ArrayList<CTHDonDTO> ct) // Xu???t ra Table t??? ArrayList
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