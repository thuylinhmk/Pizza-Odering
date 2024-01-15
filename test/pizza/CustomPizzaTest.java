package pizza;

import exceptions.TooManyToppingsException;
import menu.CustomerOrder;
import org.junit.*;
import pizza.ingredients.Bases;
import pizza.ingredients.Cheeses;
import pizza.ingredients.Sauces;
import pizza.ingredients.Topping;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;


public class CustomPizzaTest {
    private CustomPizza cusPizNoTopping, testingPizza, testingPizza2;
    private List<Topping> threeToppings, fourToppings, fourToppingsDifOder;

    @Before
    public void constructPizza() {
        //Initialize testing variable. Custom Pizza without any topping
        cusPizNoTopping = new CustomPizza();
        testingPizza = new CustomPizza();
        testingPizza2 = new CustomPizza(Bases.BaseSize.LARGE, Sauces.Sauce.GARLIC,
                Cheeses.Cheese.VEGAN);

        //clear toppings for new test//
        Topping.resetToppings();

        //Create topping
        Topping.createTopping("BACON", false);
        Topping.createTopping("MUSHROOM", true);
        Topping.createTopping("SALMON", false);
        Topping.createTopping("OLIVES", true);
        Topping.createTopping("ONIONS", true);
        Topping.createTopping("CHICKEN", false);

        //Create topping list
        threeToppings = Arrays.asList(Topping.valueOf("MUSHROOM"),
                Topping.valueOf("OLIVES"), Topping.valueOf("SALMON"));

        fourToppings = Arrays.asList(Topping.valueOf("BACON"),
                Topping.valueOf("MUSHROOM"), Topping.valueOf("OLIVES"),
                Topping.valueOf("SALMON"));

        fourToppingsDifOder = Arrays.asList(Topping.valueOf("BACON"),
                Topping.valueOf("OLIVES"), Topping.valueOf("SALMON"),
                Topping.valueOf("MUSHROOM"));

    }


    @Test
    public void constructorTest() {
        //construct a default pizza
        CustomPizza defaultPizza = new CustomPizza();
        String defaultString = "Custom Pizza: is a 'MEDIUM' sized base with 'TOMATO' " +
                "sauce and 'MOZZARELLA' cheese $5.00";
        assertEquals(defaultPizza.toString(), defaultString);
        assertEquals(defaultPizza.getToppings().size(), 0);

        // base is null -> throw IllegalArgumentException
        try {
            CustomPizza noSize = new CustomPizza(null, Sauces.Sauce.TOMATO,
                    Cheeses.Cheese.MOZZARELLA);
        } catch (Exception nullSize) {
            assertEquals(nullSize.getClass().getSimpleName(), "IllegalArgumentException");
        }

        //sauce is null
        try {
            CustomPizza noSauce = new CustomPizza(Bases.BaseSize.MEDIUM, null,
                    Cheeses.Cheese.VEGAN);
        } catch (Exception nullSauce) {
            assertEquals(nullSauce.getClass().getSimpleName(), "IllegalArgumentException");
        }

        //cheese is null
        try {
            CustomPizza noChesse = new CustomPizza(Bases.BaseSize.MEDIUM, Sauces.Sauce.TOMATO,
                    null);
        } catch (Exception nullCheese) {
            assertEquals(nullCheese.getClass().getSimpleName(), "IllegalArgumentException");
        }

        // correct different options
        try {
            CustomPizza testing3 = new CustomPizza(Bases.BaseSize.LARGE, Sauces.Sauce.GARLIC,
                    Cheeses.Cheese.MOZZARELLA);
            String testing3String = "Custom Pizza: is a 'LARGE' sized base with 'GARLIC' " +
                    "sauce and 'MOZZARELLA' cheese $7.00";
            assertEquals(testing3.toString(), testing3String);
        } catch (Exception e2) {
            fail("Fail to construct with correct arguments");
        }
    }

