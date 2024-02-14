import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServidorTCPLlista {
private int port;
private Llista llista;
Logger logger;

private ServidorTCPLlista(int port){
    this.port = port;
    logger = Logger.getLogger(ServidorTCPLlista.class.toString());
}

private void listen(){
    ServerSocket serverSocket = null;
    Socket clientSocket = null;

    try{
        serverSocket = new ServerSocket(port);
        while (true) {
            clientSocket = serverSocket.accept();
            ThreadServidorLlista filServ = new ThreadServidorLlista(clientSocket, llista);
            Thread client = new Thread(filServ);
            client.start();
        }
    }catch (IOException exception){
        Logger.getLogger(ServidorTCPLlista.class.getName());
    }
}

    public static void main(String[] args) throws IOException {
        ServidorTCPLlista server = new ServidorTCPLlista(5558);
        Thread thread = new Thread(server::listen);
        thread.start();
    }
}
