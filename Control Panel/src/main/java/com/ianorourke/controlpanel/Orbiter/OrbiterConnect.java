package com.ianorourke.controlpanel.Orbiter;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class OrbiterConnect {
    private int port = 37777;
    private String host = "10.72.35.14";

    public void connectToOrbiter() {
        AsyncOrbiterConnection orbConnection = new AsyncOrbiterConnection(host, port);

        orbConnection.execute();
    }

    private static class AsyncOrbiterConnection extends AsyncTask<Void, String, String> {
        int port;
        String host;

        Socket socket;

        PrintStream out;
        BufferedReader in;

        public AsyncOrbiterConnection(String host, int port) {
            this.port = port;
            this.host = host;
        }

        protected void onPreExecute() {
            try {
                if (socket == null || socket.isClosed()) {
                    socket = new Socket(host, port);
                }
                if (out == null || socket.isOutputShutdown()) {
                    out = new PrintStream(socket.getOutputStream(), true);
                }
                if (in == null || socket.isInputShutdown()) {
                    in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                }


            } catch (UnknownHostException e) {
                e.printStackTrace();

                socket = null;
                out = null;
                in = null;
            } catch (IOException e) {
                e.printStackTrace();

                socket = null;
                out = null;
                in = null;
            }
        }

        protected String doInBackground(Void... params) {
            if (socket == null || in == null || out == null) return "ERR00";

            //TODO: GET SOCKET

            return "HELLO!!!";
        }

        protected void onProgressUpdate(String... progress) {

        }

        protected void onPostExecute(String response) {
            Log.v("cp", "Ended Task");
        }
    }
}
