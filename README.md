# Alternative-Banking-System-JAVA
 
This is a java project of an alternative banking system, which allow clients to borrow and lend money to each other.
This is a client-server app, with a server based on TOMCAT. 
The program allows 1 admin and multiple cilents to be logged in to the system in every given moment.
The code is divided into 6 different modules:
1) AdminApp -> contains all the code relevant to the admin.
2) CustomerApp -> contatins all the code relevant to a regular customer.
3) DTO -> a module that contains only data transfer objects.
4) Engine -> contains all the system information, including customers, loans, transactions and etc... also responsible for all the logic and actions in the system.
5) JavaFX -> contains all the javaFx UI compontents of the admin and regular customers apps, including all the controllers of all the screens.
6) Server -> contains all the servlets of the server,handles every http request coming from any user (either JavaFX or POSTMAN).

The program also has another client, using POSTMAN, where you can use all the features of the program. The POSTMAN collection can be found next to the modules. 
