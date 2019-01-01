package com.messio.lineage;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.messio.lineage.transfer.NEREntity;
import com.messio.lineage.transfer.NERMention;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;

public class ExtractionTest {
    public static final int DISTANCE = 100;

    @Test
    public void testOffset() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String baseFolder = "src/test/resources";
        Path startPath = new File(baseFolder).toPath();
        Files.
                walk(startPath).
                filter(p -> Files.isRegularFile(p) &&
                        p.getFileName().toString().startsWith("organisations-") &&
                        p.getFileName().toString().endsWith(".json")).
                forEach(p -> {
                    String s = p.getFileName().toString();
                    long decisionId = Long.parseLong(s.substring(s.indexOf('-') + 1, s.lastIndexOf('.')));
                    File textFile = new File(String.format("%s/en-%s.txt", baseFolder, decisionId));
                    if (textFile.exists()){
//                        System.out.println(textFile.getAbsolutePath());
                        try {
                            String text = new String(Files.readAllBytes(textFile.toPath()), Charset.forName("UTF-8")).replace("\r\n", " ");
                            NEREntity[] entities = mapper.readValue(p.toFile(), NEREntity[].class);
                            for (NEREntity entity: entities){
                                String name = entity.getName();
                                for (NERMention mention: entity.getMentions()){
                                    int offset = mention.getOffset();
                                    String match = text.substring(offset, offset + name.length());
                                    System.out.printf("File: %s, begin offset: %s%n", textFile.getName(), offset);
                                    System.out.printf("%s | %s%n", name, match);
                                }
                            }
                        } catch(IOException e){
                            e.printStackTrace();
                        }
                    }
                });
    }

    @Test
    public void testExtraction() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String baseFolder = "src/test/resources";
        Path startPath = new File(baseFolder).toPath();
        Files.
                walk(startPath).
                filter(p -> Files.isRegularFile(p) &&
                        p.getFileName().toString().startsWith("organisations-") &&
                        p.getFileName().toString().endsWith(".json")).
                forEach(p -> {
                    String s = p.getFileName().toString();
                    long decisionId = Long.parseLong(s.substring(s.indexOf('-') + 1, s.lastIndexOf('.')));
                    File textFile = new File(String.format("%s/en-%s.txt", baseFolder, decisionId));
                    if (textFile.exists()){
//                        System.out.println(textFile.getAbsolutePath());
                        try{
                            String text = new String(Files.readAllBytes(textFile.toPath()), Charset.forName("UTF-8")).replace("\r\n", "\n");
                            NEREntity[] entities = mapper.readValue(p.toFile(), NEREntity[].class);
//                            for (NEREntity ent: entities){
//                                System.out.println("---------------------------------------------");
//                                System.out.println(ent.getName());
//                                for (NERMention men: ent.getMentions()){
//                                    System.out.print(" " + text.substring(men.getOffset(), men.getOffset() + 20));
//                                }
//                                System.out.println("---------------------------------------------");
//                            }
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
                                            if (distance > 0 && distance < DISTANCE && men0.getOffset() < men1.getOffset() && !ent0.getName().equals(ent1.getName())){
                                                int from = Math.max(0, men0.getOffset() - DISTANCE);
                                                int to = Math.min(men1.getOffset() + DISTANCE, text.length());
                                                System.out.println("---------------------------------------------------------------");
                                                System.out.println(String.format("%s: %s (%s) | %s (%s)", distance, ent0.getName(), men0.getOffset(), ent1.getName(), men1.getOffset()));
                                                System.out.println(text.substring(from, to));
                                            }

                                        }
                                    }
                                }
                            }
                        } catch(IOException e){
                            e.printStackTrace();
                        }
                    }
                });


    }
}
