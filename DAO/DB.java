package task1.DAO;


//import com.mysql.cj.jdbc.result.ResultSetMetaData;

import java.sql.*;
import java.util.*;

public class DB {
    public Connection conn;
    private String url = "jdbc:mysql://localhost:3306/java_ch";
    private String username = "root";
    private String password = "";
    public DB(){
        connect();
    }
    public void connect(){
        try{
            conn = DriverManager.getConnection(url, username, password);
            System.out.println("Connect to Database");
        } catch(SQLException e){
            System.out.println("Error");
            e.printStackTrace();
        }
    }
    public ArrayList select(String table, String where){
        ArrayList<HashMap> arrList = new ArrayList<HashMap>();
        try{
            String sql = String.format("SELECT * FROM %s %s", table, where);
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            ResultSetMetaData rsmd = rs.getMetaData();
            int colNumber = rsmd.getColumnCount();
            while(rs.next()){
                HashMap<String, String> arr = new HashMap<String, String>();
                for(int i = 1;i <= colNumber;i++){
                    String colName = rsmd.getColumnName(i);
                    arr.put(colName, rs.getString(i));
                }
                arrList.add(arr);
            }
        }catch(Exception e) {
            System.out.println(e);
            System.out.println("Error");
        }
        return arrList;
    }

    public boolean update(String table, HashMap<String, String> set, String where){
        try{
            String string = "";
            for(Map.Entry<String, String> entry : set.entrySet()){
                string += entry.getKey() + " = ";
                string += "\"" + entry.getValue() + "\""  + ",";
            }
            //xoa ky tu cuoi
            StringBuilder newString = new StringBuilder(string);
            newString.deleteCharAt(string.length()-1);
            string = newString.toString();

            //query
            String sql = String.format("UPDATE %s SET %s %s", table, string, where);
            Statement statement = conn.createStatement();
            statement.executeUpdate(sql);
        }catch(Exception e){
            System.out.println(e);
            return false;
        }
        return true;
    }

    public boolean insert(String table, HashMap<String, String> input){
        try{
            String stringKey = "";
            String stringValue = "";
            for(Map.Entry<String, String> entry : input.entrySet()){
                stringKey +=  entry.getKey() + ",";
                stringValue += "\"" + entry.getValue() + "\"" + ",";
            }
            StringBuilder newStringKey = new StringBuilder(stringKey);
            newStringKey.deleteCharAt(stringKey.length() - 1);
            StringBuilder newStringValue = new StringBuilder(stringValue);
            newStringValue.deleteCharAt(stringValue.length() - 1);
            String sql = "INSERT INTO (" +  stringKey + "VALUES" + stringValue + ")";
            Statement statement = conn.createStatement();
            statement.executeUpdate(sql);
        }catch(Exception e){
            System.out.println(e);
            return false;
        }
        return true;
    }

    public boolean delete(String table, String where){
        try{
            String sql = String.format("DELETE %s FROM %s", table, where);
            Statement statement = conn.createStatement();
            statement.executeUpdate(sql);
        } catch(Exception e){
            System.out.println(e);
            return false;
        }
        return true;
    }
    public void closeConnect(){
        try{
            conn.close();
        }catch(Exception e){
            System.out.println(e);
        }
    }
    public static void main(String args[]){
        DB a = new DB();
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("Username", "HashMap");
        String where = "Where MATK = 4";
        a.update("taikhoan", map, where);
        a.closeConnect();
    }
}

