package equipe.hackathon.service;

import equipe.hackathon.dao.EventoDao;
import equipe.hackathon.model.Evento;
import java.util.List;

public class EventoService {
    public boolean cadastrarEvento(Evento evento) {
        return new EventoDao().insert(evento);
    }

    public boolean atualizarEvento(Evento evento) {
        return new EventoDao().update(evento);
    }

    public boolean deletarEvento(Long id) {
        return new EventoDao().delete(id);
    }

    public Evento buscarEvento(Long id) {
        List<Evento> eventos = new EventoDao().select(id);
        return eventos.isEmpty() ? null : eventos.get(0);
    }

    public List<Evento> listarEventos() {
        return new EventoDao().selectAll();
    }
}
