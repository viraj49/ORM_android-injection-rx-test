package tank.viraj.orm.dataSource;

import java.sql.SQLException;
import java.util.List;

import rx.Observable;
import rx.schedulers.Schedulers;
import tank.viraj.orm.dao.GitHubUserDao;
import tank.viraj.orm.model.GitHubUser;
import tank.viraj.orm.retrofit.GitHubApiInterface;
import tank.viraj.orm.util.RxSchedulerConfiguration;

/**
 * Created by Viraj Tank, 18-06-2016.
 */
public class GitHubUserListDataSource {
    private GitHubApiInterface gitHubApiInterface;
    private GitHubUserDao gitHubUserDao;
    private RxSchedulerConfiguration rxSchedulerConfiguration;

    public GitHubUserListDataSource(GitHubApiInterface gitHubApiInterface,
                                    GitHubUserDao gitHubUserDao) {
        this.gitHubApiInterface = gitHubApiInterface;
        this.gitHubUserDao = gitHubUserDao;
        this.rxSchedulerConfiguration = new RxSchedulerConfiguration(Schedulers.computation(),
                Schedulers.computation());
    }

    public Observable<List<GitHubUser>> getGitHubUsers(boolean isForced) {
        return Observable.concat(getGitHubUsersFromORM(isForced), getGitHubUsersFromRetrofit())
                .takeFirst(gitHubUserList -> gitHubUserList != null && gitHubUserList.size() > 0);
    }

    private Observable<List<GitHubUser>> getGitHubUsersFromORM(boolean isForced) {
        return Observable.just(isForced)
                .filter(isForcedIn -> !isForcedIn)
                .subscribeOn(rxSchedulerConfiguration.getSubscribeOn())
                .observeOn(rxSchedulerConfiguration.getObserveOn())
                .map(isForcedIn -> {
                    try {
                        List<GitHubUser> gitHubUserList = gitHubUserDao.getGitHubUserList();
                        return gitHubUserList;
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    return null;
                });
    }

    private Observable<List<GitHubUser>> getGitHubUsersFromRetrofit() {
        return gitHubApiInterface.getGitHubUsersList()
                .map(gitHubUserList -> {
                    try {
                        gitHubUserDao.storeOrUpdateGitHubUserList(gitHubUserList);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    return gitHubUserList;
                });
    }

    public void clearGitHubUserORMData() {
        try {
            gitHubUserDao.clearDatabase();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}