package altf4.imn.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceManager;
import android.widget.Toast;

import altf4.imn.R;
import commonsware.PollReceiver;

public class SystPrefFragment extends PreferenceFragmentCompat {
    public SystPrefFragment() {}

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {

        // Make sure default values are applied.  In a real app, you would
        // want this in a shared function that is used to retrieve the
        // SharedPreferences wherever they are needed.
        //PreferenceManager.setDefaultValues(getActivity(),
        //       R.xml.advanced_preferences, false);
        setPreferencesFromResource(R.xml.syst_preferences, rootKey);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences( getContext() );
        Boolean notibool = prefs.getBoolean("prefNotify",true);
        String prefminut = prefs.getString("prefSyncFrequency","15");//default 15 minutes

        //convert prefminut to seconds
        int prefsec = Integer.parseInt(prefminut) * 60 * 1000;

        if(notibool){
            Toast.makeText(getContext(),"Rescheduling alarms...",Toast.LENGTH_SHORT).show();
            PollReceiver.scheduleAlarms(getContext(),prefsec);
        }else{
            Toast.makeText(getContext(),"Disabling alarms...",Toast.LENGTH_SHORT).show();
            PollReceiver.disableAlarms(getContext());
        }
    }
}