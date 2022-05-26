package task1.GUI;

import task1.BUS.ChiTietPhieuNhapBUS;
import task1.BUS.PhieuNhapBUS;
import task1.DTO.ChiTietPhieuNhapDTO;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;


public class PhieuNhapGUI extends JPanel {
    private JPanel panelInfoBox, panelInputBox, panelTable, panelInput, panelControl, panelSearch, panelDisplay;
    public static JPanel panelCTPN;
    private JTable table;
    private static JTable tableCTPN;
    private JButton btnAdd, btnDelete, btnShowMaNCC, btnShowCTPN;
    private JLabel labelSearch;
    public static JLabel labelTotal;
    private JTextField tFieldSearch;
    private JTextField[] textFields;
    private String[] stringLabel;
    private String[] stringExceptTotal;
    private DefaultTableModel tableModel;
    private static DefaultTableModel tableModelCTPN;

    private PhieuNhapBUS bus;
    private ChiTietPhieuNhapBUS CTPNbus;
    public static ArrayList<HashMap<String, String>> ctpnOriginalDTO;
    public static String[][] ctpnDTO;
    public static String[] ctpnHeaderDTO;
    public static String mapn;
    public static int type = 0;
    public static int total = 0;
    private int rowSelect;
    private int length;
    private int status = 0;

    public PhieuNhapGUI(){
        bus = new PhieuNhapBUS();
        CTPNbus = new ChiTietPhieuNhapBUS();
        ctpnOriginalDTO = new ArrayList<>();
        stringLabel = bus.getStringHeaderBUS();
        stringExceptTotal = bus.getStringInputBUS();
        length = stringLabel.length;
        setLayout(new BorderLayout(0, 5));
        add(infoNCC(), BorderLayout.NORTH);
        add(tableNCC(), BorderLayout.CENTER);
    }
    //input
    public JPanel infoNCC(){
        panelInfoBox = new JPanel();
        panelInfoBox.setLayout(new BorderLayout(5, 5));
        panelInfoBox.setPreferredSize(new Dimension(0, 500));
        panelInfoBox.add(searchBox(), BorderLayout.NORTH);
        panelInfoBox.add(inputBox(), BorderLayout.CENTER);
        return panelInfoBox;
    }

