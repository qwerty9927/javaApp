package task1.GUI;


import task1.BUS.CTHDonBUS;
import task1.BUS.HoaDonBUS;
import task1.DTO.HoaDonDTO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;

public class DonHangGUI extends JPanel{
    private JTextField txtMaHD, txtMaNV, txtNgayTao, txtTongTien, txtMaKH,txtMin,txtMax,txtSearch;
    private DefaultTableModel dtm;
    private JTable table;
    private JComboBox cbSearch;
    private JLabel lblInfoHeader,lblDetail,lblSearchHeader,lblSearch,lblTilde;
    private JScrollPane scrl;
    private String LoaiSearch[] = {"Theo hóa đơn","Theo giá","Theo nhân viên","Theo khách hàng","Tất cả"};
    private String headers[] = {"Mã đơn hàng","Ngày tạo", "Tổng tiền","Mã nhân viên","Mã khách hàng"};
    private int heightInfoTxt = 35;
    private HoaDonBUS hdBUS = new HoaDonBUS();
    private CTHDonBUS ctBUS = new CTHDonBUS();
    private ArrayList<HoaDonDTO> dshd = new ArrayList<>();
    private String reportFile = "GUI/BillsReport/bill";
    private CTHDonBUS cthDonBUS = new CTHDonBUS();
    private Choice month, year;

