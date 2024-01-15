package pizza.ingredients;

/**
 * Base interface for managing the types of base sizes available
 */
public interface Bases {
    /**
     * Enum for Pizza base size
     */
    public enum BaseSize {
        /**
         * A small pizza.
         */
        SMALL(3),

        /**
         * A average sized pizza.
         */
        MEDIUM(5),

        /**
         * A large pizza.
         */
        LARGE(7);

        /**
         * Price of the pizza
         */
        public final double price;

        /**
         * BaseSize constructor
         * @param price     The price of the base
         */
        BaseSize(double price) {
            this.price = price;
        }

        /**
         * Get the price of the pizza
         *
         * @return the double number of pizza prince
         */
        public double getPrice() {
            return this.price;
        }

    }

    /**
     * set the base to specified size
     * @param size The size
     */
    void set(Bases.BaseSize size);

}
