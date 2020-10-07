package com.myfarmnow.myfarmcrop.network;


import com.myfarmnow.myfarmcrop.activities.DashboardActivity;
import com.myfarmnow.myfarmcrop.models.merchants_model.MerchantData;
import com.myfarmnow.myfarmcrop.models.news_model.all_news.NewsData;
import com.myfarmnow.myfarmcrop.models.retrofitResponses.BalanceResponse;
import com.myfarmnow.myfarmcrop.models.retrofitResponses.InitiateTransferResponse;
import com.myfarmnow.myfarmcrop.models.retrofitResponses.LoanListResponse;
import com.myfarmnow.myfarmcrop.models.retrofitResponses.LoanPayResponse;
import com.myfarmnow.myfarmcrop.models.retrofitResponses.MerchantInfoResponse;
import com.myfarmnow.myfarmcrop.models.retrofitResponses.RequestLoanresponse;
import com.myfarmnow.myfarmcrop.models.retrofitResponses.TokenResponse;
import com.myfarmnow.myfarmcrop.models.retrofitResponses.WalletAuthentication;
import com.myfarmnow.myfarmcrop.models.retrofitResponses.WalletLoanAddPicResponse;
import com.myfarmnow.myfarmcrop.models.retrofitResponses.WalletPurchaseConfirmResponse;
import com.myfarmnow.myfarmcrop.models.retrofitResponses.WalletPurchaseResponse;
import com.myfarmnow.myfarmcrop.models.retrofitResponses.WalletTransactionReceiptResponse;
import com.myfarmnow.myfarmcrop.models.retrofitResponses.WalletTransactionResponse;
import com.myfarmnow.myfarmcrop.models.retrofitResponses.WalletUserRegistration;
import com.myfarmnow.myfarmcrop.models.user_model.UserData;
import com.myfarmnow.myfarmcrop.models.wallet.LoanApplication;
import com.myfarmnow.myfarmcrop.models.wallet.WalletPurchase;
import com.myfarmnow.myfarmcrop.models.wallet.WalletTransaction;

import org.json.JSONObject;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.GET;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;


/**
 * APIRequests contains all the Network Request Methods with relevant API Endpoints
 **/

public interface APIRequests {
    //******************** News Data ********************//


    //Update User
    @POST("update/{id}/{oldPassword}")
    Call<UserData> update(@Field("id") String id,
                          @Field("firstname") String firstname,
                          @Field("lastname") String lastname,
                          @Field("country") String country,
                          @Field("addressCountry") String addressCountry,
                          @Field("addressStreet") String addressStreet,
                          @Field("addressCityOrTown") String addressCityOrTown,
                          @Field("email") String email,
                          @Field("farmname") String farmname,
                          @Field("countryCode") String countryCode,
                          @Field("oldPassword") String oldPassword,
                          @Field("latitude") String latitude,
                          @Field("longitude") String longitude,
                          @Field("password") String password
    );

    /**************  WALLET REQUESTS *******************************/
    //wallet authentication
    @FormUrlEncoded
    @POST("emaishawallet/user/authenticate")
    Call<WalletAuthentication> authenticate(@Field("email") String email,
                                            @Field("password") String password
    );

    //wallet registration
    @FormUrlEncoded
    @POST("emaishawallet/user/create")
    Call<WalletUserRegistration> create(@Field("firstname") String firstname,
                                        @Field("lastname") String lastname,
                                        @Field("email") String email,
                                        @Field("password") String password,
                                        @Field("phoneNumber") String phoneNumber,
                                        @Field("addressStreet") String addressStreet,
                                        @Field("addressCityOrTown") String addressCityOrTown

    );

    //refresh token
    @FormUrlEncoded
    @POST("wallet/token/get")
    Call<TokenResponse> getToken(
            @Field("email") String email,
            @Field("password") String password


    );

    //request balance

    @GET("wallet/balance/request")
    Call<BalanceResponse> requestBalance(@Header("Authorization") String token

    );

    //initiate transfer
    @FormUrlEncoded
    @POST("wallet/transfer/initiate")
    Call<InitiateTransferResponse> initiateTransfer(@Header("Authorization") String token,
                                                    @Field("amount") Double amount,
                                                    @Field("receiverPhoneNumber") String receiverPhoneNumber


    );

    //wallet transaction list
    @GET("wallet/transactions/list")
    Call<WalletTransactionResponse> transactionList(@Header("Authorization") String token);

    //make transaction
    @FormUrlEncoded
    @Headers({"Accept: application/json"})
    @POST("wallet/payments/merchant")
    Call<WalletPurchaseResponse> makeTransaction(@Header("Authorization") String token,
                                                 @Field("merchantId") int merchantId,
                                                 @Field("amount") Double amount,
                                                 @Field("coupon") String coupon

    );

    //confirm payment
    @FormUrlEncoded
    @POST("wallet/payments/comfirm_paymerchant")
    Call<WalletPurchaseConfirmResponse> confirmPayment(
            @Field("merchantId") int merchantId,
            @Field("amount") double amount,
            @Field("coupon") String coupon


    );

    //get merchant information
    @GET("wallet/merchant/{merchantId}")
    Call<MerchantInfoResponse> getMerchant(@Header("Authorization") String token,
                                           @Path("merchantId") int merchantId

    );

    //get merchant receipt
    @GET("/wallet/payments/receipt/{referenceNumber}")
    Call<WalletTransactionReceiptResponse> getReceipt(@Header("Authorization") String token,
                                                      @Path("referenceNumber") String referenceNumber

    );

    //get user loans
    @GET("wallet/loan/user/loans")
    Call<LoanListResponse> getUserLoans(@Field("userId") String userId,
                                        @Header("Authorization") String token

    );

    //request loans
    @POST("wallet/loan/user/request")
    Call<RequestLoanresponse> requestLoans(@Header("Authorization") String token,
                                           @Field("userId") String userId,
                                           @Field("amount") double amount,
                                           @Field("duration") int duration,
                                           @Field("loanType") String loanType

    );

    //add loan photos
    @POST("wallet/loan/user/photos/add")
    Call<WalletLoanAddPicResponse> addLoanPhotos(@Header("Authorization") String token,
                                                 JSONObject object

    );

    //get wallet user by phone
    @GET("wallet/user/get/receiver_by_phone/{phoneNumber}")
    Call<UserData> getWalletUser(@Path("phoneNumber") String phoneNumber

    );

    //create user credit
    @GET("wallet/flutter/payment/credituser")
    Call<WalletTransaction> creditUser(@Header("Authorization") String token,
                                       @Field("email") String email,
                                       @Field("amount") Double amount,
                                       @Field("referenceNumber") String referenceNumber

    );

    //voucher deposit
    @FormUrlEncoded
    @POST("wallet/payment/voucherdeposit")
    Call<UserData> voucherDeposit(@Header("Authorization") String token,
                                  @Field("email") String email,
                                  @Field("phoneNumber") String phoneNumber,
                                  @Field("codeEntered") String codeEntered


    );

    //loan pay
    @FormUrlEncoded
    @Headers({"Accept: application/json"})
    @POST("wallet/payments/loanpay")
    Call<LoanPayResponse> loanPay(@Header("Authorization") String token,
                                  @Field("amount") double amount,
                                  @Field("userId") String userId);
}
