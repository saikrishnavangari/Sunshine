package sunshine.sai.com.sunshine;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by krrish on 26/10/2016.
 */

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction().replace(android.R.id.content, new SettingFragment()).commit();
    }
}
