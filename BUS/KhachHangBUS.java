package task1.BUS;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import task1.DAO.KhachHangDAO;
import task1.DTO.KhachHangDTO;

import javax.swing.*;

public class KhachHangBUS {
    private KhachHangDAO dao;
    private KhachHangDTO dtoArr[];
    private KhachHangDTO dto;
    public KhachHangBUS(){
        dao = new KhachHangDAO();
        dto = new KhachHangDTO();
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
        String name = "[a-zvxyỳọáầảấờễàạằệếýộậốũứĩõúữịỗìềểẩớặòùồợãụủíỹắẫựỉỏừỷởóéửỵẳẹèẽổẵẻỡơôưăêâđA-Z][a-zvxyỳọáầảấờễàạằệếýộậốũứĩõúữịỗìềểẩớặòùồợãụủíỹắẫựỉỏừỷởóéửỵẳẹèẽổẵẻỡơôưăêâđA-Z0-9-_ ]{4,24}";
        String address = "[a-zA-Z][a-zA-Z0-9-_/]{4,24}";
        String phoneNumber = "\\(?([0-9]{3})\\)?([ .-]?)([0-9]{3})\\2([0-9]{4})";
        String email = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        String code = "^[1-9][0-9]*$";
        String regexs[] = {name, address, phoneNumber, email, code};
        for(int i = 1;i < textFields.length;i++) {
            Pattern pattern = Pattern.compile(regexs[i - 1], Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(textFields[i].getText());
            if (!matcher.find()) {
                return i;
            }
        }
        return -1;
    }

    public void addBUS(JTextField[] textFields, String nameImage){
        KhachHangDTO obj = new KhachHangDTO();
        obj.setMakh(textFields[0].getText());
        obj.setTen(textFields[1].getText());
        obj.setDiaChi(textFields[2].getText());
        obj.setSDT(textFields[3].getText());
        obj.setEmail(textFields[4].getText());
        obj.setUrlHinh(nameImage);
        dao.addDAO(obj);
    }

    public void editBUS(JTextField[] textFields, String nameImage, int rowSelect){
        KhachHangDTO obj = new KhachHangDTO();
        obj.setMakh(textFields[0].getText());
        obj.setTen(textFields[1].getText());
        obj.setDiaChi(textFields[2].getText());
        obj.setSDT(textFields[3].getText());
        obj.setEmail(textFields[4].getText());
        obj.setUrlHinh(nameImage);
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
