
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;


public class PrincipalArchivo {

    
    public static void main(String[] args) throws IOException {
        ServerSocket sockeServer = new ServerSocket(2500);
        Socket socket = sockeServer.accept();
        DataOutputStream out = null;
        DataInputStream in = null;
        FileInputStream fis = null;
        BufferedInputStream bis = null;
        OutputStream os = null;
        BufferedReader lector = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String entrada;
        Scanner scanner =new  Scanner(System.in);
        String salida;
        PrintWriter escritor= new PrintWriter(socket.getOutputStream(),true);
        do {
            entrada = lector.readLine();
            String cadena;
            System.out.println(entrada);
            if (entrada.equalsIgnoreCase("fin")) {
                System.out.println("me voy");
                socket.close();
                sockeServer.close();
                System.exit(0);
            }
              
                File myFile = new File(entrada);
                byte[] mybytearray = new byte[(int) myFile.length()];
                fis = new FileInputStream(myFile);
                bis = new BufferedInputStream(fis);
                bis.read(mybytearray, 0, mybytearray.length);
                os = socket.getOutputStream();
                System.out.println("Sending " + entrada + "(" + mybytearray.length + " bytes)");
                os.write(mybytearray, 0, mybytearray.length);
                os.flush();
                System.out.println("Done.");

            
           
        } while (!entrada.equalsIgnoreCase("fin"));
    
   }
}
   
        

