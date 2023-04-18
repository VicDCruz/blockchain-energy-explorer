for i in {1..100} ; do
    /Applications/Intel\ Power\ Gadget/Powerlog -file pow-no-jit/results-"$i".csv -cmd /Users/vicdan/Library/Java/JavaVirtualMachines/graalvm-ce-19/Contents/Home/bin/java -cp ./target/note-app-algorand-1.0-SNAPSHOT-jar-with-dependencies.jar mx.unam.cruz.victor.notes.NoteField
done