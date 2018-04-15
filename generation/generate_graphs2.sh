#!/bin/bash

javac *.java

for density in 0.02
	do
		java SubsetGenerator facebook_combined.txt 1000 "$density"
		mv "$density"_1000_facebook_combined.txt "$density"_1000_facebook_combined1.txt
		java SubsetGenerator facebook_combined.txt 1000 "$density"
		mv "$density"_1000_facebook_combined.txt "$density"_1000_facebook_combined2.txt
		java SubsetGenerator facebook_combined.txt 1000 "$density"
		mv "$density"_1000_facebook_combined.txt "$density"_1000_facebook_combined3.txt

	done
