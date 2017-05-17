package com.itechtopus.sshgenerator.servlet;

import com.itechtopus.sshgenerator.generator.AllInfoGenerator;
import com.itechtopus.sshgenerator.storage.MainStorage;
import com.itechtopus.sshgenerator.storage.Parameters;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ParametersServlet extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    request.setAttribute("OUTPUT_SCHEDULER_PERIOD", Parameters.OUTPUT_SCHEDULER_PERIOD);
    request.setAttribute("TRANSACTIONS_PER_ITERATION", Parameters.TRANSACTIONS_PER_ITERATION);
    request.setAttribute("ACCOUNT_GENERATION_PERIOD", Parameters.ACCOUNT_GENERATION_PERIOD);
    request.setAttribute("CLIENT_GENERATION_PERIOD", Parameters.CLIENT_GENERATION_PERIOD);
    request.setAttribute("GENERATE_DUPLICATES", Parameters.GENERATE_DUPLICATES);
    request.setAttribute("SCHEDULER_PERIOD", Parameters.SCHEDULER_PERIOD);
    request.setAttribute("DUPLICATES_GENERATION_PERIOD", Parameters.DUPLICATES_GENERATION_PERIOD);
    request.setAttribute("generatedClients", AllInfoGenerator.get().clientPIS.size());
    request.setAttribute("generatedAccounts", AllInfoGenerator.get().accountMap.size());
    request.getRequestDispatcher("/WEB-INF/parameters.jsp").forward(request, response);
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String value = request.getParameter("OUTPUT_SCHEDULER_PERIOD");
    if (value != null && !value.isEmpty())
      Parameters.OUTPUT_SCHEDULER_PERIOD = Integer.valueOf(value);
    value = request.getParameter("TRANSACTIONS_PER_ITERATION");
    if (value != null && !value.isEmpty())
      Parameters.TRANSACTIONS_PER_ITERATION = Integer.valueOf(value);
    value = request.getParameter("ACCOUNT_GENERATION_PERIOD");
    if (value != null && !value.isEmpty())
      Parameters.ACCOUNT_GENERATION_PERIOD = Integer.valueOf(value);
    value = request.getParameter("CLIENT_GENERATION_PERIOD");
    if (value != null && !value.isEmpty())
      Parameters.CLIENT_GENERATION_PERIOD = Integer.valueOf(value);
    value = request.getParameter("GENERATE_DUPLICATES");
    if (value != null && !value.isEmpty())
      Parameters.GENERATE_DUPLICATES = Boolean.valueOf(value);
    value = request.getParameter("SCHEDULER_PERIOD");
    if (value != null && !value.isEmpty())
      Parameters.SCHEDULER_PERIOD = Integer.valueOf(value);
    value = request.getParameter("DUPLICATES_GENERATION_PERIOD");
    if (value != null && !value.isEmpty())
      Parameters.DUPLICATES_GENERATION_PERIOD = Integer.valueOf(value);

    doGet(request, response);

  }
}
