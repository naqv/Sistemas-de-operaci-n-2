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
public class Box<E> {
    
    /**
     * Valor del contenido de la caja 
     */
    private E value;
    /**
     * Valor del segundo contenido de la caja 
     */
    private E value2;
    /**
     * Apuntador al elemento siguiente de la cola
     */
    public Box<E> next;

    /**
     * Constructor de la clase Box, vacia.
     * <p>
     * Crea una caja con tres elementos, el valor, el segundo
     * valor y un apuntador a una elemento del mismo tipo. 
     * Inicializa los tres en null.
     * <p>
     */
    public Box() {// Empty constructor
	this.value = null;
        this.value2 = null;
	this.next = null;
    }
    /**
     * Constructor de la clase Box, solo con el elemento.
     * <p>
     * Crea una caja con tres elementos, el valor, el segundo
     * valor y un apuntador a una elemento del mismo tipo. 
     * Inicializa el valor con el elemento, el otro valor y next con null.
     * <p>
     *
     * @param  e elemento con que se creara la caja
     */
    public Box(E e) { //Constructor with element
	this.value = e;
        this.value2 = null;
	this.next = null;
    }
    /**
     * Constructor de la clase Box, solo con el elemento.
     * <p>
     * Crea una caja con tres elementos, el valor, el segundo
     * valor y un apuntador a una elemento del mismo tipo. Inicializa 
     * el valor con el elemento, el otro valor con el segundo elemento
     * y next con null.
     * <p>
     *
     * @param  e elemento con que se creara la caja
     */
    public Box(E e, E e2) { //Constructor with element
	this.value = e;
        this.value2 = e2;
	this.next = null;
    }
    /**
     * Constructor de la clase Box completa.
     * <p>
     * Crea una caja con tres elementos, el valor, el segundo
     * valor y un apuntador a una elemento del mismo tipo. Inicializa 
     * el valor con el elemento, el otro valor con el segundo elemento
     * y next con la otra caja.
     * <p>
     *
     * @param  e elemento con que se creara la caja
     * @param  next elemento que sera apuntado por esta caja
     */
    public Box(E e, E e2, Box<E> next) {
        this.value = e;
        this.value2 = e2;
	this.next = next;
    }
    
    /**
     * Verifica si esta caja tiene el mismo valor que otra.
     * <p>
     * Compara con el operador "==", esta sujeto a cambios.
     * Al momento de que el tipo con el cual fue definida la caja
     * no pueda compararse con ese operador, dara una excepcion.
     * <p>
     *
     * @return true si los valores son iguales, sino false
     */
    public boolean equals(Box<E> obj) { //Depends of type E
	return (this.value == obj.get_value() && this.value2 == obj.get_value_to()); // Can Change!!
    }

    /**
     * Obtiene el valor de la caja.
     *
     * @return valor contenido en la caja
     */
    public E get_value() {
	return this.value;
    }

    /**
     * Obtiene el valor de la caja.
     *
     * @return valor contenido en la caja
     */
    public E get_value_to() {
	return this.value2;
    }
}
