package task1.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MainGUI implements MouseListener {
    private JFrame frame = new JFrame();
    private JPanel p1, p2, p3, chucNang, close, max, min;
    private int width = Toolkit.getDefaultToolkit().getScreenSize().width;
    private int height = Toolkit.getDefaultToolkit().getScreenSize().height;

    public MainGUI(){
        initLayout();
    }
    public void initLayout(){
        frame.setLayout(new BorderLayout(5,5));
        frame.add(title(), "North");
        frame.add(container(), "Center");
        frame.add(menu(), "West");
        frame.setSize(new Dimension(1200, 800));
        frame.getContentPane().setBackground(Color.white);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setUndecorated(true);
        frame.setLocation((width - frame.getWidth())/2, (height -frame.getHeight())/2);
        frame.setVisible(true);
    }
    public JPanel title(){
        JLabel label = new JLabel("Quan ly cua hang");
        JPanel panelTitle = new JPanel(null);
        panelTitle.setPreferredSize(new Dimension(500, 50));
        panelTitle.setBackground(Color.decode("#00695C"));
        label.setForeground(Color.white);
        label.setFont(new Font("Verdana", Font.PLAIN, 18));
        label.setBounds(20, 0, 500, 50);
        panelTitle.add(label);
        p1 = new JPanel();
        p1.setLayout(new BorderLayout());
        p1.setOpaque(true);
        p1.setPreferredSize(new Dimension(1000, 50));
        p1.add(panelTitle, BorderLayout.WEST);
        p1.add(titlePanel(), BorderLayout.CENTER);
        return p1;
    }

    public JPanel menu(){
        p2 = new JPanel();
        p2.setBackground(Color.decode("#004D40"));
//        p2.add(new MenuGUI(p3, roleID));
        p2.add(new MenuGUI(p3));
        p2.setPreferredSize(new Dimension(200, 0));
        return p2;
    }

    public JPanel container(){
        p3 = new JPanel();
        p3.setLayout(null);
        p3.setBackground(Color.white);
        return p3;
    }


    //Chuc nang title
    public JPanel titlePanel(){
        chucNang = new JPanel();
        chucNang.setLayout(new FlowLayout(FlowLayout.RIGHT, 0,5));
        chucNang.setPreferredSize(new Dimension(500, 50));
        chucNang.add(fmin());
        chucNang.add(fmax());
        chucNang.add(fclose());
        close.addMouseListener(this);
        max.addMouseListener(this);
        min.addMouseListener(this);
        chucNang.setBackground(Color.decode("#00695C"));
        return chucNang;
    }


    public JPanel fclose(){
        close = new JPanel(null);
        JLabel label = new JLabel("X");
        label.setFont(new Font("Verdana", Font.PLAIN, 20));
        label.setForeground(Color.WHITE);
        label.setBounds(14, 10, 20, 20);
        close.setName("BtnClose");
        close.setBackground(Color.RED);
        close.add(label);
        close.setPreferredSize(new Dimension(40, 40));
        return close;
    }

    public JPanel fmax(){
        max = new JPanel(null);
        ImageIcon icon = new ImageIcon("GUI/image/maximize_button_32px.png");
        ImageIcon scaleIcon = new ImageIcon(icon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
        JLabel label = new JLabel(scaleIcon, JLabel.CENTER);
        label.setBounds(10, 10, 20, 20);
        max.setName("BtnMax");
        max.setBackground(Color.orange);
        max.add(label);
        max.setPreferredSize(new Dimension(40, 40));
        return max;
    }

    public JPanel fmin(){
        min = new JPanel();
        JLabel label = new JLabel("_");
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Verdana", Font.PLAIN, 20));
        label.setBounds(10, 10, 20, 20);
        min.setName("BtnMin");
        min.setBackground(Color.decode("#00695C"));
        min.add(label);
        min.setPreferredSize(new Dimension(40, 40));
        return min;
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getSource() == close){
            frame.dispose();
        }
//        else if(e.getSource() == max){
//            temp.setExtendedState(JFrame.MAXIMIZED_BOTH);
//        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if(e.getSource() == close){
            close.setBackground(Color.decode("#80CBC4"));
        } else if(e.getSource() == max){
            max.setBackground(Color.decode("#80CBC4"));
        } else {
            min.setBackground(Color.decode("#80CBC4"));
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if(e.getSource() == close){
            close.setBackground(Color.RED);
        } else if(e.getSource() == max){
            max.setBackground(Color.ORANGE);
        } else {
            min.setBackground(Color.decode("#00695C"));
        }
    }

    public static void main(String[] args) {
        MainGUI main = new MainGUI();
    }
}



