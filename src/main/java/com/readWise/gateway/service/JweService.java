package com.readWise.gateway.service;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWEObject;
import com.nimbusds.jose.KeyLengthException;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.DirectDecrypter;
import com.nimbusds.jwt.JWTClaimsSet;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpCookie;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.text.ParseException;

@Service
public class JweService {
    @Value("${keys.secret_key}")
    private String secretKey;

    public boolean isValid(HttpCookie jweCookie) throws ParseException {
        if(jweCookie !=null) {
            JWEObject jweObject = JWEObject.parse(jweCookie.getValue());
            try {
                jweObject.decrypt(new DirectDecrypter(secretKey.getBytes(StandardCharsets.UTF_8)));
                Payload payload = jweObject.getPayload();
                JWTClaimsSet claimSet = payload.toSignedJWT().getJWTClaimsSet();

                if(StringUtils.isNotEmpty(claimSet.getClaim("username").toString())
                        && BooleanUtils.isTrue(Boolean.valueOf(claimSet.getClaim("isAuthenticated").toString()))) {
                    return true;
                }
            } catch (JOSEException e) {
                throw new RuntimeException(e);
            }
        }
        return false;
    }
}
