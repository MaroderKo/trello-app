package spd.trello.service;

import spd.trello.domain.Workspace;
import spd.trello.repository.WorkspaceRepository;

import java.time.LocalDateTime;
import java.util.Scanner;
import java.util.UUID;

public class WorkspaceService extends AbstractService<Workspace> {
    WorkspaceRepository repository = new WorkspaceRepository();
    @Override
    public Workspace create() {
        Workspace workspace = new Workspace();
        Scanner sc = new Scanner(System.in);
        System.out.print("Input name: ");
        String name = sc.nextLine();
        System.out.println("Input description: ");
        String description = sc.nextLine();
        workspace.setName(name);
        workspace.setDescription(description);
        System.out.println(workspace.getId());
        repository.create(workspace);
        return workspace;
    }

    @Override
    public void update(UUID index, Workspace workspace) {
        Workspace workspace1 = repository.read(index);
        workspace1.setName(workspace.getName());
        workspace1.setUpdatedDate(LocalDateTime.now());


    }

    public Workspace read(UUID id) {
        return repository.read(id);
    }


}
