#!/bin/bash

javac *.java

for density in 0.001 0.005 0.01 0.02 0.04 0.08 0.16 0.32 0.64
	do
		java SubsetGenerator facebook_combined.txt 1000 "$density"
		mv "$density"_1000_facebook_combined.txt "$density"_1000_facebook_combined1.txt
		java SubsetGenerator facebook_combined.txt 1000 "$density"
		mv "$density"_1000_facebook_combined.txt "$density"_1000_facebook_combined2.txt
		java SubsetGenerator facebook_combined.txt 1000 "$density"
		mv "$density"_1000_facebook_combined.txt "$density"_1000_facebook_combined3.txt

	done
