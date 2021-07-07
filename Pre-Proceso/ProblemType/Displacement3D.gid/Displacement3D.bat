@ECHO OFF

del %2\%1.boh
del %2\%1.post.res
del %2\%1.post.dat
del %2\%1.err

rem OutputFile: %2\%1.boh
rem ErrorFile: %2\%1.err

java -jar %3\Displacement3D.jar %2\%1
