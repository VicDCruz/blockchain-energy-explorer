for i in {1..100} ; do
    /Applications/Intel\ Power\ Gadget/Powerlog -file pow-no-jit/results-"$i".csv -cmd /usr/bin/python3 main.py
done
