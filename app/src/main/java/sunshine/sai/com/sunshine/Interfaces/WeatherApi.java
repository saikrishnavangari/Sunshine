package sunshine.sai.com.sunshine.Interfaces;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import sunshine.sai.com.sunshine.model.Data;

/**
 * Created by krrish on 17/10/2016.
 */

public interface WeatherApi  {


    @GET("data/2.5/forecast/daily")
    Call<Data> getWeatherData(@QueryMap Map<String,String> parameters);
}
