package com.highexplosive.client.activities;

import com.highexplosive.client.HxConstants;
import com.highexplosive.client.HxJsonUtils;
import com.highexplosive.client.R;
import com.highexplosive.client.model.Character;

import android.app.Activity;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.widget.TextView;

public class ProfileActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.profile);

		if (HxConstants.ONLINE_MODE) {
			
		} else {
			initOfflineContent();
		}
	}

	private void initOfflineContent() {
		Character character = HxJsonUtils.getCharacterDetail(this, 0);

		((TextView) findViewById(R.id.profileName))
				.setText(character.getName());
		((TextView) findViewById(R.id.profileKarma)).setText(""
				+ character.getInfluence());
		((TextView) findViewById(R.id.profileDeclarations)).setText(""
				+ character.getNumberOfDeclarations());
		((TextView) findViewById(R.id.profileRole))
				.setText(character.getRole());
		((TextView) findViewById(R.id.profileSince)).setText(DateUtils
				.formatDateTime(this, character.getCreationDate(),
						DateUtils.FORMAT_24HOUR));
		((TextView) findViewById(R.id.profileBio)).setText(character.getName());
	}

}
