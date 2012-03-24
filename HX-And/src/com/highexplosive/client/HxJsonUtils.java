package com.highexplosive.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import android.content.Context;
import android.util.JsonReader;
import android.util.Log;

import com.highexplosive.client.model.Character;
import com.highexplosive.client.model.Declaration;
import com.highexplosive.client.model.Message;

public class HxJsonUtils {

	private static final String TAG = HxJsonUtils.class.getSimpleName();

	public static Message getMessageDetail(Context context, int messageId) {
		Message message = null;
		try {
			message = parseMessage(context.getAssets().open(
					"json/type_message.json"));
		} catch (IOException e) {
			Log.e(TAG, e.getMessage());
		}
		return message;
	}
	

	/**
	 * Get the full character from the server
	 * @param context
	 * @param declarationId
	 * @return
	 */
	public static Character getCharacterDetail(Context context, int characterId) {
		Character character = null;
		try {
			character = parseCharacter(context.getAssets().open(
					"json/type_character.json"));
		} catch (IOException e) {
			Log.e(TAG, e.getMessage());
		}
		return character;
	}
	

	/**
	 * Get the full declaration from the server
	 * @param context
	 * @param declarationId
	 * @return
	 */
	public static Declaration getDeclarationDetail(Context context, int declarationId) {
		Declaration declaration = null;
		try {
			declaration = parseDeclaration(context.getAssets().open(
					"json/type_declaration_detail.json"));
		} catch (IOException e) {
			Log.e(TAG, e.getMessage());
		}
		return declaration;
	}

	/**
	 * Get the list of most recent declarations from the server
	 * @param context
	 * @return
	 */
	public static ArrayList<Declaration> getDeclarationList(Context context) {
		ArrayList<Declaration> declarationList = null;
		try {
			if (HxConstants.ONLINE_MODE) {
				declarationList = parseDeclarationList(retrieveJSonFromUrl("URL_TO_DECLARATIONS"));
			} else {
				declarationList = parseDeclarationList(context.getAssets().open(
						"json/type_declaration_list.json"));
			}
		} catch (IOException e) {
			Log.e(TAG, e.getMessage());
		}
		return declarationList;
	}


	// Private and aux methods
	private static Message parseMessage(InputStream open) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private static Character parseCharacter(InputStream is) {
		Character character = new Character();
		JsonReader reader;
		try {
			reader = new JsonReader(new InputStreamReader(is, "UTF-8"));

			reader.beginObject();
			while (reader.hasNext()) {
				String name = reader.nextName();
				if (name.equals("playerid")) {
					character.setCharacterId(reader.nextString());
				} else if (name.equals("name")) {
					character.setName(reader.nextString());
				} else if (name.equals("faction")) {
					character.setHouse(reader.nextString());
				} else if (name.equals("karma")) {
					character.setKarma(reader.nextInt());
				} else if (name.equals("role")) {
					character.setRole(reader.nextInt());
				} else if (name.equals("bio")) {
					character.setBio(reader.nextString());
				} else if (name.equals("creationdate")) {
					character.setCreationDate(reader.nextLong());
				} else if (name.equals("declarations")) {
					character.setNumberOfDeclarations(reader.nextInt());
				} else if (name.equals("endedturn")) {
					character.setTurnEnded(reader.nextBoolean());
				}
			}
			reader.endObject();
		} catch (UnsupportedEncodingException e) {
			Log.e(TAG, e.getMessage());
		} catch (IOException e) {
			Log.e(TAG, e.getMessage());
		}
		return character;
	}
	
	private static ArrayList<Declaration> parseDeclarationList(InputStream is) {
		ArrayList<Declaration> declarationList = new ArrayList<Declaration>();
		JsonReader reader;
		try {
			reader = new JsonReader(new InputStreamReader(is, "UTF-8"));

			reader.beginObject();
			while (reader.hasNext()) {
				String name = reader.nextName();
				if (name.equals("declaration")) {
					declarationList.add(readDeclarationInList(reader));
				}
			}
			reader.endObject();
		} catch (UnsupportedEncodingException e) {
			Log.e(TAG, e.getMessage());
		} catch (IOException e) {
			Log.e(TAG, e.getMessage());
		}
		return declarationList;
	}

	/**
	 * "declaration_id" : 1 "from_id": 1, "from_name": "Kineas Liao", "subject":
	 * "Testing declarations", "body_resume":
	 * "Just another declaration test that I ..."
	 * 
	 * @param reader
	 * @throws IOException 
	 */
	private static Declaration readDeclarationInList(JsonReader reader) throws IOException {
		
		Declaration declaration = new Declaration();
		
		reader.beginObject();
		while (reader.hasNext()) {
			String name = reader.nextName();
			if (name.equals("declaration_id")) {
				declaration.setDeclarationId(reader.nextInt());
			} else if (name.equals("from_id")) {
				declaration.setFromId(reader.nextInt());
			} else if (name.equals("from_name")) {
				declaration.setFromName(reader.nextString());
			} else if (name.equals("subject")) {
				declaration.setSubject(reader.nextString());
			} else if (name.equals("body_resume")) {
				declaration.setBodyResume(reader.nextString());
			}
		} 
		reader.endObject();
		
		return declaration;
	}

	private static Declaration parseDeclaration(InputStream is) {
		
		Declaration declaration = null;
		JsonReader reader;
		try {
			reader = new JsonReader(new InputStreamReader(is, "UTF-8"));

			reader.beginObject();
			while (reader.hasNext()) {
				String name = reader.nextName();
				if (name.equals("declaration")) {
					declaration = readDeclaration(reader);
				}
			}
			reader.endObject();
		} catch (UnsupportedEncodingException e) {
			Log.e(TAG, e.getMessage());
		} catch (IOException e) {
			Log.e(TAG, e.getMessage());
		}
		return declaration;
	}

	private static Declaration readDeclaration(JsonReader reader) {
		Declaration declaration = new Declaration();

		try {
			reader.beginObject();
			while (reader.hasNext()) {
				String name = reader.nextName();
				if (name.equals("declaration_id")) {
					declaration.setDeclarationId(reader.nextInt());
				} else if (name.equals("from_id")) {
					declaration.setFromId(reader.nextInt());
				} else if (name.equals("from_name")) {
					declaration.setFromName(reader.nextString());
				} else if (name.equals("subject")) {
					declaration.setSubject(reader.nextString());
				} else if (name.equals("body")) {
					declaration.setBody(reader.nextString());
				} else if (name.equals("published")) {
					declaration.setPublished(reader.nextString());
				} else if (name.equals("karma")) {
					declaration.setKarma(reader.nextInt());
				} else if (name.equals("time")) {
					declaration.setTime(reader.nextLong());
				} else if (name.equals("favorited")) {
					declaration.setFavorited(reader.nextBoolean());
				}
			}
			reader.endObject();
		} catch (IOException e) {
			Log.e(TAG, e.getMessage());
		}

		return declaration;
	}

	private static InputStream retrieveJSonFromUrl(String string) {
		// TODO Auto-generated method stub
		return null;
	}

}
