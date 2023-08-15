package br.unitins.topicos1.domain.model.enums;

public enum Role {
    ADMIN,
    USER;

    public static Role get(String label) {

        return switch (label) {
            case "Admin" -> ADMIN;
            case "User" -> USER;
            default -> null;
        };
    }
}
