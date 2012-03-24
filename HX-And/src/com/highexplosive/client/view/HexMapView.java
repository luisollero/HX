package com.highexplosive.client.view;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.highexplosive.client.HxConstants;
import com.highexplosive.client.model.HexGridCell;


/**
 * 
 * @author Luis Ollero
 *
 */
public class HexMapView extends View {

    private static final int NOT_VISIBLE = 0;
    private static final int HOUSE_KURITA = 1;
    private static final int HOUSE_LIAO = 2;
    private static final int HOUSE_DAVION = 3;
    private static final int HOUSE_STEINER = 4;
    private static final int HOUSE_MARIK = 5;

    private final int MAX_SIZE_X = 25;
	private final int MAX_SIZE_Y = 28;
    private static final int NUM_HEX_CORNERS = 6;
    private static final int CELL_RADIUS_FULL_MAP = 15;

	private static final String TAG = HexMapView.class.getSimpleName();

    private int[][] cellMap = null;
    private int[][] cellOptimizedMap;
    
    private int[] mCornersX = new int[NUM_HEX_CORNERS];
    private int[] mCornersY = new int[NUM_HEX_CORNERS];

    private static HexGridCell cellMetrics = new HexGridCell(CELL_RADIUS_FULL_MAP);
    private static HexGridCell cellMetricsOptimizedMap = null;
    private boolean mapInitialized = false;
    private boolean useOptimizedMap = false;
	private int cellRadiusOptimizedMap = 0;
    
    public HexMapView(Context context, AttributeSet set) {
    	super(context, set);
    }
    

	protected void onDraw(Canvas canvas) {

		if (mapInitialized) {
			Paint mPaint = new Paint();
			mPaint.setStrokeWidth(8); 
			mPaint.setColor(Color.TRANSPARENT); 
			mPaint.setStyle(Paint.Style.FILL); 
//			mPaint.setAntiAlias(true); // no jagged edges, etc.
			if (useOptimizedMap) {
				useOptimizedMap(canvas, mPaint);
			} else {
				useFullMap(canvas, mPaint);
			}
		} else {
			super.onDraw(canvas);
		}
	}


	private void useFullMap(Canvas canvas, Paint paint) {
		for (int j = 0; j < cellMap.length; j++) {
			for (int i = 0; i < cellMap[j].length; i++) {
				cellMetrics.setCellIndex(i, j);
				if (cellMap[j][i] != 0) {
					cellMetrics.computeCorners(mCornersX, mCornersY);
					Path path = cellMetrics.createPath();
					paintCell(canvas, paint, path, cellMap[j][i]);
				}
			}
		}
	}
	
	private void useOptimizedMap(Canvas canvas, Paint paint) {
		
		int aux = canvas.getWidth() / cellMap[0].length;
		cellRadiusOptimizedMap = canvas.getWidth() / cellMap.length;
		cellRadiusOptimizedMap = cellRadiusOptimizedMap < aux ? cellRadiusOptimizedMap : aux;
		
		cellMetricsOptimizedMap = new HexGridCell(cellRadiusOptimizedMap);
		
		for (int i = 0; i < cellOptimizedMap.length; i++) {
			for (int j = 0; j < cellOptimizedMap[i].length; j++) {
				cellMetricsOptimizedMap.setCellIndex(i, j);
				if (cellOptimizedMap[i][j] != 0) {
					cellMetricsOptimizedMap.computeCorners(mCornersX, mCornersY);
					Path path = cellMetricsOptimizedMap.createPath();
					paintCell(canvas, paint, path, cellOptimizedMap[i][j]);
				}
			}
		}
	}
	
