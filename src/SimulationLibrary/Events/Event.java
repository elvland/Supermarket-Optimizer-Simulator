package SimulationLibrary.Events;

import SimulationLibrary.State.State;
import SimulationLibrary.EventQueue;
/**
 * @author Anton Alexandersson
 * @author Olle Elvland
 * @author Lukas Eriksson
 * @author Vincent Gustafsson
 *
 * The Event class represents an abstract event in the simulation.
 */

public abstract class Event {
    protected EventQueue queue;
    private double calculatedTime;

    public Event (double eventTime, EventQueue queue){
        this.calculatedTime = eventTime;
        this.queue = queue;
    }

    public double getCalculatedTime() {
        return this.calculatedTime;
    }


    public abstract void execute(State s);
}
