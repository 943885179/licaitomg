package chen.com.myaccount;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import chen.com.myaccount.bean.Flag;
import chen.com.myaccount.util.GreenDaoUtil;
import greendao.gen.DaoSession;
import greendao.gen.FlagDao;

public class AccountFlagActivity extends AppCompatActivity {
    private Button btnOkFlag;//确定按钮
    private EditText edflag;//文本编辑
    private DaoSession session;
    private Long  strid;
    private int addOrUpdate=0;//0添加，1修改
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_flag);
        GreenDaoUtil util=new GreenDaoUtil(this,"account");
        session=util.session;
        btnOkFlag=(Button)findViewById(R.id.btnOkFlag);
        edflag=(EditText)findViewById(R.id.etFlag);
        Intent intents=getIntent();
        Bundle bundle= intents.getExtras();
        if(bundle==null){
            btnOkFlag.setText("添加");
            addOrUpdate=0;
        }
        else {
            strid=bundle.getLong("flagid");
            btnOkFlag.setText("修改");
            Flag flags= session.getFlagDao().queryBuilder().where(FlagDao.Properties.Id.eq(strid)).unique();
            edflag.setText(flags.getFlag());
            addOrUpdate=1;
        }
        /**
         *添加按钮
         */
        btnOkFlag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strFlag = edflag.getText().toString();
                if (strFlag.isEmpty()){
                    Toast.makeText(AccountFlagActivity.this,"便签内容为空！",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(addOrUpdate==0) {
                    long id = session.getFlagDao().insert(new Flag(strFlag));
                    Toast.makeText(AccountFlagActivity.this,id>0?"添加成功":"添加失败",Toast.LENGTH_SHORT).show();
                }
                else {
                    Flag flag=new Flag();
                    flag.setId(strid);
                    flag.setFlag(strFlag);
                    session.getFlagDao().update(flag);
                    Toast.makeText(AccountFlagActivity.this,"修改成功",Toast.LENGTH_SHORT).show();
                }
                Intent intent=new Intent();
                AccountFlagActivity.this.setResult(10003,intent);
                finish();
            }
        });
    }
}
