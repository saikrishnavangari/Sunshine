package sunshine.sai.com.sunshine;

import android.content.Context;
import android.util.Log;
import android.widget.ArrayAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import sunshine.sai.com.sunshine.Interfaces.WeatherApi;
import sunshine.sai.com.sunshine.model.Data;

/**
 * Created by krrish on 16/11/2016.
 */

public class FetchWeatherTask {

    private final String LOG_TAG = FetchWeatherTask.class.getSimpleName();
    private final Context mContext;
    Retrofit retrofit;
    ArrayList<String> mDataList = new ArrayList<String>();
    private ArrayAdapter<String> mForecastAdapter;

    public FetchWeatherTask(Context context, ArrayAdapter<String> forecastAdapter) {
        mContext = context;
        mForecastAdapter = forecastAdapter;
        retrofit = new Retrofit.Builder()
                .baseUrl(MainActivity.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    void loadData(String location) {
        WeatherApi weatherApi = retrofit.create(WeatherApi.class);
        Call<Data> call = weatherApi.getWeatherData(ParameterInfo("94043", "json", "metric", 7, MainActivity.API_KEY));
        call.enqueue(new Callback<Data>() {
                         @Override
                         public void onResponse(Call<Data> call, Response<Data> response) {
                             Data list = response.body();
                             // Log.d(LOG_TAG, list.getList().get(2).getWeather().get(0).getMain());
                             for (String dayForecastStr : getArraylistForAdapter(list))
                                 mForecastAdapter.add(dayForecastStr);
                         }

                         @Override
                         public void onFailure(Call<Data> call, Throwable t) {
                         }
                     }
        );
    }

    //parameter info to be supplied in the url
    Map ParameterInfo(String location, String mode, String units, int cnt, String APPID) {
        Map<String, String> queryParameters = new HashMap<>();
        queryParameters.put("q", location);
        queryParameters.put("mode", mode);
        queryParameters.put("units", units);
        queryParameters.put("cnt", String.valueOf(cnt));
        queryParameters.put("APPID", APPID);
        return queryParameters;

    }

    // turns the weather data into an array list
    private ArrayList<String> getArraylistForAdapter(Data body) {
        Data ListObject = body;
        for (int i = 0; i < ListObject.getList().size(); i++) {
            String Tempdata;
            Data.Temp temp = body.getList().get(i).getTemp();
            ArrayList<Data.weather> weather = body.getList().get(i).getWeather();
            Tempdata = getdate() + " " + weather.get(0).getMain() + " " + String.valueOf(temp.getMax()) + " " + "/" + String.valueOf(temp.getMin());
            Log.d(LOG_TAG, " data list :" + Tempdata);
            mDataList.add(Tempdata);
        }
        return mDataList;
    }

    //get local time and date
    private String getdate() {
        Date today;
        String output;
        SimpleDateFormat formatter;
        formatter = new SimpleDateFormat("EEE, MMM d, yy", Locale.getDefault());
        today = new Date();
        output = formatter.format(today);
        return output;
    }
}
