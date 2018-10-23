package com.messio.lineage;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.messio.lineage.domain.Company;
import com.messio.lineage.domain.Extract;
import com.messio.lineage.domain.Relation;
import com.messio.lineage.transfer.ExtractValue;
import com.messio.lineage.transfer.SaveValue;
import com.mysql.cj.xdevapi.JsonArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by jpc on 31-05-17.
 */
@RestController
@RequestMapping("/api")
@CrossOrigin
public class ApiController {
    private final ObjectMapper mapper = new ObjectMapper();
    private final DataFacade facade;

    @Autowired
    public ApiController(DataFacade facade) {
        this.facade = facade;
    }

    @GetMapping("/hello")
    public JsonNode hello(){
        ObjectNode node = mapper.createObjectNode();
        node.put("title", "Hello World!");
        return node;
    }

    @GetMapping("/extract/{id}")
    public ExtractValue extract(@PathVariable("id") long id){
        return facade
                .findOne(Extract.class, id)
                .map(extract -> new ExtractValue(extract, extract.getCompanies()))
                .orElseThrow(() -> new ResourceNotFoundException("Extract not found: %s", id));
    }

    @PostMapping("/save")
    public SaveValue save(@RequestBody SaveValue saveValue){
        facade
                .findOne(Extract.class, saveValue.getExtractId())
                .ifPresent(extract ->{
                    extract.setProcessed(true);
                    facade.update(extract);
                    if (saveValue.getParentId() > 0 && saveValue.getChildId() > 0){
                        facade
                                .findOne(Company.class, saveValue.getParentId())
                                .ifPresent(parent -> facade.
                                        findOne(Company.class, saveValue.getChildId())
                                        .ifPresent(child -> {
                                            Relation relation = new Relation();
                                            relation.setExtract(extract);
                                            relation.setParent(parent);
                                            relation.setChild(child);
                                            facade.create(relation);
                                        }));

                    }
                });
        System.out.println(saveValue);
        return saveValue;
    }

    @PostMapping("/query")
    public List<Long> query(@RequestBody String query){
        return facade.findExtractsContainingText(query);
    }
}
