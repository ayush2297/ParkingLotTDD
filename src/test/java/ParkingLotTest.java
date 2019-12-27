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


    @Test
    public void givenAVehicle_WhenUnparked_ShouldReturnTrue() {
        ParkingLot parkingLot = new ParkingLot();
        Object vehicle = new Object();
        parkingLot.parkTheCar(vehicle);
        boolean isUnParked = parkingLot.unParkTheCar(vehicle);
        Assert.assertTrue(isUnParked);
    }

}
