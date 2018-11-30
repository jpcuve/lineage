package com.messio.lineage;

import com.samskivert.mustache.Mustache;
import com.samskivert.mustache.Template;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.io.IOException;
import java.io.Writer;

@ControllerAdvice  // injects stuff into all models
public class MustacheAdvice {
    public class Memory implements Mustache.Lambda {
        private String recall;

        @Override
        public void execute(Template.Fragment fragment, Writer writer) throws IOException {
            recall = fragment.execute();
        }

        public String getRecall() {
            return recall;
        }
    }

    @ModelAttribute("plain")
    public Mustache.Lambda plain(){
        return (fragment, writer) -> writer.write(fragment.execute());
    }

    @ModelAttribute("content")
    public Mustache.Lambda content(){
        return new Memory();
    }

    @ModelAttribute("title")
    public Mustache.Lambda title(){
        return new Memory();
    }
}
