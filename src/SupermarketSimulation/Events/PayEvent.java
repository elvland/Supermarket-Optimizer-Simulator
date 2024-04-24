package SupermarketSimulation.Events;

import SimulationLibrary.EventQueue;
import SimulationLibrary.Events.Event;
import SimulationLibrary.State.State;
import SupermarketSimulation.Extra.Customer;
import SupermarketSimulation.SupermarketState.SupermarketState;
/**
 * @author Anton Alexandersson
 * @author Olle Elvland
 * @author Lukas Eriksson
 * @author Vincent Gustafsson
 *
 * The PayEvent class represents an event in the supermarket simulation where a customer pays for their purchases.
 */

public class PayEvent extends Event {

    private Customer customer;

    public PayEvent(double eventTime, EventQueue queue, Customer customer) {
        super(eventTime, queue);
        this.customer = customer;
    }

    @Override
    public void execute(State s) {
        var state = (SupermarketState) s;

        state.info(this,customer.getCustomerID());

        state.removeCustomer(this.customer); //Customer leaves store
        state.addValidBuyer();


        if(state.hasCustomerInQueue()){ //Checks if theres a custoemr in the Cashout queue


            Customer nextInLine = state.nextInLine();

            double paymentTime = state.getPayTime( this.getCalculatedTime()) ;

            PayEvent payEvent = new PayEvent(paymentTime, queue, nextInLine);
            queue.add(payEvent);

        } else { // If a Checkout is free and waiting to get used
            state.freeCheckout();
        }
    }

    public  String toString(){
        return "Betalning";
    }
}

