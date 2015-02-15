package util.mongodb.exception;

/**
 * @author Gregory Boissinot
 */
public class DuplicateDocumentException extends MongoDBException {

    public DuplicateDocumentException() {
    }

    public DuplicateDocumentException(String message) {
        super(message);
    }

    public DuplicateDocumentException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicateDocumentException(Throwable cause) {
        super(cause);
    }

}
