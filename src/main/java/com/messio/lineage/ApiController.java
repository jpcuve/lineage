package com.messio.lineage;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.messio.lineage.domain.Extract;
import com.messio.lineage.transfer.AppValue;
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
    private final DataFacade facade;
    private final ObjectMapper mapper;

    @Autowired
    public ApiController(DataFacade facade, ObjectMapper mapper) {
        this.facade = facade;
        this.mapper = mapper;
    }

    @GetMapping("/hello")
    public JsonNode hello(){
        ObjectNode node = mapper.createObjectNode();
        node.put("title", "Hello World!");
        return node;
    }

    @GetMapping("/extract/{id}")
    public AppValue extract(@PathVariable("id") long id){
        return facade
                .findOne(Extract.class, id)
                .map(extract -> new AppValue(extract, extract.getOne(), extract.getTwo()))
                .orElseThrow(() -> new ResourceNotFoundException("Extract not found: %s", id));
    }

    @PostMapping("/save")
    public AppValue save(@RequestBody AppValue appValue){
        facade
                .findOne(Extract.class, appValue.getExtract().getId())
                .ifPresent(extract ->{
                    extract.setRelation(appValue.getExtract().getRelation());
                    facade.update(extract);
                });
        return appValue;
    }

    @PostMapping("/query")
    public List<Long> query(@RequestBody String query){
        return facade.findExtractsContainingText(query);
    }
}
