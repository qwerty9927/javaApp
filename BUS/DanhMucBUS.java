package task1.BUS;

import task1.DAO.DanhMucDAO;
import task1.DTO.DanhMucDTO;
import task1.DTO.QDMDTO;

import java.util.Arrays;

public class DanhMucBUS {
    private DanhMucDTO dto[];
    private DanhMucDTO dtoWithCondition[];
    private DanhMucDAO dao;
    public DanhMucBUS(){
        dao = new DanhMucDAO();
        dto = dao.getAllDataDAO();
    }

    public int getCountCategory(){
        return getStringValue().length;
    }

    public String[] getStringValue(){
        String[] string = new String[dto.length];
        for(int i = 0;i < dto.length;i++){
            string[i] = dto[i].getName();
        }
        return string;
    }

    public String[] getStringNameImage(){
        String[] string = new String[dto.length];
        for(int i = 0;i < dto.length;i++){
            string[i] = dto[i].getImage();
        }
        return string;
    }

    public int[] getCategory(String col){
        int j = 0;
        int[] visited = new int[getCountCategory()];
        dtoWithCondition = dao.getWithCondition(col);
        for(int i = 1;i <= visited.length && j != dtoWithCondition.length;i++){
            if(dtoWithCondition[j].getMadm().equalsIgnoreCase(String.valueOf(i))){
                visited[i - 1] = 1;
                j++;
            }
        }
        return visited;
    }

    public void addDM(int[] visited, String roleID){
        QDMDTO qdm[] = new QDMDTO[visited.length];
        for(int i = 0;i < visited.length;i++){
            if(visited[i] == 1){
                qdm[i] = new QDMDTO();
                qdm[i].setMadm(String.valueOf(i + 1));
                qdm[i].setMaid(roleID);
            }
        }
        dao.addDMDAO(qdm);
    }

    public void editDM(int[] visited, String roleID){
        QDMDTO qdm[] = new QDMDTO[visited.length];
        for(int i = 0;i < visited.length;i++){
            if(visited[i] == 1){
                qdm[i] = new QDMDTO();
                qdm[i].setMadm(String.valueOf(i + 1));
                qdm[i].setMaid(roleID);
            }
        }
        System.out.println("door");
        if(dao.deleteOldRole(roleID)){
            System.out.println("entry");
            dao.addDMDAO(qdm);
        }
    }
}
