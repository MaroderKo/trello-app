package spd.trello.local.storage;

import spd.trello.domain.Workspace;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class WorkspaceStorage {
    private Map<UUID, Workspace> storage = new HashMap<>();
    public void add(UUID id, Workspace workspace)
    {
        storage.put(id,workspace);
    }
}
