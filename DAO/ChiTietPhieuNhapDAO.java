package task1.DAO;

import task1.DTO.ChiTietPhieuNhapDTO;
import task1.GUI.ChiTietPhieuNhapGUI;
import task1.GUI.PhieuNhapGUI;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class ChiTietPhieuNhapDAO extends DB{
    private ArrayList<HashMap<String, String>> arrMap;
    private ArrayList<HashMap<String, String>> arrSearch;
    private SanPhamDAO SPdao;
    public ChiTietPhieuNhapDAO(){
        SPdao = new SanPhamDAO();
        connect();
        arrMap = new ArrayList<HashMap<String, String>>(selectAll("chitietphieunhap", ""));
        arrSearch = new ArrayList<>(arrMap);
        closeConnect();
    }
    public void filter(String param){
        for(int i = 0;i < arrSearch.size();i++){
            if(arrSearch.get(i).get("MaPN").equalsIgnoreCase(param)){
                PhieuNhapGUI.ctpnOriginalDTO.add(arrSearch.get(i));
            }
        }
    }
    public ChiTietPhieuNhapDTO[] getDataDAO(){
        ChiTietPhieuNhapDTO dto[] = new ChiTietPhieuNhapDTO[PhieuNhapGUI.ctpnOriginalDTO.size()];
        for(int i = 0;i < PhieuNhapGUI.ctpnOriginalDTO.size();i++){
            dto[i] = new ChiTietPhieuNhapDTO();
            dto[i].setMaPN(PhieuNhapGUI.ctpnOriginalDTO.get(i).get("MaPN"));
            dto[i].setMaSP(PhieuNhapGUI.ctpnOriginalDTO.get(i).get("MaSP"));
//            dto[i].setTenSP(PhieuNhapGUI.ctpnOriginalDTO.get(i).get("TenSP"));
            dto[i].setSoLuongSP(PhieuNhapGUI.ctpnOriginalDTO.get(i).get("SoLuongSP"));
//            dto[i].setGia(PhieuNhapGUI.ctpnOriginalDTO.get(i).get("Gia"));
        }
        return dto;
    }

    public int getTotalValue(){
        int total = 0;
        for(int i = 0;i < PhieuNhapGUI.ctpnOriginalDTO.size();i++){
            total += Integer.parseInt(PhieuNhapGUI.ctpnOriginalDTO.get(i).get("SoLuongSP")) * Integer.parseInt(ChiTietPhieuNhapGUI.price);
        }
        return total;
    }
    public void addDAO(ChiTietPhieuNhapDTO obj){
        HashMap<String, String> row = new HashMap<>();
        row.put("MaPN", obj.getMaPN());
        row.put("MaSP", obj.getMaSP());
//        row.put("TenSP", obj.getTenSP());
        row.put("SoLuongSP", obj.getSoLuongSP());
//        row.put("Gia", obj.getGia());

        arrMap.add(row);
        arrSearch.add(row);
        PhieuNhapGUI.ctpnOriginalDTO.add(row);
    }

    public void updateDAO(ChiTietPhieuNhapDTO obj, int rowSelect){
        HashMap<String, String> row = new HashMap<>();
        row.put("MaSP", obj.getMaSP());
//        row.put("TenSP", obj.getTenSP());
        row.put("SoLuongSP", obj.getSoLuongSP());
//        row.put("Gia", obj.getGia());


        //1. update to ArrayList
        PhieuNhapGUI.ctpnOriginalDTO.get(rowSelect).put("MaSP", obj.getMaSP());
//        PhieuNhapGUI.ctpnOriginalDTO.get(rowSelect).put("TenSP", obj.getTenSP());
        PhieuNhapGUI.ctpnOriginalDTO.get(rowSelect).put("SoLuongSP", obj.getSoLuongSP());
//        PhieuNhapGUI.ctpnOriginalDTO.get(rowSelect).put("Gia", obj.getGia());
    }

    public void hiddenRow(String colCode, int status, int rowSelect){
        // Delete to ArrayList
        PhieuNhapGUI.ctpnOriginalDTO.remove(rowSelect);
    }

    public void addToDB(){
        connect();
        for(int i = 0;i<PhieuNhapGUI.ctpnOriginalDTO.size();i++){
            HashMap<String, String> row = PhieuNhapGUI.ctpnOriginalDTO.get(i);
            if(insert("chitietphieunhap", row)){
                System.out.println("Success");
            }
            SPdao.updateIncreaseSL(row);
//            if(selectAll("sanpham", String.format("where MaSP = %s", row.get("MaSP"))).size() == 0){
////                SPdao.addToDB(row);
////            } else {
//                SPdao.updateToDB(row);
//            }
        }
        closeConnect();
    }

//    public void updateToDB(){
//        connect();
//        if(delete("chitietphieunhap", String.format("Where MaPN = %s", PhieuNhapGUI.mapn))){
//            for(int i = 0;i<PhieuNhapGUI.ctpnOriginalDTO.size();i++){
//                HashMap<String, String> row = PhieuNhapGUI.ctpnOriginalDTO.get(i);
//                if(update("chitietphieunhap", row, String.format("WHERE MaPN = %s ",PhieuNhapGUI.mapn))){
//                    System.out.println("Success");
//                }
//            }
//        }
//    }


    public void search(String prefix){
        arrSearch = new ArrayList<>();
        for(int i = 0;i<arrMap.size();i++){
            if(arrMap.get(i).get("MaPN").toLowerCase().startsWith(prefix) ||
                    arrMap.get(i).get("TenSP").toLowerCase().startsWith(prefix)){
                arrSearch.add(arrMap.get(i));
            }
        }
    }
}
