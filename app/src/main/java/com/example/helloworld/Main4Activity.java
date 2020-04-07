package com.example.helloworld;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Main4Activity extends AppCompatActivity {
    private Button button_re;
    private TextView PutExtra_textView_get;
    private TextView Bundle_textView_get;
    private Button Button_re;
    private EditText EditText_re;
    private String EditText_re_default_str;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        PutExtra_textView_get = (TextView)findViewById(R.id.putExtra_textView_get);
        Bundle_textView_get = (TextView)findViewById(R.id.bundle_textView_get);
        //PutExtra//
        Intent intent = getIntent();
        String PutExtra_Data = intent.getStringExtra("PutExtra_Data");
        PutExtra_textView_get.setText(PutExtra_Data);
        //Bundle//
        Bundle bundle = getIntent().getExtras();
        String Bundle_Data = bundle.getString("Bundle_Data");
        Bundle_textView_get.setText(Bundle_Data);


        //===返回===//
        EditText_re = (EditText)findViewById(R.id.editText_re);
        EditText_re_default_str = EditText_re.getText().toString();
        EditText_re.setOnFocusChangeListener(new android.view.View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    if (EditText_re.getText().toString().equals(EditText_re_default_str)) {
                        EditText_re.setText(""); //清除
                    }
                } else {
                    if (EditText_re.getText().length()==0) {
                        EditText_re.setText(EditText_re_default_str);
                    }
                }
            }
        });
        Button_re = (Button) findViewById(R.id.button_re);
        Button_re.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText_re.clearFocus();
                Intent intent = new Intent();
                intent.putExtra("ExtraData",EditText_re.getText().toString());
                setResult(1,intent);
                finish();
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
        EditText_re.clearFocus();//消除editText_PutExtra的焦点
        //editText_Bundle.clearFocus();//消除editText_Bundle的焦点
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
