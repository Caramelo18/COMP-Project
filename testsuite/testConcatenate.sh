rm *.class
rm faops/*.class
jjtree FAOPS.jjt
javacc FAOPS.jj
javac -cp "gs-core-1.3.jar:" *.java */*.java
java -cp gs-core-1.3.jar: FAOPS testConcatenate.faops
dot -Tpng testConcatenate.dot -o testConcatenate.png
