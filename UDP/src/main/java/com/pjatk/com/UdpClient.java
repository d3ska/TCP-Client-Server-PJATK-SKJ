package com.pjatk.com;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

import static com.pjatk.com.UdpServer.PORT;

public class UdpClient {
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
//        byte[]doWyslania = {0x08, 0x54, 0x01, 0x00, 0x00, 0x01, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x03, 0x77, 0x77, 0x77, 0x02, 0x77, 0x70, 0x02, 0x70, 0x6c, 0x00, 0x00,  0x01, 0x00, 0x01};
        DatagramSocket socket = new DatagramSocket();
        DatagramPacket packetToReceive = new DatagramPacket(new byte[1400], 1400);
        while (true) {
            if (getCommand().equalsIgnoreCase("READ")) {
                readResponseFromServer(socket, packetToReceive);
            } else {
                writeToServer(socket);
            }
        }
    }

    private static void readResponseFromServer(DatagramSocket socket, DatagramPacket packetToReceive) throws IOException {
        socket.receive(packetToReceive);
        System.out.println("Odebrano od: " + packetToReceive.getAddress().toString() + ":" + packetToReceive.getPort());
        String response = new String(packetToReceive.getData(), 0, packetToReceive.getLength());
        System.out.println("Dane: " + response);
        System.out.println();
//        for(int i =0; i < packetToReceive.getLength(); i++) {
//            System.out.print((packetToReceive.getData()[i]<0?256+packetToReceive.getData()[i]:packetToReceive.getData()[i]) + " ");
//        }
    }

    private static void writeToServer(DatagramSocket socket) throws IOException {
        String hostname = "localhost";
        InetAddress dnsAddress = InetAddress.getByName(hostname);
        Scanner scanner = new Scanner(System.in);
        System.out.print("Wiadomość: ");
        String toSent = scanner.nextLine();
        System.out.println();

        DatagramPacket packetToSend = new DatagramPacket(
                toSent.getBytes(),
                toSent.getBytes().length,
                dnsAddress,
                PORT
        );
        socket.send(packetToSend);
    }

    private static String getCommand() {
        System.out.println("READ/ WRITE");
        String command = scanner.nextLine();
        while (!(command.equalsIgnoreCase("READ") || command.equalsIgnoreCase("WRITE"))) {
            System.out.println("Niepoprawna opcja: " + command);
            command = scanner.nextLine();
        }
        return command;
    }
}
