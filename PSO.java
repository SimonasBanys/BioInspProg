package package1;

import java.util.ArrayList;

/*
 * Max error needs to be implemented to operate alongside max_iterations
 * Error needs to actually be calculated
 * Fitness function must be implemented
 * Must figure out how to apply population of particles to a problem, where
 * 		does each particle go? Which weight does each focus on? Is it shared?
 * Must output new weights, designed by the fitness functions, 
 * 		as a vector the same size as the input vector.
 * 
 * Implement termination criteria
 * 
 * Apply uniform distribution to the particles to different values in the 
 * 		input vector.
 * For each particle:
 * 	Set particle's best known position as initial position.
 * 	Update global best known position if required
 * 	Apply a velocity
 * While termination criteria isn't met:
 * 	for each particle:
 * 		Update particle velocity using formula
 * 		Update particle position
 * 		Update particle best known position if required
 * 		Update global best known position if required
 * 
 * 
 * Updating best known positions requires use of the fitness function.
 * Upper and lower boundaries must also be present and respected.
 * 
 * Fitness function:
 * A fitness function accepts a list of doubles, and returns the fitness
 * 	of the result. The input of doubles is the weight values for the associated
 * 	ANN. Upon inputting these into the fitness function, it should apply
 * 	the weights to an ANN, using the 
 * 
 *  */
	
public class PSO {

	// Hyper Parameters
	public int swarm_size;
	public float velocity_weight;
	public float personal_best_weight;
	public float informant_best_weight;
	public float global_best_weight;
	public float step_size;
	public int max_informants;
	public int max_iterations;
	// public double max_error;
	public ArrayList<Particle> particles;
	
	public PSO() {
		
		// Apply typical values to hyper parameters
		swarm_size = 50;
		velocity_weight = 1;
		personal_best_weight = 1;
		informant_best_weight = 1;
		global_best_weight = 1;
		step_size = 1;
		max_informants = (int) Math.sqrt(swarm_size);
		
		// Create ArrayList to store every particle created
		particles = new ArrayList<Particle>();
		
	}
	
	public static void main(String[] args) {
		
		PSO pso = new PSO();
		input_vector = [] // This should be taken from the ANN instead
		populate(pso.swarm_size, pso.particles); // Create particles
		
		int global_best = 0;
		// Run PSO
		for (Particle p : pso.particles) {
			
			System.out.println(p.weight);
			
		}
		
	}
	
	public static void populate(int length, ArrayList<Particle> populace) {
		
		for (int i = 0 ; i < length ; i++) {
			populace.add(new Particle());
		}
		
	}
	
}
