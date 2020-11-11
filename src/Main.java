package src;


import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.List;
import java.util.ArrayList;

public class Main  {

	static final double length = 1.0;
	static final double width = 0.3;

	public static void main(String[] args) throws IOException {
		double kn = 100000;
		double kt = 2*kn;

		double delta_t = 0.00001;

		double gap = Double.valueOf(args[0]);
		
		double current_time = 0.0;

		Parser fp = new Parser();

		List<Particle> particles = new ArrayList();
		
		try {
	    	particles = fp.getParticles("input");            
		} catch (FileNotFoundException e) {
			System.out.println("File not found.");
		}

		SimSystem system = new SimSystem(particles, delta_t, kn, kt, gap);

		system.getPredAcceleration();

		int round = 0;
		int printed = 0;

		double print_time = delta_t*1000;

		try (Writer writer = new BufferedWriter(new OutputStreamWriter( new FileOutputStream("KEoutput.txt"), "utf-8"))) {
			while(current_time <= 5) {
				if(printed*print_time <= current_time) {
					printOutput(particles, printed, current_time);
					writer.write(String.valueOf(current_time) + " " + String.valueOf(system.getKE()) + "\n");
					printed ++;

				}
					
				system.updateParticles();
				round ++;
				current_time = delta_t*round;
				System.out.println(current_time);
			}
		}

	}

	public static void printOutput(List<Particle> particles, int index, double time) throws UnsupportedEncodingException, FileNotFoundException, IOException {
		
		try (Writer writer = new BufferedWriter(new OutputStreamWriter( new FileOutputStream("output" + index + ".txt"), "utf-8"))) {
			writer.write(String.valueOf(particles.size()) + "\n");
			writer.write(String.valueOf(time) + "\n");
			for(Particle p : particles) {
				writer.write(String.valueOf(p.getX()) + " " + String.valueOf(p.getY()) + " " + String.valueOf(p.getRadius()) 
					+ " " + String.valueOf(p.getFt()) + " " + String.valueOf(p.getFn())+ "\n");
			}  		
		}
		return;			    	
	}
	
}