package com.messio.lineage;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.messio.lineage.domain.Extract;
import com.messio.lineage.transfer.NEREntity;
import com.messio.lineage.transfer.NERMention;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.core.env.Environment;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

@SpringBootApplication
public class LineageApplication extends SpringBootServletInitializer {
	private static final Logger LOGGER = LoggerFactory.getLogger(LineageApplication.class);
	public static final String BASE_FOLDER = "C:\\Users\\jpc\\PycharmProjects\\ops-lineage\\resources";
	private final DataFacade facade;
	private final ObjectMapper mapper;

	private void process(long decisionId, String language, String text, NEREntity[] entities, int maximumDistance){
        for (int i = 0; i < entities.length; i++){
            NEREntity ent0 = entities[i];
            for (int j = i; j < entities.length; j++){
                NEREntity ent1 = entities[j];
                // now check distances between mentions
                for (int k = 0; k < ent0.getMentions().length; k++){
                    NERMention men0 = ent0.getMentions()[k];
                    for (int l = 0; l < ent1.getMentions().length; l++){
                        NERMention men1 = ent1.getMentions()[l];
                        int distance = Math.abs(men0.getOffset() - men1.getOffset());
                        if (
                                distance > 0 &&
                                distance < maximumDistance &&
                                men0.getOffset() < men1.getOffset() &&
                                !ent0.getName().equals(ent1.getName())
                        ){
                            int from = Math.max(men0.getOffset() - maximumDistance, 0);
                            while (from > 0 && !Character.isWhitespace(text.charAt(from))){
                                from--;
                            }
                            int to = Math.min(
                                    men1.getOffset() + men1.getText().length() + maximumDistance,
                                    text.length() - 1);
                            while (to < text.length() && !Character.isWhitespace(text.charAt(to))){
                                to++;
                            }
                            String sentences = text.substring(from, to);
                            Extract extract = new Extract();
                            extract.setDecisionId(decisionId);
                            extract.setLang(language);
                            extract.setSentences(sentences);
                            facade.createExtract(extract, ent0.getName(), ent1.getName());
                        }
                    }
                }
            }
        }
    }

    private void load(){
	    File startFolder = new File(BASE_FOLDER);
	    if (startFolder.exists() && startFolder.isDirectory()){
            Path startPath = startFolder.toPath();
            try{
                Files.
                        walk(startPath).
                        filter(p -> Files.isRegularFile(p) &&
                                p.getFileName().toString().startsWith("organisations-") &&
                                p.getFileName().toString().endsWith(".json")).
                        forEach(p -> {
                            String s = p.getFileName().toString();
                            long decisionId = Long.parseLong(s.substring(s.indexOf('-') + 1, s.lastIndexOf('.')));
                            File textFile = new File(String.format("%s/en-%s.txt", BASE_FOLDER, decisionId));
                            if (textFile.exists()){
                                LOGGER.info("Processing: {}", s);
                                try{
                                    String text = new String(
                                            Files.readAllBytes(textFile.toPath()), Charset.forName("UTF-8")
                                    ).replace("\r\n", "\n");
                                    NEREntity[] entities = mapper.readValue(p.toFile(), NEREntity[].class);
                                    process(decisionId, "en", text, entities, 100);
                                } catch(IOException e){
                                    LOGGER.error("Cannot read file", e);
                                }
                            }
                        });
            } catch(IOException e){
                LOGGER.error("Error initializing test database", e);
            }
        }
    }

	@Autowired
	public LineageApplication(Environment environment, DataFacade facade, ObjectMapper mapper){
	    this.facade = facade;
	    this.mapper = mapper;
		if (Arrays.asList(environment.getActiveProfiles()).contains("dev")){
			LOGGER.info("Found dev profile, initializing database");
			load();
		}
	}

	public static void main(String[] args) {
		SpringApplication.run(LineageApplication.class, args);
	}
}
