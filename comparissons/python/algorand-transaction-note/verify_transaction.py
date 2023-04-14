def verify_transaction():
    print("txID: {}".format(txid), " confirmed in round: {}".format(
        confirmed_txn.get("confirmed-round", 0)))
    print("Transaction information: {}".format(
        json.dumps(confirmed_txn, indent=2)))
    print("Decoded note: {}".format(base64.b64decode(
        confirmed_txn["txn"]["txn"]["note"]).decode()))
    person_dict = json.loads(base64.b64decode(
        confirmed_txn["txn"]["txn"]["note"]).decode())
    print("First Name = {}".format(person_dict['firstName']))