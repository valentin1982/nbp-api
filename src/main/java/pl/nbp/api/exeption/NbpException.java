package pl.nbp.api.exeption;

/**
 * custom class exception
 */
public class NbpException extends Exception {

    private static final long serialVersionUID = 1L;

    public NbpException(String message) {
        super(message);
    }
}
