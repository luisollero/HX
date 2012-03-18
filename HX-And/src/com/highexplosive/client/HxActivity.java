package com.highexplosive.client;

import greendroid.app.GDActivity;
import greendroid.widget.ActionBarItem;
import greendroid.widget.ActionBarItem.Type;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.widget.Toast;

import com.highexplosive.client.view.ViewPagerAdapter;
import com.viewpagerindicator.TitlePageIndicator;

public class HxActivity extends GDActivity  {
	
	private ViewPagerAdapter viewPagerAdapter = null;
	private ViewPager viewPager;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActionBarContentView(R.layout.main);
        
        initActionBar();

        viewPagerAdapter = new ViewPagerAdapter(this);
		viewPager = (ViewPager) findViewById(R.id.viewpager);
		viewPager.setAdapter(viewPagerAdapter);

		TitlePageIndicator titleIndicator = (TitlePageIndicator) findViewById(R.id.title);
		titleIndicator.setViewPager(viewPager);
    }
    
    private void initActionBar() {
		getActionBar().setTitle("Highexplosive");
		addActionBarItem(Type.Refresh, 0);
	}

    public boolean onHandleActionBarItemClick(ActionBarItem item, int pos) {
		switch (pos) {
		case 1:
			Toast.makeText(getApplicationContext(),
					"Refresh", Toast.LENGTH_SHORT).show();
			break;
		default:
			break;
		}
		return super.onHandleActionBarItemClick(item, pos);
	}
    
}