	private void paintCell(Canvas canvas, Paint paint, Path path, int faction) {
		switch (faction) {
		case HOUSE_KURITA:
			paintCell(Color.RED, canvas, paint, path);
			break;
		case HOUSE_DAVION:
			paintCell(Color.YELLOW, canvas, paint, path);
			break;
		case HOUSE_LIAO:
			paintCell(Color.GREEN, canvas, paint, path);
			break;
		case HOUSE_MARIK:
			paintCell(Color.MAGENTA, canvas, paint, path);
			break;
		case HOUSE_STEINER:
			paintCell(Color.GRAY, canvas, paint, path);
			break;
		default:
			break;
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
		if (cellMap != null) {
			return;
		}
		
		cellMap = new int[MAX_SIZE_X][MAX_SIZE_Y];
		String faction = null;
		String sectorName = null;
		int i = 0,j = 0;
		
		JsonReader reader;
		try {
			reader = new JsonReader(new InputStreamReader(getContext().getAssets()
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
				
				placeSectorInArray(faction, i, j);
				
				reader.endObject();
			}
			reader.endArray();
		} catch (UnsupportedEncodingException e) {
			Log.e(TAG, e.getMessage());
		} catch (IOException e) {
			Log.e(TAG, e.getMessage());
		}

		mapInitialized = true;
		useOptimizedMap = false;

	}


	private void placeSectorInArray(String faction, int i, int j) {
		if (HxConstants.FACTION_LIAO.equals(faction)) {
			cellMap[j][i] = HOUSE_LIAO;
		} else if (HxConstants.FACTION_KURITA.equals(faction)) {
			cellMap[j][i] = HOUSE_KURITA;
		} else if (HxConstants.FACTION_STEINER.equals(faction)) {
			cellMap[j][i] = HOUSE_STEINER;
		} else if (HxConstants.FACTION_DAVION.equals(faction)) {
			cellMap[j][i] = HOUSE_DAVION;
		} else if (HxConstants.FACTION_MARIK.equals(faction)) {
			cellMap[j][i] = HOUSE_MARIK;
		} else {
			cellMap[j][i] = NOT_VISIBLE;
		}
	}


	/**
	 * Calculate the dimensions to paint the map in a proper size
	 */
	public void recalculateMapDimensions() {
		int firstX = 100, firstY = 100;
		int lastX = 0, lastY = 0;
		boolean firstFound = false;
		
		for (int i = 0; i < cellMap.length; i++) {
			firstFound = false;
			for (int j = 0; j < cellMap[i].length; j++) {
				if (firstFound) {
					int newX = (cellMap[i][j] != NOT_VISIBLE) ? j : 0;
					if (lastX < newX) {
						lastX = newX;
					}
				} else if (cellMap[i][j] != NOT_VISIBLE) {
					firstFound = true;
					if (j < firstX) {
						firstX = j;
					}
				}
			}
		}
		
		for (int i = 0; i < cellMap[0].length; i++) {
			firstFound = false;
			for (int j = 0; j < cellMap.length; j++) {
				if (firstFound) {
					int newY = (cellMap[j][i] != NOT_VISIBLE) ? j : 0;
					if (lastY < newY) {
						lastY = newY;
					}
				} else if (cellMap[j][i] != NOT_VISIBLE) {
					firstFound = true;
					if (j < firstY) {
						firstY = j;
					}
				}
			}
		}
		
		if (HxConstants.DEBUG_MODE) {
			Log.i(TAG, "Calculate coords");
			Log.i(TAG, "firstX: " + firstX + " lastX: " + lastX);
			Log.i(TAG, "firstY: " + firstY + " lastY: " + lastY);
		}
		
		// New Hex with the correct size. We initialize it.
		cellOptimizedMap = new int[lastX - firstX + 1][lastY - firstY + 1];
		for (int i = 0; i < cellOptimizedMap.length; i++) {
			for (int j = 0; j < cellOptimizedMap[i].length; j++) {
				cellOptimizedMap[i][j] = NOT_VISIBLE;
			}
		}

		// We place there the valid info
		for (int i = 0; i < cellMap.length; i++) {
			for (int j = 0; j < cellMap[i].length; j++) {
				if (cellMap[i][j] != NOT_VISIBLE) {
					cellOptimizedMap[j - firstX][i - firstY] = cellMap[i][j];
				}
			}
		}
		
		useOptimizedMap = true;
	}


	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int parentWidth = MeasureSpec.getSize(widthMeasureSpec);
		int parentHeight = MeasureSpec.getSize(heightMeasureSpec);
		
		if (useOptimizedMap) {
			int semiSize = (cellOptimizedMap.length * cellRadiusOptimizedMap) / 2; //semi-width of the map
			int semiWidth = parentWidth / 2; //semi-width of the screen
			int left = semiWidth - semiSize;
			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(parentWidth,
					parentHeight);
			lp.setMargins(left, 0, 0, 0);
			this.setLayoutParams(lp);
		}
		
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}
	

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		this.cellMap = null;
		this.cellOptimizedMap = null;
		this.mCornersX = null;
		this.mCornersY = null;
	}
}
