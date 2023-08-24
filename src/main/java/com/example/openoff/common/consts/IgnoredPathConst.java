package com.example.openoff.common.consts;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class IgnoredPathConst {

    public static final String[] IGNORED_PATHS = {
            "/", "/auth/**", "/banner",
            "/reissue",
           };
}
