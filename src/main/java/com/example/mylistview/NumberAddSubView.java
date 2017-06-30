package com.example.mylistview;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * date: 2017/6/2
 *
 * function: 自定义View实现数量加减器的功能
 */

public class NumberAddSubView extends LinearLayout implements View.OnClickListener {

    private ImageView btn_sub;
    private ImageView btn_add;
    private TextView tv_num;
    private Context mContext;
    /**
     * 设置默认值
     */
    private int value = 1;
    private int minValue = 1;
    private int maxValue = 5;

    public NumberAddSubView(Context context) {
        super(context);
    }

    public NumberAddSubView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public NumberAddSubView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        initView(context);
    }

    private void initView(Context context) {
        View.inflate(context, R.layout.number_add_sub_view, this);
        btn_sub = (ImageView) findViewById(R.id.btn_sub);
        btn_add = (ImageView) findViewById(R.id.btn_add);
        tv_num = (TextView) findViewById(R.id.tv_num);
        btn_sub.setOnClickListener(this);
        btn_add.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_sub) {
// Toast.makeText(mContext,"减",Toast.LENGTH_SHORT).show();
            subNum();
        } else if (view.getId() == R.id.btn_add) {
// Toast.makeText(mContext,"加",Toast.LENGTH_SHORT).show();
            addNum();
        }
        if (onButtonClickListenter != null) {
            onButtonClickListenter.onButtonAddClick(view, value);
        }
    }

    public int getValue() {
        String val = tv_num.getText().toString();
        if (!TextUtils.isEmpty(val)) {
            value = Integer.parseInt(val);
        }
        return value;
    }

    public void setValue(int value) {
        this.value = value;
        tv_num.setText(value + "");
    }

    public int getMinValue() {
        return minValue;
    }

    public void setMinValue(int minValue) {
        this.minValue = minValue;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }

    /**
     * 减少数据
     */
    private void subNum() {
        if (value > minValue) {
            value = value - 1;
            tv_num.setText(value + "");
        }
    }

    /**
     * 添加数据
     */
    private void addNum() {
        if (value < maxValue) {
            value = value + 1;
            tv_num.setText(value + "");
        }
    }

    public interface OnButtonClickListenter {
        /**
         * 当增加按钮被点击的时候回调该方法
         * *
         *
         * @param view
         * @param value
         */
        public void onButtonAddClick(View view, int value);

        /**
         * 当减少按钮被点击的时候回调这个方法
         * *
         *
         * @param view
         * @param value
         */
        public void onButtonSubClick(View view, int value);
    }

    private OnButtonClickListenter onButtonClickListenter;

    public void setOnButtonClickListenter(OnButtonClickListenter onButtonClickListenter) {
        this.onButtonClickListenter = onButtonClickListenter;
    }

}
