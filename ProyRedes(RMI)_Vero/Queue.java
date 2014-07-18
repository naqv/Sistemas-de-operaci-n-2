/*
 * David Lilue     --- 09-10444
 * Veronica Liñayo --- 08-10615
 * 
 * Grupo 33
 */

/**
 * @author      David Lilue <dvdalilue@gmail.com> --- 09-10444
 *		Verónica Liñayo <vlinayo@gmail.com> --- 08-10615
 * @version     1.0          
 * @since       2014-01-07
 */
public class Queue<E> {
    
    /**
     * Apuntador al principio de la cola
     */
    public Box<E> ini_queue; //First Element
    /**
     * Apuntador al final de la cola
     */
    public Box<E> end_queue; //Last Element
    /**
     * Contador de los elementos
     */    
    private int counter;      //Counter of elements

    /**
     * Constructor de la clase Queue.
     * <p>
     * Crea una estructura con tres elementos, una cabeza, 
     * con el principio, el final y un contador de los elementos 
     * de la cola. Inicializa los dos apuntadores en null 
     * y el contador en 0.
     * <p>
     */
    public Queue() {
        this.ini_queue = null; //Initialize on null
        this.end_queue = null;
        this.counter = 0; //is empty
    }

    /**
     * Agrega un elemento al final.
     * <p>
     * Toma el elemento nuevo, crea una caja para guardarlo,
     * si la cola esta vacia, el apuntador del principio y 
     * final apuntaran a el. En caso contrario el final de la
     * cola apuntara al nuevo elemento al igual que el final 
     * de la cola. En cualquier caso el contador aumenta en 1.
     * <p>
     *
     * @param  elem elemento que sera agregado
     */
    public void add_end(E elem, E elem2) {
	Box<E> aux = new Box<E>(elem, elem2);
        if (this.counter == 0) { // Case of empty queue
            this.end_queue = this.ini_queue = aux;
        } else { // Else case 
            this.end_queue = this.end_queue.next = aux;
        }
	this.counter += 1; //Always counter raise by 1
    }

    /**
     * Agrega un elemento al principio(pila).
     * <p>
     * Toma el elemento nuevo, crea una caja para guardarlo,
     * donde guarda al elemento y apunta al principio de la cola.
     * Si la cola(pila) esta vacia, el apuntador del principio y 
     * final apuntaran a el. En caso contrario el principio de la
     * cola(pila) apuntara al nuevo elemento de la cola. 
     * En cualquier caso el contador aumenta en 1.
     * <p>
     *
     * @param  elem elemento que sera agregado
     */
    public void add_ini(E elem, E elem2) {
        Box<E> aux = new Box<E>(elem, elem2, this.ini_queue);
        if (this.counter == 0) { // Case of empty queue
            this.end_queue = this.ini_queue = aux;
        } else { // Else case
            this.ini_queue = aux;
        }
	this.counter += 1; //Always counter raise by 1
    }

    /**
     * Retorna el primer elemento de la cola.
     * <p>
     * Si la cola esta vacia retorna null. Sino obtiene el valor
     * del primer elemento de la cola. Adelanta el apuntador del
     * principio al su siguiente. Por ultimo reduce el contador por 1.
     * <p>
     *
     * @return retorna el primer elemento si existe, sino null
     */
    public E first() {
	if (this.counter > 0) {
            E aux = this.ini_queue.get_value(); //gets the value on the first element
	    this.ini_queue = this.ini_queue.next; //lost the pointer to it
	    this.counter--; //decrese the counter by 1 
	    return aux;
	}
	return null;
    }

    /**
     * Verifica si la cola esta vacia.
     * <p>
     * Verifica si el contador es equivalente a 0, para decir
     * si esta vacia. Sino retorna false.
     * <p>
     *
     * @return true si hay algun elemento, sino false
     */
    public boolean is_empty() {
	if (this.counter == 0) {
	    return true; //if is 0, then true
	}
	return false;
    }

    /**
     * Obtiene el numero de elementos.
     *
     * @return numero de elementos en la cola
     */
    public int get_count() {
        return this.counter;
    }

    /**
     * Imprime en contenido de la cola.
     *
     */
    public void to_s() {
        Box<E> aux = this.ini_queue;
        while (aux != null) {
            System.out.println(aux.get_value() + " - " + aux.get_value_to());
            aux = aux.next;
        }
    }

    /**
     * Imprime en contenido de la cola hasta un maximo.
     *
     * @param max numero maximo de elementos a imprimir
     */
    public void to_s(int max) {
        Box<E> aux = this.ini_queue;
        int i = 0;
        while ((aux != null) && (i < max)) {
            System.out.println(aux.get_value() + " - " + aux.get_value_to());
            aux = aux.next;
            i++;
        }
    }
}
