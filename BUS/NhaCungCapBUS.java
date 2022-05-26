package task1.BUS;

import task1.DAO.NhaCungCapDAO;
import task1.DTO.NhaCungCapDTO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import javax.swing.*;

public class NhaCungCapBUS {
    private NhaCungCapDAO dao;
    private NhaCungCapDTO dtoArr[];
    private NhaCungCapDTO dto;
    public NhaCungCapBUS(){
        dao = new NhaCungCapDAO();
        dto = new NhaCungCapDTO();
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

    public int checkBUS(JTextField[] textFields){
        String name = "[a-zvxyỳọáầảấờễàạằệếýộậốũứĩõúữịỗìềểẩớặòùồợãụủíỹắẫựỉỏừỷởóéửỵẳẹèẽổẵẻỡơôưăêâđA-Z][a-zvxyỳọáầảấờễàạằệếýộậốũứĩõúữịỗìềểẩớặòùồợãụủíỹắẫựỉỏừỷởóéửỵẳẹèẽổẵẻỡơôưăêâđA-Z0-9-_ ]{1,24}";
        String address = "[a-zA-Z][a-zA-Z0-9-_/]{4,24}";
        String phoneNumber = "\\(?([0-9]{3})\\)?([ .-]?)([0-9]{3})\\2([0-9]{4})";
        String regexs[] = {name, address, phoneNumber};
        int i;
        for(i = 1;i < textFields.length;i++) {
            Pattern pattern = Pattern.compile(regexs[i - 1], Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(textFields[i].getText());
            if (!matcher.find()) {
                return i;
            }
        }
        return -1;
    }

    public void addBUS(JTextField[] textFields){
        NhaCungCapDTO obj = new NhaCungCapDTO();
        obj.setMaNCC(textFields[0].getText());
        obj.setTen(textFields[1].getText());
        obj.setDiaChi(textFields[2].getText());
        obj.setSDT(textFields[3].getText());
        dao.addDAO(obj);
    }

    public void editBUS(JTextField[] textFields, int rowSelect){
        NhaCungCapDTO obj = new NhaCungCapDTO();
        obj.setMaNCC(textFields[0].getText());
        obj.setTen(textFields[1].getText());
        obj.setDiaChi(textFields[2].getText());
        obj.setSDT(textFields[3].getText());
        dao.updateDAO(obj, rowSelect);
    }

    public void deleteBUS(JTextField[] textField, int status, int rowSelect){
        dao.hiddenRow(textField[0].getText(), status, rowSelect);
    }

    public void searchBUS(String prefix){
        dao.search(prefix);
    }


    public static void main(String args[]){
//        NhanVienBUS a = new NhanVienBUS();
//        a.getAllValues();
    }

}
