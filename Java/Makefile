#
#  This Makefile is designed to compile the starter code and create 4700crawler.
#  Usage:  make build -f Makefile
#
#  Students must submit a separate Makefile with their submission that compiles 
#  their code. Whether the 'crawler' script is created by the Makefile is 
#  the developer's decision. 

JFLAGS = -g
JC = javac
JAR = jar
JAVA = java

CLASSES = Crawler.java
JAR_FILE = crawler.jar

default: build

%.class: %.java
	$(JC) $(JFLAGS) $<

build: $(CLASSES:.java=.class)
	@echo "Creating manifest"
	echo "Main-Class: Crawler" > manifest.txt
	@echo "Building JAR file $(JAR_FILE)"
	$(JAR) cfm $(JAR_FILE) manifest.txt *.class
	@echo "Creating executable script '4700crawler'"
	echo '#!/bin/bash\n$(JAVA) -jar $(JAR_FILE) "$$@"' > crawler
	chmod +x crawler

clean:
	@echo "Cleaning up..."
	rm -f *.class manifest.txt crawler $(JAR_FILE)


