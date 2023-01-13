import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ArrayNode;

import factory.DatabaseFactory;
import factory.GetChangesFactory;
import fileio.*;
import pages.*;
import strategy.*;


import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Stack;


public class Main {

    private static int fifteen = 15;

    public static void main(final String[] args)
            throws IOException {
        File inputFile = new File(args[0]);
        File outputFile = new File(args[1]);

        File f = new File(args[0]);

        ObjectMapper objectMapper = new ObjectMapper();
        Input inputData = objectMapper.readValue(inputFile,
                Input.class);

        ArrayNode output = objectMapper.createArrayNode();

        ArrayList<User> users = inputData.getUsers();
        ArrayList<MovieCredentials> movies = inputData.getMovies();


        for (User user : users) {
            user.setNumFreePremiumMovies(fifteen);
        }

        User currentUser = new User();

        Output printOutput = new Output();
        boolean watched = false;

        String currentPage = "";
        boolean firstEnter = false;

        Stack<String> succesfulAction = null;


        for (Actions action : inputData.getActions()) {
            String type = action.getType();
            if (type.compareTo("change page") == 0) {
                switch (action.getPage()) {
                    case "login":
                        if (!firstEnter) {
                            firstEnter = true;
                            Homepage homepage = new Homepage(action);
                            succesfulAction = homepage.newStack();
                        } else if (currentUser != null) {
                            printOutput.errorOutput(output);
                        } else {
                            Homepage homepage = new Homepage(action);
                            succesfulAction = homepage.newStack();
                        }
                        break;
                    case "register":
                        Homepage homepage = new Homepage(action);
                        succesfulAction = homepage.newStack();
                        break;
                    case "logout":
                        assert currentUser != null;
                        if (currentUser.getCredentials() == null) {
                            printOutput.errorOutput(output);
                        }
                        currentUser = null;
                        for (int i = 0; i < Objects.requireNonNull(succesfulAction).size(); i++) {
                            succesfulAction.pop();
                        }
                        break;
                    case "movies":
                        Movies printMovies = Movies.getMovies();
                        printMovies.action(currentUser, movies, printOutput, output);
                        Objects.requireNonNull(succesfulAction).push(action.getPage());
                        break;
                    case "see details":
                        watched = false;
                        assert currentUser != null;
                        SeeDetails details = new SeeDetails(action, currentUser, printOutput,
                                output, users, succesfulAction, currentPage);
                        details.action();
                        break;
                    case "upgrades":
                        Objects.requireNonNull(succesfulAction).push(action.getPage());
                        break;
                    default: break;
                }
                currentPage = action.getPage();
            } else if (type.compareTo("on page") == 0) {
                switch (action.getFeature()) {
                    case "login":
                        UserCredentials credentials = action.getCredentials();
                        if (currentUser == null
                                || currentUser.getCredentials() == null) {
                            boolean exists = false;
                            for (User user : users) {
                                String name = user.getCredentials().getName();
                                String password = user.getCredentials().getPassword();
                                if (name.compareTo(credentials.getName()) == 0
                                        && password.compareTo(credentials.getPassword()) == 0) {
                                    currentUser = user;
                                    exists = true;
                                    currentUser.setCurrentMovies(null);
                                    printOutput.newUser(output, currentUser);
                                    break;
                                }
                            }
                            if (!exists) {
                                printOutput.errorOutput(output);
                            }
                        } else {
                            printOutput.errorOutput(output);
                        }
                        break;
                    case "register":
                        Homepage register = new Homepage(printOutput, output, action, users);
                        currentUser = register.getUser();
                        break;
                    case "search":
                        Context context = new Context(new Search(action,
                                currentUser, printOutput, output));
                        assert currentUser != null;
                        context.executeStrategy();
                        break;
                    case "filter":
                        Objects.requireNonNull(currentUser).setFilterMovie(null);
                        context = new Context(new FilterAction(action, currentUser,
                                printOutput, output, users, succesfulAction, currentPage));
                        context.executeStrategy();
                        break;
                    case "buy tokens":
                        context = new Context(new BuyTokens(action, currentUser));
                        context.executeStrategy();
                        break;
                    case "buy premium account":
                        context = new Context(new BuyPremiumAccount(currentUser));
                        context.executeStrategy();
                        break;
                    case "purchase":
                        assert currentUser != null;
                        context = new Context(new Purchase(currentUser, printOutput, output));
                        context.executeStrategy();
                        break;
                    case "watch":
                        watched = false;
                        if (currentPage.compareTo("purchase") != 0) {
                            printOutput.errorOutput(output);
                        } else {
                            assert currentUser != null;
                            if (currentUser.getCurrentMovies() != null) {
                                if (currentUser.getCurrentMovies().size() == 0
                                        || currentUser.getSeenMovie() == null) {
                                    printOutput.errorOutput(output);
                                } else {
                                    context = new Context(new Watch(currentUser,
                                            printOutput, output));
                                    context.executeStrategy();
                                    watched = true;
                                }
                            }
                        }
                        break;
                    case "like":
                        context = new Context(new Like(currentUser,
                                printOutput, output, currentPage, watched));
                        context.executeStrategy();
                        break;
                    case "rate":
                        context = new Context(new Rate(action, currentUser,
                                printOutput, output, users, succesfulAction, watched));
                        context.executeStrategy();
                        break;
                    case "subscribe":
                        context = new Context(new Subscribe(succesfulAction, printOutput,
                                output, currentUser, action, currentPage));
                        context.executeStrategy();
                        break;
                    default: break;
                }
                currentPage = action.getFeature();
            } else if (type.compareTo("back") == 0) {
                BackAction backAction = new BackAction(succesfulAction,
                        printOutput, output, currentUser);
                backAction.action();
            } else if (type.compareTo("database") == 0) {
                switch (action.getFeature()) {
                    case "add":
                        MovieCredentials addedMovie = action.getAddedMovie();
                        GetChangesFactory context = new GetChangesFactory(movies,
                                addedMovie, printOutput, output, users);
                        DatabaseFactory database =  context.createDatabase("add");
                            database.action();
                        break;
                    case "delete":
                        String deletedMovie = action.getDeletedMovie();
                        context = new GetChangesFactory(movies,
                                deletedMovie, printOutput, output, users);
                        database =  context.createDatabase("delete");
                        database.action();
                        break;
                    default: break;
                }
            }

        }

        if (currentUser != null) {
            if (currentUser.getCredentials().getAccountType().compareTo("premium") == 0) {
                Recommendation recommendation = Recommendation.getRecommendation();
                recommendation.action(currentUser, output, printOutput);
            }
        }

        File out = new File("result" + f.getName() + ".json");
        ObjectWriter objectWriter = objectMapper.writerWithDefaultPrettyPrinter();
        objectWriter.writeValue(out, output);

        objectWriter = objectMapper.writerWithDefaultPrettyPrinter();
        objectWriter.writeValue(outputFile, output);


    }

}
