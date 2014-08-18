package com.example.testkeystore;

import javax.crypto.SecretKey;

import org.nick.androidkeystore.Crypto;
import org.nick.androidkeystore.android.security.KeyStore;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements View.OnClickListener {

    private EditText evPwd;
    private TextView tvInfo;
    private Button btnOk;
    KeyStore ks = KeyStore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        evPwd = (EditText) findViewById(R.id.ev_pwd);
        tvInfo = (TextView) findViewById(R.id.tv_info);
        btnOk = (Button) findViewById(R.id.btn_ok);
        btnOk.setOnClickListener(this);
        init();
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        init();
    }

    private void init() {
        byte[] result = ks.get("key1");
        if (result != null && result.length > 0) {
            String str = new String(result);
            Log.i("test", str);
            tvInfo.setText("有证书" + "");
            evPwd.setVisibility(View.INVISIBLE);
            btnOk.setVisibility(View.INVISIBLE);
        } else {
            tvInfo.setText("没有证书");
            evPwd.setVisibility(View.VISIBLE);
            btnOk.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        case R.id.btn_ok:
            SecretKey key = Crypto.generateAesKey();
            boolean success = ks.put("key1", key.getEncoded());
            if (success) {
                Toast.makeText(this, "写入证书成功", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "写入证书失败", Toast.LENGTH_SHORT).show();
            }
            break;
        default:
            break;
        }
    }

}
