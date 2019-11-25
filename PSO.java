package package1;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/*
 * MAIN THING TO NOTE:
 * PSO takes in a vector of weights as an input. This vector comes from the weights in the ANN.
 * Each weight in this vector represents a dimension. A value we must optimise. 
 * As such, each particle is in charge of attempting to optimise all of these simultaneously.
 * A single particle will have positions and velocities for every single dimension.
 * The objective of the PSO is to constantly update these positions and velocities for every particle.
 * When every particle is taking every dimension in different directions, they should be able to locate an optimum.
 * When 1 particle suggests a set of positions which has a satisfactory fitness, using these in the ANN
 * 	should result in the function being applied.
 * 
 */

public class PSO {

	// Function file name
	String data_set_file = "1in_cubic.txt";
	
	// Hyper Parameters for the PSO
	public int swarm_size = 50;				// How many particles are created
	public double velocity_weight = 1.0;		// Weighting given to the current velocity when calculating new positions
	public double personal_best_weight = 1.0;	// Weighting given to particle's previous best position when calculating new positions
	public double informant_best_weight = 1.0;	// Weighting given to the best informant's position when calculating new positions
	public double global_best_weight = 1.0;	// Weighting given to the best global position when calculating new positions
	public int step_size = 1;				// Step size for the PSO algorithm
	public int max_informants = 10;			// Max number of informants for each particle in PSO
	public int max_iterations = 100;			// Max number of PSO iterations 
	public double max_error = 0.01;			// Highest acceptable error level for the PSO
	
	// Hyper Parameters for the ANN
	public int num_layers = 4;
	public int num_nodes = 3;
	public int activation_function = 4;		// Activation function for the ANN
	public int dimensions;				// Number of parameters we're optimising, equal to the number of weights in the ANN
	
	// Creates an ANN to use
	public ANN ann;
	
	// Function to be approximated
	public static ArrayList<ArrayList<Double>> function;
	
	// Array list for all particles
	public ArrayList<Particle> particles = new ArrayList<Particle>();
	
	public PSO() throws Exception {
		
		double[] nodes = {1.0};
		this.ann = new ANN(this.num_nodes, this.num_layers, nodes);
		this.dimensions = this.ann.getMaxWeights();
		
		// Read in the function as an array list of pairs (array lists)
		function = read_function(this.data_set_file);
		if (function == null) {
			System.out.println("Error in reading function from file.");
			System.exit(1);
		}
		
		// Create particles and assign them informants, positions and velocities for every dimension
		populate(this.swarm_size, this.particles, this.dimensions);
		
		ArrayList<Double> global_best = null;
		
		// Loop until best solution is found, or max iterations have been met
		
		// For every particle, assess fitness
		// Update global best fitness
		
		// For every particle
		// Store the previous fittest location (Fittest set of weights)
		// Store the previous fittest informants location
		// Store the global fittest location
		// For every dimension (Weight value)
		// Assign a random weight to the velocity weightings
		// Assign a new velocity to that dimension (weight)
		
		// For every particle, update position
		
		Random r = new Random();
		int iterations = 0;
		double error = 999;
		while(iterations < this.max_iterations && error > this.max_error) {
			
			// Calculate new best fitness
			for (Particle p : this.particles) {
				double fitness = assessFitness(p.positions);
				System.out.println("XXXXXXXXXXXXXXXXXXX");
				System.out.println(assessFitness(p.positions));
				System.out.println(assessFitness(p.positions));
				System.out.println(assessFitness(p.positions));
				System.out.println(assessFitness(p.positions));
				System.out.println(assessFitness(p.positions));
				System.out.println("XXXXXXXXXXXXXXXXXXX");
				if (global_best == null || fitness > assessFitness(global_best)) {
					global_best = new ArrayList<>(p.positions);
				}
				if (fitness > assessFitness(p.best_positions)) {
					p.best_positions = new ArrayList<Double>(p.positions);
				}
			}
			
			// Update velocities of every particle
			for (Particle p : this.particles) {
				for (int i = 0 ; i < p.positions.size() ; i++) {
					double pb_weight = (this.personal_best_weight) * r.nextDouble();
					double in_weight = (this.informant_best_weight) * r.nextDouble();
					double gb_weight = (this.global_best_weight) * r.nextDouble();
					double current_position = p.positions.get(i);
					double current_velocity = p.velocities.get(i);
					double best_informant_position = getBestInformant(p, i);
					double new_velocity = (this.velocity_weight * current_velocity) + (pb_weight * (p.best_positions.get(i)) - current_position) + (in_weight * (best_informant_position - current_position)) + (gb_weight * (global_best.get(i) - current_position));
					p.velocities.set(i, new_velocity);
				}
			}
			
			// Update positions of every particle
			for (Particle p : this.particles) {
				for (int i = 0 ; i < p.positions.size(); i++) {
					double position = p.positions.get(i);
					double velocity = p.velocities.get(i);
					double new_position = 0.0;
					if (position + (velocity * step_size) > 1) {
						new_position = 2 - (position + (velocity * step_size));
					} else if (position + (velocity * step_size) < -1) {
						new_position = -2 + (position + (velocity * step_size));
					} else {
						new_position = position + (velocity * step_size);
					}
				}
			}
			iterations++;
			
			// Print current iteration and fitness
			System.out.println("Current iteration: " + iterations);
			System.out.println("Current best weights = " + global_best);
			System.out.println("Current best fitness = " + assessFitness(global_best));
			System.out.println();
		}
		
		System.out.println("Best set of weights = " + global_best);
		System.out.println("Fitness of these weights = " + assessFitness(global_best));
	}
	
