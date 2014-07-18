/*
 * David Lilue     --- 09-10444
 * Veronica Liñayo --- 08-10615
 * 
 * Grupo 33
 */
import java.rmi.RemoteException;
import java.rmi.registry.*;
import java.rmi.Naming;
import gnu.getopt.Getopt;
import java.io.*;
/**
 * @author      David Lilue <dvdalilue@gmail.com> --- 09-10444
 *		Verónica Liñayo <vlinayo@gmail.com> --- 08-10615
 * @version     1.0          
 * @since       2014-01-07
 */
public class a_rmifs {
    /**
     * Metodo main de la clase que efectua la acciones del servidor
     * de autenticacion.
     *
     *@param args arreglo de strings, de los argumentos de entrada
     * cuando se ejecuta el main
     */
    public static void main(String args[]) throws Exception {

        int c;

	/**
         * Función Getopt de la libreria gnu.getopt.Getopt, basada en la función Getopt de C
	 * la cual permite el procesamiento y manejo de los datos de entrada por consola.
	 *
	 *@param args arreglo de strings, de los argumentos de entrada
	 * cuando se ejecuta el main
	 */
        Getopt g = new Getopt("rmifs", args, "f:p:h?");
        g.setOpterr(true);  //Activa el manejador de errores de la función Getopt.
        
        String usuarios = null;
        int puerto = 0;
        while ((c = g.getopt()) != -1)
            {
                switch(c)
                    {
                    case 'p':
                        try {
                            puerto = Integer.parseInt(g.getOptarg());
                        } catch (Exception e) {
                            System.out.println("Error Puerto: Debe espicificar un entero");
                            System.exit(1);
                        }
                        break;
                    case 'f':
                        usuarios = g.getOptarg();
                        break;
                    case 'h':
                        System.out.print("Sintaxis correcta: " +
                                         "./java a_rmifs -f <usuario> -p <puerto> [-h <ayuda>] \n \n " +
                                         "Detalles de las opciones:\n " +
                                         "-h             : Solicita ayuda.\n " +
                                         "-f <usuarios>  : Nombre del archivo de los usuarios y claves.\n " +
                                         "-p <puerto>    : puerto local de la aplicacion.\n ");
                        System.exit(0);
                        break;
                    case '?': //El manejador se encarga de este caso(Insertar un valor inválido).
                        break; 
                    default:
                        System.out.print("getopt() returned " + c + "\n");
                    }
            }

  // -------------------------------------------------------CONDICIONES DE ENTRADA ----------------------------------------------------------//
    
     
        if(puerto==0 || usuarios==null){
            System.out.println("Es obligatorio especificar todos los argumentos, para mayor informacion solicite ayuda con la opcion -h.\n");
            System.exit(1);
        }
 
  // -------------------------------------------------------- FIN CONDICIONES ---------------------------------------------------------------//

        System.out.println("RMI server iniciando"); 
        try {
            LocateRegistry.createRegistry(puerto); 
            System.out.println("java RMI registry creado.");
        } catch (RemoteException e) {
            System.out.println("java RMI registry ya existe: Especifique otro puerto");
            System.exit(1);
        }
        //Instancia RmiAuthenImpl
        RmiAuthenImpl obj = new RmiAuthenImpl(usuarios);
 
        // Bind este objeto a la instancia "RmiAuthenImpl"
        Naming.rebind("rmi://localhost:"+puerto+"/RmiAuthentication", obj);
        while (true) {
            System.out.print("[Servidor_Authen Rmi:~]$ ");

            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String cmd = null;
            try {
                cmd = br.readLine(); //Recepcion de comandos
                if (cmd.equals("sal")) {
                    System.exit(0);
                }
            } catch (IOException ioe) {
                System.out.println("IO error leyendo el comando!");
                System.exit(1);
            }
        }
    }
}
