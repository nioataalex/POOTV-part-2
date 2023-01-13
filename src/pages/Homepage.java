package pages;

import com.fasterxml.jackson.databind.node.ArrayNode;
import fileio.Actions;
import fileio.User;
import fileio.UserCredentials;

import java.util.ArrayList;
import java.util.Stack;

public class Homepage {
    private Output printOutput;

    private ArrayNode output;

    private final Actions action;

    private ArrayList<User> users;

    public Homepage(final Output printOutput, final ArrayNode output,
                    final Actions action, final ArrayList<User> users) {
        this.printOutput = printOutput;
        this.output = output;
        this.action = action;
        this.users = users;
    }

    public Homepage(final Actions action) {
        this.action = action;
    }

    /**
     * <p>

     */
    public User getUser() {
        UserCredentials credentials = action.getCredentials();
        User newUser = new User();
        boolean ok = false;
        for (int i = 0; i < users.size() && !ok; i++) {
            String name = users.get(i).getCredentials().getName();
            if (name.compareTo(credentials.getName()) != 0) {
                newUser.setNumFreePremiumMovies(15);
                newUser.setCredentials(credentials);
                users.add(newUser);
                ok = true;
            }
        }
        if (!ok) {
            printOutput.errorOutput(output);
        } else {
            printOutput.newUser(output, newUser);
            return newUser;
        }
        return null;
    }

    /**
     * <p>

     */
    public Stack<String> newStack() {
        Stack<String> succesfulAction = new Stack<>();
        succesfulAction.push("enterHomepage");
        succesfulAction.push(action.getPage());
        succesfulAction.push("moviesHomepage");
        return succesfulAction;
    }

}
