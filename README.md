# Attention Guided Low light Image Enhancement

## Project Name
Distributed Hash Table
## Objective

Implement a Distributed Hash Table that is tolerant to peer churns and is highly scaleable and needs no central server.

## What's the need for creating this Model?
Distributed Systems are very important for large organizations that require large amount of memory and storage. A Hash Table is an inmemory data structure to store items as Key->Value pair. Here, I try to implement a Distributed Hash Table that stores String-> Integer mapping. I have used Chord as the underlying algorithm.


## Assumptions made in this model

While making this system, I have taken a few assumptions that simplify the process.
1) The number of nodes is assumed to be 64.(This is mostly controlled by the application layer that runs on top of the DHT implemetation.)
2) In the original Chord paper, SHA1 is used to hash the nodes. But here I have used a simpler hash function because my main aim was in the implementation rather than using it as a full fledged software for large companies.
3) All the nodes are present in the localhost. (This allows me to store only the port number instead of the whole IP adress. However, it can be easily changed so that it stores the IP adress of remote node rather than only PORT number).

## A sneak peak into the architecture :)

Before reading this, I would request you to go over the![Chord Paper](https://pdos.csail.mit.edu/papers/ton:chord/paper-ton.pdf) since I am not going to go over the concepts described in the paper. \
Each node has a Lookup Table which contains the PORT number of some other nodes in the table. Now, we can have two extreme cases-
##### 1) When each node stores the PORT number of all the nodes present in the system
Advantages - We can have O(1) lookup and insertion.
Disadvantages - The memory requirements increase linearly and when a new node arrives or nodes leave(peer churns), all the nodes have to change their lookup tables which is very cumbersome and all nodes will have to perform update operaions.

##### 1) When each node stores the PORT number of only it's successor
Advantages - When new nodes arrive or nodes leave the system , only two nodes will perform update operations on their lookup tables.
Disadvantages - The lookup and insertion complexities will be O(N), which is not at all acceptable in large companies where increase in latency of few milliseconds can hamper their business.

##### 1) So....What do we do now?
We 
