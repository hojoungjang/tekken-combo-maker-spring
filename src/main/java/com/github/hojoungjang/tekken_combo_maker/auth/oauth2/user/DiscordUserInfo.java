package com.github.hojoungjang.tekken_combo_maker.auth.oauth2.user;

import java.util.Map;

public class DiscordUserInfo implements OAuth2UserInfo {

    private final Map<String, Object> attributes;

    public DiscordUserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    @Override
    public String getProvider() {
        return "discord";
    }

    @Override
    public String getProviderId() {
        return String.valueOf(attributes.get("id"));
    }

    @Override
    public String getEmail() {
        return String.valueOf(attributes.get("email"));
    }

    @Override
    public String getName() {
        return "";
    }
}
