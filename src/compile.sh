rm *.class
rm faops/*.class
jjtree FAOPS.jjt
javacc FAOPS.jj
javac -cp "gs-core-1.3.jar:" *.java */*.java
jar -cvfm FAOPS.jar manifest.mf -C ./ .
