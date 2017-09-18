package chen.com.myaccount;

import android.app.Activity;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import chen.com.myaccount.bean.Flag;
import chen.com.myaccount.bean.Inaccount;
import chen.com.myaccount.util.GreenDaoUtil;
import greendao.gen.DaoSession;
import greendao.gen.FlagDao;
import greendao.gen.InaccountDao;

public class InAddActivity extends AppCompatActivity {

    private DatePicker indate;
    private EditText inhandler;
    private EditText inmark,inmoney,intype;
    private Button btnOkFlag;
    private int addOrUpdate;
    private Long strid;
    private  Inaccount inaccount;
    private DaoSession session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_add);
        GreenDaoUtil util=new GreenDaoUtil(this,"account");
        session=util.session;
        indate = (DatePicker) findViewById(R.id.indate);
        inhandler = (EditText) findViewById(R.id.inhandler);
        inmark = (EditText) findViewById(R.id.inmark);
        inmoney= (EditText) findViewById(R.id.inmoney);
        intype=(EditText) findViewById(R.id.intype);
        Intent intents=getIntent();
        Bundle bundle= intents.getExtras();
        btnOkFlag= (Button) findViewById(R.id.inbtn);
        if(bundle==null){
            btnOkFlag.setText("添加");
            addOrUpdate=0;
        }
        else {
            strid=bundle.getLong("inid");
            btnOkFlag.setText("修改");
             inaccount= session.getInaccountDao().queryBuilder().where(InaccountDao.Properties.Id.eq(strid)).unique();
            inhandler.setText(inaccount.getHandler());
            inmark.setText(inaccount.getMark());
            String[] time=inaccount.getTime().split("-");
            indate.init(Integer.parseInt(time[0]), Integer.parseInt(time[1])-1,Integer.parseInt(time[2]), new DatePicker.OnDateChangedListener() {
                @Override
                public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {

                }
            });
            intype.setText(inaccount.getType());
            inmoney.setText(String.valueOf(inaccount.getMoney()));
            addOrUpdate=1;
        }
        btnOkFlag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                double money=0;
                try{
                    money=Double.parseDouble(inmoney.getText().toString());
                }
                catch (Exception e){
                    Toast.makeText(InAddActivity.this,"金额输入格式错误，请输入数字",Toast.LENGTH_SHORT).show();
                    return;
                }

                int y=indate.getYear();
                int m=indate.getMonth()+1;
                int d=indate.getDayOfMonth();
                String date=y+"-"+m+"-"+d;
                String type=intype.getText().toString().trim();
                String hander=inhandler.getText().toString().trim();
                String mark=inmark.getText().toString().trim();
                if(type==null||type.equals("")|| money==0){
                    Toast.makeText(InAddActivity.this,"类型和金额必须输入",Toast.LENGTH_SHORT).show();
                    return;
                }
                //TODO implement
                if(addOrUpdate==0){//添加
                   long id= session.getInaccountDao().insert(new Inaccount(money,date,type,hander,mark));
                    Toast.makeText(InAddActivity.this,id>0?"添加成功":"添加失败",Toast.LENGTH_SHORT).show();
                }
                else{//修改
                    inaccount.setHandler(hander);
                    inaccount.setMark(mark);
                    inaccount.setTime(date);
                    inaccount.setType(type);
                    inaccount.setMoney(money);
                    session.getInaccountDao().update(inaccount);
                    Toast.makeText(InAddActivity.this,"修改成功",Toast.LENGTH_SHORT).show();
                }
                Intent intent=new Intent();
                InAddActivity.this.setResult(10002,intent);
                finish();
            }
        });
    }
}
