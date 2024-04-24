package SupermarketSimulation.Extra;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Queue;
/**
 * @author Anton Alexandersson
 * @author Olle Elvland
 * @author Lukas Eriksson
 * @author Vincent Gustafsson
 *
 * The FIFIOqueue class represents a First-In-First-Out (FIFO) queue of customers in the supermarket simulation.
 */

public class FIFIOqueue  {

    ArrayList<Customer> customers;

    public FIFIOqueue(){
        this.customers =new ArrayList<>();
    }

    public Customer getFirstInLine(){
        return customers.remove(0);
    }

    public boolean isQueueEmpty(){
        return customers.isEmpty();
    }

    public void läggtill(Customer c){
        customers.add(c);
    }

    public int queueSize(){
        return customers.size();
    }
    public String toString() {
        StringBuilder str = new StringBuilder();
        if(customers.isEmpty()){
            str.append("[").append("]");
        }
        for (Customer elem : customers) {
            str.append("[").append((elem.getCustomerID())).append("]");
        }
        return str.toString();
    }
}
