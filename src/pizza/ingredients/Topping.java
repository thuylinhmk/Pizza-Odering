package pizza.ingredients;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Representing toppings to add on Pizza
 */
public class Topping extends Object {
    /**
     * Price of each topping.
     */
    public static final double PRICE = 2.0;

    /**
     * Name of pizza
     */
    private String name;

    /**
     * True if the topping is vegan friendly
     */
    private boolean isVegan;

    /**
     * Value array of topping
     */
    private static Topping[] existedTopping = new Topping[] {};

    /**
     * List of created Topping
     */
    private static List<Topping> createdTopping = new ArrayList<Topping>();

    /**
     * Private constructor for topping from name and vegan option
     *
     * @param name  Name of topping as String
     * @param isVegan is vegan friendly or not
     */
    private Topping(String name, boolean isVegan) {
        this.name = name;
        this.isVegan = isVegan;
    }

    /**
     * Create topping
     *
     * @param name  Name of topping (String)
     * @param isVegan   Is topping vegan friendly
     * @throws IllegalArgumentException if name is null or existed or blank
     */
    public static void createTopping(String name, boolean isVegan)
            throws IllegalArgumentException {
        boolean isNew = true;

        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException();
        }

        String newTopping = name.toUpperCase();
        //check if topping existed
        if (createdTopping.size() > 0) {
            for (Topping created : createdTopping) {
                if (created.toString().equals(newTopping)) {
                    isNew = false; //false if there is a topping with this name
                    throw new IllegalArgumentException();
                }
            }
        }
        if (isNew) {
            //add new topping to list, construct new topping
            createdTopping.add(new Topping(newTopping, isVegan));
        }
    }

    /**
     * Get the topping with specified name
     *
     * @param name  Name of topping want to get
     * @return  The Topping with the specifed name
     * @throws IllegalArgumentException if the name does not exist
     * @throws NullPointerException if the name is null
     */
    public static Topping valueOf(String name)
            throws IllegalArgumentException, NullPointerException {

        if (name == null) {
            throw new NullPointerException();
        }

        //get the topping with same name
        Topping getTopping = null;
        for (Topping topping : createdTopping) {
            if (Objects.equals(topping.toString(), name)) {
                //System.out.println("Match");
                getTopping = topping;
                break;
            }
        }

        if (getTopping == null) {
            throw new IllegalArgumentException();
        }

        return getTopping;
    }

    /**
     * Array containing all created toppings
     *
     * @return Array containing for created toppings
     */
    public static Topping[] values() {
        existedTopping = createdTopping.toArray(Topping.existedTopping);
        return existedTopping;
    }

    /**
     * Delete all created toppings
     */
    public static void resetToppings() {
        createdTopping.clear();
        existedTopping = new Topping[createdTopping.size()];
    }

    /**
     * Human-readable for a topping which is its name
     *
     * @return The string representing the topping
     */
    public String toString() {
        return this.name;
    }

    /**
     * if this is a vegan topping or not
     *
     * @return true if this is a vegan friendly topping. false if not
     */
    public boolean isVegan() {
        return this.isVegan;
    }


}
