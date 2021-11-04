package control;

/**
 * EXCEPTION PARA MANEJAR LOS FALLOS EN FileControl
 *
 * @author pipe
 * @version 1.0
 */
public class FileException extends Exception {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     *
     * @param message MENSAJE CON INFORMACIÓN DEL ERROR
     */
    public FileException(String message) {
        super(message);
    }
}
