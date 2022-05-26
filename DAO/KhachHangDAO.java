package task1.DAO;

import task1.DTO.KhachHangDTO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class KhachHangDAO extends DB{
    private ArrayList<HashMap<String, String>> arrMap;
    private ArrayList<HashMap<String, String>> arrSearch;
    private ResultSet rs = null;
    private ArrayList<KhachHangDTO> dskh = new ArrayList<>();
    public KhachHangDAO(){
        connect();
        arrMap = new ArrayList<HashMap<String, String>>(selectAll("khachhang", "WHERE TrangThai = 1"));
        arrSearch = new ArrayList<>(arrMap);
        closeConnect();
    }
    public KhachHangDTO[] getDataDAO(){
        KhachHangDTO dto[] = new KhachHangDTO[arrSearch.size()];
        for(int i = 0;i < arrSearch.size();i++){
            dto[i] = new KhachHangDTO();
            dto[i].setMakh(arrSearch.get(i).get("Makh"));
            dto[i].setTen(arrSearch.get(i).get("Ten"));
            dto[i].setDiaChi(arrSearch.get(i).get("DiaChi"));
            dto[i].setEmail(arrSearch.get(i).get("Email"));
            dto[i].setSDT(arrSearch.get(i).get("SDT"));
            dto[i].setUrlHinh(arrSearch.get(i).get("urlHinh"));
        }
        return dto;
    }
    public void addDAO(KhachHangDTO obj){
        connect();
        HashMap<String, String> row = new HashMap<>();
        row.put("Makh", obj.getMakh());
        row.put("Ten", obj.getTen());
        row.put("DiaChi", obj.getDiaChi());
        row.put("SDT", obj.getSDT());
        row.put("Email", obj.getEmail());
        row.put("urlHinh", obj.getUrlHinh());
        row.put("TrangThai", "1");

        //1. add vao ArrayList
        arrMap.add(row);
        arrSearch.add(row);

        //2. add vao database
        if(insert("khachhang", row)){
            System.out.println("Success");
        }
        closeConnect();
    }

    public void updateDAO(KhachHangDTO obj, int rowSelect){
        connect();
        HashMap<String, String> row = new HashMap<>();
        row.put("Ten", obj.getTen());
        row.put("DiaChi", obj.getDiaChi());
        row.put("SDT", obj.getSDT());
        row.put("Email", obj.getEmail());
        row.put("urlHinh", obj.getUrlHinh());

        //1. update to ArrayList
        arrMap.get(rowSelect).put("Ten", obj.getTen());
        arrMap.get(rowSelect).put("DiaChi", obj.getDiaChi());
        arrMap.get(rowSelect).put("SDT", obj.getSDT());
        arrMap.get(rowSelect).put("Email", obj.getEmail());

        arrMap.get(rowSelect).put("urlHinh", obj.getUrlHinh());

        arrSearch.get(rowSelect).put("Ten", obj.getTen());
        arrSearch.get(rowSelect).put("DiaChi", obj.getDiaChi());
        arrSearch.get(rowSelect).put("SDT", obj.getSDT());
        arrSearch.get(rowSelect).put("Email", obj.getEmail());
        arrSearch.get(rowSelect).put("urlHinh", obj.getUrlHinh());

        //2. update to database
        if(update("khachhang", row, String.format("WHERE Makh = %s ",obj.getMakh()))){
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
        update("khachhang", colAffect, "WHERE Makh = " + colCode);
        closeConnect();
    }

    public void search(String prefix){
        arrSearch = new ArrayList<>();
        for(int i = 0;i<arrMap.size();i++){
            if(arrMap.get(i).get("Makh").toLowerCase().startsWith(prefix) ||
                    arrMap.get(i).get("Ten").toLowerCase().startsWith(prefix) ||
                    arrMap.get(i).get("SDT").toLowerCase().startsWith(prefix)){
                arrSearch.add(arrMap.get(i));
            }
        }
    }

    public int getNextCodeDAO(){
        connect();
        int code = selectOnceRow("khachhang", "ORDER BY Makh DESC LIMIT 1", "Makh");
        closeConnect();
        return code + 1;
    }
    //Đạt
    public ArrayList<KhachHangDTO> getKhachHangTheoMa(String makh){
        DB conn = new DB();
        conn.connect();
        try{
            String sql = "select * from khachhang where makh =?";
            PreparedStatement stm = conn.getConnection().prepareStatement(sql);
            stm.setString(1,makh);
            rs = conn.executeQuery(stm);
            if(rs!=null)
                while(rs.next()){
                    KhachHangDTO kh = new KhachHangDTO();
                    kh.setMakh(rs.getString("Makh"));
                    kh.setTen(rs.getString("Ten"));
                    kh.setDiaChi(rs.getString("DiaChi"));
                    kh.setSDT(rs.getString("SDT"));
                    dskh.add(kh);
                }
        }catch (Exception e){
            Logger.getLogger(KhachHangDAO.class.getName()).log(Level.SEVERE,null,e);
        }finally {
            conn.closeConnect();
        }
        return dskh;
    }
}
