package com.yannis.mycellid;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.telephony.CellInfo;
import android.telephony.NeighboringCellInfo;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.List;
import java.util.jar.Manifest;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class MainActivity extends AppCompatActivity {

    private TelephonyManager mTelephonyManager;
    private CustomPhoneStateListener mCustomPhoneStateListener;
    private TextView mCallStateView, cid, lac, mcc, mnc, ss;
    private EditText user, service;
    private Button startbtn, stopbtn, clearbtn, sendbtn;
    private JSONObject UserData = new JSONObject();
    private String networkOperator;
    private Spinner availableWifis;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLocationPermissions();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        setContentView(R.layout.activity_main);
        mTelephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        mCustomPhoneStateListener = new CustomPhoneStateListener(this);
        networkOperator = mTelephonyManager.getNetworkOperator();

        List<NeighboringCellInfo> cellInfos = (List<NeighboringCellInfo>) this.mTelephonyManager.getNeighboringCellInfo();
        System.out.println("this is the data" + cellInfos.toString());

        mCallStateView = (TextView) findViewById(R.id.CallStateTextView);
        startbtn = (Button) findViewById(R.id.start);
        stopbtn = (Button) findViewById(R.id.stop);
        clearbtn = (Button) findViewById(R.id.clear);
        sendbtn = (Button) findViewById(R.id.send);
        cid = (TextView) findViewById(R.id.vcid);
        lac = (TextView) findViewById(R.id.vlac);
        mcc = (TextView) findViewById(R.id.vmcc);
        mnc = (TextView) findViewById(R.id.vmnc);
        ss = (TextView) findViewById(R.id.vss);
        user = (EditText) findViewById(R.id.user);
        service = (EditText) findViewById(R.id.service);
        availableWifis = (Spinner) findViewById(R.id.spinner);

        if (!TextUtils.isEmpty(networkOperator)) {
            setMcc(Integer.parseInt(networkOperator.substring(0, 3)));
            setMnc(Integer.parseInt(networkOperator.substring(3)));
        }


//        mCallStateView.setMovementMethod(new ScrollingMovementMethod());
        startbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTelephonyManager.listen(mCustomPhoneStateListener,
                        PhoneStateListener.LISTEN_CALL_STATE |
                                PhoneStateListener.LISTEN_DATA_ACTIVITY |
                                PhoneStateListener.LISTEN_DATA_CONNECTION_STATE |
                                PhoneStateListener.LISTEN_SERVICE_STATE |
                                PhoneStateListener.LISTEN_SIGNAL_STRENGTHS |
                                PhoneStateListener.LISTEN_CELL_INFO |
                                PhoneStateListener.LISTEN_CELL_LOCATION
                );
            }
        });

        stopbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTelephonyManager.listen(mCustomPhoneStateListener,
                        PhoneStateListener.LISTEN_NONE
                );
            }
        });

        clearbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallStateView.setText("");
            }
        });

        sendbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    final String url = String.valueOf(service.getText());
                    UserData = createJsonData(Integer.parseInt((String) cid.getText()),
                            Integer.parseInt((String) lac.getText()),
                            Integer.parseInt((String) mcc.getText()),
                            Integer.parseInt((String) mnc.getText()),
                            Integer.parseInt((String) ss.getText()),
                            user.getText(), -96);
