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

