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

Description: 
The Data Processing Application (DPA) is a tool designed to make
it easier to work with network packet capture (PCAP) files containing
dedicated short-range communication (DSRC) data from connected vehicle (CV)
applications. To facilitate this end, the DPA converts PCAP files containing
binary encoded DSRC messages such as Basic Safety Messages (BSM), Signal Phase
and Timing (SPaT) messages, and Geographic Intersection Description (GID/MAP)
messages, into a more human (and machine) readable format known as comma
separate values (CSV). These CSV files can be opened by many programs such as
Microsoft Excel or Microsoft Notepad as they are simply text files that adhere
to the CSV specified format.


Installation and removal instructions:
The DMP depends on the Java Runtime Environment (JRE) version 1.8 or higher.
The JRE is available from Oracle’s website at
http://www.oracle.com/technetwork/java/index.html. Find the version of the JRE
appropriate for your operating system (DMP is designed for Windows 7 64 bit)
and download and install it. It is also necessary to install the Gradle build
tool. You can then use Gradle to download the necessary dependencies and
compile the code by using the:

gradle shadowJar

command in your command prompt while in the CVDMP source root folder.
This will produce a file called dataparser-1.1.0-all.jar file under the
build/libs directory. This is the compiled, executable version of the CVDMP.

The DPA can be installed in any folder that the user has permission to write
to. Simply copy the aforementioned executable JAR file into whichever
directory you would like to install it to.

To remove the CVDMP simply delete the JAR file. 

License information
-------------------
See the accompanying LICENSE file.


System Requirements 
------------------------- 
Minimum memory: 500 MB
Processing power: 2 GHz, single core 
Connectivity: none 
Hardware supported: Intel-compatible 
Operating systems supported: Windows 7, 64-bit 
Performance will increase with additional memory and CPU cores, as it is
designed for parallel processing.

Documentation
------------- 
The software is packaged with a Word-based User Guide that is available 
through the Help menu.
