package task1.BUS;

import task1.DAO.MenuDAO;

import java.util.ArrayList;

public class MenuBUS {
    private MenuDAO dao;
    public MenuBUS(){
        dao = new MenuDAO();
    }
    public String[] getIcon(){
        return dao.getIcon();
    }

    public String[] getOption(){
        return dao.getOption();
    }
}
