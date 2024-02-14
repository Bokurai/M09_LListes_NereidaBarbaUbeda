import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ThreadServidorLlista implements Runnable{

     private Socket clientSocket = null;
    private InputStream inputStream = null;
    private OutputStream outputStream = null;
    private Llista llista;
    private boolean acabat;
    Logger logger;

    public ThreadServidorLlista(Socket clientSocket, Llista l)throws IOException{
        logger = Logger.getLogger(ThreadServidorLlista.class.getName());
        this.clientSocket = clientSocket;
        llista = l;
        inputStream = clientSocket.getInputStream();
        outputStream = clientSocket.getOutputStream();
        logger.info(String.format("canals i/o creats amb un nou jugador %s%n",clientSocket.getInetAddress()));
    }

    @Override
    public void run() {

        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());

            llista = (Llista) objectInputStream.readObject();

            System.out.println("Nom del client" + llista.getNom());
            System.out.println("Números rebuts" + llista.getNumberList());

            List<Integer> llistaOrdenada = new ArrayList<>(new HashSet<>(llista.getNumberList()));
            Collections.sort(llistaOrdenada);

            llista.setNumberList(llistaOrdenada);
            objectOutputStream.writeObject(llista);
            System.out.println("Números ordenats: " + llista.getNumberList());

            objectInputStream.close();
            objectOutputStream.close();
            clientSocket.close();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
