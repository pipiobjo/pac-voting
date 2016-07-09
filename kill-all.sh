#!/usr/bin/env bash

set -e

cd voting-assembly/target/voting-assembly-*-bin/pids


for file in *.pid; do 
	PID=$(cat "$file");
	echo "PID=$PID"
	
	if ps -p $PID > /dev/null
	then 
	    kill $PID
	    sleep 5
	    	if ps -p $PID > /dev/null
		then 
		    kill -9 $PID >/dev/null 2>&1 
		fi
	else
	    echo "PID=$PID already stopped"
	fi



#	while kill -0 $PID
#	do
#	  kill $PID
#	  sleep 15
#	  kill -9 $PID >/dev/null 2>&1 
#	done
	rm "$file"
	
done
