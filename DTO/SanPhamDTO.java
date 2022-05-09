package task1.DTO;

public class SanPhamDTO {
    private String MaSP;
    private String TenSP;
    private String SoLuongSP;
    private String Gia;
    private String urlHinh;
    private String Loai;

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

    public String getSoLuongSP() {
        return SoLuongSP;
    }

    public void setSoLuongSP(String soLuongSP) {
        SoLuongSP = soLuongSP;
    }

    public String getGia() {
        return Gia;
    }

    public void setGia(String gia) {
        Gia = gia;
    }

    public String getUrlHinh() {
        return urlHinh;
    }

    public void setUrlHinh(String urlHinh) {
        this.urlHinh = urlHinh;
    }

    public String getLoai() {
        return Loai;
    }

    public void setLoai(String loai) {
        Loai = loai;
    }

    public String[] getStringValues(){
        String[] row = {getMaSP(), getTenSP(), getSoLuongSP(), getGia(), getLoai(), getUrlHinh()};
        return row;
    }

    public String[] getStringHeader(){
        String[] row = {"MaSP", "TenSP", "SoLuongSP", "Gia", "Loai", "urlHinh"};
        return row;
    }
}
