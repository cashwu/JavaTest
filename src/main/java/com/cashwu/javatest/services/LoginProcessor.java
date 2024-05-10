package com.cashwu.javatest.services;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
@RequestScope
public class LoginProcessor {

  private String username;
  private String password;

  public boolean login() {
    String username = this.getUsername();
    String password = this.getPassword();

      return "cash".equals(username) && "pw-1234".equals(password);
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
