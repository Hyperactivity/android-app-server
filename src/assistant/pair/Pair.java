package assistant.pair;

/**
 * Created with IntelliJ IDEA.
 * User: OMMatte
 * Date: 2013-04-15
 * Time: 14:25
 */
public abstract class Pair<L,R> {

    protected L left;
    protected R right;


    private L getLeft() { return left; }
    private R getRight() { return right; }

    @Override
    public int hashCode() { return left.hashCode() ^ right.hashCode(); }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (!(o instanceof Pair)) return false;
        Pair pairo = (Pair) o;
        return this.left.equals(pairo.getLeft()) &&
                this.right.equals(pairo.getRight());
    }

}