package task1.GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;

import task1.BUS.NhanVienBUS;
import task1.DTO.NhanVienDTO;

public class NhanVienGUI extends JPanel implements MouseListener {
    private JPanel panelInput, panelButton, tableNV, input, panelInputBox, panelbtn, panelSearch;
    private JTable table;
    private JButton btn1, btn2, btn3;
    private JLabel searchJLabel;
    private JLabel[] labels;
    private JTextField searchBox;
    private JTextField[] textFields;
    private String[] lableInput;
    private DefaultTableModel tableModel;

    public NhanVienGUI(){
        setLayout(new GridLayout(2, 1, 0, 5));
        add(Nv_input());
        add(Nv_table());
    }
    //input
    public JPanel Nv_input(){
        input = new JPanel();
        input.setLayout(new BorderLayout(5, 5));
        input.setPreferredSize(new Dimension(0, 430));
        input.setBackground(Color.white);
        input.add(inputSearch(), BorderLayout.NORTH);
        input.add(inputImage(), BorderLayout.WEST);
        input.add(inputBox(), BorderLayout.CENTER);
        return input;
    }

    public JPanel inputImage(){
        JPanel info = new JPanel();
        info.setPreferredSize(new Dimension(300, 0));
        info.setBackground(Color.red);
        return info;
    }

    public JPanel inputBox(){
        panelInputBox = new JPanel(new BorderLayout(0, 5));
        panelInputBox.setBackground(Color.blue);
        panelInputBox.add(inputItems(), BorderLayout.CENTER);
        panelInputBox.add(inputButton(), BorderLayout.SOUTH);
        return panelInputBox;
    }

    public JPanel inputSearch(){
        panelSearch = new JPanel();
        panelSearch.setLayout(new FlowLayout(FlowLayout.CENTER));
        panelSearch.setPreferredSize(new Dimension(0, 50));
        searchBox = new JTextField();
        searchJLabel = new JLabel("Tìm kiếm");

        searchBox.setPreferredSize(new Dimension(300, 40));
        panelSearch.add(searchJLabel);
        panelSearch.add(searchBox);
        return panelSearch;
    }

    public JPanel inputItems(){
        NhanVienDTO dto = new NhanVienDTO();
        lableInput = dto.getStringHeader();
        panelInput = new JPanel();
        textFields = new JTextField[lableInput.length];
        panelInput.setLayout(new FlowLayout(1, 20, 20));
        for(int i = 0;i < lableInput.length;i++){
            panelInput.add(item(new JLabel(lableInput[i] + " :"), textFields[i] = new JTextField()));
        }
        panelInput.setBackground(Color.ORANGE);
        return panelInput;
    }

    public JPanel inputButton(){
        panelbtn = new JPanel();
        panelbtn.setPreferredSize(new Dimension(0, 50));
        btn1 = createBtn("Them");
        btn2 = createBtn("Sua");
        btn3 = createBtn("Xoa");
        panelbtn.add(btn1);
        panelbtn.add(btn2);
        panelbtn.add(btn3);
        panelbtn.setBackground(Color.green);
        return panelbtn;
    }

    public JPanel item(JLabel title, JTextField inputField){
        JPanel item = new JPanel(new BorderLayout());
        title.setPreferredSize(new Dimension(100, 40));
        title.setOpaque(true);
        title.setBackground(Color.white);
        inputField.setPreferredSize(new Dimension(200, 40));
        item.add(title, BorderLayout.WEST);
        item.add(inputField, BorderLayout.CENTER);
        return item;
    }

    public JButton createBtn(String label){
        JButton btn = new JButton(label);
        btn = new JButton(label);
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setPreferredSize(new Dimension(100, 40));
        return btn;
    }

    //table
    public JPanel Nv_table(){
        tableNV = new JPanel();
        tableNV.setLayout(new BorderLayout());
        tableNV.setPreferredSize(new Dimension(1000, 300));
        table();
        return tableNV;
    }

    public void table(){
        NhanVienBUS nvBus = new NhanVienBUS();
        tableModel = new DefaultTableModel(nvBus.getAllValues(), nvBus.getAllHeader());
        table = new JTable(tableModel);
        JScrollPane scrl = new JScrollPane(table);
        tableNV.add(scrl, BorderLayout.CENTER);
        table.addMouseListener(this);
    }

    public void tableMountClick(){
        int row = table.getSelectedRow();
        for(int i = 0;i < lableInput.length;i++){
            String col = (String) table.getValueAt(row, i);
            textFields[i].setText(col);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        //table
        if(e.getSource() == table) {
            tableMountClick();
        }
        //add
        //edit
        //delete

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
