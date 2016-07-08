package org.academiadecodigo.hackaton.pang.networking;

import org.academiadecodigo.hackaton.pang.screens.PlayScreen;
import org.academiadecodigo.hackaton.pang.sprites.Player;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by codecadet on 08/07/16.
 */
public class ClientOut implements Runnable {

    private InetAddress inetAddress;
    private int portServer;
    private DatagramSocket clientSocket = null;
    private Player player;

    public ClientOut(DatagramSocket clientSocket, Player player) throws UnknownHostException {
        this.clientSocket = clientSocket;
        inetAddress = InetAddress.getByName("127.0.0.1");
        portServer = 8080;
        this.player = player;
    }

    @Override
    public void run() {
        try {

            while (!player.isDead()) {

                byte[] bytes = new String(player.getBody().getPosition().x + ":" + player.getBody().getPosition().x).getBytes();
                DatagramPacket datagramPacket = new DatagramPacket(bytes, bytes.length, inetAddress, portServer);
                clientSocket.send(datagramPacket);

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
