package equipe.hackathon.dao;

import equipe.hackathon.model.Palestrante;

import java.util.List;

public interface DaoInterface<T> {
    Boolean insert(T entity);
    Boolean update(T entity);
    Boolean delete(Long pk);
    List<T> select(Long pk);
    List<T> selectAll();

    Boolean cadastrar(Palestrante palestrante);

    List<Palestrante> listarTodos();

    Palestrante buscarPorId(int id);

    boolean deletar(int id);

    boolean atualizar(Palestrante palestrante);
}
