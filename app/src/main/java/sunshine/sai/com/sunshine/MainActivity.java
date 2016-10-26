package sunshine.sai.com.sunshine;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {
    public static final String API_KEY="eff7e84139bafaab33903f3eff71fba5";
    public static final String BASE_URL="http://api.openweathermap.org/";
    public static final String LOG_TAG=MainActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fragmentManager=getFragmentManager();
        fragmentManager.beginTransaction().add(R.id.container,new MainActivityFragment(),"mainactivityFragment").commit();

        }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
         getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_settings:
               startActivity( new Intent(this, SettingsActivity.class));
        }
    return true;
    }
}
