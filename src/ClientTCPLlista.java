import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static jdk.internal.net.http.common.Utils.close;

public class ClientTCPLlista extends Thread {

    String hostname;
    int port;
    boolean continueConnected;
    Llista llista;
    Socket socket;
    InputStream input;
    OutputStream output;

    Scanner sc = new Scanner(System.in);

    public ClientTCPLlista(String hostname, int port){
       try{
           socket = new Socket(InetAddress.getByName(hostname), port);
           input = socket.getInputStream();
           output = socket.getOutputStream();
       }catch (IOException e){
           e.printStackTrace();
       }
    }

    public void run(){
        String serverData;
        String request;


        while (continueConnected){

            try {
                llista = getDataFromClient();
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(output);
                objectOutputStream.writeObject(llista);

                output.flush();
            }catch (IOException e){
                e.getMessage();
            }
        }
        close(socket);


    }

    public Llista getDataFromClient(){
        List<Integer> num = new ArrayList<>();
        String nom;

        System.out.println("Introdueix el teu nom");
        nom = sc.nextLine();

        System.out.println("Introdueix els números que vulguis sense ordre:");
        while (sc.hasNextInt()){
            int nextNum = sc.nextInt();
            if (nextNum == 0) {
                break;
            }
            num.add(nextNum);
            System.out.println(num);
        }

        return new Llista(nom, num);
    }



    private String getRequest(String serverData) {
        return "hola";
    }
}
