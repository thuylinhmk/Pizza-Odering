package pizza;

import exceptions.TooManyToppingsException;
import menu.MenuItem;
import pizza.ingredients.Bases;
import pizza.ingredients.Cheeses;
import pizza.ingredients.Sauces;
import pizza.ingredients.Topping;

import java.util.Iterator;
import java.util.List;

/**
 * A custom pizza you can add more toppings
 */
public class CustomPizza extends Pizza implements MenuItem {
    /**
     * Constructor of a default pizza with medium size, tomato sauce
     * and mozzarella cheese with no toppings
     */
    public CustomPizza() {
        super(BaseSize.MEDIUM, Sauce.TOMATO, Cheese.MOZZARELLA);
        this.setName("Custom Pizza");
    }

    /**
     * Constructor of pizza for your choice of size, sauce, cheese and no toppings
     *
     * @param size The size you want
     * @param sauce The sauce you want
     * @param cheese The cheese you want
     * @throws IllegalArgumentException if any of the argument is null
     */
    public CustomPizza(Bases.BaseSize size,
                       Sauces.Sauce sauce,
                       Cheeses.Cheese cheese)
            throws IllegalArgumentException {
        super(size, sauce, cheese);
        this.setName("Custom Pizza");
    }

    /**
     * Add a list of toppings to pizza (less than 5 toppings)
     *
     * @param toppings List of toppings you want to add
     * @throws TooManyToppingsException If adding the list makes number of topping on
     *                                  pizza more than 5
     * @throws IllegalArgumentException If the list is null
     */
    public void add(List<Topping> toppings)
            throws TooManyToppingsException, IllegalArgumentException {

        if (toppings == null) {
            throw new IllegalArgumentException();
        }
        int afterAdd = this.getToppings().size() + toppings.size();

        if (afterAdd <= MAX_TOPPINGS) {
            for (Topping topping : toppings) {
                this.add(topping);
            }
        } else {
            throw new TooManyToppingsException("Too many toppings");
        }

    }

    /**
     * Add a single topping to the pizza
     *
     * @param topping The topping you want to add
     * @throws TooManyToppingsException If add the topping make the number of toppings on pizza > 5
     * @throws IllegalArgumentException If topping is null
     */
    public void add(Topping topping)
            throws TooManyToppingsException, IllegalArgumentException {
        if (topping == null) {
            throw new IllegalArgumentException();
        }

        int afterAdd = this.getToppings().size() + 1;
        //check if after adding topping, the number of topping still valid
        if (afterAdd <= 5) {
            this.accessToppings().add(topping);
        } else {
            throw new TooManyToppingsException("Too many toppings");
        }

    }

    /**
     * Remove unwanted topping
     *
     * @param topping The topping you want to remove
     */
    public void remove(Topping topping) {
        //finding the topping you want to remove
        for (Iterator<Topping> addedTopping = this.accessToppings().iterator();
             addedTopping.hasNext();) {
            Topping checkTopping = addedTopping.next();
            if (checkTopping.equals(topping)) {
                addedTopping.remove();
                break;
            }
        }
    }

}
