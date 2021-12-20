package pdp.uz.jwtproject.enums;

public enum UserRole {
    SUPER_ADMIN("ROLE_SUPER_ADMIN"),
    ADMIN("ROLE_ADMIN"),
    USER("ROLE_USER");

    protected String code;

    UserRole(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
