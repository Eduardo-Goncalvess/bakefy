package com.cefet.bakefy.entity;

public enum TipoUsuario {
    CLIENTE(1),
    EMPRESA(2),
    ADMIN(3);

    private final int codigo;

    TipoUsuario(int codigo) {
        this.codigo = codigo;
    }

    public int getCodigo() {
        return codigo;
    }
}
