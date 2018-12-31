package altf4.imn;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.support.v14.preference.PreferenceFragment;

import java.util.List;

public class SettingsActivity extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Add a button to the header list.
        if (hasHeaders()) {
            //Button button = new Button(this);
            //button.setText("Some action");
            //setListFooter(button);
        }
    }

    //Populate the header file
    @Override
    public void onBuildHeaders(List<Header> target) {
        loadHeadersFromResource(R.xml.preference_headers, target);
    }

    //User setting fragment
    public static class UserSettFragment extends PreferenceFragment {

        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {

            // Make sure default values are applied.  In a real app, you would
            // want this in a shared function that is used to retrieve the
            // SharedPreferences wherever they are needed.
            //PreferenceManager.setDefaultValues(getActivity(),
            //       R.xml.advanced_preferences, false);

            setPreferencesFromResource(R.xml.user_preferences, rootKey);
        }
    }

    //System setting fragment
    public static class SystSettFragment extends PreferenceFragment {

        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey){
            setPreferencesFromResource(R.xml.syst_preferences, rootKey);
        }
    }

    //required method to authenticate a valid fragment
    @Override
    protected boolean isValidFragment(String fragmentName) {
        if(  UserSettFragment.class.getName().equals(fragmentName) || SystSettFragment.class.getName().equals(fragmentName)){
            return true;
        }else{
            return false;
        }
    }

    /**
     * This fragment shows the syst_preferences for the first header.
     */
    /*public static class SettFragment extends PreferenceFragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);



            // Load the syst_preferences from an XML resource
            addPreferencesFromResource(R.xml.fragmented_preferences);
        }
    }*/

    /**
     * This fragment contains a second-level set of preference that you
     * can get to by tapping an item in the first syst_preferences fragment.
     */
    /*public static class Prefs1FragmentInner extends PreferenceFragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            // Can retrieve arguments from preference XML.
            Log.i("args", "Arguments: " + getArguments());

            // Load the syst_preferences from an XML resource
            addPreferencesFromResource(R.xml.fragmented_preferences_inner);
        }
    }*/

    /**
     * This fragment shows the syst_preferences for the second header.
     */
    /*public static class Prefs2Fragment extends PreferenceFragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            // Can retrieve arguments from headers XML.
            Log.i("args", "Arguments: " + getArguments());

            // Load the syst_preferences from an XML resource
            addPreferencesFromResource(R.xml.preference_dependencies);
        }
    }*/
}
