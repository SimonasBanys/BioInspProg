  
package package1;

import java.util.ArrayList;

public class Particle {

	// Particle will store 1 position and velocity value for every single dimension in the PSO
	ArrayList<Double> positions = new ArrayList<Double>();
	ArrayList<Double> velocities = new ArrayList<Double>();
	ArrayList<Double> best_positions = new ArrayList<Double>();
	
	// Must also keep a list of every informant for the current particle
	ArrayList<Particle> informants;
	
	public Particle(int min_position, int max_position, int dimensions) {
		
		// Positions and Velocities are randomly assigned
		for (int i = 0 ; i < dimensions ; i++) {
			Double position = (((double) Math.round(Math.random() * 10000d)/10000d));
			Double velocity = (((double) Math.round(Math.random() * 10000d)/10000d));
			positions.add(position);
			best_positions.add(position);
			velocities.add(velocity);
		}
	}
	
	public void update_positions(ArrayList<Double> new_positions) {
		if (new_positions.size() == positions.size()) {
			for (int i = 0 ; i < new_positions.size(); i++) {
				positions.set(i, new_positions.get(i));
			}
		}
	}
	
	public void update_velocities(ArrayList<Double> new_velocities) {
		if (new_velocities.size() == velocities.size()) {
			for (int i = 0 ; i < new_velocities.size(); i++) {
				velocities.set(i, new_velocities.get(i));
			}
		}
	}
	
	public void update_best_positions(ArrayList<Double> new_best_positions) {
		if (new_best_positions.size() == best_positions.size()) {
			for (int i = 0 ; i < new_best_positions.size(); i++) {
				positions.set(i, new_best_positions.get(i));
			}
		}
	}
}