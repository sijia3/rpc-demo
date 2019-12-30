import com.sijia3.api.HelloService;
import com.sijia3.client.RpcProxy;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author sijia3
 * @date 2019/12/30 14:57
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring.xml")
public class TTest {

    @Autowired
    private HelloService helloService;

    @Test
    public void helloTest(){
        helloService.hello("sdddd");
    }
}