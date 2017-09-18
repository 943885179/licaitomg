package chen.com.myaccount;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;
import chen.com.myaccount.adapter.FlagAdapters;
import chen.com.myaccount.bean.Flag;
import chen.com.myaccount.util.GreenDaoUtil;
import chen.com.myaccount.util.Tabhelp;
import greendao.gen.DaoSession;

public class ShowInfoActivity extends AppCompatActivity {

    private Button btnAddFlag;//新增便签按钮
    private ListView lvinfo;
    private DaoSession session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_info);

        GreenDaoUtil util = new GreenDaoUtil(this, "account");
        session = util.session;

        final List<Flag> flagList = session.getFlagDao().queryBuilder().list();
        /**
         * 显示所有便签信息
         */
        lvinfo = (ListView) findViewById(R.id.lvinfos);
        lvinfo.setAdapter(new FlagAdapters(flagList, ShowInfoActivity.this));
        /**
         * 点击显示某一条便签信息
         */
        lvinfo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(ShowInfoActivity.this, AccountFlagActivity.class);
                TextView textView = view.findViewById(R.id.flagitem);
                Long strid = Long.parseLong(textView.getHint().toString());
                Bundle bundle = new Bundle();
                bundle.putLong("flagid", strid);
                intent.putExtras(bundle);
                startActivityForResult(intent,2);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i("sda", "onActivityResult: "+requestCode+":"+resultCode);
        Tabhelp.tabid=resultCode;
        Intent tent=new Intent(ShowInfoActivity.this,IndexActivity.class);
        startActivity(tent);
    }
}