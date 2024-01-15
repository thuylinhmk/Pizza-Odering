package pizza.ingredients;

import org.junit.Before;
import org.junit.Test;
import pizza.ingredients.Topping;

import javax.swing.plaf.basic.BasicDesktopIconUI;

import java.util.Arrays;

import static org.junit.Assert.*;

public class ToppingTest {

    @Before
    public void constructTopping() {
        Topping.resetToppings();

        Topping.createTopping("bacon", false);
        Topping.createTopping("mushroom", true);
    }


    @Test
    public void testConstructor() {
        //create new topping and call from valueOf()
        try {
            Topping.createTopping("prawn", false);
            Topping.valueOf("PRAWN");
        } catch (IllegalArgumentException e) {
            fail("fail to create non-existing topping");
        }

        //create pre-existed topping same name same isVegan-> throw IllegalArgumentException
        try {
            Topping.createTopping("bacon", false);
            fail("Same name, same isVegan");
        } catch (Exception e1) {
            assertEquals(e1.getClass().getSimpleName(), "IllegalArgumentException");
        }


        //same name but different is vegan
        try {
            Topping.createTopping("bacon", true);
            fail("fail different isVegan but same name");
        } catch (Exception e2) {
            assertEquals(e2.getClass().getSimpleName(), "IllegalArgumentException");
        }

        //create topping with null name
        try {
            String name = null;
            Topping.createTopping(name, true);
            fail("create with null name");
        } catch (Exception e3) {
            assertEquals(e3.getClass().getSimpleName(), "IllegalArgumentException");
        }

        //create with empty name
        try {
            Topping.createTopping(" ", true);
            fail("Fail: Add exited topping");
        } catch (Exception e4) {
            assertEquals(e4.getClass().getSimpleName(), "IllegalArgumentException");
        }
    }

    @Test
    public void testToString() {
        try {
            Topping bacon = Topping.valueOf("BACON");
            assertEquals(bacon.toString(), "BACON");
        } catch (Exception anyException1) {
            //System.out.println(anyException1);
        }
    }

    @Test
    public void testValueOf() {
        //existing topping
        try {
            Topping bacon = Topping.valueOf("BACON");
            assertEquals(bacon.toString(), "BACON");
            assertTrue(bacon instanceof Topping);
        } catch (Exception e) {
            //System.out.println(e);
        }

        //non exist topping -> illegalargumentexception
        try {
            Topping prawn = Topping.valueOf("PRAWN");
            fail("Fail test value of non existing Topping");
        } catch (IllegalArgumentException e4) {
            //System.out.println("pass non-exist topping valueOf");
        }

        //name is null -> nullpointexception
        try {
            Topping.valueOf(null);
            fail("Fail pass the test valueof with name = null");
        } catch (NullPointerException e) {
            //System.out.println("pass null topping valueOf");
        }
    }

    @Test
    public void testValues() {
        try {
            Topping bacon = Topping.valueOf("BACON");
            Topping mushroom = Topping.valueOf("MUSHROOM");
            Topping[] listExistingTopping = {bacon, mushroom};

            assertEquals(listExistingTopping, Topping.values());
        } catch (Exception anyException1) {
            fail("Fail the values() test");
        }
    }

    @Test
    public void testResetTopping() {
        Topping.resetToppings();
        assertEquals(Topping.values().length, 0);
    }

    @Test
    public void testIsVegan() {
        assertFalse(Topping.valueOf("BACON").isVegan());
        assertTrue(Topping.valueOf("MUSHROOM").isVegan());
    }

}
