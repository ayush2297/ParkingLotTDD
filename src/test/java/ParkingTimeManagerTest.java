import org.junit.Assert;
import org.junit.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Period;

public class ParkingTimeManagerTest {

    @Test
    public void givenARequestToGetTime_ShouldReturnCurrentTime() {
        ParkingTimeManager timeManager = new ParkingTimeManager();
        LocalDateTime currentTime = timeManager.getCurrentTime();
        LocalDateTime now = LocalDateTime.now();
        long millis = Duration.between(currentTime, now).toMillis();
        Assert.assertEquals(millis,0.0,0);
    }
}
