package sushantkumarjha.chatapp;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

public class Friend extends AppCompatActivity implements ActionBar.TabListener{
    ViewPager viewPager=null;
   ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend);
        viewPager=(ViewPager)findViewById(R.id.pager);
        actionBar=getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        FragmentManager fragmentManager=getSupportFragmentManager();
        viewPager.setAdapter(new MyPageAdapter(fragmentManager));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        ActionBar.Tab call=actionBar.newTab();
        call.setText("call");
        call.setTabListener(this);

        ActionBar.Tab circle=actionBar.newTab();
        circle.setText("circle");
        circle.setTabListener(this);

        ActionBar.Tab status=actionBar.newTab();
        status.setText("status");
        status.setTabListener(this);

        actionBar.addTab(call);
        actionBar.addTab(circle);
        actionBar.addTab(status);
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
        viewPager.setCurrentItem(tab.getPosition());

    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }
}
