for i in {1..100} ; do
    /Applications/Intel\ Power\ Gadget/Powerlog -file pow-jit/results-"$i".csv -cmd /Users/vicdan/Library/Java/JavaVirtualMachines/graalvm-ce-19/Contents/Home/bin/java -XX:+UnlockExperimentalVMOptions -XX:+EnableJVMCI -XX:+UseJVMCICompiler -cp ./build/libs/proof-of-work-1.0-SNAPSHOT.jar org.example.MainKt
done