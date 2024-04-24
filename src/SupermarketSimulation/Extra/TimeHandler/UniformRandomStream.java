package SupermarketSimulation.Extra.TimeHandler;

import java.util.Random;

/**
 * @author Anton Alexandersson
 * @author Olle Elvland
 * @author Lukas Eriksson
 * @author Vincent Gustafsson
 */

/**
 * The UniformRandomStream class generates random numbers from a uniform distribution within a specified range.
 */

public class UniformRandomStream {

	private Random rand;
	private double lower, width, upper;

	public UniformRandomStream(double lower, double upper, long seed) {
		rand = new Random(seed);
		this.lower = lower;
		this.width = upper-lower;
		this.upper = upper;
	}


	public double next() {
		return lower+rand.nextDouble()*width;
	}
}

