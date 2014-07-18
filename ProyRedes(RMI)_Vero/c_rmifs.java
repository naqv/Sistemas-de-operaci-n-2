/*
 * David Lilue     --- 09-10444
 * Veronica Liñayo --- 08-10615
 * 
 * Grupo 33
 */

import gnu.getopt.Getopt;
import java.rmi.Naming;
import java.io.*;
/**
 * @author      David Lilue <dvdalilue@gmail.com> --- 09-10444
 *		Verónica Liñayo <vlinayo@gmail.com> --- 08-10615
 * @version     1.0          
 * @since       2014-01-07
 */
public class c_rmifs {
    /**
     * Metodo main de la clase que efectua la acciones del cliente.
     * <p>
     * Funcion que se ejecuta con el comando java c_rmifs [Opciones],
     * reconece las distintas opciones, las analisa y luego muestra
     * un terminal interno para el manejo de los comandos. Se crea
     * un objeto casteado a una interfaz definida por el cliente
     * que referencia a una instancia de una implementacion de la
     * misma. Logrando el llamado de funciones remotas.
     * <p>
     *
     *@param args arreglo de strings, de los argumentos de entrada
     * cuando se ejecuta el main
     */
    public static void main(String args[]) throws Exception {
        int c;
	/**
         * Función Getopt de la librería gnu.getopt.Getopt, basada en la función Getopt de C
	 * la cual permite el procesamiento y manejo de los datos de entrada por consola.
	 *
         */
        Getopt g = new Getopt("c_rmifs", args, "f:m:p:c:h?");
        g.setOpterr(true);  //Activa el manejador de errores de la función Getopt.

        String usuarios = null;
        int puerto = 0;
        String servidor = null;
        String comandos = null;

        while ((c = g.getopt()) != -1)
            {
                switch(c)
                    {
                    case 'f':
                        usuarios = g.getOptarg();
                        break;
                    case 'm':
                        servidor = g.getOptarg();
                        break;
                    case 'p':
                        try {
                            puerto = Integer.parseInt(g.getOptarg());
                        } catch (Exception e) {
                            System.out.println("Error Puerto: Debe espicificar un entero");
                            System.exit(1);
                        }
                        break;
                    case 'c':
                        comandos = g.getOptarg();
                        break;
                    case 'h':
                        System.out.print("Sintaxis correcta: " +
                                         "./java c_rmifs [-f usuarios] -m servidor -p puerto [-c comandos] \n \n " +
                                         "Detalles de las opciones:\n " +
                                         "-h             : Solicita ayuda.\n " +
                                         "-f <usuarios>  : Nombre del archivo de los usuarios y claves.\n " +
                                         "-m <servidor>  : Es el nombre DNS o direccion IP del computador donde corre el servidor de archivos.\n " +
                                         "-p <puerto>    : Puerto donde estara el rmiregistry del servidor.\n " +
                                         "-c <comandos>  : Direccion relativa o absoluta de un archivo, que contendra en cada linea uno de\n" +
                                         "                 los comandos que el cliente puede ejecutar por linea de comandos. El cliente ejecutara\n" +
                                         "                 primero los comandos de este archivo y al terminar comenzar a aceptar comandos por\n" +
                                         "                 teclado, a menos que uno de los comandos del archivo sea el comando sal. \n");
                        System.exit(0);
                        break;
                    case '?': //El manejador se encarga de este caso(Insertar un valor inválido).
                        break; 
                    default:
                        System.out.print("getopt() returned " + c + "\n");
                    }
            }

  // -------------------------------------------------------CONDICIONES DE ENTRADA ----------------------------------------------------------//

        if(puerto==0 || servidor==null){
            System.out.println("Es obligatorio especificar un puerto y un servidor, para mayor informacion solicite ayuda con la opcion -h.\n");
            System.exit(0);
        }
 
  // -------------------------------------------------------- FIN CONDICIONES ---------------------------------------------------------------//

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String atr[], name, key, cmd = null;
            
            /*
             * Si no se especifica un archivo de usuario
             * con el nombre y clave de la forma nombre:clave
             * los pedira por pantalla.
             */
            if (usuarios == null) {
                System.out.print("Nombre: ");
                name = br.readLine().trim();
                System.out.print("Clave: ");
                key = name + ":" + br.readLine().trim();;
            } else {
                key = FileManager.get_first_line(usuarios);
                atr = key.split(":");
                name = atr[0];
            }

            //Creacion del objeto que referencia al servido
            RmiServer obj = (RmiServer)Naming.lookup("rmi://"+servidor+":"+puerto+"/RmiService");
            if (!(obj.authentic(key))) {
                System.out.println("***Usuario o clave invalido!!!");
                System.exit(0);
            }

            //Contruccion del cliente para comandos locales
            RmiClient cli = new RmiClient();

            /*
             * Si se especifica un archivo de comandos, se creara un file manager
             * para la lectura por linea del mismo. Y en cada linea se manda el comando
             * al servidor
             */
            if (comandos != null) {
                FileManager stream;
                stream = new FileManager(comandos);
                while ((cmd = stream.get_line()) != null) {
                    obj.new_cmd(cmd,name);
                    atr = cmd.split(" ");
                    System.out.print("\u001B[36m"+"Comando desde archivo:\u001B[31m "+atr[0]+"\u001B[0m \n");
                    cmd_manager(cmd, atr, cli, obj);
                }
            }
            
            while (true) {
                System.out.print("\u001B[34m["+name+"@"+"Cliente Rmi:~]$ \u001B[0m");
                cmd = br.readLine();
                if (!cmd.isEmpty()) {
                    obj.new_cmd(cmd,name);
                    atr = cmd.split(" ");
                    cmd_manager(cmd, atr, cli, obj);
                }
            }
        } catch (Exception e) {
            System.out.println("Error RmiClient");
            System.exit(1);
        }
    }

    /**
     * Metodo cmd_manager que maneja los comandos permitidos por el cliente.
     *
     * @param cmd comando a realizar
     * @param atr arreglo de strings que permite manejar los comandos ingresados
     * @param RmiClient rmi del cliente
     * @param RmiServer rmi con la referencia al servidor.
     * 
     */
    public static void cmd_manager(String cmd, String[] atr, RmiClient cli, RmiServer obj) {
        byte[] buffer;

        if (cmd.equals("sal")) {
            System.exit(0);
        }
        else if (cmd.equals("info")) {
            RmiClient.info();
        }
        else if (cmd.equals("lls")) {
            cli.scan_directory();
            cli.to_s();
        }
        else if (cmd.equals("rls")) {
            try {
                System.out.println(obj.directory());                        
            } catch (Exception e) {
                System.out.println("Error rls:" + e);
            }
        }
        else if (atr[0].equals("sub")) {
            try {
                if ((buffer = cli.get_byte_b(atr[1])) != null) {
                    obj.up(buffer, atr[1]);
                } else {
                    System.out.println("El archivo que desea subir no existe en su directorio local.");
                }
            } catch (Exception e) {
                System.out.println("Falto identificar el archivo. Ej. sub test.txt");
            }
        }
        else if (atr[0].equals("baj")) {
            try {
                buffer = obj.down(atr[1]);
                cli.create_file(buffer,atr[1]);
            } catch (Exception e) {
                System.out.println("Falto identificar el archivo. Ej. baj test.txt");
            }
        }
        else if (atr[0].equals("bor")) {
            try {
                if (obj.del(atr[1])) {
                    System.out.println("Se elimino el archivo");
                } else {
                    System.out.println("No se elimino el archivo");
                }
            } catch (Exception e) {
                System.out.println("Falto identificar el archivo. Ej. bor test.txt");
            }
        } else {
            System.out.println("-- Rmi Client: " +atr[0]+ ": comando invalido");
        }
    }
}
