package task1.DAO;

import task1.DTO.NhanVienDTO;

import java.util.ArrayList;
import java.util.HashMap;

public class NhanVienDAO extends DB{
    private ArrayList<HashMap<String, String>> arrMap;
    private ArrayList<HashMap<String, String>> arrSearch;
    public NhanVienDAO(){
        connect();
        arrMap = new ArrayList<HashMap<String, String>>(selectAll("nhanvien", "WHERE TrangThai = 1"));
        arrSearch = new ArrayList<>(arrMap);
        closeConnect();
    }
    public NhanVienDTO[] getDataDAO(){
        NhanVienDTO dto[] = new NhanVienDTO[arrSearch.size()];
        for(int i = 0;i < arrSearch.size();i++){
            dto[i] = new NhanVienDTO();
            dto[i].setManv(arrSearch.get(i).get("Manv"));
            dto[i].setTen(arrSearch.get(i).get("Ten"));
            dto[i].setDiaChi(arrSearch.get(i).get("DiaChi"));
            dto[i].setEmail(arrSearch.get(i).get("Email"));
            dto[i].setSDT(arrSearch.get(i).get("SDT"));
            dto[i].setUrlHinh(arrSearch.get(i).get("urlHinh"));
            dto[i].setNgaySinh(arrSearch.get(i).get("NgaySinh"));
            dto[i].setNgayVaoLam(arrSearch.get(i).get("NgayVaoLam"));
        }
        return dto;
    }
    public void addDAO(NhanVienDTO obj){
        connect();
        HashMap<String, String> row = new HashMap<>();
        row.put("Manv", obj.getManv());
        row.put("Ten", obj.getTen());
        row.put("DiaChi", obj.getDiaChi());
        row.put("SDT", obj.getSDT());
        row.put("Email", obj.getEmail());
        row.put("NgayVaoLam", obj.getNgayVaoLam());
        row.put("NgaySinh", obj.getNgaySinh());
        row.put("urlHinh", obj.getUrlHinh());
        row.put("TrangThai", "1");

        //1. add vao ArrayList
        arrMap.add(row);
        arrSearch.add(row);

        //2. add vao database
        if(insert("nhanvien", row)){
            System.out.println("Success");
        }
        closeConnect();
    }

    public void updateDAO(NhanVienDTO obj, int rowSelect){
        connect();
        HashMap<String, String> row = new HashMap<>();
        row.put("Ten", obj.getTen());
        row.put("DiaChi", obj.getDiaChi());
        row.put("SDT", obj.getSDT());
        row.put("Email", obj.getEmail());
        row.put("NgayVaoLam", obj.getNgayVaoLam());
        row.put("NgaySinh", obj.getNgaySinh());
        row.put("urlHinh", obj.getUrlHinh());

        //1. update to ArrayList
        arrMap.get(rowSelect).put("Ten", obj.getTen());
        arrMap.get(rowSelect).put("DiaChi", obj.getDiaChi());
        arrMap.get(rowSelect).put("SDT", obj.getSDT());
        arrMap.get(rowSelect).put("Email", obj.getEmail());
        arrMap.get(rowSelect).put("NgayVaoLam", obj.getNgayVaoLam());
        arrMap.get(rowSelect).put("NgaySinh", obj.getNgaySinh());
        arrMap.get(rowSelect).put("urlHinh", obj.getUrlHinh());

        arrSearch.get(rowSelect).put("Ten", obj.getTen());
        arrSearch.get(rowSelect).put("DiaChi", obj.getDiaChi());
        arrSearch.get(rowSelect).put("SDT", obj.getSDT());
        arrSearch.get(rowSelect).put("Email", obj.getEmail());
        arrSearch.get(rowSelect).put("NgayVaoLam", obj.getNgayVaoLam());
        arrSearch.get(rowSelect).put("NgaySinh", obj.getNgaySinh());
        arrSearch.get(rowSelect).put("urlHinh", obj.getUrlHinh());

        //2. update to database
        if(update("nhanvien", row, String.format("WHERE Manv = %s ",obj.getManv()))){
            System.out.println("Success");
        }
        closeConnect();
    }

    public void hiddenRow(String colCode, int status, int rowSelect){
        // Delete to ArrayList
        arrMap.remove(rowSelect);
        arrSearch.remove(rowSelect);

        // Hidden to DataBase
        HashMap<String, String> colAffect = new HashMap<>();
        colAffect.put("TrangThai", String.valueOf(status));
        connect();
        update("nhanvien", colAffect, "WHERE Manv = " + colCode);
        closeConnect();
    }

    public void search(String prefix){
        arrSearch = new ArrayList<>();
        for(int i = 0;i<arrMap.size();i++){
            if(arrMap.get(i).get("Manv").toLowerCase().startsWith(prefix) ||
               arrMap.get(i).get("Ten").toLowerCase().startsWith(prefix) ||
               arrMap.get(i).get("SDT").toLowerCase().startsWith(prefix)){
                arrSearch.add(arrMap.get(i));
            }
        }
    }

    public int getNextCodeDAO(){
        connect();
        int code = selectOnceRow("nhanvien", "ORDER BY Manv DESC LIMIT 1", "Manv");
        closeConnect();
        return code + 1;
    }
}