    public DonHangGUI(){
        init();
    }
    public void init(){
        this.setLayout(new BorderLayout());
        this.add(BorderLayout.NORTH,Info());
        this.add(BorderLayout.CENTER,TablePanel());
        ListHoaDon();
        this.setVisible(true);
    }
    public JPanel Info(){
        JPanel info = new JPanel();
        info.setBackground(Color.decode("#4DB6AC"));
        info.setLayout(null);
        info.setPreferredSize(new Dimension(200,200));

        lblInfoHeader = new JLabel("-------------------------------------------------------------------------------- Thông tin hóa đơn --------------------------------------------------------------------------------",JLabel.CENTER); // Mỗi bên 84 dấu ( - )
        lblInfoHeader.setBounds(50,20,900,50);
        info.add(lblInfoHeader);



        txtMaHD = new JTextField();
        txtMaHD.setBounds(50,80,150,heightInfoTxt);
        txtMaHD.setBorder(BorderFactory.createTitledBorder("Mã Hóa đơn"));
        info.add(txtMaHD);

        txtMaKH = new JTextField();
        txtMaKH.setBounds(220,80,150,heightInfoTxt);
        txtMaKH.setBorder(BorderFactory.createTitledBorder("Mã Khách hàng"));
        info.add(txtMaKH);

        txtMaNV = new JTextField();
        txtMaNV.setBounds(390,80,150,heightInfoTxt);
        txtMaNV.setBorder(BorderFactory.createTitledBorder("Mã Nhân viên"));
        info.add(txtMaNV);

        txtNgayTao = new JTextField();
        txtNgayTao.setBounds(50,150,250,heightInfoTxt);
        txtNgayTao.setBorder(BorderFactory.createTitledBorder("Ngày tạo"));
        info.add(txtNgayTao);

        txtTongTien = new JTextField();
        txtTongTien.setBounds(350,150,190,heightInfoTxt);
        txtTongTien.setBorder(BorderFactory.createTitledBorder("Tổng tiền (VND)"));
        info.add(txtTongTien);

        ImageIcon icon = new ImageIcon("GUI/image/xemchitietbutton.png");
        ImageIcon scaledIcon = new ImageIcon(icon.getImage().getScaledInstance(300,100,Image.SCALE_SMOOTH));
        lblDetail = new JLabel(scaledIcon);
        lblDetail.setBounds(600,90,300,100);
        lblDetail.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                String id = "";
                for(int i =0;i<3-txtMaHD.getText().length();i++)
                    id+="0";
                File report = new File(reportFile+id+txtMaHD.getText()+".pdf");
                try{
                    Desktop.getDesktop().open(report);
                }catch(IllegalArgumentException | IOException ex){
                    JOptionPane.showMessageDialog(null,"Không có file hóa đơn trên máy");
                }

            }
        });
        info.add(lblDetail);


        info.setOpaque(true);
        info.setVisible(true);
        return info;
    }
    //panel tra cứu
    public JPanel searchPanel(){
        JPanel panel = new JPanel();
        JPanel panelSearch = new JPanel();
        JPanel cbPanel = new JPanel();
        JPanel iconPanel = new JPanel();

        panel.setLayout(new BorderLayout());
        panel.setPreferredSize(new Dimension(1100,130));
        lblSearchHeader = new JLabel("-------------------------------------------------------------------------------- Tìm hóa đơn --------------------------------------------------------------------------------",JLabel.CENTER); // Mỗi bên 84 dấu ( - )
        lblSearchHeader.setPreferredSize(new Dimension(1100,50));

        panelSearch.setLayout(null);
        panelSearch.setPreferredSize(new Dimension(700,100));

        cbPanel.setLayout(null);
        cbPanel.setPreferredSize(new Dimension(200,2));

        iconPanel.setLayout(null);
        iconPanel.setPreferredSize(new Dimension(300,2));

        txtSearch = new JTextField();
        txtSearch.setBorder(BorderFactory.createTitledBorder("Mã hóa đơn"));
        txtSearch.setBounds(30,0,500,40);
        txtSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                search();
            }
        });


        txtMin = new JTextField();
        lblTilde = new JLabel("~");
        txtMax = new JTextField();


        JLabel lblGia = new JLabel("Giá:");

        lblGia.setBounds(30,45,25,25);
        txtMin.setBounds(60,45,150,25);
        lblTilde.setBounds(270,45,10,25);
        txtMax.setBounds(280,45,150,25);

        panelSearch.add(lblTilde);
        panelSearch.add(txtSearch);
        panelSearch.add(txtMin);
        panelSearch.add(lblTilde);
        panelSearch.add(txtMax);



        cbSearch = new JComboBox();
        cbSearch.setBounds(30,0,175,50);
        for(int i = 0;i< LoaiSearch.length;i++)
            cbSearch.addItem(LoaiSearch[i]);
        cbSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(cbSearch.getSelectedIndex() == 0)
                    txtSearch.setBorder(BorderFactory.createTitledBorder("Mã đơn hàng"));
                if(cbSearch.getSelectedIndex() == 2)
                    txtSearch.setBorder(BorderFactory.createTitledBorder("Mã nhân viên"));
                if(cbSearch.getSelectedIndex() == 3)
                    txtSearch.setBorder(BorderFactory.createTitledBorder("Mã khách hàng"));
            }
        });
        cbPanel.add(cbSearch);

        ImageIcon icon = new ImageIcon("javaApp/GUI/image/icons8-magnifying-64.png");
        ImageIcon scaledIcon = new ImageIcon(icon.getImage().getScaledInstance(75,75,Image.SCALE_SMOOTH));
        lblSearch = new JLabel(scaledIcon);
        lblSearch.setBounds(30,0,75,75);
        lblSearch.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                search();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });
        iconPanel.add(lblSearch);

        panel.add(BorderLayout.NORTH,lblSearchHeader);
        panel.add(BorderLayout.WEST,cbPanel);
        panel.add(BorderLayout.CENTER,panelSearch);
        panel.add(BorderLayout.EAST,iconPanel);
        return panel;
    }
    //panel table
    public JPanel TablePanel(){
        JPanel panel = new JPanel();
        dtm = new DefaultTableModel(headers,1);        JLabel sortTitle = new JLabel("------------------------------------------------------------------------------------ BỘ LỌC ------------------------------------------------------------------------------------",JLabel.CENTER); // Mỗi bên 84 dấu ( - )
        TableRowSorter<TableModel> rowSorter = new TableRowSorter<>(dtm);
        table = new JTable(dtm);
        table.setRowSorter(rowSorter);
        table.setShowVerticalLines(false);
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int i = table.getSelectedRow();
                if(table.getRowSorter()!=null)
                    i = table.getRowSorter().convertRowIndexToModel(i);
                txtMaHD.setText(table.getModel().getValueAt(i,0).toString());
                txtMaKH.setText(table.getModel().getValueAt(i,4).toString());
                txtMaNV.setText(table.getModel().getValueAt(i,3).toString());
                txtTongTien.setText(table.getModel().getValueAt(i,2).toString());
                txtNgayTao.setText(table.getModel().getValueAt(i,1).toString());
            }
        });
        scrl = new JScrollPane(table);
        scrl.setPreferredSize(new Dimension(100,200));


        panel.setLayout(new BorderLayout());
        panel.setPreferredSize(new Dimension(0,800));

        panel.add(BorderLayout.NORTH,searchPanel());
        panel.add(BorderLayout.CENTER,scrl);

        panel.setVisible(true);

        return panel;
    }
    public void ListHoaDon(){
        hdBUS.list();
        dshd = hdBUS.getList();
        capNhatDSHD(dtm,dshd);
    }

    public void capNhatDSHD(DefaultTableModel model , ArrayList<HoaDonDTO> hd)
    {
        Vector data;
        model.setRowCount(0);
        for(HoaDonDTO h:hd)
        {
            data = new Vector();
            data.add(h.getMaHD());
            data.add(h.getNgayHD());
            data.add(h.getTongTien());
            data.add(h.getMaNV());
            data.add(h.getMaKH());
            model.addRow(data);
        }
        table.setModel(model);
    }
    public void timKiemMaDH(String madh){
        hdBUS.listMaDH(madh);
        dshd = hdBUS.getList();
        capNhatDSHD(dtm,dshd);
    }
    public void timKiemTheoMaKH(String makh){
        hdBUS.listDHKH(makh);
        dshd = hdBUS.getList();
        capNhatDSHD(dtm,dshd);
    }
    public void timKiemTheoMaNV(String manv){
        hdBUS.listDHNV(manv);
        dshd = hdBUS.getList();
        capNhatDSHD(dtm,dshd);
    }
    public void timKiemTheoGia(String min, String max){
        hdBUS.listDHGia(min,max);
        dshd = hdBUS.getList();
        capNhatDSHD(dtm,dshd);
    }
    public void search(){
        if(cbSearch.getSelectedIndex() == 0)
            timKiemMaDH(txtSearch.getText());
        if(cbSearch.getSelectedIndex() == 1)
            timKiemTheoGia(txtMin.getText(),txtMax.getText());
        if(cbSearch.getSelectedIndex()==2)
            timKiemTheoMaNV(txtSearch.getText());
        if(cbSearch.getSelectedIndex()==3)
            timKiemTheoMaKH(txtSearch.getText());
        if(cbSearch.getSelectedIndex()==4)
            ListHoaDon();
    }


}