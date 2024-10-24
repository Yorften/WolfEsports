package app.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import app.view.ClientView;
import app.view.ComponentView;
import app.view.ProjectView;
import app.view.interfaces.Menu;
import app.view.menu.TournamentMenu;

import app.util.IO;
import app.util.InputValidator;

public class ProjectController {

    private Menu projectMenu = new TournamentMenu();
    private ProjectView projectView = new ProjectView();
    private ClientView clientView = new ClientView();
    private ComponentView componentView = new ComponentView();

    private boolean isRunning = true;

    public void startProjectMenu() {
        isRunning = true;
        while (isRunning) {
            IO.clear();
            int choice = projectMenu.display();
            handleChoice(choice);
        }
    }

    public Client startClientMenu() {
        Client client = null;
        isRunning = true;
        do {
            IO.clear();
            int choice = projectView.clientMenu();
            client = handleClientMenuChoice(choice);
        } while (isRunning && client == null);

        return client;
    }

    public void addProjectUI() {
        List<Component> components = new ArrayList<>();
        Client selectedClient = startClientMenu();

        if (selectedClient == null)
            return;

        Project project = projectView.addProjectUI();

        List<Material> materials = componentView.addMaterialUI();
        List<WorkForce> workForces = componentView.addWorkForceUI();
        components.addAll(materials);
        components.addAll(workForces);

        Double totalCost = projectView.showProjectSummary(project, selectedClient, components);

        IO.sysPause();
        if (InputValidator.promptYesOrNo("Do you want to save the project")) {
            Quote quote = quoteService.addQuote(new Quote(totalCost, LocalDate.now()));
            if (quote != null)
                System.out.println("Quote generated.");

            project.setTotalCost(totalCost);
            project.setClient(selectedClient);
            project.setQuote(quote);

            Project addedProject = projectService.addProject(project);

            if (materialService.addMaterial(materials, addedProject.getId())) {
                System.out.println("** Materials added successfully! **");
            }

            if (workForceService.addWorkForce(workForces, addedProject.getId())) {
                System.out.println("** Work Forces added successfully! **");
            }

            System.out.println("Project saved successfully !");
            IO.sysPause();
        }
    }

    private void handleChoice(int choice) {
        List<Project> projects = new ArrayList<>();
        Project selectedProject;
        switch (choice) {
            case 1:
                projects = projectService.getAll(ProjectStatus.ONGOING);
                selectedProject = projectView.listProjects(projects);
                if (selectedProject != null)
                    projectView.subProjectMenu(selectedProject);
                break;
            case 2:
                projects = projectService.getAll(null);
                selectedProject = projectView.listProjects(projects);
                if (selectedProject != null)
                    projectView.subProjectMenu(selectedProject);
                break;
            case 3:
                projects = projectService.getAll(ProjectStatus.FINISHED);
                selectedProject = projectView.listProjects(projects);
                List<Component> components = new ArrayList<>();
                if (selectedProject == null)
                    return;

                List<Material> materials = materialService.getAll(selectedProject.getId());
                List<WorkForce> workForces = workForceService.getAll(selectedProject.getId());

                components.addAll(materials);
                components.addAll(workForces);

                selectedProject.setComponents(components);

                projectView.showProjectSummary(selectedProject, selectedProject.getClient(), components); 
                IO.sysPause();  
                break;
            case 4:
                isRunning = false;
                break;
            default:
                break;
        }
    }

    public Client handleClientMenuChoice(int choice) {
        Client client = null;

        switch (choice) {
            case 1:
                String input = clientView.searchClientUI();
                List<Client> clients = clientService.findClientByName(input);
                client = clientView.listClients(clients);
                break;
            case 2:
                client = clientService.addClient(clientView.addClientUI());
                if (client != null) {
                    System.out.println("Client added successfuly!");
                }
                IO.sysPause();
                break;
            case 3:
                isRunning = false;
                break;
            default:
                break;
        }

        return client;
    }
}
