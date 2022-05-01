package task1.GUI;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import task1.BUS.NhanVienBUS;

public class NhanVienGUI extends JPanel {
    private JPanel panelInfoBox, panelInputBox, panelTable, panelInput, panelControl, panelSearch, panelImage, panelSubmit;
    private JTable table;
    private JButton btnAdd, btnEdit, btnDelete, btnSubmitAdd, btnSubmitEdit, btnChoiceImage;
    private JLabel labelSearch, labelImage;
    private JLabel[] labels;
    private JTextField tFieldSearch;
    private JTextField[] textFields;
    private String[] stringLabel;
    private DefaultTableModel tableModel;

    private NhanVienBUS nvBus;
    private int length;
    private String nameImage = "";


    public NhanVienGUI(){
        nvBus = new NhanVienBUS();
        stringLabel = nvBus.getStringHeaderBUS();
        length = stringLabel.length;
        setLayout(new BorderLayout(0, 5));
        add(infoNV(), BorderLayout.NORTH);
        add(tableNV(), BorderLayout.CENTER);
    }
    //input
    public JPanel infoNV(){
        panelInfoBox = new JPanel();
        panelInfoBox.setLayout(new BorderLayout(5, 5));
        panelInfoBox.setPreferredSize(new Dimension(0, 450));
        panelInfoBox.setBackground(Color.white);
        panelInfoBox.add(searchBox(), BorderLayout.NORTH);
        panelInfoBox.add(imageBox(), BorderLayout.WEST);
        panelInfoBox.add(inputBox(), BorderLayout.CENTER);
        return panelInfoBox;
    }

    public JPanel imageBox(){
        panelImage = new JPanel();
        panelImage.setPreferredSize(new Dimension(300, 0));
        panelImage.setBackground(Color.red);
        labelImage = new JLabel();
        panelImage.add(labelImage);
        return panelImage;
    }

    public JPanel searchBox(){
        panelSearch = new JPanel();
        panelSearch.setLayout(new FlowLayout(FlowLayout.CENTER));
        panelSearch.setPreferredSize(new Dimension(0, 50));
        tFieldSearch = new JTextField();
        labelSearch = new JLabel("Tìm kiếm");

        tFieldSearch.setPreferredSize(new Dimension(300, 40));
        panelSearch.add(labelSearch);
        panelSearch.add(tFieldSearch);
        return panelSearch;
    }

    public JPanel inputBox(){
        panelSubmit = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelInputBox = new JPanel(new BorderLayout(0, 5));
        panelInputBox.setBackground(Color.blue);
        panelInputBox.add(inputItems(), BorderLayout.NORTH);
        panelInputBox.add(panelSubmit, BorderLayout.CENTER);
        panelInputBox.add(controller(), BorderLayout.SOUTH);
        return panelInputBox;
    }

    public JPanel inputItems(){
        panelInput = new JPanel();
        textFields = new JTextField[length];
        panelInput.setPreferredSize(new Dimension(0, 280));
        panelInput.setLayout(new FlowLayout(1, 15, 15));
        for(int i = 0;i < length;i++){
            textFields[i] = new JTextField();
            panelInput.add(item(new JLabel(stringLabel[i] + " :"), textFields[i]));
        }
        textFields[0].setEnabled(false);
        panelInput.setBackground(Color.ORANGE);
        return panelInput;
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

    public ImageIcon resizeImage(String path, int width, int height){
        ImageIcon image = new ImageIcon(path);
        System.out.println(path);
        ImageIcon newImage = new ImageIcon(image.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH));
        return newImage;
    }

    public void choiceImage(){
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        FileNameExtensionFilter filter = new FileNameExtensionFilter("*.Images", "jpg", "gif", "png");
        fileChooser.addChoosableFileFilter(filter);

        int result = fileChooser.showOpenDialog(this);
        if(result == JFileChooser.APPROVE_OPTION){
            File selectedFile = fileChooser.getSelectedFile();
            nameImage = selectedFile.getName();
            System.out.println(nameImage);
            labelImage.setIcon(resizeImage("GUI/image/" + nameImage, 300, 450));
        }
    }

