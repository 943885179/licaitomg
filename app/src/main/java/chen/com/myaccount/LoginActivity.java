package chen.com.myaccount;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import chen.com.myaccount.bean.Users;
import chen.com.myaccount.util.GreenDaoUtil;
import greendao.gen.DaoSession;
import greendao.gen.UsersDao;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity{
    private DaoSession session;
    private EditText editText;
    private Button login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        GreenDaoUtil util=new GreenDaoUtil(this,"account");
        session=util.getSession();
        //初始化一个登陆密码
        //session.getUsersDao().insert(new Users("123456"));
        login= (Button) findViewById(R.id.login);
        login.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                editText= (EditText) findViewById(R.id.pwdgo);
                if(editText.getText().toString().trim().equals(""))
                {
                    Toast.makeText(LoginActivity.this,"请输入密码！",Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    Users users=session.getUsersDao().queryBuilder().where(UsersDao.Properties.Password.eq(editText.getText().toString().trim())).unique();
                    if (users==null || users.getId()<=0){
                        Toast.makeText(LoginActivity.this,"密码错误 ！",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Toast.makeText(LoginActivity.this,"登录成功！",Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(LoginActivity.this,IndexActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

}

