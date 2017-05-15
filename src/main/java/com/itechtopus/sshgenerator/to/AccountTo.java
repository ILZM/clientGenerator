package com.itechtopus.sshgenerator.to;

import com.itechtopus.sshgenerator.generator.Util;
import com.itechtopus.sshgenerator.model.ClientAccount;
import com.itechtopus.sshgenerator.model.ClientAccountTransaction;

import java.util.List;

public class AccountTo {

  public ClientAccount account;

  public List<ClientAccountTransaction> transactions = Util.newList();
}
