package algorithm.move;

/**
 * @author Kamil Walkowiak
 */
public abstract class Move {
    int delta;

    public int getDelta() {
        return delta;
    }
    public abstract int countDelta();
    public abstract void doMove();
}
