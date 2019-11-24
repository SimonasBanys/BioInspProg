  
package package1;

import java.util.ArrayList;

public class Particle {

	// Particle will store 1 position and velocity value for every single dimension in the PSO
	ArrayList<Double> positions;
	ArrayList<Double> velocities;
	ArrayList<Double> best_positions;
	
	// Must also keep a list of every informant for the current particle
	ArrayList<Particle> informants;
	
	public Particle(int min_position, int max_position) {
		
		// Positions and Velocities are randomly assigned
		
		
	}
	
}