    public JPanel controller(){
        panelControl = new JPanel();
        panelControl.setPreferredSize(new Dimension(0, 50));
        btnAdd = createBtn("Them");
        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textFields[length-1].setEnabled(false);
                for(int i=1;i < length;i++){
                    textFields[i].setText("");
                }
                textFields[0].setText(String.valueOf(nvBus.getNextCodeBUS()));
                textFields[length - 1].setText("4");
//                System.out.println(textFields[1].getText());
                btnChoiceImage = createBtn("Chon anh");
                btnChoiceImage.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        choiceImage();
                    }
                });
                panelInput.add(btnChoiceImage);
                if(panelInput.getComponentCount() > length + 1){
                    panelInput.remove(panelInput.getComponentCount() - 1);
                }
                ActionAdd();
            }
        });
        btnEdit = createBtn("Sua");
        btnEdit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textFields[length-1].setEnabled(true);
                btnChoiceImage = createBtn("Chon anh");
                btnChoiceImage.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        choiceImage();
                    }
                });
                panelInput.add(btnChoiceImage);
                if(panelInput.getComponentCount() > length + 1){
                    panelInput.remove(panelInput.getComponentCount() - 1);
                }
                ActionEdit();
            }
        });
        btnDelete = createBtn("Xoa");
        panelControl.add(btnAdd);
        panelControl.add(btnEdit);
        panelControl.add(btnDelete);
        panelControl.setBackground(Color.green);
        return panelControl;
    }

    public JButton createBtn(String label){
        JButton btn = new JButton(label);
        btn = new JButton(label);
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setPreferredSize(new Dimension(100, 40));
        return btn;
    }

    public void ActionAdd(){
        System.out.println("entry");
        if(panelSubmit.getComponentCount() > 0){
            panelSubmit.remove(0);
        }
        btnSubmitAdd = createBtn("Them");
        btnSubmitAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int result = nvBus.check(textFields);
                if(result == 1){
                    JOptionPane.showMessageDialog(null, "Ho ten khong hop le");
                } else if(result == 2){
                    JOptionPane.showMessageDialog(null, "Dia chi khong hop le");
                } else if(result == 3){
                    JOptionPane.showMessageDialog(null, "So dien thoai khong hop le");
                } else if(result == 4){
                    JOptionPane.showMessageDialog(null, "Email khong hop le");
                } else {
                    JOptionPane.showMessageDialog(null, "Thanh cong");
                }
            }
        });
        panelSubmit.add(btnSubmitAdd);
        panelInputBox.repaint();
        panelInputBox.revalidate();
    }

    public void ActionEdit(){
        System.out.println("entry");
        if(panelSubmit.getComponentCount() > 0){
            panelSubmit.remove(0);
        }
        btnSubmitEdit = createBtn("Sua");
        btnSubmitEdit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(2);
            }
        });
        panelSubmit.add(btnSubmitEdit);
        panelInputBox.repaint();
        panelInputBox.revalidate();
    }

    //table
    public JPanel tableNV() {
        panelTable = new JPanel();
        panelTable.setLayout(new BorderLayout());
        panelTable.setPreferredSize(new Dimension(0, 250));
        panelTable.add(table(), BorderLayout.NORTH);
        return panelTable;
    }

    public JScrollPane table(){
        tableModel = new DefaultTableModel(nvBus.getAllValuesBUS(), nvBus.getAllHeaderBUS());
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
        int row = table.getSelectedRow();
        for(int i = 0;i < length;i++){
            String col = (String) table.getValueAt(row, i);
            textFields[i].setText(col);
        }
        if(panelInput.getComponentCount() > length){
            panelInput.remove(panelInput.getComponentCount() - 1);
            panelSubmit.remove(0);
            panelSubmit.repaint();
            panelInput.repaint();
        }
    }
}