    //test add() topping function
    @Test
    public void testAddToppings() {
        //add 3 toppings as list in no topping pizza, getting topping will be called
        try {
            cusPizNoTopping.add(threeToppings);
            assertEquals(cusPizNoTopping.getToppings(), threeToppings);
        } catch (TooManyToppingsException e) {
            fail("Fail adding 3 topping onto empty pizza");
        }

        //add 1 topping
        try {
            cusPizNoTopping.add(Topping.valueOf("BACON"));
            assertTrue(cusPizNoTopping.getToppings().containsAll(fourToppings));
            assertTrue(fourToppings.containsAll(cusPizNoTopping.getToppings()));
        }catch (TooManyToppingsException e1) {
            fail("fail to add 1 topping in available pizza");
        }

        //add null list topping
        try {
            List<Topping> nullTopping = null;
            cusPizNoTopping.add(nullTopping);
            fail("Fail null list adding test");
        } catch (Exception nullList) {
            assertEquals(nullList.getClass().getSimpleName(), "IllegalArgumentException");
        }

        // add null topping
        try {
            Topping nullTopping = null;
            cusPizNoTopping.add(nullTopping);
            fail("Fail test add a null topping");
        } catch (Exception nullTopping) {
            assertEquals(nullTopping.getClass().getSimpleName(), "IllegalArgumentException");
        }

        // add new list -> too much topping
        try {
            cusPizNoTopping.add(fourToppings);
            fail("Fail: add a list causing number of topping > 5");
        } catch (TooManyToppingsException e3) {
            assertEquals(cusPizNoTopping.getToppings().size(), 4);
        }

        //add excessive topping
        try {
            cusPizNoTopping.add(Topping.valueOf("BACON"));
            cusPizNoTopping.add(Topping.valueOf("ONIONS"));
            fail("Fail test: adding too much topping");
        } catch (TooManyToppingsException e2) {}
    }

    @Test
    public void testGetToppings() {
        try {
            cusPizNoTopping.add(threeToppings);
            assertEquals(cusPizNoTopping.getToppings(), threeToppings);
        } catch (TooManyToppingsException e) {}
    }

    @Test
    public void testRemove() {
        try {
            cusPizNoTopping.add(fourToppings);
        } catch (TooManyToppingsException e) {}
        cusPizNoTopping.remove(Topping.valueOf("BACON"));
        assertEquals(cusPizNoTopping.getToppings(), threeToppings);
    }

    @Test
    public void testSetAndGetName() {
        cusPizNoTopping.setName("Miki");
        assertEquals(cusPizNoTopping.getName(), "Miki");
    }

    @Test
    public void testHashCode() {
        //without topping, same size, sauce, cheese
        assertEquals(cusPizNoTopping.hashCode(), testingPizza.hashCode());

        //without topping, different in size, sauce, cheese
        assertFalse(cusPizNoTopping.hashCode() == testingPizza2.hashCode());

        //with toppings of different order
        try {
            cusPizNoTopping.add(fourToppings);
            testingPizza.add(fourToppingsDifOder);
            assertEquals(cusPizNoTopping.hashCode(), testingPizza.hashCode());
        } catch (TooManyToppingsException e) {}

        //different toppings
        try {
            cusPizNoTopping.add(Topping.valueOf("ONIONS"));
            assertFalse(cusPizNoTopping.hashCode() == testingPizza.hashCode());
        } catch (TooManyToppingsException e) {}

    }

    @Test
    public void testEquals() {
        //same size, sauce, cheese
        assertEquals(testingPizza, cusPizNoTopping);

        // different size, base, cheese
        assertNotEquals(testingPizza, testingPizza2);

        // same size, sauce, cheese but different order of toppings
        try {
            cusPizNoTopping.add(fourToppings);
            testingPizza.add(fourToppingsDifOder);
            assertEquals(cusPizNoTopping, testingPizza);
        } catch (TooManyToppingsException e) {}

    }

    @Test
    public void testGetTotalPrice() {
        //no topping
        assertTrue(((cusPizNoTopping.getTotalPrice() - 5) == 0));

        //with topping
        try {
            cusPizNoTopping.add(threeToppings);
            assertTrue(((cusPizNoTopping.getTotalPrice() - (5 + 3*2)) == 0));
        } catch (TooManyToppingsException e) {}
    }

    @Test
    public void testToString() {
        String expectedNoTopping = "Custom Pizza: is a 'MEDIUM' sized base with 'TOMATO' " +
                "sauce and 'MOZZARELLA' cheese $5.00";
        //no topping
        assertEquals(cusPizNoTopping.toString(), expectedNoTopping);

        //with topping
        try {
            cusPizNoTopping.add(threeToppings);
        } catch (TooManyToppingsException e) {}

        String expectedToppings = "Custom Pizza: is a 'MEDIUM' sized base with 'TOMATO' " +
                "sauce and 'MOZZARELLA' cheese - Toppings: [MUSHROOM, OLIVES, SALMON] $11.00";
        assertEquals(cusPizNoTopping.toString(), expectedToppings);
    }
}
