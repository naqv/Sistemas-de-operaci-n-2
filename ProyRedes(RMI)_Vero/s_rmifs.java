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
public class s_rmifs {
    /**
     * Metodo main de la clase que efectua la acciones del servidor
     * de archivos asi como enlazar con el servidor de autenticacion
     * al construir el objeto RmiServerImpl. Para luego recibir
     * comandos por pantalla.
     * <p>
     * Funcion que se ejecuta con java s_rmifs [Opciones], las analisa 
     * y dependiendo dara un mensaje de error o montara el servidor.
     * Construye un objeto que implemento una interfaz definida por
     * el cliente. Para que otra aplicacion pueda acceder a los servicios
     * se enlaza con el localhost y un puerto que se especifico en las
     * opciones.
     * Internamente el objeto RmiServerImpl, posee una referencia a otro
     * servidor que en este caso es de autenticacion.
     * <p>
     *
     *@param args arreglo de strings, de los argumentos de entrada
     * cuando se ejecuta el main
     */
    public static void main(String args[]) throws Exception {

        int c;  
	/**
         * Funcion Getopt de la libreria gnu.getopt.Getopt, basada en la funcion Getopt de C
	 * la cual permite el procesamiento y manejo de los datos de entrada por consola.
	 *
         */
        Getopt g = new Getopt("s_rmifs", args, "l:h:r:a?");
        g.setOpterr(true); //Activa el manejador de errores de la función Getopt.

        String host = null;
        int plocal = 0;
        int puerto = 0;
 
        while ((c = g.getopt()) != -1)
            {
                switch(c)
                    {
                    case 'l':
                        try {
                            plocal = Integer.parseInt(g.getOptarg());
                        } catch (Exception e) {
                            System.out.println("Error Puerto Local: Debe espicificar un entero");
                            System.exit(1);
                        }
                    case 'h':
                        host = g.getOptarg();
                        break;
                    case 'r':
                        try {
                            puerto = Integer.parseInt(g.getOptarg());
                        } catch (Exception e) {
                            System.out.println("Error Puerto: Debe espicificar un entero");
                            System.exit(1);
                        }
                        break;
                    case 'a':
                        System.out.print("Sintaxis correcta: " +
                                         "./java s_rmifs -l <puertolocal> -h <host> -r <puerto> \n\n" + 
                                         "Detalles de las opciones:\n" +
                                         "-a           : Solicita ayuda.\n" +
                                         "-l <puerto>  : El puerto local que usara el rmiregistry que tendra informacion de\n" +
                                         "               los objetos remotos publicados por el servidor de archivos.\n" +
                                         "-h <host>    : Es el nombre DNS o direccion IP del computador donde corre el servidor de autenticacion.\n" +
                                         "-r <puerto>  : Este sera el puerto que usará el rmiregistry que tendra informacion\n" +
                                         "               de los objetos remotos publicados por el servidor de autenticacion.\n");
                        System.exit(0);
                        break;
                    case '?': //El manejador se encarga de este caso(Insertar un valor inválido).
                        break;
                    default:
                        System.out.print("getopt() returned " + c + "\n");
                    }
            }

  // -------------------------------------------------------CONDICIONES DE ENTRADA ----------------------------------------------------------//
     
        if(host==null || puerto==0 || plocal==0){
            System.out.println("Es obligatorio especificar todos los argumentos, para mayor informacion solicite ayuda con la opcion -a.\n");
            System.exit(0);
        }

  // -------------------------------------------------------- FIN CONDICIONES ---------------------------------------------------------------//

        try {
            System.out.println("RMI server iniciando"); 
            try {
                LocateRegistry.createRegistry(plocal); 
                System.out.println("java RMI registry creado.");
            } catch (RemoteException e) {
                System.out.println("java RMI registry ya existe: Especifique otro puerto");
                System.exit(1);
            }
             //Instancia RmiServerImpl
            RmiServerImpl obj = new RmiServerImpl(host,puerto);
 
            // Bind este objeto a la instancia "RmiServerImpl"
            Naming.rebind("rmi://localhost:"+plocal+"/RmiService", obj);
            while (true) {
                System.out.print("[Servidor_Archivos Rmi:~]$ ");
                
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                String cmd = null;
                try {
                    /*
                     * Reconocimiento de comandos
                    */
                    cmd = br.readLine();
                    if (cmd.equals("sal")) {
                        System.exit(0);
                    } else if (cmd.equals("log")) {
                        obj.log();
                    }  else {
                        System.out.println("-- Rmi Server: " +cmd+ ": comando invalido");
                    }
                } catch (IOException ioe) {
                    System.out.println("IO error leyendo el comando!");
                    System.exit(1);
                }
            }
        } catch (Exception e) {
            System.out.println("RmiServer exception: " + e);
        }
    }
}
