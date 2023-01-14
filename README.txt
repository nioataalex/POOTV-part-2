322CA - Nioață Alexandra
January 2023

Project - PooTV - part two

Little reminder:
This program implements a simple backend of a platform like HBOMax, Netflix etc.
The input data is being loaded, in JSON format, and transformed
into objects. There are a few commands that needs to be executed and I will talk
separately about them. The result from each action is displayed in an output file,
in JSON format.
Before reading the actions, various preparations are made,
for example list of movies and user are made into 2 separate arraylists,
a user that represent the current user is made etc.
The output is modified by adding a new field called notifications, where all the notifications
that will be received by the user are stored.

Design Patterns:

1. Singleton:
- implements a method that creates the instance of a class that does not exist, or return the reference
if it is exists, the constructor must be private;
- I used the Lazy Instation implementation, because
the memory is used just when it is needed and the instance is called used getInstance() method;
- I used this pattern for classes: Movies and Recommendation;

2. Strategy:
- encapsulate algorithms in classes that provide a specific user interface,
  and can be selected at runtime;
- used for on page actions (like, rate, subscribe etc.);

3. Observer:
- defines a 1-to-n dependency relationship between objects so that when an object changes its state,
all its dependents are automatically notified and updated;
- using this pattern implies the existence of an object with the role of subject, which has associated a
list of dependent objects with the role of observers, which it automatically calls every time an action happens;
- used to inform users about new recommendations or changes in database;

4. Factory:
- applied for objects that instantiate related classes (implement the same interface, inherit the same abstract class);
- benefits when we want to isolate the object that needs an instance of a particular type from actually creating it;
- used for the movies' database changes (add and delete);

I implemented this patterns using the information from the laboratory dedicated to them.

Commands:
Firstly, we need to see on what type of page we are change page, on page, back or database. Most of the functionalities
are explained in the first part of the project. The new functionalities are:

1. subscribe:
- on page type;
- the current user has the opportunity to subscribe to some genres;
- the genres that can be subscribed to represent the genres of the film whose page we are on;
- if the user tries to subscribe again to a genre an error will appear;

2. database add:
- a new movie will be added to the database of movies;
- from the input is given all the movie's credentials;
- users who subscribe to one of the genres of this movie will be notified of its appearance;
- if the movie already exists in the database, an error will appear;

3. database delete:
- a movie will be erased from the database;
- from the input, the movie name will be given;
- if the movie doesn't exist in the database, an error will appear;
- the users that purchased the movies will be notified that the movie is removed;
- the movie will be erased from the purchased movie list, watched movie list etc.;
- the user will receive back 2 tokens if it is standard, and a premium free movie
if it is premium;

4. back:
- a "reverse" change page action;
- gives the possibility to return to the previous page;
- browsing between pages is seen as a stack in which pages that have been successfully
reached are added, and back is the "pop" action;
- an error will appear in the following cases: if the stack of pages is empty,
if we reach the login/register pages;
- the logout action deletes everything in the stack;

5. premium user recommendations:
- given just to the premium users;
- does not have a command, at the end of the actions,the connected premium user will receive
a movie recommendation based on the movie genres he likes the most;
- the algorithm for finding the best recommendation is:
   - creating a top with the most appreciated genres in lexicographic order and the number of likes;
   - sorting movies from the database that can be seen by the user in descending order
            according to the total number of likes;
   - finding the first movie in the database with the highest number of likes that has not been seen
        by the user and is part of the movie genre most liked by the user;
   - if the first movie has been viewed, it continues until a good movie is found;
   - if we don't find any movie, a suggestive message will appear;

Each error that might occur are very well explained on ocw.

Classes:
I made five packages: fileio that contains the classes that I needed for the input
and pages which contains the classes that represents few pages from the platform
and the class for creating the output, factory, observer and strategy, that helped me
for creating the design patterns.

I will explain the new classes that I created for the functionalities given in part 2.
Also, I changed a little bit of the first part of the project by making a class
for each on page action.

Every method from these classes is explained
using JavaDoc.

FileIO classes:
1. Notification:
- 2 strings that represents the movie name and a message;
- getters and setters;
- toString method;

2. User:
- added a new method for the Observer Pattern and a new array list
that keeps subscribed genres;

Factory classes:

1. AddChange:
- method for adding a new movie to the database;
- extends DatabaseFactory;

2. DatabaseFactory:
- abstract class used for Factory;

3. DeleteChange:
- method for deleting a movie from the database;
- extends DatabaseFactory;

4. GetChangesFactory:
- generate object of concrete classes based on given information;

Observer classes:

1. NewUpdates:
- implements Subject interface;
- methods for changes about notifications;

2. Observer:
- interface for Observer;
- perform a notification interface about changes in the topic;
- all observers for a given topic must implement this interface;
- provides a method that can be invoked by the Subject to notify a change;

3. Subject:
- interface for Observer;
- when changes occur it notifies all observers;
- provides method to add an Observer and a method by which observers can be unregistered;

Pages classes:
1. BackAction:
- method which returns to the previous page;

2. Homepage:
- methods for registering a new user and making a new stack;

Strategy:

1. Subscribe:
- methods used to subscribe to genres;

2. Context:
-  asks from Startegy interface to execute the type of strategy;

3. Strategy:
- interface defined fot Strategy Pattern;
- defined for actions implemented on page type;

Another used classes:

1. LikedGenres:
- string that represents a genre and int that represents
the number of likes from this genre;
- getters and setters;
- used to find the genres with the most likes and to make
the top for the recommendation for the premium user;

2. Recommendation:
- Singleton pattern used;
- methods for the algorithm that founds
a recommendation for the premium user;

Feedback:
- I loved this homework very much and I believe it was very
helpful in understanding some oop concepts;
- better understanding of design patterns;
- my biggest congrats!!!

Disclaimer:
- I wanted to change a little more of the main method,
to modularize it better, but worse, I broked the code.
I wanted to have a method for the login action and one for
register too and I wanted to use Strategy for them too.
