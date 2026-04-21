testJava:  clean
	javac *.java
	java schedulerTest
	
everything:clean
	javac *.java	
	jar cvfe  part1.jar schedulerTest *.class
	java -jar part1.jar


clean: 
	-rm *.class
jarFiles: 
	jar cvfe  part1.jar schedulerTest *.class

runjar: 
	java -jar part1.jar