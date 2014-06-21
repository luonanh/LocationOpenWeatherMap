package course.examples.Location.GetLocation;

public class WeatherInfo {
	private double tempMin = 0;
	private double tempMax = 120;
	private double tempCurrent = 50;

	public WeatherInfo(double min, double max, double current) {
		tempMin = min;
		tempMax = max;
		tempCurrent = current;
	}

	public double getTempMin() {
		return tempMin;
	}

	public void setTempMin(double tempMin) {
		this.tempMin = tempMin;
	}

	public double getTempMax() {
		return tempMax;
	}

	public void setTempMax(double tempMax) {
		this.tempMax = tempMax;
	}

	public double getTempCurrent() {
		return tempCurrent;
	}

	public void setTempCurrent(double tempCurrent) {
		this.tempCurrent = tempCurrent;
	}
}
