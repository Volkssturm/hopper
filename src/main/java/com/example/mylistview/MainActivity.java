package com.example.mylistview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * 时间：20170602
 * 类功能：主要的界面展示
 * 思路：
 * 1.根据要求完成布局
 * 2.自定义数据用来完成适配
 * 3.重写适配器
 * 4.监听点击事件完成交互
 * 5.编写算法完成计算
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView bianji;
    private TextView tv_heji;
    private CheckBox quanxuan;
    private TextView tv_heji2;
    private Button btn_jiesuan;
    private RelativeLayout rl1;
    private CheckBox quanxuan2;
    private Button btn_shanchu;
    private Button btn_shoucang;
    private RelativeLayout rl2;
    private NumberAddSubView nb_addsub_view;
    private RecyclerView mRecyclerView;
    private MyRecyclerAdapter mAdapter;
    private ArrayList<String> mNames;
    private ArrayList<String> mPrices;
    private ArrayList<Boolean> mList;
    private ArrayList<Integer> mCount = new ArrayList<>();
    private boolean mA = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //舒适化控件
        initView();
        //初适化数据
        initData();
    }

    private void initData() {
        //初适化商品数量的集合
        mCount = new ArrayList<>();
        mCount.add(1);
        mCount.add(1);
        mCount.add(1);
        mCount.add(1);
        mCount.add(1);

        //初适化商品是否被选中的集合
        mList = new ArrayList<>();
        mList.add(false);
        mList.add(false);
        mList.add(false);
        mList.add(false);
        mList.add(false);
        //初适化商品名称的集合
        mNames = new ArrayList<>();
        mNames.add("电脑");
        mNames.add("水杯");
        mNames.add("空调");
        mNames.add("面包");
        mNames.add("咖啡豆");
        //初适化商品价格的集合
        mPrices = new ArrayList<>();
        mPrices.add("4200");
        mPrices.add("50");
        mPrices.add("2200");
        mPrices.add("8");
        mPrices.add("135");
        //创建适配器，适配数据
        mAdapter = new MyRecyclerAdapter();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);

    }

    private void initView() {
        //找到控件
        bianji = (TextView) findViewById(R.id.bianji);
//        tv_heji = (TextView) findViewById(R.id.tv_heji);
        quanxuan = (CheckBox) findViewById(R.id.quanxuan);
        tv_heji2 = (TextView) findViewById(R.id.tv_heji2);
        btn_jiesuan = (Button) findViewById(R.id.btn_jiesuan);
        rl1 = (RelativeLayout) findViewById(R.id.rl1);
        quanxuan2 = (CheckBox) findViewById(R.id.quanxuan2);
        btn_shanchu = (Button) findViewById(R.id.btn_shanchu);
        btn_shoucang = (Button) findViewById(R.id.btn_shoucang);
        rl2 = (RelativeLayout) findViewById(R.id.rl2);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);

        //注册点击事件
        btn_jiesuan.setOnClickListener(this);
        btn_shanchu.setOnClickListener(this);
        btn_shoucang.setOnClickListener(this);
        quanxuan.setOnClickListener(this);
        quanxuan2.setOnClickListener(this);
        bianji.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //实现结算功能
            case R.id.btn_jiesuan:
                int price = getPrice();
                Toast.makeText(this, price + "", Toast.LENGTH_SHORT).show();
                break;
            //实现删除功能
            case R.id.btn_shanchu:
                for (int i = 0; i < mList.size(); i++) {
                    if (mList.get(i)) {
                        mList.remove(i);
                        mNames.remove(i);
                        mPrices.remove(i);
                    }
                }
                mAdapter.notifyDataSetChanged();
                break;
            //实现收藏功能
            case R.id.btn_shoucang:
                if(mList.size()==0){
                    return;
                }
                Toast.makeText(this, "收藏成功", Toast.LENGTH_SHORT).show();
                break;
            //实现全选及反选功能
            case R.id.quanxuan:
                if (quanxuan.isChecked()) {
                    for (int i = 0; i < mList.size(); i++) {
                        mList.set(i, true);
                        getPrice();
                    }
                } else {
                    for (int i = 0; i < mList.size(); i++) {
                        mList.set(i, false);
                        getPrice();
                    }
                }
                mAdapter.notifyDataSetChanged();
                break;
            //实现全选及反选功能
            case R.id.quanxuan2:
                if (quanxuan2.isChecked()) {
                    for (int i = 0; i < mList.size(); i++) {
                        mList.set(i, true);
                    }
                } else {
                    for (int i = 0; i < mList.size(); i++) {
                        mList.set(i, false);
                    }
                }
                mAdapter.notifyDataSetChanged();
                break;
            //实现编辑功能
            case R.id.bianji:
                if (bianji.getText().equals("编辑")) {
                    bianji.setText("完成");
                    rl1.setVisibility(View.GONE);
                    rl2.setVisibility(View.VISIBLE);
//                    tv_heji.setVisibility(View.GONE);
                    mA = false;
                } else {
                    bianji.setText("编辑");
                    rl2.setVisibility(View.GONE);
                    rl1.setVisibility(View.VISIBLE);
//                    tv_heji.setVisibility(View.VISIBLE);
                    mA = true;
                    getPrice();
                }

                break;
        }
    }
    //计算商品价格的方法
    public int getPrice() {
        //判断是否处于编辑页面
        if (mA) {
            int sum = 0;
            for (int i = 0; i < mList.size(); i++) {
                if (mList.get(i)) {
                    sum += Integer.parseInt(mPrices.get(i)) * mCount.get(i);
                }
            }
//            tv_heji.setText("合计:" + sum+"元");
            tv_heji2.setText("合计:" + sum+"元");
            return sum;
        }
        return 0;
    }
    /////////////////////////////////////////////////////////////////////////////////
    //创建内部适配器
    class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.MyViewHolder> {

        @Override
        public MyRecyclerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            //绑定视图
            View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.item, parent,
                    false);
            MyRecyclerAdapter.MyViewHolder holder = new MyRecyclerAdapter.MyViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(final MyRecyclerAdapter.MyViewHolder holder, final int
                position) {
            //绑定数据
            holder.mTextView.setText(mNames.get(position));
            holder.mTextView2.setText(mPrices.get(position)+"元");
            holder.mRadioButton.setChecked(mList.get(position));
            holder.mRadioButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (holder.mRadioButton.isChecked()) {
                        mList.set(position, true);
                        getPrice();
                    } else {
                        mList.set(position, false);
                        getPrice();
                    }
                }
            });

            holder.nb_addsub_view.setOnButtonClickListenter(new NumberAddSubView
                    .OnButtonClickListenter() {
                @Override
                public void onButtonAddClick(View view, int value) {
//                    Toast.makeText(MainActivity.this, "AddClick Vaule == " + value, Toast
//                            .LENGTH_SHORT).show();
                    mCount.set(position, value);
                    getPrice();
                }

                @Override
                public void onButtonSubClick(View view, int value) {
//                    Toast.makeText(MainActivity.this, "SubClick Vaule == " + value, Toast
//                            .LENGTH_SHORT).show();
                    mCount.set(position, value);
                    getPrice();
                }
            });

        }

        @Override
        public int getItemCount() {
            //返回RecyclerView的条目数
            return mNames.size();
        }
        //自定义ViewHolder
        public class MyViewHolder extends RecyclerView.ViewHolder {

            private CheckBox mRadioButton;
            private ImageView mImageView;
            private TextView mTextView, mTextView2;
            private NumberAddSubView nb_addsub_view;
            //查找控件
            public MyViewHolder(View itemView) {
                super(itemView);
                mRadioButton = (CheckBox) itemView.findViewById(R.id.items_radioButton);
                mTextView = (TextView) itemView.findViewById(R.id.items_name);
                mTextView2 = (TextView) itemView.findViewById(R.id.jiage);
                mImageView = (ImageView) itemView.findViewById(R.id.items_img);
                nb_addsub_view = (NumberAddSubView) itemView.findViewById(R.id.zujian);
            }
        }
    }


}
