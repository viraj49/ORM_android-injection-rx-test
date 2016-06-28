package tank.viraj.orm.injections;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import tank.viraj.orm.adapter.MainAdapter;
import tank.viraj.orm.dao.GitHubUserDao;
import tank.viraj.orm.dao.GitHubUserProfileDao;
import tank.viraj.orm.dataSource.GitHubUserListDataSource;
import tank.viraj.orm.dataSource.GitHubUserProfileDataSource;
import tank.viraj.orm.database.Database;
import tank.viraj.orm.presenter.GitHubUserPresenter;
import tank.viraj.orm.presenter.GitHubUserProfilePresenter;
import tank.viraj.orm.retrofit.GitHubApiInterface;

/**
 * Created by Viraj Tank, 18-06-2016.
 */

@Module
@Singleton
public class ApplicationModule {
    private static final String baseUrl = "https://api.github.com/";

    private Application application;

    private Database database;

    public ApplicationModule(Application application, Database database) {
        this.application = application;
        this.database = database;
    }

    /* DAO (data access object) for GitHubUser model */
    @Provides
    @Singleton
    GitHubUserDao provideGitHubUserDao() {
        return new GitHubUserDao(database);
    }

    /* DAO (data access object) for GitHubUser model */
    @Provides
    @Singleton
    GitHubUserProfileDao provideGitHubUserProfileDao() {
        return new GitHubUserProfileDao(database);
    }

    /* Presenter for GitHubUser */
    @Provides
    @Singleton
    GitHubUserPresenter provideGitHubPresenter(GitHubUserListDataSource gitHubUserListDataSource) {
        return new GitHubUserPresenter(gitHubUserListDataSource);
    }

    /* Presenter for GitHubUserProfile */
    @Provides
    @Singleton
    GitHubUserProfilePresenter provideProfilePresenter(
            GitHubUserProfileDataSource gitHubUserProfileDataSource) {
        return new GitHubUserProfilePresenter(gitHubUserProfileDataSource);
    }

    /* Data source for GitHubUserListDataSource */
    @Provides
    @Singleton
    GitHubUserListDataSource provideGitHubUserListDataSource(GitHubApiInterface gitHubApiInterface,
                                                             GitHubUserDao gitHubUserDao) {
        return new GitHubUserListDataSource(gitHubApiInterface, gitHubUserDao);
    }

    /* Data source for GitHubUserProfileDataSource */
    @Provides
    @Singleton
    GitHubUserProfileDataSource provideGitHubUserProfileDataSource(
            GitHubApiInterface gitHubApiInterface, GitHubUserProfileDao gitHubUserProfileDao) {
        return new GitHubUserProfileDataSource(gitHubApiInterface, gitHubUserProfileDao);
    }

    /* OkHttpclient for retrofit2 */
    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient() {
        return new OkHttpClient.Builder().build();
    }

    /* retrofit2 */
    @Provides
    @Singleton
    GitHubApiInterface provideGitHubApiInterface(OkHttpClient okHttpClient) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        return retrofit.create(GitHubApiInterface.class);
    }

    /* Data adapter for recycler view */
    @Provides
    @Singleton
    MainAdapter provideMainAdapter() {
        return new MainAdapter(application.getApplicationContext());
    }
}