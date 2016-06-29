package tank.viraj.orm.dataSource;

import java.sql.SQLException;

import rx.Observable;
import rx.schedulers.Schedulers;
import tank.viraj.orm.dao.GitHubUserProfileDao;
import tank.viraj.orm.model.GitHubUserProfile;
import tank.viraj.orm.retrofit.GitHubApiInterface;
import tank.viraj.orm.util.RxSchedulerConfiguration;

/**
 * Created by Viraj Tank, 18-06-2016.
 */
public class GitHubUserProfileDataSource {
    private GitHubApiInterface gitHubApiInterface;
    private GitHubUserProfileDao gitHubUserProfileDao;
    private RxSchedulerConfiguration rxSchedulerConfiguration;

    public GitHubUserProfileDataSource(GitHubApiInterface gitHubApiInterface,
                                       GitHubUserProfileDao gitHubUserProfileDao) {
        this.gitHubApiInterface = gitHubApiInterface;
        this.gitHubUserProfileDao = gitHubUserProfileDao;
        this.rxSchedulerConfiguration = new RxSchedulerConfiguration(Schedulers.computation(),
                Schedulers.computation());
    }

    public Observable<GitHubUserProfile> getGitHubUserProfile(String login, boolean isForced) {
        return Observable.concat(
                getGitHubUserProfileFromORM(login, isForced),
                getGitHubUserProfileFromRetrofit(login))
                .takeFirst(profile -> profile != null);
    }

    private Observable<GitHubUserProfile> getGitHubUserProfileFromORM(String login,
                                                                      boolean isForced) {
        return Observable.just(isForced)
                .filter(isForcedIn -> !isForcedIn)
                .subscribeOn(rxSchedulerConfiguration.getSubscribeOn())
                .observeOn(rxSchedulerConfiguration.getObserveOn())
                .map(isForcedIn -> {
                    try {
                        GitHubUserProfile gitHubUserProfile = gitHubUserProfileDao.getProfile(login);
                        return gitHubUserProfile;
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    return null;
                });
    }

    private Observable<GitHubUserProfile> getGitHubUserProfileFromRetrofit(String login) {
        return gitHubApiInterface.getGitHubUserProfile(login)
                .map(profile -> {
                    try {
                        gitHubUserProfileDao.storeOrUpdateProfile(profile);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    return profile;
                });
    }

    public void clearGitHubUserProfileORMData() {
        try {
            gitHubUserProfileDao.clearDatabase();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}