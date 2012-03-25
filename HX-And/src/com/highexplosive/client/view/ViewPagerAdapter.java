package com.highexplosive.client.view;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.format.DateUtils;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.highexplosive.client.HxJsonUtils;
import com.highexplosive.client.R;
import com.highexplosive.client.model.Character;
import com.highexplosive.client.model.Declaration;
import com.highexplosive.client.model.Message;
import com.viewpagerindicator.TitleProvider;

public class ViewPagerAdapter extends PagerAdapter implements TitleProvider {

	
	public static final int POSITION_LATEST = 0;
	public static final int POSITION_HOUSE = 1;
	public static final int POSITION_MESSAGES = 2;
	public static final int POSITION_PROFILE = 3;

//	private static String[] titles = new String[4];
	
	public static int NUM_VIEWS = 4;
	private Context ctx;
	
	private int currentPosition = 0;
	private ListView declarationsListView = null;
	private ListView messagesListView = null;
	private ArrayList<Declaration> declarationList = null;
	private ArrayList<Declaration> factionDeclarationList = null;
	private ArrayList<Message> characterMessageList = null;
	private MessageAdapter messagesAdapter;

	public ViewPagerAdapter(Context context) {
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
		currentPosition = position;
		
		switch (position) {
		case POSITION_LATEST:
			
			linearLayout = (LinearLayout) inflater.inflate(R.layout.main_latest, null);

			((HexMapView)linearLayout.findViewById(R.id.isMap)).createMap("map/hx_map_prod.json");

			DeclarationAdapter messageAdapter = new DeclarationAdapter(ctx, android.R.layout.simple_list_item_1);
			declarationsListView = (ListView) linearLayout.findViewById(R.id.mainDeclarationList);
			declarationsListView.setAdapter(messageAdapter);

			if (declarationList == null) {
				declarationList = new ArrayList<Declaration>();
				declarationList = HxJsonUtils.getDeclarationList(ctx);
			}

			for (Declaration declaration : declarationList) {
				messageAdapter.add(declaration);
			}

			((ViewPager) collection).addView(linearLayout, 0);
			break;
		case POSITION_HOUSE:
			linearLayout = (LinearLayout) inflater.inflate(R.layout.main_house, null);
			
			((HexMapView)linearLayout.findViewById(R.id.factionMap)).createMap("map/hx_liao_map.json");
			((HexMapView)linearLayout.findViewById(R.id.factionMap)).recalculateMapDimensions();
			
			DeclarationAdapter houseDeclarationsAdapter = new DeclarationAdapter(ctx, android.R.layout.simple_list_item_1);
			declarationsListView = (ListView) linearLayout.findViewById(R.id.factionDeclarationList);
			declarationsListView.setAdapter(houseDeclarationsAdapter);

			if (factionDeclarationList == null) {
				factionDeclarationList = new ArrayList<Declaration>();
				factionDeclarationList = HxJsonUtils.getDeclarationList(ctx);
			}

			for (Declaration declaration : factionDeclarationList) {
				houseDeclarationsAdapter.add(declaration);
			}
			
			((ViewPager) collection).addView(linearLayout, 0);
			break;
		case POSITION_MESSAGES:
			linearLayout = (LinearLayout) inflater.inflate(R.layout.main_messages, null);
			
			messagesAdapter = new MessageAdapter(ctx, android.R.layout.simple_list_item_1);
			messagesListView = (ListView) linearLayout.findViewById(R.id.userMessageList);
			messagesListView.setAdapter(messagesAdapter);
			
			if (characterMessageList == null) {
				characterMessageList = new ArrayList<Message>();
				characterMessageList = HxJsonUtils.getMessageList(ctx, 1);
			}
			
			for (Message message : characterMessageList) {
				messagesAdapter.add(message);
			}
			
			((ViewPager) collection).addView(linearLayout, 0);
			break;
		case POSITION_PROFILE:
			linearLayout = (LinearLayout) inflater.inflate(R.layout.main_profile, null);
			Character character = HxJsonUtils.getCharacterDetail(ctx, 0);
			
			((TextView)linearLayout.findViewById(R.id.profileName)).setText(character.getName());
			((TextView)linearLayout.findViewById(R.id.profileKarma)).setText("" + character.getInfluence());
			((TextView)linearLayout.findViewById(R.id.profileDeclarations)).setText("" + character.getNumberOfDeclarations());
			((TextView)linearLayout.findViewById(R.id.profileRole)).setText(character.getRole());
			((TextView)linearLayout.findViewById(R.id.profileSince)).setText(DateUtils
					.formatDateTime(ctx, character.getCreationDate(),
							DateUtils.FORMAT_24HOUR));
			((TextView)linearLayout.findViewById(R.id.profileBio)).setText(character.getName());
			
			((ViewPager) collection).addView(linearLayout, 0);
			break;
		default:
			TextView tv = new TextView(ctx);
			tv.setTextColor(Color.WHITE);
			tv.setTextSize(30);
			tv.setText("Other stuff " + position);
			linearLayout = new LinearLayout(ctx);
			linearLayout.addView(tv);
			((ViewPager) collection).addView(linearLayout, 0);
			break;
		}

		return linearLayout;
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
		case POSITION_LATEST:
			title = ctx.getString(R.string.section_latest);
			break;
		case POSITION_HOUSE:
			title = ctx.getString(R.string.section_house);
			break;
		case POSITION_MESSAGES:
			title = ctx.getString(R.string.section_messages);
			break;
		case POSITION_PROFILE:
			title = ctx.getString(R.string.section_profile);
			break;
		default:
			break;
		}
		return title;
	}

	
	public void deleteMessages() {
		
		if (messagesListView != null) {
			ArrayList<Integer> list = messagesAdapter.getItemsSelected();
			for (Integer integer : list) {
				Toast.makeText(ctx, "Id to delete: " + integer, Toast.LENGTH_LONG).show();
			}
			
		}
	}
}