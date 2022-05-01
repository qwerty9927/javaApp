package task1.DAO;

import task1.DTO.NhanVienDTO;

import java.util.ArrayList;
import java.util.HashMap;

public class NhanVienDAO extends DB{
    private ArrayList<HashMap<String, String>> arrMap;
    public NhanVienDAO(){
        arrMap = new ArrayList<HashMap<String, String>>(select("nhanvien", ""));
    }
    public NhanVienDTO[] getDataDAO(){
        NhanVienDTO dto[] = new NhanVienDTO[arrMap.size()];
        for(int i = 0;i < arrMap.size();i++){
            dto[i] = new NhanVienDTO();
            dto[i].setMakh(arrMap.get(i).get("Makh"));
            dto[i].setTen(arrMap.get(i).get("Ten"));
            dto[i].setDiaChi(arrMap.get(i).get("DiaChi"));
            dto[i].setEmail(arrMap.get(i).get("Email"));
            dto[i].setSDT(arrMap.get(i).get("SDT"));
            dto[i].setMATK(arrMap.get(i).get("MATK"));
        }
        closeConnect();
        return dto;
    }
    public void addDAO(){
        //1. add vao ArrayList
        //2. add vao database
    }
    public int getNextCodeDAO(){
        int code = Integer.parseInt(arrMap.get(arrMap.size() - 1).get("Makh"));
        return code + 1;
    }
}