//                    new PostData().execute()
                    makeRequest(url, UserData.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public JSONObject createJsonData(int cid, int lac, int mcc, int mnc, int ss, Editable user, int rssi) {
        JSONObject allJsonData = new JSONObject();
        Long tslong = System.currentTimeMillis();
        String ts = tslong.toString();
        try {
            JSONObject userJsonData = new JSONObject();
            JSONObject cellular = new JSONObject();
            JSONObject cellTowers = new JSONObject();
            JSONObject cellinfo = new JSONObject();
            JSONObject considerIp = new JSONObject();
            JSONArray acellular = new JSONArray();

            cellinfo.put("cellId", cid);
            cellinfo.put("locationAreaCode", lac);
            cellinfo.put("mobileCountryCode", mcc);
            cellinfo.put("mobileNetworkCode", mnc);
            cellinfo.put("signalStrength", ss);
            considerIp.put("considerIp", "false");

//            cellTowers.put("cellTowers", cellinfo);
            acellular.put(cellinfo);
//            acellular.put(considerIp);
//            cellTowers.put("cellTowers", acellular);

            cellular.put("considerIp", "false");
            cellular.put("cellTowers", acellular);

            userJsonData.put("time_stamp", ts);
            userJsonData.put("user", user);
            userJsonData.put("RSSI", rssi);
            userJsonData.put("cellular", cellular);

            allJsonData = userJsonData;
            System.out.println(allJsonData);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return allJsonData;
    }


    public static String makeRequest(String url, String data) {
        String answer = "";
        try {
            MediaType JSON = MediaType.parse("application/json; charset=utf-8");

            OkHttpClient client = new OkHttpClient();

            RequestBody body = RequestBody.create(JSON, data);

            Request request = new Request.Builder()
                    .addHeader("Content-Type", "application/json")
                    .url(url)
                    .post(body)
                    .build();

            Response response = client.newCall(request).execute();
            answer = response.body().toString();
            response.body().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return answer;
    }


//    public static String makeRequest(String uri, String json) {
//        HttpURLConnection urlConnection;
//        String url;
//        String data = json;
//        String result = null;
//        try {
//            //Connect
//            urlConnection = (HttpURLConnection) ((new URL(uri).openConnection()));
//            urlConnection.setDoOutput(true);
//            urlConnection.setRequestProperty("Content-Type", "application/json");
//            urlConnection.setRequestProperty("Accept", "application/json");
//            urlConnection.setRequestMethod("POST");
//            urlConnection.connect();
//
//            //Write
//            OutputStream outputStream = urlConnection.getOutputStream();
//            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
//            writer.write(data);
//            writer.close();
//            outputStream.close();
//
//            //Read
//            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));
//
//            String line = null;
//            StringBuilder sb = new StringBuilder();
//
//            while ((line = bufferedReader.readLine()) != null) {
//                sb.append(line);
//            }
//
//            bufferedReader.close();
//            result = sb.toString();
//
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return result;
//    }


//    public void sendDatatoService(JSONObject userData) throws IOException {
//
//        try {
//            userData.put("user", "yannis");
//            userData.put("mcc", 310);
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        URL url = new URL("http://10.0.3.96:5000/ss");
//        HttpURLConnection client = null;
//        try {
//            client = (HttpURLConnection) url.openConnection();
//            client.setRequestMethod("POST");
//            OutputStream outputPost = new BufferedOutputStream(client.getOutputStream());
//            client.setDoOutput(true);
//            writeStream(outputPost, userData);
//
//        } catch (IOException error) {
//            //Handles input and output errors
//            error.printStackTrace();
//        } finally {
//            if (client != null)
//                client.disconnect();
//        }
//    }
//
//    public void writeStream(OutputStream out, JSONObject userData) {
//        String outputText = userData.toString();
//
//        try {
//            out.write(outputText.getBytes());
//            out.flush();
//            out.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }

    public void log(String text) {
        mCallStateView.setText(text);
    }

    public String getLogText() {
        return (String) mCallStateView.getText();
    }

    public void setCID(int vcid) {
        cid.setText(String.valueOf(vcid));
    }

    public void setLac(int vlac) {
        lac.setText(String.valueOf(vlac));
    }

    public void setSS(int vss) {
        ss.setText(String.valueOf(vss));
    }

    public void setMcc(int vmcc) {
        mcc.setText(String.valueOf(vmcc));
    }

    public void setMnc(int vmnc) {
        mnc.setText(String.valueOf(vmnc));
    }
//    @Override
//    protected void onStart() {
//        super.onStart();
//        mTelephonyManager.listen(mCustomPhoneStateListener,
//                PhoneStateListener.LISTEN_CALL_STATE |
//                        PhoneStateListener.LISTEN_DATA_ACTIVITY |
//                        PhoneStateListener.LISTEN_DATA_CONNECTION_STATE |
//                        PhoneStateListener.LISTEN_SERVICE_STATE |
//                        PhoneStateListener.LISTEN_SIGNAL_STRENGTHS |
//                        PhoneStateListener.LISTEN_CELL_INFO |
//                        PhoneStateListener.LISTEN_CELL_LOCATION
//        );
//    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }


    private static final int LOCATION_PERMISSIONS_REQUEST = 123;

    @TargetApi(Build.VERSION_CODES.M)
    public void getLocationPermissions() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (shouldShowRequestPermissionRationale(android.Manifest.permission.ACCESS_COARSE_LOCATION)) {

            }
            requestPermissions(new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION}, LOCATION_PERMISSIONS_REQUEST);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        if (requestCode == LOCATION_PERMISSIONS_REQUEST) {
            if (grantResults.length == 1 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Location Permissin Granted", Toast.LENGTH_SHORT).show();
            } else {
//                boolean showRationale = shouldShowRequestPermissionRationale(this, android.Manifest.permission.ACCESS_COARSE_LOCATION);

//                if (showRationale){
//
//                }
//                else {
//                    Toast.makeText(this, "Location Permission Dennied", Toast.LENGTH_SHORT).show();
//                }
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}




