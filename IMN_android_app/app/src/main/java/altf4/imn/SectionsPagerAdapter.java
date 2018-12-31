package altf4.imn;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;


public class SectionsPagerAdapter extends FragmentStatePagerAdapter {

    //integer to count number of tabs
    int tabCount;
    private static final String logtag = "PagerFragment";

    //Constructor to the class
    public SectionsPagerAdapter(FragmentManager fm, int tabCount) {
        super(fm);
        //Initializing tab count
        this.tabCount= tabCount;
    }

    //Overriding method getItem
    @Override
    public Fragment getItem(int position) {
        //Returning the current tabs
        switch (position) {
            case 0:
                NewPostFragment tab1 = new NewPostFragment();
                return tab1;
            case 1:
                StarredPostFragment tab2 = new StarredPostFragment();
                return tab2;
            case 2:
                OldPostFragment tab3 = new OldPostFragment();
                return tab3;
            default:
                return null;
        }
    }

    //Overriden method getCount to get the number of tabs
    @Override
    public int getCount() {
        return tabCount;
    }
}