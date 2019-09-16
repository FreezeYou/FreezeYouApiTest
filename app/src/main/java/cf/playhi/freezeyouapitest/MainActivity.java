package cf.playhi.freezeyouapitest;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;

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
        Button urlButton = findViewById(R.id.url);
        Button getCurrentModeUseProvider = findViewById(R.id.getCurrentMode_useProvider);
        Button getIfCanInstallApplicationsStatusUseProvider = findViewById(R.id.getIfCanInstallApplicationsStatus_useProvider);
        Button disableUseProvider = findViewById(R.id.disable_useProvider);
        Button enableUseProvider = findViewById(R.id.enable_useProvider);
        Button getDisableStatusUseProvider = findViewById(R.id.getDisableStatus_useProvider);
        final EditText editText = findViewById(R.id.editText);

        get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setComponent(new ComponentName("cf.playhi.freezeyou", "cf.playhi.freezeyou.GetDisabledApplications"));
                try {
                    startActivityForResult(intent, 0);
                } catch (ActivityNotFoundException e) {
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this, "老版本 FreezeYou 或未安装 FreezeYou ", Toast.LENGTH_LONG).show();//老版本FreezeYou或未安装FreezeYou
                } catch (SecurityException e) {
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this, "是否在 Manifest 中声明权限了？", Toast.LENGTH_LONG).show();//是否在 Manifest 中声明权限了
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
                    intent.putExtra("packages", strings);
                    intent.setComponent(new ComponentName("cf.playhi.freezeyou", "cf.playhi.freezeyou.DisableApplications"));
                    try {
                        startActivityForResult(intent, 1);
                    } catch (ActivityNotFoundException e) {
                        e.printStackTrace();
                        Toast.makeText(MainActivity.this, "老版本 FreezeYou 或未安装 FreezeYou ", Toast.LENGTH_LONG).show();//老版本FreezeYou或未安装FreezeYou
                    } catch (SecurityException e) {
                        e.printStackTrace();
                        Toast.makeText(MainActivity.this, "是否在 Manifest 中声明权限了？", Toast.LENGTH_LONG).show();//是否在 Manifest 中声明权限了
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
                    intent.putExtra("packages", strings);
                    intent.setComponent(new ComponentName("cf.playhi.freezeyou", "cf.playhi.freezeyou.EnableApplications"));
                    try {
                        startActivityForResult(intent, 2);
                    } catch (ActivityNotFoundException e) {
                        e.printStackTrace();
                        Toast.makeText(MainActivity.this, "老版本 FreezeYou 或未安装 FreezeYou ", Toast.LENGTH_LONG).show();//老版本FreezeYou或未安装FreezeYou
                    } catch (SecurityException e) {
                        e.printStackTrace();
                        Toast.makeText(MainActivity.this, "是否在 Manifest 中声明权限了？", Toast.LENGTH_LONG).show();//是否在 Manifest 中声明权限了
                    }
                }
            }
        });

        //如需通过网页、WebView调用，可使用 <a href="freezeyou://fuf/?pkgName=com.android.gallery3d">提示信息</a>
        urlButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] strings = editText.getText().toString().split("\n");
                //批量请求实际生效（留存）为最后一次请求的内容，先前的请求会被直接忽略，防止恶意（无限、超大量）请求
                for (String pkgName : strings) {
                    Uri webPage = Uri.parse("freezeyou://fuf/?pkgName=" + pkgName);//调冻结解冻启动弹窗
                    Intent intent = new Intent(Intent.ACTION_VIEW, webPage);
                    if (intent.resolveActivity(getPackageManager()) != null) {
                        startActivity(intent);
                    } else {
                        Toast.makeText(MainActivity.this, "无可用程序，是否已安装 FreezeYou 7.2 及以上版本呢？", Toast.LENGTH_LONG).show();//无可用程序，是否已安装 FreezeYou 7.2 及以上版本呢？
                    }
                }
            }
        });
        //如需通过网页、WebView调用，可使用 <a href="freezeyou://fuf/?pkgName=com.android.gallery3d">提示信息</a>

        getCurrentModeUseProvider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle resultBundle = getContentResolver().call(
                        Uri.parse("content://cf.playhi.freezeyou.export.QUERY"), "QUERY_MODE",
                        null, new Bundle()
                );
                if (resultBundle != null) {
                    // 返回值
                    Toast.makeText(MainActivity.this, resultBundle.getString("currentMode", "Failed"), Toast.LENGTH_LONG).show();
                }
            }
        });

        getIfCanInstallApplicationsStatusUseProvider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle resultBundle = getContentResolver().call(
                        Uri.parse("content://cf.playhi.freezeyou.export.QUERY"), "QUERY_IF_CAN_INSTALL_APPLICATIONS_STATUS",
                        null, new Bundle()
                );
                if (resultBundle != null) {
                    // 返回值
                    boolean[] b = resultBundle.getBooleanArray("status");
                    if (b != null) {
                        Toast.makeText(
                                MainActivity.this,
                                "boolean[]{" + b[0] + "," + b[1] + "," + b[2] + "," + b[3] + "}",
                                Toast.LENGTH_LONG
                        ).show();
                    }
                }
            }
        });

        disableUseProvider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(MainActivity.this,
                        "cf.playhi.freezeyou.permission.DISABLE_APPLICATIONS")
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MainActivity.this,
                            new String[]{"cf.playhi.freezeyou.permission.DISABLE_APPLICATIONS"},
                            10);
                } else {
                    String pkgName = editText.getText().toString();
                    Bundle willBeSend = new Bundle();
                    willBeSend.putString("packageName", pkgName);
                    Bundle resultBundle = getContentResolver().call(
                            Uri.parse("content://cf.playhi.freezeyou.export.FREEZE"), "MODE_AUTO",
                            null, willBeSend
                    );
                    if (resultBundle != null) {
                        // 返回值
                        Toast.makeText(MainActivity.this, Integer.toString(resultBundle.getInt("result", 123456)), Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        enableUseProvider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(MainActivity.this,
                        "cf.playhi.freezeyou.permission.ENABLE_APPLICATIONS")
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MainActivity.this,
                            new String[]{"cf.playhi.freezeyou.permission.ENABLE_APPLICATIONS"},
                            11);
                } else {
                    String pkgName = editText.getText().toString();
                    Bundle willBeSend = new Bundle();
                    willBeSend.putString("packageName", pkgName);
                    Bundle resultBundle = getContentResolver().call(
                            Uri.parse("content://cf.playhi.freezeyou.export.UNFREEZE"), "MODE_AUTO",
                            null, willBeSend
                    );
                    if (resultBundle != null) {
                        // 返回值
                        Toast.makeText(MainActivity.this, Integer.toString(resultBundle.getInt("result", 123456)), Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        getDisableStatusUseProvider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pkgName = editText.getText().toString();
                Bundle willBeSend = new Bundle();
                willBeSend.putString("packageName", pkgName);
                Bundle resultBundle = getContentResolver().call(
                        Uri.parse("content://cf.playhi.freezeyou.export.QUERY"), "QUERY_FREEZE_STATUS",
                        null, willBeSend
                );
                if (resultBundle != null) {
                    // 返回值
                    Toast.makeText(MainActivity.this, Integer.toString(resultBundle.getInt("status", 123456)), Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        EditText editText = findViewById(R.id.editText);
        switch (requestCode) {
            case 0:
                if (resultCode == RESULT_OK) {
                    editText.setText(data.getStringArrayListExtra("packages").toString());
                } else {
                    Toast.makeText(MainActivity.this, Integer.toString(resultCode), Toast.LENGTH_LONG).show();
                }
                break;
            case 1:
                Toast.makeText(MainActivity.this, Integer.toString(resultCode), Toast.LENGTH_LONG).show();
                break;
            case 2:
                Toast.makeText(MainActivity.this, Integer.toString(resultCode), Toast.LENGTH_LONG).show();
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
