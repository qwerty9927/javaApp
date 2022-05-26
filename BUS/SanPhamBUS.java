package task1.BUS;

import task1.DAO.SanPhamDAO;
import task1.DTO.SanPhamDTO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import javax.swing.*;

public class SanPhamBUS {
    private SanPhamDAO dao;
    private SanPhamDTO dtoArr[];
    private SanPhamDTO dto;
    private ArrayList<SanPhamDTO> dssp = new ArrayList<>();
    public SanPhamBUS(){
        dao = new SanPhamDAO();
        dto = new SanPhamDTO();
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

    public int checkBUS(JTextField[] textFields, int cbSelected){
        String name = "[a-zvxyỳọáầảấờễàạằệếýộậốũứĩõúữịỗìềểẩớặòùồợãụủíỹắẫựỉỏừỷởóéửỵẳẹèẽổẵẻỡơôưăêâđA-Z][a-zvxyỳọáầảấờễàạằệếýộậốũứĩõúữịỗìềểẩớặòùồợãụủíỹắẫựỉỏừỷởóéửỵẳẹèẽổẵẻỡơôưăêâđA-Z0-9-_ ]{1,24}";
        String quantity = "^[1-9][0-9]*$";
        String regexs[] = {name, quantity, quantity};
        int i;
        for(i = 1;i < textFields.length;i++) {
            if(i == 2) continue;
            Pattern pattern = Pattern.compile(regexs[i - 1], Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(textFields[i].getText());
            if (!matcher.find()) {
                return i;
            }
        }
        if(cbSelected == 0){
            return i;
        }
        return -1;
    }

    public void addBUS(JTextField[] textFields, String nameImage, int cbSelected){
        SanPhamDTO obj = new SanPhamDTO();
        obj.setMaSP(textFields[0].getText());
        obj.setTenSP(textFields[1].getText());
        obj.setSoLuongSP(textFields[2].getText());
        obj.setGia(textFields[3].getText());
        obj.setUrlHinh(nameImage);
        obj.setLoai(String.valueOf(cbSelected));
        dao.addDAO(obj);
    }

    public void editBUS(JTextField[] textFields, String nameImage, int rowSelect, int cbSelected){
        SanPhamDTO obj = new SanPhamDTO();
        obj.setMaSP(textFields[0].getText());
        obj.setTenSP(textFields[1].getText());
        obj.setSoLuongSP(textFields[2].getText());
        obj.setGia(textFields[3].getText());
        obj.setUrlHinh(nameImage);
        obj.setLoai(String.valueOf(cbSelected));
        dao.updateDAO(obj, rowSelect);
    }

    public void deleteBUS(JTextField[] textField, int status, int rowSelect){
        dao.hiddenRow(textField[0].getText(), status, rowSelect);
    }

    public void searchBUS(String prefix){
        dao.search(prefix);
    }

    //Tan new update
    public void updateSLSP(String MaSP, int SoLuongSP){
        dao.updateSLSP(MaSP, SoLuongSP);
    }

    //Đạt
    public void ListLoaiSP(String Loai){
        dao = new SanPhamDAO();
        dssp = new ArrayList<>();
        dssp = dao.getDanhSachLoaiSanPham(Loai);
    }
    public ArrayList<SanPhamDTO> getList() {
        return dssp;
    }
    public boolean checkSL(String masp , int sl)
    {
        for(SanPhamDTO sp:dssp)
            if(sp.getMaSP().equals(masp))
                if(sl>Integer.parseInt(sp.getSoLuongSP())){
                    JOptionPane.showMessageDialog(null,"Hàng trong kho không đủ");
                    return false;
                }
        return true;
    }



    public static void main(String args[]){
//        NhanVienBUS a = new NhanVienBUS();
//        a.getAllValues();
    }

}
