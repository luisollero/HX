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
    private static final int CELL_RADIUS = 15;

	private static final String TAG = HexMapView.class.getSimpleName();

    private int[][] cellGrid = null;
    
    private int[] mCornersX = new int[NUM_HEX_CORNERS];
    private int[] mCornersY = new int[NUM_HEX_CORNERS];

	private Integer sizeX = 0;
	private Integer sizeY = 0;

    private static HexGridCell cellMetrics = new HexGridCell(CELL_RADIUS);
    private boolean mapInitialized = false;
    
    public HexMapView(Context context, AttributeSet set) {
    	super(context, set);
    	this.context = context;
    }
    

	protected void onDraw(Canvas canvas) {

		if (mapInitialized) {
			Paint mPaint = new Paint();
			mPaint.setStrokeWidth(8); 
			mPaint.setColor(Color.TRANSPARENT); 
			mPaint.setStyle(Paint.Style.FILL); 
			mPaint.setAntiAlias(true); // no jagged edges, etc.
			
			for (int j = 0; j < sizeX; j++) {
				for (int i = 0; i < sizeY; i++) {
					cellMetrics.setCellIndex(i, j);
					if (cellGrid[j][i] != 0) {
						
						cellMetrics.computeCorners(mCornersX, mCornersY);
						Path path = cellMetrics.createPath();
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
		} else {
			super.onDraw(canvas);
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
	public void createMap(String mapURI) {
		if (cellGrid != null) {
			return;
		}
		
		cellGrid = new int[25][28];
		sizeX = 25;
		sizeY = 28;
		String faction = null;
		String sectorName = null;
		int i = 0,j = 0;
		
		JsonReader reader;
		try {
			reader = new JsonReader(new InputStreamReader(context.getAssets()
						.open(mapURI), "UTF-8"));

			reader.beginArray();
			while (reader.hasNext()) {
				reader.beginObject();
				while (reader.hasNext()) {
					String name = reader.nextName();
					if (name.equals("name")) {
						sectorName = reader.nextString();
					} else if (name.equals("coordX")) {
						i = reader.nextInt();
					} else if (name.equals("coordY")) {
						j = reader.nextInt();
					} else if (name.equals("house")) {
						faction = reader.nextString();
					} else if (name.equals("id")) {
						reader.nextInt();
					}
				}
				
				if ("LIAO".equals(faction)) {
					cellGrid[j][i] = HOUSE_LIAO;
				} else if ("KURITA".equals(faction)) {
					cellGrid[j][i] = HOUSE_KURITA;
				} else if ("STEINER".equals(faction)) {
					cellGrid[j][i] = HOUSE_STEINER;
				} else if ("DAVION".equals(faction)) {
					cellGrid[j][i] = HOUSE_DAVION;
				} else if ("MARIK".equals(faction)) {
					cellGrid[j][i] = HOUSE_MARIK;
				} else {
					cellGrid[j][i] = NOT_VISIBLE;
				}
				
				reader.endObject();
			}
			reader.endArray();
		} catch (UnsupportedEncodingException e) {
			Log.e(TAG, e.getMessage());
		} catch (IOException e) {
			Log.e(TAG, e.getMessage());
		}

		mapInitialized = true;

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
