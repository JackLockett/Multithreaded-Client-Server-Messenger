# Multithreaded-Client-Server-Messenger

This is a Multithreaded chat application that I created during my second year at Sheffield Hallam University for the Networked Software Development module.

# Assessment Brief

# Introduction
In this assignment, you will design and implement a console-based multithreaded client-server messaging application for Sheffield Hallam University student
community, using networked application development techniques. You will demonstrate your understanding of those techniques in the implementation of
your prototype system. You are at liberty to decide and justify what kind of messaging application you want to design and implement. However, you will be
provided with specific communication protocol for the messaging system (please see the server’s specification below). This protocol must be strictly adhered
to.

# Task
A prototype of the multithreaded client-server messaging application for Sheffield Hallam University student community will be designed and implemented
using client-server architecture and network software development techniques. As the architecture suggests, the prototype will consist of a multithreaded
server and clients communicating over a network. The system will allow clients to publish messages, subscribe to channels, and read messages through the
channels to which they have subscribed.

# Server Specification
The server and clients will exchange message requests and responses using JSON format based on a fixed protocol specified in a separate document (included
in the supplementary material). You will implement, using Java, a server that allows multiple clients to connect concurrently on port 12345 (you may choose a
different port number) and that correctly responds to the requests as detailed in the protocol specification. You may assume that messages only have JSON
encoded object with “from”, “when” and “body” (please see the client specifications below). For the server, an optional persistence as an extension is
recommended. In this regard, the server should be able to back up messages to a persistence storage such as a database or a file on the disk. If the server is
killed, and restarted, it should be able to automatically recover the persisted messages.

# Client Specification
The client to be implemented in Java should connect to the server above running on localhost and port number 12345 (you may choose a different port
number). The client should allow a user to login to a particular channel, subscribe to and unsubscribe from other channels, publish messages, and read messages
that have been published on the subscribed channels. This functionality must be implemented to communicate with servers using the JSON protocol in the
server specification above. The users of your application should be able to interact with the client through a console. In addition, the application console should
automatically display (without user’s intervention) messages that have been published on their respective subscribed channels. For the client, you may decide
to implement an optional extension such as the capability to search messages, e.g., display all messages containing specific hashtag. Other extensions are
allowed but you should discuss these with your tutors to clarify which feature counts as an extension
