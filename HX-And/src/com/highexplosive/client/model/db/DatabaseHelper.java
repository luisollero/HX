package com.highexplosive.client.model.db;

import java.sql.SQLException;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.highexplosive.client.R;
import com.highexplosive.client.model.RegimentOrder;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

/**
 * Database helper class used to manage the creation and upgrading of your
 * database. This class also usually provides the DAOs used by the other
 * classes.
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

	private static final String TAG = DatabaseHelper.class.getName();

	private static final String DATABASE_NAME = "hx.db";
	
	// any time you make changes to your database objects, you may have to increase the database version
	private static final int DATABASE_VERSION = 1;

	// the DAO object we use to access the RegimentOrder table
	private Dao<RegimentOrder, Integer> regimentOrderDao = null;
	private RuntimeExceptionDao<RegimentOrder, Integer> regimentOrderRuntimeDao = null;

	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION,
				R.raw.ormlite_config);
	}

	/**
	 * This is called when the database is first created. Usually you should
	 * call createTable statements here to create the tables that will store
	 * your data.
	 */
	@Override
	public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
		try {
			Log.i(TAG, "onCreate");
			TableUtils.createTable(connectionSource, RegimentOrder.class);
		} catch (SQLException e) {
			Log.e(DatabaseHelper.class.getName(), "Can't create database", e);
			throw new RuntimeException(e);
		}

		// here we try inserting data in the on-create as a test
		RuntimeExceptionDao<RegimentOrder, Integer> dao = getRegimentOrderDao();
		long millis = System.currentTimeMillis();
		// create some entries in the onCreate
		RegimentOrder simple = new RegimentOrder();
		dao.create(simple);
		simple = new RegimentOrder();
		dao.create(simple);
		Log.i(DatabaseHelper.class.getName(),
				"created new entries in onCreate: " + millis);
	}

	/**
	 * This is called when your application is upgraded and it has a higher
	 * version number. This allows you to adjust the various data to match the
	 * new version number.
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource,
			int oldVersion, int newVersion) {
		try {
			Log.i(DatabaseHelper.class.getName(), "onUpgrade");
			TableUtils.dropTable(connectionSource, RegimentOrder.class, true);
			// after we drop the old databases, we create the new ones
			onCreate(db, connectionSource);
		} catch (SQLException e) {
			Log.e(DatabaseHelper.class.getName(), "Can't drop databases", e);
			throw new RuntimeException(e);
		}
	}

	/**
	 * Returns the Database Access Object (DAO) for our RegimentOrder class. It
	 * will create it or just give the cached value.
	 */
	public Dao<RegimentOrder, Integer> getDao() throws SQLException {
		if (regimentOrderDao == null) {
			regimentOrderDao = getDao(RegimentOrder.class);
		}
		return regimentOrderDao;
	}

	/**
	 * Returns the RuntimeExceptionDao (Database Access Object) version of a Dao
	 * for our RegimentOrder class. It will create it or just give the cached
	 * value. RuntimeExceptionDao only through RuntimeExceptions.
	 */
	public RuntimeExceptionDao<RegimentOrder, Integer> getRegimentOrderDao() {
		if (regimentOrderRuntimeDao == null) {
			regimentOrderRuntimeDao = getRuntimeExceptionDao(RegimentOrder.class);
		}
		return regimentOrderRuntimeDao;
	}

	/**
	 * Close the database connections and clear any cached DAOs.
	 */
	@Override
	public void close() {
		super.close();
		regimentOrderRuntimeDao = null;
	}
}
