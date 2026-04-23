SHELL := powershell.exe
.SHELLFLAGS := -NoProfile -Command

JAVA := "$(JAVA_HOME)\bin\java.exe"
JAVAC := "$(JAVA_HOME)\bin\javac.exe"
JAR := "$(JAVA_HOME)\bin\jar.exe"

testJava:  clean
	javac *.java
	java schedulerTestCWRR

clean: 
	-rm *.class
jarFiles: 
	javac *.java
	& $(JAR) cvfe part2.jar schedulerTestCWRR *.class
runjar: 
	java -jar part2.jar