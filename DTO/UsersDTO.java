package task1.DTO;

public class UsersDTO {
    String username, email, password;
    String manvTK;
    int roleID, active;

    public UsersDTO(String username, String email, String password, int roleID, int active, String manv) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.roleID = roleID;
        this.active = active;
        this.manvTK = manv;
    }

    public String getManvTK() {
        return manvTK;
    }

    public void setManvTK(String manvTK) {
        this.manvTK = manvTK;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRoleID() {
        return roleID;
    }

    public void setRoleID(int roleID) {
        this.roleID = roleID;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }
}
