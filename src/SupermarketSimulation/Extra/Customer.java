package SupermarketSimulation.Extra;
/**
 * @author Anton Alexandersson
 * @author Olle Elvland
 * @author Lukas Eriksson
 * @author Vincent Gustafsson
 *
 * The Customer class represents a customer in the supermarket simulation.
 * It stores a unique customer ID.
 */

public class Customer {
    private int customerID;

    public Customer(int idNum){

        this.customerID = idNum;
    }

    public int getCustomerID(){
        return customerID;
    }

}
