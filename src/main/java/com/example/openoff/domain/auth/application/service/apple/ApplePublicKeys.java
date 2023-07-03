package com.example.openoff.domain.auth.application.service.apple;

import com.example.openoff.common.exception.Error;
import com.example.openoff.domain.auth.application.exception.AppleOIDCException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class ApplePublicKeys {

    private List<ApplePublicKey> keys;

    public ApplePublicKey getMatchesKey(String alg, String kid) {
        return this.keys
                .stream()
                .filter(k -> k.getAlg().equals(alg) && k.getKid().equals(kid))
                .findFirst()
                .orElseThrow(() -> new AppleOIDCException(Error.APPLE_OIDC_FAILED4));
    }
}
