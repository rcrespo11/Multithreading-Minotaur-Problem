# Multithreading-Minotaur-Problem

The assignment uses multithreading as a solution to the Minotaur Problem

To compile the programs:

javac Problem1.java
javac Problem2.java

To execute the programs, no user input is needed. Simply run:

java Problem1.java
java Problem2.java

# Problem 1

For the first problem, the activation of a thread is required for each guest. To solve the issue of informing the Minotaur when all guests have eaten a cake, it's necessary for the first thread to count how many times the cake is replaced. Since each guest will be called randomly, every time a guest consumes a cake, a flag will be assigned to determine if they had previously consumed a cake or not. When everyone has had the opportunity to eat, and the counter reaches N guests, the Minotaur will be informed and the program will terminate.

# Problem 2

For the following program, the best implementation option is the second one. This strategy is the most straightforward and less complex to implement, as it doesn't require managing a queue; only mutual exclusion for each thread is needed. The downside is that, similar to the first problem, it's difficult to have a specific order for each visitor, which could affect the execution time. 
The way it works is by creating a set to identify which threads have visited the vase. Also, the room will have the properties of "empty" and "busy," so it can be known when the next guest can enter. 
Every time a guest enters to see the vase, they will be added to the set so they can only visit it once.
