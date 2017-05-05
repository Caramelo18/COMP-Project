rm *.class
rm faops/*.class
javacc FAOPS.jj
javac -cp "gs-core-1.3.jar:" *.java
java -cp gs-core-1.3.jar: FAOPS
dot -Tpng exitTest.dot -o exitTest.png
