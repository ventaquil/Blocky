#!/bin/bash
if [ `ls -1 | grep class | wc -l` -gt 0 ]
then
    rm *.class
fi

if [ `ls -1 | grep jar | wc -l` -gt 0 ]
then
    rm *.jar
fi

if [ $# -gt 0 ]
then
    path=$1
else
    path="."
fi

if [ "${path:${#path}-1:1}" == "/" ]
then
    path=${path:0:${#path}-1}
fi

javac Blocky.java
jar cfe Blocky.jar Blocky *.class resources/*

if [ "$path" != "." ]
then
    mv Blocky.jar "$path/Blocky.jar"
fi
