# FHWA Connected Vehicle DSRC Message Processor

## What is it?
The FHWA Connected Vehicle DSRC Message Processor (DMP) is a Java
application that provides an easy-to-use user interface for converting PCAP
formatted CV data captures into easier to work with CSV files. This enables
users to utilize the data stored in BSM, SPaT, and MAP messages recorded in
those PCAP files without having to deal with the minutia of processing that
data.

## How do I use it?
Simply install the Java 8 JRE, install Gradle, build the executable jar, and 
double click. From there you select an input folder containing PCAP files and 
an output folder where you wish the output CSVs to be saved. Then click "Start 
Processing" and the DMP will run in the background and notify you when 
processing is complete.

## How is it built?
The DMP is programmed in Java 8 and makes use of the Spring libraries for
dependency injection and configuration. The build system in use is Gradle with
the Java, application, maven, and John Rengelman's Shadow plugins. Javadoc is
provided for each method and class and describes the overall functionality and
role in the system.

*[FHWA]: Federal Highway Administration
*[DMP]: DSRC Message Processor
*[DSRC]: Dedicated Short-range Radio Communications
*[CV]: Connected Vehicle
*[PCAP]: packet capture
*[BSM]: Basic Safety Message
*[SPaT]: Signal Phase and Timing
*[MAP]: Intersection Geometry Message
