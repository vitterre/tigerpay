package com.tigerpay.auth.security.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.experimental.UtilityClass;
import lombok.val;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.util.HashMap;

@UtilityClass
public class HttpResponseUtil {

    public static void putExceptionInResponse(
            final HttpServletRequest request,
            final HttpServletResponse response,
            final Exception exception,
            final Integer statusCode
    ) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(statusCode);

        val body = new HashMap<String, Object>();
        body.put("status", statusCode);
        body.put("error", "Unauthorized");
        body.put("message", exception.getMessage());
        body.put("path", request.getRequestURI());

        val mapper = new ObjectMapper();
        mapper.writeValue(response.getOutputStream(), body);
    }
}
