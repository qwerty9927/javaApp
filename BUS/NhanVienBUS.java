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

    public int checkBUS(JTextField[] textFields){
        String name = "[a-zvxyỳọáầảấờễàạằệếýộậốũứĩõúữịỗìềểẩớặòùồợãụủíỹắẫựỉỏừỷởóéửỵẳẹèẽổẵẻỡơôưăêâđA-Z][a-zvxyỳọáầảấờễàạằệếýộậốũứĩõúữịỗìềểẩớặòùồợãụủíỹắẫựỉỏừỷởóéửỵẳẹèẽổẵẻỡơôưăêâđA-Z0-9-_ ]{1,24}";
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

    public void addBus(JTextField[] textFields, String workingDate, String dateOfBirth, String nameImage){
        NhanVienDTO obj = new NhanVienDTO();
        obj.setManv(textFields[0].getText());
        obj.setTen(textFields[1].getText());
        obj.setDiaChi(textFields[2].getText());
        obj.setSDT(textFields[3].getText());
        obj.setEmail(textFields[4].getText());
        obj.setUrlHinh(nameImage);
        obj.setNgaySinh(dateOfBirth);
        obj.setNgayVaoLam(workingDate);
        dao.addDAO(obj);
    }

    public void editBus(JTextField[] textFields, String workingDate, String dateOfBirth, String nameImage, int rowSelect){
        NhanVienDTO obj = new NhanVienDTO();
        obj.setManv(textFields[0].getText());
        obj.setTen(textFields[1].getText());
        obj.setDiaChi(textFields[2].getText());
        obj.setSDT(textFields[3].getText());
        obj.setEmail(textFields[4].getText());
        obj.setUrlHinh(nameImage);
        obj.setNgaySinh(dateOfBirth);
        obj.setNgayVaoLam(workingDate);
        dao.updateDAO(obj, rowSelect);
    }

    public void deleteBus(JTextField[] textField, int status, int rowSelect){
        dao.hiddenRow(textField[0].getText(), status, rowSelect);
    }

    public void searchBus(String prefix){
        dao.search(prefix);
    }


    public static void main(String args[]){
        NhanVienBUS a = new NhanVienBUS();
//        a.getAllValues();
    }

}
