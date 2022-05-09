package task1.DTO;

public class AccountDTO {
    private String username;
    private String password;
    private String rePassword;
    private String manv;
    private String email;
    private String urlHinh;
    private String roleID;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRePassword() {
        return rePassword;
    }

    public void setRePassword(String rePassword) {
        this.rePassword = rePassword;
    }

    public String getManv() {
        return manv;
    }

    public void setManv(String manv) {
        this.manv = manv;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUrlHinh() {
        return urlHinh;
    }

    public void setUrlHinh(String urlHinh) {
        this.urlHinh = urlHinh;
    }

    public String getRoleID() {
        return roleID;
    }

    public void setRoleID(String roleID) {
        this.roleID = roleID;
    }

    public String[] getStringValues(){
        String[] row = {getUsername(), getPassword(), getEmail(), getManv(), getUrlHinh()};
        return row;
    }

    public String[] getStringHeader(){
        String[] row = {"Username", "Password", "Email", "Manv", "urlHinh"};
        return row;
    }

    public String[] getStringHeaderAdd(){
        String[] row = {"Username", "Password", "Re-Password", "Email", "Manv"};
        return row;
    }

    public String[] getLengthInput(){
        String[] row = {"Username", "Password", "Email", "Manv"};
        return row;
    }
}
