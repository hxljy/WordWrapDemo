package test.hhx.com.wordwrapdemo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    private WordWrapView wordWrapView;
    String[] word = {"大叔大婶", "的点点滴滴多多多多", "打赏", "大叔大", "打扫的", "大大大啊", "sas撒飒飒", "打撒所多所所多", "打死打死打死打死", "4545sd5s45s4d5sd", "qqqqqq", "啊飒飒"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        wordWrapView = ((WordWrapView) findViewById(R.id.wordwrap));


        for (int i = 0; i < word.length; i++) {
            final TextView textview = new TextView(getApplicationContext());
            textview.setBackgroundColor(Color.parseColor("#a0a0a0"));
            textview.setTextSize(14);
            textview.setText(word[i]);
            final int finalI = i;
            textview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getApplicationContext(), word[finalI], Toast.LENGTH_SHORT).show();
                }
            });
            wordWrapView.addView(textview);
        }


    }
}
