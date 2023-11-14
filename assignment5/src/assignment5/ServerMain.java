package assignment5;

import java.io.*;
import java.net.*;
import java.sql.Array;
import java.util.*;

// Server: The server is responsible for receiving guesses from clients and dispatching responses to
// appropriate clients. The server is responsible for generating the secret code. The server should
// support multiple clients. Multiple clients will independently play a game to determine the
// server’s single code for a given round. You just need one server; call the main class of the server
// ServerMain.java. Make sure that ServerMain.java has a main() method.
public class ServerMain{
    static Socket socket;
    static ServerSocket ss;
    static String secret;
    static ArrayList<ClientHandler> clients;
    static volatile boolean done;
    public static void main(String[] args) {
        try {
            ss = new ServerSocket(6666);
            ss.setSoTimeout(1000);
            secret = SecretCodeGenerator.getInstance().getNewSecretCode();
            clients = new ArrayList<>();
            while (true) { // loop to accept incoming client request
                try{
                    socket = ss.accept();
                    //System.out.println(done);
                    System.out.println("client conencted");
                    DataInputStream inputStream = new DataInputStream(socket.getInputStream());
                    DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
                    ClientHandler client = new ClientHandler(socket, inputStream, outputStream, secret);
                    clients.add(client);
                    System.out.println(clients.size());
                    Thread t = new Thread(client);
                    t.start();
                } catch (SocketTimeoutException ste) {
                    if (done == true) {
                        System.out.println("reached");
                        for (ClientHandler a : clients) {
                            if(!a.s.isClosed()){
                                System.out.println("done sent");
                                a.dos.writeUTF("done");
                            }
                            //a.closeConnections();
                        }
                        break;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        try{
            ss.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
class ClientHandler implements Runnable
{
    Scanner scn = new Scanner(System.in);
    final DataInputStream dis;
    final DataOutputStream dos;
    final String secret;
    final Game masterMind;
    int guess = GameConfiguration.guessNumber;
    Socket s;
    // constructor
    public ClientHandler(Socket s, DataInputStream dis, DataOutputStream dos,  String secret) {
        this.dis = dis;
        this.dos = dos;
        this.s = s;
        this.secret = secret;
        masterMind = new Game(true, secret);
    }
    @Override
    public void run() {
        String received;
        try{
            dos.writeUTF(GameConfiguration.intro);
        }
        catch(IOException e){}
        while (true)
        {
            try
            {
                if (s.isClosed()) {
                    System.out.println("Socket is closed. Exiting handler.");
                    break;
                }
                // receive the string
                received = dis.readUTF();
                //System.out.println(received); // debugging purpose
                if(received.equals("Y")){ // client wants to play the game
                    dos.writeUTF(masterMind.startGame());
                } else if(received.equals("N")) { // the client does not want to play the game
                    break;
                } else if(received.equals(this.secret)){ // edge case need to fix
                    ServerMain.done = true;
                    System.out.println("Reached at Handler");
                } else{
                    String s = masterMind.checkInput(received);
                    if(s.equals("\\n Sorry, you are out of guesses. You lose, boo-hoo.\" + \"\\n\\nAre you ready for another game (Y/N): ")){
                        dos.writeUTF(s);
                        break;
                    } else{
                        dos.writeUTF(s);

                    }
                }
            } catch (IOException e) {
                if (s.isClosed()) {
                    System.out.println("client successfully disconnected");
                } else {
                    System.out.println("IOException in handler: " + e.getMessage());
                }
                break;
            }
        }
           closeConnections();
    }
    public void closeConnections() {
        try {
            if (s != null && !s.isClosed()) {
                s.close();
            }
            if (dis != null) {
                dis.close();
            }
            if (dos != null) {
                dos.close();
            }
        } catch (IOException e) {
            System.out.println("Error closing client connections: " + e.getMessage());
        }
    }
}

