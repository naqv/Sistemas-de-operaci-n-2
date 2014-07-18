/*
 * David Lilue     --- 09-10444
 * Veronica Liñayo --- 08-10615
 * 
 * Grupo 33
 */

import java.rmi.Remote;
import java.rmi.RemoteException;
/**
 * @author      David Lilue <dvdalilue@gmail.com> --- 09-10444
 *		Verónica Liñayo <vlinayo@gmail.com> --- 08-10615
 * @version     1.0          
 * @since       2014-01-07
 */
public interface RmiServer extends Remote {

    /**
     * Devuelve un string donde esta la informacion de los 
     * archivos en el directorio actual del servidor.
     *
     * @return el string de los archivos
     */
    public String directory() throws RemoteException;

    /**
     * Elimina un archivo o directorio.
     * <p>
     * Toma el nombre del archivo o directorio del parametro
     * lo elimina.
     * <p>
     * @param name nombre del archivo o directorio a borrar.
     * @return string de true si lo logro, de lo contrario de false
     */
    public Boolean del(String name) throws RemoteException;

    /**
     * Descarga un archivo o directorio.
     * <p>
     * Toma el nombre del archivo o directorio del parametro
     * lo descarga.
     * <p>
     * @param name nombre del archivo o directorio a descargar.
     * @return cadena de bytes con la informacion
     */
    public byte[] down(String name) throws RemoteException;

    /**
     * Sube un archivo o directorio.
     * <p>
     * Toma el nombre del archivo o directorio del parametro
     * lo sube(crea localmente).
     * <p>
     * @param buffer arreglo de bytes con la informacion 
     */
    public void up(byte[] buffer, String name) throws RemoteException;

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
    public Boolean authentic(String usr_pass) throws RemoteException;

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
    public void new_cmd(String cmd, String name) throws RemoteException;

    /**     
     * Imprime el contenido de la cola hasta 20 maximo.
     */
    public void log() throws RemoteException;
}
