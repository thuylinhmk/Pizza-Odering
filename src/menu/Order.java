package menu;

import pizza.Pizza;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Class for ordering pizzas
 */
public class Order extends Object {
    /**
     * 10% discount
     */
    public static final MenuItem.Discount DISCOUNT_10 = price -> 0.9 * price;

    /**
     * 25% discount
     */
    public static final MenuItem.Discount DISCOUNT_25 = price -> 0.75 * price;

    /**
     * Name of order (name of customer).
     */
    private String name;

    /**
     * Unique random uuid number/series.
     */
    private UUID uuid;

    /**
     * Date of order.
     */
    private LocalDate date;

    /**
     * Time of order.
     */
    private LocalTime time;

    /**
     * List of order.
     */
    private List<Pizza> orderList;

    /**
     * Original price
     */
    private double payment;

    /**
     * price after discount applied
     */
    private double discountPayment;

    /**
     * Constructor of Order class
     */
    public Order() {
        this.setName("Not Given");
        this.setUUID(UUID.randomUUID());
        this.setDate(LocalDate.now());
        this.setTime(LocalTime.now());
        this.orderList = new ArrayList<Pizza>();
    }

    /**
     * Change the name of the order
     * @param name The name wanting to change for this order
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Change the UUID of this order
     * @param uuid the UUID wanting to change into
     */
    public void setUUID(UUID uuid) {
        this.uuid = uuid;
    }

    /**
     * Set the date of this order
     * @param date The date in format of YYYY-MM-dd
     */
    public void setDate(LocalDate date) {
        this.date = LocalDate.parse(date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
    }

    /**
     * Set the time for this order
     * @param time The time in format HH-mm
     */
    public void setTime(LocalTime time) {
        this.time = LocalTime.parse(time.format(DateTimeFormatter.ofPattern("HH:mm")));
    }

    /**
     * Add pizza to this order
     * @param pizza Pizza wanted to add
     */
    public void add(Pizza pizza) {
        this.orderList.add(pizza);

        this.payment = 0;
        this.discountPayment = 0;

        //calculate the price for this order
        for (int pizzaNumb = 0; pizzaNumb < this.orderList.size(); pizzaNumb++) {
            this.payment += this.orderList.get(pizzaNumb).getTotalPrice();
        }

        //check if discount term is valid
        if (this.orderList.size() >= 6) {
            this.discountPayment = DISCOUNT_25.applyDiscount(this.payment);
        } else if (this.orderList.size() >= 3) {
            this.discountPayment = DISCOUNT_10.applyDiscount(this.payment);
        }
    }


    /**
     * A Human-readable string representation of this order with information about this order
     * @return The string representing of this order
     */
    public String toString() {
        String toPrint = "Date: " + this.date + System.lineSeparator()
                + "Time: " + this.time + System.lineSeparator()
                + "Customer: " + this.name + System.lineSeparator()
                + "Order number: " + this.uuid.toString() + System.lineSeparator()
                + "Order:";
        String orderPrint = System.lineSeparator();
        int orderNumb = 1;
        //get order if they have order
        if (this.orderList.size() > 0) {
            for (Pizza order : this.orderList) {
                orderPrint += orderNumb + " - " + order.toString() + System.lineSeparator();
                orderNumb += 1;
            }
        }

        //price of this order
        String priceToPay = "";
        toPrint += orderPrint + System.lineSeparator();
        if (this.discountPayment == 0) {
            priceToPay = "Total: $" + String.format("%.2f", this.payment);
        } else {
            priceToPay = "Multi item discount applied of $" + String.format("%.2f", this.payment)
                    + " applied, new Total: $" + String.format("%.2f", this.discountPayment);
        }

        toPrint += priceToPay + System.lineSeparator();

        return toPrint;
    }
}
