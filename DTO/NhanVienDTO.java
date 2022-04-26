package task1.DTO;

public class NhanVienDTO {
    private String Makh;
    private String Ten;
    private String DiaChi;
    private String SDT;
    private String Email;
    private String MATK;

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

    public String getMATK() {
        return MATK;
    }

    public void setMATK(String MATK) {
        this.MATK = MATK;
    }

    public String[] getStringValues(){
        String[] row = {getMakh(), getTen(), getDiaChi(), getSDT(), getEmail(), getMATK()};
        return row;
    }

    public String[] getStringHeader(){
        String[] row = {"Makh", "Ten", "DiaChi", "SDT", "Email", "MATK"};
        return row;
    }
}
