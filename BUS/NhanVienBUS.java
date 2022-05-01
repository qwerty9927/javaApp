package task1.BUS;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import task1.DAO.NhanVienDAO;
import task1.DTO.NhanVienDTO;

import javax.swing.*;

public class NhanVienBUS {
    private NhanVienDAO dao;
    private NhanVienDTO dtoArr[];
    private NhanVienDTO dto;
    public NhanVienBUS(){
        dao = new NhanVienDAO();
        dto = new NhanVienDTO();
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

    public int check(JTextField[] textFields){
        String name = "[a-zvxyỳọáầảấờễàạằệếýộậốũứĩõúữịỗìềểẩớặòùồợãụủíỹắẫựỉỏừỷởóéửỵẳẹèẽổẵẻỡơôưăêâđA-Z][a-zvxyỳọáầảấờễàạằệếýộậốũứĩõúữịỗìềểẩớặòùồợãụủíỹắẫựỉỏừỷởóéửỵẳẹèẽổẵẻỡơôưăêâđA-Z0-9-_ ]{4,24}";
        String address = "[a-zA-Z][a-zA-Z0-9-_/]{4,24}";
        String phoneNumber = "\\(?([0-9]{3})\\)?([ .-]?)([0-9]{3})\\2([0-9]{4})";
        String email = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        String code = "^[1-9][0-9]*$";
        String regexs[] = {name, address, phoneNumber, email, code};
        for(int i = 1;i < textFields.length;i++){
            Pattern pattern = Pattern.compile(regexs[i - 1], Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(textFields[i].getText());
            if(!matcher.find()){
                return i;
            }
        }
        return -1;
    }

    public void addNv(){

    }
    public static void main(String args[]){
        NhanVienBUS a = new NhanVienBUS();
//        a.getAllValues();
    }

}
