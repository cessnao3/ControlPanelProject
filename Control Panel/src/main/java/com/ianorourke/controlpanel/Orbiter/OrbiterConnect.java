package com.ianorourke.controlpanel.Orbiter;

import android.os.AsyncTask;
import android.util.Log;

import com.ianorourke.controlpanel.ShapeObjects.GridController;

import java.io.*;
import java.net.Socket;

public class OrbiterConnect {

    public void connectToOrbiter(/*String host, int port*/) {
        AsyncOrbiterConnection orbConnection = new AsyncOrbiterConnection("192.168.1.102", 37777);

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

        protected String doInBackground(Void... params) {
            Log.v("cp", "Connecting to: " + host + ":" + new Integer(port).toString());

            try {
                socket = new Socket(this.host, this.port);

                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintStream(new BufferedOutputStream(socket.getOutputStream()), true);

                socket.setSoTimeout(20000);

            } catch (IOException e) {
                e.printStackTrace();
            }

            Log.v("cp", "Socket: " + socket.toString());

            this.sleepThread(5000);

            try {
                if (!socket.isConnected()) return GENERIC_ERROR;

                out.println("ORB:GBodyCount");
                out.flush();

                if (out.checkError()) Log.v("cp", "Send Error");

                this.sleepThread(500);

                if (!socket.isOutputShutdown()) socket.shutdownOutput();

                for (int i = 0; i < 5; i++) {
                    this.sleepThread(500);

                    if (!in.ready()) {
                        Log.v("cp", "Continue");
                        continue;
                    }

                    int test = in.read();
                    Log.v("cp", "Read: " + new Integer(test).toString());

                    String response = in.readLine();
                    Log.v("cp", "Response " + new Integer(i + 1).toString() + ": " + response);

                    if (response != null){
                        onProgressUpdate(response);
                        break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();

                Log.v("cp", "IO Exception Error");
            } finally {
                try {
                    if (!socket.isClosed()) {
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
            Log.v("cp", "Ended Task");
        }
    }
}
