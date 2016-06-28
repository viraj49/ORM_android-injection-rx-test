package tank.viraj.orm.dao;

import java.sql.SQLException;

import tank.viraj.orm.database.Database;

/**
 * Created by Viraj Tank, 18-06-2016.
 */
public abstract class AbstractDao {
    /* we are injecting Database with Dagger2 */
    Database database;

    public AbstractDao(Database database) {
        this.database = database;
    }

    /* Clearing right tables with right DAO is important,
     * test this method to avoid any misplaced clear */
    abstract public void clearDatabase() throws SQLException;
}