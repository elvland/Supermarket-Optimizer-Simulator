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
 * The GatherEvent class represents an event in the supermarket simulation where a customer gathers items for purchase.
 */

public class GatherEvent extends Event {
    private Customer customer;

    public GatherEvent(double eventTime, EventQueue queue, Customer customer) {
        super(eventTime, queue);
        this.customer = customer;
    }

    @Override
    public void execute(State s) {
        var state = (SupermarketState) s;

        state.info(this,this.customer.getCustomerID());


        if(state.hasFreeChckout()){ // Checks if free Checkout available

            state.useCheckout(); //Removes a cashout slot / cashout queue is empty

            double paymentTime = state.getPayTime(super.getCalculatedTime());
            PayEvent payEvent = new PayEvent(paymentTime, queue, this.customer);

            queue.add(payEvent);

        } else { //If all Cashiers are occupied add customer to Cashout queue

            state.addCustomerQueue(this.customer);

        }
    }

    public  String toString(){
        return "Plock";
    }
}
