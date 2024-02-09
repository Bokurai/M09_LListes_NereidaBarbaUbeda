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

        int PORT;




        public static void main(String[] args) {
               ServidorTCPLlista servidorTCPLlista = new ServidorTCPLlista(5558);
               servidorTCPLlista.listen();
        }
}
