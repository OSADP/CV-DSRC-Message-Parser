Open Source Overview
============================
Connected Vehicle DSRC Message Parser (DMP)
Version 1.1.0

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

Web sites
---------
The software is distributed through the USDOT's JPO Open Source Application 
Development Portal (OSADP) http://itsforge.net/ 
