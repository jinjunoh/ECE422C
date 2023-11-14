package assignment5;
import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ClientMain {
    private static final String HOST = "localhost";
    private static final int PORT = 6666;
    private static volatile boolean isClosed = false; // Shared flag

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket(HOST, PORT);
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());
        DataInputStream in = new DataInputStream(socket.getInputStream());
        Scanner sc = new Scanner(System.in);
        Thread sendThread = new Thread(new Runnable()
        {
            @Override
            public void run() {
                while (!isClosed) {
                    // read the message to deliver.
                    try {
                        if (System.in.available() > 0) {
                            String msg = sc.nextLine();
                            try {
                                if (msg.equals("N")) {
                                    isClosed = true;
                                    socket.close();
                                    break;
                                }
                                // write on the output stream
                                out.writeUTF(msg);
                            } catch (IOException e) {
                                System.out.println("Game ended");
                                break;
                            }
                        }
                        if (isClosed) {
                            System.out.println("We Have A Winner!!!");
                            break;
                        }
                    } catch(Exception e){
                        System.out.println("not available");
                    }
                }
            }
        });
        Thread readThread = new Thread(new Runnable()
        {
            @Override
            public void run() {

                while (!isClosed) {
                    try {
                        // read the message sent to this client
                        String serverRes = in.readUTF();
                        if(serverRes.equals("done")){
                            isClosed= true;
                            socket.close();
                            System.out.println("server is done closing client");
                           break;
                        }
                        System.out.println(serverRes);
                    } catch (IOException e) {
                        System.out.println("Game ended");
                        break;
                    }
                }
            }
        });
        sendThread.start();
        readThread.start();
        try {
            sendThread.join();
            readThread.join();
        } catch (InterruptedException e) {
            System.out.println("Threads interrupted: " + e.getMessage());
        }
        //System.out.println("Connected to Mastermind Server. Start guessing!");
//        try {
//            while (true) {
//                try{
//                    String serverRes = in.readUTF();
//                    if(serverRes.equals("done")){
//                        break;
//                    } else{
//                        System.out.println(serverRes);
//                        userInput = sc.nextLine();
//                    }
//                    out.writeUTF(userInput);
//                } catch(EOFException e){
//                    System.out.println("Game ended");
//                    break;
//                }
//
//            }
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//        try{
//            out.close();
//            in.close();
//            sc.close();
//            socket.close();
//        } catch (Exception e) {
//            System.out.println(e);
//        }
    }
}