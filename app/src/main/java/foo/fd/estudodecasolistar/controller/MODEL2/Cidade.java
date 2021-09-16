package foo.fd.estudodecasolistar.controller.MODEL2;

public class Cidade {

    private int id;
    private String nome;
    private String sigla;


    public Cidade() {
    }

    public Cidade(int id, String nome, String sigla) {
        this.id = id;
        this.nome = nome;
        this.sigla = sigla;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public String toString(String sigla) {

        return "--> ID: " + this.getId() + " --> Cidade: " + this.getNome() + " --> Sigla: " + sigla;
    }
}