package com.github.hojoungjang.tekken_combo_maker.auth.oauth2.user;

public interface OAuth2UserInfo {
    String getProvider();
    String getProviderId();
    String getEmail();
}
