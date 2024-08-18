package hello.tobagi.tobagi.login.entity;


import java.util.Map;

public class GoogleUserDetails implements OAuth2UserInfo {

    private final Map<String, Object> attributes;
    private String providerId;
    private String email;
    private String name;

    public GoogleUserDetails(Map<String, Object> attributes) {
        this.attributes = attributes;
        this.providerId = (String) attributes.get("sub");
        this.email = (String) attributes.get("email");
        this.name = (String) attributes.get("name");
    }

    @Override
    public String getProvider() {
        return null;
    }

    @Override
    public String getProviderId() {
        return providerId;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }
}