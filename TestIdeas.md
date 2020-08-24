1 CreateAccount
* Write test to json request using wrong values, upper/lower cases, value missing, special characters
* Create account to person not exists.
* Create account to person whos account is closed
* Create account to person who is in blacklist.
* Create account to the minor.
* Create account to the foreign person without id code.
* Create account when no authentication token.
* Create account when authentication token is corrupted
* Create account when authentication token is expired
* 

2 CreateTransactionsTests
* Write test to json request using wrong values, upper/lower cases, value missing, special characters
* Write test when amount negative, zero
* Write tests different currencyCodes. Not supported currencyCodes. 
* Write tests different transactionTypeCodes
* Write tests effectiveDate in past, in future, wrong format.


3 GetBalancesTest
* Write tests to json request
* Validate response values.
* Get balance when auth token and accountId do not belong to same person.


4 InitializeConfirmPaymentsTest
* Write tests to json request
* Validate response values.
* Confirm when accountId payments paymentId different users.