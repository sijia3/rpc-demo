import com.sijia3.registry.ServerDiscovery;
import com.sijia3.registry.impl.ServerDiscoveryImpl;
import com.sijia3.registry.impl.ServerRegistryImpl;
import org.I0Itec.zkclient.ZkClient;

/**
 * @author sijia3
 * @date 2019/12/19 11:31
 */
public class ZookeeperTest {
    public static void main(String[] args) {
        ZkClient zkClient = new ZkClient("127.0.0.1:2181", 5000, 1000);
        String registryPath = "/registry";
        if (zkClient.exists(registryPath)){
            zkClient.deleteRecursive(registryPath);
            System.out.println("级联删除/registry节点");
        }
        ServerRegistryImpl serverRegistry = new ServerRegistryImpl("127.0.0.1:2181");
        serverRegistry.registry("server1","127.0.0.1:2181");
        serverRegistry.registry("server1", "127.0.0.2:2181");
        ServerDiscoveryImpl serverDiscovery = new ServerDiscoveryImpl("127.0.0.1:2181");
        String s = serverDiscovery.findServerByName("server1");
        System.out.println(s);
    }
}