	public static void main(String[] args) throws Exception {
		
		// Create the PSO and ANNs
		PSO pso = new PSO();
		
	}
	
	public static ArrayList<ArrayList<Double>> read_function(String file_name) throws IOException {
			
		/*
		 * Method to read in data sets to an array list of pairs of doubles
		 */
			
			try {
				// Create readers to read the input file
				FileReader fr = new FileReader(file_name);
				BufferedReader br = new BufferedReader(fr);
				
				// Create an array list of pairs to store the file contents
				ArrayList<ArrayList<Double>> function = new ArrayList<ArrayList<Double>>();
				String line = br.readLine();
				while (line != null) {
					
					// Split every line into 2 strings, cast those strings to double, and store them in a pair arraylist
					String[] list = line.split(" ");
					ArrayList<Double> entry = new ArrayList<Double>();
					entry.add(Double.parseDouble(list[0]));
					entry.add(Double.parseDouble(list[1]));
					function.add(entry);
					line = br.readLine();
					
				}
				br.close();
				return function;
				
			} catch (FileNotFoundException e) {
				System.out.println("Could not find file.");
				return null;
			}
			
	}
	
	public void populate(int length, ArrayList<Particle> populace, int dimensions) {
		
		/*
		 * This function should assign each particle 1 random position and velocity for every dimension there is.
		 * This means that for an ANN with 20 weights to edit, a single particle will have 20 positions and 20 velocities associated with it.
		 * Each particle created is assigned a random position and velocity.
		 */
		
		for (int i = 0 ; i < length ; i++) {
			populace.add(new Particle(-1, 1, dimensions));
		}
		
		for (Particle p : populace) {

			// Generate a list of random integers
			HashSet<Integer> informant_indexes = new HashSet<Integer>();
			while (informant_indexes.size() != this.max_informants) {
				int randomNum = ThreadLocalRandom.current().nextInt(0, populace.size());
				informant_indexes.add(randomNum);
			}
			
			ArrayList<Particle> informants = new ArrayList<Particle>();
			for (Integer i : informant_indexes) {
				informants.add(populace.get(i));
			}
			p.informants = informants;
		}
	}
	
	public double assessFitness(ArrayList<Double> positions) throws Exception {
		
		/*
		 * This function aims to calculate the fitness of a given "position", being the weight of a node in our ANN.
		 * Fitness is calculated by running the ANN with the new weight values suggested by the current particle's dimensions.
		 * Fitness is decided by how close each input is to its output, via the mean squared error.
		 * The mean squared error will be the result, as a double.
		 */
		
		double mean_squared_error = 0;
		int samples = function.size();
		
		// Set weights for current ANN
		
		
		// ANN is tested with each input / expected output stored in the function to calculate overall error
		for (int i = 0 ; i < function.size() ; i++) {
			
			double sample_input = function.get(i).get(0);
			double desired_output = function.get(i).get(1);
			
			// Change ANN's input value
			double[] new_inputs = {sample_input};
			this.ann.updateInputs(new_inputs);
			
			// Update weights
			double[] weights = new double[positions.size()];
			for (int j = 0 ; j < positions.size(); j++) {
				weights[j] = positions.get(j);
			}
			this.ann.setWeights(weights);
			
			// Calculate all node values again
			this.ann.calculate(this.activation_function);
			
			// Get output value
			double actual_output = ann.getOutput();
			mean_squared_error += Math.pow((desired_output - actual_output), 2);
			
			System.out.println("Input: " + sample_input);
			System.out.println("Expected Output: " + desired_output);
			System.out.println("Actual Output: " + actual_output);
		}
		mean_squared_error = (1 / (double) samples) * mean_squared_error;
		return mean_squared_error;
	}
	
	public double getBestInformant(Particle p, int index) throws Exception {
		
		int best_index = -1;
		double best_fitness = -1.0;
		for (int i = 0 ; i < p.informants.size(); i++) {
			double informant_fitness = assessFitness(p.informants.get(i).best_positions);
			if (informant_fitness > best_fitness) {
				best_index = i;
				best_fitness = informant_fitness;
			}
		}
		return p.informants.get(best_index).positions.get(index);
	}
	
}