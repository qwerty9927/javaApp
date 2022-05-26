package task1.GUI;

import task1.BUS.AccountBUS;
import task1.BUS.DanhMucBUS;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.*;
import java.io.File;


public class AccountGUI extends JPanel {
    private JPanel panelInfoBox, panelInputBox, panelTable, panelInput, panelControl, panelSearch, panelImage, panelSubmit;
    private JTable table;
    private JButton btnAdd, btnEdit, btnDelete, btnChoiceImage, btnRole, btnShow;
    private JLabel labelSearch, labelImage;
    private JLabel[] labels;
    private JTextField tFieldSearch;
    private JTextField[] textFields;
    private JPasswordField password;
    private JPasswordField rePassword;
    private String[] stringLabel;
    private String[] stringLabelAdd;
    private DefaultTableModel tableModel;

    private AccountBUS bus;
    private DanhMucBUS busDM;
    private int[] visited;
    private int rowSelect;
    private int length;
    private String usernameClick;
    private int passChanged = 0;
    private String[] stringExceptURL;
    private int lenghtExceptURL;
    private int posURLInLength;
    private int lengthStringLabelAdd;
    public static int changed = 0;
    private int status = 0;
    private String nameImage = "default.png";


    public AccountGUI(){
        bus = new AccountBUS();
        busDM = new DanhMucBUS();
        visited = new int[busDM.getCountCategory()];
        stringLabel = bus.getStringHeaderBUS();
        length = stringLabel.length;
        stringExceptURL = bus.getLengthInputBUS();
        lenghtExceptURL = stringExceptURL.length;
        posURLInLength = length - 1;
        stringLabelAdd = bus.getStringHeaderAddBUS();
        lengthStringLabelAdd = stringLabelAdd.length;
        setLayout(new BorderLayout(0, 5));
        add(infoACC(), BorderLayout.NORTH);
        add(tableACC(), BorderLayout.CENTER);
    }
    //input
    public JPanel infoACC(){
        panelInfoBox = new JPanel();
        panelInfoBox.setLayout(new BorderLayout(5, 5));
        panelInfoBox.setPreferredSize(new Dimension(0, 400));
        panelInfoBox.add(searchBox(), BorderLayout.NORTH);
        panelInfoBox.add(imageBox(), BorderLayout.WEST);
        panelInfoBox.add(inputBox(), BorderLayout.CENTER);
        return panelInfoBox;
    }

