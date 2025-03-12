package cn.mu00.tools;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan({"cn.mu00.tools.**"})
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class LxApplication {

    public static void main(String[] args) {
        SpringApplication.run(LxApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  Lx-ToolBox启动成功   ლ(´ڡ`ლ)ﾞ  \n");
    }

}
