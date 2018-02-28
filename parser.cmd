@echo off

echo Creating Executables
call mvnw.cmd clean flyway:clean package -U -P !unit-tests,!integration-tests

set argC=0
for %%x in (%*) do Set /A argC+=1
if "%argC%"=="0" (
    echo ............................................
    echo Starting Log Watch CLI with default testData
    echo ............................................
    java -jar ./logwatch-cli/target/logwatch-boot-cli.jar --accessLog=access.log --startDate=2017-01-01.13:00:00 --duration=hourly --threshold=100
    GOTO FINISHED
)
echo ...................................................
echo Starting Log Watch CLI with parameters: %1 %2 %3 %4
echo ...................................................
java -jar ./logwatch-cli/target/logwatch-boot-cli.jar %1 %2 %3 %4

:FINISHED
echo ........
echo FINISHED
echo ........