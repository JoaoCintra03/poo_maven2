package equipe.hackathon.dao;

import equipe.hackathon.model.Palestrante;
import java.sql.*;
import java.util.List;

public class PalestranteDao extends Dao implements DaoInterface {

    @Override
    public boolean atualizar(Palestrante palestrante) {
        String sql = "UPDATE palestrantes SET nome=?, descricao=?, foto=?, tema=? WHERE id=?";
        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setString(1, palestrante.getNome());
            stmt.setString(2, palestrante.getDescricao());
            stmt.setString(3, palestrante.getFoto());
            stmt.setString(4, palestrante.getTema());
            stmt.setInt(5, palestrante.getId());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar palestrante: " + e.getMessage());
        }
        return false;
    }

    public Object buscarPorId(int id) {
        String sql = "SELECT * FROM palestrantes WHERE id=?";
        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Palestrante p = new Palestrante();
                p.setId(rs.getInt("id"));
                p.setNome(rs.getString("nome"));
                p.setDescricao(rs.getString("descricao"));
                p.setFoto(rs.getString("foto"));
                p.setTema(rs.getString("tema"));
                return p;
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar palestrante: " + e.getMessage());
        }
        return insert(null);
    }

    @Override
    public Boolean update(Object entity) {
        return insert(null);
    }

    @Override
    public Boolean delete(Long pk) {
        return null;
    }

    @Override
    public List select(Long pk) {
        return List.of();
    }

    @Override
    public List selectAll() {
        return List.of();
    }
}
