package task1.DAO;

import task1.BUS.DanhMucBUS;
import task1.DTO.DanhMucDTO;
import task1.DTO.QDMDTO;
import task1.DTO.SanPhamDTO;

import java.util.ArrayList;
import java.util.HashMap;

public class DanhMucDAO extends DB{
    private ArrayList<HashMap<String, String>> arrMap;
    private DanhMucDTO dto[];
    private DanhMucDTO dtoWithCondition[];
    public DanhMucDAO(){
        connect();
        arrMap = new ArrayList<HashMap<String, String>>(selectAll("danhmuc", ""));
        closeConnect();
        dto = new DanhMucDTO[arrMap.size()];
    }
    public DanhMucDTO[] getAllDataDAO(){
        for(int i = 0;i < arrMap.size();i++){
            dto[i] = new DanhMucDTO();
            dto[i].setCheckType(0);
            dto[i].setMadm(arrMap.get(i).get("MADM"));
            dto[i].setName(arrMap.get(i).get("TENDM"));
            dto[i].setImage(arrMap.get(i).get("urlHinh"));
        }
        return dto;
    }
    public DanhMucDTO[] getWithCondition(String condition){
        connect();
        ArrayList<HashMap<String, String>> arrWithCondition = new ArrayList<>(
                selectAll("role_dm as rm, danhmuc as dm", String.format("where MaID = %s and rm.MaDM = dm.MADM", condition))
        );
        closeConnect();
        dtoWithCondition = new DanhMucDTO[arrWithCondition.size()];
        for(int i = 0;i < arrWithCondition.size();i++){
            dtoWithCondition[i] = new DanhMucDTO();
            dtoWithCondition[i].setCheckType(1);
            dtoWithCondition[i].setMadm(arrWithCondition.get(i).get("MADM"));
            dtoWithCondition[i].setName(arrWithCondition.get(i).get("TENDM"));
            dtoWithCondition[i].setImage(arrWithCondition.get(i).get("urlHinh"));
        }
        return dtoWithCondition;
    }

    public void addDMDAO(QDMDTO[] qdm){
        connect();
        for(int i = 0;i < qdm.length;i++){
            if(qdm[i] != null){
                HashMap<String, String> hm = new HashMap<>();
                hm.put("MaID", qdm[i].getMaid());
                hm.put("MaDM", qdm[i].getMadm());
                insert("role_dm", hm);
            }
        }
        closeConnect();
    }
    public boolean deleteOldRole(String oldRoleID){
        connect();
        boolean status = delete("role_dm", String.format("Where MaID = %s", oldRoleID));
        closeConnect();
        return status;

    }
}
