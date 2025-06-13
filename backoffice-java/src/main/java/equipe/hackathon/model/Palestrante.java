package equipe.hackathon.model;

public class Palestrante {
    private int id;
    private String nome;
    private String descricao;
    private String foto;
    private String tema;

    public Palestrante() {}

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public String getFoto() { return foto; }
    public void setFoto(String foto) { this.foto = foto; }

    public String getTema() { return tema; }
    public void setTema(String tema) { this.tema = tema; }
}
