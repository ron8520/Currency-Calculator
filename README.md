# W14B_G4_Assignment1

## members
    Ron Ruan
    Matthew Wang
    Jayce Zhu
    Paul Wang
    Sylvia Liu

This project develop a simple currency converter application in JAVA. Users can simply check the Top 4 popular currency that store in the json file database, caclulate the max, min, median, average and standard derivation of the specific currency and serach the history of the currency. If the user is an admin, it has permission to change the currency rate or add a new currency to the current database and display on the Home page table. That's involed Gradle, Jenkins for automitc testing, Junit junpiter for jacoco report and JavaFX for GUI

To run our product, users can use terminal to get into the folder and use "gradle build" and "gradle run" to start it after downloading it.

To test our product, after getting into the folder by terminal, testers need to use "gradle test" to start testing our code. For the code in the folder "model", the testcase will run automatically and assert if error happens. For the code in the folder "view", the testers will be shown the interface for testing. They can test the interface manually as the view of users.

## Requirments:
To use jenkins correctly, make sure your complier java version and runtime java version are the same. In jenkins, the java version is java 11.
