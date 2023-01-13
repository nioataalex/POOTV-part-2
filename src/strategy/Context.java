package strategy;

public class Context {

    private Strategy strategy;

    public Context(final Strategy strategy) {
        this.strategy = strategy;
    }

    /**
     * <p>
     * method used to execute actions
     * Strategy Pattern
     */
    public void executeStrategy() {
        strategy.action();
    }
}
