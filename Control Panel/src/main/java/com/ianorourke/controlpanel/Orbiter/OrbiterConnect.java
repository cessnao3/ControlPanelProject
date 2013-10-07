package com.ianorourke.controlpanel.Orbiter;

import android.os.AsyncTask;
import android.util.Log;

import com.ianorourke.controlpanel.ShapeObjects.GridController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class OrbiterConnect {

    public void connectToOrbiter(/*String host, int port*/) {
        //AsyncOrbiterConnection orbConnection = new AsyncOrbiterConnection("192.168.73.1", 37777);
        AsyncOrbiterConnection orbConnection = new AsyncOrbiterConnection("10.0.2.2", 37777);

        orbConnection.execute();
    }

    private static class AsyncOrbiterConnection extends AsyncTask<Void, String, String> {
        final String GENERIC_ERROR = "ERR00";

        private int port;
        private String host;

        private String testString = "FOCUS:Name";

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
            Log.v("cp", "Connecting to: " + host + ":" + new Integer(port).toString());
            //TODO: Rewrite Connection Code
            return "00END00";
        }

        protected void onProgressUpdate(String... progress) {
            String update = progress[0];

            Log.v("cp", "Update: " + update);
            GridController.updateRects(update);
        }

        protected void onPostExecute(String response) {
            Log.v("cp", "Ended Task");
        }
    }
}
