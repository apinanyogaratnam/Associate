# Associate Social Media Network

## Description:
Associate is a java application that allows users to follow other
users and companies like a social media platform. This project was
made using java, MySQL, and Git. 

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
- sql CRUD methods
- finish user methods
- make company methods
- unit tests for all methods
- for sql methods, migrate methods to User and Company classes
- make sql methods concurrent with user methods
- add comments to all code
- make getDegree in another way
- possibly make SQL class with polymorphism CRUD with extends SQL
- rename methods in sql from update to add
- shorten uses of code (make it more modular)
- add return; after catch statements for loading methods

SQL ready: <br />

    CREATE: <br />
        - create new user - tested <br />
        - create new company - tested <br />

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
