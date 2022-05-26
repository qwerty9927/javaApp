package task1.DTO;

public class PhieuNhapDTO {
    private String MaPN;
    private String MaNCC;
    private String Manv;
    private String TongTien;

    public String getMaPN() {
        return MaPN;
    }

    public void setMaPN(String maPN) {
        MaPN = maPN;
    }

    public String getMaNCC() {
        return MaNCC;
    }

    public void setMaNCC(String maNCC) {
        MaNCC = maNCC;
    }

    public String getManv() {
        return Manv;
    }

    public void setManv(String manv) {
        Manv = manv;
    }

    public String getTongTien() {
        return TongTien;
    }

    public void setTongTien(String tongTien) {
        TongTien = tongTien;
    }

    public String[] getStringValues(){
        String[] row = {getMaPN(), getMaNCC(), getManv(), getTongTien()};
        return row;
    }

    public String[] getStringHeader(){
        String[] row = {"MaPN", "MaNCC", "Manv", "TongTien"};
        return row;
    }

    public String[] getStringInput(){
        String[] row = {"MaPN", "MaNCC", "Manv"};
        return row;
    }
}
