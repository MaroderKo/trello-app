package spd.trello.service;

import spd.trello.domain.Card;
import spd.trello.domain.Workspace;
import spd.trello.repository.WorkspaceRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class WorkspaceService extends AbstractService<Workspace>{
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
            WorkspaceRepository.create(workspace);
            return workspace;
        }

        @Override
        public void update(UUID index, Workspace workspace) {
            Workspace Workspace1 = WorkspaceRepository.read(index);
            Workspace1.setName(workspace.getName());
            Workspace1.setUpdatedDate(LocalDateTime.now());


        }

        public Workspace read(UUID id)
        {
            return WorkspaceRepository.read(id);
        }

    

}
