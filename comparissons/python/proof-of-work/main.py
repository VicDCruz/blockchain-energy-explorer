import hashlib
import time

nonce = 0
data = "Hello, world!"


def mine(prefix: int, genesis_hash: str):
    global nonce
    millis = round(time.time() * 1000)
    hash_string = calculate_hash(genesis_hash, millis)
    prefix_string = '0' * prefix

    while hash_string[0:prefix] != prefix_string:
        nonce += 1
        hash_string = calculate_hash(genesis_hash, millis)

    print(f"Mined Block Hash: {hash_string} with (nonce: {nonce})")


def calculate_hash(genesis, milliseconds):
    return hashlib.sha256(f"{genesis}{milliseconds}${nonce}${data}".encode('utf-8')).hexdigest()


if __name__ == '__main__':
    mine(6, 'e7wM2iSaaNssTJcHZRGaZYTEoSibEm6sqDdNm')
