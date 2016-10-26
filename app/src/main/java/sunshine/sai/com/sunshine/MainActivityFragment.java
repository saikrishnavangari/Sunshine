package sunshine.sai.com.sunshine;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import sunshine.sai.com.sunshine.Interfaces.WeatherApi;
import sunshine.sai.com.sunshine.model.Data;

import static sunshine.sai.com.sunshine.MainActivity.LOG_TAG;

/**
 * Created by krrish on 17/10/2016.
 */

public class MainActivityFragment extends Fragment {
    private  View rootview;
    private ArrayList<String> dataList=new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
         rootview=inflater.inflate(R.layout.fragment_main,container,false);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(MainActivity.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        WeatherApi weatherApi= retrofit.create(WeatherApi.class);

        Call<Data> call=weatherApi.getWeatherData(ParameterInfo(94043,"json","metric",7,MainActivity.API_KEY));
        call.enqueue(new Callback<Data>() {
            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {
                Data list=  response.body();
                //  Log.d(LOG_TAG, list.getList().get(2).getWeather().get(0).getMain());
                updateview(getArraylistForAdapter(list));
                Log.d(LOG_TAG,"from fragment activity");
            }

            @Override
            public void onFailure(Call<Data> call, Throwable t) {
            }
        });
        return rootview;
    }

    //parameter info to be supplied in the url
    Map ParameterInfo(int q, String mode, String units, int cnt, String APPID){
        Map<String , String> queryParameters= new HashMap<>();
        queryParameters.put("q", String.valueOf(q));
        queryParameters.put("mode", mode);
        queryParameters.put("units", units);
        queryParameters.put("cnt", String.valueOf(cnt));
        queryParameters.put("APPID", APPID);
        return queryParameters;

    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.forecastfragment,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        return super.onOptionsItemSelected(item);
        switch(item.getItemId()) {
            case R.id.action_refresh:
                Toast.makeText(getActivity(), "refresh button selected",Toast.LENGTH_SHORT).show();
                default:
                    Toast.makeText(getActivity(), "unknown item",Toast.LENGTH_SHORT).show();
        }
        return true;
    }
//populate the listview with the data fetched
    public void updateview(ArrayList<String> datalist) {
        if( datalist!=null){
            List<String> weekForecast = datalist;
            final ArrayAdapter<String> mForecastAdapter = new ArrayAdapter<String>(getActivity(), R.layout.list_item_forecast,
                    R.id.list_item_forecast_textview, weekForecast);
            ListView listView = (ListView) rootview.findViewById(R.id.listView_forecast);
            listView.setAdapter(mForecastAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    String item_Clicked=mForecastAdapter.getItem(i);
                    // start Detail Activity
                    Intent intent= new Intent(getActivity(), DetailActivity.class);
                    intent.putExtra(Intent.EXTRA_TEXT,item_Clicked);
                    startActivity(intent);
                }
            });
        }
    }

    // turns the weather data into an array list
    private ArrayList getArraylistForAdapter(Data body) {
        Data ListObject=body;
        for(int i=0;i<ListObject.getList().size();i++)
        {
            String Tempdata;
            Data.Temp temp=body.getList().get(i).getTemp();
            ArrayList<Data.weather> weather=body.getList().get(i).getWeather();
            Tempdata=getdate()+" "+weather.get(0).getMain()+" "+String.valueOf(temp.getMax())+" "+"/"+String.valueOf(temp.getMin());
            Log.d(LOG_TAG," data list :"+Tempdata);
            dataList.add(Tempdata);
        }
        return dataList;
    }
    //get local time and date
        private  String  getdate() {
        Date today;
        String output;
        SimpleDateFormat formatter;
        formatter = new SimpleDateFormat("EEE, MMM d, yy", Locale.getDefault() );
        today = new Date();
        output = formatter.format(today);
        return output;
    }

}
