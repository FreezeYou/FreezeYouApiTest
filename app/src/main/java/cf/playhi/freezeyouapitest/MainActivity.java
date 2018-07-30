package cf.playhi.freezeyouapitest;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button get = findViewById(R.id.get);
        Button disable = findViewById(R.id.disable);
        Button enable = findViewById(R.id.enable);
        final EditText editText = findViewById(R.id.editText);
        get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setComponent(new ComponentName("cf.playhi.freezeyou","cf.playhi.freezeyou.GetDisabledApplications"));
                try {
                    startActivityForResult(intent,0);
                } catch (ActivityNotFoundException e){
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this,"老版本FreezeYou或未安装FreezeYou",Toast.LENGTH_LONG).show();//老版本FreezeYou或未安装FreezeYou
                } catch (SecurityException e){
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this,"是否在 Manifest 中声明权限了？",Toast.LENGTH_LONG).show();//是否在 Manifest 中声明权限了
                }
            }
        });
        disable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(MainActivity.this,
                        "cf.playhi.freezeyou.permission.DISABLE_APPLICATIONS")
                        != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(MainActivity.this,
                                new String[]{"cf.playhi.freezeyou.permission.DISABLE_APPLICATIONS"},
                                10);
                } else {
                    Intent intent = new Intent();
                    String[] strings = editText.getText().toString().split("\n");
                    intent.putExtra("packages",strings);
                    intent.setComponent(new ComponentName("cf.playhi.freezeyou","cf.playhi.freezeyou.DisableApplications"));
                    try {
                        startActivityForResult(intent,1);
                    } catch (ActivityNotFoundException e){
                        e.printStackTrace();
                        Toast.makeText(MainActivity.this,"老版本FreezeYou或未安装FreezeYou",Toast.LENGTH_LONG).show();//老版本FreezeYou或未安装FreezeYou
                    } catch (SecurityException e){
                        e.printStackTrace();
                        Toast.makeText(MainActivity.this,"是否在 Manifest 中声明权限了？",Toast.LENGTH_LONG).show();//是否在 Manifest 中声明权限了
                    }
                }
            }
        });
        enable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(MainActivity.this,
                        "cf.playhi.freezeyou.permission.ENABLE_APPLICATIONS")
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MainActivity.this,
                            new String[]{"cf.playhi.freezeyou.permission.ENABLE_APPLICATIONS"},
                            11);
                } else {
                    Intent intent = new Intent();
                    String[] strings = editText.getText().toString().split("\n");
                    intent.putExtra("packages",strings);
                    intent.setComponent(new ComponentName("cf.playhi.freezeyou","cf.playhi.freezeyou.EnableApplications"));
                    try {
                        startActivityForResult(intent,2);
                    } catch (ActivityNotFoundException e){
                        e.printStackTrace();
                        Toast.makeText(MainActivity.this,"老版本FreezeYou或未安装FreezeYou",Toast.LENGTH_LONG).show();//老版本FreezeYou或未安装FreezeYou
                    } catch (SecurityException e){
                        e.printStackTrace();
                        Toast.makeText(MainActivity.this,"是否在 Manifest 中声明权限了？",Toast.LENGTH_LONG).show();//是否在 Manifest 中声明权限了
                    }
                }
            }
        });
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        EditText editText = findViewById(R.id.editText);
        switch (requestCode){
            case 0:
                if (resultCode == RESULT_OK) {
                    editText.setText(data.getStringArrayListExtra("packages").toString());
                } else {
                    editText.setText(Integer.toString(resultCode));
                }
                break;
            case 1:
                editText.setText(Integer.toString(resultCode));
                break;
            case 2:
                editText.setText(Integer.toString(resultCode));
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
