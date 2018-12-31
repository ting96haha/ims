package altf4.imn;


import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

//LIBRARY BY TORANOVA chia_jason96@live.com
import altf4.imn.customviews.StandardPostView;
import mmls.MMLSclient;
import mmls.MMLSparser;
/**
 * A simple {@link Fragment} subclass.
 */
public class NewPostFragment extends Fragment {

    private static final String logtag = "PostFragments";

    private static ViewGroup insertPoint;

    public NewPostFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View outView = inflater.inflate(R.layout.fragment_new_post_minimal, container, false);
        insertPoint = outView.findViewById(R.id.insertion_point);

        StandardPostView DefaultPost = new StandardPostView(getContext());
        DefaultPost.setResVals("HOTPOT","Waiting for data...");

        new PageObtainTask().execute(this);

        insertPoint.addView(DefaultPost);

        return outView;
    }

    public void addPost(StandardPostView target){
        insertPoint.addView(target);
    }


    private class PageObtainTask extends AsyncTask<NewPostFragment,Void, String>{

        private NewPostFragment mFrag;

        @Override
        protected String doInBackground(NewPostFragment... params) {
            try{

                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences( getActivity());
                String SID = prefs.getString( "prefStudentID" , "1161300548");
                String SPW = prefs.getString("prefStudentPW","abcde12345T%");
                Log.d(logtag,"Loaded SID & PW:"+SID+' '+SPW);
                MMLSclient client = new MMLSclient(SID,SPW);
                MMLSparser parser = new MMLSparser();

                mFrag = params[0];
                parser.setDoc(client.getLogin());
                return parser.retrRawString();
            } catch (Exception e){
                Log.e(logtag,e.toString());
            }
            return "ERROR-asynctaskFailure";
        }


        @Override
        protected void onPostExecute(String result) {
            //if you had a ui element, you could display the title
            Log.d(logtag,result);
            StandardPostView newPost = new StandardPostView(getContext());
            newPost.setResVals("HOTPOT",result);
            mFrag.addPost(newPost);
        }
    }

}
