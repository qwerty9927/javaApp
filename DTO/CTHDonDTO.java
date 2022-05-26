package task1.DTO;

public class CTHDonDTO {
    private String MaDH;
    private String MaSP, TenSP;
    private int SoLuong, Gia;


    public CTHDonDTO(String maDH, String maSP, String tenSP, int soLuong, int gia) {
        MaDH = maDH;
        MaSP = maSP;
        TenSP = tenSP;
        SoLuong = soLuong;
        Gia = gia;
    }

    public String getMaDH() {
        return MaDH;
    }

    public void setMaDH(String maDH) {
        MaDH = maDH;
    }

    public String getMaSP() {
        return MaSP;
    }

    public void setMaSP(String maSP) {
        MaSP = maSP;
    }

    public String getTenSP() {
        return TenSP;
    }

    public void setTenSP(String tenSP) {
        TenSP = tenSP;
    }

    public int getSoLuong() {
        return SoLuong;
    }

    public void setSoLuong(int soLuong) {
        SoLuong = soLuong;
    }

    public int getGia() {
        return Gia;
    }

    public void setGia(int gia) {
        Gia = gia;
    }
}
