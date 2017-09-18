package chen.com.myaccount;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import chen.com.myaccount.bean.Inaccount;
import chen.com.myaccount.bean.Outaccount;
import chen.com.myaccount.util.GreenDaoUtil;
import greendao.gen.DaoSession;
import greendao.gen.InaccountDao;
import greendao.gen.OutaccountDao;

public class OutAddActivity extends AppCompatActivity {

    private DatePicker outdate;
    private EditText outadress;
    private EditText outmark,outmoney,outtype;
    private Button btnOkFlag;
    private int addOrUpdate;
    private Long strid;
    private Outaccount outaccount;
    private DaoSession session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_out_add);
        GreenDaoUtil util=new GreenDaoUtil(this,"account");
        session=util.session;
        outdate = (DatePicker) findViewById(R.id.outdate);
        outadress = (EditText) findViewById(R.id.outaddress);
        outmark = (EditText) findViewById(R.id.outmark);
        outmoney= (EditText) findViewById(R.id.outmoney);
        outtype=(EditText) findViewById(R.id.outtype);
        Intent intents=getIntent();
        Bundle bundle= intents.getExtras();
        btnOkFlag= (Button) findViewById(R.id.outbtn);
        if(bundle==null){
            btnOkFlag.setText("添加");
            addOrUpdate=0;
        }
        else {
            strid=bundle.getLong("outid");
            btnOkFlag.setText("修改");
            outaccount= session.getOutaccountDao().queryBuilder().where(OutaccountDao.Properties.Id.eq(strid)).unique();
            outadress.setText(outaccount.getAddress());
            outmark.setText(outaccount.getMark());
            String[] time=outaccount.getTime().split("-");
            outdate.init(Integer.parseInt(time[0]), Integer.parseInt(time[1])-1,Integer.parseInt(time[2]), new DatePicker.OnDateChangedListener() {
                @Override
                public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {

                }
            });
            outtype.setText(outaccount.getType());
            outmoney.setText(String.valueOf(outaccount.getMoney()));
            addOrUpdate=1;
        }
        btnOkFlag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double money=0;
                try{
                    money=Double.parseDouble(outmoney.getText().toString());
                }
                catch (Exception e){
                    Toast.makeText(OutAddActivity.this,"金额输入格式错误，请输入数字",Toast.LENGTH_SHORT).show();
                    return;
                } int y=outdate.getYear();
                int m=outdate.getMonth()+1;
                int d=outdate.getDayOfMonth();
                String date=y+"-"+m+"-"+d;
                String type=outtype.getText().toString();
                String address=outadress.getText().toString();
                String mark=outmark.getText().toString();
                if(type==null||type.equals("")|| money==0){
                    Toast.makeText(OutAddActivity.this,"类型和金额必须输入",Toast.LENGTH_SHORT).show();
                    return;
                }
                //TODO implement
                if(addOrUpdate==0){//添加
                    long id= session.getOutaccountDao().insert(new Outaccount(money,date,type,address,mark));
                    Toast.makeText(OutAddActivity.this,id>0?"添加成功":"添加失败",Toast.LENGTH_SHORT).show();
                }
                else{//修改
                    outaccount.setAddress(address);
                    outaccount.setMark(mark);
                    outaccount.setTime(date);
                    outaccount.setType(type);
                    outaccount.setMoney(money);
                    session.getOutaccountDao().update(outaccount);
                    Toast.makeText(OutAddActivity.this,"修改成功",Toast.LENGTH_SHORT).show();
                }
                Intent intent=new Intent();
                OutAddActivity.this.setResult(10001,intent);
                finish();
            }
        });
    }
}