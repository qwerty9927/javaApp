package task1.DAO;

import task1.DTO.CTHDonDTO;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CTHDonDAO {
    public void add(CTHDonDTO ct) throws SQLException{
        DB conn = new DB();
        conn.connect();
        String sql = "insert into chitietdonhang values(";
        sql += "'"+ ct.getMaDH()+"',";
        sql += "'"+ ct.getMaSP()+"',";
        sql += "'"+ ct.getSoLuong()+"',";
        sql += "'"+ ct.getGia()+"')";
        PreparedStatement stm = conn.getConnection().prepareStatement(sql);
        System.out.println(sql);
        conn.executeUpdate(stm);
        conn.closeConnect();
    }
}
