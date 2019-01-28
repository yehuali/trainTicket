package com.example.util.matcher;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 用于从{@link HttpServletRequest}中提取URI变量的接口
 */
public interface RequestVariablesExtractor {
    /**
     * Extract URL template variables from the request.
     *
     * @param request the HttpServletRequest to obtain a URL to extract the variables from
     * @return the URL variables or empty if no variables are found
     */
    Map<String, String> extractUriTemplateVariables(HttpServletRequest request);
}
