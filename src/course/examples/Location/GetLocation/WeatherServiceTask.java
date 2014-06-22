package course.examples.Location.GetLocation;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.TextView;

public class WeatherServiceTask extends AsyncTask<String, Void, WeatherInfo>{
	private WeatherProviderInterface weatherProvider; // an interface can be swapped at run time
	private Activity uiActivity;
	
	public WeatherServiceTask(WeatherProviderInterface wp, Activity uiThread) {
		super();
		weatherProvider = wp;
		uiActivity = uiThread;
	}

	@Override
	protected WeatherInfo doInBackground(String... params) {
		double lat = Double.parseDouble(params[0]);
		double lon = Double.parseDouble(params[1]);
		String data = weatherProvider.getWeatherDataFromLatLong(lat, lon);
		return weatherProvider.getWeatherInfoFromWeatherData(data);
		// use mock return weatherProvider.getWeatherInfoFromWeatherData(OpenWeatherMapMockFeed.rawText());
	}

	@Override
	protected void onPostExecute(WeatherInfo result) {
		TextView temp = (TextView) uiActivity.findViewById(R.id.temp);
		temp.setText("Current Temperature: " + String.valueOf(result.getTempCurrent()));
		TextView tempMax = (TextView) uiActivity.findViewById(R.id.tempMax);
		tempMax.setText("Max Temperature: " + String.valueOf(result.getTempMax()));
		TextView tempMin = (TextView) uiActivity.findViewById(R.id.tempMin);
		tempMin.setText("Min Temperature: " + String.valueOf(result.getTempMin()));
		TextView rain = (TextView) uiActivity.findViewById(R.id.rain);
		if (result.isRain()) {
			rain.setText("Rain today: " + "Yes");
		} else {
			rain.setText("Rain today: " + "No");
		}
		TextView windSpeed = (TextView) uiActivity.findViewById(R.id.windSpeed);
		windSpeed.setText("Wind speed: " + String.valueOf(result.getWindSpeed()));
	}
}
