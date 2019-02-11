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

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.util.Log;

public class PollReceiver extends BroadcastReceiver {
    private static final int PERIOD=300000; // 15 minutes
    private static final String logtag = "IMN_DEBUG_CHANNEL";

    @Override
    public void onReceive(Context ctxt, Intent i) {
        if (i.getAction() == null) {
            Log.d(logtag, "AlarmTimeout event. work enqued");
            ScheduledService.launchAlarm(ctxt);
        }
        else {
            Log.d(logtag, "Full reschedule event for 15 min");
            scheduleAlarms(ctxt,900000);
        }
    }

    public static void scheduleAlarms(Context ctxt, int int_millis) {
        Log.d(logtag,"Scheduling Alarm for "+Long.toString(int_millis));
        AlarmManager mgr=
                (AlarmManager)ctxt.getSystemService(Context.ALARM_SERVICE);
        Intent i=new Intent(ctxt, PollReceiver.class);
        PendingIntent pi=PendingIntent.getBroadcast(ctxt, 0, i, 0);

        mgr.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                SystemClock.elapsedRealtime() +  int_millis,
                int_millis, pi);
        Log.d(logtag,"Alarm scheduled");
    }

    public static void disableAlarms(Context ctxt){
        AlarmManager mgr=
                (AlarmManager)ctxt.getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(ctxt, PollReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(ctxt, 0, i,0);
        mgr.cancel(pi);
        Log.d(logtag,"Alarm cancelled");
    }

    //SAMPLE USE:
    /*
    PollReceiver.scheduleAlarms(this,period);
    on the onCreate of an activity*/
}
