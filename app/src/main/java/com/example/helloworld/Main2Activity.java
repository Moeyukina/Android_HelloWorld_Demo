package com.example.helloworld;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity {
    private Button button;
    private Button button2;
    private EditText editText;
    private EditText editText2;
    private TextView textView6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });//结束

        button = (Button) findViewById(R.id.button);
        textView6 = (TextView)findViewById(R.id.textView6);
        textView6.setText("在两个 EditText 中输入值对比，可任意类型");
        editText = (EditText)findViewById(R.id.editText_PutExtra);
        editText2 = (EditText)findViewById(R.id.editText_Bundle);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editText.getText().toString().equals(editText2.getText().toString()))
                {
                    //if true
                    Log.i("Compare_Info", String.valueOf("数据一致"));
                    textView6.setText("String is same");
                    textView6.setTextColor(Color.parseColor("#008000"));
                }
                else
                {
                    //if false
                    Log.i("Compare_Info", String.valueOf("数据不一致"));
                    textView6.setText("String is not same");
                    textView6.setTextColor(Color.parseColor("#FF0000"));
                }
            }
        });
    }
    public boolean dispatchTouchEvent(MotionEvent ev) { //点击事件
        //如果是点击事件，获取点击的view，并判断是否要收起键盘
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            //获取目前得到焦点的view
            View v = getCurrentFocus();
            //判断是否要收起并进行处理
            if (isShouldHideKeyboard(v, ev)) {
                hideKeyboard(v.getWindowToken());
            }
        }
        //这个是activity的事件分发，一定要有，不然就不会有任何的点击事件了
        return super.dispatchTouchEvent(ev);
    }
    //隐藏软键盘并让editText失去焦点
    protected void hideKeyboard(IBinder token) {
        editText.clearFocus();
        editText2.clearFocus();
        if (token != null) {
            //这里先获取InputMethodManager再调用他的方法来关闭软键盘
            //InputMethodManager就是一个管理窗口输入的manager
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (im != null) {
                im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }
    private boolean isShouldHideKeyboard(View v, MotionEvent event) {
        //如果目前得到焦点的这个view是editText的话进行判断点击的位置
        if (v instanceof EditText) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0],
                    top = l[1],
                    bottom = top + v.getHeight(),
                    right = left + v.getWidth();
            // 点击EditText的事件，忽略它。
            return !(event.getX() > left) || !(event.getX() < right)
                    || !(event.getY() > top) || !(event.getY() < bottom);
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditText上
        return false;
    }
}
