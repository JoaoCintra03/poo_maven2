package equipe.hackathon.dao;

import equipe.hackathon.model.Palestrante;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class Dao {
    public static final String URL = "jdbc:mysql://localhost:3306/db_eventos?useTimezone=true&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "";
    private Connection connection;

    public Dao() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("[DAO Connection] " + e.getMessage());
            throw new RuntimeException("Failed to initialize database connection", e);
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public Boolean insert(Object entity) {
        return null;
    }

    public Boolean cadastrar(Palestrante palestrante) {
        String sql = "INSERT INTO palestrantes(nome, descricao, foto, tema) VALUES(?,?,?,?)";
        try (PreparedStatement stmt = getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, palestrante.getNome());
            stmt.setString(2, palestrante.getDescricao());
            stmt.setString(3, palestrante.getFoto());
            stmt.setString(4, palestrante.getTema());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        palestrante.setId(rs.getInt(1));
                    }
                }
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar palestrante: " + e.getMessage());
        }
        return false;
    }

    public abstract boolean atualizar(Palestrante palestrante);

    public boolean deletar(int id) {
        String sql = "DELETE FROM palestrantes WHERE id=?";
        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao deletar palestrante: " + e.getMessage());
        }
        return false;
    }

    public List<Palestrante> listarTodos() {
        List<Palestrante> palestrantes = new ArrayList<>();
        String sql = "SELECT * FROM palestrantes";

        try (Statement stmt = getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Palestrante p = new Palestrante();
                p.setId(rs.getInt("id"));
                p.setNome(rs.getString("nome"));
                p.setDescricao(rs.getString("descricao"));
                p.setFoto(rs.getString("foto"));
                p.setTema(rs.getString("tema"));
                palestrantes.add(p);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar palestrantes: " + e.getMessage());
        }
        return palestrantes;
    }
}
