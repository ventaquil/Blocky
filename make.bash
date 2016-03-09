#!/bin/bash
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
jar cfe "$path/Blocky.jar" Blocky *

