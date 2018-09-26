package br.com.luansilveira.sosacessvel.Enum;

public enum Status {
    AGUARDANDO_ATENDIMENTO(1, "Aguardando atendimento"),
    ATENDIDA(2, "Atendida"),
    REJEITADA(3, "Rejeitada"),
    FINALIZADA(4, "Finalizada");

    private int id;
    private String descrição;

    Status(int id, String descrição) {
        this.id = id;
        this.descrição = descrição;
    }

    public int getId() {
        return id;
    }
}
