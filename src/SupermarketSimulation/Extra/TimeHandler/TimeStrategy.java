package SupermarketSimulation.Extra.TimeHandler;
/**
 * @author Anton Alexandersson
 * @author Olle Elvland
 * @author Lukas Eriksson
 * @author Vincent Gustafsson
 */
/**
 * The TimeStrategy interface defines methods for calculating event times based on different time handling types.
 */

public interface TimeStrategy {
     double finishEventTime(double currentTime);
     Object getEventType();
}
