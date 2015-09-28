#!/usr/bin/ksh

export LD_LIBRARY_PATH=$LD_LIBRARY_PATH:lib/solaris/

### If you are to process a large input matrix:
### java -Xmx1000M (for 1GB reserved Memory)          
###                                            
### If you have problems with starting java:
### specify your java Version (e. g. java-1.5.0_04 -classpath "BicAT_v2...)

java -classpath "BicAT_v2.22.jar:./lib/jcommon-0.9.6.jar:./lib/junit.jar:./lib/jcommon-0.9.6.jar:./lib/servlet.jar:./lib/jfreechart-0.9.21.jar:" bicat.gui.BicatGui
