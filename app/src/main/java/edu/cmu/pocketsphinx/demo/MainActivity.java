package edu.cmu.pocketsphinx.demo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button setComBtn = (Button)findViewById(R.id.setGameBtn);
        setComBtn.setOnClickListener(this);

        Button startBtn = (Button)findViewById(R.id.startBtn);
        startBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.setGameBtn:
                //Intent intent = new Intent(MainActivity.this,SettingActivity.class);
                //startActivity(intent);
                break;
            case R.id.startBtn:
                Intent intent2 = new Intent(MainActivity.this, PocketSphinxActivity.class);
                startActivity(intent2);
                break;
            default:

        }
    }
}
