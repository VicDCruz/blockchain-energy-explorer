from Network import Network

from helpers.terminal_helper import print_colored

node2 = Network("192.168.0.108")
node2.start(5052)

print("SERVER-3 (SPAIN)")
print_colored("PORT 5052 is started active Please enter a key to continue", "green")
input()

node2.connectToNode("192.168.0.108", 5050)

"""node2.connectToNode("192.168.56.1",5055)
node2.connectToNode("192.168.56.1",5056)

"""

# node2.join_network("192.168.0.108", 5050)
# randomNode = node2.askRandNode("192.168.56.1",5050)


data = 0
while True:
    data = input()

    node2.broadcast(data)
