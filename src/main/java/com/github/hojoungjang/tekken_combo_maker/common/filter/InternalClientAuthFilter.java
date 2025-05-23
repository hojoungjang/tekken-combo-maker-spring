package com.github.hojoungjang.tekken_combo_maker.common.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HexFormat;
import java.util.stream.Collectors;

@Component
public class InternalClientAuthFilter extends OncePerRequestFilter {

    @Value("${application.env.rest-client-auth-key}")
    private String authKey;
    private final String hmacSignatureHeader = "Hmac-Signature";
    private final String hmacTimestampHeader = "Hmac-Timestamp";

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        String signature = request.getHeader(hmacSignatureHeader);
        String timestamp = request.getHeader(hmacTimestampHeader);

        if (signature == null || timestamp == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Missing internal client auth headers");
            return;
        }

        // Reject old timestamps
        long currentTime = System.currentTimeMillis();
        long requestTime = Long.parseLong(timestamp);
        if (Math.abs(currentTime - requestTime) > 5 * 60 * 1000) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Old request time");
            return;
        }

        String method = request.getMethod();
        String path = request.getRequestURI();
        String body = request.getReader().lines().collect(Collectors.joining());

        String data = method + path + timestamp + body;
        String expectedSignature = hmacSha256(authKey, data);

        if (!expectedSignature.equals(signature)) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid signature");
            return;
        }

        filterChain.doFilter(request, response);
    }

    private String hmacSha256(String secret, String data) {
        try {
            SecretKeySpec keySpec = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(keySpec);
            byte[] rawHmac = mac.doFinal(data.getBytes(StandardCharsets.UTF_8));
            return HexFormat.of().formatHex(rawHmac);
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate HMAC", e);
        }
    }
}
