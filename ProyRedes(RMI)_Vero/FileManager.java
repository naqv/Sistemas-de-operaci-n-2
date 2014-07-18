/*
 * David Lilue     --- 09-10444
 * Veronica Liñayo --- 08-10615
 * 
 * Grupo 33
 */

import java.io.*;
/**
 * @author      David Lilue <dvdalilue@gmail.com> --- 09-10444
 *		Verónica Liñayo <vlinayo@gmail.com> --- 08-10615
 * @version     1.0          
 * @since       2014-01-07
 */


public class FileManager  {

    private BufferedReader stream;
    /**
     * Método FileManager de la clase crea el Buffer para manejar la lectura de un archivo.
     * 
     *@param file -- el archivo que se desea leer.
     */
    public FileManager(String file) {
        try{
            this.stream = new BufferedReader(new FileReader(new File(file)));
        } catch (FileNotFoundException e){ //Atrapa las excepciones
            System.out.println("No se pudo abrir el archivo: " + e.getMessage());
        }

    }

    /**
     * Método String get_line de la clase realiza la lectura de un archivo linea por linea.
     * 
     */
    public String get_line() {
        String linea = null;        
        try {
            while ((linea = this.stream.readLine()) != null) {
                if (linea.trim().length() != 0) {
                    linea = linea.trim();
                    break;
                }
            }
        } catch (IOException e) {
            System.out.println("Problema leyendo el archivo: " + e.getMessage());
            System.exit(1);
        }
        return linea;
    }

    /**
     * Método String get_first_line de la clase realiza la lectura de un archivo leyendo unicamente la primera línea del mismo.
     * 
     *@param file -- el archivo que se desea leer.
     */
    public static String get_first_line(String file) {
        String strLinea = null;
        try{
            BufferedReader buffer = new BufferedReader(new FileReader(new File(file)));
            try {
                while ((strLinea = buffer.readLine()) != null) {
                    if (strLinea.trim().length() != 0) {
                        strLinea = strLinea.trim();
                        break;
                    }
                }
            } catch (IOException e) {
                System.out.println("Problema leyendo el archivo: " + e.getMessage());
                System.exit(1);
            }
        } catch (FileNotFoundException e){ //Atrapa las excepciones
            System.out.println("No se pudo abrir el archivo: " + e.getMessage());
        }
        return strLinea;
    }
}


