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

    public void connectToOrbiter() {
        AsyncOrbiterConnection orbConnection = new AsyncOrbiterConnection("192.168.173.1", 37777);

        orbConnection.execute();
    }

    private static class AsyncOrbiterConnection extends AsyncTask<Void, String, String> {
        final String GENERIC_ERROR = "ERR00";

        int port;
        String host;

        Socket socket;

        PrintStream out;
        BufferedReader in;

        String testString = "FOCUS:Name";

        public AsyncOrbiterConnection(String host, int port) {
            this.port = port;
            this.host = host;
        }

        protected String doInBackground(Void... params) {
            Log.v("cp", "Connecting to: " + host + ":" + new Integer(port).toString());


            try {
                if (socket == null || socket.isClosed()) {
                    socket = new Socket(host, port);

                    //socket = new Socket()
                    //socket.setKeepAlive(true);
                }
                if (out == null || socket.isOutputShutdown()) {
                    out = new PrintStream(socket.getOutputStream(), true);
                }
                if (in == null || socket.isInputShutdown()) {
                    in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                }
            } catch (UnknownHostException e) {
                e.printStackTrace();

                Log.v("cp", "Unknown Host: " + host);

                return GENERIC_ERROR;
            } catch (IOException e) {
                e.printStackTrace();

                Log.v("cp", "IO Exception 1");

                return GENERIC_ERROR;
            }

            //if (socket == null || in == null || out == null) return "ERR00";

            boolean loop = true;

            //While loop = true

            //TODO: GET SOCKET

            if (!socket.isConnected()) return GENERIC_ERROR;

            try {
                out.println(this.testString);

                String response = in.readLine();
                if (response == null || response.length() == 0) {
                    if (socket.isConnected()) socket.close();
                    return "ERR00";
                }

                publishProgress(response);

                return response;
            } catch (IOException e) {
                e.printStackTrace();
                return "ERR00";
            } catch (NullPointerException e) {
                e.printStackTrace();
            }

            return "00END00";
        }

        protected void onProgressUpdate(String... progress) {
            Log.v("cp", progress[0]);
        }

        protected void onPostExecute(String response) {
            Log.v("cp", "Ended Task: " + response);
        }
    }
}
