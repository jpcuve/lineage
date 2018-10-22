package com.messio.lineage;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.messio.lineage.domain.Extract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public JsonNode extract(@PathVariable("id") long id){
        return facade
                .findOne(Extract.class, id)
                .map(extract -> {
                    ObjectNode node = mapper.createObjectNode();
                    node.putPOJO("extract", extract);
                    ArrayNode list = node.putArray("companies");
                    extract.getCompanies().forEach(list::addPOJO);
                    return node;
                })
                .orElseThrow(() -> new ResourceNotFoundException("Extract not found: %s", id));
    }
}
