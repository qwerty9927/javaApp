package task1.DAO;

import task1.DTO.NhaCungCapDTO;

import java.util.ArrayList;
import java.util.HashMap;

public class NhaCungCapDAO extends DB{
    private ArrayList<HashMap<String, String>> arrMap;
    private ArrayList<HashMap<String, String>> arrSearch;
    public NhaCungCapDAO(){
        connect();
        arrMap = new ArrayList<HashMap<String, String>>(selectAll("nhacungcap", "WHERE TrangThai = 1"));
        arrSearch = new ArrayList<>(arrMap);
        closeConnect();
    }
    public NhaCungCapDTO[] getDataDAO(){
        NhaCungCapDTO dto[] = new NhaCungCapDTO[arrSearch.size()];
        for(int i = 0;i < arrSearch.size();i++){
            dto[i] = new NhaCungCapDTO();
            dto[i].setMaNCC(arrSearch.get(i).get("MaNCC"));
            dto[i].setTen(arrSearch.get(i).get("Ten"));
            dto[i].setDiaChi(arrSearch.get(i).get("DiaChi"));
            dto[i].setSDT(arrSearch.get(i).get("SDT"));
        }
        return dto;
    }
    public void addDAO(NhaCungCapDTO obj){
        connect();
        HashMap<String, String> row = new HashMap<>();
        row.put("MaNCC", obj.getMaNCC());
        row.put("Ten", obj.getTen());
        row.put("DiaChi", obj.getDiaChi());
        row.put("SDT", obj.getSDT());
        row.put("TrangThai", "1");

        //1. add vao ArrayList
        arrMap.add(row);
        arrSearch.add(row);

        //2. add vao database
        if(insert("nhacungcap", row)){
            System.out.println("Success");
        }
        closeConnect();
    }

    public void updateDAO(NhaCungCapDTO obj, int rowSelect){
        connect();
        HashMap<String, String> row = new HashMap<>();
        row.put("Ten", obj.getTen());
        row.put("DiaChi", obj.getDiaChi());
        row.put("SDT", obj.getSDT());


        //1. update to ArrayList
        arrMap.get(rowSelect).put("Ten", obj.getTen());
        arrMap.get(rowSelect).put("DiaChi", obj.getDiaChi());
        arrMap.get(rowSelect).put("SDT", obj.getSDT());


        arrSearch.get(rowSelect).put("Ten", obj.getTen());
        arrSearch.get(rowSelect).put("DiaChi", obj.getDiaChi());
        arrSearch.get(rowSelect).put("SDT", obj.getSDT());

        //2. update to database
        if(update("nhacungcap", row, String.format("WHERE MaNCC = %s ",obj.getMaNCC()))){
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
        update("nhacungcap", colAffect, "WHERE MaNCC = " + colCode);
        closeConnect();
    }

    public void search(String prefix){
        arrSearch = new ArrayList<>();
        for(int i = 0;i<arrMap.size();i++){
            if(arrMap.get(i).get("MaNCC").toLowerCase().startsWith(prefix) ||
                    arrMap.get(i).get("Ten").toLowerCase().startsWith(prefix) ||
                    arrMap.get(i).get("SDT").toLowerCase().startsWith(prefix)){
                arrSearch.add(arrMap.get(i));
            }
        }
    }

    public int getNextCodeDAO(){
        connect();
        int code = selectOnceRow("nhacungcap", "ORDER BY MaNCC DESC LIMIT 1", "MaNCC");
        closeConnect();
        return code + 1;
    }
}
