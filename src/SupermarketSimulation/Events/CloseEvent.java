package SupermarketSimulation.Events;

import SimulationLibrary.EventQueue;
import SimulationLibrary.Events.Event;
import SimulationLibrary.State.State;
import SupermarketSimulation.SupermarketState.SupermarketState;
/**
 * @author Anton Alexandersson
 * @author Olle Elvland
 * @author Lukas Eriksson
 * @author Vincent Gustafsson
 *
 * The CloseEvent class represents the event of closing a supermarket simulation.
 */

public class CloseEvent extends Event {
        private double closetime;


        public CloseEvent(double eventTime, EventQueue queue) {
            super(eventTime, queue);
            this.closetime = eventTime;
        }


    @Override
    public void execute(State s) {
        var state = (SupermarketState) s;
        state.info(this,-1);
        state.setCloseTime(this.closetime);
        state.changeStoreStatus();
    }

    public  String toString(){
        return "Stängning ";
    }

}
