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

		double rx = p.getX() + p.getVelocityX()*delta_t + (1.0/6.0)*(4*new_ax - ax)*Math.pow(delta_t,2);
		double ry = p.getY() + p.getVelocityY()*delta_t + (1.0/6.0)*(4*new_ay - ay)*Math.pow(delta_t,2);

		if(ry < -0.1) {
			return true;
		}

		p.setX(rx);
		p.setY(ry);

		getForces(p);

		double vx = p.getVelocityX() + delta_t*(2*p.getAccelerationX() + 5*new_ax - ax)/6.0;
		double vy = p.getVelocityY() + delta_t*(2*p.getAccelerationY() + 5*new_ay - ay)/6.0;

		p.setVelocityX(vx);
		p.setVelocityY(vy);

		getForces(p);

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
            			dx =- i.getX();
            			dy =- (1.0 - i.getRadius());
            			break;
            		case LEFT:
            			dx =- (-i.getRadius());
            			dy =- i.getY();
            			break;
            		case RIGHT:
            			dx =- (0.4 + i.getRadius());
            			dy =- i.getY();
            			break;
            		case DOWN:
            			dx =- i.getX();
            			dy =- (i.getRadius());
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

		if(i.getId() == 102 && round > 10000 && round <18000) {
			System.out.println(round);
			System.out.println(total_x/i.getMass());
			System.out.println(total_y/i.getMass());
			System.out.println(total_x);
			System.out.println(total_y);
			System.out.println(" ");
		}



		i.setAccelerationX(total_x/i.getMass());
		i.setAccelerationY(total_y/i.getMass());


	}


}

