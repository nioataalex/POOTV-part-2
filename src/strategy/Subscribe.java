package strategy;

import com.fasterxml.jackson.databind.node.ArrayNode;
import fileio.Actions;
import fileio.User;
import pages.Output;

import java.util.ArrayList;
import java.util.Stack;

public class Subscribe implements Strategy {


    private final Stack<String> succesfulAction;
    private final Output printOutput;
    private final ArrayNode output;

    private final User currentUser;

    private final Actions actions;

    private final String currentPage;

    public Subscribe(final Stack<String> succesfulAction, final Output printOutput,
                     final ArrayNode output, final User currentUser,
                     final Actions actions, final String currentPage) {
        this.succesfulAction = succesfulAction;
        this.printOutput = printOutput;
        this.output = output;
        this.currentUser = currentUser;
        this.actions = actions;
        this.currentPage = currentPage;
    }

    /**
     * <p>

     */

    public void addSubscription(final String subscribedGenre, final User currentUser) {
        for (int i = 0; i < currentUser.getSeenMovie().size(); i++) {
            for (int j = 0; j < currentUser.getSeenMovie().get(i).getGenres().size(); j++) {
                if (currentUser.getSeenMovie().get(i).
                        getGenres().get(j).compareTo(subscribedGenre) == 0) {
                    if (currentUser.getSubscribedGenres() == null) {
                        ArrayList<String> helper = new ArrayList<>();
                        helper.add(subscribedGenre);
                        currentUser.setSubscribedGenres(helper);
                    } else {
                        for (int k = 0; k < currentUser.getSubscribedGenres().size(); k++) {
                            if (currentUser.getSubscribedGenres().
                                    get(k).compareTo(subscribedGenre) == 0) {
                                printOutput.errorOutput(output);
                                break;
                            } else {
                                currentUser.getSubscribedGenres().add(subscribedGenre);
                                currentUser.setSubscribedGenres(currentUser.getSubscribedGenres());
                                break;
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * <p>

     */
    @Override
    public void action() {
        String subscribedGenre = actions.getSubscribedGenre();
        if (succesfulAction.peek().compareTo("see details") != 0) {
            printOutput.errorOutput(output);
        } else if (succesfulAction.peek().compareTo("see details") == 0) {
            addSubscription(subscribedGenre, currentUser);
        } else  if (currentPage.compareTo("see details") != 0) {
            printOutput.errorOutput(output);
        }
    }
}
