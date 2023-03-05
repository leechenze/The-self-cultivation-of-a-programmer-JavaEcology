import com.lee.config.SpringConfig;
import com.lee.controller.UserController;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

public class App {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(SpringConfig.class);
        // ctx.register(SpringConfig.class);
        // 打印UserController这个类，看是否过滤掉了@Controller这个注解
        // System.out.println(ctx.getBean(UserController.class));
    }
}
