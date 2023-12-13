package ILogTest;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ILogMain {

    @Test
    public void contextLoads() {
        ILogAnnotationTest iLogAnnotationTest = new ILogAnnotationTest();
        iLogAnnotationTest.MyLog();
        iLogAnnotationTest.MyLog("SkyeMoon");
    }
}
