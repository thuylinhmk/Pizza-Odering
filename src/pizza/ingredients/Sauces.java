package pizza.ingredients;

/**
 * Interface of sauces
 */
public interface Sauces {

    /**
     * Enum for sauce can be put on pizza
     */
    static enum Sauce {
        /**
         * Tomato.
         */
        TOMATO,

        /**
         * BBQ.
         */
        BBQ,

        /**
         * Garlic.
         */
        GARLIC,

        /**
         * No sauce on pizza.
         */
        NONE
    }

    /**
     * Set sauce to the specified sauce
     * @param sauce The sauce you want to add on your pizza
     */
    void set(Sauces.Sauce sauce);
}
