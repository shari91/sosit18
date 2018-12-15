package controller;

import be.hbo5.java.menu.MenuItem;
import be.hbo5.java.menu.MenuLink;
import be.hbo5.java.menu.MenuList;
import be.hbo5.java.xml.LinksXmlReader;
import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import net.bootsfaces.component.dropMenu.DropMenu;
import net.bootsfaces.component.navBarLinks.NavBarLinks;
import net.bootsfaces.component.navLink.NavLink;

/**
 * Deze bean laad de links voor de navbar uit een xml bestand en rendered er een
 * NavBarLinks van. Als een user is ingelogd of uitgelogd moet navBarMenu
 * opnieuw gerendered worden.
 *
 * @author woutbr@student.hik.be
 */
@Named(value = "navBarBean")
@SessionScoped
public class NavBarBean implements Serializable {

    private final MenuList links;
    private NavBarLinks navBarMenu;

    public NavBarBean() {
        ExternalContext ext = FacesContext.getCurrentInstance().getExternalContext();
        String linksXmlPath = ext.getRealPath("/templates/navbarlinks.xml");
        this.links = LinksXmlReader.readLinksFromXmlFile(linksXmlPath);
        this.renderNavBarMenu();
    }

    public MenuList getLinks() {
        return links;
    }

    public NavBarLinks getNavBarMenu() {
        return navBarMenu;
    }

    /**
     * Create a BootsFaces NavBarLinks from the links in the MenuList variable.
     */
    public final void renderNavBarMenu() {
        this.navBarMenu = new NavBarLinks();
        List<UIComponent> children = this.navBarMenu.getChildren();
        for (MenuItem mi : this.links) {
            if (MenuLink.class.isInstance(mi)) {
                children.add(createNavLinkFromMenuLink((MenuLink) mi));
            } else if (MenuList.class.isInstance(mi)) {
                children.add(createDropMenuFromMenuList((MenuList) mi));
            }
        }
    }

    private NavLink createNavLinkFromMenuLink(MenuLink menulink) {
        NavLink navLink = new NavLink();
        navLink.setValue(menulink.getName());
        navLink.setHref(menulink.getHref());
        return navLink;
    }

    private DropMenu createDropMenuFromMenuList(MenuList menulist) {
        DropMenu dropmenu = new DropMenu();
        List<UIComponent> children = dropmenu.getChildren();
        for (MenuItem mi : menulist) {
            if (MenuLink.class.isInstance(mi)) {
                children.add(createNavLinkFromMenuLink((MenuLink) mi));
            } else if (MenuList.class.isInstance(mi)) {
                children.add(createDropMenuFromMenuList((MenuList) mi));
            }
        }
        return dropmenu;
    }

}
