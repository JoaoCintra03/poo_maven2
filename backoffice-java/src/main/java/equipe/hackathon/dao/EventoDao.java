package equipe.hackathon.dao;

import equipe.hackathon.model.Evento;

import java.util.ArrayList;
import java.util.List;

public class EventoDao extends Dao implements DaoInterface {

    @Override
    public Boolean insert(Object entity) {
        try {
            var evento = (Evento) entity;
            var insertSql = "insert into evento(titulo, descricao, lugar, dataHora, curso) values(?,?,?,?,?)";
            var ps = getConnection().prepareStatement(insertSql);
            ps.setString(1, evento.getTitulo());
            ps.setString(2, evento.getDescricao());
            ps.setString(3, evento.getLugar());
            ps.setString(4, evento.getDataHora());
            ps.setString(5, evento.getCurso());
            ps.execute();
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public Boolean uptade(Object entity) {
        return null;
    }

    @Override
    public Boolean delete(Long pk) {
        return null;
    }

    @Override
    public List<Object> select(Long pk) {
        return List.of();
    }

    @Override
    public List<Object> selectALL() {
        List<Evento> eventos = new ArrayList<>();
        try {
            var selectSql = "select * from evento";
            var rs = getConnection().prepareStatement(selectSql).executeQuery();
            while (rs.next());
            eventos.add(new Evento(
            ));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return new ArrayList<>(eventos);
    }
}