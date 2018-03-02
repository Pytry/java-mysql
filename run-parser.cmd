@echo off

echo Creating Executables
call mvnw.cmd clean flyway:clean package --quiet -U -P "!unit-tests,!integration-tests"

set argC=0
for %%x in (%*) do Set /A argC+=1
if "%argC%"=="0" (
    echo ********************
    echo Running Test Case 1:
    echo ********************
    echo "java -jar ./logwatch-boot-cli/target/logwatch-boot-cli.jar --accessLog=access.log --startDate=2017-01-01.13:00:00 --duration=hourly --threshold=100"
    echo Parsing and Saving the "access.log" file. This could take a little while ...
    java -jar ./logwatch-boot-cli/target/logwatch-boot-cli.jar --accessLog=access.log --startDate=2017-01-01.13:00:00 --duration=hourly --threshold=100

    echo ********************
    echo Running Test Case 2:
    echo ********************
    echo "java -jar ./logwatch-boot-cli/target/logwatch-boot-cli.jar --startDate=2017-01-01.13:00:00 --duration=hourly --threshold=100"
    java -jar ./logwatch-boot-cli/target/logwatch-boot-cli.jar --startDate=2017-01-01.13:00:00 --duration=hourly --threshold=100

    echo ********************
    echo Running Test Case 3:
    echo ********************
    echo "java -jar ./logwatch-boot-cli/target/logwatch-boot-cli.jar --startDate=2017-01-01.13:00:00 --duration=daily --threshold=250"
    java -jar ./logwatch-boot-cli/target/logwatch-boot-cli.jar --startDate=2017-01-01.13:00:00 --duration=daily --threshold=250

    echo ********************
    echo Running Test Case 4:
    echo ********************
    echo "java -jar ./logwatch-boot-cli/target/logwatch-boot-cli.jar --ipv4=192.168.234.82"
    java -jar ./logwatch-boot-cli/target/logwatch-boot-cli.jar --ipv4=192.168.234.82

    GOTO FINISHED
)
echo *********************************************
echo Starting Log Watch CLI with Custom Parameters
echo *********************************************
echo "java -jar ./logwatch-boot-cli/target/logwatch-boot-cli.jar %1 %2 %3 %4 %5"
java -jar ./logwatch-boot-cli/target/logwatch-boot-cli.jar %1 %2 %3 %4 %5

:FINISHED
echo FINISHED
