package task1.DAO;

import java.util.ArrayList;
import java.util.HashMap;

public class KhachHangDAO extends DB {
    public KhachHangDAO(){

    }
    public static void main(String args[]){
        KhachHangDAO a = new KhachHangDAO();

        ArrayList<HashMap<String, String>> arrmap = new ArrayList<HashMap<String, String>>(a.select("khachhang", ""));
        System.out.println(arrmap.get(1).get("DiaChi"));
        a.closeConnect();
    }
}
