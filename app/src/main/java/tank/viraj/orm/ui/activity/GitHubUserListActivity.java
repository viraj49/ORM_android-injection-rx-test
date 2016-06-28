package tank.viraj.orm.ui.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import tank.viraj.orm.R;
import tank.viraj.orm.ui.fragment.GitHubUserListFragment;

/**
 * Created by Viraj Tank, 18-06-2016.
 */
public class GitHubUserListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setActionBarTitle();

        /* create the fragment and load it in frame layout */
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.activity_main, new GitHubUserListFragment())
                    .commit();
        }
    }

    private void setActionBarTitle() {
        ActionBar bar = getSupportActionBar();
        if (bar != null) {
            bar.setTitle(getResources().getString(R.string.user_list));
        }
    }
}