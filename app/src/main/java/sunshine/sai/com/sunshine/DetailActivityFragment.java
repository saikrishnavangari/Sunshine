package sunshine.sai.com.sunshine;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailActivityFragment extends Fragment {

    public DetailActivityFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview= inflater.inflate(R.layout.fragment_detail, container, false);
        Intent intent= getActivity().getIntent();
        if(intent!=null&&intent.hasExtra(intent.EXTRA_TEXT)){
            String forecast= intent.getStringExtra(intent.EXTRA_TEXT);
            TextView detail_TextView= (TextView) rootview.findViewById(R.id.detailText);
            detail_TextView.setText(forecast);
        }
        return rootview;
    }
}
