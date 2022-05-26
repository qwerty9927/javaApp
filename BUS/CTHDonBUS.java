package task1.BUS;

import task1.DAO.CTHDonDAO;
import task1.DTO.CTHDonDTO;

import java.sql.SQLException;

public class CTHDonBUS {
    public void add(CTHDonDTO ct) throws SQLException {
        CTHDonDAO ctDAO = new CTHDonDAO();
        ctDAO.add(ct);
    }

}
