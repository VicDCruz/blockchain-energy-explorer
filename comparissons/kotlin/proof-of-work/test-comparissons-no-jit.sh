for i in {1..100} ; do
    /Applications/Intel\ Power\ Gadget/Powerlog -file pow-no-jit/results-"$i".csv -cmd /Users/vicdan/Library/Java/JavaVirtualMachines/graalvm-ce-19/Contents/Home/bin/java -cp ./target/proof-of-work-1.0-SNAPSHOT.jar org.codehaus.mojo.MainKt
done