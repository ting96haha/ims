package cjason;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.preference.PreferenceManager;
import android.util.Log;

import java.io.File;

import altf4.imn.R;
import mmls.MMLSclient;
import mmls.MMLSparser;
import mmls.Posthandler;

public class PageObtainTask extends AsyncTask<Context,Void, MMLSparser> {

    private static final String logtag = "IMN_DEBUG_CHANNEL";

    private String stdprefid;
    private Context ctxt;

    @Override
    protected MMLSparser doInBackground(Context... params) {
        try{
            ctxt = params[0];
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(ctxt);
            stdprefid = prefs.getString("mmls_id",ctxt.getString(R.string.nologin));
            String stdprefpw = prefs.getString("mmls_pw",ctxt.getString(R.string.nologin));


            MMLSclient client = new MMLSclient(stdprefid,stdprefpw);
            MMLSparser parser = new MMLSparser(client.getLogin()); //obtain the login

            return parser;

        } catch (Exception e){
            Log.e(logtag,e.toString());
            return null;
        }

    }

    private static Boolean checkFilExist(String filename){
        File f = new File(filename);
        if(f.exists() && !f.isDirectory()) {
            // do something
            return true;
        }else{
            return false;
        }
    }


    @Override
    protected void onPostExecute(MMLSparser parser) {

        String filename = ctxt.getFilesDir().toString() + "/" + stdprefid + ".dat";
        Log.d(logtag,"Target Directory is :"+filename);
        Posthandler postmaster;
        if(checkFilExist(filename)){
            //file exists, load from file
            Log.d(logtag,"User history does not exist. Reading from old PostHandler");
            postmaster = Posthandler.readFile(filename);
        }else{
            //create new postmaster
            Log.d(logtag,"User history does not exist. Creating new PostHandler");
            postmaster = new Posthandler();
        }

        int newcount = postmaster.getCount();
        postmaster.checkList(parser.parsePost());
        postmaster.displayUnNotifiedPost(false);
        postmaster.saveFile(filename);
        Log.d(logtag,"New Posts:"+Integer.toString(newcount -  postmaster.getCount()));
    }
}