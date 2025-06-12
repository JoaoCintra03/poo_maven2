package equipe.hackathon.dao;

import java.util.List;

public interface DaoInterface {
    Boolean insert(Object entity);
    Boolean uptade(Object entity);
    Boolean delete(Long pk);
    List<Object> select(Long pk);
    List<Object> selectALL();


}