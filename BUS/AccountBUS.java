package task1.BUS;

import task1.DAO.AccountDAO;
import task1.DAO.UtilityDAO;
import task1.DTO.AccountDTO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import javax.swing.*;
import javax.swing.text.JTextComponent;

public class AccountBUS {
    private AccountDAO dao;
    private AccountDTO dtoArr[];
    private AccountDTO dto;
    private UtilityDAO udao;
    public AccountBUS(){
        dao = new AccountDAO();
        dto = new AccountDTO();
        udao = new UtilityDAO();
    }

    public String[][] getAllValuesBUS(){
        dtoArr = dao.getDataDAO();
        String superString[][] = new String[dtoArr.length][];
        for(int i = 0;i < dtoArr.length;i++){
            superString[i] = dtoArr[i].getStringValues();
        }
        return superString;
    }

    public String[] getStringHeaderAddBUS(){
        return dto.getStringHeaderAdd();
    }

    public String[] getLengthInputBUS(){
        return dto.getLengthInput();
    }

//    public int getNextCodeBUS(){
////        return dao.getNextCodeDAO();
//    }

    public String[] getStringHeaderBUS(){
        return dto.getStringHeader();
    }

    public int checkBUS(JTextComponent[] textFields){
        String username = textFields[0].getText();
        String password = textFields[1].getText();
        String RePassword = textFields[2].getText();
        String email = textFields[3].getText();
        String manv = textFields[4].getText();
        if(username.isEmpty()){
            return 0;
        } else if(password.length() < 8){
            return 1;
        } else if(!password.equalsIgnoreCase(RePassword)){
            return 2;
        } else {
            Pattern pattern = Pattern.compile("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(email);
            if (!matcher.find()) {
                return 3;
            }
        }
        if(manv.isEmpty()){
            return 4;
        }
        return -1;
    }

    public void addBUS(JTextComponent[] textFields, String nameImage){
        AccountDTO obj = new AccountDTO();
        obj.setUsername(textFields[0].getText());
        obj.setPassword(udao.getSecurityMD5(textFields[1].getText()));
        textFields[1].setText(obj.getPassword());
        obj.setEmail(textFields[3].getText());
        obj.setManv(textFields[4].getText());
        obj.setUrlHinh(nameImage);
        dao.addDAO(obj);
    }

    public void editBUS(JTextField[] textFields, String nameImage, int rowSelect){
        AccountDTO obj = new AccountDTO();
        obj.setUsername(textFields[0].getText());
        obj.setPassword(udao.getSecurityMD5(textFields[1].getText()));
        textFields[1].setText(obj.getPassword());
        obj.setEmail(textFields[3].getText());
        obj.setManv(textFields[4].getText());
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
