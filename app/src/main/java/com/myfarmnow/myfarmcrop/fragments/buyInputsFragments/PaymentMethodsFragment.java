package com.myfarmnow.myfarmcrop.fragments.buyInputsFragments;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Toast;

import com.braintreepayments.api.BraintreeFragment;
import com.braintreepayments.api.Card;
import com.braintreepayments.api.exceptions.BraintreeError;
import com.braintreepayments.api.exceptions.ErrorWithResponse;
import com.braintreepayments.api.interfaces.BraintreeCancelListener;
import com.braintreepayments.api.interfaces.BraintreeErrorListener;
import com.braintreepayments.api.interfaces.ConfigurationListener;
import com.braintreepayments.api.interfaces.PaymentMethodNonceCreatedListener;
import com.braintreepayments.api.models.CardBuilder;
import com.braintreepayments.api.models.Configuration;
import com.braintreepayments.api.models.PaymentMethodNonce;
import com.braintreepayments.cardform.utils.CardType;
import com.braintreepayments.cardform.view.SupportedCardTypesView;
import com.myfarmnow.myfarmcrop.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class PaymentMethodsFragment extends Fragment {
    private View rootView;
    private RadioButton cashOnDelivery,eMaishaWallet,eMaishaCard,Visa,MobileMoney;
    private LinearLayout merchantCard,VisaCard,MobileM;
    private EditText cardNumber,cardExpiry,cvv;
    Button continuePayment;
    private static final CardType[] SUPPORTED_CARD_TYPES = {CardType.VISA, CardType.MASTERCARD, CardType.MAESTRO,
            CardType.UNIONPAY, CardType.AMEX};
    CardType cardType;
    private BraintreeFragment braintreeFragment;
    SupportedCardTypesView braintreeSupportedCards;
    private  String selectedPaymentMethod;
    private  CardBuilder braintreeCard;
    private String paymentNonceToken = "";



    public PaymentMethodsFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_payment_methods, container, false);
        // Inflate the layout for this fragment


        cashOnDelivery = rootView.findViewById(R.id.radio_btn_cash_on_delivery);
        eMaishaWallet = rootView.findViewById(R.id.radio_btn_emaisha_wallet);
        eMaishaCard = rootView.findViewById(R.id.radio_btn_merchant_card);
        Visa = rootView.findViewById(R.id.radio_btn_visa_card);
        MobileMoney = rootView.findViewById(R.id.radio_btn_mobile_money);
        merchantCard = rootView.findViewById(R.id.layout_merchant_card_details);
        VisaCard = rootView.findViewById(R.id.layout_visa_card_details);
        MobileM = rootView.findViewById(R.id.layout_mobile_money_number);
        continuePayment = rootView.findViewById(R.id.btn_payment_methods);
        cardExpiry = rootView.findViewById(R.id.visa_card_expiry);
        cardNumber = rootView.findViewById(R.id.txt_visa_card_number);
        cvv = rootView.findViewById(R.id.visa_card_cvv);
        braintreeSupportedCards = rootView.findViewById(R.id.supported_card_types);



        cashOnDelivery.setOnClickListener(v -> {
            MobileMoney.setChecked(false);
            eMaishaWallet.setChecked(false);
            eMaishaCard.setChecked(false);
            Visa.setChecked(false);
            MobileM.setVisibility(v.GONE);
            VisaCard.setVisibility(v.GONE);
            merchantCard.setVisibility(v.GONE);

        });
        eMaishaWallet.setOnClickListener(v -> {
            cashOnDelivery.setChecked(false);
            MobileMoney.setChecked(false);
            eMaishaCard.setChecked(false);
            Visa.setChecked(false);
            MobileM.setVisibility(v.GONE);
            VisaCard.setVisibility(v.GONE);
            merchantCard.setVisibility(v.GONE);
        });

        eMaishaCard.setOnClickListener(v ->{
            merchantCard.setVisibility(v.VISIBLE);
            cashOnDelivery.setChecked(false);
            eMaishaWallet.setChecked(false);
            MobileMoney.setChecked(false);
            Visa.setChecked(false);
            MobileM.setVisibility(v.GONE);
            VisaCard.setVisibility(v.GONE);

        });

        Visa.setOnClickListener(v ->{
            VisaCard.setVisibility(v.VISIBLE);
            cashOnDelivery.setChecked(false);
            eMaishaWallet.setChecked(false);
            eMaishaCard.setChecked(false);
            MobileMoney.setChecked(false);
            MobileM.setVisibility(v.GONE);
            merchantCard.setVisibility(v.GONE);
        });

        MobileMoney.setOnClickListener(v->{
            MobileM.setVisibility(v.VISIBLE);
            cashOnDelivery.setChecked(false);
            eMaishaWallet.setChecked(false);
            eMaishaCard.setChecked(false);
            Visa.setChecked(false);
            VisaCard.setVisibility(v.GONE);
            merchantCard.setVisibility(v.GONE);
        });

        braintreeSupportedCards.setSupportedCardTypes(SUPPORTED_CARD_TYPES);
        cardExpiry.setKeyListener(null);
        cardNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(cardNumber.getText().toString().trim())) {
                    CardType type = CardType.forCardNumber(cardNumber.getText().toString());
                    if (cardType != type) {
                        cardType = type;

                        InputFilter[] filters = {new InputFilter.LengthFilter(cardType.getMaxCardLength())};
                        cardNumber.setFilters(filters);
                        cardNumber.invalidate();

                        braintreeSupportedCards.setSelected(cardType);
                    }
                } else {
                    braintreeSupportedCards.setSupportedCardTypes(SUPPORTED_CARD_TYPES);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        cardExpiry.setOnTouchListener((v, event) -> {

            if (event.getAction() == MotionEvent.ACTION_UP) {
                // Get Calendar instance
                final Calendar calendar = Calendar.getInstance();

                // Initialize DateSetListener of DatePickerDialog
                DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        // Set the selected Date Info to Calendar instance
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, monthOfYear);

                        // Set Date Format
                        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/yyyy", Locale.US);

                        // Set Date in input_dob EditText
                        cardExpiry.setText(dateFormat.format(calendar.getTime()));
                    }
                };


                // Initialize DatePickerDialog
                DatePickerDialog datePicker = new DatePickerDialog
                        (
                                getContext(),
                                date,
                                calendar.get(Calendar.YEAR),
                                calendar.get(Calendar.MONTH),
                                calendar.get(Calendar.DAY_OF_MONTH)
                        );

                // Show datePicker Dialog
                datePicker.show();
            }

            return false;
        });


            continuePayment.setOnClickListener(v->{
          if(cashOnDelivery.isChecked()){
              //navigate to thank you
              selectedPaymentMethod = "cash On Delivery";


          }else if(eMaishaWallet.isChecked()){
              //
            selectedPaymentMethod = "eMaisha Wallet";
          }
          else if(eMaishaCard.isChecked()){
              //
              selectedPaymentMethod = "eMaisha Card";
              validateSelectedPaymentMethod();
            }else if(Visa.isChecked()){
              //save card info
              selectedPaymentMethod = "Visa";
              validateSelectedPaymentMethod();

          }else if(MobileMoney.isChecked()){
              selectedPaymentMethod = "Mobile Money";
          }
        });




        return rootView;


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


    private void validateSelectedPaymentMethod() {

        if (selectedPaymentMethod.equalsIgnoreCase("Visa")) {

            // Initialize BraintreeCard
            braintreeCard = new CardBuilder()
                    .cardNumber(cardNumber.getText().toString().trim())
                    .expirationDate(cardExpiry.getText().toString().trim())
                    .cvv(cvv.getText().toString().trim());

            // Tokenize BraintreeCard
            Card.tokenize(braintreeFragment, braintreeCard);


            // Add PaymentMethodNonceCreatedListener to BraintreeFragment
            braintreeFragment.addListener(new PaymentMethodNonceCreatedListener() {
                @Override
                public void onPaymentMethodNonceCreated(PaymentMethodNonce paymentMethodNonce) {

                    // Get Payment Nonce
                    paymentNonceToken = paymentMethodNonce.getNonce();
                }
            });


            // Add BraintreeErrorListener to BraintreeFragment
            braintreeFragment.addListener(new BraintreeErrorListener() {
                @Override
                public void onError(Exception error) {

                    // Check if there was a Validation Error of provided Data
                    if (error instanceof ErrorWithResponse) {
                        ErrorWithResponse errorWithResponse = (ErrorWithResponse) error;

                        BraintreeError cardNumberError = errorWithResponse.errorFor("number");
                        BraintreeError cardCVVErrors = errorWithResponse.errorFor("creditCard");
                        BraintreeError expirationMonthError = errorWithResponse.errorFor("expirationMonth");
                        BraintreeError expirationYearError = errorWithResponse.errorFor("expirationYear");

                        // Check if there is an Issue with the Credit Card
                        if (cardNumberError != null) {
                            cardNumber.setError(cardNumberError.getMessage());
                        } else if (expirationMonthError != null) {
                            cardExpiry.setError(expirationMonthError.getMessage());
                        } else if (expirationYearError != null) {
                            cardExpiry.setError(expirationYearError.getMessage());
                        } else if (cardCVVErrors != null) {
                            cvv.setError(cardCVVErrors.getMessage());
                        } else {
                            Toast.makeText(getContext(), errorWithResponse.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });


            // Add ConfigurationListener to BraintreeFragment
            braintreeFragment.addListener(new ConfigurationListener() {
                @Override
                public void onConfigurationFetched(Configuration configuration) {
                }
            });

            // Add BraintreeCancelListener to BraintreeFragment
            braintreeFragment.addListener(new BraintreeCancelListener() {
                @Override
                public void onCancel(int requestCode) {
                }
            });


        }
         else if (selectedPaymentMethod.equalsIgnoreCase("paypal")) {
            return;

        } else if (selectedPaymentMethod.equalsIgnoreCase("instamojo")) {
            return;

        } else {
            return;
        }

    }
}