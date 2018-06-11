package statistics.ntels.coefficient;

public class CorrelationData {

	private double x;
	private double y;
	public double getX() {
		return x;
	}
	public void setX(Object x) {
		this.x = Double.valueOf(String.valueOf(x));
	}
	public double getY() {
		return y;
	}
	public void setY(Object y) {
		this.y = Double.valueOf(String.valueOf(y));
	}
	
	
}
