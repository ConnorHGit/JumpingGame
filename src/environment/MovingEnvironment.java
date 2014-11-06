package environment;


public abstract class MovingEnvironment extends BaseEnvironment {
	protected double dX;
	protected double dY;
	@Override
	public abstract void tick();


	public double getdX() {
		return dX;
	}


	public void setdX(double dX) {
		this.dX = dX;
	}


	public double getdY() {
		return dY;
	}


	public void setdY(double dY) {
		this.dY = dY;
	}

}
