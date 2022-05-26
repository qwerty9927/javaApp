package task1.GUI;

import task1.BUS.ChiTietPhieuNhapBUS;
import task1.BUS.SanPhamBUS;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;

public class ChiTietPhieuNhapGUI extends JFrame {
//    private JFrame frameStaff = new JFrame();
    private JPanel panelContent, panelForm, panelTable, panelSearch, panelInput, panelControl;
    private JLabel labelSearch;
    private JTextField tFieldSearch;
    private JTextField[] textFields;
    private DefaultTableModel tableModel;
    private JTable table;
    private JButton btnConfirm, btnAdd, btnEdit, btnDelete, btnShowMaSP, btnAddNewSP;
    private ChiTietPhieuNhapBUS bus;
    private String mancc = "";
    private SanPhamBUS SPbus;

    private int rowSelect;
    private int status = 0;
    private int length;
    private String mapn;
    private String[] stringLabel;
    public static String price = "";

    private int width = Toolkit.getDefaultToolkit().getScreenSize().width;
    private int height = Toolkit.getDefaultToolkit().getScreenSize().height;
    public ChiTietPhieuNhapGUI(String mapn){
        this.mapn = mapn;
        SPbus = new SanPhamBUS();
        bus = new ChiTietPhieuNhapBUS();
        stringLabel = bus.getStringHeaderBUS();
        length = stringLabel.length;
        setLayout(new BorderLayout(5, 5));
        add(content(), BorderLayout.WEST);
        add(tableNV(), BorderLayout.CENTER);

        setSize(new Dimension(1200, 700));
        setLocation((width - getWidth())/2, (height - getHeight())/2);
        setVisible(true);
    }

    public JPanel content(){
        panelContent = new JPanel();
        panelContent.setLayout(new BorderLayout(5,5));
        panelContent.setPreferredSize(new Dimension(400, 0));
        panelContent.add(searchBox(), BorderLayout.NORTH);
        panelContent.add(form(), BorderLayout.CENTER);
        panelContent.add(comfirm(), BorderLayout.SOUTH);
        return panelContent;
    }

    public JPanel searchBox(){
        panelSearch = new JPanel();
        panelSearch.setLayout(new FlowLayout(FlowLayout.CENTER));
        panelSearch.setPreferredSize(new Dimension(400, 50));
        labelSearch = new JLabel("Tìm kiếm");
        tFieldSearch = new JTextField();
        tFieldSearch.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                bus.searchBUS(tFieldSearch.getText().toLowerCase());
                System.out.println(tFieldSearch.getText());
                panelTable.removeAll();
                panelTable.add(table(), BorderLayout.NORTH);
                panelTable.repaint();
                panelTable.revalidate();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                bus.searchBUS(tFieldSearch.getText().toLowerCase());
                System.out.println(tFieldSearch.getText());
                panelTable.removeAll();
                panelTable.add(table(), BorderLayout.NORTH);
                panelTable.repaint();
                panelTable.revalidate();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {

            }
        });
        tFieldSearch.setPreferredSize(new Dimension(200, 40));
        panelSearch.add(labelSearch);
        panelSearch.add(tFieldSearch);
        return panelSearch;
    }

    public JPanel comfirm(){
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnConfirm = createBtn("Xác nhận");
        btnConfirm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(tableModel.getRowCount() == 0){
                    JOptionPane.showMessageDialog(null, "Cần nhập dữ liệu trước khi xác nhận");
                } else {
                    PhieuNhapGUI.ctpnDTO = bus.getAllValuesBUS();
                    PhieuNhapGUI.ctpnHeaderDTO = bus.getAllHeaderBUS();
                    PhieuNhapGUI.panelCTPN.removeAll();
                    PhieuNhapGUI.panelCTPN.add(PhieuNhapGUI.tableCTPN());
                    PhieuNhapGUI.panelCTPN.repaint();
                    PhieuNhapGUI.panelCTPN.revalidate();
                    PhieuNhapGUI.total = bus.getTotalValueBUS();
                    PhieuNhapGUI.labelTotal.setText("Tổng: " + String.valueOf(PhieuNhapGUI.total));
                    PhieuNhapGUI.type = 1;
                    dispose();
                }
            }
        });
