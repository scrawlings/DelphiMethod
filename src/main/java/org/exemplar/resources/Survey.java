package org.exemplar.resources;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.*;
import java.util.*;

@XmlRootElement(name="survey")
public class Survey {
    @XmlAttribute protected String id;
    @XmlElement protected String title;
    @XmlElement protected String description;
    @XmlElement protected List<String> options;

    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public List<String> getOptions() { return options; }

    protected Survey() {
        this.options = new ArrayList();
    }

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
                JAXBContext ctx = JAXBContext.newInstance(Survey.class);
                Marshaller marshaller = ctx.createMarshaller();
                marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
                marshaller.marshal(this, writer);
            } catch (JAXBException jaxbe) {}
            finally {
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
            BufferedReader reader;
            try {
                reader = new BufferedReader(new FileReader(surveyFile));
            } catch (FileNotFoundException fnfe) {
                return null;
            }
            JAXBContext ctx = JAXBContext.newInstance(Survey.class);
            Survey survey = (Survey)ctx.createUnmarshaller().unmarshal(reader);
            return survey;
        } catch (JAXBException jaxbe) {}

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