    public JPanel imageBox(){
        panelImage = new JPanel();
        panelImage.setPreferredSize(new Dimension(300, 0));
        panelImage.setBackground(Color.white);
        labelImage = new JLabel();
        panelImage.add(labelImage);
        return panelImage;
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
        textFields = new JTextField[lengthStringLabelAdd];
        panelInput.setPreferredSize(new Dimension(0, 150));
        panelInput.setLayout(new FlowLayout(1, 15, 15));
        for(int i = 0;i < lengthStringLabelAdd;i++){
            textFields[i] = new JTextField();
            if(stringLabelAdd[i].equalsIgnoreCase("Manv")){
                JPanel item = new JPanel(new BorderLayout());
                JLabel title = new JLabel(stringLabelAdd[i] + " :");
                btnShow = new JButton("...");
                btnShow.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        choiceStaff(textFields[4]);
                    }
                });
                title.setPreferredSize(new Dimension(100, 25));
                title.setOpaque(true);
                title.setBackground(Color.white);
                textFields[i].setPreferredSize(new Dimension(175, 25));
                textFields[i].setEnabled(false);
                btnShow.setPreferredSize(new Dimension(25, 25));
                item.add(title, BorderLayout.WEST);
                item.add(textFields[i], BorderLayout.CENTER);
                item.add(btnShow, BorderLayout.EAST);
                panelInput.add(item);
            } else if(stringLabelAdd[i].equalsIgnoreCase("Password")){
                textFields[i] = password = new JPasswordField();
                password.getDocument().addDocumentListener(new DocumentListener() {
                    @Override
                    public void insertUpdate(DocumentEvent e) {
                        passChanged = 1;
                        System.out.println(passChanged);
                    }

                    @Override
                    public void removeUpdate(DocumentEvent e) {
                        passChanged = 1;
                        System.out.println(passChanged);
                    }

                    @Override
                    public void changedUpdate(DocumentEvent e) {

                    }
                });
                password.setEchoChar('\u25cf');
                panelInput.add(item(new JLabel(stringLabelAdd[i] + ";"), textFields[i]));
            } else if(stringLabelAdd[i].equalsIgnoreCase("Re-Password")){
                textFields[i] = rePassword = new JPasswordField();
                rePassword.setEchoChar('\u25cf');
                panelInput.add(item(new JLabel(stringLabelAdd[i] + ";"), textFields[i]));
            } else {
              panelInput.add(item(new JLabel(stringLabelAdd[i] + " :"), textFields[i]));
            }
        }
        panelInput.setBackground(Color.WHITE);
    }

    public void fetureBtn(){
        btnChoiceImage = createBtn("Chọn ảnh");
        RoundedBorder.BorderRadius2(btnChoiceImage);
        btnChoiceImage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                choiceImage();
            }
        });

        btnRole = createBtn("Chọn quyền");
        RoundedBorder.BorderRadius2(btnRole);
        btnRole.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                choiceRole();
            }
        });

        JPanel horizontalBtn = new JPanel();
        horizontalBtn.setBackground(Color.white);
        horizontalBtn.setLayout(new BoxLayout(horizontalBtn, BoxLayout.X_AXIS));
        btnRole.setMaximumSize(new Dimension(120, 40));
        btnChoiceImage.setMaximumSize(new Dimension(120, 40));
        horizontalBtn.add(btnRole);
        horizontalBtn.add(Box.createRigidArea(new Dimension(5, 0)));
        horizontalBtn.add(btnChoiceImage);

        JPanel showHidePassword = new JPanel(new FlowLayout(FlowLayout.CENTER));
        showHidePassword.setBackground(Color.white);
        JLabel labelShowHide = new JLabel("Hiện password");
        JCheckBox checkBox = new JCheckBox();
        checkBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(checkBox.isSelected()){
                    labelShowHide.setText("Ẩn password");
                    password.setEchoChar((char)0);
                    rePassword.setEchoChar((char)0);
                } else {
                    labelShowHide.setText("Hiện password");
                    password.setEchoChar('\u25cf');
                    rePassword.setEchoChar('\u25cf');
                }
            }
        });
        showHidePassword.add(checkBox);
        showHidePassword.add(labelShowHide);
        panelSubmit.add(showHidePassword);
        panelSubmit.add(horizontalBtn);
        panelSubmit.add(Box.createRigidArea(new Dimension(0, 5)));
    }

    public JPanel item(JLabel title, JTextComponent inputField){
        JPanel item = new JPanel(new BorderLayout());
        JTextField input = (JTextField) inputField;
        title.setPreferredSize(new Dimension(100, 25));
        title.setOpaque(true);
        title.setBackground(Color.white);
        input.setPreferredSize(new Dimension(200, 25));
        item.add(title, BorderLayout.WEST);
        item.add(input, BorderLayout.CENTER);
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
            nameImage = selectedFile.exists() ? selectedFile.getName() : "default.png";
            System.out.println(nameImage);
            labelImage.setIcon(resizeImage("GUI/image/" + nameImage, 300, 450));
        }
    }

    public void choiceStaff(JTextField tf){
        SubNhanVienGUI snv = new SubNhanVienGUI(tf);
    }

    public void choiceRole(){
        DanhMucGUI dm = new DanhMucGUI(visited);
    }

    public JButton addController(){
        btnAdd = createBtn("Thêm");
        RoundedBorder.BorderRadius1(btnAdd);
        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(status == 0){
                    int result = bus.checkBUS(textFields);
                    if(result == 0){
                        JOptionPane.showMessageDialog(null, "Nhập username");
                        textFields[0].requestFocus();
                        textFields[0].selectAll();
                    } else if(result == 1){
                        JOptionPane.showMessageDialog(null, "Password cần dài hơn 8 ký tự");
                        textFields[1].requestFocus();
                        textFields[1].selectAll();
                    } else if(result == 2){
                        JOptionPane.showMessageDialog(null, "Re password không trùng khớp");
                        textFields[2].requestFocus();
                        textFields[2].selectAll();
                    } else if(result == 3){
                        JOptionPane.showMessageDialog(null, "Email không hợp lệ");
                        textFields[3].requestFocus();
                        textFields[3].selectAll();
                    } else if(result == 4){
                        JOptionPane.showMessageDialog(null, "Manv không hơp lệ");
                    } else {
                        if(!bus.checkUsernameBUS(textFields[0].getText())){
                            String roleID = bus.getNextRoleIDBUS();
                            if(changed != 0){
                                busDM.addDM(visited, roleID);
                                bus.addBUS(textFields, nameImage, roleID);
                                changed = 0;
                            } else {
                                bus.addBUS(textFields, nameImage, roleID);
                            }
                            JOptionPane.showMessageDialog(null, "Thêm thành công");
                            nameImage = "default.png";
                            removeAll();
                            add(infoACC(), BorderLayout.NORTH);
                            add(tableACC(), BorderLayout.CENTER);
                            repaint();
                            revalidate();
                        } else {
                            JOptionPane.showMessageDialog(null, "Tài khoản đã tồn tại");
                        }
                    }
                } else {
                    panelInput.removeAll();
                    inputItems();
                    panelInput.repaint();
                    panelInput.revalidate();
                    status = 0;
                    labelImage.setIcon(null);
                }
            }
        });
        return btnAdd;
    }

    public JButton editController(){
        btnEdit = createBtn("Sửa");
        RoundedBorder.BorderRadius1(btnEdit);
        btnEdit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(status == 1){
                    int result = bus.checkBUS(textFields);
                    if(result == 1){
                        JOptionPane.showMessageDialog(null, "Password cần dài hơn 8 ký tự");
                        textFields[1].requestFocus();
                        textFields[1].selectAll();
                    } else if(result == 2){
                        JOptionPane.showMessageDialog(null, "Re password không trùng khớp");
                        textFields[2].requestFocus();
                        textFields[2].selectAll();
                    } else if(result == 3){
                        JOptionPane.showMessageDialog(null, "Email không hợp lệ");
                        textFields[3].requestFocus();
                        textFields[3].selectAll();
                    } else if(result == 4){
                        JOptionPane.showMessageDialog(null, "Manv không hơp lệ");
                    } else {
                        System.out.println(changed);

                        if(changed != 0){
                            System.out.println("change");
                            String roleID = bus.getRoleIDBUS(usernameClick);
//                            String newRoleID = bus.getNextRoleIDBUS();
                            busDM.editDM(visited, roleID);
                            changed = 0;
                        }
                        bus.editBUS(textFields, nameImage, rowSelect, passChanged);
                        JOptionPane.showMessageDialog(null, "Sửa thành công");
                        table.getSelectionModel().clearSelection();
                        status = 0;
                        removeAll();
                        add(infoACC(), BorderLayout.NORTH);
                        add(tableACC(), BorderLayout.CENTER);
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
        RoundedBorder.BorderRadius1(btnDelete);
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
                        add(infoACC(), BorderLayout.NORTH);
                        add(tableACC(), BorderLayout.CENTER);
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
    public JPanel tableACC() {
        panelTable = new JPanel();
        panelTable.setLayout(new BorderLayout());
        panelTable.setPreferredSize(new Dimension(0, 250));
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
        String col = (String) table.getValueAt(rowSelect, 0);
        usernameClick = col;
        textFields[0].setText(col);
        textFields[0].setEnabled(false);
        col = (String) table.getValueAt(rowSelect, 1);
        textFields[1].setText(col);
        col = (String) table.getValueAt(rowSelect, 1);
        textFields[2].setText(col);
        col = (String) table.getValueAt(rowSelect, 2);
        textFields[3].setText(col);
        col = (String) table.getValueAt(rowSelect, 3);
        textFields[4].setText(col);

        //take category
        visited = busDM.getCategory(bus.getRoleID((String) table.getValueAt(rowSelect, 0)));

        // Image
        labelImage.setIcon(resizeImage("GUI/image/" + table.getValueAt(rowSelect, posURLInLength), 300, 400));
        nameImage = (String) table.getValueAt(rowSelect, posURLInLength);
        passChanged = 0;

    }
}
