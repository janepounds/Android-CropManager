package com.myfarmnow.myfarmcrop.models.wallet;


public class ApiPaths {

    public final static String LOCAL_URL = "http://197.221.132.254";
    public final static String LIVE_SERVER_URL = "http://emaishawallet.myfarmnow.com";

    public final static String SERVER_URL = LIVE_SERVER_URL;

    //public final static String SERVER_URL = LOCAL_URL;

    public static final String Emaisha_Wallet_LOGIN_GET_ALL =  SERVER_URL + "/api/emaishawallet/user/authenticate";

    public static final String Wallet_CREATE_USER =  SERVER_URL + "/api/emaishawallet/user/create";


    //WALLET TRANSACTIONS
    public static final String WALLET_GET_TOKEN =  SERVER_URL + "/api/wallet/token/get";
    public static final String WALLET_GET_BALANCE =  SERVER_URL + "/api/wallet/balance/request";
    public static final String WALLET_INITIATE_MOMO_DEPOSIT =  SERVER_URL + "/api/wallet/momo/deposit/initiate";
    public static final String WALLET_INITIATE_MOMO_WITHDRAW =  SERVER_URL + "/api/wallet/momo/withdraw/initiate";
    public static final String WALLET_INITIATE_TRANSFER =  SERVER_URL + "/api/wallet/transfer/initiate";
    public static final String WALLET_MOMO_CHECK_WITHDRAW_STATUS =  SERVER_URL + "/api/wallet/momo/withdraw/status/check/";
    public static final String CROP_WALLET_MOMO_CHECK_DEPOSIT_STATUS =  SERVER_URL + "/api/wallet/momo/deposit/status/check/";
    public static final String WALLET_TRANSACTIONS_LIST =  SERVER_URL + "/api/wallet/transactions/list";
    public static final String WALLET_PAY_MERCHANT =  SERVER_URL + "/api/wallet/payments/merchant";
    public static final String WALLET_CPMFIRM_PAY_MERCHANT =  SERVER_URL + "/api/wallet/payments/comfirm_paymerchant";
    public static final String MERCHANT_GET_INFO =  SERVER_URL + "/api/wallet/merchant/";
    public static final String WALLET_CHECK_PENDING_TRANSACTIONS =  SERVER_URL + "/api/wallet/transactions/pending";
    public static final String WALLET_PAYMENTS_MERCHANT_RECEIPT =  SERVER_URL + "/api/wallet/payments/receipt/";
    public static final String WALLET_LOANS_LIST =  SERVER_URL + "/api/wallet/loan/user/loans/";
    public static final String WALLET_LOAN_APPLICATION_INITIATE =  SERVER_URL + "/api/wallet/loan/user/request";
    public static final String WALLET_LOAN_APPLICATION_ADD_PHOTOS =  SERVER_URL + "/api/wallet/loan/user/photos/add";
    public static final String WALLET_ADD_PROFILE_PHOTO =  SERVER_URL + "/api/wallet/user/profile_photos/add";
    public static final String WALLET_USER_PROFILE_PCTURE =  SERVER_URL + "/api/wallet/user/profile_photos/get";
    public static final String USER_GET_INFO_BY_PHONE =  SERVER_URL + "/api/wallet/user/get/receiver_by_phone/";
    public static final String WALLET_INITIATE_VISA_DEPOSIT =  SERVER_URL + "/api/wallet/dpo/payment/visadeposit";

    public static final String WALLET_INITIATE_MM_DEPOSIT =  SERVER_URL + "/api/wallet/flutter/payment/mobilemoney";
    public static final String WALLET_VERIFY_MM_DEPOSIT =  SERVER_URL + "/api/wallet/flutter/paymentverification/mobilemoney";
    public static final String WALLET_CREDIT_VISA_DEPOSIT =  SERVER_URL + "/api/wallet/flutter/payment/visa";
    public static final String WALLET_CREDIT_MOBILE_DEPOSIT =  SERVER_URL + "/api/wallet/beyonic/payment/mobile";

    public static final String WALLET_CREDIT_USER_DEPOSIT =  SERVER_URL + "/api/wallet/flutter/payment/credituser";

    public static final String WALLET_INITIATE_VOUCHER_DEPOSIT =  SERVER_URL + "/api/wallet/payment/voucherdeposit";
    public static final String WALLET_PAY_LOAN =  SERVER_URL + "/api/wallet/payments/loanpay";
    public static final String BEYONIC_PAYMENT_API_ENDPOINT = "https://app.beyonic.com/api/collectionrequests";
}
