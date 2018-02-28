#!/usr/bin/env bash

echo Creating Executables
mvnw clean flyway:clean package -U -P !unit-tests,!integration-tests

if [ "$#" -e 0 ];
then
    echo "**************************************************"
    echo "* Starting Log Watch CLI with Default parameters *"
    echo "**************************************************"
    java -jar ./logwatch-cli/target/logwatch-boot-cli.jar --accessLog=access.log --startDate=2017-01-01.13:00:00 --duration=hourly --threshold=100
else
    echo "**************************"
    echo "* Starting Log Watch CLI *"
    echo "**************************"
fi

java -jar ./logwatch-cli/target/logwatch-boot-cli.jar $1 $2 $3 $4

echo ........
echo FINISHED
echo ........