    public JPanel searchBox(){
        panelSearch = new JPanel();
        panelSearch.setLayout(new FlowLayout(FlowLayout.CENTER));
        panelSearch.setPreferredSize(new Dimension(0, 50));
        labelSearch = new JLabel("Tìm kiếm");
        tFieldSearch = new JTextField();
        tFieldSearch.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                bus.searchBUS(tFieldSearch.getText().toLowerCase());
                System.out.println(tFieldSearch.getText());
                panelTable.removeAll();
                panelTable.add(table());
                panelTable.repaint();
                panelTable.revalidate();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                bus.searchBUS(tFieldSearch.getText().toLowerCase());
                System.out.println(tFieldSearch.getText());
                panelTable.removeAll();
                panelTable.add(table());
                panelTable.repaint();
                panelTable.revalidate();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                if(tFieldSearch.getText().equalsIgnoreCase("")){
                    System.out.println(3);
                }
            }
        });
        tFieldSearch.setPreferredSize(new Dimension(300, 40));
        panelSearch.add(labelSearch);
        panelSearch.add(tFieldSearch);
        return panelSearch;
    }

    public JPanel inputBox(){
        panelDisplay = new JPanel();
        panelInput = new JPanel();
        inputItems();
        panelDisplay.setLayout(new BorderLayout(5, 5));
        panelDisplay.setBackground(Color.decode("#4DB6AC"));
        fetureBtn();
        panelInputBox = new JPanel(new BorderLayout(0, 0));
        panelInputBox.add(panelInput, BorderLayout.NORTH);
        panelInputBox.add(panelDisplay, BorderLayout.CENTER);
        panelInputBox.add(controller(), BorderLayout.SOUTH);
        return panelInputBox;
    }

    public void inputItems(){
        textFields = new JTextField[stringExceptTotal.length];
        panelInput.setPreferredSize(new Dimension(0, 50));
        panelInput.setLayout(new FlowLayout(1, 15, 15));
        for(int i = 0;i < stringExceptTotal.length;i++){
            textFields[i] = new JTextField();
            if(stringExceptTotal[i].equalsIgnoreCase("MaNCC")){
                btnShowMaNCC = new JButton("...");
                btnShowMaNCC.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        choiceProvider(textFields[1]);
                    }
                });
                panelInput.add(itemChoice(new JLabel(stringExceptTotal[i] + ":"), textFields[i], btnShowMaNCC));
            } else if(stringExceptTotal[i].equalsIgnoreCase("Manv")){
                textFields[i].setText(LoginGUI.manvTK);
                textFields[i].setEnabled(false);
                panelInput.add(item(new JLabel(stringLabel[i] + " :"), textFields[i]));
            } else {
                panelInput.add(item(new JLabel(stringLabel[i] + " :"), textFields[i]));
            }
        }
        textFields[0].setEnabled(false);
        textFields[0].setText(String.valueOf(bus.getNextCodeBUS()));
        mapn = textFields[0].getText();
        panelInput.setBackground(Color.WHITE);
    }

    public void fetureBtn(){
        labelTotal = new JLabel("Tổng: 0" );
        labelTotal.setFont(new Font("Serif", Font.PLAIN, 20));
        JPanel panelNorth = new JPanel(new FlowLayout(FlowLayout.CENTER));
//        JPanel panelCenter = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JPanel panelSouth = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JPanel panelEast = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JPanel panelWest = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelEast.setPreferredSize(new Dimension(100, 0 ));
        panelWest.setPreferredSize(new Dimension(100, 0 ));

        btnShowCTPN = createBtn("Nhập CTPN");
        RoundedBorder.BorderRadius2(btnShowCTPN);
        btnShowCTPN.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                insertData();
            }
        });
        panelNorth.add(btnShowCTPN);
        panelSouth.add(labelTotal);

        panelDisplay.add(panelNorth, BorderLayout.NORTH);
        panelDisplay.add(tablePNCTPN(), BorderLayout.CENTER);
        panelDisplay.add(panelSouth, BorderLayout.SOUTH);
        panelDisplay.add(panelEast, BorderLayout.EAST);
        panelDisplay.add(panelWest, BorderLayout.WEST);
    }

    public JPanel item(JLabel title, JTextField inputField){
        JPanel item = new JPanel(new BorderLayout());
        title.setPreferredSize(new Dimension(100, 25));
        title.setOpaque(true);
        title.setBackground(Color.white);
        inputField.setPreferredSize(new Dimension(200, 25));
        item.add(title, BorderLayout.WEST);
        item.add(inputField, BorderLayout.CENTER);
        return item;
    }

    public JPanel itemChoice(JLabel title, JTextField textField, JButton btnShow){
        JPanel item = new JPanel(new BorderLayout());
        title.setPreferredSize(new Dimension(100, 25));
        title.setOpaque(true);
        title.setBackground(Color.white);
        textField.setPreferredSize(new Dimension(175, 25));
        textField.setEnabled(false);
        btnShow.setPreferredSize(new Dimension(25, 25));
        item.add(title, BorderLayout.WEST);
        item.add(textField, BorderLayout.CENTER);
        item.add(btnShow, BorderLayout.EAST);
        panelInput.add(item);
        return item;
    }

    public void choiceProvider(JTextField tf){
        SubNCC sncc = new SubNCC(tf);
    }

    public void choiceStaff(JTextField tf){
        SubNhanVienGUI snv = new SubNhanVienGUI(tf);
    }

    public void insertData(){
        ChiTietPhieuNhapGUI ctpn = new ChiTietPhieuNhapGUI(textFields[0].getText());
    }

    public JButton addController(){
        btnAdd = createBtn("Thêm");
        RoundedBorder.BorderRadius1(btnAdd);
        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(status == 0){
                    int result = bus.checkBUS(textFields);
                    if(result == 1){
                        JOptionPane.showMessageDialog(null, "MaNCC không hợp lệ");
                    } else if(result == 2){
                        JOptionPane.showMessageDialog(null, "Manv không hợp lệ");
                    } else if(type == 0){
                        JOptionPane.showMessageDialog(null, "Nhập chi tiết sản phẩm");
                    } else {
                        bus.addBUS(textFields, total);
                        CTPNbus.addToDBBUS();
                        JOptionPane.showMessageDialog(null, "Thêm thành công");
                        type = 0;
                        removeAll();
                        add(infoNCC(), BorderLayout.NORTH);
                        add(tableNCC(), BorderLayout.CENTER);
                        repaint();
                        revalidate();
                        ctpnDTO = null;
                        ctpnHeaderDTO = null;
                        panelCTPN.removeAll();
                        panelCTPN.add(tableCTPN());
                        panelCTPN.repaint();
                        panelCTPN.revalidate();
                    }
                } else {
                    panelInput.removeAll();
                    inputItems();
                    labelTotal.setText("Tổng: 0");
                    panelInput.repaint();
                    panelInput.revalidate();
                    status = 0;
                    type = 0;
                    ctpnOriginalDTO = new ArrayList<>();
                    table.getSelectionModel().clearSelection();
                    textFields[0].setText(String.valueOf(bus.getNextCodeBUS()));
                    mapn = textFields[0].getText();
                    ctpnDTO = null;
                    ctpnHeaderDTO = null;
                    panelCTPN.removeAll();
                    panelCTPN.add(tableCTPN());
                    panelCTPN.repaint();
                    panelCTPN.revalidate();
                }
            }
        });
        return btnAdd;
    }

    public JButton deleteController(){
        btnDelete = createBtn("Xóa");
        RoundedBorder.BorderRadius1(btnDelete);
        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(status != 1){
                    JOptionPane.showMessageDialog(null, "Chọn một dòng trong bảng để xóa");
                } else {
                    int confirm = JOptionPane.showConfirmDialog(null, "Bạn muốn xóa ?");
                    if(confirm == JOptionPane.YES_OPTION){
                        bus.deleteBUS(0, rowSelect);
                        table.getSelectionModel().clearSelection();
                        status = 0;
                        removeAll();
                        add(infoNCC(), BorderLayout.NORTH);
                        add(tableNCC(), BorderLayout.CENTER);
                        repaint();
                        revalidate();
                    }
                }
            }
        });
        return btnDelete;
    }

    public JPanel controller(){
        panelControl = new JPanel();
        panelControl.setPreferredSize(new Dimension(0, 50));
        panelControl.add(addController());
        panelControl.add(deleteController());
        panelControl.setBackground(Color.decode("#4DB6AC"));
        return panelControl;
    }

    public JButton createBtn(String label){
        JButton btn = new JButton(label);
        btn = new JButton(label);
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setPreferredSize(new Dimension(100, 40));
        return btn;
    }

    // table CTPN
    public JPanel tablePNCTPN() {
        panelCTPN = new JPanel();
        panelCTPN.setLayout(new BorderLayout());
        panelCTPN.setPreferredSize(new Dimension(0, 250));
        panelCTPN.add(tableCTPN(), BorderLayout.NORTH);
        return panelCTPN;
    }

    public static JScrollPane tableCTPN(){
        tableModelCTPN = new DefaultTableModel(ctpnDTO, ctpnHeaderDTO);
        tableCTPN = new JTable(tableModelCTPN);
        JScrollPane scrl = new JScrollPane(tableCTPN);
        return scrl;
    }


    //table main
    public JPanel tableNCC() {
        panelTable = new JPanel();
        panelTable.setLayout(new BorderLayout());
        panelTable.setPreferredSize(new Dimension(0, 250));
        panelTable.add(table(), BorderLayout.NORTH);
        return panelTable;
    }

    public JScrollPane table(){
        tableModel = new DefaultTableModel(bus.getAllValuesBUS(), bus.getAllHeaderBUS());
        table = new JTable(tableModel);
        JScrollPane scrl = new JScrollPane(table);
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                tableMountClick();
            }
        });
        return scrl;
    }

    public void tableMountClick(){
        status = 1;
        rowSelect = table.getSelectedRow();
        for(int i = 0;i < length - 1;i++){
            String col = (String) table.getValueAt(rowSelect, i);
            textFields[i].setText(col);
        }
        labelTotal.setText("Tổng: " + (String) table.getValueAt(rowSelect, length - 1));
        mapn = (String) table.getValueAt(rowSelect, 0);

        ctpnOriginalDTO = new ArrayList<>();
        CTPNbus.filter(mapn);
        ctpnDTO = CTPNbus.getAllValuesBUS();
        ctpnHeaderDTO = CTPNbus.getAllHeaderBUS();
        panelCTPN.removeAll();
        panelCTPN.add(tableCTPN());
        panelCTPN.repaint();
        panelCTPN.revalidate();
    }
}
