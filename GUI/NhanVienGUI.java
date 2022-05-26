package task1.GUI;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import org.jdatepicker.JDatePanel;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.SqlDateModel;
import org.jdatepicker.impl.UtilDateModel;
import task1.BUS.NhanVienBUS;

public class NhanVienGUI extends JPanel {
    private JPanel panelInfoBox, panelInputBox, panelTable, panelInput, panelControl, panelSearch, panelImage, panelSubmit;
    private JTable table;
    private JButton btnAdd, btnEdit, btnDelete, btnSubmitAdd, btnSubmitEdit, btnSubmitDelete, btnChoiceImage;
    private JLabel labelSearch, labelImage;
    private JLabel[] labels;
    private JTextField tFieldSearch;
    private JTextField[] textFields;
    private JDatePickerImpl workingDate;
    private JDatePickerImpl dateOfBirth;
    private String[] stringLabel;
    private DefaultTableModel tableModel;

    private NhanVienBUS bus;
    private int rowSelect;
    private int length;
    private int lengthExceptSomeInput;
    private int posWorkingDateInLength;
    private int posDateOfBirthInLength;
    private int posURLInLength;
    private int status = 0;
    private String stringValueWorkingDate;
    private String stringValueDateOfBirth;
    private String nameImage = "default.png";


