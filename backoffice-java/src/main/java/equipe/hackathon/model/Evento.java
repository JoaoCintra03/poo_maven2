package equipe.hackathon.model;

public class Evento {
    private int id;
    private String titulo;
    private String descricao;
    private String dataHora;
    private String curso;
    private String lugar;
    private int palestranteId;
    private String palestranteNome;

    public Evento() {}

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public String getDataHora() { return dataHora; }
    public void setDataHora(String dataHora) { this.dataHora = dataHora; }

    public String getCurso() { return curso; }
    public void setCurso(String curso) { this.curso = curso; }

    public String getLugar() { return lugar; }
    public void setLugar(String lugar) { this.lugar = lugar; }

    public int getPalestranteId() { return palestranteId; }
    public void setPalestranteId(int palestranteId) { this.palestranteId = palestranteId; }

    public String getPalestranteNome() { return palestranteNome; }
    public void setPalestranteNome(String palestranteNome) { this.palestranteNome = palestranteNome; }
}
