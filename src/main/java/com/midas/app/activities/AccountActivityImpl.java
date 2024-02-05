package com.midas.app.activities;

import com.midas.app.enums.ProdviderType;
import com.midas.app.models.Account;
import com.midas.app.repositories.AccountRepository;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.param.CustomerCreateParams;
import io.temporal.spring.boot.ActivityImpl;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ActivityImpl
@RequiredArgsConstructor
public class AccountActivityImpl implements AccountActivity {

  private final Logger logger = LoggerFactory.getLogger(AccountActivityImpl.class);

  private final AccountRepository accountRepository;

  @Override
  public Account saveAccount(Account account) {
    return accountRepository.save(account);
  }

  @Override
  public Account createPaymentAccount(Account account) {
    CustomerCreateParams params =
        CustomerCreateParams.builder()
            .setName(account.getFirstName() + " " + account.getLastName())
            .setEmail(account.getEmail())
            .build();
    Customer customer;
    try {
      customer = Customer.create(params);
      account.setProviderId(customer.getId());
      account.setProdviderType(ProdviderType.STRIPE);
    } catch (StripeException e) {
      logger.error("Error while trying to create customer account: {}", e);
    }
    return account;
  }
}
