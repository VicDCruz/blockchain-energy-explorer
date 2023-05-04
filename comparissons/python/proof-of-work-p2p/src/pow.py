import hashlib


class Miner:
    def __init__(self):
        self.nonce = 0

    def mine(self, prefix: int, data: str, millis, queue):
        hash_string = calculate_hash(data, millis, self.nonce)
        prefix_string = '0' * prefix

        while hash_string[0:prefix] != prefix_string:
            self.nonce += 1
            hash_string = calculate_hash(data, millis, self.nonce)

        print(f"Mined Block Hash: {hash_string} with (nonce: {self.nonce})")
        queue.put(self.nonce)
        queue.put(hash_string)


def calculate_hash(data, milliseconds, nonce):
    return hashlib.sha256(f"{milliseconds}{nonce}{data}".encode('utf-8')).hexdigest()


def verify_hash(hash, milliseconds, nonce, id):
    return hashlib.sha256(f"{milliseconds}{nonce}{id}".encode("utf-8")).hexdigest() == hash
