for i in {1..100} ; do
    /Applications/Intel\ Power\ Gadget/Powerlog -file note-app-jit/results-"$i".csv -cmd /usr/local/bin/pypy3 main.py
done
