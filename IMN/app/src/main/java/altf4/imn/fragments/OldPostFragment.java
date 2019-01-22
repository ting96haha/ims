package altf4.imn.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.List;

import altf4.imn.R;
import altf4.imn.customeviews.StandardPostView;
import cjason.PageObtainTask;
import mmls.MMLSpost;
import mmls.Posthandler;


public class OldPostFragment extends Fragment {
    View view;
    Posthandler postmaster;
    List<MMLSpost> listOfPosts;
    LinearLayout insertion_point;

    private static final String logtag = "IMN_DEBUG_TEST";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        view = inflater.inflate(R.layout.fragment_old_post, container, false);

        insertion_point = view.findViewById(R.id.insertion_point);

        //get student id
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        String stdprefid = prefs.getString("mmls_id",getContext().getString(R.string.nologin));

        //get filename
        String filename = getContext().getFilesDir().toString() + "/" + stdprefid + ".dat";

        if(PageObtainTask.checkFilExist(filename)){
            //file exists, load from file
            Log.d(logtag,"User history exists. Reading from old PostHandler");
            postmaster = Posthandler.readFile(filename);
        }else{
            //create new postmaster
            Log.d(logtag,"User history does not exist. Creating new PostHandler");
            postmaster = new Posthandler();
        }

        //obtain list of notified posts
        listOfPosts = postmaster.getPostList(true);


        Log.d(logtag,"size: " + listOfPosts.size());
        //create each unnotified post
        for(int i=0; i<listOfPosts.size(); i++){
            StandardPostView postView = new StandardPostView(this.getContext(), false);
            MMLSpost currentPost = listOfPosts.get(i);
            postView.setResVals(currentPost.getTitle(), currentPost.getCourse(),
                    currentPost.getAuthor(), currentPost.getDate(), currentPost.getContent(), null);
            insertion_point.addView(postView.getView());
        }

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Notified(Old) Posts");
    }
}
