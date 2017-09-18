package chen.com.myaccount;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import chen.com.myaccount.bean.Users;
import chen.com.myaccount.util.GreenDaoUtil;
import greendao.gen.DaoSession;
import greendao.gen.UsersDao;

public class UserActivity extends AppCompatActivity {
    private Button pwdbtn,escbtn;
    private EditText pwdedit;
    private DaoSession session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        GreenDaoUtil util=new GreenDaoUtil(this,"account");
        session=util.getSession();
        pwdbtn= (Button) findViewById(R.id.pwdbtn);
        escbtn= (Button) findViewById(R.id.esc);
        pwdbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pwdedit= (EditText) findViewById(R.id.pwdedit);
                if(pwdedit.getText().toString().trim().equals("")){
                    Toast.makeText(UserActivity.this,"请输入新密码", Toast.LENGTH_SHORT).show();
                    return;
                }
                session.getUsersDao().deleteAll();
                session.getUsersDao().insert(new Users(pwdedit.getText().toString().trim()));
                Toast.makeText(UserActivity.this,"密码修改成功"+pwdedit.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });
        escbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(UserActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}
