package org.academiadecodigo.hackaton.pang.networking;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

/**
 * Created by codecadet on 08/07/16.
 */
public class GameServer implements Runnable {

    private DatagramSocket serverSocket;


    @Override
    /**
     * @see Runnable#run()
     */
    public void run() {

        try {

            serverSocket = new DatagramSocket(8080);
            byte[] message = new byte[5];

            while (true) {
//                DatagramPacket receivePacket = new DatagramPacket(data, data.length);
//
//               serverSocket.receive(message);

            }

        } catch (SocketException e) {
            e.printStackTrace();
        }


    }

    private class ClientThread implements Runnable {

        @Override
        /**
         * @see Runnable#run()
         */
        public void run() {

        }
    }
}
