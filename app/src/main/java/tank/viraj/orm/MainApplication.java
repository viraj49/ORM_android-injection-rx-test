package tank.viraj.orm;

import android.app.Application;

import tank.viraj.orm.database.Database;
import tank.viraj.orm.injections.ApplicationComponent;
import tank.viraj.orm.injections.ApplicationModule;
import tank.viraj.orm.injections.DaggerApplicationComponent;

/**
 * Created by Viraj Tank, 18-06-2016.
 */
public class MainApplication extends Application {

    private ApplicationComponent applicationComponent;

    public MainApplication() {
    }

    @Override
    public void onCreate() {
        super.onCreate();

        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this, new Database(this)))
                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }
}