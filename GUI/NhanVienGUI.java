package task1.GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class NhanVienGUI extends JPanel implements MouseListener {
    private JPanel panelInput, panelButton, tableKH, input, panelInputBox, panelbtn, panelSearch;
    private JTable table;
    private JButton btn1, btn2, btn3;
    private JLabel searchJLabel;
    private JLabel[] labels;
    private JTextField searchBox;
    private JTextField[] textFields;
    private String[] lableInput = {"Ma SV", "Ho Ten", "Ngay Sinh", "Noi Sinh", "Dia Chi", "Dien Thoai", "Hinh Anh", "Email"};
    private double length = lableInput.length/2;
    private DefaultTableModel tableModel;
    private Object[][] values = {
            {"text1", "text2", "text3", "text4", "text5", "text6", "text7", "text8", "text9", "text10"},
            {"text11", "text2", "text3", "text4", "text5", "text6", "text7", "text8", "text9", "text10"}
    };
    private String[] header = {"1", "2", "3", "4", "5", "6", "7", "8", "9", " 10"};

    public NhanVienGUI(){
        setLayout(new GridLayout(2, 1, 0, 5));
        add(Nv_input());
        add(Nv_table());
    }
    //input
    public JPanel Nv_input(){
        input = new JPanel();
        labels = new JLabel[lableInput.length];
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
//        panelInputBox.setPreferredSize(new Dimension(0, 0));
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
        panelbtn.setLayout(new FlowLayout(FlowLayout.CENTER));
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
        tableKH = new JPanel();
        tableKH.setLayout(new BorderLayout());
        tableKH.setPreferredSize(new Dimension(1000, 300));
        table();
        return tableKH;
    }

    public void table(){
        tableModel = new DefaultTableModel(values, header);
        table = new JTable(tableModel);
        JScrollPane scrl = new JScrollPane(table);
        tableKH.add(scrl, BorderLayout.CENTER);
        table.addMouseListener(this);
    }

    public void tableMountClick(){
        int row = table.getSelectedRow();
        String col1 = (String) table.getValueAt(row, 0);
        textFields[0].setText(col1);
        String col2 = (String) table.getValueAt(row, 1);
        textFields[1].setText(col2);
        String col3 = (String) table.getValueAt(row, 2);
        textFields[2].setText(col3);
        String col4 = (String) table.getValueAt(row, 3);
        textFields[3].setText(col4);
        String col5 = (String) table.getValueAt(row, 4);
        textFields[4].setText(col5);
        String col6 = (String) table.getValueAt(row, 5);
        textFields[5].setText(col6);
        String col7 = (String) table.getValueAt(row, 6);
        textFields[6].setText(col7);
        String col8 = (String) table.getValueAt(row, 7);
        textFields[7].setText(col8);
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
