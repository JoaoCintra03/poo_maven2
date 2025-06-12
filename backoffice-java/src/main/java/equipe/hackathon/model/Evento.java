package equipe.hackathon.model;

public class Evento {
    private int id;
    private String titulo;
    private String descricao;
    private String dataHora;
    private String curso;
    private String lugar;

    public Evento() {}

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

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public String getLugar() {
        return lugar;
    }
}