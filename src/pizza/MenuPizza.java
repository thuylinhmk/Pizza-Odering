package pizza;

import exceptions.TooManyToppingsException;
import menu.MenuItem;
import pizza.ingredients.Topping;

import java.util.List;

/**
 * Representing pizzas in the menu
 */
public class MenuPizza extends Pizza implements MenuItem {

    /**
     * Construct the pizza with size, sauce, cheese and list of topping
     *
     * @param size      The size of this pizza
     * @param sauce     The sauce on this pizza
     * @param cheese    The cheese on this pizza
     * @param toppings  The list of toppings on this pizza
     * @throws IllegalArgumentException If any of the argument is null
     * @throws TooManyToppingsException If the list containing too many toppings
     */
    public MenuPizza(BaseSize size,
                     Sauce sauce,
                     Cheese cheese,
                     List<Topping> toppings)
            throws IllegalArgumentException, TooManyToppingsException {
        super(size, sauce, cheese, toppings);
    }

    /**
     * A human-readable string representing this pizza
     *
     * @return The string form representing this pizza
     */
    public String toString() {
        String toPrint = "[MenuPizza] " + super.toString();
        return toPrint;
    }
}
