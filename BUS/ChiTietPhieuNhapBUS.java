package task1.BUS;

import task1.DAO.ChiTietPhieuNhapDAO;
import task1.DTO.ChiTietPhieuNhapDTO;
import task1.GUI.PhieuNhapGUI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import javax.swing.*;

public class ChiTietPhieuNhapBUS {
    private ChiTietPhieuNhapDAO dao;
    private ChiTietPhieuNhapDTO dtoArr[];
    private ChiTietPhieuNhapDTO dto;
    public ChiTietPhieuNhapBUS(){
        dao = new ChiTietPhieuNhapDAO();
        dto = new ChiTietPhieuNhapDTO();
    }

    public int getTotalValueBUS(){
        return dao.getTotalValue();
    }

    public String[][] getAllValuesBUS(){
        dtoArr = dao.getDataDAO();
        System.out.println(dtoArr.length);
        String superString[][] = new String[dtoArr.length][];
        for(int i = 0;i < dtoArr.length;i++){
            superString[i] = dtoArr[i].getStringValues();
        }
        return superString;
    }

    public void filter(String param){
        dao.filter(param);
    }

    public String[] getAllHeaderBUS(){
        return dto.getStringHeader();
    }

    public String[] getStringHeaderBUS(){
        return dto.getStringHeader();
    }

    public int checkBUS(JTextField[] textFields){
        String quantity = "^[1-9][0-9]*$";
        if(textFields[1].getText().isEmpty()){
            return 1;
        }
        Pattern pattern = Pattern.compile(quantity, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(textFields[2].getText());
        if (!matcher.find()) {
            return 2;
        }
        return -1;
    }

    public void addBUS(JTextField[] textFields){
        ChiTietPhieuNhapDTO obj = new ChiTietPhieuNhapDTO();
        obj.setMaPN(textFields[0].getText());
        obj.setMaSP(textFields[1].getText());
//        obj.setTenSP(textFields[2].getText());
        obj.setSoLuongSP(textFields[2].getText());
//        obj.setGia(textFields[4].getText());
        dao.addDAO(obj);
    }

    public void editBUS(JTextField[] textFields, int rowSelect){
        ChiTietPhieuNhapDTO obj = new ChiTietPhieuNhapDTO();
        obj.setMaPN(textFields[0].getText());
        obj.setMaSP(textFields[1].getText());
//        obj.setTenSP(textFields[2].getText());
        obj.setSoLuongSP(textFields[2].getText());
//        obj.setGia(textFields[4].getText());
        dao.updateDAO(obj, rowSelect);
    }

    public void deleteBUS(JTextField[] textField, int status, int rowSelect){
        dao.hiddenRow(textField[0].getText(), status, rowSelect);
    }

    public void searchBUS(String prefix){
        dao.search(prefix);
    }

    public void addToDBBUS(){
        dao.addToDB();
    }

//    public void editToDBBUS(){
//        dao.updateToDB();
//    }

    public static void main(String args[]){
//        NhanVienBUS a = new NhanVienBUS();
//        a.getAllValues();
    }

}
