package com.ceos.vote.global.security.jwt;

import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Base64;

@Component
public class JwtProvider {

    private static final String SECRET_KEY = "ceos-vote-local-development-secret-key-for-hs256";
    private static final long ACCESS_TOKEN_EXPIRATION_SECONDS = 60 * 60;

    public String createAccessToken(Long userId) {
        Instant now = Instant.now();
        String header = "{\"alg\":\"HS256\",\"typ\":\"JWT\"}";
        String payload = String.format(
                "{\"sub\":\"%d\",\"iat\":%d,\"exp\":%d}",
                userId,
                now.getEpochSecond(),
                now.plusSeconds(ACCESS_TOKEN_EXPIRATION_SECONDS).getEpochSecond()
        );

        String encodedHeader = base64UrlEncode(header.getBytes(StandardCharsets.UTF_8));
        String encodedPayload = base64UrlEncode(payload.getBytes(StandardCharsets.UTF_8));
        String unsignedToken = encodedHeader + "." + encodedPayload;
        String signature = base64UrlEncode(sign(unsignedToken));

        return unsignedToken + "." + signature;
    }

    private byte[] sign(String data) {
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKeySpec = new SecretKeySpec(
                    SECRET_KEY.getBytes(StandardCharsets.UTF_8),
                    "HmacSHA256"
            );
            mac.init(secretKeySpec);
            return mac.doFinal(data.getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            throw new IllegalStateException("JWT 토큰 생성에 실패했습니다.", e);
        }
    }

    private String base64UrlEncode(byte[] bytes) {
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }
}
