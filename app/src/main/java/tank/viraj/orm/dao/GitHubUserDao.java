package tank.viraj.orm.dao;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

import tank.viraj.orm.database.Database;
import tank.viraj.orm.model.GitHubUser;

/**
 * Created by Viraj Tank, 18-06-2016.
 */
public class GitHubUserDao extends AbstractDao {

    public GitHubUserDao(Database database) {
        super(database);
    }

    public void storeOrUpdateGitHubUserList(List<GitHubUser> gitHubUserList) throws SQLException {
        Dao<GitHubUser, Integer> dao = database.getGitHubUserDao();
        try {
            database.startTransaction();
            for (GitHubUser gitHubUser : gitHubUserList) {
                dao.createOrUpdate(gitHubUser);
            }
        } catch (Exception e) {
        } finally {
            database.endTransaction();
        }
    }

    public List<GitHubUser> getGitHubUserList() throws SQLException {
        Dao<GitHubUser, Integer> dao = database.getGitHubUserDao();
        return dao.queryForAll();
    }

    @Override
    public void clearDatabase() throws SQLException {
        database.clearTable(GitHubUser.class);
    }
}