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
public class MainTest {

    @Test
    public void testMain() throws IOException {
        String[] args = {"-ip=123.123.123.123"};
        Parser.main(args);
        String[] args2 = {"-al=c:/min-access-log.txt" , "--threshold=100"};
        Parser.main(args2);
    }
}
