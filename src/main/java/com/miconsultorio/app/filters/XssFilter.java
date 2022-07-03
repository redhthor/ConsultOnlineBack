package com.miconsultorio.app.filters;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

/**
 * Description: Cross-site request prevention
 *
 * @author
 *
 */
@WebFilter(filterName = "xssFilter", urlPatterns = "/*", asyncSupported = true)
public class XssFilter implements Filter {

 
  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
	  // TO-DO
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    XssHttpServletRequestWrapper xssRequest = new XssHttpServletRequestWrapper((HttpServletRequest) request);
    chain.doFilter(xssRequest, response);

  }

  @Override
  public void destroy() {
	  // TO-DO
  }

}