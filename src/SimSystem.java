package src;

import java.util.*;
import java.util.List;
import java.util.ArrayList;

public class SimSystem {
	
	List<Particle> particles;
	double delta_t;
	double kn;
	double kt;
	double dropped;
	double gap;
	double round;

	public SimSystem(List<Particle> particles, double delta_t, double kn, double kt, double gap) {
		this.particles = particles;
		this.delta_t = delta_t;
		this.kn = kn;
		this.kt = kt;
		this.gap = gap;
		this.round = 0;
	}

	public void getPredAcceleration() {

		for(Particle p : particles) {
			p.setPredAccX(p.getAccelerationX());
			p.setPredAccY(p.getAccelerationY());
		
			getForces(p);

			p.setVelocityX(p.getVelocityX() + delta_t*p.getAccelerationX());
			p.setVelocityY(p.getVelocityY() + delta_t*p.getAccelerationY());
			p.setX(p.getX() + delta_t*(p.getVelocityX() + delta_t*p.getAccelerationX()));
			p.setY(p.getY() + delta_t*(p.getVelocityY() + delta_t*p.getAccelerationY()));
		}

	}

	public void updateParticles() {
		List<Particle> removed = new ArrayList<>();
		for(Particle p : particles) {
			double ax = p.getAccelerationX();
			double ay = p.getAccelerationY();
			getForces(p);
			if(beeman(p, ax, ay)) {
				if(!reposition(p)) {
					removed.add(p);
				}
			}
		}

		particles.removeAll(removed);
	}

	public boolean reposition(Particle p) {
		double ry = 1.0-p.getRadius();
		Random r = new Random();
		p.setY(ry);
		boolean flag = false;
		
		for(int i =0; i<5000 && !flag; i++) {
			double rx = p.getRadius() + ((0.4-p.getRadius()) - p.getRadius()) * r.nextDouble();
			p.setX(rx);

			flag = true;

			for(int n = 0; n<particles.size() && flag; n++) {
				Particle j = particles.get(n);

				if(p.getId() != n) {
					if(p.getDistance(j) < p.getRadius() + j.getRadius()) {
						flag = false;
					}
				}
			}

			if(flag) {
				p.setVelocityX(0.0);
				p.setVelocityY(0.0);
				return flag;
			}
		}

		System.out.println("Can't place particle back in silo");
		return flag;
	}

	public boolean beeman(Particle p, double ax, double ay) {

		double new_ax = p.getAccelerationX();
		double new_ay = p.getAccelerationY();

		double pred_ax = p.getPredAccX();
		double pred_ay = p.getPredAccY();


		double rx = p.getX() + p.getVelocityX()*delta_t + (1.0/6.0)*(4*ax - pred_ax)*Math.pow(delta_t,2);
		double ry = p.getY() + p.getVelocityY()*delta_t + (1.0/6.0)*(4*ay - pred_ay)*Math.pow(delta_t,2);

		if(ry < -0.1) {
			return true;
		}

		p.setX(rx);
		p.setY(ry);

		double vx = p.getVelocityX() + delta_t*(2*new_ax + 5*ax - pred_ax)*(1.0/6.0);
		double vy = p.getVelocityY() + delta_t*(2*new_ay + 5*ay - pred_ay)*(1.0/6.0);

		p.setVelocityX(vx);
		p.setVelocityY(vy);
		
		p.setPredAccX(ax);
		p.setPredAccY(ay);

		return false;

	}

	public void getForces(Particle i) {

		double total_y = i.getMass()*-10.0;
		double total_x = 0.0;

		if(i.getId() == 102) {
			round++;

		}

		for(Particle j: particles){
			double overlap = i.overlap(j);

			if(overlap > 0) {
				double dx = i.getX() - j.getX();
				double dy = i.getY() - j.getY();
				double dist = Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));

				double enx = dx/dist;
				double eny = dy/dist;

				double fn = -kn*overlap;
				double ft = -kt*overlap*i.relativeV(j, enx, eny);

				total_x = total_x + fn*enx - ft*eny;
				total_y = total_y + fn*eny + ft*enx;

			}

		}

		for(Walls w : Walls.values()) {
			double overlap = i.overlapWall(w, gap);

			if(overlap > 0) {
				double dx = i.getX();
				double dy = i.getY();

				switch(w) {
            		case UP:
            			dx = 0.0;
            			dy =- 1.0;
            			break;
            		case LEFT:
            			dx =- 0.0;
            			dy = 0.0;
            			break;
            		case RIGHT:
            			dx =- 0.4;
            			dy = 0.0;
            			break;
            		case DOWN:
            			dx = 0.0;
            			dy =- 0.0;
            			break;
            	}

				double dist = Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));

				double enx = dx/dist;
				double eny = dy/dist;

				double fn = -kn*overlap;
				double ft = -kt*overlap*(-i.getVelocityX()*eny + i.getVelocityY()*enx);

				total_x = total_x + fn*enx - ft*eny;
				total_y = total_y + fn*eny + ft*enx;


			}
				
		}

		i.setAccelerationX(total_x/i.getMass());
		i.setAccelerationY(total_y/i.getMass());


	}


}

