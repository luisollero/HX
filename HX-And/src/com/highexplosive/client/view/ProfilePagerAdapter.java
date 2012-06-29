package com.highexplosive.client.view;

import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.highexplosive.client.HxConstants;
import com.highexplosive.client.HxJsonUtils;
import com.highexplosive.client.R;
import com.highexplosive.client.model.Character;
import com.viewpagerindicator.TitleProvider;

public class ProfilePagerAdapter extends PagerAdapter implements TitleProvider {

	@SuppressWarnings("unused")
	private static final String TAG = ProfilePagerAdapter.class.getName();
	
	public static final int POSITION_PROFILE = 0;

	public static int NUM_VIEWS = 1;
	private Context ctx;
	
	public ProfilePagerAdapter(Context context) {
		this.ctx = context;
	}

	@Override
	public int getCount() {
		return NUM_VIEWS;
	}

    /**
     * Create the page for the given position.  The adapter is responsible
     * for adding the view to the container given here, although it only
     * must ensure this is done by the time it returns from
     * {@link #finishUpdate()}.
     *
     * @param container The containing View in which the page will be shown.
     * @param position The page position to be instantiated.
     * @return Returns an Object representing the new page.  This does not
     * need to be a View, but can be some other container of the page.
     */
	@Override
	public Object instantiateItem(View collection, int position) {
		LayoutInflater inflater = LayoutInflater.from(ctx);
		LinearLayout linearLayout = null;
		
		switch (position) {
		case POSITION_PROFILE:
			linearLayout = profileSection(inflater);
			break;
		default:
			break;
		}

		return linearLayout;
	}


	/**
	 * House/Faction section
	 * @param collection
	 * @param inflater
	 * @return
	 */
	private LinearLayout profileSection(LayoutInflater inflater) {
		LinearLayout linearLayout = (LinearLayout) inflater.inflate(R.layout.profile_detail, null);
		
		if (HxConstants.ONLINE_MODE) {

		} else {
			initOfflineContent(linearLayout);
		}
		
		return linearLayout;
	}

	private void initOfflineContent(LinearLayout linearLayout) {
		Character character = HxJsonUtils.getCharacterDetail(this.ctx, 0);

		((TextView) linearLayout.findViewById(R.id.profileName))
				.setText(character.getName());
		((TextView) linearLayout.findViewById(R.id.profileKarma)).setText(""
				+ character.getInfluence());
		((TextView) linearLayout.findViewById(R.id.profileDeclarations)).setText(""
				+ character.getNumberOfDeclarations());
		((TextView) linearLayout.findViewById(R.id.profileRole))
				.setText(character.getRole());
		((TextView) linearLayout.findViewById(R.id.profileSince)).setText(DateUtils
				.formatDateTime(this.ctx, character.getCreationDate(),
						DateUtils.FORMAT_24HOUR));
		((TextView) linearLayout.findViewById(R.id.profileBio)).setText(character.getName());
	}

    /**
     * Remove a page for the given position.  The adapter is responsible
     * for removing the view from its container, although it only must ensure
     * this is done by the time it returns from {@link #finishUpdate()}.
     *
     * @param container The containing View from which the page will be removed.
     * @param position The page position to be removed.
     * @param object The same object that was returned by
     * {@link #instantiateItem(View, int)}.
     */
	@Override
	public void destroyItem(View collection, int position, Object view) {
		((ViewPager) collection).removeView((View) view);
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		if (object instanceof LinearLayout) {
			return view == ((LinearLayout) object);
		} else if (object instanceof HexMapView) {
			return true;
		} else if (object instanceof TextView) {
			return view == ((TextView) object);
		}
		return false;
	}

	
    /**
     * Called when the a change in the shown pages has been completed.  At this
     * point you must ensure that all of the pages have actually been added or
     * removed from the container as appropriate.
     * @param container The containing View which is displaying this adapter's
     * page views.
     */
	@Override
	public void finishUpdate(View arg0) {
		
	}

	@Override
	public void restoreState(Parcelable arg0, ClassLoader arg1) {
	}

	@Override
	public Parcelable saveState() {
		return null;
	}

	@Override
	public void startUpdate(View arg0) {
	}

	@Override
	public String getTitle(int position) {
		String title = new String();
		switch (position) {
		case POSITION_PROFILE:
			title = ctx.getString(R.string.section_war_reports);
			break;
		default:
			break;
		}
		return title;
	}
	
	
}