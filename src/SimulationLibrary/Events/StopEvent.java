package SimulationLibrary.Events;

import SimulationLibrary.State.State;
import SimulationLibrary.EventQueue;

/**
 * @author Anton Alexandersson
 * @author Olle Elvland
 * @author Lukas Eriksson
 * @author Vincent Gustafsson
 *
 * The StopEvent class represents an event that stops the simulation.
 */

public class StopEvent extends Event{
    public StopEvent(EventQueue queue,int stopTime) {
        super(stopTime, queue);
    }

    @Override
    public void execute(State s){
        s.stopSimulation();
        s.informObservers();
    }

    public String toString(){
        return "STOP";
    }
}
