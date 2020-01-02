import org.junit.Assert;
import org.junit.Test;

public class ParkingLotsSystemTest {

    @Test
    public void givenAParkingLot_ShouldGetAddedToTheParkingLotsManagedByTheSystem() {
        ParkingLot parkingLot1 = new ParkingLot(3);
        ParkingLot parkingLot2 = new ParkingLot(4);
        ParkingLot parkingLot3 = new ParkingLot(5);
        ParkingLotSystem parkingSystem = new ParkingLotSystem(parkingLot1,parkingLot2);
        parkingSystem.addParking(parkingLot3);
        int numberOfParkingLots = parkingSystem.getNumberOfParkingLots();
        Assert.assertEquals(3,numberOfParkingLots);
    }
}
