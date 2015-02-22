package messaging;

/**
 * @author Gregory Boissinot
 */
public interface Reveiver<E extends Event> {
    void receive(E event);
}
