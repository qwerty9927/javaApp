package task1.DAO;


import task1.DTO.SanPhamDTO;

import java.util.ArrayList;
import java.util.HashMap;

public class SanPhamDAO extends DB{
    private ArrayList<HashMap<String, String>> arrMap;
    private ArrayList<HashMap<String, String>> arrSearch;
    public SanPhamDAO(){
        connect();
        arrMap = new ArrayList<HashMap<String, String>>(selectAll("sanpham", "WHERE TrangThai = 1"));
        arrSearch = new ArrayList<>(arrMap);
        closeConnect();
    }
    public SanPhamDTO[] getDataDAO(){
        SanPhamDTO dto[] = new SanPhamDTO[arrSearch.size()];
        for(int i = 0;i < arrSearch.size();i++){
            dto[i] = new SanPhamDTO();
            dto[i].setMaSP(arrSearch.get(i).get("MaSP"));
            dto[i].setTenSP(arrSearch.get(i).get("TenSP"));
            dto[i].setSoLuongSP(arrSearch.get(i).get("SoLuongSP"));
            dto[i].setGia(arrSearch.get(i).get("Gia"));
            dto[i].setUrlHinh(arrSearch.get(i).get("urlHinh"));
            dto[i].setLoai(arrSearch.get(i).get("Loai"));
        }
        return dto;
    }
    public void addDAO(SanPhamDTO obj){
        connect();
        HashMap<String, String> row = new HashMap<>();
        row.put("MaSP", obj.getMaSP());
        row.put("TenSP", obj.getTenSP());
        row.put("SoLuongSP", obj.getSoLuongSP());
        row.put("Gia", obj.getGia());
        row.put("urlHinh", obj.getUrlHinh());
        row.put("Loai", obj.getLoai());
        row.put("TrangThai", "1");

        //1. add vao ArrayList
        arrMap.add(row);
        arrSearch.add(row);

        //2. add vao database
        if(insert("sanpham", row)){
            System.out.println("Success");
        }
        closeConnect();
    }

    public void updateDAO(SanPhamDTO obj, int rowSelect){
        connect();
        HashMap<String, String> row = new HashMap<>();
        row.put("TenSP", obj.getTenSP());
        row.put("SoLuongSP", obj.getSoLuongSP());
        row.put("Gia", obj.getGia());
        row.put("urlHinh", obj.getUrlHinh());
        row.put("Loai", obj.getLoai());

        //1. update to ArrayList
        arrMap.get(rowSelect).put("TenSP", obj.getTenSP());
        arrMap.get(rowSelect).put("SoLuongSP", obj.getSoLuongSP());
        arrMap.get(rowSelect).put("Gia", obj.getGia());
        arrMap.get(rowSelect).put("urlHinh", obj.getUrlHinh());
        arrMap.get(rowSelect).put("Loai", obj.getLoai());

        arrSearch.get(rowSelect).put("TenSP", obj.getTenSP());
        arrSearch.get(rowSelect).put("SoLuongSP", obj.getSoLuongSP());
        arrSearch.get(rowSelect).put("Gia", obj.getGia());
        arrSearch.get(rowSelect).put("urlHinh", obj.getUrlHinh());
        arrSearch.get(rowSelect).put("Loai", obj.getLoai());

        //2. update to database
        if(update("sanpham", row, String.format("WHERE MaSP = %s ",obj.getMaSP()))){
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
        update("sanpham", colAffect, "WHERE MaSP = " + colCode);
        closeConnect();
    }

    public void search(String prefix){
        arrSearch = new ArrayList<>();
        for(int i = 0;i<arrMap.size();i++){
            if(arrMap.get(i).get("MaSP").toLowerCase().startsWith(prefix) ||
            arrMap.get(i).get("TenSP").toLowerCase().startsWith(prefix)){
                arrSearch.add(arrMap.get(i));
            }
        }
    }

    public int getNextCodeDAO(){
        connect();
        int code = selectOnceRow("sanpham", "ORDER BY MaSP DESC LIMIT 1", "MaSP");
        closeConnect();
        return code + 1;
    }
}
