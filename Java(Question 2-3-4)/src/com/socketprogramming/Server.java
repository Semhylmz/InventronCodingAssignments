package com.socketprogramming;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalTime;
import java.util.Timer;
import java.util.TimerTask;

public class Server {
    ServerSocket serverSocket = null;
    Socket socket = null;
    DataInputStream dataInputStream = null;
    DataOutputStream dataOutputStream = null;
    private String getClientMsg = "";
    private String sendClientMsg = "ISYOURTIMESET";
    LocalTime localTime = LocalTime.now();
    Timer timer = new Timer();
    TimerTask timerTask;

    public static void main(String[] args) {
        Server server = new Server(8000);
    }

    public Server(int port) {
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Server and socket started.");
            System.out.println("Client waiting... \n");
            socket = serverSocket.accept();
            dataInputStream = new DataInputStream((socket.getInputStream()));
            dataOutputStream = new DataOutputStream(socket.getOutputStream());

            //If there was more data, we would use BufferedStream for efficient I/O operation.
            //dataInputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            timerTask = new TimerTask() {
                @Override
                public void run() {
                    try {
                        dataOutputStream.writeUTF(sendClientMsg);
                        System.out.println("Send message " + sendClientMsg + "\n");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            };
            timer.schedule(timerTask, 0, 10000);

            while (true) {
                try {
                    getClientMsg = dataInputStream.readUTF();
                    if (getClientMsg.equals("DATETIME")) {
                        System.out.println("Receipt Client Message: " + getClientMsg);
                        dataOutputStream.writeUTF(localTime.toString());
                        System.out.println("Send local time: " + localTime);
                    } else if (getClientMsg.equals("OK")) {
                        System.out.println("Receipt: " + getClientMsg + "\n");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
