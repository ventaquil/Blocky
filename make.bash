#!/bin/bash

verbose=false
execute=false

function write {
    if $verbose
    then
        echo $1
    fi
}

path="."

if [ $# -gt 0 ]
then
    for (( i=1; $i<=$#; i=$i+1 ))
    do
        if [ "${!i}" == "-v" ]
        then
            verbose=true
        elif [ "${!i}" == "-e" ]
        then
            execute=true
        else
            path=${!i}
        fi
    done
fi

if [ `ls -1 | grep class | wc -l` -gt 0 ]
then
    write "> rm *.class"
    rm *.class
fi

if [ `ls -1 | grep jar | wc -l` -gt 0 ]
then
    write "> rm *.jar"
    rm *.jar
fi

if [ "${path:${#path}-1:1}" == "/" ]
then
    path=${path:0:${#path}-1}
fi

write "> javac Blocky.java"
javac Blocky.java

write "> jar cfe Blocky.jar Blocky *.class resources/*"
jar cfe Blocky.jar Blocky *.class resources/*

write "> chmod +x Blocky.jar"
chmod +x Blocky.jar

if [ "$path" != "." ]
then
    write "> mv Blocky.jar \"$path/Blocky.jar\""
    mv Blocky.jar "$path/Blocky.jar"
fi

write "> rm *.class"
rm *.class

if $execute
then
    write "> java -jar \"$path/Blocky.jar\""
    java -jar "$path/Blocky.jar"
fi
