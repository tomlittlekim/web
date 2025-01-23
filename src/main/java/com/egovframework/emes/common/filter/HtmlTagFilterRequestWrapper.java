package com.egovframework.emes.common.filter;

import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import org.apache.commons.text.StringEscapeUtils;

public class HtmlTagFilterRequestWrapper extends HttpServletRequestWrapper {

  public HtmlTagFilterRequestWrapper(HttpServletRequest request) {
    super(request);
  }

  @Override
  public String[] getParameterValues(String parameter) {
    String[] values = super.getParameterValues(parameter);

    if (values == null) {
      return null;
    }

    return Arrays.stream(values)
        .map(this::escapeHtml)
        .toArray(String[]::new);
  }

  @Override
  public String getParameter(String parameter) {
    String value = super.getParameter(parameter);

    return value == null ? null : escapeHtml(value);
  }

  /**
   * HTML 특수 문자를 이스케이프 처리
   *
   * @param input 입력 문자열
   * @return 이스케이프된 문자열
   */
  private String escapeHtml(String input) {
    return StringEscapeUtils.escapeHtml4(input);
  }
}
