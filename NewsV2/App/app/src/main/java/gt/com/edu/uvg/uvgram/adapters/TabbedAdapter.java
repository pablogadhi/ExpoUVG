package gt.com.edu.uvg.uvgram.adapters;




import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import gt.com.edu.uvg.uvgram.fragments.SendFragment;
import gt.com.edu.uvg.uvgram.fragments.TweetsFragment;


public class TabbedAdapter extends FragmentPagerAdapter {
    public TabbedAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0: return "Subir";
            case 1: return "Tweets";
        }
        return super.getPageTitle(position);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0: return new SendFragment();
            case 1: return new TweetsFragment();
            default: return null;
        }

    }

    @Override
    public int getCount() {
        return 2;
    }
}
