package task1.GUI;

import task1.DAO.UserModifyDAO;
import task1.DAO.UtilityDAO;
import task1.DTO.UsersDTO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class LoginGUI extends JPanel implements MouseListener, ActionListener {
    private JFrame f;
    private JPanel panel, p1;
    private JButton btnlogin,btn2;
    private JTextField usernametxt;
    private JPasswordField passwordtxt;
    private JLabel lbl1, lbl2, lbl3;
    private int width = Toolkit.getDefaultToolkit().getScreenSize().width;
    private int height = Toolkit.getDefaultToolkit().getScreenSize().height;

    public static int roleID;
    public LoginGUI() {
        init();
    }

    public static void main(String[] args) {
        LoginGUI lg= new LoginGUI();
    }
    public void init() {
        f = new JFrame();
        f.setLayout(new FlowLayout());
        panel = new JPanel();
        panel.setLayout(new BorderLayout());
        f.setSize(new Dimension(500, 300));
        f.add(panel,BorderLayout.CENTER);
        panel.add(Login());
        f.add(panel);
        f.setLocation((width - f.getWidth())/2, (height -f.getHeight())/2);
        f.setVisible(true);
    }
    public JPanel Login() {
        panel = new JPanel();
        p1 = new JPanel();
        lbl1 = new JLabel("Ten dang nhap");
        lbl1.setPreferredSize(new Dimension(100,35));
        usernametxt = new JTextField();
        usernametxt.setPreferredSize(new Dimension(340,35));
        lbl2 = new JLabel("Mat khau");
        lbl2.setPreferredSize(new Dimension(100,35));
        passwordtxt = new JPasswordField();
        passwordtxt.setEchoChar('*');
        passwordtxt.setPreferredSize(new Dimension(340,35));

        lbl3 = new JLabel("Xem mat khau");
        JCheckBox checkBox = new JCheckBox();
        checkBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (checkBox.isSelected()) {
                    lbl3.setText("An mat khau");
                    passwordtxt.setEchoChar((char)0);
                } else {
                    lbl3.setText("Xem mat khau");
                    passwordtxt.setEchoChar('*');
                }
            }
        });
        panel.add(lbl1);
        panel.add(usernametxt);
        panel.add(lbl2);
        panel.add(passwordtxt);
        p1.setPreferredSize(new Dimension(450,70));
        p1.add(lbl3);
        p1.add(checkBox);
        panel.add(p1);
        btnlogin = new JButton("Dang nhap");
        btnlogin.setLayout(new FlowLayout());
        btnlogin.setBackground(Color.GREEN);
        btnlogin.setPreferredSize(new Dimension(100,35));
        btnlogin.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                logigBTNActionPerformed(e);
            }
        });
        btn2 = new JButton("Dang ky");
        btn2.setLayout(new FlowLayout());
        btn2.setBackground(Color.GREEN);
        btn2.setPreferredSize(new Dimension(100,35));
      btn2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               registerBtnActionPerformed(e);
            }
        });
        panel.add(btnlogin);
        panel.add(btn2);
        panel.setPreferredSize(new Dimension(450,350));
        return panel;
    }
    private void registerBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_registerBtnActionPerformed
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new RegisterGUI().setVisible(true);
            }
        });
        setVisible(false);
        f.dispose();
    }
    private void logigBTNActionPerformed(java.awt.event.ActionEvent e) {
        String username = usernametxt.getText();
        String password = passwordtxt.getText();
        password = UtilityDAO.getSecurityMD5(password);

        UsersDTO user = UserModifyDAO.login(username, password);
        if (user == null) {
            JOptionPane.showMessageDialog(getRootPane(),"Tai khoan ton tai, vui long kiem tra ten nguoi dung va mat khau!");
        } else if(user.getActive() == 0) {
            JOptionPane.showMessageDialog(getRootPane(),"Tai khoan cua ban khong hoat dong, vui long lien he voi quan tri vien");
        } else {
//            Open MenuHomeGUI
//            Admin -> MenuAdmin
//            if (user.getRoleID() == 1) {
////                java.awt.EventQueue.invokeLater(new Runnable() {
////                    @Override
////                    public void run() {
////                        MainGUI m = new MainGUI();
////                    }
////                });
////                System.out.println("1");
//            } else {
                java.awt.EventQueue.invokeLater(new Runnable() {
                    @Override
                    public void run() {

                        roleID = user.getRoleID();
                        MainGUI m = new MainGUI();
                    }
                });
//            }
//            User -> MenuGUI
            setVisible(false);
            f.dispose();
        }
    }


    @Override
    public void mouseClicked(MouseEvent e) {

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

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    private class CentralPhoneTextBox {
    }
}
