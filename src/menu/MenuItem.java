package menu;

/**
 * Represent item can be add to menu and ordered
 */
public interface MenuItem {

    /**
     * Register this item in the menu
     */
    default void registerMenuItem() {
        Menu menu = Menu.getInstance();
        menu.registerMenuItem(this);
    }

    /**
     * Default method implementing in the subclass. Get the price of this item
     *
     * @return Double type for the price if this item
     */
    double getTotalPrice();

    /**
     * Implement in the subclass. Get the name of item
     * @return The name of this menu item
     */
    String getName();

    /**
     * Class for discount
     */
    public static interface Discount {
        /**
         * Applying discount
         *
         * @param price the current price
         * @return the price after discount is applied
         */
        double applyDiscount(double price);
    }

}
