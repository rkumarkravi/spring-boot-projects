package com.rk.learnity.config.filters;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Collection;
import java.util.Enumeration;
import java.util.UUID;

import static com.rk.learnity.utility.ResponseUtility.gsonObj;

@Configuration
@Slf4j
public class LoggingFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        ContentCachingRequestWrapper requestCacheWrapperObject = new ContentCachingRequestWrapper(req);
        ContentCachingResponseWrapper responseCacheWrapperObject = new ContentCachingResponseWrapper(res);
        long start = System.currentTimeMillis();

        MDC.put("requestId", UUID.randomUUID().toString());

        log.info("Request Method << {}", req.getMethod());
        Enumeration<String> hnReq = req.getHeaderNames();
        while (hnReq.hasMoreElements()) {
            log.info("request headers << {}", req.getHeader(hnReq.nextElement()));
        }
        String requestBody = new String(requestCacheWrapperObject.getContentAsByteArray());
        log.info("Request Body << {}", requestBody);

        chain.doFilter(requestCacheWrapperObject, responseCacheWrapperObject);

        Collection<String> hnRes = res.getHeaderNames();
        hnRes.stream().forEach(hres -> log.info("response headers >> {}", res.getHeader(hres)));
        byte[] responseArray = responseCacheWrapperObject.getContentAsByteArray();
        String responseStr = new String(responseArray, responseCacheWrapperObject.getCharacterEncoding());
        log.info("Response Body >> {}", gsonObj.toJson(responseStr));

        responseCacheWrapperObject.copyBodyToResponse();

        log.info("Total time taken: {}", System.currentTimeMillis() - start);
    }

    private String getRequestBody(HttpServletRequest request) {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = request.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            log.error("Failed to read request body", e);
        }
        return sb.toString();
    }
}
