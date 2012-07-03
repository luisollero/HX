package com.highexplosive.client.view;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.JsonReader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.highexplosive.client.HxConstants;
import com.highexplosive.client.HxJsonUtils;
import com.highexplosive.client.HxLinks;
import com.highexplosive.client.R;
import com.highexplosive.client.activities.MessageCreateActivity;
import com.highexplosive.client.model.Declaration;
import com.highexplosive.client.model.Faction;
import com.highexplosive.client.model.Message;
import com.highexplosive.client.util.HxUtil;
import com.viewpagerindicator.TitleProvider;

public class NewsPagerAdapter extends PagerAdapter implements TitleProvider {

	private static final String TAG = NewsPagerAdapter.class.getName();
	
	public static final int POSITION_LATEST = 0;
	public static final int POSITION_HOUSE = 1;
	public static final int POSITION_MESSAGES = 2;

	public static int NUM_VIEWS = 3;
	private Context ctx;
	
	private ListView declarationsListView = null;
	private ListView messagesListView = null;
	private ArrayList<Declaration> declarationList = null;
	private ArrayList<Declaration> factionDeclarationList = null;
	private ArrayList<Message> characterMessageList = null;
	private MessageAdapter messagesAdapter;

	private HexMapView innerSphereMap;

	private LinearLayout newsLinearLayout;

	private Integer userId = 1;
	private Faction faction;

	private LinearLayout newsHouseLinearLayout;

	private DeclarationAdapter houseDeclarationsAdapter;

	private LinearLayout messagesLinearLayout;

	public NewsPagerAdapter(Context context, Faction faction) {
		this.ctx = context;
		this.faction = faction;
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
		case POSITION_LATEST:
			linearLayout = latestSection(collection, inflater);
			break;
		case POSITION_HOUSE:
			linearLayout = houseSection(collection, inflater);
			break;
		case POSITION_MESSAGES:
			linearLayout = messageSection(collection, inflater);
			break;
		default:
			break;
		}

