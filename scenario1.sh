#!/bin/bash

javac *.java

for graph in ./graphs/*.txt
	do
		java ScenarioOne "$graph"
	done
