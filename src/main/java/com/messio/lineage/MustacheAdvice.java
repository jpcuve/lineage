package com.messio.lineage;

import com.samskivert.mustache.Mustache;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.io.Writer;

@ControllerAdvice  // injects stuff into all models
public class MustacheAdvice {
    public class Template implements Mustache.Lambda {
        private String content;
        private String title;

        @Override
        public void execute(com.samskivert.mustache.Template.Fragment fragment, Writer writer) {
            content = fragment.execute();
        }

        public String getContent() {
            return content;
        }

        public String getTitle() {
            return title;
        }
    }

    @ModelAttribute("plain")
    public Mustache.Lambda plain(){
        return (fragment, writer) -> writer.write(fragment.execute());
    }

    @ModelAttribute("template")
    public Template content(){
        return new Template();
    }

    @ModelAttribute("title")
    public Mustache.Lambda title(@ModelAttribute Template template){
        return (fragment, writer) -> template.title = fragment.execute();
    }
}
