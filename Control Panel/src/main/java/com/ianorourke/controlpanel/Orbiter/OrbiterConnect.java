package com.ianorourke.controlpanel.Orbiter;

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.List;

public class OrbiterConnect {
    private AsyncOrbiterConnection orbConnection;

    protected static Context mainContext = null;

    public void connect(String host) {
        final int DEFAULT_PORT = 37777;
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
        OrbiterStatus.resetOrbiterStatus();
        Log.v("cp", "Connection Canceled");
    }

    public boolean isConnected() {
        return orbConnection != null && (orbConnection.getStatus() == AsyncTask.Status.RUNNING);
    }

    public static void setContext(Context c) {
        mainContext = c;
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
                socket = new Socket();

                socket.setSoTimeout(5000);
                socket.connect(new InetSocketAddress(this.host, this.port), socket.getSoTimeout());

                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintStream(socket.getOutputStream(), true);
            } catch (IOException e) {
                e.printStackTrace();
                return "Socket Init Error";
            }

            if (socket == null || !socket.isConnected()) return GENERIC_ERROR;

            String[] subscribeMessages = OrbiterData.getSubscriptions();

            for (String message : subscribeMessages) {
                out.println(message + "\r");
                Log.v("cp", "Sent: " + message);
            }

            //Listening Loop
            while (true) {
                try {
                    if (shouldCancel()) break;

                    if (OrbiterMessages.hasMessages()) {
                        List<String> messages = OrbiterMessages.getMessages();

                        for (String message : messages) {
                            out.println(message + "\r");
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

                for (String id : ids) {
                    out.println("UNSUBSCRIBE:" + id + "\r");
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

            //00END00 for Manual Cancel
            //00END01 for Connection Lost
            return (this.isCancelled()) ? "00END00" : "00END01";
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

                if (response.equals("00END01")) builder.setMessage("Lost Connection to " + host);
                else builder.setMessage("Could not Connect to Specified IP Address" + "\n\n" + host + ":" + Integer.valueOf(port).toString());

                builder.setPositiveButton("Ok", null);
                builder.create().show();
            }

            OrbiterData.clearSubscriptionMap();
        }
    }
}
