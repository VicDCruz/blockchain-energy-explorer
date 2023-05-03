for i in {1..100} ; do
    /Applications/Intel\ Power\ Gadget/Powerlog -file pow-jit/results-"$i".csv -cmd docker-compose up --scale pow-jit=4 pow-jit
done