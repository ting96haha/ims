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
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.List;

import altf4.imn.R;
import altf4.imn.customeviews.StandardPostView;
import cjason.PageObtainTask;
import mmls.MMLSpost;
import mmls.Posthandler;


public class NewPostFragment extends Fragment {
    View view;
    Posthandler postmaster;
    List<Integer> postIndexi;
    ViewGroup insertion_point;

    private static final String logtag = "IMN_DEBUG_TEST";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        view = inflater.inflate(R.layout.fragment_new_post, container, false);

        insertion_point = view.findViewById(R.id.insertion_point);
        Button seen_all = view.findViewById(R.id.unall);

        //get student id
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        String stdprefid = prefs.getString("mmls_id",getContext().getString(R.string.nologin));

        //get filename
        final String filename = getContext().getFilesDir().toString() + "/" + stdprefid + ".dat";


        if(PageObtainTask.checkFilExist(filename)){
            //file exists, load from file
            Log.d(logtag,"User history exists. Reading from old PostHandler");
            postmaster = Posthandler.readFile(filename);
        }else{
            //create new postmaster
            Log.d(logtag,"User history does not exist. Creating new PostHandler");
            postmaster = new Posthandler();
        }

        //obtain list of unnotified posts
        postIndexi = postmaster.getPostList(false);

        Log.d(logtag,"size: " + postIndexi.size());
        //create each unnotified post
        for(int i=0; i<postIndexi.size(); i++){
            final StandardPostView postView = new StandardPostView(this.getContext(), true);
            //MMLSpost currentPost = listOfPosts.get(i);
            final int temp = postIndexi.get(i);
            final int icpy = i;
            View.OnClickListener click = new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    Log.d(logtag, Integer.toString(temp)+" Notified: " + postmaster.getPost(temp).isNotified());
                    Log.d(logtag, postmaster.getPost(temp).getTitle());
                    postmaster.notifyPost(temp);
                    Log.d(logtag, Integer.toString(temp)+" Notified: " + postmaster.getPost(temp).isNotified());
                    insertion_point.removeView(postView.getView());
                    postmaster.saveFile(filename);
                }
            };
            postView.setResVals(postmaster.getPost(temp), click);
            insertion_point.addView(postView.getView());
        }

        seen_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int j=0;j<postIndexi.size();j++){
                    postmaster.notifyPost(postIndexi.get(j));
                    insertion_point.removeAllViews();
                    postmaster.saveFile(filename);
                }
            }
        });

        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Fresh Posts");
    }
}
