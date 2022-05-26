package task1.GUI;

import javax.swing.*;

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

    public JPanel sanPham(){
        rootGUI = new SanPhamGUI();
        rootGUI.setBounds(0,0, width, height);
        return rootGUI;
    }

    public JPanel nhaCungCap(){
        rootGUI = new NhaCungCapGUI();
        rootGUI.setBounds(0,0, width, height);
        return rootGUI;
    }

    public JPanel account(){
        rootGUI = new AccountGUI();
        rootGUI.setBounds(0,0, width, height);
        return rootGUI;
    }

    public JPanel banHang(){
        rootGUI = new BanHangGUI();
        rootGUI.setBounds(0,0, width, height);
        return rootGUI;
    }

    public JPanel phieuNhap(){
        rootGUI = new PhieuNhapGUI();
        rootGUI.setBounds(0,0, width, height);
        return rootGUI;
    }
    public JPanel donHang(){
        rootGUI = new DonHangGUI();
        rootGUI.setBounds(0,0,width,height);
        return rootGUI;
    }

}

