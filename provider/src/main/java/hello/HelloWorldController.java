package hello;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloWorldController {

    private static final String template = "Hello %s!";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping(method=RequestMethod.GET,path = "/hello-world")
    public @ResponseBody Greeting sayHello(@RequestParam(value="name", required=false, defaultValue="World") String name) {
        return new Greeting(counter.incrementAndGet(), String.format(template, name));
    }

    @RequestMapping(method=RequestMethod.POST,path = "/readName")
    public ResponseEntity<Object> readName(@RequestParam(value="name", required=false, defaultValue="no name") String name) {
        return ResponseEntity.status(HttpStatus.CREATED).body(new Greeting(counter.incrementAndGet(), String.format(template, name)));
    }
}