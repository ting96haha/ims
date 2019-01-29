package altf4.imn.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.preference.PreferenceFragmentCompat;


import altf4.imn.R;

public class SystemPWrapFragment extends Fragment {

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, new SystemPrefFragment())
                .commit();
    }

    public static class SystemPrefFragment extends PreferenceFragmentCompat {
        public SystemPrefFragment() {}

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
}