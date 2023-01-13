package strategy;

public class Context {

    private Strategy strategy;

    public Context(final Strategy strategy) {
        this.strategy = strategy;
    }

    /**
     * <p>

     */
    public void executeStrategy() {
        strategy.action();
    }
}
