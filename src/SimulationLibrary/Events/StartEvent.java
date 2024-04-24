package SimulationLibrary.Events;

import SimulationLibrary.State.State;
import SimulationLibrary.EventQueue;
/**
 * @author Anton Alexandersson
 * @author Olle Elvland
 * @author Lukas Eriksson
 * @author Vincent Gustafsson
 *
 * The StartEvent class represents an event that starts the simulation.
 */
public class StartEvent extends Event{
    public StartEvent(double eventTime, EventQueue queue) {
        super(eventTime, queue);
    }

    @Override
    public void execute(State s){ s.informObservers(); }
    public String toString(){
        return "Start";
    }
}
