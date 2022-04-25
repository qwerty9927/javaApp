package task1.GUI;

import javax.swing.*;
import java.awt.*;

public class ContainerGUI {
    JPanel rootGUI;
    private int width, height;
    public ContainerGUI(int width, int height){
        this.width = width;
        this.height = height;
    }
    public JPanel khachHang(){
        rootGUI = new KhachHangGUI();
        rootGUI.setBounds(0,0, width, height);
        return rootGUI;
    }

    public JPanel nhanVien(){
        rootGUI = new NhanVienGUI();
        rootGUI.setBounds(0,0, width, height);
        return rootGUI;
    }

}

