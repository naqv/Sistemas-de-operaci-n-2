/*
 * David Lilue     --- 09-10444
 * Veronica Liñayo --- 08-10615
 * 
 * Grupo 33
 */

import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;
import java.util.*;
import java.io.*;
/**
 * @author      David Lilue <dvdalilue@gmail.com> --- 09-10444
 *		Verónica Liñayo <vlinayo@gmail.com> --- 08-10615
 * @version     1.0          
 * @since       2014-01-07
 */
public class RmiAuthenImpl
    extends UnicastRemoteObject 
    implements RmiAuthen {

    /**
     * Coleccion de comandos de los clientes.
     */
    private Collection<String> permisos;
    private static final long serialVersionUID = 7526472295622776147L;

    /**
     * <p>
     * Contruye la clase a partir del constructor de la clase
     * extendida UnicastRemoteObject, ademas inicializa la coleccion
     * con toda la informacion del archivo que se busca por el
     * parametro pasado al constructor. En una lista de Strings
     * <p>
     *
     * @param arch archivo con los usuarios y claves permitidos
     */
    public RmiAuthenImpl(String arch) throws RemoteException {
        super(0);
        this.permisos = new ArrayList<String>();
        String aux = "";
        try {
            BufferedReader in = new BufferedReader(new FileReader(new File(arch)));;
            while ((aux = in.readLine()) != null) {
                this.permisos.add(aux);
            }
        } catch (Exception e) {
            System.out.println("Problemas leyendo el archivo: " + e);
            System.exit(1);
        }
    }

    /**     
     * Verifica si la combinacion de nombre y clave es parte de los usuarios.
     * <p>
     * La verficcacion se realiza gracias a un servidor de autenticacion
     * done estan todos los usuarios con su clave. Que poseen permiso de entrar
     * al servidor de archivos.
     * <p>
     * @param usr nombre del usuario a verificar.
     * @param pass clave que supuestamente tiene asignado el usuario.
     *
     * @return retorna true si existe la combinacion, sino false
     */
    public Boolean authentic(String usr_pass) {
        if (this.permisos.contains(usr_pass)) {
            return true;
        }
        return false;
    }
}
