package com.midas.app.config;

import com.midas.app.activities.AccountActivity;
import com.midas.app.activities.AccountActivityImpl;
import com.midas.app.repositories.AccountRepository;
import com.midas.app.worker.TemporalWorker;
import lombok.Setter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Setter
public class TemporalConfig {

  @Bean
  public TemporalWorker temporalWorker(AccountRepository accountRepository) {
    return new TemporalWorker(accountActivity(accountRepository));
  }

  @Bean
  public AccountActivity accountActivity(AccountRepository accountRepository) {
    return new AccountActivityImpl(accountRepository);
  }
}
