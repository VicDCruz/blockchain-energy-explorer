# Blockchain Energy Explorer
Discovery how much enery (Joules) do the programming languages (Java, Python) consume when running differente scenarios.
* Blockchain transactions
    * Algorand
    * Solana
* Consensus protocols
    * Proof of work (PoW)

# Requierements
1. Java 8+
1. Python 3
1. [Intel Power Gadget](https://www.intel.com/content/www/us/en/developer/articles/tool/power-gadget.html): Only works with Intel processors

## Optional
* Docker: Run the codes in a container

## How-to
All the codes to compare are found in the `comparissons` folder

1. Run the codes by the Intel Power Gadget suit command. This way to run an instance and get how many Joules does that program consume
    1. EG:
1. Other way, run the script attach to every comparisson
1. Another way, run the `dockerfiles`

# Graphs
Intel Power Gadget creates a result file that contains the information related to the power consumption and time execution of that process.

Use `energy-consumption-graphs` to get the graphs related to those reports.

1. Insert the files in `data` folder.
2. Run `main.py` to get the graph
