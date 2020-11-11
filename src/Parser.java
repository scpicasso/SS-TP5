package src;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.ThreadLocalRandom;

public class Parser {
	private int particleCount;

	private List<Particle> particles;

	public Parser() {
		this.particles = new LinkedList<Particle>();
	}
	
    public List<Particle> getParticles(String filePath) throws FileNotFoundException {
    	FileInputStream fis = new FileInputStream(filePath);  
        Scanner sc = new Scanner(fis);
        particleCount = sc.nextInt();
        for (int i = 0; i < particleCount; i++) {
        	double x = sc.nextDouble();
        	double y = sc.nextDouble();
        	double r = sc.nextDouble();
        	Particle p = new Particle(i, x, y, 0.0, 0.0, 0.01, 0.0, -10.0, r);    
        	particles.add(p);
        }            
		return particles;       
	}

	public int getN() {
		return particleCount;
	}
}
