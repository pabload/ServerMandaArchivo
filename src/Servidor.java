
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {

    private String puerto;
    private ServerSocket serverSocket;
    private Socket socket;

    public Servidor(String puerto) {
        this.puerto = puerto;
        this.serverSocket = null;
        this.socket = null;
    }

    public void crearServidor() {
        try {
            serverSocket = new ServerSocket(Integer.parseInt(puerto));
            ConfigurarServidor();

        } catch (IOException ex) {
            System.out.println("Error al crear socket servidor " + ex);
        }
    }

    public void ConfigurarServidor() {
        String entrada = null;
        do {
            BufferedReader lector = null;
            PrintWriter escritor = null;
            try {
                socket = serverSocket.accept();
                lector = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                escritor = new PrintWriter(socket.getOutputStream(), true);
            } catch (IOException ex) {
                System.out.println("Error al crear socket " + ex);
            }
            FileInputStream fis = null;
            BufferedInputStream bis = null;
            OutputStream os = null;
            try {
                entrada = lector.readLine();
            } catch (IOException ex) {
                System.out.println("Error al leer ruta del archivo" + ex);
            }
            System.out.println(entrada);
            if (entrada != null) {
                File archivo = new File(entrada);
                if (archivo.exists()) {
                    try {
                        long longitud = (long) new File(entrada).length();
                        escritor.println(longitud);
                        fis = new FileInputStream(archivo);
                        bis = new BufferedInputStream(fis);
                        os = socket.getOutputStream();
                        long count;
                        long c = 0;
                        byte[] buffer = null;
                        if (longitud > 100000000) {
                            buffer = new byte[100000000];
                        } else {
                            if (longitud < 10) {
                                buffer = new byte[(int) longitud];
                            } else {
                                if (longitud <= 100000000) {
                                    buffer = new byte[(int) longitud / 10];
                                }
                            }
                        }
                        while ((count = bis.read(buffer)) > 0) {
                            c = c + count;
                            os.write(buffer, 0, (int) count);
                            System.out.print("Se mando: " + c + " de " + longitud + " \r");
                        }
                        os.close();
                        os.flush();
                    } catch (IOException ex) {
                        System.out.println("Error al mandar archivo" + ex);
                    }
                } else {
                    escritor.println("non");
                }
                if (entrada.equalsIgnoreCase("fin")) {
                    try {
                        System.out.println("me voy");
                        escritor.println("fin");
                        socket.close();
                        serverSocket.close();
                        System.exit(0);
                    } catch (IOException ex) {
                        System.out.println("Erro al cerrar sockets" + ex);
                    }
                }
            }

        } while (!entrada.equalsIgnoreCase("fin"));
    }
}
