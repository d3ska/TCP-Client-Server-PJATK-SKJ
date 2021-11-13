package com.pjatk.com;

import java.io.IOException;
import java.net.*;
import java.util.Scanner;

public class UdpServer {

    private static Scanner scanner = new Scanner(System.in);
    public static final int PORT = 12345;

    public static void main(String[] args) throws IOException {
        DatagramSocket socket = new DatagramSocket(PORT);
        DatagramPacket packetToReceive = new DatagramPacket(new byte[1400], 1400);
        while(true) {
            if (getCommand().equalsIgnoreCase("READ")) {
                packetToReceive = readFromClient(socket);
            } else {
                writeToClient(socket, packetToReceive);
            }
        }
}

    private static void writeToClient(DatagramSocket socket, DatagramPacket packetToReceive) throws IOException {
        int port = packetToReceive.getPort();
        InetAddress localHost = InetAddress.getLocalHost();
        System.out.print("Wiadomość: ");
        String messageToSend = scanner.nextLine();
        System.out.println();
        DatagramPacket packet = new DatagramPacket(messageToSend.getBytes(), messageToSend.getBytes().length, localHost, port);
        socket.send(packet);
    }

    private static DatagramPacket readFromClient(DatagramSocket socket) throws IOException {
        DatagramPacket packetToReceive;
        packetToReceive = new DatagramPacket(new byte[1400], 1400);
        socket.receive(packetToReceive);
        System.out.println("Odebrano od: " + packetToReceive.getAddress().toString() + ":" + packetToReceive.getPort());
        String response = new String(packetToReceive.getData(), 0, packetToReceive.getLength());
        System.out.println("Dane: " + response);
        System.out.println();
//        for(int i =0; i < packetToReceive.getLength(); i++) {
//            System.out.print((packetToReceive.getData()[i]<0?256+packetToReceive.getData()[i]:packetToReceive.getData()[i]) + " ");
//        }
        return packetToReceive;
    }

    private static String getCommand() {
        System.out.println("READ/ WRITE");
        String command = scanner.nextLine();
        while(!(command.equalsIgnoreCase("READ") || command.equalsIgnoreCase("WRITE"))){
            System.out.println("Niepoprawna opcja: " + command);
            command = scanner.nextLine();
        }
        return command;
      }
    }
