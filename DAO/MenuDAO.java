package task1.DAO;

import task1.GUI.LoginGUI;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class MenuDAO extends DB {
    private ArrayList<HashMap<String, String>> arrMap;
    public MenuDAO(){
        connect();
        arrMap = selectAll("role_dm as rm, danhmuc as dm", String.format("Where  rm.MaID = %s and rm.MaDM = dm.MADM", LoginGUI.roleID));
        System.out.println(LoginGUI.roleID);
        closeConnect();
    }

    public String[] getOption(){
        String[] strings = new String[arrMap.size()];
        for(int i = 0;i < arrMap.size();i++){
            strings[i] = arrMap.get(i).get("TENDM");
        }
        return strings;
    }

    public String[] getIcon(){
        String[] strings = new String[arrMap.size()];
        for(int i = 0;i < arrMap.size();i++){
            strings[i] = arrMap.get(i).get("urlHinh");
        }
        return strings;
    }
}
