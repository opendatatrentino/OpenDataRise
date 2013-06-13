@echo off
rem This is a modified copy of refine.bat file
rem It modifies the /d behaviour: the /d now let you to specify a data dir 
rem where refine store data
rem the /debug option let you to set the logging level
rem You have also the possibility to specify a default refineport (DEVPORT) and a default datadir (setting the DEVPATH variable).
rem This script let you also to run server TestNG test cases: use the server_test or server_tests option.
rem This script remove the data dir each time you start Refine and replace it with a default one (stored in \backup-data-dir
rem Created by azanella

SET DEVPORT=4444
SET DEVPATH=%USERPROFILE%\odr-dev-workspace\
rem ---------------
rem
rem Configuration variables
rem
rem ANT_HOME
rem   Home of Ant installation; copy is in the source as tools\apache-ant-*
rem
rem JAVA_HOME
rem   Home of Java installation.
rem
rem JAVA_OPTIONS
rem   Extra options to pass to the JVM
rem

if "%OS%"=="Windows_NT" @setlocal
if "%OS%"=="WINNT" @setlocal

rem --- First two utilities for exiting --------------------------------------------

goto endUtils

:usage
echo ====== development build / start script ========
echo Possibility to set default port and default workspace path at the top of the present batch
echo on variables 'DEVPORT' and 'DEVPATH'. These variables could be overwritten using /p and /d
echo command-line parameters.
echo.
echo.
echo Usage %0 [options] ^<action^>
echo where [options] include:
echo.
echo  "/?" print this message and exit
echo.
echo  "/p <port>" the port that OpenRefine will listen to
echo     default: %DEVPORT%
echo.
echo  "/i <interface>" the host interface OpenRefine should bind to
echo     default: 127.0.0.1
echo.
echo  "/w <path>" path to the webapp
echo     default src\main\webapp
echo.
echo  "/debug" enable JVM debugging (on port 8000)
echo.
echo  "/d <path>" path to the data directory. Default %DEVPATH%
echo.
echo  "/v <level>" verbosity level [from low to high: error,warn,info,debug,trace]
echo      default: info
echo.
echo  "/m <memory>" max memory heap size to use
echo      default: 1024M
echo.
echo  "/x" enable JMX monitoring (for jconsole and friends)
echo.
echo  "/keepdatadir" keep the existing data dir. Do not replace with
echo  \backup-data-dir
echo.
echo "and <action> is one of
echo.
echo   build ..................... Build OpenRefine
echo   run ....................... Run OpenRefine
echo.
echo   clean ..................... Clean compiled classes
echo   distclean ................. Remove all generated files
echo   server_test ..................... Run TestNG server test
echo   server_tests ..................... Run TestNG server test
echo.
goto end

:fail
echo Type 'refine /h' for usage.
goto end

:endUtils

rem --- Read ini file -----------------------------------------------

set OPTS=

for /f "tokens=1,* delims==" %%a in (refine.ini) do (
    set %%a=%%b
)

rem --- Check JAVA_HOME ---------------------------------------------

if not "%JAVA_HOME%" == "" goto gotJavaHome
echo You must set JAVA_HOME to point at your Java Development Kit installation
echo.
echo If you don't know how to do this, follow the instructions at
echo.
echo   http://bit.ly/1c2gkR
echo.

goto fail
:gotJavaHome

rem --- Default value definition mod. azanella 	14/05/2013 ----

if not "%DEVPORT%" == "" set REFINE_PORT=%DEVPORT%
if not "%DEVPATH%" == "" set REFINE_DATA_DIR=%DEVPATH%


rem --- Argument parsing --------------------------------------------

:loop

if ""%1"" == """" goto endArgumentParsing
if ""%1"" == ""/h"" goto usage
if ""%1"" == ""/?"" goto usage
if ""%1"" == ""/p"" goto arg-p
if ""%1"" == ""/i"" goto arg-i
if ""%1"" == ""/w"" goto arg-w
REM BEGIN EDIT BY azanella on 2013/05/10
if ""%1"" == ""/debug"" goto arg-debug
if ""%1"" == ""/d"" goto arg-d
if ""%1"" == ""/v"" goto arg-v
REM END EDIT BY azanella on 2013/05/10
REM BEGIN EDIT BY azanella on 2013/06/13
if ""%1"" == ""/keepdatadir"" goto arg-keep
REM END EDIT BY azanella on 2013/06/13
if ""%1"" == ""/m"" goto arg-m
if ""%1"" == ""/x"" goto arg-x
goto endArgumentParsing

:arg-p
set REFINE_PORT=%2
goto shift2loop

:arg-i
set REFINE_HOST=%2
goto shift2loop

:arg-w
set REFINE_WEBAPP=%2
goto shift2loop

:arg-m
set REFINE_MEMORY=%2
goto shift2loop

REM EDIT BY azanella on 2013/05/10
:arg-debug
set OPTS=%OPTS% -Xdebug -Xrunjdwp:transport=dt_socket,address=8000,server=y,suspend=n
goto shift2loop

REM ADDED BY azanella on 2013/05/10
:arg-d
echo entro1
set REFINE_DATA_DIR=%2
goto shift2loop

REM ADDED BY azanella on 2013/05/10
:arg-v
set REFINE_VERBOSITY=%2
goto shift2loop

REM ADDED BY azanella on 2013/06/13
:arg-keep
set ODR_KEEPDATADIR="1"
goto shift2loop

:arg-x
set OPTS=%OPTS% -Dcom.sun.management.jmxremote
goto shift2loop

:shift2loop
shift
shift
goto loop

:endArgumentParsing


rem --- Fold in Environment Vars --------------------------------------------

if not "%JAVA_OPTIONS%" == "" goto gotJavaOptions
set JAVA_OPTIONS=
:gotJavaOptions
set OPTS=%OPTS% %JAVA_OPTIONS%

if not "%REFINE_MEMORY%" == "" goto gotMemory
set REFINE_MEMORY=1024M
:gotMemory
set OPTS=%OPTS% -Xms256M -Xmx%REFINE_MEMORY% -Drefine.memory=%REFINE_MEMORY%

REM ADDED BY azanella on 2013/05/10
if not "%REFINE_DATA_DIR%" == "" set OPTS=%OPTS% -Drefine.data_dir=%REFINE_DATA_DIR%
if not "%REFINE_VERBOSITY%" == "" set OPTS=%OPTS% -Drefine.verbosity=%REFINE_VERBOSITY%
REM added by azanella on May, 22 2013
if "%REFINE_TEST_DIR%"=="" set REFINE_TEST_DIR=main\tests

REM added by azanella on 2013/06/13
if not "%ODR_KEEPDATADIR%" == "" goto endRemove
rd /S /Q %REFINE_DATA_DIR%
xcopy /E /I /Q backup-data-dir %REFINE_DATA_DIR%
:endRemove

if not "%REFINE_PORT%" == "" goto gotPort
set REFINE_PORT=3333
:gotPort
set OPTS=%OPTS% -Drefine.port=%REFINE_PORT%

if not "%REFINE_HOST%" == "" goto gotHost
set REFINE_HOST=127.0.0.1
:gotHost
set OPTS=%OPTS% -Drefine.host=%REFINE_HOST%

if not "%REFINE_WEBAPP%" == "" goto gotWebApp
set REFINE_WEBAPP=main\webapp
:gotWebApp
set OPTS=%OPTS% -Drefine.webapp=%REFINE_WEBAPP%

if not "%REFINE_CLASSES_DIR%" == "" goto gotClassesDir
set REFINE_CLASSES_DIR=server\classes
:gotClassesDir

if not "%REFINE_LIB_DIR%" == "" goto gotLibDir
set REFINE_LIB_DIR=server\lib
:gotLibDir

rem ----- Respond to the action ----------------------------------------------------------

set ACTION=%1

if ""%ACTION%"" == ""build"" goto doAnt
if ""%ACTION%"" == ""clean"" goto doAnt
if ""%ACTION%"" == ""distclean"" goto doAnt
if ""%ACTION%"" == ""run"" goto doRun
rem Added by azanella on May, 22 2013
if ""%ACTION%"" == ""server_test"" goto doSrvTests
if ""%ACTION%"" == ""server_tests"" goto doSrvTests


:doRun
set CLASSPATH="%REFINE_CLASSES_DIR%;%REFINE_LIB_DIR%\*"
"%JAVA_HOME%\bin\java.exe" -cp %CLASSPATH% %OPTS% -Djava.library.path=%REFINE_LIB_DIR%/native/windows com.google.refine.Refine
goto end

REM Added by azanella on May 22, 2013
:doSrvTests
call "%ANT_HOME%\bin\ant.bat" -f build.xml build_tests
set CLASSPATH="%REFINE_TEST_DIR%\server\classes;%REFINE_WEBAPP%/WEB-INF\classes;%REFINE_CLASSES_DIR%;%REFINE_TEST_DIR%\server\lib\*;%REFINE_LIB_DIR%\*;%REFINE_WEBAPP%\WEB-INF\lib\*"
set TESTS=-excludegroups broken %REFINE_TEST_DIR%\server\conf\tests.xml
set RUN_CMD="%JAVA_HOME%\bin\java.exe" -cp %CLASSPATH% %OPTS% org.testng.TestNG -d %REFINE_BUILD_DIR%\server_tests -listener org.testng.reporters.DotTestListener %TESTS%
rem echo %RUN_CMD%
%RUN_CMD%
goto end

:doAnt
if not "%ANT_HOME%" == "" goto gotAntHome
echo You must have Apache Ant installed and the ANT_HOME environment variable to point to it
echo.
echo You can download it from
echo.
echo   http://ant.apache.org/
echo.
echo If you don't know how to set environment variables, follow the instructions at
echo.
echo   http://bit.ly/1c2gkR
echo.
:gotAntHome
"%ANT_HOME%\bin\ant.bat" -f build.xml %ACTION%
goto end

:end