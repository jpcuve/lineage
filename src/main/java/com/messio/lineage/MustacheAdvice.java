package com.messio.lineage;

import com.samskivert.mustache.Mustache;
import com.samskivert.mustache.Template;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.io.IOException;
import java.io.Writer;

@ControllerAdvice  // injects stuff into all models
public class MustacheAdvice {
    @ModelAttribute("layout")
    public Mustache.Lambda layout(){
        return new Mustache.Lambda() {
            private String body;

            @Override
            public void execute(Template.Fragment fragment, Writer writer) throws IOException {
                body = fragment.execute();
            }

            public String getBody() {
                return body;
            }
        };
    }
}
