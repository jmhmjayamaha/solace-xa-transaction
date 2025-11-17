package lk.hmj.xa.controller;

import lk.hmj.xa.service.XaPublishService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    private final XaPublishService xaPublishService;

    public MessageController(XaPublishService xaPublishService) {
        this.xaPublishService = xaPublishService;
    }

    /**
     * Example:
     * POST http://localhost:8080/api/messages?topic=demo/topic&body=hello&fail=false
     */
    @PostMapping
    public ResponseEntity<String> send(
            @RequestParam("topic") String topic,
            @RequestParam("body") String body,
            @RequestParam(name = "fail", defaultValue = "false") boolean fail) {

        xaPublishService.publishToTopicAndLog(topic, body, fail);
        return ResponseEntity.ok("XA done: H2 insert + Solace send (fail=" + fail + ")");
    }
}