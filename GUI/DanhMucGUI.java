package task1.GUI;

import task1.BUS.DanhMucBUS;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class DanhMucGUI {
    private JFrame frameRole;
    private JPanel panelItems, panelConfirm;
    private JPanel panelCheckBoxs[];
    private JButton btnConfirm;
    private JCheckBox cb[];
    private DanhMucBUS bus;

    private String stringValue[];
    private String stringNameImage[];
    private int visited[];
    private int length;
    private int width = Toolkit.getDefaultToolkit().getScreenSize().width;
    private int height = Toolkit.getDefaultToolkit().getScreenSize().height;

    public DanhMucGUI(int[] visited){
        bus = new DanhMucBUS();
        stringValue = bus.getStringValue();
        stringNameImage = bus.getStringNameImage();
        length = bus.getCountCategory();
        panelCheckBoxs = new JPanel[length];
        cb = new JCheckBox[length];
        this.visited = visited;
        frameRole = new JFrame();
        frameRole.setLayout(new BorderLayout(5, 5));
        frameRole.add(items(), BorderLayout.CENTER);
        frameRole.add(confirm(), BorderLayout.SOUTH);

        frameRole.setSize(new Dimension(500, 500));
        frameRole.setLocation((width - frameRole.getWidth())/2, (height -frameRole.getHeight())/2);
        frameRole.setVisible(true);
    }
    public JPanel items(){
        panelItems = new JPanel(new FlowLayout(FlowLayout.CENTER, 10 , 10));
        panelItems.setBackground(Color.white);
        for(int i = 0;i < panelCheckBoxs.length;i++){
            cb[i] = new JCheckBox();
            if(visited[i] == 1){
                cb[i].setSelected(true);
            }
            panelCheckBoxs[i] = itemCheckBox(stringValue[i], stringNameImage[i], cb[i]);
            panelCheckBoxs[i].addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    handleEvent(e);
                }
            });
            panelItems.add(panelCheckBoxs[i]);
        }
        return panelItems;
    }

    public JPanel confirm(){
        panelConfirm = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelConfirm.setPreferredSize(new Dimension(0, 50));
        btnConfirm = new JButton("Xác nhận");
        btnConfirm.setPreferredSize(new Dimension(120, 40));
        btnConfirm.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                frameRole.dispose();
            }
        });
        panelConfirm.add(btnConfirm);
        panelConfirm.setBackground(Color.white);
        return panelConfirm;
    }

    public ImageIcon resizeImage(String path, int width, int height){
        ImageIcon image = new ImageIcon(path);
        System.out.println(path);
        ImageIcon newImage = new ImageIcon(image.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH));
        return newImage;
    }

    public JPanel itemCheckBox(String value, String name, JCheckBox checkBox){
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.green);
        panel.setPreferredSize(new Dimension(200, 40));
        JLabel lbIcon = new JLabel();
        JLabel lbName = new JLabel(value, SwingConstants.LEFT);
        lbName.setPreferredSize(new Dimension(120, 40));
        lbIcon.setIcon(resizeImage("GUI/image/" + name, 40, 40));
        panel.add(checkBox, BorderLayout.WEST);
        panel.add(lbIcon, BorderLayout.CENTER);
        panel.add(lbName, BorderLayout.EAST);
        return panel;
    }

    public void handleEvent(MouseEvent e){
        for(int i = 0;i < length;i++){
            if(e.getSource() == panelCheckBoxs[i]){
//                System.out.println(visited[i]);
                if(visited[i] == 0){
                    cb[i].setSelected(true);
                    visited[i] = 1;
                    AccountGUI.changed++;
                } else {
                    cb[i].setSelected(false);
                    visited[i] = 0;
                    AccountGUI.changed--;
                }
            }
        }
        System.out.println("Changed : " +  AccountGUI.changed);
    }
}
