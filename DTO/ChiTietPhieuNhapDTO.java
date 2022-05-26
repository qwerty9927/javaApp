package task1.DTO;

public class ChiTietPhieuNhapDTO {
    private String MaPN;
    private String MaSP;
    private String SoLuongSP;

    public String getMaPN() {
        return MaPN;
    }

    public void setMaPN(String maPN) {
        MaPN = maPN;
    }

    public String getMaSP() {
        return MaSP;
    }

    public void setMaSP(String maSP) {
        MaSP = maSP;
    }

    public String getSoLuongSP() {
        return SoLuongSP;
    }

    public void setSoLuongSP(String soLuongSP) {
        SoLuongSP = soLuongSP;
    }

    public String[] getStringValues(){
        String[] row = {getMaPN(), getMaSP(), getSoLuongSP()};
        return row;
    }

    public String[] getStringHeader(){
        String[] row = {"MaPN", "MaSP", "SoLuongSp"};
        return row;
    }
}
