package helloworld.rolen4lab.lan;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
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

// test info

public class MainActivity extends AppCompatActivity {
    private TextView mString2Byte;
    private EditText mPlainString;
    private boolean status=false;
    private String localip="127.0.0.1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mString2Byte = (TextView) findViewById(R.id.tv_byte_view);
        mPlainString = (EditText) findViewById(R.id.et_string_to_byte);

    }

    private void GetLocalIP() {
        ConnectivityManager connectivitymanager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activenetworkInfo = connectivitymanager.getActiveNetworkInfo();
        if (activenetworkInfo != null) {
            mString2Byte.setText("Internet available\n");
            status = true;
            localip = connectivitymanager.getActiveNetworkInfo().toString();
        } else {
            mString2Byte.setText("Internet NOT available\n");
        }
    }

    private void storeimage(Bitmap bm, String filename){
        String dirpath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/tmp8ack";
        File dir = new File(dirpath);
        if (!dir.exists()){
            dir.mkdir();
        }
        File file = new File(dirpath, filename);
        try {
            FileOutputStream fos = new FileOutputStream(file);
        } catch (IOException e){
            e.printStackTrace();
        }
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

    void convertImage2Byte() {
        //Invoke the photogallery
        //Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);

        //Where to find the data
        File pictureDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS+"/freepik.jpg");
        mString2Byte.append(pictureDirectory.toString());
        try {
            //byte[] image2byte = Files.readAllBytes(pictureDirectory.toPath());
            byte[] image2byte = (byte[]) Files.readAllBytes(Paths.get("logo.jpg"));
            int i2blength = image2byte.length;
            int i;
            mString2Byte.append("Array length : " + i2blength + "\n");
            for (i = 0; i < i2blength; i++) {
                mString2Byte.append("Array on place : " + i + "__Value : " + image2byte[i] + "\n");

            }

        }catch (IOException e) {
            e.printStackTrace();
        }



            // Uri representation
        //Uri data = Uri.parse(pictureDirectorypath);

        // Set data and type. This gets all the images
        //photoPickerIntent.setDataAndType(data,"image/*");

        // invoke the activity and get something back from it



    }

    private void startBotMasterConnection(){
        ServerSocket ss;
        Socket s;
        DataInputStream dis;
        PrintWriter pw;
        try {
            ss = new ServerSocket(9999);
            s = ss.accept();
            InputStreamReader isr = new InputStreamReader(s.getInputStream());
            BufferedReader br = new BufferedReader(isr);
            String message = br.readLine();
            mString2Byte.append("Bot connected : "+message+"\n");
            br.close();
            isr.close();
            s.close();
            ss.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void connect2BotMaster() {
        DataOutputStream dos;
        PrintWriter pw = null;
        Socket s1;
        try {
            s1 = new Socket("192.168.1.8", 9999);
            pw = new PrintWriter(s1.getOutputStream());
            pw.write("bot1.started");
            pw.flush();
            pw.close();
            //s1.close();

        } catch (IOException ex){
            ex.printStackTrace();
        }
    }

    void menu3WasClicked(){
        mString2Byte.append("Check internet connection\n");
        GetLocalIP();
        if(status){
            mString2Byte.append("Internet up. Connecting to bot master\n"+localip+"\n");
            connect2BotMaster();
        }else {
            mString2Byte.append("Internet is down\n");
        }

    }

    void menu4WasClicked(){
        mString2Byte.append("\nCheck internet connection");
        GetLocalIP();
        if(status){
            mString2Byte.append("Internet up. Starting server\n"+localip+"\n");
        }else {
            mString2Byte.append("Internet is down\n");
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
                Toast.makeText(this,"Image 2 byte array", Toast.LENGTH_LONG).show();
                convertImage2Byte();
                return true;
            }else {
                if (itemThatWasClickedId==R.id.action_menu3){
                    Toast.makeText(this, "Menu 3 was clicked", Toast.LENGTH_LONG).show();
                    menu3WasClicked();
                    return true;
                } else {
                    if (itemThatWasClickedId==R.id.action_menu4){
                        Toast.makeText(this,"Menu 4 was clicked", Toast.LENGTH_LONG).show();
                        menu4WasClicked();
                        return true;
                    }
                }
            }
        }
        return super.onOptionsItemSelected(item);
    }
    }

