# middleware-solution
Middleware solution code as shown in Java One 2016.

There is four different projects into this solution, you can see the topology below:

![Solution Topology](img/Topology.png?raw=true "Solution Topology")

Please note that five services are involved: RobotSimulator, TranslationMiddleware, on-off-machine-times, show-data and a MongoDB.

## RobotSimulator
A Java ME project used to generate signals, you don't need the actual production system (machine, robot, ...) to generate signals to test your middleware.

You can mount the circut below to test it:

![RobotSimulator Schematic](img/RobotSimulatorSchematic.png?raw=true "RobotSimulator Schematic")

## TranslationMiddleware
A Java ME project used to translate machine signals to events that ERP can understand.

Yo can mount the circuit below to test it:

![TranslationMiddleware Schematic](img/TranslationMiddlewareSchematic.png?raw=true "TranslationMiddleware Schematic")

## on-off-machine-times
A Spring Boot REST application that receives events from Middleware, send them to a MongoDB and if requested return events translated to intervals. You can find a docker image of this service at docker hub.

# show-data
A Spring Boot Vaadin application that shows a report using data retrived from on-off-machine-times microservice. You can find a docker image of this service at docker hub.
