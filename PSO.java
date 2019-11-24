package package1;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

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
	String data_set_file = "C:/Users/Chay-z/eclipse-workspace/BioInspiredComp/src/package1/Data/Data/1in_cubic.txt";
	
	// Hyper Parameters for the PSO
	public int swarm_size = 50;				// How many particles are created
	public float velocity_weight = 1;		// Weighting given to the current velocity when calculating new positions
	public float personal_best_weight = 1;	// Weighting given to particle's previous best position when calculating new positions
	public float informant_best_weight = 1;	// Weighting given to the best informant's position when calculating new positions
	public float global_best_weight = 1;	// Weighting given to the best global position when calculating new positions
	public float step_size = 1;				// Step size for the PSO algorithm
	public int max_informants = 10;			// Max number of informants for each particle in PSO
	public int max_iterations = 100;			// Max number of PSO iterations 
	public double max_error = 0.01;			// Highest acceptable error level for the PSO
	
	// Hyper Parameters for the ANN
	public int num_layers = 3;
	public int num_nodes = 3;
	public int activation_function = 1;		// Activation function for the ANN
	public int dimensions;				// Number of parameters we're optimising, equal to the number of weights in the ANN
	
	public static ANN ann;
	
	// Function to be approximated
	public static ArrayList<ArrayList<Double>> function;
	
	// Array list for all particles
	public ArrayList<Particle> particles;
	
	public PSO() {
		
		// Create ArrayList to store every particle created
		particles = new ArrayList<Particle>();
		
	}
	
	public static void main(String[] args) throws IOException {
		
		// Create the PSO and ANNs
		PSO pso = new PSO();
		double[] nodes = {0.0};
		pso.ann = new ANN(pso.num_nodes, pso.num_layers, nodes);
		pso.dimensions = pso.ann.getMaxWeights();
		
		// Read in the function as an array list of pairs (array lists)
		function = read_function(pso.data_set_file);
		if (function == null) {
			System.out.println("Error in reading function from file.");
			System.exit(1);
		}
		
		// Create particles and assign them informants, positions and velocities for every dimension
		populate(pso.swarm_size, pso.particles, pso.dimensions);
		
		double global_best = 0.0;
		
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
		
		int iterations = 0;
		double error = 999;
		while(iterations < pso.max_iterations && error > pso.max_error) {
			
			// Calculate new best fitness
			for (Particle p : pso.particles) {
				double fitness = assessFitness(p, pso.activation_function);
				System.out.println(fitness);
				if (fitness > global_best || global_best == 0.0) {
					global_best = fitness;
				}
			}
			
			// Update velocities of every particle
			
			
			// Update positions of every particle
			
			iterations++;
		}
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
	
	public static void populate(int length, ArrayList<Particle> populace, int dimensions) {
		
		/*
		 * This function should assign each particle 1 random position and velocity for every dimension there is.
		 * This means that for an ANN with 20 weights to edit, a single particle will have 20 positions and 20 velocities associated with it.
		 * Each particle created is assigned a random position and velocity.
		 */
		
		for (int i = 0 ; i < length ; i++) {
			populace.add(new Particle(-1, 1, dimensions));
		}
		
		for (Particle p : populace) {
			System.out.println(p.positions);
			System.out.println(p.velocities);
		}
	}
	
	public static double assessFitness(Particle p, int activationFunction) {
		
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
			ann.updateInputs(new_inputs);
			
			// Calculate all node values again
			ann.calculate(activationFunction);
			
			// Get output value
			double actual_output = ann.getOutput();
			mean_squared_error += Math.pow((desired_output - actual_output), 2);
			
		}
		mean_squared_error = (1 / samples) * mean_squared_error;	
		return mean_squared_error;
	}
	
}