import org.junit.Assert;
import org.junit.Test;

public class ParkingLotTest {

    @Test
    public void givenAVehicle_WhenParkedInParkingLot_ShouldReturnTrue() {
        ParkingLot parkingLot = new ParkingLot();
        Object vehicle = new Object();
        boolean isParked = parkingLot.parkTheCar(vehicle);
        Assert.assertTrue(isParked);
    }

}
