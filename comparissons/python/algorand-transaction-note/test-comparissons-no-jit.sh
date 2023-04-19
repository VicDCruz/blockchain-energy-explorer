for i in {1..100} ; do
    /Applications/Intel\ Power\ Gadget/Powerlog -file note-app-no-jit/results-"$i".csv -cmd ./venv/bin/python main.py
done
