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
 * The ArriveEvent class represents an event in the supermarket simulation where a customer arrives at the store.
 */

public class ArriveEvent extends Event {
    private Customer customer;
    public ArriveEvent(double eventTime, EventQueue queue, Customer customer) {
        super(eventTime, queue);
        this.customer = customer;
    }

    @Override
    public void execute(State s) {
        var state = (SupermarketState) s;
        state.info(this,this.customer.getCustomerID());

        if(state.isStoreOpen()){ //Checks if store open

            if(state.isStoreFree()){ //Checks if Store is free for customer to entry

                state.addCustomerInStore();

                double gatherItemsTime =  state.getGatherTime(super.getCalculatedTime()) ;
                // Creates a GatherEvent and adds it to the event queue
                GatherEvent gatherEvent = new GatherEvent(gatherItemsTime, queue, this.customer);

                queue.add(gatherEvent);
            } else{
                state.addRejectedCustomer(); // STORE IS OPEN BUT FULL = REJECTED
            }

            double arriveTime = state.getArriveTime(super.getCalculatedTime());
            // Creates an ArriveEvent for the next customer and adds it to the event queue
            ArriveEvent arrivalEvent = new ArriveEvent(arriveTime, queue, state.createNewCustomer());

            queue.add(arrivalEvent);
        } else {
            //Store is closed sry bro,do nothing

        }


    }

    public  String toString(){
        return "Ankomst";
    }

}
