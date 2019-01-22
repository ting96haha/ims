package altf4.imn.fragments;

import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;

import altf4.imn.R;

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
}