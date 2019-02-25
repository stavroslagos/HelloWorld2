package helloworld.rolen4lab.lan;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class receive extends AsyncTask<String, Void, String> {
    @Override
    protected String doInBackground(String... strings) {
        int port = Integer.parseInt(strings[0]);
        ServerSocket ss;
        Socket s;
        DataInputStream dis;
        PrintWriter pw;
        String message = null;
        try {
            ss = new ServerSocket(port);
            s = ss.accept();
            InputStreamReader isr = new InputStreamReader(s.getInputStream());
            BufferedReader br = new BufferedReader(isr);
            message = br.readLine();
            //mString2Byte.append("Bot connected : "+message+"\n");
            //onProgressUpdate(message);
            br.close();
            isr.close();
            s.close();
            ss.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return message;
    }

    @Override
    protected void onPostExecute(String s) {
        
    }
}