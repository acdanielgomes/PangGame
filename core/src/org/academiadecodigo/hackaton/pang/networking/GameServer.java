package org.academiadecodigo.hackaton.pang.networking;

import org.academiadecodigo.hackaton.pang.PangGame;
import org.academiadecodigo.hackaton.pang.sprites.Player;

import java.io.IOException;
import java.net.*;
import java.util.LinkedList;
import java.util.Map;

/**
 * Created by codecadet on 08/07/16.
 */
public class GameServer {

    public static void main(String[] args) throws UnknownHostException {

        GameServer gameServer = new GameServer();

    }

    private DatagramSocket serverSocket;
    private ClientThread player1;
    private ClientThread player2;
    private LinkedList<ClientThread> clients;


    public GameServer() throws UnknownHostException {

        clients = new LinkedList<ClientThread>();

        try {

            serverSocket = new DatagramSocket(8080);

        } catch (SocketException e) {
            e.printStackTrace();
        }

        init();

    }

    public void init() {


        try {
            player1 = new ClientThread(8081, InetAddress.getByName("localhost"));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        try {
            byte[] message = new byte[5];

            while (true) {
                DatagramPacket receivePacket = new DatagramPacket(message, message.length);
                serverSocket.receive(receivePacket);
                ClientThread client = new ClientThread(receivePacket.getPort(), receivePacket.getAddress());

                if (clients.isEmpty()) {
                    clients.add(client);

                } else if (clients.get(0).port != client.port) {
                    if (clients.size() == 1) {
                        clients.add(client);
                    }
                }

//                for (ClientThread clientTest : clients) {
//                    System.out.println("ip: "+clientTest.add+" Port is : "+clientTest.port);
//                }

                sendAll(receivePacket);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void sendAll(DatagramPacket receivePacket) {
        //for (ClientThread client : clients) {
         //   client.send(receivePacket);
       // }
        player1.send(receivePacket);
    }

    private class ClientThread {

        public int port;
        public InetAddress add;

        public ClientThread(int port, InetAddress add) {
            this.port = 8081;
            this.add = add;
        }

        private void send(DatagramPacket packet){
            try {
                DatagramPacket sendPacket = new DatagramPacket(packet.getData(), packet.getData().length, this.add, this.port);
                serverSocket.send(sendPacket);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
