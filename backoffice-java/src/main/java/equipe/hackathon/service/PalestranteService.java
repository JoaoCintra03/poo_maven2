package equipe.hackathon.service;

import equipe.hackathon.dao.DaoInterface;
import equipe.hackathon.model.Palestrante;
import java.util.List;

public class PalestranteService {
    private final DaoInterface dao;

    public PalestranteService() {
        this.dao = new DaoInterface() {
            @Override
            public Boolean insert(Object entity) {
                return null;
            }

            @Override
            public Boolean update(Object entity) {
                return null;
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

            @Override
            public Boolean cadastrar(Palestrante palestrante) {
                return false;
            }

            @Override
            public List<Palestrante> listarTodos() {
                return List.of();
            }

            @Override
            public Palestrante buscarPorId(int id) {
                return null;
            }

            @Override
            public boolean deletar(int id) {
                return false;
            }

            @Override
            public boolean atualizar(Palestrante palestrante) {
                return false;
            }
        };
    }

    public boolean cadastrarPalestrante(Palestrante palestrante) {
        return dao.cadastrar(palestrante);
    }

    public boolean atualizarPalestrante(Palestrante palestrante) {
        return dao.atualizar(palestrante);
    }

    public boolean deletarPalestrante(int id) {
        return dao.deletar(id);
    }

    public Palestrante buscarPalestrante(int id) {
        return dao.buscarPorId(id);
    }

    public List<Palestrante> listarPalestrantes() {
        return dao.listarTodos();
    }
}
