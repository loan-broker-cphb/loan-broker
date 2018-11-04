package com.loanbroker.credit.score;

import creditbureau.*;

public class GetCreditScore {

//    public static void main(String[] args) {
//        System.out.println(getCreditScore("110292-1234"));
//        System.out.println(getCreditScore("110292-1234"));
//        System.out.println(getCreditScore("110292-1234"));
//        System.out.println(getCreditScore("110292-1234"));
//    }

    public int getCreditScore(String ssn){
        int creditScore = 0;
        try{
            CreditScoreService_Service service = new CreditScoreService_Service();
            CreditScoreService port = service.getCreditScoreServicePort();
            creditScore = port.creditScore(ssn);
        }catch(Exception e){
            System.out.println(e);
        }
        return creditScore;
    }

}
