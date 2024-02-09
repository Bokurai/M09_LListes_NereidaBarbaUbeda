import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServidorTCPLlista {
        private Scanner sc = new Scanner(System.in);
        static final int PORT = 9090;
        private boolean end= false;

        public void listen(){
                ServerSocket serverSocket= null;
                Socket clientSocket= null;

                try{
                   clientSocket = serverSocket.accept();
                   while (!end) {
                           System.out.println("Client " + clientSocket.getInetAddress() + "connectat");
                           processClientRequest(clientSocket);
                           closeClient(clientSocket);

                           try {
                                   Thread.sleep(10);
                           } catch (InterruptedException ex) {
                                   ex.getMessage();
                           }
                   }
                   if(serverSocket!=null && !serverSocket.isClosed()){
                           serverSocket.close();
                   }
                }catch (IOException ex){
                        Logger.getLogger(ServidorTCPLlista.class.getName()).log(Level.SEVERE, null, ex);
                }
        }

        private void processClientRequest(Socket clientSocket) {
                boolean farewellMessage= false;
                String clientMessage="";
                BufferedReader in= null;
                PrintStream out= null;

                try {

                        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                        out = new PrintStream(clientSocket.getOutputStream());
                        System.out.println("E/S channels established");
                        do {
                                String dataToSend = processData(clientMessage);
                                out.println(dataToSend);
                                out.flush();
                                clientMessage= in.readLine();
                                farewellMessage = isFarewellMessage(clientMessage);

                        } while ((clientMessage) != null && !farewellMessage);
                }catch (IOException ex){
                        Logger.getLogger(ServidorTCPLlista.class.getName()).log(Level.SEVERE, null, ex);
                }

        }

        private boolean isFarewellMessage(String message) {
                if(message.equals("bye")) return true;
                else return false;
        }

        private String processData(String data) {
                System.out.println("Clientt$" + data);
                System.out.println("$ ");
                return sc.nextLine();
        }

        public void closeClient(Socket clientSocket) {
                try{
                        if(clientSocket!=null && !clientSocket.isClosed()){
                                if(!clientSocket.isInputShutdown()){
                                        clientSocket.shutdownInput();
                                }
                                if(!clientSocket.isOutputShutdown()){
                                        clientSocket.shutdownOutput();
                                }

                        clientSocket.close();
                        }

                }catch (IOException ex){
                        Logger.getLogger(ServidorTCPLlista.class.getName()).log(Level.SEVERE, null, ex);
                }
        }

        public static void main(String[] args) {
               ServidorTCPLlista servidorTCPLlista = new ServidorTCPLlista();
               servidorTCPLlista.listen();
        }
}
