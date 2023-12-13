package ILogTest;

import lombok.extern.slf4j.Slf4j;
import org.skyemoon.index12306.framework.starter.log.annotation.ILog;

@ILog
public class ILogAnnotationTest {

    public void MyLog(String message) {
        System.out.println("This is my message: " + message);
    }

    public void MyLog() {
        System.out.println("This is my empty message.");
    }
}
