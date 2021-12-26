package spd.trello.service;

import spd.trello.domain.Resource;

import java.util.List;
import java.util.UUID;

public abstract class AbstractService<T extends Resource> {

    public abstract T create();

    public void print(T t)
    {
        System.out.println(t.toString());
    };

    public abstract void update (UUID index, T t);


}
