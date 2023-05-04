for i in {1..100} ; do
    /Applications/Intel\ Power\ Gadget/Powerlog -file pow-no-jit/results-"$i".csv -cmd docker-compose up --scale pow-no-jit=4 pow-no-jit
done