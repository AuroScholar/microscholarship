package com.auro.scholr.payment.domain;

import com.auro.scholr.core.common.AppConstant;
import com.auro.scholr.core.common.ValidationModel;
import com.auro.scholr.home.data.model.DemographicResModel;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PaymentUseCase {

    public ValidationModel isValidIFSCode(String str)
    {
        // Regex to check valid IFSC Code.
        String regex = "^[A-Z]{4}0[A-Z0-9]{6}$";


        if (str == null) {
            return new ValidationModel(false, AppConstant.ifscCode.EMPTYSTRING);
        }

        // Pattern class contains matcher()
        // method to find matching between
        // the given string and
        // the regular expression.
        if(!Pattern.matches(regex,  str.toString())){
            return new ValidationModel(false, AppConstant.ifscCode.VALISDIFSCCODE);
        }


        // Return if the string
        // matched the ReGex
        return new ValidationModel(true, AppConstant.ifscCode.VALID);
    }

    public  ValidationModel isValidBankAccountNumber(String accountnumber,String ifscCode,String account2){
        // Regex to check valid IFSC Code.
        String regex = "[0-9]{9,18}";


        // If the string is empty
        // return false
        if (accountnumber == null) {
            return new ValidationModel(false, AppConstant.bankAccountNumber.EMPTYSTRING);
        }




        if(!Pattern.matches(regex,  accountnumber.toString())){
            return new ValidationModel(false, AppConstant.bankAccountNumber.VALIDACCOUNTNUMBER);
        }

        if(!Pattern.matches(regex,  account2.toString())){
            return new ValidationModel(false, AppConstant.bankAccountNumber.VALIDCONFIRMACCOUNTNUMBER);
        }

        if(!accountnumber.equals(account2)){
            return new ValidationModel(false, AppConstant.bankAccountNumber.BANKACCOUNTMATCH);
        }


        // Regex to check valid IFSC Code.
        String regexifsc = "^[A-Z]{4}0[A-Z0-9]{6}$";


        if (ifscCode == null) {
            return new ValidationModel(false, AppConstant.ifscCode.EMPTYSTRING);
        }

        // Pattern class contains matcher()
        // method to find matching between
        // the given string and
        // the regular expression.
        if(!Pattern.matches(regexifsc,  ifscCode.toString())){
            return new ValidationModel(false, AppConstant.ifscCode.VALISDIFSCCODE);
        }





        // Return if the string
        // matched the ReGex
        return new ValidationModel(true, AppConstant.bankAccountNumber.VALID);
    }

    public ValidationModel isVlaidPhoneNumber(String phonenumber){

        String regex = "[a-zA-Z]+";

        // If the string is empty
        // return false
        if (phonenumber.isEmpty()) {
            return new ValidationModel(false, AppConstant.phoneNumber.EMPTYSTRING);
        }

        if(phonenumber.length() < 9 && phonenumber.length() >= 10 ){

            return new ValidationModel(false, AppConstant.phoneNumber.VALIDPHONENUMBER);
        }
/*
        if(Pattern.matches(regex,  phonenumber.toString())){
            return new ValidationModel(false, AppConstant.phoneNumber.VALIDPHONENUMBER);
        }
*/

        // Return if the string
        // matched the ReGex
        return new ValidationModel(true, AppConstant.phoneNumber.VALID);

    }

    public ValidationModel isVlaidMatchBankAccount(String account1,String account2){
        if(!account1.equals(account2)){
            return new ValidationModel(false, AppConstant.bankAccountNumber.BANKACCOUNTMATCH);
        }
        return new ValidationModel(true, AppConstant.bankAccountNumber.BANKACCOUNTMATCH);

    }



}
