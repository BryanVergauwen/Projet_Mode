#!/bin/bash

find "./src" | while read path
do
	if [[ $path == *".java" ]]
	then
		echo compiling $path "in bin ..."
		javac -cp ".:bin:ressources/bdd/sqliteJdbc.jar" -d bin $path
	fi
done
