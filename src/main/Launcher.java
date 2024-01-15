package main;

import exceptions.TooManyToppingsException;
import gui.Display;
import io.MenuLoader;
import menu.CustomerOrder;
import menu.Order;
import pizza.CustomPizza;
import pizza.Pizza;
import pizza.ingredients.Bases;
import pizza.ingredients.Cheeses;
import pizza.ingredients.Sauces;
import pizza.ingredients.Topping;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Class to launcher order
 */
public class Launcher {

    /**
     * Launcher construction
     */
    public Launcher() {}

    /**
     * Main place to launch customer order/ GUI for ordering
     * @param args  Provide no usable function
     */
    public static void main(String[] args) {
        //Create topping
        Topping.resetToppings();
        Topping.createTopping("bacon", false);
        Topping.createTopping("mushroom", true);
        Topping.createTopping("olives", true);
        Topping.createTopping("prawn", false);
        Topping.createTopping("beef", false);
        Topping.createTopping("onions", true);

        menu.Menu menu = MenuLoader.load("PizzaMenu.txt");
        System.out.println(menu.getItems());

        //Default pizza
        Pizza margherita = new CustomPizza();
        List<Topping> fullTopping = List.of(Topping.valueOf("BACON"),
                Topping.valueOf("PRAWN"), Topping.valueOf("BEEF"),
                Topping.valueOf("OLIVES"));
        CustomPizza supreme = new CustomPizza(Bases.BaseSize.LARGE, Sauces.Sauce.TOMATO,
                                 Cheeses.Cheese.MOZZARELLA);
        try {
            supreme.add(fullTopping);
        } catch (TooManyToppingsException tooMuchTopping) {
            ;
        }

        //create a new Customer order
        CustomerOrder newCustomer = new CustomerOrder();
        System.out.println(newCustomer.toString());

        //try to get order
        try {
            Order customerOrder = newCustomer.createOrder();
            customerOrder.add(margherita);
            customerOrder.add(supreme);
        } catch (TooManyToppingsException addTooMuch) {
            //System.out.println("You try to add so many toppings!");
        }
        System.out.println(newCustomer.toString());

        //call GUI for ordering
        Display startOrderInterface = new Display();

    }
}
