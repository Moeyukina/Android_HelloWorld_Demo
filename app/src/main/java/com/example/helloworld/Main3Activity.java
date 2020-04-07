package com.example.helloworld;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.IBinder;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Main3Activity extends AppCompatActivity {

    private Button button_transfer;
    private Button button_return;
    private EditText editText_PutExtra;
    private EditText editText_Bundle;
    private TextView textView_back;
    private String PutExtraData_Default_String;
    private String BundleData_Default_String;
    private boolean BundleError;
    private boolean PutExtraError;

    protected void hideInput() {   //隐藏键盘
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);//输入法管理器imm
        View v = getWindow().peekDecorView();
        if (null != v) {
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }
    protected void Bundletips(){
        //Snackbar.make(view, "舰长别急，这个功能在搞 ing 呢"+"\nNo action here, Building ......",
          //      Snackbar.LENGTH_LONG).setAction("Action", null).show();
        if (BundleError = true){
            //Snackbar.make();
            Toast.makeText(Main3Activity.this,"Error",Toast.LENGTH_LONG);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_main3);
       textView_back = (TextView)findViewById(R.id.textView9);
       textView_back.setTextColor(Color.parseColor("#FF0000"));
       button_return = (Button)findViewById(R.id.button_return);
       button_return.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               finish();
           }
       });
       BundleError = false;
       PutExtraError = false;
       editText_PutExtra = (EditText)findViewById(R.id.editText_PutExtra);
       PutExtraData_Default_String = editText_PutExtra.getText().toString();//获取editText_PutExtra中的默认内容
       editText_PutExtra.setOnFocusChangeListener(new android.view.View.OnFocusChangeListener()
       {
           @Override
           public void onFocusChange(View v, boolean hasFocus) {
               if (hasFocus) {
                   // 此处为得到焦点时的处理内容
                   if (editText_PutExtra.getText().toString().equals(PutExtraData_Default_String)) {
                       editText_PutExtra.setText(""); //清除
                   }
               }
               else {
                   // 此处为失去焦点时的处理内容
                   if (editText_PutExtra.getText().length()==0) {
                       editText_PutExtra.setText(PutExtraData_Default_String);
                   }
                   /*else{
                       PutExtraError = true;
                       Toast.makeText(Main3Activity.this,"PutExtraError 处理错误",Toast.LENGTH_LONG).show();
                       Log.e("PutExtraError", "Bundle 在失去焦点时处理内容出错");
                       Log.e("PutExtraError", "Line 45:editText_Bundle.getText().length() Not = 0!");
                       //Snackbar.make(view, "舰长别急，这个功能在搞 ing 呢"+"\nNo action here, Building ......",
                       //Snackbar.LENGTH_LONG).setAction("Action", null).show();
                   }*/
               }
           }
       });



       editText_Bundle = (EditText)findViewById(R.id.editText_Bundle);
       BundleData_Default_String = editText_Bundle.getText().toString();//获取editText_Bundle中的默认内容
        editText_Bundle.setOnFocusChangeListener(new android.view.View.OnFocusChangeListener()
        {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    // 此处为得到焦点时的处理内容
                    if (editText_Bundle.getText().toString().equals(BundleData_Default_String)) {
                        editText_Bundle.setText(""); //清除
                    }
                }
                else {
                    // 此处为失去焦点时的处理内容
                    if (editText_Bundle.getText().length()==0) {
                        editText_Bundle.setText(BundleData_Default_String);
                    }
                   // else{
                        //BundleError = true;
                        //Toast.makeText(Main3Activity.this,"Bundle 处理错误",Toast.LENGTH_LONG).show();
                        //Log.e("BundleInfo", "Bundle 在失去焦点时处理内容出错");
                        //Log.e("BundleInfo", "Line 45:editText_Bundle.getText().length() Not = 0!");
                        //Snackbar.make(view, "舰长别急，这个功能在搞 ing 呢"+"\nNo action here, Building ......",
                                //Snackbar.LENGTH_LONG).setAction("Action", null).show();
                   // }
                }
            }
        });
       button_transfer = (Button) findViewById(R.id.button_transfer);
       button_transfer.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
                editText_PutExtra.clearFocus();
                editText_Bundle.clearFocus();
                hideInput();
                Intent intent = new Intent(Main3Activity.this,Main4Activity.class);
                intent.putExtra("PutExtra_Data",editText_PutExtra.getText().toString());
                Bundle bundle = new Bundle();
                bundle.putString("Bundle_Data",editText_Bundle.getText().toString());
                intent.putExtras(bundle);
                //带返回请求//
                startActivityForResult(intent,1);
           }
       });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1){
            if (resultCode == 1) {
                textView_back = (TextView) findViewById(R.id.textView9);
                //textView_back.setTextColor(Color.parseColor("#FF0000"));
                textView_back.setText(data.getStringExtra("ExtraData"));
            }
        }
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
        editText_PutExtra.clearFocus();//消除editText_PutExtra的焦点
        editText_Bundle.clearFocus();//消除editText_Bundle的焦点
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
