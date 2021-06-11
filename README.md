# Associate Social Media Network

## Description:
Associate is a java application that allows users to follow other
users and companies like a social media platform. This project was
made using Java, MySQL, Postgres, and Git. 

Concepts and tools used:
- Undirected Graphs
- relational database
- object-oriented programming
- Unit testing

Notes:
- users identified with username
- company identified with company name

TODO:
- place test methods into separate files
- unit tests for all methods
  Main
  Company
  User
- add comments to all code
- make getDegree in another way
- shorten uses of code (make it more modular)
- add return; after catch statements for loading methods
- make sql super class abstract
- make main class interactive

SQL ready: (COMPLETED)<br />

    CREATE: <br />
        - user: create new user - tested <br />
        - company: create new company - tested <br />

    READ: <br />
        - most methods use read to get data <br />

    UPDATE: <br />
        - user: add friend (everything updates across db) - tested <br />
        - user: add company (everything updates across db) - tested <br />
        - user: rename first, last, username (everything updates across db) - tested <br />
        - user: remove friend (everything updates across db) - tested <br />
        - user: remove company (everything updates across db) - tested <br />
        - company: add network (everything updates across db) - tested <br />
        - company: rename company name (everything updates across db) - tested <br />
        - company: remove network (everything udpates across db) - tested <br />

    DELETE: <BR />
        - user: delete user (everything updates across db) - tested <br />
        - company: delete company (everythin updates across db) -tested <br />
