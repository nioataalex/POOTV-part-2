package strategy;

import fileio.Actions;
import fileio.User;

public class BuyTokens implements Strategy {

    private Actions actions;
    private User currentUser;

    public BuyTokens(final Actions actions, final User currentUser) {
        this.actions = actions;
        this.currentUser = currentUser;
    }

    /**
     * <p>

     */
    @Override
    public void action() {
        String tokens = actions.getCount();
        int newTokens = Integer.valueOf(tokens);
        currentUser.setTokens(newTokens + currentUser.getTokens());
        int balance = Integer.valueOf(currentUser.getCredentials().getBalance());
        int newBalance = balance - newTokens;
        currentUser.getCredentials().setBalance(String.valueOf(newBalance));
    }
}
