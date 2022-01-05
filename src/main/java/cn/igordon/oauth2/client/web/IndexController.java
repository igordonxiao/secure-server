package cn.igordon.oauth2.client.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Index相关的资源
 */
@RestController
public class IndexController {

    @GetMapping("/api/index/a")
    //@PreAuthorize("hasAuthority('SCOP_index')")，配置在资源服务器中
    String index() {
        return "index";
    }

}
