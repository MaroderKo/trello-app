package spd.trello.service;

import spd.trello.domain.Resource;

import java.util.List;

public abstract class AbstractService<T extends Resource> {

    public abstract T create();

    public void print(T t)
    {
        System.out.println(t.toString());
    };

    public abstract void update (int index, T t);


}
