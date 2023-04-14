import json
import base64
from algosdk.v2client import algod
from algosdk import mnemonic
from algosdk.transaction import PaymentTxn

def send_note():
    # Use sandbox or your address and token
    algod_address = "http://localhost:4001"
    algod_token = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
    algod_client = algod.AlgodClient(algod_token, algod_address)

    passphrase = "<your-25-word-mnemonic>"
    private_key = mnemonic.to_private_key(passphrase)
    my_address = mnemonic.word(passphrase)
    print("My address: {}".format(my_address))
    params = algod_client.suggested_params()
    # comment out the next two (2) lines to use suggested fees
    params.flat_fee = True
    params.fee = 1000
    note = '{"firstName":"John", "LastName":"Doe"}'.encode()
    receiver = "GD64YIY3TWGDMCNPP553DZPPR6LDUSFQOIJVFDPPXWEG3FVOJCCDBBHU5A"

    unsigned_txn = PaymentTxn(my_address, params, receiver, 1000000, None, note)

    # sign transaction
    signed_txn = unsigned_txn.sign(private_key)

send_note()