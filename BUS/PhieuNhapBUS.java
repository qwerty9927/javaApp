package task1.BUS;

import task1.DAO.PhieuNhapDAO;
import task1.DTO.PhieuNhapDTO;
import task1.GUI.PhieuNhapGUI;

import javax.swing.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PhieuNhapBUS {
    private PhieuNhapDAO dao;
    private PhieuNhapDTO dtoArr[];
    private PhieuNhapDTO dto;
    public PhieuNhapBUS(){
        dao = new PhieuNhapDAO();
        dto = new PhieuNhapDTO();
    }

    public String[][] getAllValuesBUS(){
        dtoArr = dao.getDataDAO();
        String superString[][] = new String[dtoArr.length][];
        for(int i = 0;i < dtoArr.length;i++){
            superString[i] = dtoArr[i].getStringValues();
        }
        return superString;
    }

    public String[] getAllHeaderBUS(){
        return dto.getStringHeader();
    }

    public int getNextCodeBUS(){
        return dao.getNextCodeDAO();
    }

    public String[] getStringHeaderBUS(){
        return dto.getStringHeader();
    }

    public String[] getStringInputBUS(){
        return dto.getStringInput();
    }

    public int checkBUS(JTextField[] textFields){
        if(textFields[1].getText().isEmpty()){
            return 1;
        } else if(textFields[2].getText().isEmpty()){
            return 2;
        }
        return -1;
    }

    public void addBUS(JTextField[] textFields, int total){
        PhieuNhapDTO obj = new PhieuNhapDTO();
        obj.setMaPN(textFields[0].getText());
        obj.setMaNCC(textFields[1].getText());
        obj.setManv(textFields[2].getText());
        obj.setTongTien(String.valueOf(total));
        dao.addDAO(obj);
    }

    public void editBUS(JTextField[] textFields, int rowSelect, int total){
        PhieuNhapDTO obj = new PhieuNhapDTO();
        obj.setMaPN(textFields[0].getText());
        obj.setMaNCC(textFields[1].getText());
        obj.setManv(textFields[2].getText());
        obj.setTongTien(String.valueOf(PhieuNhapGUI.labelTotal.getText().split(" ")[1]));
        dao.updateDAO(obj, rowSelect);
    }

    public void deleteBUS(int status, int rowSelect){
        dao.hiddenRow(status, rowSelect);
    }

    public void searchBUS(String prefix){
        dao.search(prefix);
    }


    public static void main(String args[]){
//        NhanVienBUS a = new NhanVienBUS();
//        a.getAllValues();
    }

}
