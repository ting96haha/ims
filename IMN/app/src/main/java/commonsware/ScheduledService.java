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
import android.support.v4.app.JobIntentService;
import android.util.Log;

import cjason.NotificationMaster;

public class ScheduledService extends JobIntentService {
    private static final int UNIQUE_JOB_ID=1337;

    static void enqueueWork(Context ctxt) {
        enqueueWork(ctxt, ScheduledService.class, UNIQUE_JOB_ID,
                new Intent(ctxt, ScheduledService.class));
    }

    static void launchAlarm(Context ctxt,int timeoutCount){
        Intent target = new Intent(ctxt,ScheduledService.class);
        target.putExtra("timeoutCount",timeoutCount);
        enqueueWork(ctxt, ScheduledService.class, UNIQUE_JOB_ID,
                target);
    }

    @Override
    public void onHandleWork(Intent i) {
        Log.d(getClass().getPackage().getName(), "JobIntentService execute.");
        //------------------------------------------------------------------------------------------
        //      TODO: EDIT NON PERSISTENT CODE HERE
        //------------------------------------------------------------------------------------------

        int timeoutCount = i.getIntExtra("timeoutCount",0);
        NotificationMaster nmaster = new NotificationMaster(this);
        nmaster.showStringNotification(this,"TEST"+Integer.toString(timeoutCount));


        //------------------------------------------------------------------------------------------
        //------------------------------------------------------------------------------------------
    }
}