# Distributed Hash Table (Chord implementation)

## Project Name
Distributed Hash Table
## Objective

Implement a Distributed Hash Table that is tolerant to peer churns and is highly scaleable and needs no central server.

## What's the need for creating this System?
Distributed Systems are very important for large organizations that require large amount of memory and storage. A Hash Table is an inmemory data structure to store items as Key->Value pair. Here, I try to implement a Distributed Hash Table that stores String-> Integer mapping. I have used Chord as the underlying algorithm.



## A sneak peak into the architecture :)

#### Operations
The Hash Table supports oprations like INSERT(to insert new entry) , GET (to get the value of a key) , DELETE (to delete an entry from a hash table).
##### How do the nodes decide on which node is a key stored or in which node to insert?
All the nodes are assigned a node number. \
First each node's IP is hashed using a suitable Hash Function(SHA1 in original paper). Then we use consistent hashing to place the nodes in a circular fashion which is known as Chord circle.


The key is first hashed by an algorithm , like SHA1(I have used a simpler hash function). Then it uses consistent hashing to generate a number between [0, M] , where M is the maximum number of nodes that can be present in the ring. Then the nodes use some predefined heuristics to determine the node on which it should be stores. When the node numeber is found, it checks if it has the adress of the remote node that should store it(from it's fingertables). If not, it forwards it to some other node(to which node it will forward this message is again governed by some rules) which again checks if it has the adress of the node that is requires and the process continues. 
 
 

#### About FingerTables
Before reading this, I would request you to go over the![Chord Paper](https://pdos.csail.mit.edu/papers/ton:chord/paper-ton.pdf) since I am not going to go over the concepts described in the paper. \
Each node has a Lookup Table which contains the PORT number of some other nodes in the table. Now, we can have two extreme cases-
##### 1) When each node stores the PORT number of all the nodes present in the system
Advantages - We can have O(1) lookup and insertion. \
Disadvantages - The memory requirements increase linearly and when a new node arrives or nodes leave(peer churns), all the nodes have to change their lookup tables which is very cumbersome and all nodes will have to perform update operaions.

##### 1) When each node stores the PORT number of only it's successor
Advantages - When new nodes arrive or nodes leave the system , only two nodes will perform update operations on their lookup tables. \
Disadvantages - The lookup and insertion complexities will be O(N), which is not at all acceptable in large companies where increase in latency of few milliseconds can hamper their business.

##### 1) So....What do we do now?
We use lookup tables that contain floor(log M, base 2) adresses, where M is the maximum number of nodes that can be present in the Chord Ring which is decided by the application layer. \
By doing so, we hit the sweet spot. Now we can reduce lookup and insertion complexities to O(log M), which is quiet acceptable. \
I would encourage you to check out the original paper to know which nodes are present in the fingertables.


#### How do nodes communicate?
Each node has a Client(DHTClient.java) and Server(DHTServer.java) class that is used to Send requests and listen to incoming requests to the node. \
The communication happens using Sockets that use TCP as Transport Layer Protocol.

#### How do Nodes guarantee fault tolerance?
I would start this by stating the fact that Chord does not gurantee 100 % fault tolerance(Like any other distributed systems). However, it does take care that there is no single point of failure. Each node maintains a list of successors where it stores -> The successor, The successor's successor and so on... 
This ensures that even if one node fails , it's predecessor has the adress of it's successor's successor which will now become it's successor. \
So how many adresses should I store in the successor list? \
Well, we choose an optimal number which is floor(log(M) base 2).



## Assumptions made in this implementation

While making this system, I have taken a few assumptions that simplify the process.
1) The number of nodes is assumed to be 64.(This is mostly controlled by the application layer that runs on top of the DHT implemetation.)
2) In the original Chord paper, SHA1 is used to hash the nodes. But here I have used a simpler hash function because my main aim was in the implementation rather than using it as a full fledged software for large companies.
3) All the nodes are present in the localhost. (This allows me to store only the port number instead of the whole IP adress. However, it can be easily changed so that it stores the IP adress of remote node rather than only PORT number.However, this is not the case for real world Distributed Systems).
