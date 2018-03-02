_Log Watch CLI_
---------------

For convenience, all deliverable artifacts are placed in the "./deliverables" folder.
The source code is this entire project, and is organized into three maven
modules. 
    
    logwatch-data, logwatch-files, logwatch-boot-cli

### Instructions

1. If you haven't already, install a local MySQL server available on port "3306", 
or which ever port you wish to configure.
    
1. Modify the contents of "./logwatch-boot-cli/src/main/resources/application.yml"
to contain the root user and password for your local MySQL server.

1. Modify the root "pom.xml" to contain the users names and passwords for your local
MySQL server.

1. From the command line, or terminal, enter the following from the root project
directory.

Windows
    
    run-parser.cmd
  
Linux

    sudo chmod +x ./run-parser.sh    
    ./run-parser.sh
