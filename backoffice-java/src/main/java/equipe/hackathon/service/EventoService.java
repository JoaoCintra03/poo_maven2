package equipe.hackathon.service;

import equipe.hackathon.dao.EventoDao;
import equipe.hackathon.model.Evento;

import java.util.ArrayList;
import java.util.List;

public class EventoService {

    public boolean cadastrarEvento(Evento evento) {
        var dao = new EventoDao();
        return dao.insert(evento);
    }

    public List<Evento> listarEventos() {
        var dao = new EventoDao();
        List<Evento> alunos = new ArrayList<>();
        dao.selectALL().forEach(object -> alunos.add((Evento) object));

        return alunos;
    }
}
