package com.highexplosive.client.activities;

import greendroid.app.GDActivity;
import greendroid.widget.ActionBarItem;
import greendroid.widget.ActionBarItem.Type;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.highexplosive.client.R;
import com.highexplosive.client.view.ProfilePagerAdapter;
import com.viewpagerindicator.TitlePageIndicator;

public class ProfileActivity extends GDActivity {

	private ProfilePagerAdapter viewPagerAdapter = null;
	private ViewPager viewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setActionBarContentView(R.layout.profile);
		initActionBar();

        viewPagerAdapter = new ProfilePagerAdapter(this);
		viewPager = (ViewPager) findViewById(R.id.profilePager);
		viewPager.setAdapter(viewPagerAdapter);

		TitlePageIndicator titleIndicator = (TitlePageIndicator) findViewById(R.id.profileTitle);
		titleIndicator.setViewPager(viewPager);
	}

	private void initActionBar() {
		getActionBar().setTitle(getString(R.string.action_bar_title));
		getActionBar().addItem(Type.Edit, 0);
	}

	
	public boolean onHandleActionBarItemClick(ActionBarItem item, int pos) {
		switch (pos) {
		case 0:
			break;
		default:
			break;
		}
		return super.onHandleActionBarItemClick(item, pos);
	}

}
