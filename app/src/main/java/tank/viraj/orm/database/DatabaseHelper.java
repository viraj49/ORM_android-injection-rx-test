package tank.viraj.orm.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

import tank.viraj.orm.R;

/**
 * Created by Viraj Tank, 28-06-2016.
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
    public static final String DATABASE_NAME = "database";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION, R.raw.ormlite_config);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
        try {
            for (Class c : Database.CLASSES) {
                TableUtils.createTable(connectionSource, c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void dropAllTables() {
        try {
            for (Class c : Database.CLASSES) {
                TableUtils.dropTable(connectionSource, c, true);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource,
                          int oldVersion, int newVersion) {
        dropAllTables();
        onCreate(sqLiteDatabase, connectionSource);
    }
}
