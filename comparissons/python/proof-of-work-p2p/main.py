import json
import socket
import sys
import time
from datetime import datetime
from multiprocessing import Process, Queue

from src.my_own_p2p_node import MyOwnPeer2PeerNode
from src.pow import Miner, verify_hash

queue = Queue(2)


def finish():
    time.sleep(100)


def choose_winner(inbound_messages):
    valid_hashes = [h for h in inbound_messages if verify_hash(h["hash"], h["millis"], h["nonce"], h["id"])]
    min_date = datetime.now()
    min_id = ""
    for vh in valid_hashes:
        fromisoformat = datetime.fromisoformat(vh["sent_at"])
        if min_date > fromisoformat:
            min_date = fromisoformat
            min_id = vh["id"]
    print(f"Winner is {min_id}")


if __name__ == '__main__':
    ip_address = socket.gethostbyname(socket.gethostname())
    print(f"IP address: {ip_address}")

    node = MyOwnPeer2PeerNode(ip_address, 10001)
    node.start()
    time.sleep(5)

    millis = round(time.time() * 1000)
    miner = Miner()
    proc = Process(target=miner.mine, args=(5, node.id, millis, queue))
    proc.start()

    for i in range(int(sys.argv[1]) + 1):
        print(f"Connecting to {ip_address[0:-1] + str(2 + i)}:10001")
        node.connect_with_node(ip_address[0:-1] + str(2 + i), 10001)

    proc.join()
    nonce = queue.get()
    hash2 = queue.get()
    data = {"hash": hash2, "millis": millis, "nonce": nonce, "id": node.id,
            "sent_at": datetime.now().isoformat()}

    node.send_to_nodes({"message": json.dumps(data)})

    time.sleep(5)  # Create here your main loop of the application

    limit = 0
    while node.get_total_inbound_messages() < node.get_total_inbounds_connected() and limit < 20:
        print(f"Still not there {node.get_total_inbound_messages()}/{node.get_total_inbounds_connected()}...")
        limit += 1
    choose_winner(node.inbound_messages)
    node.stop()
    queue.close()
