package br.com.api.admin.enumeration;

public enum ProfileEnum {

    ADMIN("Administrador"),
    USER("Usuário");

    private String description;

    ProfileEnum(String description) {
        this.description = description;
    }
}
