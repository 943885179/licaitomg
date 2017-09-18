package chen.com.myaccount.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import chen.com.myaccount.R;
import chen.com.myaccount.bean.Flag;
import chen.com.myaccount.bean.Outaccount;
import chen.com.myaccount.util.GreenDaoUtil;
import greendao.gen.DaoSession;

/**
 * Created by ios13 on 17/9/15.
 */

public class FlagAdapters extends BaseAdapter {
    public List<Flag> data; //数据源
    private Context context;
    private float downX;  //点下时候获取的x坐标
    private float upX;   //手指离开时候的x坐标
    private Button button; //用于执行删除的button
    private Animation animation;  //删除时候的动画
    private View view;
    private DaoSession session;

    public FlagAdapters(List<Flag> data, Context context) {
        GreenDaoUtil util = new GreenDaoUtil(context, "account");
        this.session = util.session;
        this.data = data;
        this.context = context;
        animation = AnimationUtils.loadAnimation(context, android.R.anim.fade_out);  //用xml获取一个动画
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int arg0) {
        return data.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        return arg0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.flag_item,
                    null);
            holder = new ViewHolder();
            holder.flagitem = convertView.findViewById(R.id.flagitem);
            holder.button = (Button) convertView.findViewById(R.id.delbtnflag);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.button.setVisibility(View.GONE);
        convertView.setOnTouchListener(new View.OnTouchListener() {  //为每个item设置setOnTouchListener事件

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ViewHolder holder = (ViewHolder) v.getTag();
                //获取滑动时候相应的ViewHolder，以便获取button按钮
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:  //手指按下
                        downX = event.getX(); //获取手指x坐标
                        if (button != null) {
                            button.setVisibility(View.GONE);  //影藏显示出来的button
                        }
                        break;
                    case MotionEvent.ACTION_UP:  //手指离开
                        upX = event.getX(); //获取x坐标值
                        break;
                }

                if (holder.button != null) {
                    if (Math.abs(downX - upX) > 100) {  //2次坐标的绝对值如果大于100，就认为是左右滑动
                        holder.button.setVisibility(View.VISIBLE);  //显示删除button
                        button = holder.button;  //赋值给全局button，一会儿用
                        view = v; //得到itemview，在上面加动画
                        return true; //终止事件
                    }
                    return false;  //释放事件，使onitemClick可以执行
                }
                return false;
            }

        });
        final Flag flag = data.get(position);
        holder.button.setOnClickListener(new View.OnClickListener() {  //为button绑定事件

            @Override
            public void onClick(View v) {

                if (button != null) {
                    Log.i("delete", "onClick: " + flag.getId());
                    new AlertDialog.Builder(context).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            session.getFlagDao().delete(flag);
                            button.setVisibility(View.GONE);  //点击删除按钮后，影藏按钮
                            deleteItem(view, position);   //删除数据，加动画
                        }
                    }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    }).setTitle("确定删除？")
                            .setIcon(android.R.drawable.ic_menu_info_details)
                            .setMessage("删除后的数据无法恢复！")
                            .create().show();

                }

            }
        });

        holder.flagitem.setText(flag.getFlag());
        holder.flagitem.setHint("" + flag.getId());
        return convertView;
    }

    public void deleteItem(View view, final int position) {
        view.startAnimation(animation);  //给view设置动画
        animation.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) { //动画执行完毕
                data.remove(position);  //把数据源里面相应数据删除
                notifyDataSetChanged();

            }
        });


    }

    class ViewHolder {
        public TextView flagitem;
        public Button button;  //删除按钮
    }

}
