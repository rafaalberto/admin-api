package br.com.api.admin.enumeration;

public enum ProfileEnum {

    ROLE_ADMIN("AD", "Administrador"),
    ROLE_USER("US", "Usuário");

    private final String code;
    private final String description;

    ProfileEnum(final String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
