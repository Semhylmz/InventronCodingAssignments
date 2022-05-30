package com.socketprogramming;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.time.LocalTime;
import java.util.Timer;
import java.util.TimerTask;

public class Client {
    Socket socket = null;
    DataOutputStream dataOutputStream = null;
    DataInputStream dataInputStream;
    private String sendServerMsg = "DATETIME";
    private String getServerMsg = "";
    Timer timer = new Timer();
    TimerTask timerTask;
    LocalTime localTime = LocalTime.now();

    public static void main(String[] args) {
        Client client = new Client("localhost", 8000);
    }

    public Client(String IPAddr, int port) {
        try {
            socket = new Socket(IPAddr, port);
            System.out.println("Connected! \n");
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataInputStream = new DataInputStream(socket.getInputStream());

            timerTask = new TimerTask() {
                @Override
                public void run() {
                    try {
                        dataOutputStream.writeUTF(sendServerMsg);
                        System.out.println("Send message: " + sendServerMsg + "\n");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            };
            timer.schedule(timerTask, 0, 20000);
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (true) {
            try {
                getServerMsg = dataInputStream.readUTF();
                //System.out.println("Receipt Local Time: " + getServerMsg);

                if (getServerMsg.equals("ISYOURTIMESET")) {
                    dataOutputStream.writeUTF("OK");
                    System.out.println("Receipt ISYOURTIMESET. " +
                            "Send: OK" + "\n");
                } else {
                    System.out.println("Receipt Server Time: " + getServerMsg);
                    //System.out.println("Compare Local Time: " + localTime.compareTo(LocalTime.parse(getServerMsg)) + "\n");
                    int res = localTime.compareTo(LocalTime.parse(getServerMsg));
                    switch (res) {
                        case 1:
                            System.out.println("Code 1: Client time > Server time");
                            break;
                        case -1:
                            System.out.println("Code -1: Client time > Server time");
                            break;
                        case 0:
                            System.out.println("Code 0: Client time = Server time");
                            break;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
