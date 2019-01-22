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
    private static final int INITIAL_DELAY=5000; // 5 seconds
    private static int timeoutCount;

    @Override
    public void onReceive(Context ctxt, Intent i) {
        timeoutCount++; //increments
        if (i.getAction() == null) {
            Log.d(getClass().getPackage().getName(), "AlarmTimeout event. work enqued");
            ScheduledService.launchAlarm(ctxt,timeoutCount);
        }
        else {
            int reschedule_millis = i.getIntExtra("retime",PERIOD);
            scheduleAlarms(ctxt,reschedule_millis);
            Log.d(getClass().getPackage().getName(), "Retime @"+Integer.toString(reschedule_millis));
        }
    }

    public static void scheduleAlarms(Context ctxt, int int_millis) {
        AlarmManager mgr=
                (AlarmManager)ctxt.getSystemService(Context.ALARM_SERVICE);
        Intent i=new Intent(ctxt, PollReceiver.class);
        PendingIntent pi=PendingIntent.getBroadcast(ctxt, 0, i, 0);

        mgr.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                SystemClock.elapsedRealtime() + INITIAL_DELAY,
                int_millis, pi);
        timeoutCount = 0; //reinit timeout count
    }

    //SAMPLE USE:
    /*
    PollReceiver.scheduleAlarms(this,period);
    on the onCreate of an activity*/
}
