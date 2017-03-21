package com.tdt.musicapp.service.braintree;

import com.braintreegateway.BraintreeGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("tdBraintreeService")
public class TdBraintreeService {

    @Autowired
    private org.springframework.core.env.Environment env;

    public BraintreeGateway getBraintreeGateway() {
        return new BraintreeGateway(
                com.braintreegateway.Environment.SANDBOX,
                env.getProperty("braintree.merchantId"),
                env.getProperty("braintree.publicKey"),
                env.getProperty("braintree.privateKey")
        );
    }

}
