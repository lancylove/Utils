package com.lancy.utils.wifi;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.lancy.utils.R;


/**
 * 获取手机WIFI的MAC地址
 * @author 单红宇
 *
 */
public class GetmacipinfoActivity extends Activity {
    /** Called when the activity is first created. */
    private static final int REQUEST_ENABLE_BT = 3;
    private WifiManager mWifi;
    private WifiConfiguration wcg;
 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mWifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
 
        if (!mWifi.isWifiEnabled()) {
            mWifi.setWifiEnabled(true);
        }
 
        WifiInfo wifiInfo = mWifi.getConnectionInfo();
        
        TextView mac = (TextView) findViewById(R.id.textview);
        mac.setTextSize(16);
 
        
        mac.setText("WIFI网络信息:  " + wifiInfo.getSSID());
        
        init();
        
    }
    
    private void init(){
       
        wcg = new WifiConfiguration();
      //创建一个新的WifiConfiguration
        WifiConfiguration wcg = new WifiConfiguration();
        wcg.BSSID = "test";
        //SSID和preSharedKey必须添加双引号，否则将会导致连接失败
        wcg.SSID = "\"test\""; 

        wcg.hiddenSSID = false;
        wcg.status = WifiConfiguration.Status.ENABLED;
        wcg.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN);
        wcg.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.SHARED);
        wcg.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
        wcg.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
        wcg.allowedProtocols.set(WifiConfiguration.Protocol.RSN);
        wcg.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
       
           wcg.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
           wcg.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40);
           wcg.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP104);
           wcg.wepKeys[0] ="\"" + "1234567890"+ "\""; //This is the WEP Password
           wcg.wepTxKeyIndex = 0;
       
        Log.i("test", "ssid---"+wcg.SSID);
       
        int netId = mWifi.addNetwork(wcg);
        
        
        Toast.makeText(this, "netId:"+netId, 0).show();
    }
    
    

}