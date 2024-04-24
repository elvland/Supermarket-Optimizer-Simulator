package SupermarketSimulation.Events;

import SimulationLibrary.Events.StartEvent;
import SimulationLibrary.State.State;
import SimulationLibrary.EventQueue;
import SupermarketSimulation.Extra.Customer;
import SupermarketSimulation.SupermarketState.SupermarketState;
/**
 * @author Anton Alexandersson
 * @author Olle Elvland
 * @author Lukas Eriksson
 * @author Vincent Gustafsson
 *
 * The OpenEvent class represents the event of opening a supermarket simulation.
 * It initializes the simulation by creating the first customer and scheduling their arrival.
 */

public class OpenEvent extends StartEvent {
    private Customer customer;

    public OpenEvent(EventQueue queue) {
        super(0, queue);
    }

    @Override
    public void execute(State s) {
        var state = (SupermarketState) s;
        //Creates first Customer
        this.customer = state.createNewCustomer();

        // Provides information about the event to the state
        state.info(this,this.customer.getCustomerID());


        double arriveStartTime = state.getArriveTime(super.getCalculatedTime());

        queue.add(new ArriveEvent(arriveStartTime, queue, customer));
    }

    public  String toString(){
        return "Start" ;
    }
}
