# Yoti - Lampkicking

Technical Java test for Backend Java developer at Yoti. The application runs using Spring Boot in 
port 9001 (port can be configured in 'src/resources/application.yml'), with a Webservice.

## Software requirements

* Maven (min. v2)

## Getting started

* **Building application**: mvn clean install
* **Integration tests execution**: mvn install -P integration
* **Running the application**: mvn spring-boot:run

## What it does

Web service that simulates the moves of a robotic hoover inside of a rectangular room. It determines how many patches of dirt
don't get cleaned during those moves and where the robot stops. 

Using a Cartesian coordinate system, the top bottom left corner of the room is (0,0) and top right corner is (N,M), where N represents the
width and M the height. The room will be rectangular, no obstacles (except the room walls), no doors.

Placing the hoover on a patch of dirt ("hoovering") removes the patch of dirt and it is considered cleaned. The hoover is always on - 
there is no need to enable it. Driving into a wall has no effect (the robot skids in place).

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

Example:

```javascript
{
  "coords" : [1, 3],
  "patches" : 1
}
```
