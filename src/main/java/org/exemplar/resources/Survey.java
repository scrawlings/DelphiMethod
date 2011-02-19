package org.exemplar.resources;

import java.io.*;
import java.util.*;

public class Survey {
    private String id;
    private String title;
    private String description;
    private List<String> options;

    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public List<String> getOptions() { return options; }

    public Survey(String title, String description) {
        this(UUID.randomUUID().toString(), title, description);
    }

    private Survey(String id, String title, String description) {
        this.title = title;
        this.description = description;
        this.id = id;
        this.options = new ArrayList();
    }

    public void resetOptions() {
        this.options = new ArrayList();
    }

    public void appendOption(String option) {
        options.add(option);
    }

    public Survey persist() {
        try {
            Writer writer = new BufferedWriter(new FileWriter("data/surveys/" + getId()));
            try {
                writer.write(getId() + "\n");
                writer.write(getTitle() + "\n");
                writer.write(getDescription() + "\n");

                for (String option : getOptions()) {
                    writer.write(option + "\n");
                }
            } finally {
                writer.close();
            }
        } catch (IOException ioe) {}
        return this;
    }

    public static void delete(String requestedId) {
        File file = new File("data/surveys/" + requestedId);
        file.delete();
    }

    public static Survey recover(String requestedId) {
        File file = new File("data/surveys/" + requestedId);
        if (file.exists()) {
            return recover(file);
        }
        return null;
    }
    public static Survey recover(File surveyFile) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(surveyFile));
            String id = reader.readLine();
            String title = reader.readLine();
            String description = reader.readLine();
            Survey survey = new Survey(id, title, description);

            String option = reader.readLine();
            while (option != null) {
                survey.appendOption(option);
                option = reader.readLine();
            }
            return survey;
        } catch (IOException ioe) {}

        return null;
    }

    public static Set<Survey> all() {
        Set<Survey> surveys = new HashSet<Survey>();

        File directory = new File("data/surveys");
        for (File file : directory.listFiles()) {
            surveys.add(recover(file));
        }

        return surveys;
    }
}


