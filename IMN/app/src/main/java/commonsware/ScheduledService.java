/***
 Copyright (c) 2012 CommonsWare, LLC
 Licensed under the Apache License, Version 2.0 (the "License"); you may not
 use this file except in compliance with the License. You may obtain a copy
 of the License at http://www.apache.org/licenses/LICENSE-2.0. Unless required
 by applicable law or agreed to in writing, software distributed under the
 License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS
 OF ANY KIND, either express or implied. See the License for the specific
 language governing permissions and limitations under the License.

 Covered in detail in the book _The Busy Coder's Guide to Android Development_
 https://commonsware.com/Android
 */

package commonsware;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.JobIntentService;
import android.support.v7.preference.PreferenceManager;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import altf4.imn.R;
import cjason.NotificationMaster;
import cjason.PageObtainTask;
import mmls.MMLSpost;
import mmls.Posthandler;

public class ScheduledService extends JobIntentService {
    private static final int UNIQUE_JOB_ID=1337;
    private static final String logtag = "IMN_DEBUG_CHANNEL";

    static void enqueueWork(Context ctxt) {
        enqueueWork(ctxt, ScheduledService.class, UNIQUE_JOB_ID,
                new Intent(ctxt, ScheduledService.class));
    }

    static void launchAlarm(Context ctxt){
        Intent target = new Intent(ctxt,ScheduledService.class);
        enqueueWork(ctxt, ScheduledService.class, UNIQUE_JOB_ID,
                target);
    }

    @Override
    public void onHandleWork(Intent i) {
        Log.d("IMN_DEBUG_CHANNEL", "JobIntentService execute.");
        //------------------------------------------------------------------------------------------
        //      TODO: EDIT NON PERSISTENT CODE HERE
        //------------------------------------------------------------------------------------------

        new PageObtainTask().execute(this); //launch getPost
        Posthandler postmaster;

        //get student id
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String stdprefid = prefs.getString("mmls_id",this.getString(R.string.nologin));

        //get filename
        String filename = this.getFilesDir().toString() + "/" + stdprefid + ".dat";


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
        List<Integer> postIndexi = postmaster.getPostList(false);
        List<MMLSpost> postlist = new ArrayList<>();
        for(Integer ic : postIndexi){
            postlist.add(postmaster.getPost(ic));
        }

        if(postlist.size()>0){
            Log.d(logtag,"Some un-notified posts,launching notifications");
            NotificationMaster nmaster = new NotificationMaster(this);
            nmaster.showListNotification(this,postlist);
        }


        //int timeoutCount = i.getIntExtra("timeoutCount",0);
        //nmaster.showStringNotification(this,"TEST"+Integer.toString(timeoutCount));

        //------------------------------------------------------------------------------------------
        //------------------------------------------------------------------------------------------
    }
}