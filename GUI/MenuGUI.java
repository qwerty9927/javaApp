package task1.GUI;

import task1.BUS.MenuBUS;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class MenuGUI extends JPanel implements MouseListener {
    JFrame frame;
    JPanel panel, currentClick, logOut;
    JPanel[] listPanel;
    String [] listOption;
    String [] listIcon;
    public MenuGUI(JPanel panel, JFrame frame){
        this.frame = frame;
        MenuBUS bus = new MenuBUS();
        listIcon = bus.getIcon();
        listOption = bus.getOption();
        init(panel);
    }

    public void init(JPanel panel){
        this.panel = panel;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        listPanel = new JPanel[listOption.length];
        menu();
    }
    public ImageIcon createIcon(String path, int size){
        ImageIcon iconUser = new ImageIcon("GUI/image/" + path);
        ImageIcon scaleIconUser = new ImageIcon(iconUser.getImage().getScaledInstance(size, size, Image.SCALE_SMOOTH));
        return scaleIconUser;
    }
    public void menu(){
        JPanel panelIcon = new JPanel(null);
        panelIcon.setPreferredSize(new Dimension(200, 200));
        panelIcon.setBackground(Color.decode("#004D40"));
        JLabel label = new JLabel(createIcon("man.png", 180), JLabel.CENTER);
        label.setBounds(10, 10, 180, 180);
        panelIcon.add(label);
        add(panelIcon);
        for(int i = 0;i < listOption.length;i++){
            listPanel[i] = new JPanel();
            add(makePanel(listPanel[i], listOption[i], createIcon(listIcon[i], 30)));
        }
        logOut = new JPanel();
        add(makePanel(logOut, "LogOut", createIcon("logout.png", 30)));
    }
    public JPanel makePanel(JPanel p, String value, ImageIcon icon){
        JLabel labelText = new JLabel(value);
        JLabel labelIcon = new JLabel(icon, JLabel.CENTER);
        p.setLayout(null);
        p.setPreferredSize(new Dimension(200, 50));
        labelText.setBounds(60, 0, 100, 50);
        labelText.setForeground(Color.white);
        labelText.setFont(new Font("Verdana", Font.PLAIN, 14));
        labelIcon.setBounds(10, 10, 30, 30);
        p.setName(value);
        p.setBackground(Color.decode("#004D40"));
        p.addMouseListener(this);
        p.add(labelIcon);
        p.add(labelText);
        return p;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        for (int i = 0; i < listPanel.length; i++) {
            listPanel[i].setBackground(Color.decode("#004D40"));
        }
        currentClick = (JPanel) e.getSource();
        currentClick.setBackground(Color.decode("#009688"));
        ContainerGUI contentOption = new ContainerGUI(panel.getWidth(), panel.getHeight());
        switch(e.getComponent().getName()){
            case "Kh??ch h??ng":
                panel.removeAll();
                panel.add(contentOption.khachHang());
                panel.repaint();
                panel.revalidate();
                break;

            case "Nh??n vi??n":
                panel.removeAll();
                panel.add(contentOption.nhanVien());
                panel.repaint();
                panel.revalidate();
                break;

            case "S???n ph???m":
                panel.removeAll();
                panel.add(contentOption.sanPham());
                panel.repaint();
                panel.revalidate();
                break;

            case "Nh?? cung c???p":
                panel.removeAll();
                panel.add(contentOption.nhaCungCap());
                panel.repaint();
                panel.revalidate();
                break;

            case "Account":
                panel.removeAll();
                panel.add(contentOption.account());
                panel.repaint();
                panel.revalidate();
                break;

                case "Phi???u nh???p":
                panel.removeAll();
                panel.add(contentOption.phieuNhap());
                panel.repaint();
                panel.revalidate();
                break;

            case "Order":
                panel.removeAll();
                panel.add(contentOption.banHang());
                panel.repaint();
                panel.revalidate();
                break;
            case "????n h??ng":
                panel.removeAll();
                panel.add(contentOption.donHang());
                panel.repaint();
                panel.revalidate();
                break;
            case "LogOut":
                frame.dispose();
                new LoginGUI();
        }

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        for(int i = 0;i < listPanel.length;i++){
            if(e.getSource() == listPanel[i]){
                listPanel[i].setBackground(Color.decode("#009688"));
                break;
            }
        }
        if(e.getSource() == logOut){
            logOut.setBackground(Color.decode("#009688"));
        }

    }

    @Override
    public void mouseExited(MouseEvent e) {
        JPanel hover = (JPanel) e.getSource();
        if(hover != currentClick){
            hover.setBackground(Color.decode("#004D40"));
        }
        if(e.getSource() == logOut){
            logOut.setBackground(Color.decode("#004D40"));
        }
    }
}
