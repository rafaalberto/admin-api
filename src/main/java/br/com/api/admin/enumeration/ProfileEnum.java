package br.com.api.admin.enumeration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ProfileEnum {

    ROLE_ADMIN("AD", "Administrador"),
    ROLE_USER("US", "Usuário");

    private final String code;
    private final String description;
}
