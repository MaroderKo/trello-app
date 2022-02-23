package spd.trello.web;

public interface ControllerActionList {
    void getAll() throws Exception;
    void createSuccess() throws Exception;
    void getOne() throws Exception;
    void getOneNotExisted() throws Exception;
    void updateSuccess() throws Exception;
    void updateNotExisted() throws Exception;
    void deleteSuccess() throws Exception;
    void deleteNotExisted() throws Exception;
}
