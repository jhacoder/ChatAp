package sushantkumarjha.chatapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Sushant kumar jha on 22-04-2017.
 */

public class MyPageAdapter extends FragmentPagerAdapter {

    public MyPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        Fragment fragment=null;
        if(i==0)
            fragment=new Call();
        if(i==1)
            fragment=new Circle();
        if(i==2)
            fragment=new Status();

        return fragment;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
