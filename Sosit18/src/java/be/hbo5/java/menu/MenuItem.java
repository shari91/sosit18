package be.hbo5.java.menu;

/**
 * @author woutbr@student.hik.be
 */
public abstract class MenuItem {

    private String name;

    public MenuItem(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "MenuItem{" + "name=" + name + '}';
    }

}
