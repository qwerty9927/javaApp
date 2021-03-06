package task1.DTO;

public class NhanVienDTO {
    private String Manv;
    private String Ten;
    private String DiaChi;
    private String SDT;
    private String Email;
    private String NgayVaoLam;
    private String NgaySinh;
    private String urlHinh;

    public String getManv() {
        return Manv;
    }

    public void setManv(String manv) {
        Manv = manv;
    }

    public String getTen() {
        return Ten;
    }

    public void setTen(String ten) {
        Ten = ten;
    }

    public String getDiaChi() {
        return DiaChi;
    }

    public void setDiaChi(String diaChi) {
        DiaChi = diaChi;
    }

    public String getSDT() {
        return SDT;
    }

    public void setSDT(String SDT) {
        this.SDT = SDT;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String EMAIL) {
        Email = EMAIL;
    }

    public String getNgayVaoLam() {
        return NgayVaoLam;
    }

    public void setNgayVaoLam(String ngayVaoLam) {
        NgayVaoLam = ngayVaoLam;
    }

    public String getNgaySinh() {
        return NgaySinh;
    }

    public void setNgaySinh(String ngaySinh) {
        NgaySinh = ngaySinh;
    }


    public String getUrlHinh() {
        return urlHinh;
    }

    public void setUrlHinh(String urlHinh) {
        this.urlHinh = urlHinh;
    }


    public String[] getStringValues(){
        String[] row = {getManv(), getTen(), getDiaChi(), getSDT(), getEmail(), getUrlHinh(), getNgayVaoLam(), getNgaySinh()};
        return row;
    }

    public String[] getStringHeader(){
        String[] row = {"Manv", "Ten", "DiaChi", "SDT", "Email", "urlHinh", "NgayVaoLam", "NgaySinh"};
        return row;
    }
}
