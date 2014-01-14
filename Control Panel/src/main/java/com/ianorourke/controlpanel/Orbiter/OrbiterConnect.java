package com.ianorourke.controlpanel.Orbiter;

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.*;
import java.net.Socket;
import java.util.List;

public class OrbiterConnect {
    private AsyncOrbiterConnection orbConnection;
    private final int DEFAULT_PORT = 37777;

    protected static Context mainContext = null;

    public void connect(String host) {
        this.connect(host, DEFAULT_PORT);
    }

    public void connect(String host, int port) {
        if (isConnected()) {
            disconnect();
            return;
        }

        Log.v("cp", "Connection Started");

        orbConnection = new AsyncOrbiterConnection(host, port);
        orbConnection.execute();
    }

    public void disconnect() {
        orbConnection.cancel(false);
        OrbiterData.resetData();
        Log.v("cp", "Connection Canceled");
    }

    public boolean isConnected() {
        if (orbConnection != null) {
            return (orbConnection.getStatus() == AsyncTask.Status.RUNNING);
        } else return false;
    }

    public void setContext(Context c) {
        this.mainContext = c;
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

        private boolean shouldCancel() {
            return (!socket.isConnected() || isCancelled());
        }

        protected void onPreExecute() {
            OrbiterData.clearSubscriptionMap();
        }

        protected String doInBackground(Void... params) {
            try {
                socket = new Socket(this.host, this.port);

                socket.setSoTimeout(5000);

                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintStream(socket.getOutputStream(), true);
            } catch (IOException e) {
                e.printStackTrace();
                return "Socket Init Error";
            }

            if (socket == null || !socket.isConnected()) return GENERIC_ERROR;

            String[] subscribeMessages = OrbiterData.getSubscriptions();

            for (int i = 0; i < subscribeMessages.length; i++) {
                out.println(subscribeMessages[i] + "\r");
                Log.v("cp", "Sent: " + subscribeMessages[i]);
            }

            //Listening Loop
            while (true) {
                try {
                    if (shouldCancel()) break;

                    if (OrbiterMessages.hasMessages()) {
                        List<String> messages = OrbiterMessages.getMessages();

                        for (int i = 0; i < messages.size(); i++) {
                            out.println(messages.get(i) + "\r");
                        }

                        OrbiterMessages.clearMessages();
                    }

                    while (in.ready()) {
                        if (OrbiterMessages.hasMessages() || shouldCancel()) break;
                        publishProgress(in.readLine());
                    }
                } catch (IOException e) {
                    e.printStackTrace();

                    Log.v("cp", "IO Exception Error");
                    break;
                }

                this.sleepThread(250);
            }

            //Unsubscribe from Existing Subscriptions
            if (socket.isConnected()) {
                String[] ids = OrbiterData.getSubscriptionIds();

                for (int i = 0; i < ids.length; i++) {
                    out.println("UNSUBSCRIBE:" + ids[i] + "\r");
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
            if (progress[0] != null) OrbiterData.parseMessage(progress[0]);
        }

        protected void onPostExecute(String response) {
            if (response != null || !response.equals("")) Log.v("cp", "Ended Task: " + response);
            else Log.v("cp", "Ended Task");

            if (!response.equals("00END00")) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mainContext);
                builder.setTitle("Error!");
                builder.setMessage("Could not Connect to Specified IP Address" + "\n\n" + host + ":" + Integer.valueOf(port).toString());
                builder.setPositiveButton("Ok", null);
                builder.create().show();
            }

            OrbiterData.clearSubscriptionMap();
        }
    }
}
