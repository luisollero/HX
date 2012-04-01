package com.highexplosive.client.activities;

import greendroid.app.GDActivity;
import greendroid.widget.ActionBarItem;
import greendroid.widget.ActionBarItem.Type;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.widget.Toast;

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
		getActionBar().setTitle("Highexplosive");
		getActionBar().addItem(Type.Trashcan, 0);
		
		
	}
    
    
    public boolean onHandleActionBarItemClick(ActionBarItem item, int pos) {
		switch (pos) {
		case 0:
			Toast.makeText(getApplicationContext(),
					"Garbage", Toast.LENGTH_SHORT).show();
			break;
		default:
			break;
		}
		return super.onHandleActionBarItemClick(item, pos);
	}
    
}