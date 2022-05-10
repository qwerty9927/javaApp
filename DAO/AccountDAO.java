package task1.DAO;

import task1.DTO.AccountDTO;

import java.util.ArrayList;
import java.util.HashMap;

public class AccountDAO extends DB{
    private ArrayList<HashMap<String, String>> arrMap;
    private ArrayList<HashMap<String, String>> arrSearch;
    public AccountDAO(){
        connect();
        arrMap = new ArrayList<HashMap<String, String>>(selectAll("users", "WHERE active = 2"));
        arrSearch = new ArrayList<>(arrMap);
        closeConnect();
    }

    public AccountDTO[] getDataDAO(){
        AccountDTO dto[] = new AccountDTO[arrSearch.size()];
        for(int i = 0;i < arrSearch.size();i++){
            dto[i] = new AccountDTO();
            dto[i].setUsername(arrSearch.get(i).get("username"));
            dto[i].setPassword(arrSearch.get(i).get("password"));
            dto[i].setEmail(arrSearch.get(i).get("email"));
            dto[i].setManv(arrSearch.get(i).get("Manv"));
            dto[i].setUrlHinh(arrSearch.get(i).get("urlHinh"));
            dto[i].setRoleID(arrSearch.get(i).get("role_id"));
        }
        return dto;
    }

    public void addDAO(AccountDTO obj){
        connect();
        HashMap<String, String> row = new HashMap<>();
        row.put("username", obj.getUsername());
        row.put("password", obj.getPassword());
        row.put("email", obj.getEmail());
        row.put("Manv", obj.getManv());
        row.put("urlHinh", obj.getUrlHinh());
        row.put("role_id", obj.getRoleID());
        row.put("active", "2");

        //1. add vao ArrayList
        arrMap.add(row);
        arrSearch.add(row);

        //2. add vao database
        if(insert("users", row)){
            System.out.println("Success");
        }
        closeConnect();
    }

    public void updateDAO(AccountDTO obj, int rowSelect){
        connect();
        HashMap<String, String> row = new HashMap<>();
        row.put("username", obj.getUsername());
        row.put("password", obj.getPassword());
        row.put("email", obj.getEmail());
        row.put("Manv", obj.getManv());
        row.put("urlHinh", obj.getUrlHinh());

        //1. update to ArrayList
        arrMap.get(rowSelect).put("username", obj.getUsername());
        arrMap.get(rowSelect).put("password", obj.getPassword());
        arrMap.get(rowSelect).put("email", obj.getEmail());
        arrMap.get(rowSelect).put("Manv", obj.getManv());
        arrMap.get(rowSelect).put("urlHinh", obj.getUrlHinh());


        arrMap.get(rowSelect).put("username", obj.getUsername());
        arrMap.get(rowSelect).put("password", obj.getPassword());
        arrMap.get(rowSelect).put("email", obj.getEmail());
        arrMap.get(rowSelect).put("Manv", obj.getManv());
        arrMap.get(rowSelect).put("urlHinh", obj.getUrlHinh());


        //2. update to database
        if(update("users", row, String.format("WHERE username = '%s' ",obj.getUsername()))){
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
        colAffect.put("active", String.valueOf(status));
        connect();
        update("users", colAffect, "WHERE username = " + "'" + colCode + "'");
        closeConnect();
    }

    public void search(String prefix){
        arrSearch = new ArrayList<>();
        for(int i = 0;i<arrMap.size();i++){
            if(arrMap.get(i).get("username").toLowerCase().startsWith(prefix) ||
                    arrMap.get(i).get("email").toLowerCase().startsWith(prefix)){
                arrSearch.add(arrMap.get(i));
            }
        }
    }

    public boolean checkUsernameDAO(String username){
        connect();
        ArrayList<HashMap<String, String>> arr = selectAll("users", String.format("Where username = '%s'", username));
//        System.out.println(username);
//        System.out.println(arr.size());
        if(arr.size() > 0){
            closeConnect();
            return true;
        } else {
            closeConnect();
            return false;
        }
    }

    public int getNextRoleIDDAO(){
        connect();
        int code = selectOnceRow("users", "ORDER BY role_id DESC LIMIT 1", "role_id");
        closeConnect();
        return code + 1;
    }
}
