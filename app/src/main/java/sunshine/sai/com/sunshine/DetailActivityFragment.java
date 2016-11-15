package sunshine.sai.com.sunshine;

import android.app.Fragment;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import sunshine.sai.com.sunshine.data.WeatherContract.LocationEntry;
import sunshine.sai.com.sunshine.data.WeatherDbHelper;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailActivityFragment extends Fragment {
    private String  mforecastString;
    private final static String LOG_TAG=DetailActivityFragment.class.getSimpleName();
    private static final String FORECAST_SHARE_HASHTAG = " #SunshineApp";


    public DetailActivityFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview= inflater.inflate(R.layout.fragment_detail, container, false);
        Intent intent= getActivity().getIntent();
        if(intent!=null&&intent.hasExtra(intent.EXTRA_TEXT)){
            mforecastString= intent.getStringExtra(intent.EXTRA_TEXT);
            insertdummyData();
            TextView detail_TextView= (TextView) rootview.findViewById(R.id.detailText);
            detail_TextView.setText(mforecastString);
        }
        return rootview;
    }

    private void insertdummyData() {

        WeatherDbHelper DbHelper=new WeatherDbHelper(getActivity().getApplicationContext());
        SQLiteDatabase writableDatabase=DbHelper.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(LocationEntry.COLUMN_CITY_NAME,"North Pole");
        contentValues.put(LocationEntry.COLUMN_COORD_LAT,"64.7488");
        contentValues.put(LocationEntry.COLUMN_COORD_LONG,"-147.353");
        contentValues.put(LocationEntry.COLUMN_LOCATION_SETTING,"99705");
       long rowID= writableDatabase.insert(LocationEntry.TABLE_NAME,null,contentValues);
        if(rowID>0)
            Toast.makeText(getActivity(),"row inserted success", Toast.LENGTH_SHORT).show();
        readDataFromDatabase(DbHelper);
        writableDatabase.close();
    }

    private void readDataFromDatabase(WeatherDbHelper dbhelper) {
        SQLiteDatabase readDatabase=dbhelper.getReadableDatabase();
        Cursor cursor=readDatabase.query(LocationEntry.TABLE_NAME,
                null, // leaving "columns" null just returns all the columns.
                null, // cols for "where" clause
                null, // values for "where" clause
                null, // columns to group by
                null, // columns to filter by row groups
                null  // sort order
        );
       if( cursor.moveToNext()) {
           String cityname = cursor.getString(cursor.getColumnIndex(LocationEntry.COLUMN_CITY_NAME));
           Log.d(LOG_TAG, cityname);
       }
        cursor.close();
        readDatabase.close();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.detail_fragment,menu);
        MenuItem menuItem=menu.findItem(R.id.action_share);

        ShareActionProvider shareActionProvider= (ShareActionProvider) MenuItemCompat.getActionProvider(menuItem);

        if(shareActionProvider!=null){
            shareActionProvider.setShareIntent(createShareForecstIntent());
        }

    }

    private Intent createShareForecstIntent(){

       Intent shareintent=new Intent(Intent.ACTION_SEND);
       shareintent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
       shareintent.setType("text/plain");
       shareintent.putExtra(Intent.EXTRA_TEXT,mforecastString + FORECAST_SHARE_HASHTAG);
       return shareintent;
   }
}
