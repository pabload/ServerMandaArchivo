
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
        DataInputStream in = null;
        FileInputStream fis = null;
        BufferedInputStream bis = null;
        OutputStream os = null;
        BufferedReader lector = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter escritor = new PrintWriter(socket.getOutputStream(), true);
        String entrada;
        String salida;
        String ruta = lector.readLine();
        System.out.println("ruta de tu carpeta servidor");
        //String lector.readLine()
        File direcion = new File(ruta);
        if (direcion.exists()) {
            System.out.println("existe");

            do {
                entrada = lector.readLine();
                File archivo = new File(entrada);
                System.out.println(archivo);
                //escritor.println("xd");
                if (archivo.exists()) {
                    String cadena;
                    System.out.println(entrada);
                    int longitud = (int) new File(entrada).length();
                    escritor.println(longitud);
                    byte[] mybytearray = new byte[(int) archivo.length()];
                    fis = new FileInputStream(archivo);
                    bis = new BufferedInputStream(fis);
                    bis.read(mybytearray, 0, mybytearray.length);
                    os = socket.getOutputStream();
                    System.out.println("enviando " + entrada + "(" + mybytearray.length + " bytes)");
                    //escritor.print(longitud);
                    os.write(mybytearray, 0, mybytearray.length);
                    os.flush();
                    System.out.println("ya.");
                } else {
                    escritor.println("non");
                }
                if (entrada.equalsIgnoreCase("fin")) {
                    System.out.println("me voy");
                    escritor.println("fin");
                    socket.close();
                    sockeServer.close();
                    System.exit(0);
                }

            } while (!entrada.equalsIgnoreCase("fin"));
        } else {
            System.out.println("no existe");
        }
    }

}
