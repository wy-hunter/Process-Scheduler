SHELL := powershell.exe
.SHELLFLAGS := -NoProfile -Command

JAVA := "$(JAVA_HOME)\bin\java.exe"
JAVAC := "$(JAVA_HOME)\bin\javac.exe"
JAR := "$(JAVA_HOME)\bin\jar.exe"

testJava:  clean
	javac *.java
	java Packaging
	
everything:clean
	javac *.java	
	jar cvfe  part1.jar schedulerTest *.class
	java -jar part1.jar

clean: 
	-rm *.class
jarFiles: 
	javac *.java
	& $(JAR) cvfe part4.jar Packaging *.class
runjar: 
	java -jar part4.jar