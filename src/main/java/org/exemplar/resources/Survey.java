package org.exemplar.resources;

import java.io.*;
import java.util.UUID;
import java.util.Set;
import java.util.HashSet;

public class Survey {
    private String id;
    private String title;
    private String description;

    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }

    public Survey(String title, String description) {
        this(UUID.randomUUID().toString(), title, description);
    }

    private Survey(String id, String title, String description) {
        this.title = title;
        this.description = description;
        this.id = id;
    }

    public void persist() {
        try {
            Writer writer = new BufferedWriter(new FileWriter("data/surveys/" + getId()));
            try {
                writer.write(getId() + "\n");
                writer.write(getTitle() + "\n");
                writer.write(getDescription() + "\n");
            } finally {
                writer.close();
            }
        } catch (IOException ioe) {}
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
            StringBuilder description = new StringBuilder();
            String descriptionLine = reader.readLine();
            while (descriptionLine != null && descriptionLine.length() > 0) {
                description.append(descriptionLine);
                descriptionLine = reader.readLine();
            }
            return new Survey(id, title, description.toString());
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


