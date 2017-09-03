# Yoti - Lampkicking

Technical Java test for Backend Java developer at Yoti. The application runs using Spring Boot in 
port 9001 (port can be configured in *'src/resources/application.yml'*).

## Software requirements

* Maven (min. v2)
* MongoDB (3.2+) - database to used should be configured in property *'spring.data.mongodb.uri'*, file *'src/resources/application.yml'*

## Getting started

* **Building application**: mvn clean install
* **Integration tests execution**: mvn install -P integration
* **Running the application**: mvn spring-boot:run

## What it does

Web service that simulates the moves of a robotic hoover inside of a rectangular room. It determines how many patches of dirt
get cleaned during those moves and where the robot stops. 

Using a Cartesian coordinate system, the top bottom left corner of the room is (0,0) and top right corner is (maxX,maxY), where maxX represents the
width and maxY the height. The room will be rectangular, no obstacles (except the room walls), no doors.

Placing the hoover on a patch of dirt ("hoovering") removes the patch of dirt and it is considered cleaned. The hoover is always on - 
there is no need to enable it. Driving into a wall has no effect (the robot skids in place). The current position won't be considered to
be cleaned, unless the robot passes over that same position during the simulation.

## Simulation webservice endpoint

The simulation can be executed by calling the webservice at endpoint '/api/v1/simulate'. The endpoint accepts POST requests 
with json payload and returns a json payload.

### Request

The request body is expected to have the following format:

* roomSize - [Width, Height] of the room
* coords - [X,Y] robot initial coordinates
* patches - list of coordinates [X,Y] with dirt patches
* instructions - sequence of moves of the robot, with possible values:
** N - move towards North
** E - East
** S - South
** W - West

Example:
```javascript
{
  "roomSize" : [5, 5],
  "coords" : [1, 2],
  "patches" : [
    [1, 0],
    [2, 2],
    [2, 3]
  ],
  "instructions" : "NNESEESWNWW"
}
```

### Response
Service returns as a json payload. The format to expect is:

* coords - [X,Y] robot final position
* patches - number of locations left with dirt patches

**Example of successful call (201 - Created):**

```javascript
{
  "coords" : [1, 3],
  "patches" : 1
}
```

If the provided room size if not valid (X < 1 or Y < 1), the initial position
places the robot outside of the room or the instructions have values outside
{N, E, S, W} and error response will be generated:


**Error response example (400 - Bad Request):**

```javascript
{
    "code": 400,
    "message": "The room should a valid size"
}
```
### Persistence of simulations

All request are persisted to a MongoDB database. 

If the request was successful, the simulation input and output will be stored at collection
'SimulationResult':

```javascript
{
        "_id" : ObjectId("59ac4ad090f1521cecc4a65a"),
        "input" : {
                "roomSize" : [
                        5,
                        5
                ],
                "coords" : [
                        1,
                        2
                ],
                "patches" : [
                        [
                                1,
                                0
                        ],
                        [
                                2,
                                2
                        ],
                        [
                                2,
                                3
                        ]
                ],
                "instructions" : "NNESEESWNWW"
        },
        "output" : {
                "coords" : [
                        1,
                        3
                ],
                "patches" : 1
        }
}
```
If the request returned a code different than 201 - Created, the simulation input and the error will be
stored in collection 'SimulationError':

```javascript
{
        "_id" : ObjectId("59ac48c290f152264cb8ebaf"),
        "input" : {
                "roomSize" : [
                        0,
                        0
                ],
                "coords" : [
                        1,
                        1
                ],
                "patches" : [ ],
                "instructions" : ""
        },
        "error" : {
                "code" : 400,
                "message" : "The room should a valid size"
        }
}
```
