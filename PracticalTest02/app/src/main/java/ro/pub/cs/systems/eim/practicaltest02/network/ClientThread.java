package ro.pub.cs.systems.eim.practicaltest02.network;

import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import ro.pub.cs.systems.eim.practicaltest02.general.Constants;
import ro.pub.cs.systems.eim.practicaltest02.general.Utilities;

/**
 * Created by BGL on 5/18/2017.
 */

public class ClientThread extends Thread {

    private Socket socket;

    private String address;
    private int port;
    private TextView webInformationTextView;
    //TODO other info
    //private String info_i

    //TODO constructor with all params
    //set in constructor all fields also textview

    @Override
    public void run() {
        try {
            socket = new Socket(address, port);
            if (socket == null) {
                Log.e(Constants.TAG, "[CLIENT THREAD] Could not create socket!");
            }

            BufferedReader bufferedReader = Utilities.getReader(socket);
            PrintWriter printWriter = Utilities.getWriter(socket);
            if (bufferedReader != null && printWriter != null) {
                //send input info to web server
                //TODO printWriter.println(info_i);
                //printWriter.flush();

                String webInformation;
                while ((webInformation = bufferedReader.readLine()) != null) {
                    final String finalizedWebInformation = webInformation;
                    webInformationTextView.post(new Runnable() {
                        @Override
                        public void run() {
                            webInformationTextView.append(finalizedWebInformation + "\n");
                        }
                    });
                }
            } else {
                Log.e(Constants.TAG, "[CLIENT THREAD] BufferedReader / PrintWriter are null!");
            }
            socket.close();
        } catch (IOException ioException) {
            Log.e(Constants.TAG, "[CLIENT THREAD] An exception has occurred: " + ioException.getMessage());
            if (Constants.DEBUG) {
                ioException.printStackTrace();
            }
        }
    }
}
