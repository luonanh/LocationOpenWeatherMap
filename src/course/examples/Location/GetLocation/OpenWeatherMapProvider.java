package course.examples.Location.GetLocation;

import java.io.InputStream;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class OpenWeatherMapProvider implements WeatherProviderInterface {
	private final static String LOG_TAG = OpenWeatherMapProvider.class.getCanonicalName();
	
	public OpenWeatherMapProvider() {
	}

	public static Weather getWeather(String data) throws JSONException  {
		Weather weather = new Weather();
		try {
			// We create out JSONObject from the data
			JSONObject jObj = new JSONObject(data);
			
			// We start extracting the info
			Location loc = new Location();
			
			JSONObject coordObj = getObject("coord", jObj);
			loc.setLatitude(getFloat("lat", coordObj));
			loc.setLongitude(getFloat("lon", coordObj));
			
			JSONObject sysObj = getObject("sys", jObj);
			loc.setCountry(getString("country", sysObj));
			loc.setSunrise(getInt("sunrise", sysObj));
			loc.setSunset(getInt("sunset", sysObj));
			loc.setCity(getString("name", jObj));
			weather.location = loc;
			
			// We get weather info (This is an array)
			JSONArray jArr = jObj.getJSONArray("weather");
			
			// We use only the first value
			JSONObject JSONWeather = jArr.getJSONObject(0);
			weather.currentCondition.setWeatherId(getInt("id", JSONWeather));
			weather.currentCondition.setDescr(getString("description", JSONWeather));
			weather.currentCondition.setCondition(getString("main", JSONWeather));
			weather.currentCondition.setIcon(getString("icon", JSONWeather));
			
			JSONObject mainObj = getObject("main", jObj);
			weather.currentCondition.setHumidity(getInt("humidity", mainObj));
			weather.currentCondition.setPressure(getInt("pressure", mainObj));
			weather.temperature.setMaxTemp(getFloat("temp_max", mainObj));
			weather.temperature.setMinTemp(getFloat("temp_min", mainObj));
			weather.temperature.setTemp(getFloat("temp", mainObj));
			
			// Wind
			JSONObject wObj = getObject("wind", jObj);
			weather.wind.setSpeed(getFloat("speed", wObj));
			weather.wind.setDeg(getFloat("deg", wObj));
			
			// Clouds
			JSONObject cObj = getObject("clouds", jObj);
			weather.clouds.setPerc(getInt("all", cObj));
			
			// Rain AL added to get rain just in case
			JSONObject rObj = getObject("rain", jObj);
			if (null != rObj) {
				float rainAmount = getFloat("3h", rObj);
				// check for Float.Nan per http://stackoverflow.com/questions/9341653/float-nan-float-nan
				if (rainAmount == rainAmount) {
					weather.rain.setAmmount(getFloat("3h", rObj));
				}
			}
			
			// We download the icon to show
						
			return weather;
		} catch (JSONException e) {
			//Log.i(LOG_TAG, e.getMessage());
			Log.i(LOG_TAG, e.toString());
			return weather;
		}
	}

	private static JSONObject getObject(String tagName, JSONObject jObj) {
		try {
			JSONObject subObj = jObj.getJSONObject(tagName);
			return subObj;
		} catch (JSONException e) {
			Log.i(LOG_TAG, "getObject JSONException with tag - " + tagName);
			return null;
		}
	}
	
	private static String getString(String tagName, JSONObject jObj) {
		try {
			return jObj.getString(tagName);
		} catch (JSONException e) {
			Log.i(LOG_TAG, "getString JSONException with tag - " + tagName);
			return null;
		}
	}

	private static float  getFloat(String tagName, JSONObject jObj) {
		try {
			return (float) jObj.getDouble(tagName);
		} catch (JSONException e) {
			Log.i(LOG_TAG, "getString JSONException with tag - " + tagName);
			return Float.NaN;
		}
	}
	
	private static int  getInt(String tagName, JSONObject jObj) throws JSONException {
		try {
			return jObj.getInt(tagName);
		} catch (JSONException e) {
			Log.i(LOG_TAG, "getString JSONException with tag - " + tagName);
			return Integer.MIN_VALUE;
		}
	}
	
	public static int kelvinToFahrenheit(double k) {
		double f = (k - (double)273.15); // to celcius
		f = (f * (9/5)) + 32;
		return (int) f;
	}

	@Override
	public String getWeatherDataFromLatLong(double lat, double lon) {
		return new WeatherHttpClient().getWeatherData(lat, lon);
	}

	@Override
	public WeatherInfo getWeatherInfoFromWeatherData(String data) {
		try {
			Weather w = getWeather(data);
			WeatherInfo wi = new WeatherInfo(
					kelvinToFahrenheit(w.temperature.getMaxTemp()), 
					kelvinToFahrenheit(w.temperature.getMinTemp()), 
					kelvinToFahrenheit(w.temperature.getTemp()));
			if (w.rain.getAmmount() > 0) {
				wi.setRainAmount(w.rain.getAmmount());
			}
			if (w.wind.getSpeed() > 0) {
				wi.setWindSpeed(w.wind.getSpeed());
			}
			return wi;
		} catch (Exception e) {
			//Log.i(LOG_TAG, e.getMessage());
			Log.i(LOG_TAG, e.toString());
			return null;
		}
	}

}
