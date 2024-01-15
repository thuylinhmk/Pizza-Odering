package pizza.ingredients;

/**
 * Interface representing cheese to add on pizza
 */
public interface Cheeses {
    /**
     * Enum of cheese types can be put on pizza
     */
    static enum Cheese {
        /**
         * Mozzarella cheese type.
         */
        MOZZARELLA,

        /**
         * Vegan cheese type.
         */
        VEGAN,

        /**
         * No cheese on pizza.
         */
        NONE
    }

    /**
     * Set cheese to specified cheese
     * @param cheese The cheese you want to add
     */
    void set(Cheeses.Cheese cheese);
}
