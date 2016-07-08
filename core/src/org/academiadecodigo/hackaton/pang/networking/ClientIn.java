package org.academiadecodigo.hackaton.pang.networking;

import org.academiadecodigo.hackaton.pang.PangGame;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * Created by codecadet on 08/07/16.
 */
public class ClientIn implements Runnable {

    private float[] position;
    private boolean isShooting;
    private DatagramSocket datagramSocket;

    public ClientIn(DatagramSocket datagramSocket) {
        position = new float[2];
        this.datagramSocket = datagramSocket;
    }

    @Override
    public void run() {

        try {

            while (true) {

                byte[] message = new byte[100];
                DatagramPacket datagramPacket = new DatagramPacket(message, message.length);

                datagramSocket.receive(datagramPacket);

                String[] data = new String(datagramPacket.getData()).split(":");

                position[0] = Float.parseFloat(data[0]);
                position[1] = PangGame.BOUNDARY_THICKNESS + PangGame.PLAYER_HEIGHT / 2;


                //isShooting = Integer.parseInt(data[2]) == 1 ? true: false;
               /* System.out.println(position[0]);
                System.out.println(position[1]);*/

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public float[] getPosition() {
        return position;
    }

    public boolean isShooting() {
        return isShooting;
    }
}
