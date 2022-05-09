package task1.DTO;

public class KhachHangDTO {
    private String Makh;
    private String Ten;
    private String DiaChi;
    private String SDT;
    private String Email;
    private String urlHinh;

    public String getMakh() {
        return Makh;
    }

    public void setMakh(String makh) {
        Makh = makh;
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

    public String getUrlHinh() {
        return urlHinh;
    }

    public void setUrlHinh(String urlHinh) {
        this.urlHinh = urlHinh;
    }


    public String[] getStringValues(){
        String[] row = {getMakh(), getTen(), getDiaChi(), getSDT(), getEmail(), getUrlHinh()};
        return row;
    }

    public String[] getStringHeader(){
        String[] row = {"Makh", "Ten", "DiaChi", "SDT", "Email", "urlHinh"};
        return row;
    }
}
