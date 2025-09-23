package my.project.clientProcessing.controller;


import lombok.RequiredArgsConstructor;
import my.project.clientProcessing.dto.ProductCreateDto;
import my.project.clientProcessing.service.TestService;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/test")
public class TestController {

    private final TestService testService;

    @PostMapping
    public ResponseEntity<String> createProduct(@RequestBody String message) {
        testService.testMessage(message);
        return ResponseEntity.ok("norm");
    }

}
