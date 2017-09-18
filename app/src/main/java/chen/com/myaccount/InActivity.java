package chen.com.myaccount;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import chen.com.myaccount.adapter.InCpmeAdapter;
import chen.com.myaccount.bean.Inaccount;
import chen.com.myaccount.util.GreenDaoUtil;
import chen.com.myaccount.util.Tabhelp;
import greendao.gen.DaoSession;

public class InActivity extends AppCompatActivity {
    private DaoSession session;
    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in);
        listView=(ListView)findViewById(R.id.newoutcome);
        listView.invalidate();
        GreenDaoUtil util=new GreenDaoUtil(this,"account");
        session=util.getSession();
        //session.getInaccountDao().insert(new Inaccount(2200,"2017-11-21","工资","xxx公司","发放工资的"));
        List<Inaccount> list=session.getInaccountDao().queryBuilder().list();
        final InCpmeAdapter outadapter=new InCpmeAdapter(list,this);
        listView.setAdapter(outadapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView text=view.findViewById(R.id.type);
               // Toast.makeText(InActivity.this,text.getHint(),Toast.LENGTH_SHORT).show();
                Long id=Long.parseLong(text.getHint().toString());
                Intent intent = new Intent(InActivity.this, InAddActivity.class);
                Bundle bundle = new Bundle();
                bundle.putLong("inid", id);
                intent.putExtras(bundle);
                startActivityForResult(intent,2);
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Tabhelp.tabid=resultCode;
        Intent tent=new Intent(InActivity.this,IndexActivity.class);
        startActivity(tent);
    }
}

