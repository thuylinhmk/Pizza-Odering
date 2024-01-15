package io;

import exceptions.PizzaFormatException;
import exceptions.TooManyToppingsException;
import menu.Menu;
import menu.MenuItem;
import pizza.MenuPizza;

import pizza.ingredients.Bases;
import pizza.ingredients.Cheeses;
import pizza.ingredients.Sauces;
import pizza.ingredients.Topping;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.List;

/**
 * Class to load text file contain Pizza menu
 */
public class MenuLoader {
    /**
     * default PATH to text file
     */
    public static final String PATH = "./src/assets/";

    /**
     * File reader variable
     */
    private static FileReader file;

    /**
     * BufferedReader variable
     */
    private static BufferedReader menuRead;

    /**
     * Represent FileNotFoundException
     */
    protected static final int COULD_NOT_OPEN_FILE = 1;

    /**
     * Represent PizzaFormatException
     */
    protected static final int FILE_FORMAT_ERROR = 2;

    /**
     * Represent TooManyToppingsException
     */
    protected static final int TOO_MANY_TOPPINGS = 4;

    /**
     * Represent IndexOutOfBoundsException
     */
    protected static final int MISSING_NUMBER_OF_PIZZAS = 5;

    /**
     * Represent IOException
     */
    protected static final int CANNOT_READ_LINE = 6;

    //public MenuLoader() {};

    /**
     * Load text file contains pizza menu
     *
     * @param filename name of text file want to load
     * @return menu object contains pizzas
     */
    public static Menu load(String filename) {
        Topping.resetToppings();
        Menu menu = Menu.getInstance();
        try {
            file = new FileReader(PATH + filename);
            menuRead = new BufferedReader(file);
            menu = getMenu(menuRead);
        } catch (FileNotFoundException e1) {
            System.out.println(COULD_NOT_OPEN_FILE);
        } catch (PizzaFormatException | TooManyToppingsException | IOException
                 | IndexOutOfBoundsException e2) {
            switch (e2.getClass().getSimpleName()) {
                case "PizzaFormatException":
                    System.out.println(FILE_FORMAT_ERROR);
                    break;
                case "TooManyToppingsException":
                    System.out.println(TOO_MANY_TOPPINGS);
                    break;
                case "IOException":
                    System.out.println(CANNOT_READ_LINE);
                    break;
                case "IndexOutOfBoundsException":
                    System.out.println(MISSING_NUMBER_OF_PIZZAS);
            }
        }
        return menu;
    }

    /**
     * Read the text file and load information to menu
     *
     * @param reader the file wanting to read
     * @return The menu with information in the text file
     * @throws PizzaFormatException      If pizza not in correct format
     * @throws TooManyToppingsException  If there are too many topping
     * @throws IOException               If cannot read the file
     * @throws IndexOutOfBoundsException If number of pizza lines more than number of
     *                                   pizza declared in the start
     */
    public static Menu getMenu(BufferedReader reader)
            throws PizzaFormatException, TooManyToppingsException,
            IOException, IndexOutOfBoundsException {
        //null reader
        if (reader == null) {
            throw new PizzaFormatException("File is null", 0);
        }
        //menuitem list
        List<MenuItem> listItems = new ArrayList<>();
        //menu
        Menu menu = Menu.getInstance();
        //mapp with pizza name and list of its topping
        Map<String, List<Topping>> listPizzaMenu = new HashMap<String, List<Topping>>();
        //list of vegan and meat topping
        List<String> veganToppings = new ArrayList<>();
        List<String> meatToppings = new ArrayList<>();
        try {

            String line = reader.readLine();
            int lineNum = 1; //keep track of line number
            int numberOfPizza = 0;

            if (line == null) {
                throw new PizzaFormatException("Empty file", 0);
            }
            //read line in file
            while (line != null) {
                if (lineNum == 1) { //first line of text file
                    if (line.startsWith("PizzaMenu ")) {
                        String[] content = line.split("\\s+");
                        try {
                            //get number of pizza in this menu file
                            numberOfPizza = Integer.parseInt(content[1]);
                        } catch (Exception e) {
                            throw new PizzaFormatException("Cannot read number of pizza", lineNum);
                        }
                    } else {
                        throw new PizzaFormatException("Wrong PizzaMenu", lineNum);
                    }
                } else if (lineNum == 2 || lineNum == 5) { //empty line to separate information
                    if (!line.trim().isEmpty()) {
                        throw new PizzaFormatException("Line after PizzaMenu or "
                                + "toppings is not empty", lineNum);
                    }
                } else if (lineNum == 4) { //line 4 containing vegan toppings
                    //separate the name of topping by comma
                    veganToppings = Arrays.asList(line.split(", "));
                    for (String topping : veganToppings) {
                        Topping.createTopping(topping, true); //create toppings with names
                    }
                } else if (lineNum == 3) { // line 3 containing non-vegan topping
                    meatToppings = Arrays.asList(line.split(", "));
                    for (String topping : meatToppings) {
                        Topping.createTopping(topping, false);
                    }
                } else if (lineNum >= 6) { //the rest of line will be pizza line
                    //separate name of pizza and list of toppings
                    String[] pizzaContent = line.split("\\[|\\]");
                    String pizzaName = pizzaContent[0].trim();

                    List<String> pizzaTopping =
                            new ArrayList<String>(Arrays.asList(pizzaContent[1]
                                    .split(", ")));


                    //not too many topping
                    List<Topping> toppingList = new ArrayList<Topping>();
                    if (pizzaTopping.size() > 5) {
                        throw new TooManyToppingsException("Too many topping to add", lineNum);
                    } else {
                        //valid topping
                        for (String topping : pizzaTopping) {
                            try {
                                String toppingName = topping.toUpperCase();
                                Topping toAdd = Topping.valueOf(toppingName);
                                //System.out.println(toAdd);
                                toppingList.add(toAdd);
                            } catch (IllegalArgumentException invalidTopping) {
                                throw new PizzaFormatException("Invalid topping", lineNum);
                            }
                        }
                    }
                    listPizzaMenu.put(pizzaName, toppingList); //add name and topping to map
                }
                //move to next line
                lineNum += 1;
                line = reader.readLine();
            }
            // check if valid number of pizza then create pizza
            if (listPizzaMenu.size() == numberOfPizza) {
                List<MenuPizza> menuPizzas = new ArrayList<MenuPizza>();
                for (Map.Entry<String, List<Topping>> pizza : listPizzaMenu.entrySet()) {
                    MenuPizza newPizza = new MenuPizza(Bases.BaseSize.MEDIUM,
                            Sauces.Sauce.TOMATO,
                            Cheeses.Cheese.MOZZARELLA,
                            pizza.getValue());
                    newPizza.setName(pizza.getKey());
                    menu.registerMenuItem(newPizza); //register to menu
                }
            } else {
                throw new IndexOutOfBoundsException();
            }
        } catch (IOException cannotRead) {
            throw new IOException();
        }
        return menu;
    }
}
