package com.example.IntercepterTest;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.ContentCachingRequestWrapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.logging.Logger;


@Component
public class APIInterceptor extends HandlerInterceptorAdapter {
    private static Logger log = Logger.getLogger(APIInterceptor.class.getName());

    // before controller
    // return false -> controller로 요청을 안함
    // Object ->핸들러 정보( RequestMapping , DefaultServletHandler )

    public boolean preHandle_test(HttpServletRequest req, HttpServletResponse res, Object obj) throws IOException {
        System.out.println("Interceptor Pre Handle!");

        /* request base */
        String method = req.getMethod();
        String url = req.getRequestURL().toString();
        String urlQuery = "";
        try {
            urlQuery = URLDecoder.decode(req.getQueryString(), "UTF-8");
        } catch (Exception e) {
        }
        /* request header */
        List<String> headers = new ArrayList<>();
        for (Enumeration<?> e = req.getHeaderNames(); e.hasMoreElements();) {
            String headerName = e.nextElement().toString();
            String headerValue = req.getHeader(headerName);

            headers.add(String.format("%s: %s", headerName, headerValue));
        }

        String reqStr = String.format(
                "#### General ####" +
                        "\nRequest Method: " + method +
                        "\nRequest URL: " + url +
                        "\nRequest Query: " + urlQuery +
                        "\nStatus Code: " + res.getStatus() +
                        "\n\n#### Request Headers ####\n" + String.join("\n", headers)
        );

        System.out.println(reqStr);

        return true;
    }

    //after Controller
    @Override
    public void postHandle(HttpServletRequest req, HttpServletResponse res, Object obj, ModelAndView mav) throws IOException {
        System.out.println("Interceptor Post Handle !");

    }
    /*
     *
     * */
    // view까지 처리가 끝난 후에 처리됨

    public void afterCompletiontest(HttpServletRequest req, HttpServletResponse res, Object obj, Exception ex) throws IOException {
        System.out.println("Interceptor all Fininsh");

        /* request base */
        String method = req.getMethod();
        String url = req.getRequestURL().toString();
        String urlQuery = "";
        try {
            urlQuery = URLDecoder.decode(req.getQueryString(), "UTF-8");
        } catch (Exception e) {
        }
        /* request header */
        List<String> headers = new ArrayList<>();
        for (Enumeration<?> e = req.getHeaderNames(); e.hasMoreElements();) {
            String headerName = e.nextElement().toString();
            String headerValue = req.getHeader(headerName);

            headers.add(String.format("%s: %s", headerName, headerValue));
        }

        /* request body */
        String requestBody = "";
        if ("POST".equals(method)) {
            System.out.println("request read stream");
            ContentCachingRequestWrapper cachingRequest = (ContentCachingRequestWrapper) req;
            if (cachingRequest.getContentAsByteArray() != null && cachingRequest.getContentAsByteArray().length != 0 && req.getMethod().equals("POST") ) {
                requestBody = new String(cachingRequest.getContentAsByteArray());
            }
            //requestBody = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        }

        String reqStr = String.format(
                "#### General ####" +
                        "\nRequest Method: " + method +
                        "\nRequest URL: " + url +
                        "\nRequest Query: " + urlQuery +
                        "\nStatus Code: " + res.getStatus() +
                        "\n\n#### Request Headers ####\n" + String.join("\n", headers) +
                        "\n\n#### Request Body ####\n" + requestBody
        );
        System.out.println(reqStr);
    }

    public void requestPrinter(HttpServletRequest request) throws IOException {

        String queryString = "";
        String requestBody = "";

        System.out.println("Attributes:");
        for (Enumeration<?> attributeNames = request.getAttributeNames(); attributeNames.hasMoreElements(); ) {
            String nextAttributeName = (String) attributeNames.nextElement();
            System.out.println("attribute \"" + nextAttributeName + "\" value is \"" + request.getAttribute(nextAttributeName) + "\"");
        }

        System.out.println("Parameters:");
        for (Enumeration<?> parameterNames = request.getParameterNames(); parameterNames.hasMoreElements(); ) {
            String nextParameterName = (String) parameterNames.nextElement();

            System.out.println("parameter \"" + nextParameterName + "\" value is \"" + request.getParameter(nextParameterName) + "\"");
        }

        System.out.println("Headers:");
        for (Enumeration<?> e = request.getHeaderNames(); e.hasMoreElements(); ) {
            String nextHeaderName = (String) e.nextElement();

            System.out.println("header name: \"" + nextHeaderName + "\" value is \"" + request.getHeader(nextHeaderName) + "\"");
        }
        ContentCachingRequestWrapper cachingRequest = (ContentCachingRequestWrapper) request;
        if (cachingRequest.getContentAsByteArray() != null && cachingRequest.getContentAsByteArray().length != 0 && request.getMethod().equals("POST") ) {
            requestBody = new String(cachingRequest.getContentAsByteArray());
        }
        if(null != request.getQueryString()) queryString = URLDecoder.decode(request.getQueryString(), "UTF-8");
        String logStatement = "Server Name: " + request.getServerName() + "\n";
        logStatement += "\tServer Port: " + request.getServerPort() + "\n";
        logStatement += "\tServlet Path: " + request.getServletPath() + "\n";
        logStatement += "\tMethod: " + request.getMethod() + "\n";
        logStatement += "\tPath info: " + request.getPathInfo() + "\n";
        logStatement += "\tPath translated: " + request.getPathTranslated() + "\n";
        logStatement += "\tRequest URI: " + request.getRequestURI() + "\n";
        logStatement += "\tRequest URL: " + request.getRequestURL() + "\n";
        logStatement += "\tQuery String: " + queryString + "\n";
        logStatement += "\tRequest Body: " + requestBody + "\n";
        logStatement += "\tContext path: " + request.getContextPath() + "\n";

        System.out.println(logStatement);
    }

}


