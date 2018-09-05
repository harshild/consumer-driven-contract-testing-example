package hello;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class HelloWorldController {

    private static final String template = "Hello %s!";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping(method=RequestMethod.GET,path = "/hello-world")
    public @ResponseBody Greeting sayHello(@RequestParam(value="name", required=false, defaultValue="World") String name) {
        return new Greeting(counter.incrementAndGet(), String.format(template, name));
    }

    @RequestMapping(method=RequestMethod.POST,path = "/hello-world-post")
    public ResponseEntity<Greeting> sayHelloPost(@RequestBody String name) {
        return ResponseEntity.status(201).body(new Greeting(counter.incrementAndGet(), String.format(template, name)));
    }
}