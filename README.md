# Clip Project

Create an API that must be have the following operations:

## Make/Accept a transaction:
Payload example:
{
amount: This is the money you debit from the card-holder user,
clip_user: This is the clip-user that’s going to receive the money,
card_data: This is the card information where the API is debiting the money.
date: This is the timestamp at the moment the payment is sent.
}

## REQUEST POST
http://localhost:8080/api/clip/createPayload/

BODY

{
  "amount": "12.00s",
  "cardData": {
    "cardType": "Debit",
    "cardNumber": "1234123412341134",
    "expMonth" : "12",
    "expYear" : "2021"
  },
   "clipUser": {
    "userName": "clip_user1"
  }
}


## Get transactions by clip_user:

This endpoint needs to retrieve all transactions received for a clip_user.
Make a disbursement:
This operation needs to get all transactions grouped by clip_user and represented as a new entity that represents the amount of money that the api may deposit to the clip_user.
Example:
transaction1: 10 pesos - clip_user: clippy
transaction2: p15 esos - clip_user: clippy
transaction3: 20 pesos - clip_user: clippy2

## REQUEST GET

http://localhost:8080/api/clip/transactions/clip_user1


## If i make a call to the make_disbursement endpoint it should retrieve the following:
disbursement1: 25 pesos - clip_user:clippy [transaction1+transaction2] 
disbursement2: 20 pesos - clip_user: clippy2 [transaction3]

## REQUEST POST

http://localhost:8080/api/clip/createDisbursement

BODY
{
    "userName": "clip_user1"
  }


## Get disbursements:
This endpoint needs to retrieve all disbursements by a clip_user.
Example:
disbursement1: 10 pesos - clip_user1 - date: 08/03/21 
disbursement2: 15 pesos - clip_user1 - date: 09/03/21
disbursement3: 10 pesos - clip_user3 - date: 07/02/20

Date means at what time the disbursement is created.
Consider that once a transaction is already included in a disbursement it’s not going to be included in any other disbursement by no mean

## REQUEST GET
http://localhost:8080/api/clip/disbursements/clip_user1

## Starting 🚀

### Building 🔧

mvn clean install

## Run 📦

mvn exec:java -Dexec.mainClass=com.example.clip.ClipApplication

