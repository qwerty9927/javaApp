package task1.BUS;

import java.util.ArrayList;
import java.util.HashMap;
import task1.DAO.NhanVienDAO;
import task1.DTO.NhanVienDTO;

public class NhanVienBUS {
    public String[][] getAllValues(){
        NhanVienDAO dao = new NhanVienDAO();
        NhanVienDTO dto[] = dao.getAll();
        String superString[][] = new String[dto.length][];
        for(int i = 0;i < dto.length;i++){
            superString[i] = dto[i].getStringValues();
        }
        System.out.println(superString);
        return superString;
    }

    public String[] getAllHeader(){
        NhanVienDTO dto = new NhanVienDTO();
        return dto.getStringHeader();
    }
    public void addNv(){

    }
    public static void main(String args[]){
        NhanVienBUS a = new NhanVienBUS();
        a.getAllValues();
    }
}
