#!/bin/sh
mkdir -p bin/classes
javac -d bin/classes src/*.java
dx --dex --debug --verbose --output=bin/Mtk.jar bin/classes/
adb push bin/Mtk.jar /sdcard/
mkdir -p output
adb shell dexdump -d /sdcard/Mtk.jar > output/dexdump.log
adb shell "cd /sdcard/; mkdir -p data; mkdir -p data/dalvik-cache"
DALVIK_OPTS=$@
adb shell "export ANDROID_DATA=/sdcard/data; cd /sdcard; logcat -c; dalvikvm -Xjitthreshold:1 -Xint:jit -Xjitverbose -Xjitclass:Ljava,Llibcore,Ldalvik,LMtk $DALVIK_OPTS -cp Mtk.jar Mtk"
adb shell logcat -d -s -b main -v tag dalvikvm:V > output/logcat.log
