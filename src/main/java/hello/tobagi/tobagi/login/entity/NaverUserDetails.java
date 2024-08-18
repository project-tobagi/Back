package hello.tobagi.tobagi.login.entity;

import java.util.Map;

public class NaverUserDetails implements OAuth2UserInfo {
    private final Map<String, Object> attributes;

    public NaverUserDetails(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    @Override
    public String getProvider() {
        return null;
    }

    @Override
    public String getProviderId() {
        return (String) attributes.get("id");
    }

    @Override
    public String getName() {
        return (String) attributes.get("name");
    }

    @Override
    public String getEmail() {
        return (String) attributes.get("email");
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }
}