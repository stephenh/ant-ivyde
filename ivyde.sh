#!/bin/bash

ivy=$(ls ../ivy/build/artifact/ | grep org.apache | tail -n 1)
ivyde_dir=$(ls work/dist | grep apache-ivyde | tail -n 1)

rm -f ivyde.zip
rm -fr ivyde
mkdir ivyde
mkdir ivyde/plugins
mkdir ivyde/features

echo "ivy = $ivy"
echo "ivyde = $ivyde_dir"

cp ../ivy/build/artifact/$ivy ivyde/plugins
cp work/dist/$ivyde_dir/plugins/* ivyde/plugins
cp work/dist/$ivyde_dir/features/* ivyde/features

jar cvf ivyde.zip ivyde/

# ls dist/ | grep dev.zip | grep -v md5 | grep -v sha | grep -v sources

