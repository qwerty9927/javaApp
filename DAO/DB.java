package task1.DAO;


//import com.mysql.cj.jdbc.result.ResultSetMetaData;

import java.sql.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DB {
    public Connection conn;
    private String url = "jdbc:mysql://localhost:3306/java_ch";
    private String username = "root";
    private String password = "";
    private ResultSet rs = null;
    public DB(){
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
    public ArrayList selectAll(String table, String where){
        ArrayList<HashMap> arrList = new ArrayList<HashMap>();
        try{
            String sql = String.format("SELECT * FROM %s %s", table, where);
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            ResultSetMetaData rsmd = rs.getMetaData();
            System.out.println(sql);
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

    public int selectOnceRow(String table, String where, String col){
        int result = 0;
        try{
            String sql = String.format("SELECT * FROM %s %s", table, where);
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while(rs.next()){
                result = Integer.parseInt(rs.getString(col));
            }
        } catch(Exception e){
            System.out.println(e);
        }
        return result;
    }

    public boolean update(String table, HashMap<String, String> set, String where){
        try{
            String string = "";
            for(Map.Entry<String, String> entry : set.entrySet()){
                string += entry.getKey() + " = ";
                string += "'" + entry.getValue() + "'"  + ",";
            }
            //xoa ky tu cuoi
            StringBuilder newString = new StringBuilder(string);
            newString.deleteCharAt(string.length()-1);
            string = newString.toString();

            //query
            String sql = String.format("UPDATE %s SET %s %s", table, string, where);
            System.out.println(sql);
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
                stringValue += "'" + entry.getValue() + "'" + ",";
            }
            StringBuilder newStringKey = new StringBuilder(stringKey);
            newStringKey.deleteCharAt(stringKey.length()-1);
            stringKey = newStringKey.toString();

            StringBuilder newStringValue = new StringBuilder(stringValue);
            newStringValue.deleteCharAt(stringValue.length() - 1);
            stringValue = newStringValue.toString();

            String sql = String.format("INSERT INTO %s (%s) VALUES (%s)", table, stringKey, stringValue);
            System.out.println(sql);
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
            String sql = String.format("DELETE FROM %s %s", table, where);
            Statement statement = conn.createStatement();
            System.out.println(sql);
            statement.executeUpdate(sql);
        } catch(Exception e){
            System.out.println(e);
            return false;
        }
        return true;
    }
    //?????t
    public ResultSet executeQuery(PreparedStatement stm) {
        try {
            connect();
            rs = stm.executeQuery();
            return rs;
        } catch (SQLException ex) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    //?????t
    public void executeUpdate(PreparedStatement stm){
        try{
            connect();
            stm.executeUpdate();
        }catch(SQLException ex){
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE,null,ex);
        }
    }
    public void closeConnect(){
        try{
            conn.close();
            System.out.println("Close connect");
        }catch(Exception e){
            System.out.println(e);
        }
    }
    //?????t
    public Connection getConnection(){
        return conn;
    }
    public static void main(String args[]){
//        DB a = new DB();
        //select
//        System.out.println(a.select("taikhoan", ""));

        //update
//        HashMap<String, String> map = new HashMap<String, String>();
//        map.put("Username", "HashMap");
//        String where = "Where MATK = 4";
//        a.update("taikhoan", map, where);

        //insert
//        HashMap<String, String> map = new HashMap<String, String>();
//        map.put("MaPN", "1");
//        map.put("MaNCC", "1");
//        map.put("Manv", "1");
//        map.put("TongTien", "10000");
//        a.insert("phieunhap", map);

        //delete
//        a.delete("phieunhap", "Where MaPN = '1'");
//        a.closeConnect();
    }
}

