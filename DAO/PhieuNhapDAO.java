package task1.DAO;

import task1.DTO.PhieuNhapDTO;
import task1.GUI.PhieuNhapGUI;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;

public class PhieuNhapDAO extends DB{
    private ArrayList<HashMap<String, String>> arrMap;
    private ArrayList<HashMap<String, String>> arrSearch;
    private SanPhamDAO SPbus;
    public PhieuNhapDAO(){
        SPbus = new SanPhamDAO();
        connect();
        arrMap = new ArrayList<HashMap<String, String>>(selectAll("phieunhap", "WHERE TrangThai = 1"));
        arrSearch = new ArrayList<>(arrMap);
        closeConnect();
    }
    public PhieuNhapDTO[] getDataDAO(){
        PhieuNhapDTO dto[] = new PhieuNhapDTO[arrSearch.size()];
        for(int i = 0;i < arrSearch.size();i++){
            dto[i] = new PhieuNhapDTO();
            dto[i].setMaPN(arrSearch.get(i).get("MaPN"));
            dto[i].setMaNCC(arrSearch.get(i).get("MaNCC"));
            dto[i].setManv(arrSearch.get(i).get("Manv"));
            dto[i].setTongTien(arrSearch.get(i).get("TongTien"));
        }
        return dto;
    }
    public void addDAO(PhieuNhapDTO obj){
        connect();
        HashMap<String, String> row = new HashMap<>();
        row.put("MaPN", obj.getMaPN());
        row.put("MaNCC", obj.getMaNCC());
        row.put("Manv", obj.getManv());
        row.put("TongTien", obj.getTongTien());
        row.put("TrangThai", "1");

        //1. add vao ArrayList
        arrMap.add(row);
        arrSearch.add(row);

        //2. add vao database
        if(insert("phieuNhap", row)){
            System.out.println("Success");
        }
        closeConnect();
    }

    public void updateDAO(PhieuNhapDTO obj, int rowSelect){
        connect();
        HashMap<String, String> row = new HashMap<>();
        row.put("MaNCC", obj.getMaNCC());
        row.put("Manv", obj.getManv());
        row.put("TongTien", obj.getTongTien());


        //1. update to ArrayList
        arrMap.get(rowSelect).put("MaNCC", obj.getMaNCC());
        arrMap.get(rowSelect).put("Manv", obj.getManv());
        arrMap.get(rowSelect).put("TongTien", obj.getTongTien());


        arrSearch.get(rowSelect).put("MaNCC", obj.getMaNCC());
        arrSearch.get(rowSelect).put("Manv", obj.getManv());
        arrSearch.get(rowSelect).put("TongTien", obj.getTongTien());

        //2. update to database
        if(update("phieuNhap", row, String.format("WHERE MaPN = %s ",obj.getMaPN()))){
            System.out.println("Success");
        }
        closeConnect();
    }

    public void hiddenRow(int status, int rowSelect){
            // Delete to ArrayList
            arrMap.remove(rowSelect);
            arrSearch.remove(rowSelect);

            // Hidden to DataBase
            HashMap<String, String> colAffect = new HashMap<>();
            colAffect.put("TrangThai", String.valueOf(status));
            connect();
            update("phieuNhap", colAffect, "WHERE MaPN = " + PhieuNhapGUI.mapn);
            closeConnect();
            SPbus.updateDecreaseSL();
    }

    public void search(String prefix){
        arrSearch = new ArrayList<>();
        for(int i = 0;i<arrMap.size();i++){
            if(arrMap.get(i).get("MaPN").toLowerCase().startsWith(prefix) ||
                    arrMap.get(i).get("Manv").toLowerCase().startsWith(prefix)){
                arrSearch.add(arrMap.get(i));
            }
        }
    }

    public int getNextCodeDAO(){
        connect();
        int code = selectOnceRow("phieuNhap", "ORDER BY MaPN DESC LIMIT 1", "MaPN");
        closeConnect();
        return code + 1;
    }
}
