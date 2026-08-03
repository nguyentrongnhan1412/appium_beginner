package pages;

import components.AppBar;
import components.SideMenu;

public class BasePage {
    public SideMenu sideMenu;
    public AppBar appBar;

    public BasePage(){
        appBar = new AppBar();
        sideMenu = new SideMenu();
    }
}
