package com.messio.lineage;

import com.samskivert.mustache.Mustache;
import com.samskivert.mustache.Template;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.io.Writer;

@ControllerAdvice  // injects stuff into all models
public class MustacheAdvice {
    public class Layout implements Mustache.Lambda {
        private String content;
        private String title;

        @Override
        public void execute(Template.Fragment fragment, Writer writer) {
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

    @ModelAttribute("layout")
    public Layout content(){
        return new Layout();
    }

    @ModelAttribute("title")
    public Mustache.Lambda title(@ModelAttribute Layout layout){
        return (fragment, writer) -> layout.title = fragment.execute();
    }
}
