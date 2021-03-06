package com.example.helloworld;
//moeyukina's App Helloworld Demo
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private Button button;
    private Button button_ActSwitch;
    private Button button_DataTrs;

    private TextView textView;

    private int i;
    private int i2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 跳转到 对比 Demo Activity
        button_ActSwitch = (Button) findViewById(R.id.button_ActSwitch);
        button_ActSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,Main2Activity.class);
                startActivity(intent);
            }
        });

        // 跳转到 传输 Demo Activity
        button_DataTrs = (Button) findViewById(R.id.button_DataTrs);
        button_DataTrs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,Main3Activity.class);
                startActivity(intent);
            }
        });

        // MainActivity 代码区域
        button = (Button) findViewById(R.id.button_LUswitch);
        final String[] data1 = {"Language Switch","语言切换 / 語言切換","言語を切り替える","언어 전환",
                "Sprachwechsel","Переключатель языка","การสลับภาษา","<(￣︶￣)↗[GO!]"};

        textView = (TextView) findViewById(R.id.miantextView);//id: textView3 即大标题
        final String[] data = {"Hello world!", "你好世界!", "こんにちは世界！", "안녕하세요 세상!",
                "Hallo Welt!", "Привет, мир!", "สวัสดีโลก", "(๑＞ڡ＜)☆"};

        i = 1;
        i2 = 1;

         button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("变量 i=", String.valueOf(i));
                Log.i("变量 i2=", String.valueOf(i2));
                textView.setText(data[i]);
                if (i == data.length - 1) {
                    i = 0;
                } else {
                    i = i + 1;
                }

                button.setText(data1[i2]);
                if (i2 == data1.length - 1) {
                    i2 = 0;
                } else {
                    i2 = i2 + 1;
                }
            }
        });
    }
}