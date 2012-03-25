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
import com.highexplosive.client.model.Sector;

public class HxJsonUtils {

	private static final String TAG = HxJsonUtils.class.getSimpleName();

	/**
	 * Get the full {@link Message} from the server
	 * @param context
	 * @param messageId
	 * @return
	 */
	public static Message getMessageDetail(Context context, int messageId) {
		Message message = null;
		try {
			JsonReader reader = null;
			if (HxConstants.ONLINE_MODE) {
				// TODO: Implement the online mode
			} else {
				reader = new JsonReader(new InputStreamReader(context
						.getAssets().open("json/type_message_detail.json"),
						"UTF-8"));
			}
			message = parseMessage(reader);
		} catch (IOException e) {
			Log.e(TAG, e.getMessage());
		}
		return message;
	}

	/**
	 * Get a list of {@link Message} from the server related to a {@link Character}
	 * @param context
	 * @param messageId
	 * @return
	 */
	public static ArrayList<Message> getMessageList(Context context,
			int characterId) {
		ArrayList<Message> list = null;
		try {
			JsonReader reader = null;
			if (HxConstants.ONLINE_MODE) {
				// TODO: Implement the online mode
			} else {
				reader = new JsonReader(new InputStreamReader(context
						.getAssets().open("json/type_message_list.json"),
						"UTF-8"));
			}
			list = parseMessageList(reader);
		} catch (IOException e) {
			Log.e(TAG, e.getMessage());
		}
		return list;
	}

	/**
	 * Get the full character from the server
	 * 
	 * @param context
	 * @param declarationId
	 * @return
	 */
	public static Character getCharacterDetail(Context context, int characterId) {
		Character character = null;
		try {
			JsonReader reader = null;
			if (HxConstants.ONLINE_MODE) {
				// TODO: Implement the online mode
			} else {
				reader = new JsonReader(new InputStreamReader(context
						.getAssets().open("json/type_character.json"), 
						"UTF-8"));
			}
			character = parseCharacter(reader);
		} catch (IOException e) {
			Log.e(TAG, e.getMessage());
		}
		return character;
	}

	/**
	 * Get the full declaration from the server
	 * 
	 * @param context
	 * @param declarationId
	 * @return
	 */
	public static Declaration getDeclarationDetail(Context context,
			int declarationId) {
		Declaration declaration = null;
		try {
			JsonReader reader = null;
			if (HxConstants.ONLINE_MODE) {
				// TODO: Implement the online mode
			} else {
				reader = new JsonReader(new InputStreamReader(context
						.getAssets().open("json/type_declaration_detail.json"),
						"UTF-8"));
			}
			declaration = parseDeclaration(reader);
		} catch (IOException e) {
			Log.e(TAG, e.getMessage());
		}
		return declaration;
	}

	/**
	 * Get the list of most recent declarations from the server
	 * 
	 * @param context
	 * @return
	 */
	public static ArrayList<Declaration> getDeclarationList(Context context) {
		ArrayList<Declaration> declarationList = null;
		try {
			JsonReader reader = null;
			if (HxConstants.ONLINE_MODE) {
				// TODO: Implement the online mode
			} else {
				reader = new JsonReader(new InputStreamReader(context
						.getAssets().open("json/type_declaration_list.json"),
						"UTF-8"));
			}
			declarationList = parseDeclarationList(reader);
		} catch (IOException e) {
			Log.e(TAG, e.getMessage());
		}
		return declarationList;
	}

	// ********************************************************************
	// Private and auxiliary methods
	// ********************************************************************
	private static ArrayList<Message> parseMessageList(JsonReader reader) {
		ArrayList<Message> list = new ArrayList<Message>();
		Message message = null;
		try {
			reader.beginArray();
			while (reader.hasNext()) {
				message = parseMessage(reader);
				list.add(message);
			}
			reader.endArray();
		} catch (Exception e) {
			Log.e(TAG, e.getMessage());
		}
		return list;
	}

	private static Message parseMessage(JsonReader reader) throws IOException {
		Message message = new Message();
		reader.beginObject();
		while (reader.hasNext()) {
			String name = reader.nextName();
			if (name.equals("body")) {
				message.setBody(reader.nextString());
			} else if (name.equals("fromId")) {
				message.setFromId(reader.nextInt());
			} else if (name.equals("time")) {
				message.setTime(reader.nextLong());
			} else if (name.equals("favorited")) {
				message.setFavorited(reader.nextBoolean());
			} else if (name.equals("subject")) {
				message.setSubject(reader.nextString());
			} else if (name.equals("fromName")) {
				message.setFromName(reader.nextString());
			} else if (name.equals("messageId")) {
				message.setMessageId(reader.nextInt());
			} else if (name.equals("to")) {
				reader.beginArray();
				while (reader.hasNext()) {
					parseCharacter(reader);
				}
				reader.endArray();
			}
		}
		reader.endObject();
		return message;
	}

