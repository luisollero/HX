package com.highexplosive.client;

import greendroid.app.GDActivity;
import greendroid.widget.ActionBarItem;
import greendroid.widget.ActionBarItem.Type;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.Toast;

import com.highexplosive.client.view.InitialPagerAdapter;
import com.viewpagerindicator.TitlePageIndicator;

public class HxActivity extends GDActivity  {
	
	private static final String TAG = HxActivity.class.getName();
	private InitialPagerAdapter viewPagerAdapter = null;
	private ViewPager viewPager;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActionBarContentView(R.layout.main);
        
        initActionBar();

        viewPagerAdapter = new InitialPagerAdapter(this);
		viewPager = (ViewPager) findViewById(R.id.viewpager);
		viewPager.setAdapter(viewPagerAdapter);

		TitlePageIndicator titleIndicator = (TitlePageIndicator) findViewById(R.id.title);
		titleIndicator.setViewPager(viewPager);
    }
    
    private void initActionBar() {
		getActionBar().setTitle("Highexplosive");
		addActionBarItem(Type.Refresh, 0);
		addActionBarItem(Type.Trashcan);
	}

    public boolean onHandleActionBarItemClick(ActionBarItem item, int pos) {
		switch (pos) {
		case 0:
			Toast.makeText(getApplicationContext(),
					"Refresh", Toast.LENGTH_SHORT).show();
			break;
		case 1:
			if (viewPager.getCurrentItem() == InitialPagerAdapter.POSITION_MESSAGES) {
				viewPagerAdapter.deleteMessages();
			}
			break;
		default:
			break;
		}
		return super.onHandleActionBarItemClick(item, pos);
	}
    
}