    public NhanVienGUI(){
        bus = new NhanVienBUS();
        stringLabel = bus.getStringHeaderBUS();
        length = stringLabel.length;
        posDateOfBirthInLength = length - 1;
        posWorkingDateInLength = length - 2;
        posURLInLength = length - 3;
        lengthExceptSomeInput = length - 3;
        setLayout(new BorderLayout(0, 5));
        add(infoNV(), BorderLayout.NORTH);
        add(tableNV(), BorderLayout.CENTER);
    }
    //input
    public JPanel infoNV(){
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
                bus.searchBus(tFieldSearch.getText().toLowerCase());
                System.out.println(tFieldSearch.getText());
                panelTable.removeAll();
                panelTable.add(table());
                panelTable.repaint();
                panelTable.revalidate();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                bus.searchBus(tFieldSearch.getText().toLowerCase());
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
        textFields = new JTextField[lengthExceptSomeInput];
        panelInput.setPreferredSize(new Dimension(0, 200));
        panelInput.setLayout(new FlowLayout(1, 15, 15));
        for(int i = 0;i < lengthExceptSomeInput;i++){
            textFields[i] = new JTextField();
            panelInput.add(item(new JLabel(stringLabel[i] + " :"), textFields[i]));
        }
        workingDate = DateBoard();
        dateOfBirth = DateBoard();
        workingDate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(workingDate.getModel().getValue());
            }
        });
        dateOfBirth.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(dateOfBirth.getModel().getValue());
            }
        });

        panelInput.add(itemOfDate(new JLabel("Ngày sinh :"), dateOfBirth));
        panelInput.add(itemOfDate(new JLabel("Ngày vào làm :"), workingDate));
        textFields[0].setEnabled(false);
        textFields[0].setText(String.valueOf(bus.getNextCodeBUS()));
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
        btnChoiceImage.setMaximumSize(new Dimension(120, 40));
        panelSubmit.add(btnChoiceImage);
        panelSubmit.add(Box.createRigidArea(new Dimension(0, 5)));
    }

    public JDatePickerImpl DateBoard(){
        JDatePickerImpl datePicker;
        SqlDateModel model = new SqlDateModel();
        Properties p = new Properties();
        p.put("text.day", "Day");
        p.put("text.month", "Month");
        p.put("text.year", "Year");
        JDatePanelImpl panel = new JDatePanelImpl(model, p);
        datePicker = new JDatePickerImpl(panel, new JFormattedTextField.AbstractFormatter() {
            @Override
            public Object stringToValue(String text) throws ParseException {
                return null;
            }

            @Override
            public String valueToString(Object value) throws ParseException {
                if(value != null){
                    Calendar cal = (Calendar) value;
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    String strDate = format.format(cal.getTime());
                    return strDate;
                }
                return "";
            }
        });
        return datePicker;
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

    public JPanel itemOfDate(JLabel title, JDatePickerImpl datePicker){
        JPanel item = new JPanel(new BorderLayout());
        title.setPreferredSize(new Dimension(100, 0));
        title.setOpaque(true);
        title.setBackground(Color.white);
        item.add(title, BorderLayout.WEST);
        item.add(datePicker, BorderLayout.CENTER);
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
            labelImage.setIcon(resizeImage("GUI/image/" + nameImage, 300, 450));
        }
    }

    public JButton addController(){
        btnAdd = createBtn("Thêm");
        RoundedBorder.BorderRadius1(btnAdd);
        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(panelInput.getComponentCount());
                if(status == 0){
                    int result = bus.checkBUS(textFields);
                    if(result == 1){
                        JOptionPane.showMessageDialog(null, "Họ tên không hợp lệ");
                        textFields[1].requestFocus();
                        textFields[1].selectAll();
                    } else if(result == 2){
                        JOptionPane.showMessageDialog(null, "Địa chỉ không hợp lệ");
                        textFields[2].requestFocus();
                        textFields[2].selectAll();
                    } else if(result == 3){
                        JOptionPane.showMessageDialog(null, "Số điện thoại không hợp lệ");
                        textFields[3].requestFocus();
                        textFields[3].selectAll();
                    } else if(result == 4){
                        JOptionPane.showMessageDialog(null, "Email không hợp lệ");
                        textFields[4].requestFocus();
                        textFields[4].selectAll();
                    } else {
                        String[] arrWorkingDate = null;
                        String[] arrDateOfBirth = null;
                        try{
                            Date selectedaDateOfBirth = (Date) dateOfBirth.getModel().getValue();
                            DateFormat dfDateOfBirth = new SimpleDateFormat("yyyy-MM-dd");
                            stringValueDateOfBirth = dfDateOfBirth.format(selectedaDateOfBirth);
                            arrDateOfBirth = stringValueDateOfBirth.split("-");
                            if(arrWorkingDate != null){
                                if(Integer.parseInt(arrDateOfBirth[0]) > Integer.parseInt(arrWorkingDate[0]) - 18){
                                    JOptionPane.showMessageDialog(null, "Ngày tháng không hợp lệ");
                                    return;
                                }
                            }
                        } catch(Exception ex){
                            JOptionPane.showMessageDialog(null, "Cần chọn ngày sinh");
                            return;
                        }

                        try{
                            Date selectedOfWorkingDate = (Date) workingDate.getModel().getValue();
                            DateFormat dfWorkingDate = new SimpleDateFormat("yyyy-MM-dd");
                            stringValueWorkingDate = dfWorkingDate.format(selectedOfWorkingDate);
                            arrWorkingDate = stringValueWorkingDate.split("-");
                            if(arrDateOfBirth != null){
                                if(Integer.parseInt(arrDateOfBirth[0]) > Integer.parseInt(arrWorkingDate[0]) - 18){
                                    JOptionPane.showMessageDialog(null, "Ngày tháng không hợp lệ");
                                    return;
                                }
                            }
                        } catch(Exception ex){
                            JOptionPane.showMessageDialog(null, "Cần chọn ngày vào làm");
                            return;
                        }

                        bus.addBus(textFields, stringValueWorkingDate, stringValueDateOfBirth, nameImage);
                        JOptionPane.showMessageDialog(null, "Thêm thành công");
                        nameImage = "default.png";
                        removeAll();
                        add(infoNV(), BorderLayout.NORTH);
                        add(tableNV(), BorderLayout.CENTER);
                        repaint();
                        revalidate();
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
                        JOptionPane.showMessageDialog(null, "Họ tên không hợp lệ");
                        textFields[1].requestFocus();
                        textFields[1].selectAll();
                    } else if(result == 2){
                        JOptionPane.showMessageDialog(null, "Địa chỉ không hợp lệ");
                        textFields[2].requestFocus();
                        textFields[2].selectAll();
                    } else if(result == 3){
                        JOptionPane.showMessageDialog(null, "Số điện thoại không hợp lệ");
                        textFields[3].requestFocus();
                        textFields[3].selectAll();
                    } else if(result == 4){
                        JOptionPane.showMessageDialog(null, "Email không hợp lệ");
                        textFields[4].requestFocus();
                        textFields[4].selectAll();
                    } else {
                        String[] arrWorkingDate = null;
                        String[] arrDateOfBirth = null;
                        try{
                            Date selectedaDateOfBirth = (Date) dateOfBirth.getModel().getValue();
                            DateFormat dfDateOfBirth = new SimpleDateFormat("yyyy-MM-dd");
                            stringValueDateOfBirth = dfDateOfBirth.format(selectedaDateOfBirth);
                            arrDateOfBirth = stringValueDateOfBirth.split("-");
                            if(arrWorkingDate != null){
                                if(Integer.parseInt(arrDateOfBirth[0]) > Integer.parseInt(arrWorkingDate[0]) - 18){
                                    JOptionPane.showMessageDialog(null, "Ngày tháng không hợp lệ");
                                    return;
                                }
                            }
                        } catch(Exception ex){
                            JOptionPane.showMessageDialog(null, "Cần chọn ngày sinh");
                            return;
                        }

                        try{
                            Date selectedOfWorkingDate = (Date) workingDate.getModel().getValue();
                            DateFormat dfWorkingDate = new SimpleDateFormat("yyyy-MM-dd");
                            stringValueWorkingDate = dfWorkingDate.format(selectedOfWorkingDate);
                            arrWorkingDate = stringValueWorkingDate.split("-");
                            if(arrDateOfBirth != null){
                                if(Integer.parseInt(arrDateOfBirth[0]) > Integer.parseInt(arrWorkingDate[0]) - 18){
                                    JOptionPane.showMessageDialog(null, "Ngày tháng không hợp lệ");
                                    return;
                                }
                            }
                        } catch(Exception ex){
                            JOptionPane.showMessageDialog(null, "Cần chọn ngày vào làm");
                            return;
                        }

                        bus.editBus(textFields, stringValueWorkingDate, stringValueDateOfBirth, nameImage, rowSelect);
                        JOptionPane.showMessageDialog(null, "Sửa thành công");
                        removeAll();
                        add(infoNV(), BorderLayout.NORTH);
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
        RoundedBorder.BorderRadius1(btnDelete);
        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(status != 1){
                    JOptionPane.showMessageDialog(null, "Chọn một dòng trong bảng để xóa");
                } else {
                    int confirm = JOptionPane.showConfirmDialog(null, "Bạn muốn xóa ?");
                    if(confirm == JOptionPane.YES_OPTION){
                        bus.deleteBus(textFields, 0, rowSelect);
                        table.getSelectionModel().clearSelection();
                        status = 0;
                        removeAll();
                        add(infoNV(), BorderLayout.NORTH);
                        add(tableNV(), BorderLayout.CENTER);
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
    public JPanel tableNV() {
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
        for(int i = 0;i < lengthExceptSomeInput;i++){
            String col = (String) table.getValueAt(rowSelect, i);
            textFields[i].setText(col);
        }

        // Image
        labelImage.setIcon(resizeImage("GUI/image/" + table.getValueAt(rowSelect, posURLInLength), 300, 400));
        nameImage = (String) table.getValueAt(rowSelect, posURLInLength);

        // Working Date
        String stringOfWorkingDate = (String) table.getValueAt(rowSelect, posWorkingDateInLength);
        String[] arrOfWorkingDate = stringOfWorkingDate.split("-");
        System.out.println(posWorkingDateInLength);
        System.out.println(stringOfWorkingDate);
        workingDate.getModel().setSelected(true);
        workingDate.getModel().setDate(Integer.parseInt(arrOfWorkingDate[0]), Integer.parseInt(arrOfWorkingDate[1]) - 1, Integer.parseInt(arrOfWorkingDate[2]));

        // Date of Birth
        String stringOfDateOfBirth = (String) table.getValueAt(rowSelect, posDateOfBirthInLength);
        String[] arrOfDateOfBirth = stringOfDateOfBirth.split("-");
        dateOfBirth.getModel().setSelected(true);
        dateOfBirth.getModel().setDate(Integer.parseInt(arrOfDateOfBirth[0]), Integer.parseInt(arrOfDateOfBirth[1]) - 1, Integer.parseInt(arrOfDateOfBirth[2]));

        if(panelInput.getComponentCount() > length - 1){
            panelInput.remove(panelInput.getComponentCount() - 1);
            panelSubmit.remove(0);
            panelSubmit.repaint();
            panelInput.repaint();
        }
    }
}
