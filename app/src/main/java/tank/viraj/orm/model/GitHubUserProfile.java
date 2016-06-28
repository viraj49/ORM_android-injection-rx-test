package tank.viraj.orm.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import lombok.Getter;

/**
 * Created by Viraj Tank, 18-06-2016.
 */

@DatabaseTable(tableName = "GitHubUserProfile")
@Getter
public class GitHubUserProfile {
    @DatabaseField(id = true)
    private String login;
    @DatabaseField
    private String name;
    @DatabaseField
    private String email;

    public GitHubUserProfile() {
    }

    public GitHubUserProfile(String login, String name, String email) {
        this.login = login;
        this.name = name;
        this.email = email;
    }
}