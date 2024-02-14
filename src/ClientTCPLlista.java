import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import static jdk.internal.net.http.common.Utils.close;

public class ClientTCPLlista extends Thread {

    private String nom;
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

    private void close(Socket socket){
        //si falla el tancament no podem fer gaire cosa, només enregistrar
        //el problema
        try {
            //tancament de tots els recursos
            if(socket!=null && !socket.isClosed()){
                if(!socket.isInputShutdown()){
                    socket.shutdownInput();
                }
                if(!socket.isOutputShutdown()){
                    socket.shutdownOutput();
                }
                socket.close();
            }
        } catch (IOException ex) {
            //enregistrem l'error amb un objecte Logger
            Logger.getLogger(ClientTCPLlista.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {
        String persona, ipServ;

        System.out.println("IP servidor: ");
        Scanner sc = new Scanner(System.in);
        ipServ = sc.next();
        System.out.println("El teu nom: ");
        persona = sc.next();

        ClientTCPLlista clientTCPLlista = new ClientTCPLlista(ipServ, 5558);
        clientTCPLlista.nom = persona;
        clientTCPLlista.start();
    }
}
