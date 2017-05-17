package com.itechtopus.sshgenerator.listener;

import javax.servlet.*;
import java.io.IOException;

public class EcodingFilter implements Filter {
  @Override
  public void init(FilterConfig filterConfig) throws ServletException {

  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    request.setCharacterEncoding("UTF-8");
    chain.doFilter(request, response);
  }

  @Override
  public void destroy() {

  }
}
