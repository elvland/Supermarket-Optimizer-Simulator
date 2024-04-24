package SupermarketSimulation.Extra;


/**
 * @author Anton Alexandersson
 * @author Olle Elvland
 * @author Lukas Eriksson
 * @author Vincent Gustafsson
 *
 * The CustomerFactory class represents a factory for creating customer objects with unique IDs.
 */
public class CustomerFactory {
    private int customerID = 0;

    public Customer createCustomer(){
        Customer customer = new Customer(this.customerID);
        this.customerID++;

        return customer;
    }

}
