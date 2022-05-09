package task1.GUI;

import task1.BUS.NhaCungCapBUS;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;


public class NhaCungCapGUI extends JPanel {
    private JPanel panelInfoBox, panelInputBox, panelTable, panelInput, panelControl, panelSearch, panelHeader, panelSubmit;
    private JTable table;
    private JButton btnAdd, btnEdit, btnDelete, btnSubmitAdd, btnSubmitEdit, btnSubmitDelete;
    private JLabel labelSearch;
    private JLabel[] labels;
    private JTextField tFieldSearch;
    private JTextField[] textFields;
    private String[] stringLabel;
    private DefaultTableModel tableModel;

    private NhaCungCapBUS bus;
    private int rowSelect;
    private int length;
    private int status = 0;


    public NhaCungCapGUI(){
        bus = new NhaCungCapBUS();
        stringLabel = bus.getStringHeaderBUS();
        length = stringLabel.length;
        setLayout(new BorderLayout(0, 5));
        add(infoNCC(), BorderLayout.NORTH);
        add(tableNCC(), BorderLayout.CENTER);
    }
    //input
    public JPanel infoNCC(){
        panelInfoBox = new JPanel();
        panelInfoBox.setLayout(new BorderLayout(5, 5));
        panelInfoBox.setPreferredSize(new Dimension(0, 400));
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
        panelSubmit = new JPanel();
        panelInput = new JPanel();
        inputItems();
        fetureBtn();
        panelSubmit.setLayout(new BoxLayout(panelSubmit, BoxLayout.Y_AXIS));
        panelSubmit.setBackground(Color.white);
        panelInputBox = new JPanel(new BorderLayout(0, 0));
        panelInputBox.add(panelInput, BorderLayout.NORTH);
        panelInputBox.add(panelSubmit, BorderLayout.CENTER);
        panelInputBox.add(controller(), BorderLayout.SOUTH);
        return panelInputBox;
    }

    public void inputItems(){
        textFields = new JTextField[length];
        panelInput.setPreferredSize(new Dimension(0, 200));
        panelInput.setLayout(new FlowLayout(1, 15, 15));
        for(int i = 0;i < length;i++){
            textFields[i] = new JTextField();
            panelInput.add(item(new JLabel(stringLabel[i] + " :"), textFields[i]));
        }
        textFields[0].setEnabled(false);
        textFields[0].setText(String.valueOf(bus.getNextCodeBUS()));
        panelInput.setBackground(Color.WHITE);
    }

    public void fetureBtn(){

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

    public JButton addController(){
        btnAdd = createBtn("Thêm");
        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(status == 0){
                    int result = bus.checkBUS(textFields);
                    if(result == 1){
                        JOptionPane.showMessageDialog(null, "Tên không hợp lệ");
                    } else if(result == 2){
                        JOptionPane.showMessageDialog(null, "Địa chỉ không hợp lệ");
                    } else if(result == 3){
                        JOptionPane.showMessageDialog(null, "Số điện thoại không hợp lệ");
                    } else {
                        bus.addBUS(textFields);
                        JOptionPane.showMessageDialog(null, "Thêm thành công");
                        removeAll();
                        add(infoNCC(), BorderLayout.NORTH);
                        add(tableNCC(), BorderLayout.CENTER);
                        repaint();
                        revalidate();
                    }
                } else {
                    panelInput.removeAll();
                    inputItems();
                    panelInput.repaint();
                    panelInput.revalidate();
                    status = 0;
                    textFields[0].setText(String.valueOf(bus.getNextCodeBUS()));
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
                        JOptionPane.showMessageDialog(null, "Tên không hợp lệ");
                    } else if(result == 2){
                        JOptionPane.showMessageDialog(null, "Địa chỉ không hợp lệ");
                    } else if(result == 3){
                        JOptionPane.showMessageDialog(null, "Số điện thoại không hợp lệ");
                    } else {
                        bus.editBUS(textFields, rowSelect);
                        JOptionPane.showMessageDialog(null, "Sửa thành công");
                        table.getSelectionModel().clearSelection();
                        status = 0;
                        removeAll();
                        add(infoNCC(), BorderLayout.NORTH);
                        add(tableNCC(), BorderLayout.CENTER);
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
        panelControl.add(editController());
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

    //table
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
        for(int i = 0;i < length;i++){
            String col = (String) table.getValueAt(rowSelect, i);
            textFields[i].setText(col);
        }
    }
}
