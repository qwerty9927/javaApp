package task1.GUI;


import task1.BUS.NhaCungCapBUS;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;

public class SubNCC {
    private JFrame frameStaff = new JFrame();
    private JPanel panelImage, panelContent, panelAction, panelTable, panelSearch;
    private JLabel labelImage, labelSearch;
    private JTextField tFieldSearch, tf;
    private DefaultTableModel tableModel;
    private JTable table;
    private JButton btnConfirm;
    private NhaCungCapBUS bus;
    private String mancc = "";

    private int rowSelect;
    private int length;
    private int posURLInLength;
    private int width = Toolkit.getDefaultToolkit().getScreenSize().width;
    private int height = Toolkit.getDefaultToolkit().getScreenSize().height;
    public SubNCC(JTextField tf){
        this.tf = tf;
        bus = new NhaCungCapBUS();
        length = bus.getAllHeaderBUS().length;
        posURLInLength = length - 3;
        frameStaff.setLayout(new BorderLayout(5, 5));
        frameStaff.add(content(), BorderLayout.WEST);
        frameStaff.add(tableNV(), BorderLayout.CENTER);

        frameStaff.setSize(new Dimension(1200, 700));
        frameStaff.setLocation((width - frameStaff.getWidth())/2, (height -frameStaff.getHeight())/2);
        frameStaff.setVisible(true);
    }

    public JPanel content(){
        panelContent = new JPanel();
        panelContent.setLayout(new BorderLayout(5,5));
        panelContent.setPreferredSize(new Dimension(300, 0));
//        panelContent.add(imageBox(), BorderLayout.NORTH);
        panelContent.add(action(), BorderLayout.CENTER);
        return panelContent;
    }

    public JPanel searchBox(){
        panelSearch = new JPanel();
        panelSearch.setLayout(new FlowLayout(FlowLayout.CENTER));
        panelSearch.setPreferredSize(new Dimension(300, 50));
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

            }
        });
        tFieldSearch.setPreferredSize(new Dimension(200, 40));
        panelSearch.add(labelSearch);
        panelSearch.add(tFieldSearch);
        return panelSearch;
    }

    public JButton comfirm(){
        btnConfirm = new JButton("Xác nhận");
        btnConfirm.setPreferredSize(new Dimension(100, 40));
        btnConfirm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(mancc.isEmpty()){
                    JOptionPane.showMessageDialog(null, "Cần chọn nhân viên trước khi xác nhận");
                } else {
                    tf.setText(mancc);
                    frameStaff.dispose();
                }
            }
        });
        return btnConfirm;
    }

    public JPanel action(){
        panelAction = new JPanel();
        panelAction.setLayout(new FlowLayout(FlowLayout.CENTER));
        panelAction.setBackground(Color.white);
        panelAction.add(searchBox());
        panelAction.add(comfirm());
        return panelAction;
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
        rowSelect = table.getSelectedRow();
        mancc = (String) table.getValueAt(rowSelect, 0);
    }
}
