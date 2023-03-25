package priyankapractice.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PriyankaPracticeController {
    
    @GetMapping("/priyanka")
    public  String  priyankaPractice(){
        return "priyankaPractice";
    }   
}
