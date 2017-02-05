# ServerCommunication

###### This API enables you to easily create servers and clients messaging with each other


###Quick Overview / Tutorial:
###### (Exceptions were not caught in these examples)

When you use ServerCommunication, you must always have one server and at least one client to communicate.
#####You create a server like this:
```java
SimpleServer server = new SimpleServer(12345); //some random port
```
As the constructor parameter is self explaining I'll not go into detail.

Now you need at least one client to connect to the server. Once you instantiate a client it will automatically connect to the server.
#####You create a client like this:
```java
SimpleClient client = new SimpleClient("localhost", 12345, "My name");
```
Wow, here we have a bunch of parameters. The first one is obvisouly the host. Enter an IP address if your SimpleServer you have created is on a remote server. The second parameter is the port which must be the same as you used in your SimpleServer constructor.
The third parameter is the name of the client. If you later on would like to communicate between clients over the server, this is the way to identify the clients. The server will not accept a client if the name is already taken.
