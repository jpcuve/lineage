package com.messio.lineage;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.messio.lineage.domain.Extract;
import com.messio.lineage.transfer.ExtractValue;
import com.messio.lineage.transfer.SaveValue;
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
                .map(extract -> new ExtractValue(extract, extract.getOne(), extract.getTwo()))
                .orElseThrow(() -> new ResourceNotFoundException("Extract not found: %s", id));
    }

    @PostMapping("/save")
    public SaveValue save(@RequestBody SaveValue saveValue){
        facade
                .findOne(Extract.class, saveValue.getExtractId())
                .ifPresent(extract ->{
                    extract.setProcessed(true);
                    extract.setRelation(saveValue.getRelation());
                    facade.update(extract);
                });
        return saveValue;
    }

    @PostMapping("/query")
    public List<Long> query(@RequestBody String query){
        return facade.findExtractsContainingText(query);
    }
}
