package cn.igordon.oauth2.client.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Test相关的资源
 */
@RestController
public class TestController {
    @GetMapping("/api/test/a")
    String test() {
        return "test";
    }

    @GetMapping("/api/test/foo")
//    @PreAuthorize("hasAuthority('SCOPE_test')")
    String foo() {
        return "foobar2";
    }
}