//        panel.add(btnAddNewSP);
        panel.add(btnConfirm);
        return panel;
    }

    public JPanel form(){
        panelInput = new JPanel();
        inputItems();
        panelForm = new JPanel(new BorderLayout(5, 5));
        panelForm.setLayout(new BorderLayout(5, 5));
        panelForm.setBackground(Color.white);
        panelForm.add(panelInput, BorderLayout.CENTER);
        panelForm.add(controller(), BorderLayout.SOUTH);
        return panelForm;
    }

    public void inputItems(){
        textFields = new JTextField[stringLabel.length];
        panelInput.setPreferredSize(new Dimension(0, 50 ));
        panelInput.setLayout(new FlowLayout(1, 15, 15));
        for(int i = 0;i < stringLabel.length;i++){
            textFields[i] = new JTextField();
            if(stringLabel[i].equalsIgnoreCase("MaSP")){
                JPanel item = new JPanel(new BorderLayout());
                JLabel title = new JLabel(stringLabel[i] + " :");
                btnShowMaSP = new JButton("...");
                btnShowMaSP.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        choiceProduct(textFields[1]);
                    }
                });
                title.setPreferredSize(new Dimension(100, 25));
                title.setOpaque(true);
                title.setBackground(Color.white);
                textFields[i].setPreferredSize(new Dimension(175, 25));
                textFields[i].setEnabled(false);
                btnShowMaSP.setPreferredSize(new Dimension(25, 25));
                item.add(title, BorderLayout.WEST);
                item.add(textFields[i], BorderLayout.CENTER);
                item.add(btnShowMaSP, BorderLayout.EAST);
                panelInput.add(item);
            } else {
                panelInput.add(item(new JLabel(stringLabel[i] + " :"), textFields[i]));
            }
        }
        textFields[0].setEnabled(false);
        textFields[0].setText(PhieuNhapGUI.mapn);
        panelInput.setBackground(Color.WHITE);
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

    public void choiceProduct(JTextField tf){
        SubSanPhamGUI ssp = new SubSanPhamGUI(tf);
    }

    public JPanel controller(){
        panelControl = new JPanel();
        panelControl.setPreferredSize(new Dimension(0, 50));
        panelControl.add(addController());
        panelControl.add(editController());
        panelControl.add(deleteController());
        panelControl.setBackground(Color.decode("#4DB6AC"));
        return panelControl;
    }

    public JButton addController(){
        btnAdd = createBtn("Thêm");
        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(status == 0){
                    int result = bus.checkBUS(textFields);
                    if(result == 1){
                        JOptionPane.showMessageDialog(null, "MaSP không hợp lệ");
                        textFields[1].requestFocus();
                        textFields[1].selectAll();
                    } else if(result == 2){
                        JOptionPane.showMessageDialog(null, "Số lượng không hợp lệ");
                        textFields[2].requestFocus();
                        textFields[2].selectAll();
                    } else {
                        bus.addBUS(textFields);
                        JOptionPane.showMessageDialog(null, "Thêm thành công");
                        getContentPane().removeAll();
                        add(content(), BorderLayout.WEST);
                        add(tableNV(), BorderLayout.CENTER);
                        repaint();
                        revalidate();
                        System.out.println("endline");
                    }
                } else {
                    panelInput.removeAll();
                    inputItems();
                    panelInput.repaint();
                    panelInput.revalidate();
                    status = 0;
                    textFields[0].setText(mapn);
                }
            }
        });
        return btnAdd;
    }

    public JButton editController(){
        btnEdit = createBtn("Sửa");
        btnEdit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(status == 1){
                    int result = bus.checkBUS(textFields);
                    if(result == 1){
                        JOptionPane.showMessageDialog(null, "MaSP không hợp lệ");
                        textFields[1].requestFocus();
                        textFields[1].selectAll();
                    } else if(result == 2){
                        JOptionPane.showMessageDialog(null, "Số lượng không hợp lệ");
                        textFields[2].requestFocus();
                        textFields[2].selectAll();
                    } else {
                        bus.editBUS(textFields, rowSelect);
                        JOptionPane.showMessageDialog(null, "Sửa thành công");
                        table.getSelectionModel().clearSelection();
                        status = 0;
                        getContentPane().removeAll();
                        add(content(), BorderLayout.WEST);
                        add(tableNV(), BorderLayout.CENTER);
                        repaint();
                        revalidate();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Cần chọn một dòng để sửa");
                }
            }
        });
        return btnEdit;
    }

    public JButton deleteController(){
        btnDelete = createBtn("Xóa");
        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(status != 1){
                    JOptionPane.showMessageDialog(null, "Chọn một dòng trong bảng để xóa");
                } else {
                    int confirm = JOptionPane.showConfirmDialog(null, "Bạn muốn xóa ?");
                    if(confirm == JOptionPane.YES_OPTION){
                        bus.deleteBUS(textFields, 0, rowSelect);
                        table.getSelectionModel().clearSelection();
                        status = 0;
                        getContentPane().removeAll();
                        add(content(), BorderLayout.WEST);
                        add(tableNV(), BorderLayout.CENTER);
                        repaint();
                        revalidate();
                    }
                }
            }
        });
        return btnDelete;
    }

    public JButton createBtn(String label){
        JButton btn = new JButton(label);
        btn = new JButton(label);
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setPreferredSize(new Dimension(100, 40));
        return btn;
    }

    public JPanel tableNV() {
        panelTable = new JPanel();
        panelTable.setBackground(Color.white);
        panelTable.setLayout(new BorderLayout());
        panelTable.add(table(), BorderLayout.NORTH);
        return panelTable;
    }

    public JScrollPane table(){
        tableModel = new DefaultTableModel(bus.getAllValuesBUS(), bus.getStringHeaderBUS());
        table = new JTable(tableModel);
        JScrollPane scrl = new JScrollPane(table);
        MouseListener Mlistener = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                tableMountClick();
            }
        };
        table.addMouseListener(Mlistener);
        return scrl;
    }

    public void tableMountClick(){
        status = 1;
        rowSelect = table.getSelectedRow();
        for(int i = 0;i < length;i++){
            String col = (String) table.getValueAt(rowSelect, i);
            textFields[i].setText(col);
        }
    }
}
