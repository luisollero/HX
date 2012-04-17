package com.highexplosive.client.view;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.highexplosive.client.HxConstants;
import com.highexplosive.client.R;
import com.highexplosive.client.activities.WarOrderCreationActivity;
import com.highexplosive.client.activities.WarReportDetailActivity;
import com.highexplosive.client.model.RegimentOrder;
import com.highexplosive.client.model.WarReport;
import com.viewpagerindicator.TitleProvider;

public class WarPagerAdapter extends PagerAdapter implements TitleProvider {

	@SuppressWarnings("unused")
	private static final String TAG = WarPagerAdapter.class.getName();
	
	public static final int POSITION_REPORTS = 0;
	public static final int POSITION_ORDERS = 1;

	public static int NUM_VIEWS = 2;
	private Context ctx;
	
	private ListView reportsListView = null;
	private ArrayList<WarReport> warReportList = null;
	private ArrayList<RegimentOrder> warOrdersList = null;
	private WarReportAdapter reportAdapter = null;

	private ListView ordersListView;


	public WarPagerAdapter(Context context) {
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
		case POSITION_REPORTS:
			linearLayout = reportSection(collection, inflater);
			break;
		case POSITION_ORDERS:
			linearLayout = orderSection(collection, inflater);
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
	private LinearLayout orderSection(View collection, LayoutInflater inflater) {
		LinearLayout linearLayout = (LinearLayout) inflater.inflate(R.layout.war_orders, null);
		
		WarOrderAdapter warOrdersAdapter = new WarOrderAdapter(ctx, android.R.layout.simple_list_item_1);
		ordersListView = (ListView) linearLayout.findViewById(R.id.warOrdersList);
		ordersListView.setAdapter(warOrdersAdapter);
		
		if (warOrdersList == null) {
			warOrdersList = new ArrayList<RegimentOrder>();
			for (int i = 0; i < 5; i++) {
				warOrdersList.add(new RegimentOrder(i, ctx.getString(R.string.dummy_text), ctx.getString(R.string.dummy_text)));
			}
		}
		
		for (WarReport report : warReportList) {
			warOrdersAdapter.add(report);
		}
		
		((ViewPager) collection).addView(linearLayout, 0);
		return linearLayout;
	}

	/**
	 * Latest news section
	 * @param collection
	 * @param inflater
	 * @return
	 */
	private LinearLayout reportSection(View collection, LayoutInflater inflater) {
		LinearLayout linearLayout = (LinearLayout) inflater.inflate(R.layout.war_reports, null);

		reportAdapter = new WarReportAdapter(ctx, android.R.layout.simple_list_item_1);
		reportsListView = (ListView) linearLayout.findViewById(R.id.warReportsList);
		reportsListView.setAdapter(reportAdapter);
		
		if (warReportList == null) {
			if (HxConstants.ONLINE_MODE) {
				//TODO: War reports online mode
			} else {
				warReportList = new ArrayList<WarReport>();
				for (int i = 0; i < 5; i++) {
					warReportList.add(new WarReport(1, ctx.getString(R.string.dummy_text), ctx.getString(R.string.dummy_text_long)));
				}
			}
		}
		
		for (WarReport report : warReportList) {
			reportAdapter.add(report);
		}
		
		((ViewPager) collection).addView(linearLayout, 0);
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
		case POSITION_REPORTS:
			title = ctx.getString(R.string.section_war_reports);
			break;
		case POSITION_ORDERS:
			title = ctx.getString(R.string.section_war_orders);
			break;
		default:
			break;
		}
		return title;
	}

	
	public void deleteReports() {
		if (reportAdapter != null) {
			ArrayList<Integer> list = reportAdapter.getItemsSelected();
			for (Integer integer : list) {
				Toast.makeText(ctx, "Id to delete: " + integer, Toast.LENGTH_LONG).show();
			}
		}
	}

	public void addOrder() {
		Intent intent = new Intent(ctx, WarOrderCreationActivity.class);
		ctx.startActivity(intent);
	}
	
	
}