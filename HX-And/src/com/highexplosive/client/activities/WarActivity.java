package com.highexplosive.client.activities;

import greendroid.app.GDActivity;
import greendroid.widget.ActionBarItem;
import greendroid.widget.ActionBarItem.Type;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.highexplosive.client.R;
import com.highexplosive.client.view.WarPagerAdapter;
import com.viewpagerindicator.TitlePageIndicator;

public class WarActivity extends GDActivity  {
	
	@SuppressWarnings("unused")
	private static final String TAG = WarActivity.class.getName();
	
	private WarPagerAdapter viewPagerAdapter = null;
	private ViewPager viewPager;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setActionBarContentView(R.layout.war);
        initActionBar();

        viewPagerAdapter = new WarPagerAdapter(this);
		viewPager = (ViewPager) findViewById(R.id.warPager);
		viewPager.setAdapter(viewPagerAdapter);

		TitlePageIndicator titleIndicator = (TitlePageIndicator) findViewById(R.id.warTitle);
		titleIndicator.setViewPager(viewPager);
    }
    
    private void initActionBar() {
    	getActionBar().setTitle(getString(R.string.action_bar_title));
		getActionBar().addItem(Type.Add, 0);
		getActionBar().addItem(Type.Trashcan, 1);
	}
    
    
    public boolean onHandleActionBarItemClick(ActionBarItem item, int pos) {
		switch (pos) {
		case 0:
			viewPagerAdapter.addOrder();
			break;
		case 1:
			viewPagerAdapter.deleteReports();
			break;
		default:
			break;
		}
		return super.onHandleActionBarItemClick(item, pos);
	}
    
}