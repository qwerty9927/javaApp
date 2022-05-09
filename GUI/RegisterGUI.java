/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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


public class RegisterGUI extends JPanel implements MouseListener, ActionListener {
    private JFrame f;
    private JPanel p, p1;
    private JButton btn1,btn2;
    private JLabel lbl1,lbl2,lbl3,lbl4,lbl5;
    private JTextField usernameTxt,emailTxt;
    private JPasswordField passwordTxt,confirmPwdTxt;
    private int width = Toolkit.getDefaultToolkit().getScreenSize().width;
    private int height = Toolkit.getDefaultToolkit().getScreenSize().height;

    public RegisterGUI() {
        init();
    }

    public static void main(String[] args) {
        RegisterGUI RG = new RegisterGUI();
    }
    public void init()
    {
        f = new JFrame();
        f.setLayout(new FlowLayout());
        p = new JPanel();
        p.setLayout(new BorderLayout());
        p.add(register());
        f.setSize(new Dimension(500, 370));
        f.add(p,BorderLayout.CENTER);
        f.add(p);
        f.setLocation((width - f.getWidth())/2, (height -f.getHeight())/2);
        f.setVisible(true);
    }
    public JPanel register()
    {
        p = new JPanel();
        p1 = new JPanel();
        lbl1 = new JLabel("Ten dang nhap");
        lbl1.setPreferredSize(new Dimension(100,35));
        usernameTxt = new JTextField();
        usernameTxt.setPreferredSize(new Dimension(340,35));
        lbl2 = new JLabel("Email");
        lbl2.setPreferredSize(new Dimension(100,35));
        emailTxt = new JTextField();
        emailTxt.setPreferredSize(new Dimension(340,35));
        lbl3 = new JLabel("Mat Khau");
        lbl3.setPreferredSize(new Dimension(100,35));
        passwordTxt = new JPasswordField();
        passwordTxt.setEchoChar('*');
        passwordTxt.setPreferredSize(new Dimension(340,35));
        lbl4 = new JLabel("Nhap lai mat khau");
        lbl4.setPreferredSize(new Dimension(100,35));
        confirmPwdTxt = new JPasswordField();
        confirmPwdTxt.setEchoChar('*');
        confirmPwdTxt.setPreferredSize(new Dimension(340,35));
        lbl5 = new JLabel("Xem mat khau");
        JCheckBox checkBox = new JCheckBox();
        checkBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (checkBox.isSelected()) {
                    lbl5.setText("An mat khau");
                    passwordTxt.setEchoChar((char)0);
                    confirmPwdTxt.setEchoChar((char)0);
                } else {
                    lbl5.setText("Xem mat khau");
                    passwordTxt.setEchoChar('*');
                    confirmPwdTxt.setEchoChar('*');
                }
            }
        });
        p.add(lbl1);
        p.add(usernameTxt);
        p.add(lbl2);
        p.add(emailTxt);
        p.add(lbl3);
        p.add(passwordTxt);
        p.add(lbl4);
        p.add(confirmPwdTxt);
        p1.setPreferredSize(new Dimension(450,70));
        p1.add(lbl5);
        p1.add(checkBox);
        p.add(p1);
        btn1 = new JButton("Xac nhan");
        btn1.setPreferredSize(new Dimension(120,35));
        btn1.setHorizontalAlignment(JButton.CENTER);
        btn1.setBackground(Color.GREEN);
        btn1.addActionListener(this);
        btn2 = new JButton("Dang nhap");
        btn2.setPreferredSize(new Dimension(120,35));
        btn2.setHorizontalAlignment(JButton.CENTER);
        btn2.setBackground(Color.GREEN);
        btn2.addActionListener(new java.awt.event.ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                loginBtnActionPerformed(e);
            }
        });
        p.add(btn1);
        p.add(btn2);
        p.setPreferredSize(new Dimension(450,550));
        return p;
    }
    private void loginBtnActionPerformed(java.awt.event.ActionEvent evt) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LoginGUI().setVisible(true);
            }
        });
        setVisible(false);
        f.dispose();
    }
    private void registerBtnActionPerformed(java.awt.event.ActionEvent evt) {
        String username = usernameTxt.getText();
        String email = emailTxt.getText();
        String password = passwordTxt.getText();
        String confirmPwd = confirmPwdTxt.getText();
        if (!password.equals(confirmPwd)) {
            JOptionPane.showMessageDialog(getRootPane(),"Vui long kiem tra lai mat khau, mat khau nhap va xac nhap mat khau khong giong nhau!");
            return;
        }
        password = UtilityDAO.getSecurityMD5(password);
        UsersDTO user = new UsersDTO(username,email,password,2,2);
        UserModifyDAO.insert(user);
        loginBtnActionPerformed(null);
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
        registerBtnActionPerformed(e);
    }
}
