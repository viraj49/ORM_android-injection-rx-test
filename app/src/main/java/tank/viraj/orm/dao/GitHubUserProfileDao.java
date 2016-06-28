package tank.viraj.orm.dao;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

import tank.viraj.orm.database.Database;
import tank.viraj.orm.model.GitHubUserProfile;

/**
 * Created by Viraj Tank, 18-06-2016.
 */
public class GitHubUserProfileDao extends AbstractDao {

    public GitHubUserProfileDao(Database database) {
        super(database);
    }

    public void storeOrUpdateProfile(GitHubUserProfile gitHubUserProfile) throws SQLException {
        Dao<GitHubUserProfile, Integer> dao = database.getGitHubUserProfileDao();
        try {
            database.startTransaction();
            dao.createOrUpdate(gitHubUserProfile);
        } finally {
            database.endTransaction();
        }
    }

    public GitHubUserProfile getProfile(String login) throws SQLException {
        GitHubUserProfile gitHubUserProfile = null;
        Dao<GitHubUserProfile, Integer> dao = database.getGitHubUserProfileDao();
        List<GitHubUserProfile> gitHubUserProfileTemp = dao.queryForEq("login", login);
        if (gitHubUserProfileTemp != null) {
            if (gitHubUserProfileTemp.size() > 0) {
                gitHubUserProfile = gitHubUserProfileTemp.get(0);
            }
        }

        return gitHubUserProfile;
    }

    @Override
    public void clearDatabase() throws SQLException {
        database.clearTable(GitHubUserProfile.class);
    }
}