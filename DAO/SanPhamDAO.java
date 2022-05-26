package task1.DAO;


import task1.DTO.SanPhamDTO;
import task1.GUI.PhieuNhapGUI;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SanPhamDAO extends DB{
    private ArrayList<HashMap<String, String>> arrMap;
    private ArrayList<HashMap<String, String>> arrSearch;
    private ArrayList<SanPhamDTO> dssp = new ArrayList<>();
    private ResultSet rs = null;
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

    public void updateIncreaseSL(HashMap<String, String> param){
        connect();
        int sl = selectOnceRow("sanpham", String.format("WHERE MaSP = %s",param.get("MaSP")), "SoLuongSP");
        HashMap<String, String> row = new HashMap<>();
        row.put("MaSP", param.get("MaSP"));
        row.put("SoLuongSP", String.valueOf(sl + Integer.parseInt(param.get("SoLuongSP"))));
        if(update("sanpham", row, String.format("WHERE MaSP = %s",param.get("MaSP")))){
            System.out.println("Success");
        }
        closeConnect();
    }

    public void updateDecreaseSL() {
        connect();
        ArrayList<HashMap<String, String>> arrSP = selectAll("chitietphieunhap", String.format("where MaPN = %s", PhieuNhapGUI.mapn));
        for (int i = 0; i < arrSP.size(); i++) {
            int slOld = selectOnceRow("sanpham", String.format("WHERE MaSP = %s", arrSP.get(i).get("MaSP")), "SoLuongSP");
            int slNew = slOld - Integer.parseInt(arrSP.get(i).get("SoLuongSP"));
            HashMap<String, String> row = new HashMap<>();
            row.put("SoLuongSP", String.valueOf(slNew));
            if (update("sanpham", row, String.format("WHERE MaSP = %s", arrSP.get(i).get("MaSP")))) {
                System.out.println("Success");
            }
        }
        closeConnect();
    }

    //Tan
    public void updateSLSP(String Masp, int SoLuongSP){
        connect();
        int sl = selectOnceRow("sanpham", String.format("WHERE MaSP = %s",Masp), "SoLuongSP");
        System.out.println(sl);
        System.out.println(Masp);
        closeConnect();
        try{
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/java_ch", "root", "");
            String sql = "UPDATE sanpham SET SoLuongSP = " + String.valueOf(sl - SoLuongSP) + " WHERE MaSP = " + Masp;
            Statement st = con.createStatement();
            System.out.println(sql);
            st.executeUpdate(sql);
            con.close();
        } catch(Exception e){
            System.out.println(e);
            System.out.println("Error");
        }
    }

    //Đạt
    //Lay danh san pham theo LoaiSP trong database
    public ArrayList<SanPhamDTO> getDanhSachLoaiSanPham(String Loai) {
        DB conn = new DB();
        conn.connect();
        try {
            PreparedStatement stm = conn.getConnection().prepareStatement("select * from sanpham where Loai = '"+Loai+"'");
            rs = conn.executeQuery(stm);
            if (rs != null) {
                while (rs.next()) {
                    SanPhamDTO SP = new SanPhamDTO();
                    SP.setMaSP(rs.getString("MaSP"));
                    SP.setTenSP(rs.getString("TenSP"));
                    SP.setGia(rs.getString("Gia"));
                    SP.setUrlHinh(rs.getString("urlHinh"));
                    SP.setSoLuongSP(rs.getString("SoLuongSP"));
                    dssp.add(SP);
                }
            }
        } catch (Exception e) {
            Logger.getLogger(SanPhamDAO.class.getName()).log(Level.SEVERE, null, e);
        }finally{
            conn.closeConnect();
        }
        return dssp;
    }
}
