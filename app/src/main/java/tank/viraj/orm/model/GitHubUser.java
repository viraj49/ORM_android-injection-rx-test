package tank.viraj.orm.model;

import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

import lombok.Getter;

/**
 * GitHubUser class should be Parcelable but for readability purpose Serializable is used
 * Created by Viraj Tank, 18-06-2016.
 */

@Getter
@DatabaseTable(tableName = "GitHubUser")
public class GitHubUser implements Serializable {
    @SerializedName("id")
    @DatabaseField(id = true)
    private int gitHubUserId;
    @DatabaseField
    private String login;
    @DatabaseField
    private String avatar_url;

    public GitHubUser() {
    }

    public GitHubUser(int gitHubUserId, String login, String avatar_url) {
        this.gitHubUserId = gitHubUserId;
        this.login = login;
        this.avatar_url = avatar_url;
    }
}