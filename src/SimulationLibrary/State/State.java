package SimulationLibrary.State;

import java.util.Observable;
/**
 * @author Anton Alexandersson
 * @author Olle Elvland
 * @author Lukas Eriksson
 * @author Vincent Gustafsson
 *
 * The State class represents the state of the simulation. It extends the Observable class
 * to allow view observers to be notified of state changes.
 */


public abstract class State extends Observable {
    private double currentCalculatedTime;
    private boolean stopSimulator = false;

    public void stopSimulation(){
        this.stopSimulator = true;
    }

    public boolean isSimulatorRunning()
    {
        return !this.stopSimulator;
    }

    /**
     * Notify observers with updated state
     */
    public void informObservers(){
        setChanged();
        notifyObservers(this);
    }

    protected void setTime(double time) {
        if(time >= this.currentCalculatedTime) {
            this.currentCalculatedTime = time;
        }
    }
}
