package task1.DTO;

public class UsersDTO {
    String username, email, password;
    int roleID, active;

    public UsersDTO(String username, String email, String password, int roleID, int active) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.roleID = roleID;
        this.active = active;
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
