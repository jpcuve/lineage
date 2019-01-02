package com.messio.lineage;

import com.samskivert.mustache.Mustache;
import com.samskivert.mustache.Template;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.io.Writer;
import java.util.HashMap;

@ControllerAdvice  // injects stuff into all models
public class MustacheAdvice {
    public class MapLambda extends HashMap<String, Object> implements Mustache.Lambda {
        @Override
        public void execute(Template.Fragment fragment, Writer writer) {
            put("content", fragment.execute());
        }
    }

    @ModelAttribute("plain")
    public Mustache.Lambda plain(){
        return (fragment, writer) -> writer.write(fragment.execute());
    }

    @ModelAttribute("skeleton")
    public MapLambda skeleton(){
        return new MapLambda();
    }

    @ModelAttribute("title")
    public Mustache.Lambda title(@ModelAttribute("skeleton") MapLambda skeleton){
        return (fragment, writer) -> skeleton.put("title", fragment.execute());
    }
}