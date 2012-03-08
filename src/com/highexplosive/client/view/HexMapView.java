package com.highexplosive.client.view;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;

import com.highexplosive.client.model.HexGridCell;


public class HexMapView extends View {

	private Context context;

    private static final int GRID_OFFSET_X = 100;
    private static final int GRID_OFFSET_Y = 100;

    private static final int NOT_VISIBLE = 0;
    private static final int HOUSE_KURITA = 1;
    private static final int HOUSE_LIAO = 2;
    private static final int HOUSE_DAVION = 3;
    private static final int HOUSE_STEINER = 4;
    private static final int HOUSE_MARIK = 5;

    private static final int NUM_HEX_CORNERS = 6;
    private static final int CELL_RADIUS = 40;

	private static final String TAG = HexMapView.class.getSimpleName();

    private int[][] cellGrid = null;
    
    private int[] mCornersX = new int[NUM_HEX_CORNERS];
    private int[] mCornersY = new int[NUM_HEX_CORNERS];

	private Integer sizeX = 0;
	private Integer sizeY = 0;

    private static HexGridCell mCellMetrics = new HexGridCell(CELL_RADIUS);
    
    public HexMapView(Context context, AttributeSet set) {
    	super(context, set);
    	this.context = context;
    	createMap();
    }
    

	protected void onDraw(Canvas canvas) {

		Paint mPaint = new Paint();
		mPaint.setStrokeWidth(8); 
		mPaint.setColor(Color.TRANSPARENT); 
		mPaint.setStyle(Paint.Style.FILL); 
		mPaint.setAntiAlias(true); // no jagged edges, etc.

		for (int j = 0; j < sizeX; j++) {
			for (int i = 0; i < sizeY; i++) {
				mCellMetrics.setCellIndex(i, j);
				if (cellGrid[j][i] != 0) {

					mCellMetrics.computeCorners(mCornersX, mCornersY);
					Path path = mCellMetrics.createPath();
					switch (cellGrid[j][i]) {
					case HOUSE_KURITA:
						paintCell(Color.RED, canvas, mPaint, path);
						break;
					case HOUSE_DAVION:
						paintCell(Color.YELLOW, canvas, mPaint, path);
						break;
					case HOUSE_LIAO:
						paintCell(Color.GREEN, canvas, mPaint, path);
						break;
					case HOUSE_MARIK:
						paintCell(Color.MAGENTA, canvas, mPaint, path);
						break;
					case HOUSE_STEINER:
						paintCell(Color.GRAY, canvas, mPaint, path);
						break;
					default:
						break;
					}
				}
			}
		}
	}

    /**
	 * Paint a sector. First fill, then stroke to set the black border. Sadly
	 * FillStroke Style doesn't work properly.
	 * 
	 * @param canvas
	 * @param mPaint
	 * @param path
	 */
	private void paintCell(int color, Canvas canvas, Paint mPaint, Path path) {
		mPaint.setColor(color);
		mPaint.setStyle(Paint.Style.FILL);
		canvas.drawPath(path, mPaint);
		mPaint.setStyle(Paint.Style.STROKE);
		mPaint.setColor(Color.BLACK);
		canvas.drawPath(path, mPaint);
	}
    
	/**
	 * Read the JSon map and create the required structure for it
	 */
	private void createMap() {
		if (cellGrid != null) {
			return;
		}
		JsonReader reader;
		try {
			reader = new JsonReader(new InputStreamReader(context.getAssets()
					.open("map/hx_map.json"), "UTF-8"));

			reader.beginObject();
			while (reader.hasNext()) {
				String name = reader.nextName();
				if (name.equals("map")) {
					readMap(reader, name);
				}
			}
			reader.endObject();
		} catch (UnsupportedEncodingException e) {
			Log.e(TAG, e.getMessage());
		} catch (IOException e) {
			Log.e(TAG, e.getMessage());
		}


	}

	/**
	 * Map
	 * @param reader
	 * @param name
	 * @throws IOException
	 */
	private void readMap(JsonReader reader, String name) throws IOException {
		reader.beginObject();

		while (reader.hasNext()) {
			name = reader.nextName();
			if (name.equals("size")) {
				ArrayList<Integer> list = new ArrayList<Integer>();
				reader.beginArray();
				while (reader.hasNext()) {
					list.add(reader.nextInt());
				}
				reader.endArray();
				cellGrid = new int[list.get(0)][list.get(1)];
				sizeX = list.get(0);
				sizeY = list.get(1);
			} else if (name.equals("column")) {
				reader.beginObject();
				readColumn(reader, name);
				reader.endObject();
			}
		}
		reader.endObject();
	}
	
	/**
	 * Column 
	 * @param reader
	 * @param name
	 * @throws IOException
	 */
	private void readColumn(JsonReader reader, String name) throws IOException {
		int i = 0;
		int j = 0;
		while (reader.hasNext()) {
			name = reader.nextName();
			if (name.equals("nCol")) {
				i = reader.nextInt();
			} else if (name.equals("tile")) {
				reader.beginObject();
				while (reader.hasNext()) {
					name = reader.nextName();
					if (name.equals("name")) {
						reader.nextString();
					} else if (name.equals("y")) {
						j = reader.nextInt();
					} else if (name.equals("faction")) {
						String faction = reader.nextString();
						if ("liao".equals(faction)) {
							cellGrid[i][j] = HOUSE_LIAO;
						} else if ("kurita".equals(faction)) {
							cellGrid[i][j] = HOUSE_KURITA;
						} else if ("steiner".equals(faction)) {
							cellGrid[i][j] = HOUSE_STEINER;
						} else if ("davion".equals(faction)) {
							cellGrid[i][j] = HOUSE_DAVION;
						} else if ("marik".equals(faction)) {
							cellGrid[i][j] = HOUSE_MARIK;
						} else {
							cellGrid[i][j] = NOT_VISIBLE;
						}
					}
				}
				reader.endObject();
			}
		}
	}

//	@Override
//	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//		int parentWidth = MeasureSpec.getSize(widthMeasureSpec);
//		int parentHeight = MeasureSpec.getSize(heightMeasureSpec);
//		this.setMeasuredDimension(parentWidth, parentHeight / 2);
//		this.setLayoutParams(new LinearLayout.LayoutParams(parentWidth,
//				parentHeight / 2));
//		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//	}

}
