package chen.com.myaccount;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import chen.com.myaccount.adapter.ComeOutadapter;
import chen.com.myaccount.adapter.ListViewAdpter;
import chen.com.myaccount.bean.Outaccount;
import chen.com.myaccount.util.GreenDaoUtil;
import greendao.gen.DaoSession;

public class OutActivity extends AppCompatActivity {

    private DaoSession session;
    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_out);
        listView=(ListView)findViewById(R.id.newoutcome);
        listView.invalidate();
        GreenDaoUtil util=new GreenDaoUtil(this,"account");
        session=util.getSession();
      //  session.getOutaccountDao().insert(new Outaccount(100,"2017-11-21","工资","昆明","发放工资的"));
        List<Outaccount> list=session.getOutaccountDao().queryBuilder().list();
        final ListViewAdpter outadapter=new ListViewAdpter(list,this);
        listView.setAdapter(outadapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView text=view.findViewById(R.id.type);
                Toast.makeText(OutActivity.this,text.getHint(),Toast.LENGTH_SHORT).show();
                Long id=Long.parseLong(text.getHint().toString());

            }
        });

    }
}
