def send_transaction():
    # sign transaction
    signed_txn = unsigned_txn.sign(private_key)
    # send transaction
    txid = algod_client.send_transaction(signed_txn)
    print("Send transaction with txID: {}".format(txid))

    # wait for confirmation
    try:
        confirmed_txn = wait_for_confirmation(algod_client, txid, 4)
    except Exception as err:
        print(err)
        return