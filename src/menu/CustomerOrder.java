package menu;

import javax.swing.*;

import exceptions.TooManyToppingsException;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Class representing a customer order
 */
public class CustomerOrder extends Object {
    /**
     * new order
     */
    private Order newOrder;

    /**
     * GUI variable: Frame contains other GUI
     */
    private JFrame nameRequestFrame;

    /**
     * GUI variable: Button
     */
    private JButton doneButton;

    /**
     * GUI variable: Text field to prompt user input for name
     */
    private JTextField nameRequestPrompt;

    /**
     * GUI variable: Panel to put text field and button
     */
    private JPanel nameRequestPanel;

    /**
     * GUI label: "Please enter your name"
     */
    private JLabel nameRequestLabel;

    /**
     * Variable storing customer name
     */
    private String customerName;

    /**
     * Constructor of CustomerOrder without any parameters
     */
    public CustomerOrder() {
        this("No Name Yet");
    }

    /**
     * Constructor with the string of pseudo username call GUI to get user input for their name
     * @param customerName Pseudo name of customer
     */
    public CustomerOrder(String customerName) {
        try {
            this.customerName = requestName();
        } catch (InterruptedException e) {
            ;
        }
        try {
            createOrder();
        } catch (TooManyToppingsException e) {
            ;
        }
    }

    /**
     * GUI for getting user input for their name
     *
     * @return The string form of user input
     * @throws InterruptedException Handle the interrupt intention waiting for user input
     */
    protected String requestName() throws InterruptedException {
        if (SwingUtilities.isEventDispatchThread()) {
            throw new IllegalStateException();
        }
        ArrayBlockingQueue<String> userInput = new ArrayBlockingQueue<>(1);
        SwingUtilities.invokeLater(() -> {
            nameRequestFrame = new JFrame("Name of Customer");
            doneButton = new JButton("Done");
            nameRequestPrompt = new JTextField(20);
            nameRequestPanel = new JPanel();
            nameRequestLabel = new JLabel("Please Enter Your Name");

            //set up done button
            doneButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if ("Done".equals(e.getActionCommand())) {
                        userInput.offer(nameRequestPrompt.getText());
                        nameRequestFrame.dispose();
                    }
                }
            });

            //set up panel
            nameRequestPanel.add(nameRequestLabel, BorderLayout.NORTH);
            nameRequestPanel.add(nameRequestPrompt, BorderLayout.AFTER_LAST_LINE);
            nameRequestPanel.add(doneButton, BorderLayout.CENTER);

            //add panel to frame
            nameRequestFrame.add(nameRequestPanel, BorderLayout.CENTER);
            nameRequestFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            nameRequestFrame.setSize(300, 200);
            nameRequestFrame.setVisible(true);
        });

        return userInput.take();
    }

    /**
     * Create object Order for ordering pizzas
     *
     * @return new Order
     * @throws TooManyToppingsException If trying to add many toppings on a pizza
     */
    public Order createOrder() throws TooManyToppingsException {
        this.newOrder = new Order();
        if (!(this.customerName == null)) {
            this.newOrder.setName(this.customerName);
        }
        return newOrder;
    }

    /**
     * Human-readable representing for the order of this customer
     *
     * @return  The string with information about this order
     */
    public String toString() {
        String toPrint = "This is:" + System.lineSeparator()
                + "Customer Order {" + this.newOrder.toString() + "}";
        return toPrint;
    }
}
