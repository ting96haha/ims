package altf4.imn;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.preference.PreferenceManager;
import android.util.Log;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.io.File;

import altf4.imn.fragments.AccountFragment;
import altf4.imn.fragments.NewPostFragment;
import altf4.imn.fragments.OldPostFragment;
import altf4.imn.fragments.SystPrefFragment;

import cjason.PageObtainTask;
import commonsware.PollReceiver;
import cjason.NotificationMaster;
import mmls.Posthandler;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static NotificationMaster nmaster;
    private static final String logtag = "IMN_DEBUG_CHANNEL";

    //globals
    private static Posthandler postmaster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initNavBars(); //initialize the navigation bars


        //PollReceiver.scheduleAlarms(this,1000); //1seconds
        //nmaster = new NotificationMaster(this);
        //nmaster.showStringNotification(this,"TEST");//show a simple notification

    }

    @Override
    protected void onResume(){
        super.onResume();
        //obtain the preferences
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences( this );
        Boolean notibool = prefs.getBoolean("prefNotify",true);
        String prefminut = prefs.getString("prefSyncFrequency","15");//default 15 minutes
        String stdprefid = prefs.getString("mmls_id",getString(R.string.nologin));

        //Set the username on the navbar
        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        TextView navUsername =  headerView.findViewById(R.id.student_id);
        navUsername.setText(stdprefid);



        //convert prefminut to seconds
        int prefsec = Integer.parseInt(prefminut) * 60 * 1000;

        Log.d(logtag,"Notify?"+notibool);
        Log.d(logtag,"Preference Period(s):"+Integer.toString(prefsec));
        PollReceiver.scheduleAlarms(this,prefsec); //1seconds
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if(id==R.id.download_now){
            Log.d(logtag, "Get Posts Launched");
            new PageObtainTask().execute(this); //launch getPost
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment fragment;
        if (id == R.id.new_post) {
            // loads the un-notified posts
            fragment = new NewPostFragment();
            Log.d(logtag, "New Posts Drawn");
        } else if (id == R.id.old_post) {
            // loads notified posts
            fragment = new OldPostFragment();
            Log.d(logtag, "Old Posts Drawn");
        } else if (id == R.id.user_profile) {
            // setups user profile
            fragment = new AccountFragment();
            Log.d(logtag, "User Profile Drawn");
        } else if (id == R.id.reload_freq) {
            //tune the reloading/clearing cache frequency
            fragment = new SystPrefFragment();
            Log.d(logtag, "Reload Drawn");
        } else if (id == R.id.get_post) {
            fragment = null;
            Log.d(logtag, "Get Posts Launched");
            new PageObtainTask().execute(this); //launch getPost
        }else{
            fragment = null;
        }

        //replacing the fragment
        if (fragment != null) {
            Log.d(logtag, "Replacing current fragment");
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment);
            ft.commit();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //Non-logic
    private void initNavBars(){
        //simple initialization of the navigational toolbars
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences( this );
        //String sid = prefs.getString("mmls_id",getString(R.string.nologin));
        //TextView stdid = navigationView.findViewById(R.id.student_id);
        //stdid.setText(sid);
    }

}