		return linearLayout;
	}

	/**
	 * Messages section
	 * @param collection
	 * @param inflater
	 * @return
	 */
	private LinearLayout messageSection(View collection, LayoutInflater inflater) {
		
		messagesLinearLayout = (LinearLayout) inflater.inflate(R.layout.news_messages, null);
		
		messagesAdapter = new MessageAdapter(ctx, android.R.layout.simple_list_item_1);
		messagesListView = (ListView) messagesLinearLayout.findViewById(R.id.userMessageList);
		messagesListView.setAdapter(messagesAdapter);
		
		if (characterMessageList == null) {
			if (HxConstants.ONLINE_MODE) {
				new LoadMessagesTask().execute(userId);
			} else {
				try {
					characterMessageList = HxJsonUtils.getMessageList(ctx, ctx.getAssets().open("json/type_message_list.json"));
				} catch (IOException e) {
					Log.e(TAG, e.getMessage());
				}
				for (Message message : characterMessageList) {
					messagesAdapter.add(message);
				}
			}
		}
		
		
		((ViewPager) collection).addView(messagesLinearLayout, 0);
		return messagesLinearLayout;
	}

	/**
	 * House/Faction section
	 * @param collection
	 * @param inflater
	 * @return
	 */
	private LinearLayout houseSection(View collection, LayoutInflater inflater) {
		
		if (newsHouseLinearLayout == null) {
			
			newsHouseLinearLayout = (LinearLayout) inflater.inflate(R.layout.news_house, null);

			houseDeclarationsAdapter = new DeclarationAdapter(ctx, android.R.layout.simple_list_item_1);
			declarationsListView = (ListView) newsHouseLinearLayout.findViewById(R.id.factionDeclarationList);
			declarationsListView.setAdapter(houseDeclarationsAdapter);
			
			if (HxConstants.ONLINE_MODE) {
				((HexMapView)newsHouseLinearLayout.findViewById(R.id.factionMap)).createMap(HxLinks.MAP_HOUSE + faction.getName());
			} else {
				((HexMapView)newsHouseLinearLayout.findViewById(R.id.factionMap)).createMap("map/hx_liao_map.json");
			}
			
//			((HexMapView)newsHouseLinearLayout.findViewById(R.id.factionMap)).recalculateMapDimensions();
//			((HexMapView)newsHouseLinearLayout.findViewById(R.id.factionMap)).invalidate();

			new LoadHouseDeclarationsTask().execute();
			
			((ViewPager) collection).addView(newsHouseLinearLayout, 0);
		}
		return newsHouseLinearLayout;
	}
	

	/**
	 * Latest news section, with lazy loading.
	 * @param collection
	 * @param inflater
	 * @return
	 */
	private LinearLayout latestSection(View collection, LayoutInflater inflater) {

		if (newsLinearLayout == null) {
			newsLinearLayout = (LinearLayout) inflater.inflate(R.layout.news_latest, null);
		
			if (innerSphereMap == null) {
				innerSphereMap = ((HexMapView)newsLinearLayout.findViewById(R.id.isMap));
				if (HxConstants.ONLINE_MODE) {
					innerSphereMap.createMap(HxLinks.MAP);
				} else {
					innerSphereMap.createMap("map/hx_map_prod.json");
				}
			}
	
			new LoadDeclarationsTask().execute();
			
//			innerSphereMap.recalculateMapDimensions();
//			innerSphereMap.invalidate();
			
		}

		((ViewPager) collection).addView(newsLinearLayout, 0);
		return newsLinearLayout;
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
		default:
			break;
		}
		return title;
	}

	public void createMessage() {
		Intent intent = new Intent(ctx, MessageCreateActivity.class);
		ctx.startActivity(intent);
	}
	
	public void deleteMessages() {
		if (messagesListView != null) {
			ArrayList<Integer> list = messagesAdapter.getItemsSelected();
			for (Integer integer : list) {
				Toast.makeText(ctx, "Id to delete: " + integer, Toast.LENGTH_LONG).show();
			}
		}
	}
	

	/**
	 * Helper class to do in background the call to the server
	 * @author luis.pena
	 *
	 */
	class LoadHouseDeclarationsTask extends AsyncTask<Void, Void, Void> {

		private InputStream is;

		@Override
		protected Void doInBackground(Void... params) {
			if (HxConstants.ONLINE_MODE) {
				is = HxUtil.retrieveInputStreamFromURL(HxLinks.DECLARATIONS);
			} else {
				try {
					is = ctx.getAssets().open("json/type_declaration_list.json");
				} catch (IOException e) {
					Log.e(TAG, e.getMessage());
				}
			}
			factionDeclarationList = HxJsonUtils.getDeclarationList(ctx, is);
			return null;
		}
		
		
		@Override
		protected void onPostExecute(Void result) {
			for (Declaration declaration : factionDeclarationList) {
				houseDeclarationsAdapter.add(declaration);
			}
			super.onPostExecute(result);
		}
	}

	/**
	 * Helper class to do in background the call to the server
	 * @author luis.pena
	 *
	 */
	class LoadDeclarationsTask extends AsyncTask<Void, Void, Void> {
		
		private InputStream is;
		
		@Override
		protected Void doInBackground(Void... params) {
			if (HxConstants.ONLINE_MODE) {
				is = HxUtil.retrieveInputStreamFromURL(HxLinks.DECLARATIONS);
			} else {
				try {
					is = ctx.getAssets().open("json/type_declaration_list.json");
				} catch (IOException e) {
					Log.e(TAG, e.getMessage());
				}
			}
			declarationList = HxJsonUtils.getDeclarationList(ctx, is);
			return null;
		}
		
		
		@Override
		protected void onPostExecute(Void result) {
			
			DeclarationAdapter messageAdapter = new DeclarationAdapter(ctx, android.R.layout.simple_list_item_1);
			declarationsListView = (ListView) newsLinearLayout.findViewById(R.id.mainDeclarationList);
			declarationsListView.setAdapter(messageAdapter);
			
			for (Declaration declaration : declarationList) {
				messageAdapter.add(declaration);
			}
			super.onPostExecute(result);
		}
	}

	/**
	 * Helper class to do in background the call to the server
	 * @author luis.pena
	 *
	 */
	class LoadMessagesTask extends AsyncTask<Integer, Void, Void> {
		
		private InputStream is;
		
		@Override
		protected Void doInBackground(Integer... params) {
			is = HxUtil.retrieveInputStreamFromURL(HxLinks.MESSAGES + params[0] + ".json");
			characterMessageList = HxJsonUtils.getMessageList(ctx, is);
			return null;
		}
		
		
		@Override
		protected void onPostExecute(Void result) {
			
			for (Message message : characterMessageList) {
				messagesAdapter.add(message);
			}

			super.onPostExecute(result);
		}
	}
	
}