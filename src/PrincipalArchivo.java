
public class PrincipalArchivo {

    public static void main(String[] args) {
        try {
            Servidor servidor = new Servidor(args[0]);
            servidor.crearServidor();
        } catch (Exception e) {
            System.out.println("Error al iniciar el servidor" );
        }

    }
}
