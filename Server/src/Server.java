import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main(String[] args) throws IOException {
        int port = 1234;
        start(port);
    }



    private static void start(final int port) throws IOException {
        ServerSocket serverSocket  = new ServerSocket(port);
        boolean innerRunning = true;

        while(true){
            try{
                Socket socket = serverSocket.accept();

                InputStreamReader inputStreamReader = new InputStreamReader(socket.getInputStream());
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(socket.getOutputStream());

                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
                int sum = 0;
                int counter = 0;
                while(innerRunning){
                    counter ++;
                    String messageFromClient = bufferedReader.readLine();
                    System.out.println("Client: " + messageFromClient);
                    sum += Integer.parseInt(messageFromClient);
                    if(counter >= 3){
                        bufferedWriter.write(sum+"");
                    }
                    bufferedWriter.write("Message received.");
                    bufferedWriter.newLine();
                    bufferedWriter.flush();

                    if(messageFromClient.equalsIgnoreCase("BYE")){
                        break;
                    }
                }
                closeAll(socket, inputStreamReader, outputStreamWriter, bufferedReader, bufferedWriter);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void closeAll(Socket socket, InputStreamReader inputStreamReader, OutputStreamWriter outputStreamWriter, BufferedReader bufferedReader, BufferedWriter bufferedWriter) throws IOException {
        socket.close();
        inputStreamReader.close();
        outputStreamWriter.close();
        bufferedReader.close();
        bufferedWriter.close();
    }
}
