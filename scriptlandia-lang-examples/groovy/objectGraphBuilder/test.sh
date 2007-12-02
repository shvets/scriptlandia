#!/bin/sh

mkdir -p bin

# compile to bin dir
groovyc -d bin *.groovy

# run the test
groovy -cp bin ObjectGraphBuilderTests

