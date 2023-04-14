import json

from algosdk import mnemonic
from algosdk.transaction import *

from wait_for_confirmation import wait_for_confirmation

import pandas as pd

def get_algod_client():
    # Use sandbox or your address and token
    algod_address = "https://testnet-api.algonode.cloud"
    algod_token = ""
    algod_client = algod.AlgodClient(algod_token, algod_address)
    return algod_client



def get_accounts_from_csv():
    return pd.read_csv('account.csv').to_numpy()


def send_note():
    algod_client = get_algod_client()
    accounts = get_accounts_from_csv()

    passphrase = accounts[0][1]
    private_key = mnemonic.to_private_key(passphrase)
    my_address = accounts[0][0]
    print("My address: {}".format(my_address))
    params = algod_client.suggested_params()
    # comment out the next two (2) lines to use suggested fees
    params.flat_fee = True
    params.fee = constants.MIN_TXN_FEE
    note = '{"firstName":"JohnChris", "lastName":"Doe"}'.encode()
    # note = 'JohnChris'.encode()
    receiver = accounts[1][0]

    unsigned_txn = PaymentTxn(my_address, params, receiver, 100000, None, note)

    # sign transaction
    signed_txn = unsigned_txn.sign(private_key)

    # wait for confirmation
    try:
        # send transaction
        txid = algod_client.send_transaction(signed_txn)
        print("Send transaction with txID: {}".format(txid))
        confirmed_txn = wait_for_confirmation(algod_client, txid, 4)
    except Exception as err:
        print(err)
        return

    print("txID: {}".format(txid), " confirmed in round: {}".format(
        confirmed_txn.get("confirmed-round", 0)))
    print("Transaction information: {}".format(
        json.dumps(confirmed_txn, indent=2)))
    print("Decoded note: {}".format(base64.b64decode(
        confirmed_txn["txn"]["txn"]["note"]).decode()))
    person_dict = json.loads(base64.b64decode(
        confirmed_txn["txn"]["txn"]["note"]).decode())
    print("First Name = {}".format(person_dict['firstName']))


send_note()
