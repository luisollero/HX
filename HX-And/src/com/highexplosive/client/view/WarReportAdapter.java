package com.highexplosive.client.view;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.highexplosive.client.R;
import com.highexplosive.client.activities.MessageDetailActivity;
import com.highexplosive.client.activities.WarReportDetailActivity;
import com.highexplosive.client.model.Message;
import com.highexplosive.client.model.WarReport;

public class WarReportAdapter extends ArrayAdapter<WarReport> {

	private ArrayList<WarReport> items;
	private ArrayList<Integer> itemsSelected = new ArrayList<Integer>();

	public WarReportAdapter(Context context, int textViewResourceId) {
		super(context, textViewResourceId);
	}

	@Override
	public void add(WarReport object) {
		if (this.items == null) {
			this.items = new ArrayList<WarReport>();
		}

		this.items.add(object);
		super.add(object);
	}

	@Override
	public View getView(final int position, View convertView,
			ViewGroup parent) {
		View view = convertView;
		if (view == null) {
			LayoutInflater layoutInflater = (LayoutInflater) getContext()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = layoutInflater
					.inflate(R.layout.war_report_row, null);

			if (items.get(position) != null) {
				((TextView)view.findViewById(R.id.warReportResult)).setText(items.get(position).getResult());
				
				view.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent intent = new Intent(getContext(), WarReportDetailActivity.class);
						getContext().startActivity(intent);
					}
				});
				
				((CheckBox)view.findViewById(R.id.messageCheckBox)).setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						if (((CheckBox)v).isChecked()) {
							itemsSelected.add(new Integer(position));
						} else {
							itemsSelected.remove(new Integer(position));
						}
					}
				});
			}
		}
		
		return view;
	}
	
	public ArrayList<Integer> getItemsSelected() {
		ArrayList<Integer> list = new ArrayList<Integer>();
		
		for (Integer position : itemsSelected) {
			list.add(getItem(position).getReportId());
		}
		return list;
	}
}
