package com.itechtopus.sshgenerator.to;

import com.itechtopus.sshgenerator.generator.Util;
import com.itechtopus.sshgenerator.model.Client;
import com.itechtopus.sshgenerator.model.ClientAddr;
import com.itechtopus.sshgenerator.model.ClientPhone;
import com.itechtopus.sshgenerator.model.ModelParent;
import com.itechtopus.sshgenerator.model.enums.AddressType;

import java.util.List;
import java.util.Map;

// Client Particular Info

public class ClientPI extends ModelParent {

  public Client client;

  public Map<AddressType, ClientAddr> addresses = Util.newMap();

  public List<ClientPhone> phones = Util.newList();
}
