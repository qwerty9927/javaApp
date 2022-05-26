
package task1.DAO;

import task1.DTO.HoaDonDTO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;


public class HoaDonDAO {
    private DB conn = new DB();
    public HoaDonDAO(){}
    public ArrayList<HoaDonDTO> list()
    {
        ArrayList<HoaDonDTO> dshd = new ArrayList<>();
        try {
            conn.connect();
            String sql = "SELECT * FROM donhang";
            PreparedStatement stm = conn.getConnection().prepareStatement(sql);
            ResultSet rs = conn.executeQuery(stm);
            while(rs.next())
            {
                String maHD = rs.getString("MaDH");
                String maKH = rs.getString("MaKH");
                String maNV = rs.getString("MaNV");
                Date ngayhd = rs.getTimestamp("ThoiDiemDatHang");
                int tongtien = rs.getInt("TongTien");
                HoaDonDTO ct = new HoaDonDTO(maHD, maKH, maNV, ngayhd, tongtien);
                dshd.add(ct);
            }
            rs.close();
            conn.closeConnect();
        } catch (SQLException ex) {
            Logger.getLogger(NhanVienDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return dshd;
    }
    public void add(HoaDonDTO hd) throws SQLException {
        DB conn = new DB();
        conn.connect();
        String maKH = hd.getMaKH().equals("")?null:"'"+hd.getMaKH()+"'";
        String sql = "INSERT INTO donhang VALUES (";
        sql += "'"+hd.getMaHD()+"',";
        sql += "'"+hd.getNgayHD()+"',";
        sql += "'"+hd.getTongTien()+"',";
        sql += "'1',";
        sql += "'"+hd.getMaNV()+"',";
        sql += "'"+hd.getMaKH()+"')";
        PreparedStatement stm = conn.getConnection().prepareStatement(sql);
        System.out.println(sql);
        conn.executeUpdate(stm);
        conn.closeConnect();
    }
    public ArrayList<HoaDonDTO> listMaDH(String madh)
    {
        ArrayList<HoaDonDTO> dshd = new ArrayList<>();
        try {
            conn.connect();
            String sql = "SELECT * FROM donhang where madh = "+madh;
            PreparedStatement stm = conn.getConnection().prepareStatement(sql);
            ResultSet rs = conn.executeQuery(stm);
            while(rs.next())
            {
                String maHD = rs.getString("MaDH");
                String maKH = rs.getString("MaKH");
                String maNV = rs.getString("MaNV");
                Date ngayhd = rs.getTimestamp("ThoiDiemDatHang");
                int tongtien = rs.getInt("TongTien");
                HoaDonDTO ct = new HoaDonDTO(maHD, maKH, maNV, ngayhd, tongtien);
                dshd.add(ct);
            }
            rs.close();
            conn.closeConnect();
        } catch (SQLException ex) {
            Logger.getLogger(NhanVienDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return dshd;
    }
    public ArrayList<HoaDonDTO> listDHMaNV(String manv)
    {
        ArrayList<HoaDonDTO> dshd = new ArrayList<>();
        try {
            conn.connect();
            String sql = "SELECT * FROM donhang where manv = "+manv;
            PreparedStatement stm = conn.getConnection().prepareStatement(sql);
            ResultSet rs = conn.executeQuery(stm);
            while(rs.next())
            {
                String maHD = rs.getString("MaDH");
                String maKH = rs.getString("MaKH");
                String maNV = rs.getString("MaNV");
                Date ngayhd = rs.getTimestamp("ThoiDiemDatHang");
                int tongtien = rs.getInt("TongTien");
                HoaDonDTO ct = new HoaDonDTO(maHD, maKH, maNV, ngayhd, tongtien);
                dshd.add(ct);
            }
            rs.close();
            conn.closeConnect();
        } catch (SQLException ex) {
            Logger.getLogger(NhanVienDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return dshd;
    }
    public ArrayList<HoaDonDTO> listDHMaKH(String makh  )
    {
        ArrayList<HoaDonDTO> dshd = new ArrayList<>();
        try {
            conn.connect();
            String sql = "SELECT * FROM donhang where makh = "+makh;
            PreparedStatement stm = conn.getConnection().prepareStatement(sql);
            ResultSet rs = conn.executeQuery(stm);
            while(rs.next())
            {
                String maHD = rs.getString("MaDH");
                String maKH = rs.getString("MaKH");
                String maNV = rs.getString("MaNV");
                Date ngayhd = rs.getTimestamp("ThoiDiemDatHang");
                int tongtien = rs.getInt("TongTien");
                HoaDonDTO ct = new HoaDonDTO(maHD, maKH, maNV, ngayhd, tongtien);
                dshd.add(ct);
            }
            rs.close();
            conn.closeConnect();
        } catch (SQLException ex) {
            Logger.getLogger(NhanVienDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return dshd;
    }

    public ArrayList<HoaDonDTO> listDHGia(String min, String max  )
    {
        ArrayList<HoaDonDTO> dshd = new ArrayList<>();
        try {
            conn.connect();
            String sql = "SELECT * FROM donhang where tongtien between "+min+" and "+max;
            System.out.printf(sql);
            PreparedStatement stm = conn.getConnection().prepareStatement(sql);
            ResultSet rs = conn.executeQuery(stm);
            while(rs.next())
            {
                String maHD = rs.getString("MaDH");
                String maKH = rs.getString("MaKH");
                String maNV = rs.getString("MaNV");
                Date ngayhd = rs.getTimestamp("ThoiDiemDatHang");
                int tongtien = rs.getInt("TongTien");
                HoaDonDTO ct = new HoaDonDTO(maHD, maKH, maNV, ngayhd, tongtien);
                dshd.add(ct);
            }
            rs.close();
            conn.closeConnect();
        } catch (SQLException ex) {
            Logger.getLogger(NhanVienDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return dshd;
    }
}