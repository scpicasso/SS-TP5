package src;

public class Particle implements Comparable<Particle>{
	
    private int id;
    private double x;
    private double y;
    private double radius;
    private double mass;
    private double vx;
    private double vy;
    private double ax;
    private double ay;
    private double pred_ax;
    private double pred_ay;



    public Particle(int id, double x, double y, double vx, double vy, double mass,
        double ax, double ay, double radius) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.mass = mass;
        this.vx = vx;
        this.vy = vy;
        this.ax = ax;
        this.ay = ay;
    }

    public double overlap(Particle p) {
        if(p.getId() == id)
            return -1;
        return (p.getRadius() + radius) - getDistance(p);
    }

    public double getDistance(Particle p) {
        return Math.sqrt(Math.pow(x - p.getX(), 2) + Math.pow(y - p.getY(), 2));
    }

    public double relativeV(Particle p, double enx, double eny) {
        return (vx - p.getVelocityX())*(-eny) + (vy - p.getVelocityY())*enx;
    }

    public double overlapWall(Walls wall, double gap) {
        switch(wall) {
            case UP:
                return radius - Math.abs(1.0 - y);
            case LEFT:
                return radius - Math.abs(0.0 - x);
            case RIGHT:
                return radius - Math.abs(0.4 - x);
            case DOWN:
                if(x > 0.2 - gap/2 && x < 0.2 + gap/2) 
                    return -1;
                else
                    return radius - Math.abs(0.0 - y);
        }
        return -1;
    }

    public int getId() {
        return id;
    }

    public double getVelocityX() {
        return vx;
    }

    public double getVelocityY() {
        return vy;
    }

    public double getAccelerationX() {
        return ax;
    }

    public double getAccelerationY() {
        return ay;
    }

    public double getPredAccX() {
        return pred_ax;
    }

    public double getPredAccY() {
        return pred_ay;
    }
    
    public double getRadius() {
        return radius;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getMass() {
        return mass;
    }

    public void setX(double x){
        this.x = x;
    }

    public void setY(double y){
        this.y = y;
    }

    public void setVelocityX(double vx){
        this.vx = vx;
    }

    public void setVelocityY(double vy){
        this.vy = vy;
    }

    public void setAccelerationX(double ax){
        this.ax = ax;
    }

    public void setAccelerationY(double ay){
        this.ay = ay;
    }

    public void setPredAccX(double pred_ax) {
        this.pred_ax = pred_ax;
    }

    public void setPredAccY(double pred_ay) {
        this.pred_ay = pred_ay;
    }

	@Override
	public int compareTo(Particle j) {
        double dx  = j.x - this.x;
        double dy  = j.y - this.y;
		if (Math.sqrt(dx*dx + dy*dy) <= (this.radius*2)) {			
			return 0;
		}
		return this.id - j.id;
	}

}
