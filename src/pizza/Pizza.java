package pizza;

import exceptions.TooManyToppingsException;
import menu.MenuItem;
import pizza.ingredients.Bases;
import pizza.ingredients.Cheeses;
import pizza.ingredients.Sauces;
import pizza.ingredients.Topping;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Abstract class for Pizza
 */
public abstract class Pizza extends Object implements Bases, Sauces, Cheeses, MenuItem {
    /**
     * The maximum number of toppings that can be placed on a pizza (5).
     */
    public static final int MAX_TOPPINGS = 5;

    /**
     * Pizza size
     */
    private BaseSize size;

    /**
     * Sauce on this pizza
     */
    private Sauce sauce;

    /**
     * Cheese on this pizza
     */
    private Cheese cheese;

    /**
     * Name of this Pizza
     */
    private String pizzaName;

    /**
     * List of toppings on this pizza
     */
    private List<Topping> addedTopping;

    /**
     * Construction of default pizza with no additional topping
     */
    public Pizza() {
        //set pizza
        this.set(BaseSize.MEDIUM);
        this.set(Sauce.TOMATO);
        this.set(Cheese.MOZZARELLA);
        this.setName("Dr Java's Pizza");
        registerMenuItem();
    }

    /**
     * Construction of pizza with choice of size, sauce, cheese and no toppings
     * @param size      Size of this pizza
     * @param sauce     Sauce for this pizza
     * @param cheese    Cheese for this pizza
     * @throws IllegalArgumentException If any argument is null
     */
    public Pizza(Bases.BaseSize size,
                 Sauces.Sauce sauce,
                 Cheeses.Cheese cheese) throws IllegalArgumentException {
        //throw error if null in any argument
        if (size == null || sauce == null || cheese == null) {
            throw new IllegalArgumentException();
        }

        //set pizza
        this.set(size);
        this.set(sauce);
        this.set(cheese);
        this.setName("Dr Java's Pizza");
        this.addedTopping = new ArrayList<Topping>();
        registerMenuItem();

    }

    /**
     * Construct a pizza with choice of size, sauce, cheese and list of toppings
     *
     * @param size      The size of this pizza
     * @param sauce     The sauce on this pizza
     * @param cheese    The cheese on the pizza
     * @param toppings  The list of topping adding to this pizza
     * @throws IllegalArgumentException If any agrument is null
     * @throws TooManyToppingsException If the list of toppings more than 5
     */
    public Pizza(Bases.BaseSize size,
                 Sauces.Sauce sauce,
                 Cheeses.Cheese cheese,
                 List<Topping> toppings)
            throws IllegalArgumentException, TooManyToppingsException {
        if (size == null || sauce == null || cheese == null || toppings == null) {
            throw new IllegalArgumentException();
        }

        //set pizza
        this.set(size);
        this.set(sauce);
        this.set(cheese);
        this.setName("Dr Java's Pizza");
        this.addedTopping = new ArrayList<Topping>();
        registerMenuItem();

        // add topping to pizza
        if (toppings.size() > MAX_TOPPINGS) {
            throw new TooManyToppingsException("Too Many Topping");
        } else {
            this.addedTopping.addAll(toppings);
        }

    }

    /**
     * Getting a list of current toppings on this pizza
     *
     * @return Copied list of toppings on this pizza
     */
    public List<Topping> getToppings() {
        List<Topping> toppingOfPizza = new ArrayList<>();
        if (!(this.addedTopping == null) && this.addedTopping.size() > 0) {
            toppingOfPizza = List.copyOf(this.addedTopping);
        }
        return toppingOfPizza;
    }

    /**
     * Get the price for this pizza
     *
     * @return Price of this pizza
     */
    public double getTotalPrice() {
        double totalPrice = this.size.getPrice() + this.getToppings().size() * Topping.PRICE;
        return totalPrice;
    }

    /**
     * Get the name of this pizza
     * @return  The String of name for this pizza
     */
    public String getName() {
        return this.pizzaName;
    }

    /**
     * Change the name of this pizza
     * @param name  The name you want to change into for this pizza
     * @throws IllegalArgumentException If name is null or empty
     */
    public void setName(String name) throws IllegalArgumentException {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException();
        }
        this.pizzaName = name;
    }

    /**
     * Change the base size of this pizza
     *
     * @param size The size of pizza
     */
    @Override
    public void set(Bases.BaseSize size) {
        this.size = size;
    }

    /**
     * Change the cheese on the pizza
     *
     * @param cheese The cheese you want to add
     */
    @Override
    public void set(Cheeses.Cheese cheese) {
        this.cheese = cheese;
    }

    /**
     * Change the sauce on this pizza
     *
     * @param sauce The sauce you want to add on your pizza
     */
    @Override
    public void set(Sauces.Sauce sauce) {
        this.sauce = sauce;
    }

    /**
     * Unique number code for this pizza. Calculate based on size, sauce, cheese and toppings.
     *
     * @return  Integer for the hashcode of this pizza
     */
    @Override
    public int hashCode() {
        int hashCode = this.size.hashCode() * this.sauce.hashCode() * this.cheese.hashCode();
        for (Topping topping :  this.getToppings()) {
            hashCode *= topping.hashCode();
        }
        return hashCode;
    }

    /**
     * Comparing two pizza. Two equal pizza will have the same size, sauce, cheese and toppings (no
     * care about order of toppings)
     *
     * @param other   The reference object with which to compare
     * @return  True if 2 pizzas are equal. False if not
     */
    public boolean equals(Object other) {
        boolean isEqual = false;

        if (this == other) {
            return true;
        }

        if (other == null) {
            return false;
        }

        if (this.getClass() != other.getClass()) {
            return false;
        }

        Pizza compare = (Pizza) other;
        if (this.size.equals(compare.size) && this.sauce.equals(compare.sauce)
                && this.cheese.equals(compare.cheese)) {
            if (this.getToppings().size() == ((Pizza) other).getToppings().size()) {
                if (this.getToppings().containsAll(((Pizza) other).getToppings())
                        && ((Pizza) other).getToppings().containsAll(this.getToppings())) {
                    isEqual = true;
                }
            }
        }

        return isEqual;
    }

    /**
     * Human-readable string representing this pizza
     *
     * @return The string representing this pizza
     */
    public String toString() {
        String toPrint = this.getName() + ": is a '" + this.size.toString()
                + "' sized base with '" + this.sauce.toString() + "' sauce and '"
                + this.cheese.toString() + "' cheese";
        if (this.getToppings().size() > 0) {
            String toppingPrint = " - Toppings: ["
                    + this.getToppings().stream().map(Object::toString)
                    .collect(Collectors.joining(", ")) + "]";
            toPrint += toppingPrint;
        }
        String price = " $" + String.format("%.2f", this.getTotalPrice());
        toPrint += price;
        return toPrint;
    }

    /**
     * Protect method to access the list of toppings on this pizza. Can change the toppings on this
     * pizza
     *
     * @return The list of toppings on this pizza
     * @return The list of toppings on this pizza
     */
    protected List<Topping> accessToppings() {
        return this.addedTopping;
    }

}
