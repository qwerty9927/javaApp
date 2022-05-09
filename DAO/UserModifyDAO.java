package task1.DAO;

import task1.DTO.UsersDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserModifyDAO {

    public static List<UsersDTO> getUserList() {
        List<UsersDTO> dataList = new ArrayList<>();

        Connection conn = null;
        PreparedStatement statement = null;

        try {
            conn = DriverManager.getConnection(Config.url, Config.username, Config.password);

            String sql = "select * from users";
            statement = conn.prepareStatement(sql);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                UsersDTO u = new UsersDTO(
                        resultSet.getString("username"),
                        resultSet.getString("email"),
                        resultSet.getString("password"),
                        resultSet.getInt("role_id"),
                        resultSet.getInt("active")
                );
                dataList.add(u);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserModifyDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException ex) {
                    Logger.getLogger(UserModifyDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(UserModifyDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        return dataList;
    }

    public static UsersDTO login(String username, String password) {
        UsersDTO user = null;
        Connection conn = null;
        PreparedStatement statement = null;

        try {
            conn = DriverManager.getConnection(Config.url, Config.username, Config.password);

            String sql = "select * from users where username = ? and password = ?";
            statement = conn.prepareStatement(sql);
            statement.setString(1, username);
            statement.setString(2, password);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                user = new UsersDTO(
                        resultSet.getString("username"),
                        resultSet.getString("email"),
                        resultSet.getString("password"),
                        resultSet.getInt("role_id"),
                        resultSet.getInt("active")
                );
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserModifyDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException ex) {
                    Logger.getLogger(UserModifyDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(UserModifyDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        return user;
    }

    public static void insert(UsersDTO user) {
        Connection conn = null;
        PreparedStatement statement = null;

        try {
            conn = DriverManager.getConnection(Config.url, Config.username, Config.password);

            String sql = "insert into users (username, email, password, role_id, active) "
                    + "values (?, ?, ?, ?, ?)";
            statement = conn.prepareStatement(sql);

            statement.setString(1, user.getUsername());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPassword());
            statement.setInt(4, user.getRoleID());
            statement.setInt(5, user.getActive());

            statement.execute();
        } catch (SQLException ex) {
            Logger.getLogger(UserModifyDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException ex) {
                    Logger.getLogger(UserModifyDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(UserModifyDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public static void update(UsersDTO user) {
        Connection conn = null;
        PreparedStatement statement = null;

        try {
            conn = DriverManager.getConnection(Config.url, Config.username, Config.password);

            String sql = "update users set email = ?, password = ?, role_id = ?, active = ? where username = ?";
            statement = conn.prepareStatement(sql);

            statement.setString(1, user.getEmail());
            statement.setString(2, user.getPassword());
            statement.setInt(3, user.getRoleID());
            statement.setInt(4, user.getActive());
            statement.setString(5, user.getUsername());

            statement.execute();
        } catch (SQLException ex) {
            Logger.getLogger(UserModifyDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException ex) {
                    Logger.getLogger(UserModifyDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(UserModifyDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public static void delete(String username) {
        Connection conn = null;
        PreparedStatement statement = null;

        try {
            conn = DriverManager.getConnection(Config.url, Config.username, Config.password);

            String sql = "delete from users where username = ?";
            statement = conn.prepareStatement(sql);

            statement.setString(1, username);

            statement.execute();
        } catch (SQLException ex) {
            Logger.getLogger(UserModifyDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException ex) {
                    Logger.getLogger(UserModifyDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(UserModifyDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}