	private static Character parseCharacter(JsonReader reader) {
		Character character = new Character();
		try {
			reader.beginObject();
			while (reader.hasNext()) {
				String name = reader.nextName();
				if (name.equals("id")) {
					character.setCharacterId(reader.nextInt());
				} else if (name.equals("completeName")) {
					character.setCompleteName(reader.nextString());
				} else if (name.equals("factionId")) {
					character.setHouse(reader.nextString());
				} else if (name.equals("influence")) {
					character.setInfluence(reader.nextInt());
				} else if (name.equals("role")) {
					character.setRole(reader.nextString());
				} else if (name.equals("homeSectorId")) {
					character.setSectorId(reader.nextInt());
				} else if (name.equals("userId")) {
					character.setUserId(reader.nextInt());
				} else if (name.equals("name")) {
					character.setName(reader.nextString());
				} else if (name.equals("endedTurn")) {
					character.setEndedTurn(reader.nextBoolean());
				} else if (name.equals("sectorsRuled")) {
					character.setSectorRuled(parseSectorList(reader));
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

	private static ArrayList<Sector> parseSectorList(JsonReader reader) throws IOException {
		ArrayList<Sector> list = new ArrayList<Sector>();
		reader.beginArray();
		while (reader.hasNext()) {
			list.add(parseSector(reader));
		}
		reader.endArray();
		return list;
	}

	private static Sector parseSector(JsonReader reader) {
		Sector sector = new Sector();
		try {
			reader.beginObject();
		while (reader.hasNext()) {
			String name = reader.nextName();
			if (name.equals("name")) {
				sector.setName(reader.nextString());
			} else if (name.equals("coordX")) {
				sector.setCoordX(reader.nextInt());
			} else if (name.equals("coordY")) {
				sector.setCoordY(reader.nextInt());
			} else if (name.equals("house")) {
				sector.setFaction(reader.nextString());
			} else if (name.equals("id")) {
				sector.setId(reader.nextInt());
			}
		}
		reader.endObject();
		} catch (IOException e) {
			Log.e(TAG, e.getMessage());
		}
		return sector;
	}

	private static ArrayList<Declaration> parseDeclarationList(JsonReader reader) {
		ArrayList<Declaration> declarationList = new ArrayList<Declaration>();
		Declaration declaration = new Declaration();
		try {
			reader.beginArray();
			while (reader.hasNext()) {
				declaration = parseDeclaration(reader);
				declarationList.add(declaration);	
			}
			reader.endArray();
		} catch (UnsupportedEncodingException e) {
			Log.e(TAG, e.getMessage());
		} catch (IOException e) {
			Log.e(TAG, e.getMessage());
		}
		return declarationList;
	}

	/**
	 * 
	 * @param reader
	 * @throws IOException
	 */
	private static Declaration parseDeclaration(JsonReader reader)
			throws IOException {

		Declaration declaration = new Declaration();

		reader.beginObject();
		while (reader.hasNext()) {
			String name = reader.nextName();
			if (name.equals("communicationId")) {
				declaration.setDeclarationId(reader.nextInt());
			} else if (name.equals("fromId")) {
				declaration.setFromId(reader.nextInt());
			} else if (name.equals("fromName")) {
				declaration.setFromName(reader.nextString());
			} else if (name.equals("subject")) {
				declaration.setSubject(reader.nextString());
			} else if (name.equals("karma")) {
				declaration.setKarma(reader.nextInt());
			} else if (name.equals("body")) {
				declaration.setBody(reader.nextString());
				declaration.setBodyResume(declaration.getBody().substring(0, 50) + "...");
			} else if (name.equals("time")) {
				declaration.setTime(reader.nextLong());
			} else if (name.equals("favorited")) {
				declaration.setFavorited(reader.nextBoolean());
			} else if (name.equals("publishedIn")) {
				declaration.setPublished(reader.nextString());
			}
		}
		reader.endObject();

		return declaration;
	}


	private static JsonReader retrieveJSonFromUrl(String string) {
		// TODO Get JSon From Server URL
		return null;
	}

}
