# Bus Route Challenge

Project was build using NetBeans IDE but can be easily imported for packaging,
deployment and run using any other IDE or command line. 

### How application works

All the data imported from data file, are placed in memory. To optimize search 
performance, all the routes are indexed.

|index_id(bus stop id)|route ids         |
|---------------------|------------------|
|5					  |2, 19, 6, 7, 18   |
|11					  |2, 13, 7, 8       |
|12					  |13, 14, 6, 8, 11  |
|16					  |6, 11             |
|17					  |1, 14             |
|19					  |6                 |
|20					  |1                 |
|24					  |1                 |
|106				  |1, 2, 13, 8       |
|114				  |19, 14, 7, 18, 11 |
|118				  |6                 |
|121				  |19, 7, 11         |
|138				  |6, 8, 18          |
|140				  |1                 |
|142				  |5, 14, 8          |
|148				  |1, 13, 6, 7, 8, 11|
|150				  |1, 19, 14, 7      |
|153				  |1, 19, 13, 18     |
|155				  |11                |
|160				  |1                 |
|169				  |13, 7, 8, 11      |
|174				  |14, 6             |
|179				  |14                |
|184				  |6                 |

After all the data is places in memory, application is ready to accept requestsN.

***Next step*** is to get indexes for departure and arrival bus stop ids:

departure stop index(114) = 9, 14, 7, 18, 11

arrival stop index(150) = 1, 19, 14, 7

***Next step*** is to iterate one index array and to check if second index array
 has the same value.

If route id is found in in both index arrays, Route with this id will be 
returned if index of departure bus stop id is less then index of arrival bus 
stop id. Other way null is returned. 

### Project structure

```
project_folder
├── src
├── data/
├── pom.xml
├── Dockerfile
└── service.sh
```

### Configuration

Before running application it should be configured to use route data file.

Configuration options are located in file `src/main/resources/app.properties`.

### Package

`./service.sh dev_build`

### Run with Jetty

After building of project, project can be easily deployed using jetty-runner.jar
file. Corresponding maven dependency plugin executor is included in pom.xml.

`./service.sh dev_run`

### Run with Tomcat

Deploy to tomcat using manager application