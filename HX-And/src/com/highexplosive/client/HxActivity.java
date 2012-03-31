package com.highexplosive.client;

import greendroid.app.GDActivity;
import greendroid.widget.ActionBarItem;
import greendroid.widget.ActionBarItem.Type;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.widget.Toast;

import com.highexplosive.client.view.InitialPagerAdapter;
import com.viewpagerindicator.TitlePageIndicator;

public class HxActivity extends GDActivity  {
	
	@SuppressWarnings("unused")
	private static final String TAG = HxActivity.class.getName();
	
	private InitialPagerAdapter viewPagerAdapter = null;
	private ViewPager viewPager;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActionBarContentView(R.layout.news);
        
        initActionBar();

        viewPagerAdapter = new InitialPagerAdapter(this);
		viewPager = (ViewPager) findViewById(R.id.viewpager);
		viewPager.setAdapter(viewPagerAdapter);

		TitlePageIndicator titleIndicator = (TitlePageIndicator) findViewById(R.id.title);
		titleIndicator.setViewPager(viewPager);
    }
    
    private void initActionBar() {
		getActionBar().setTitle("Highexplosive");
		getActionBar().addItem(Type.Refresh, 0);
		getActionBar().addItem(Type.Trashcan, 1);
		getActionBar().addItem(Type.Mail, 2);
		
//		getActionBar().removeItem(2);
//		getActionBar().removeItem(1);
//		getActionBar().removeItem(0);
		
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
		case 2:
			if (viewPager.getCurrentItem() == InitialPagerAdapter.POSITION_MESSAGES) {
				viewPagerAdapter.createMessage();
			}
			break;
		default:
			break;
		}
		return super.onHandleActionBarItemClick(item, pos);
	}
    
}