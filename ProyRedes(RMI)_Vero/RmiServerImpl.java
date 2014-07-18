/*
 * David Lilue     --- 09-10444
 * Veronica Liñayo --- 08-10615
 * 
 * Grupo 33
 */

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.lang.StringBuilder;
import java.io.*;
/**
 * @author      David Lilue <dvdalilue@gmail.com> --- 09-10444
 *		Verónica Liñayo <vlinayo@gmail.com> --- 08-10615
 * @version     1.0          
 * @since       2014-01-07
 */
public class RmiServerImpl
    extends UnicastRemoteObject 
    implements RmiServer {

    /**
     * Cola de los comandos que han realizado los cliente
     *
     */
    private Queue<String> cmds;

    /**
     * Objeto que referencia a la implementacion del servidor
     * de autenticacion.
     *
     */
    private RmiAuthen aut;
    private static final long serialVersionUID = 7526472295622776147L;

    /**
     * <p>
     * Contruye la clase a partir del constructor de la clase
     * extendida UnicastRemoteObject, ademas instancia una nueva cola
     * y referencia al servidor de autenticacion.
     * <p>
     *
     * @param host DNS o IP donde esta servidor de autenticacion
     * @param port puerto en el cual se encuentra el servicio requerido
     *
     */
    public RmiServerImpl(String host, int port) throws RemoteException {
        super(0);
        this.cmds = new Queue<String>();
        try {
            this.aut = (RmiAuthen) Naming.lookup("rmi://" + host + ":" + 
                                                 port + "/RmiAuthentication");
        } catch (Exception e) {
            System.out.println("Constructor RmiServerImpl: " + e);
            System.exit(1);
        }
    }

    /**
     * Devuelve un string donde esta la informacion de los 
     * archivos en el directorio actual del servidor.
     *
     * @return el string de los archivos
     */
    public String directory() {
        File file = new File(".");
        StringBuilder aux = new StringBuilder("\n");
        File[] children = file.listFiles();
        if (children != null) {
            for (File child : children) {
                if (child.isDirectory()) {
                    aux.append("\u001B[32m " + 
                               child.getName() + 
                               "\u001B[0m" + 
                               "\n");
                } else {
                    aux.append(" " + 
                               child.getName() + 
                               "\n");
                } 
            }
        }
        return aux.toString();
    }

    /**
     * Elimina un archivo o directorio.
     * <p>
     * Toma el nombre del archivo o directorio del parametro
     * lo elimina.
     * <p>
     * @param name nombre del archivo o directorio a borrar.
     * @return string de true si lo logro, de lo contrario de false
     */
    public Boolean del(String name) {
        File aux = new File(name);
        return aux.delete();
    }

    /**
     * Descarga un archivo o directorio.
     * <p>
     * Toma el nombre del archivo o directorio del parametro
     * lo descarga.
     * <p>
     * @param name nombre del archivo o directorio a descargar.
     * @return cadena de bytes con la informacion
     */
    public byte[] down(String name) {
        try {
            File file = new File(name);
            byte buffer[] = new byte[(int)file.length()];
            BufferedInputStream input = new BufferedInputStream(new FileInputStream(name));
            input.read(buffer,0,buffer.length);
            input.close();
            return(buffer);
        } catch(Exception e){
            System.out.println("DownFile: "+e.getMessage());
            e.printStackTrace();
            return(null);
        }
    }

    /**
     * Sube un archivo o directorio.
     * <p>
     * Toma el nombre del archivo o directorio del parametro
     * lo sube(crea localmente).
     * <p>
     * @param buffer arreglo de bytes con la informacion 
     */
    public void up(byte[] buffer, String name) {
        try {
            BufferedOutputStream output = new BufferedOutputStream(new FileOutputStream(name));
            output.write(buffer,0,buffer.length);
            output.flush();
            output.close();
        } catch (Exception e) {
            System.out.println("Fallo al intentar crear un archivo: " + e);
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
        try {
            if (!usr_pass.equals(":")) {
                return this.aut.authentic(usr_pass);
            } else {
                return false;
            } 
        } catch (Exception e) {
            System.out.println("ServerAuthen: " + e);
        }
        return false;
    }
    
    /**     
     * Agrega un elemento a la cola de comandos.
     * <p>
     * Metodo ejecutado por el cliente cada vez que introduce un comando
     * se pasa a la cola de comando, agregando el elemento al principio.
     * <p>
     * @param cmd comando que escribio el cliente
     * @param name nombre del usuario que realizo el comando
     *
     */
    public void new_cmd(String cmd, String name) {
        this.cmds.add_ini(cmd, name);
    }

    /**     
     * Imprime el contenido de la cola hasta 20 maximo.
     */
    public void log() {
        this.cmds.to_s(20);
    }
}
