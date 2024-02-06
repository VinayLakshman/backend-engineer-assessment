package com.midas.app.activities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.midas.app.models.Account;
import com.midas.app.repositories.AccountRepository;
import com.stripe.model.Customer;
import com.stripe.param.CustomerCreateParams;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {AccountActivityTest.class})
public class AccountActivityTest {

  MockedStatic<Customer> staticCustomer;

  Account account;

  @Mock AccountRepository accountRepository;

  Customer mockCustomer;

  AccountActivity accountActivity;

  @BeforeEach
  public void setup() {
    account = new Account();
    account.setFirstName("Josh");
    account.setLastName("Mathew");
    account.setEmail("joshm@test.co");

    mockCustomer = new Customer();
    mockCustomer.setName("Josh Mathew");
    mockCustomer.setEmail("joshm@test.co");

    staticCustomer = Mockito.mockStatic(Customer.class);
    staticCustomer
        .when(() -> Customer.create(any(CustomerCreateParams.class)))
        .thenReturn(mockCustomer);

    when(accountRepository.save(any(Account.class))).thenReturn(account);

    accountActivity = new AccountActivityImpl(accountRepository);
  }

  @AfterEach
  public void close() {
    if (!staticCustomer.isClosed()) {
      staticCustomer.close();
    }
  }

  @Test
  public void getCreatePaymentAccount() {
    Account actual = accountActivity.createPaymentAccount(account);

    assertEquals(mockCustomer.getEmail(), actual.getEmail());
    assertEquals(mockCustomer.getName().split(" ")[0], actual.getFirstName());
    assertEquals(mockCustomer.getName().split(" ")[1], actual.getLastName());
  }

  @Test
  public void getSaveAccount() {
    Account actual = accountActivity.saveAccount(account);

    assertEquals(account.getFirstName(), actual.getFirstName());
    assertEquals(account.getLastName(), actual.getLastName());
    assertEquals(account.getEmail(), actual.getEmail());
  }
}
