package menu;

import java.util.ArrayList;
import java.util.List;

/**
 * Singleton class for defining a Menu containing items that can be ordered
 */
public class Menu extends Object {
    /**
     * Instance for singleton class
     */
    private static Menu menuInstance = null;

    /**
     * List of items in the Menu
     */
    private List<MenuItem> menuItems = null;

    /**
     * Private constructor of Menu
     */
    private Menu() {
        menuItems = new ArrayList<>();
    }

    /**
     * Get menu to constructor (one time only)
     * @return a Menu
     */
    public static Menu getInstance() {
        if (menuInstance == null) {
            menuInstance = new Menu();
        }
        return menuInstance;
    }

    /**
     * Getting the list of items in the menu
     *
     * @return Copied list of current items in the menu
     */
    public List<MenuItem> getItems() {
        List<MenuItem> peekingMenu = List.copyOf(this.menuItems);
        return peekingMenu;
    }

    /**
     * Add new item in this menu.
     *
     * @param item A MenuItem object to be add
     */
    public void registerMenuItem(MenuItem item) {
        boolean newItem = true;

        //checking if the item exists or not
        for (MenuItem existingItem : this.getItems()) {
            if (existingItem.equals(item)) {
                newItem = false;
            }
        }

        if (newItem) {
            this.menuItems.add(item);
        }
    }

    /**
     * Get the item at the specified position
     *
     * @param index The position of the item in the menu list
     * @return The MenuItem at the specified position
     * @throws IndexOutOfBoundsException if the position does not exist
     */
    public MenuItem get(int index) throws IndexOutOfBoundsException {
        if (index >= this.getItems().size()) {
            throw new IndexOutOfBoundsException();
        } else {
            return this.getItems().get(index);
        }
    }

    /**
     * Delete all the items in the menu
     */
    public void clear() {
        this.menuItems = new ArrayList<MenuItem>();
    }

}
