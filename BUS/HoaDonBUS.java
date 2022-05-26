
package task1.BUS;

import task1.DAO.HoaDonDAO;
import task1.DTO.HoaDonDTO;

import java.sql.SQLException;
import java.util.ArrayList;


public class HoaDonBUS {
    private ArrayList<HoaDonDTO> dsHD;

    public HoaDonBUS() {
 list();
    }

    public HoaDonBUS(int i) {
        list();
    }

    public void list() {
        HoaDonDAO hdDAO = new HoaDonDAO();
        dsHD = new ArrayList<>();
        dsHD = hdDAO.list();
    }
    public void add(HoaDonDTO hd) throws SQLException {
        HoaDonDAO hdDAO = new HoaDonDAO();
        hdDAO.add(hd);
    }
    public String initMaHD()
    {
        list();
        int max = 0;
        String s ="";
        for(HoaDonDTO hd :dsHD){
            //Duyệt danh sách hóa đơn, tìm mã hóa đơn lớn nhất
            int id = Integer.parseInt(hd.getMaHD());
            if(id > max)
                max = id;
        }
        for(int i = 0;i<3-String.valueOf(max+1).length();i++)
            s+="0";
        return s+(max+1);
    }
    public void listMaDH(String maDH){
        HoaDonDAO dao = new HoaDonDAO();
        dsHD = new ArrayList<>();
        dsHD = dao.listMaDH(maDH);
    }
    public void listDHKH(String makh){
        HoaDonDAO dao = new HoaDonDAO();
        dsHD = new ArrayList<>();
        dsHD = dao.listDHMaKH(makh);
    }
    public void listDHNV(String manv){
        HoaDonDAO dao = new HoaDonDAO();
        dsHD = new ArrayList<>();
        dsHD = dao.listDHMaNV(manv);
    }
    public void listDHGia(String min, String max){
        HoaDonDAO dao = new HoaDonDAO();
        dsHD = new ArrayList<>();
        dsHD = dao.listDHGia(min, max);
    }
    public ArrayList<HoaDonDTO> getList(){
        return dsHD;
    }
}