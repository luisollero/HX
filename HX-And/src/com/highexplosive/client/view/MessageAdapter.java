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
import com.highexplosive.client.model.Message;

public class MessageAdapter extends ArrayAdapter<Message> {

	private ArrayList<Message> items;
	private ArrayList<Integer> itemsSelected = new ArrayList<Integer>();

	public MessageAdapter(Context context, int textViewResourceId) {
		super(context, textViewResourceId);
	}

	@Override
	public void add(Message object) {
		if (this.items == null) {
			this.items = new ArrayList<Message>();
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
					.inflate(R.layout.message_row, null);

			if (items.get(position) != null) {
				TextView text = ((TextView)view.findViewById(R.id.messageSubject));
				text.setText(items.get(position).getSubject());
				
				view.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent intent = new Intent(getContext(), MessageDetailActivity.class);
						Bundle bundle = new Bundle();
						bundle.putInt(Message.MESSAGE_ID, items.get(position).getMessageId());
						intent.putExtras(bundle);
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
			list.add(getItem(position).getMessageId());
		}
		return list;
	}
	
}
