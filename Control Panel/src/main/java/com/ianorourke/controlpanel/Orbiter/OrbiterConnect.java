package com.ianorourke.controlpanel.Orbiter;

import android.os.AsyncTask;
import android.util.Log;

import com.ianorourke.controlpanel.ShapeObjects.GridController;

import java.io.*;
import java.net.Socket;

public class OrbiterConnect {

    //protected AsyncOrbiterConnection orbConnection;

    private final int DEFAULT_PORT = 37777;

    public void connect(/*String host, int port*/) {
        //AsyncOrbiterConnection orbConnection = new AsyncOrbiterConnection("192.168.1.102", DEFAULT_PORT); //ProBook Home Network
        //AsyncOrbiterConnection orbConnection = new AsyncOrbiterConnection("10.0.2.2", DEFAULT_PORT); //Android Emulator
        AsyncOrbiterConnection orbConnection = new AsyncOrbiterConnection("10.0.3.2", DEFAULT_PORT); //Genymotion Emulator

        //TODO: Fix Socket Init Error

        orbConnection.execute();
    }

    public void disconnect(AsyncOrbiterConnection orbConnection) {
        if (orbConnection == null) return;
        //TODO: Setup Canceling

        if (!orbConnection.isCancelled()) orbConnection.cancel(true);

        orbConnection = null;
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

        protected String doInBackground(Void... params) {
            try {
                socket = new Socket(this.host, this.port);

                socket.setSoTimeout(30000);

                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintStream(socket.getOutputStream(), true);
                //out = new PrintStream(socket.getOutputStream());

            } catch (IOException e) {
                e.printStackTrace();
                return "Socket Init Error";
            }

            //TODO: Remove most sleepThread if possible
            this.sleepThread(1000);

            //TODO: Listen Loop
            //boolean listenLoop = true;

            //TODO: Fix Null Pointer Exception Crash

            try {
                if (socket == null) return GENERIC_ERROR;
                if (!socket.isConnected()) return GENERIC_ERROR;
                Log.v("cp", "Socket: " + socket.toString());

                //final String CHANGE_HUD = "ORB:ToggleHudColor" + "\n";
                final String GET_FOCUS = "FOCUS:Name";
                //final String SET_TIMEWARP = "ORB:SetTimeAccel:5";

                //String message = CHANGE_HUD;

                Log.v("cp", "Message: " + GET_FOCUS);

                out.println(GET_FOCUS + "\r");

                publishProgress("Message Published");

                if (out.checkError()){
                    Log.v("cp", "OUT ERROR");
                    publishProgress("Out Error");
                    return GENERIC_ERROR;
                }

                for (int i = 0; i < 5; i++) {
                    if (!in.ready()) {
                        Log.v("cp", "Continue");

                        this.sleepThread(1000);

                        continue;
                    }

                    String response = in.readLine();
                    Log.v("cp", "Response: " + response);

                    if (response != null){
                        publishProgress(response);
                        break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();

                Log.v("cp", "IO Exception Error");
            } finally {
                try {
                    if (!socket.isClosed()) {
                        //TODO: Add these?
                        //if (!socket.isInputShutdown()) socket.shutdownInput();
                        //if (!socket.isOutputShutdown()) socket.shutdownOutput();
                        socket.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return "00END00";
        }

        protected void onProgressUpdate(String... progress) {
            String update = progress[0];

            Log.v("cp", "Update: " + update);
            GridController.updateRects(update);
        }

        protected void onPostExecute(String response) {
            Log.v("cp", "Ended Task: " + response);
        }
    }
}
