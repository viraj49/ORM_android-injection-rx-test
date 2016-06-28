package tank.viraj.orm.database;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

import tank.viraj.orm.model.GitHubUser;
import tank.viraj.orm.model.GitHubUserProfile;

/**
 * Created by Viraj Tank, 28-06-2016.
 */
public class Database {

    public static final Class<?>[] CLASSES = new Class[]{
            GitHubUser.class,
            GitHubUserProfile.class
    };

    DatabaseHelper databaseHelper = null;

    public Database(Context context) {
        databaseHelper = new DatabaseHelper(context);
        databaseHelper.getWritableDatabase();
    }

    public Dao<GitHubUser, Integer> getGitHubUserDao() throws SQLException {
        return databaseHelper.getDao(GitHubUser.class);
    }

    public Dao<GitHubUserProfile, Integer> getGitHubUserProfileDao() throws SQLException {
        return databaseHelper.getDao(GitHubUserProfile.class);
    }

    public void startTransaction() {
        databaseHelper.getWritableDatabase().beginTransaction();
    }

    public void endTransaction() {
        databaseHelper.getWritableDatabase().setTransactionSuccessful();
        databaseHelper.getWritableDatabase().endTransaction();
    }

    public void clearTable(Class<?> cl) throws SQLException {
        TableUtils.clearTable(databaseHelper.getConnectionSource(), cl);
    }
}