# Bugs

1. CreateCustomer json have line:

    `" \t\"idNumber\": \" " + randomPhoneNumber + "\" \n"`
    
    Json tag name and expected value name does not match. IdNumber not referring to phone number.
    
2. CreateCustomer request json has typo:

   `+ " \"acitivityCode\": \"EMPLOYEE\", \n" typo`
   
   acitivityCode I thing have to be activityCode
   
3. account-api and person-api too unstable and give no response.
4. Test server gives technical error too much. Can't test against api.

