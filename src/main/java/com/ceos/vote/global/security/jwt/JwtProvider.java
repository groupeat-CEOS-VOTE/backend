package com.ceos.vote.global.security.jwt;

import com.ceos.vote.global.apiPayload.code.status.GlobalErrorStatus;
import com.ceos.vote.global.exception.GeneralException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Base64;

@Component
public class JwtProvider {

    private static final String SECRET_KEY = "ceos-vote-local-development-secret-key-for-hs256";
    private static final long ACCESS_TOKEN_EXPIRATION_SECONDS = 60 * 60;
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

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

    public Long getUserId(String token) {
        try {
            String[] parts = token.split("\\.");
            if (parts.length != 3) {
                throw new GeneralException(GlobalErrorStatus._UNAUTHORIZED);
            }

            String unsignedToken = parts[0] + "." + parts[1];
            if (!MessageDigest.isEqual(base64UrlDecode(parts[2]), sign(unsignedToken))) {
                throw new GeneralException(GlobalErrorStatus._UNAUTHORIZED);
            }

            JsonNode payload = OBJECT_MAPPER.readTree(base64UrlDecode(parts[1]));
            if (!payload.hasNonNull("sub") || !payload.hasNonNull("exp")) {
                throw new GeneralException(GlobalErrorStatus._UNAUTHORIZED);
            }

            if (payload.get("exp").asLong() < Instant.now().getEpochSecond()) {
                throw new GeneralException(GlobalErrorStatus._UNAUTHORIZED);
            }

            return Long.valueOf(payload.get("sub").asText());
        } catch (GeneralException e) {
            throw e;
        } catch (Exception e) {
            throw new GeneralException(GlobalErrorStatus._UNAUTHORIZED);
        }
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

    private byte[] base64UrlDecode(String value) {
        return Base64.getUrlDecoder().decode(value);
    }
}
