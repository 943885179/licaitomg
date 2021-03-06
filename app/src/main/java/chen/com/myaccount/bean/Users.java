package chen.com.myaccount.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;

/**
 * Created by ios13 on 17/9/13.
 */
@Entity
public class Users {
    @Id
    private Long id;
    @NotNull
    private String password;

    public Users(String password) {
        this.password = password;
    }

    public String getPassword() {
        return this.password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    @Generated(hash = 1879200408)
    public Users(Long id, @NotNull String password) {
        this.id = id;
        this.password = password;
    }
    @Generated(hash = 2146996206)
    public Users() {
    }

}
