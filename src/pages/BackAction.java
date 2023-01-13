package pages;

import com.fasterxml.jackson.databind.node.ArrayNode;
import fileio.User;

import java.util.Stack;

public class BackAction {

    private final Stack<String> succesfulAction;
    private final Output printOutput;
    private final ArrayNode output;

    private final User currentUser;

    public BackAction(final Stack<String> succesfulAction,
                      final Output printOutput, final ArrayNode output,
                      final User currentUser) {
        this.succesfulAction = succesfulAction;
        this.output = output;
        this.printOutput = printOutput;
        this.currentUser = currentUser;
    }

    /**
     * <p>
     * method that helps the current user to go back to the page that he was on before
     * the current page
     */
    public void action() {
        if (succesfulAction.isEmpty()) {
            printOutput.errorOutput(output);
        } else if (succesfulAction.peek().compareTo("login") == 0
                || succesfulAction.peek().compareTo("register") == 0) {
            printOutput.errorOutput(output);
        } else {
            succesfulAction.pop();
            if (succesfulAction.size() == 0) {
                printOutput.errorOutput(output);
            } else if (succesfulAction.peek().compareTo("login") == 0
                    || succesfulAction.peek().compareTo("register") == 0) {
                printOutput.errorOutput(output);
            } else if (succesfulAction.peek().compareTo("movies") == 0) {
                Movies movie = Movies.getMovies();
                movie.outputMovies(output, currentUser);
            }
        }
    }
}
