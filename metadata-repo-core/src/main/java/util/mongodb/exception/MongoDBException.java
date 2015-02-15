package util.mongodb.exception;

/**
 * @author Gregory Boissinot
 */
public class MongoDBException extends RuntimeException {

    public MongoDBException() {
    }

    public MongoDBException(String message) {
        super(message);
    }

    public MongoDBException(String message, Throwable cause) {
        super(message, cause);
    }

    public MongoDBException(Throwable cause) {
        super(cause);
    }

}
