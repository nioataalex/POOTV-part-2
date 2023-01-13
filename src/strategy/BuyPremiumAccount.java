package strategy;

import fileio.User;

public class BuyPremiumAccount implements Strategy {
    private final int ten = 10;

    private final User currentUser;

    public BuyPremiumAccount(final User currentUser) {
        this.currentUser = currentUser;
    }

    /**
     * <p>

     */
    @Override
    public void action() {
        currentUser.getCredentials().setAccountType("premium");
        currentUser.setTokens(currentUser.getTokens() - ten);
    }
}
