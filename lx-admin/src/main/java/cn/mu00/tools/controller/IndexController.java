package cn.mu00.tools.controller;

import cn.mu00.tools.common.domain.R;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/index")
public class IndexController {


    @RequestMapping("/hello")
    public R<String> hello(){
        return R.ok("hello world");
    }
}
