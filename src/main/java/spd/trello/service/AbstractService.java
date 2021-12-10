package spd.trello.service;

import spd.trello.domain.Resource;

public abstract class AbstractService<T extends Resource> {

    public abstract T create();

    public abstract void print(T t);

    public abstract void update (int index, T t);


}
