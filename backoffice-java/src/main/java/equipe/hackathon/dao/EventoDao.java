package equipe.hackathon.dao;

import equipe.hackathon.model.Evento;
import equipe.hackathon.model.Palestrante;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class EventoDao extends Dao implements DaoInterface<Evento> {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public Boolean insert(Evento evento) {
        String sql = "INSERT INTO eventos (titulo, descricao, lugar, data, hora, curso, palestrante_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, evento.getTitulo());
            ps.setString(2, evento.getDescricao());
            ps.setString(3, evento.getLugar());

            LocalDateTime dataHora = LocalDateTime.parse(evento.getDataHora(), DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
            ps.setDate(4, Date.valueOf(dataHora.toLocalDate()));
            ps.setTime(5, Time.valueOf(dataHora.toLocalTime()));

            ps.setString(6, evento.getCurso());
            ps.setInt(7, evento.getPalestranteId());

            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        evento.setId(rs.getInt(1));
                        return true;
                    }
                }
            }
            return false;
        } catch (SQLException e) {
            System.out.println("Erro ao inserir evento: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Boolean update(Evento evento) {
        String sql = "UPDATE eventos SET titulo=?, descricao=?, lugar=?, data=?, hora=?, curso=?, palestrante_id=? WHERE id=?";
        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setString(1, evento.getTitulo());
            ps.setString(2, evento.getDescricao());
            ps.setString(3, evento.getLugar());

            LocalDateTime dataHora = LocalDateTime.parse(evento.getDataHora(), DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
            ps.setDate(4, Date.valueOf(dataHora.toLocalDate()));
            ps.setTime(5, Time.valueOf(dataHora.toLocalTime()));

            ps.setString(6, evento.getCurso());
            ps.setInt(7, evento.getPalestranteId());
            ps.setInt(8, evento.getId());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar evento: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Boolean delete(Long pk) {
        String sql = "DELETE FROM eventos WHERE id=?";
        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setLong(1, pk);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao deletar evento: " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<Evento> select(Long pk) {
        List<Evento> eventos = new ArrayList<>();
        String sql = "SELECT e.*, p.nome as palestrante_nome FROM eventos e LEFT JOIN palestrantes p ON e.palestrante_id = p.id WHERE e.id=?";

        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setLong(1, pk);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    eventos.add(mapearEvento(rs));
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar evento: " + e.getMessage());
        }
        return eventos;
    }

    @Override
    public List<Evento> selectAll() {
        List<Evento> eventos = new ArrayList<>();
        String sql = "SELECT e.*, p.nome as palestrante_nome FROM eventos e LEFT JOIN palestrantes p ON e.palestrante_id = p.id";

        try (Statement stmt = getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                eventos.add(mapearEvento(rs));
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar eventos: " + e.getMessage());
        }
        return eventos;
    }

    @Override
    public Palestrante buscarPorId(int id) {
        return null;
    }

    private Evento mapearEvento(ResultSet rs) throws SQLException {
        Evento evento = new Evento();
        evento.setId(rs.getInt("id"));
        evento.setTitulo(rs.getString("titulo"));
        evento.setDescricao(rs.getString("descricao"));
        evento.setLugar(rs.getString("lugar"));

        // Combinando data e hora
        String dataHora = rs.getDate("data") + " " + rs.getTime("hora");
        evento.setDataHora(dataHora);

        evento.setCurso(rs.getString("curso"));
        evento.setPalestranteId(rs.getInt("palestrante_id"));
        evento.setPalestranteNome(rs.getString("palestrante_nome"));
        return evento;
    }

    @Override
    public boolean atualizar(Palestrante palestrante) {
        return Boolean.parseBoolean(null);
    }
}
