package test;

import com.ef.Parser;
import java.io.IOException;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

/**
 *
 * @author Abed
 */
@Ignore
public class MainTest {

    @Test
    public void testMain() throws IOException {
        String[] args = {"-ip=123.123.123.123"};
        Parser.main(args);
        String[] args2 = {
            "-al=c:/access.log" ,
            "--threshold=100",
            "--startDate=2017-01-01.13:00:00",
            "--duration=hourly"
        };
        Parser.main(args2);
    }
}
