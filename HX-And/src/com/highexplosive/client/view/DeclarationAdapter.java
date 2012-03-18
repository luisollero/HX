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
import android.widget.TextView;

import com.highexplosive.client.R;
import com.highexplosive.client.activities.DeclarationDetailActivity;
import com.highexplosive.client.model.Declaration;

public class DeclarationAdapter extends ArrayAdapter<Declaration> {

	private ArrayList<Declaration> items;

	public DeclarationAdapter(Context context, int textViewResourceId) {
		super(context, textViewResourceId);
	}

	@Override
	public void add(Declaration object) {
		if (this.items == null) {
			this.items = new ArrayList<Declaration>();
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
					.inflate(R.layout.declaration_row, null);

			if (items.get(position) != null) {
				((TextView)view.findViewById(R.id.declarationSubject)).setText(items.get(position).getSubject());
				((TextView)view.findViewById(R.id.declarationBodyResume)).setText(items.get(position).getBodyResume());
				
				view.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent intent = new Intent(getContext(), DeclarationDetailActivity.class);
						Bundle bundle = new Bundle();
						bundle.putInt(Declaration.DECLARATION_ID, items.get(position).getDeclarationId());
						intent.putExtras(bundle);
						getContext().startActivity(intent);
					}
				});
			}
		}
		
		return view;
	}
	
}
