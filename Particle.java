package package1;

import java.util.ArrayList;

public class Particle {

	int index;
	double weight;
	double velocity;
	ArrayList<Particle> informants = new ArrayList<Particle>();
	
	public Particle(double weight) {
		
		this.weight = weight;
		velocity = 0;
		
	}
	
}
