package com.example.IntercepterTest;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;


@Component
public class CustomServletWrappingFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        /*
         * Tomcat setting에서 reqeustBody는 한번만 읽을수 있으므로
         * RequestBody 로깅을 위해
         * filter level에서 caching 해놓아야 한다.
         */
        ContentCachingRequestWrapper wrappingRequest = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper wrappingResponse = new ContentCachingResponseWrapper(response);
        try {
            filterChain.doFilter(wrappingRequest, wrappingResponse);
        } catch (Exception e ) {
            e.printStackTrace();
        }
        wrappingResponse.copyBodyToResponse();
        printReqAndResInFilter(wrappingRequest, wrappingResponse , new String(wrappingRequest.getContentAsByteArray()) );
    }
    void printReqAndResInFilter(ContentCachingRequestWrapper req, ContentCachingResponseWrapper res, String bodyStr) {
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
        String requestBody = bodyStr;



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

}
