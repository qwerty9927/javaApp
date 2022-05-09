package task1.DTO;

public class NhaCungCapDTO {
    private String MaNCC;
    private String Ten;
    private String DiaChi;
    private String SDT;

    public String getMaNCC() {
        return MaNCC;
    }

    public void setMaNCC(String maNCC) {
        MaNCC = maNCC;
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

    public String[] getStringValues(){
        String[] row = {getMaNCC(), getTen(), getDiaChi(), getSDT()};
        return row;
    }

    public String[] getStringHeader(){
        String[] row = {"MaNCC", "Ten", "DiaChi", "SDT"};
        return row;
    }
}
