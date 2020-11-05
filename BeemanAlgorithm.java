package src;

import java.util.*;

public class BeemanAlgorithm {

	private Particle particle;
	private double delta_t;
	private double r;
	private double v;
	private double a;
	private double prev_a;
	private double g_constant;
	private double k_constant;

	public BeemanAlgorithm(Particle particle, double delta_t, double g_constant, double k_constant){
		this.particle = particle;
		this.delta_t = delta_t;
		this.r = particle.getX();
		this.v = particle.getVelocityX();
		this.a = particle.getElasticAcceleration(r, k_constant, g_constant, v);
		this.prev_a = particle.getElasticAcceleration(r - delta_t*v, k_constant, g_constant, v - delta_t*a);
		this.g_constant = g_constant;
		this.k_constant = k_constant;
	}

	public void updateParticle() {
			double new_r = getNewPosition();
			double new_v = getNewVelocity(new_r);
			this.r = new_r;
			this.v = new_v;
			this.prev_a = a;
			this.a = particle.getElasticAcceleration(r, k_constant, g_constant, v);
			particle.setX(r);
			particle.setVelocityX(v);	

	}

	public double getNewPosition() {
		return (r + v*delta_t + (1/6)*(4*a - prev_a)*Math.pow(delta_t,2));
	}

	public double getNewVelocity(double new_r) {
		double acc = particle.getElasticAcceleration(new_r, k_constant, g_constant, v);
		return (v + delta_t*(2*acc + 5*a - prev_a)/6);
	}


}