import java.io.*;
import java.net.Socket;
import java.util.Objects;
import java.util.Scanner;

public class Client {

    public static final String HOST = "127.0.0.1";
    public static final int PORT = 1234;

    public static void main(String[] args) {
        start(HOST, PORT);
    }

    private static void start(String host, int port) {
        Socket socket = null;
        InputStreamReader inputStreamReader = null;
        OutputStreamWriter outputStreamWriter = null;
        BufferedReader bufferedReader = null;
        BufferedWriter bufferedWriter = null;
        boolean isRunning = true;
        try{
            socket = new Socket(host, port);

            inputStreamReader = new InputStreamReader(socket.getInputStream());
            outputStreamWriter = new OutputStreamWriter(socket.getOutputStream());

            bufferedReader = new BufferedReader(inputStreamReader);
            bufferedWriter = new BufferedWriter(outputStreamWriter);

            Scanner scanner = new Scanner(System.in);

            while(isRunning){
                String messageToSend = scanner.nextLine();
                bufferedWriter.write(messageToSend);
                bufferedWriter.newLine();
                bufferedWriter.flush();
                System.out.println("Server: " + bufferedReader.readLine());
                if(messageToSend.equalsIgnoreCase("BYE")){
                    isRunning = false;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            closeStreams(socket, inputStreamReader, outputStreamWriter, bufferedReader, bufferedWriter);
        }
    }

    private static void closeStreams(Socket socket, InputStreamReader inputStreamReader, OutputStreamWriter outputStreamWriter, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
        try {
            if (Objects.nonNull(socket)) {
                socket.close();
            }
            if (Objects.nonNull(inputStreamReader)) {
                inputStreamReader.close();
                ;
            }
            if (Objects.nonNull(outputStreamWriter)) {
                outputStreamWriter.close();
            }
            if (Objects.nonNull(bufferedReader)) {
                bufferedReader.close();
            }
            if (Objects.nonNull(bufferedWriter)) {
                bufferedWriter.close();
            }
        }catch (IOException e){
            System.out.println(e.getCause());
        }
    }
}
