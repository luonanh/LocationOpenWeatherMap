package course.examples.Location.GetLocation;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.TextView;

public class WeatherServiceTask extends AsyncTask<String, Void, WeatherInfo>{
	private WeatherProvider weatherProvider; // an interface can be swapped at run time
	private Activity uiActivity;
	
	public WeatherServiceTask(WeatherProvider wp, Activity uiThread) {
		super();
		weatherProvider = wp;
		uiActivity = uiThread;
	}

	@Override
	protected WeatherInfo doInBackground(String... params) {
		double lat = Double.parseDouble(params[0]);
		double lon = Double.parseDouble(params[1]);
		String data = weatherProvider.getWeatherDataFromLatLong(lat, lon);
		//return weatherProvider.getWeatherInfoFromWeatherData(data);
		return weatherProvider.getWeatherInfoFromWeatherData(OpenWeatherMapMockFeed.rawText());
	}

	@Override
	protected void onPostExecute(WeatherInfo result) {
		TextView temp = (TextView) uiActivity.findViewById(R.id.woeid);
		temp.setText(String.valueOf(result.getTempCurrent()));
	}
}
