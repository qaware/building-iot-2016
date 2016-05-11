# building-iot
Source code for the [talk at the building IoT conference 2016](http://www.buildingiot.de/veranstaltung-4984-die-leichtigkeit-des-seins%3A-bindings-f%C3%BCr-eclipse-smarthome-entwickeln.html).

## Before you start

* Create an OAuth access token for the Nest webservice and store it in [NestBindingConstants.java](https://github.com/phxql/building-iot/blob/master/org.eclipse.smarthome.binding.nest/src/main/java/org/eclipse/smarthome/binding/nest/NestBindingConstants.java). Otherwise the binding will not work!

## Building

* Run `mvnw clean install` in the `org.eclipse.smarthome.binding.nest` folder.