package com.ianorourke.controlpanel.Orbiter;

import android.os.AsyncTask;
import android.util.Log;

import com.ianorourke.controlpanel.ShapeObjects.GridController;

import java.io.*;
import java.net.Socket;

public class OrbiterConnect {

    private AsyncOrbiterConnection orbConnection;

    //private boolean isConnected = true;

    private final int DEFAULT_PORT = 37777;

    public void connect(/*String host, int port*/) {
        if (orbConnection != null && orbConnection.getStatus() == AsyncTask.Status.RUNNING) {
            orbConnection.cancel(false);
            return;
        }

        //orbConnection = new AsyncOrbiterConnection("192.168.1.102", DEFAULT_PORT); //ProBook Home Network
        orbConnection = new AsyncOrbiterConnection("10.72.17.5", DEFAULT_PORT); //School Network
        //orbConnection = new AsyncOrbiterConnection("10.0.2.2", DEFAULT_PORT); //Android Emulator
        //orbConnection = new AsyncOrbiterConnection("10.0.3.2", DEFAULT_PORT); //Genymotion Emulator

        orbConnection.execute();
    }

    private static class AsyncOrbiterConnection extends AsyncTask<Void, String, String> {
        final String GENERIC_ERROR = "ERR00";

        private int port;
        private String host;

        private Socket socket;

        private PrintStream out;
        private BufferedReader in;

        public AsyncOrbiterConnection(String host, int port) {
            this.port = port;
            this.host = host;
        }

        private void sleepThread(int mill) {
            try {
                Thread.sleep(mill);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        protected void onPreExecute() {
            OrbiterData.clearSubscriptionMap();
        }

        protected String doInBackground(Void... params) {
            try {
                socket = new Socket(this.host, this.port);

                socket.setSoTimeout(30000);

                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintStream(socket.getOutputStream(), true);
            } catch (IOException e) {
                e.printStackTrace();
                return "Socket Init Error";
            }

            if (socket == null) return GENERIC_ERROR;

            //Try Count
            for (int i = 0; i < 10; i++) {
                if (socket.isConnected()) break;

                Log.v("cp", "Socket Not Ready");
                this.sleepThread(250);
            }

            if (!socket.isConnected()) return GENERIC_ERROR;

            String[] subscribeMessages = OrbiterData.getSubscriptions();

            for (int i = 0; i < subscribeMessages.length; i++) {
                out.println(subscribeMessages[i] + "\r");
                Log.v("cp", "Sent: " + subscribeMessages[i]);
            }

            //Listening Loop
            while (true) {
                try {
                    if (isCancelled() || !socket.isConnected()) {
                        //if (!socket.isConnected()) return GENERIC_ERROR;
                        break;
                    }

                    if (OrbiterData.getMessage().equals("")){
                        out.println(OrbiterData.getMessage() + "\r");
                        OrbiterData.setMessage("");
                    }

                    while (in.ready()) {
                        publishProgress(in.readLine());
                    }
                } catch (IOException e) {
                    e.printStackTrace();

                    Log.v("cp", "IO Exception Error");
                    break;
                }

                this.sleepThread(1000);
            }

            //Unsubscribe
            if (socket.isConnected()) {
                for (String key : OrbiterData.getSubscriptionMap().keySet()) {
                    out.println("UNSUBSCRIBE:" + key + "\r");
                }
            } else return GENERIC_ERROR;

            //Disconnect
            try {
                if (!socket.isClosed()) socket.close();
                if (!socket.isInputShutdown()) socket.shutdownInput();
                if (!socket.isOutputShutdown()) socket.shutdownOutput();
            } catch (IOException e) {
                e.printStackTrace();
            }

            //End Code

            return "00END00";
        }

        protected void onProgressUpdate(String... progress) {
            if (progress[0] == null) return;
            OrbiterData.parseMessage(progress[0]);
        }

        protected void onPostExecute(String response) {
            Log.v("cp", "Ended Task: " + response);

            OrbiterData.clearSubscriptionMap();
        }
    }
}
