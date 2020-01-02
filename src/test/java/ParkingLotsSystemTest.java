import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ParkingLotsSystemTest {

    private ParkingLot lot1;
    private ParkingLot lot2;
    private ParkingLotSystem parkingSystem;
    private Object vehicle1;
    private Object vehicle2;

    @Before
    public void setUp() {
        ParkingTimeManager timeManager = new ParkingTimeManager();
        this.lot1 = new ParkingLot(3,timeManager);
        this.lot2 = new ParkingLot(4,timeManager);
        this.vehicle1 = new Object();
        this.vehicle2 = new Object();
        this.parkingSystem = new ParkingLotSystem(lot1, lot2);
    }

    //************** U C 9 A
    @Test
    public void givenAParkingLot_ShouldGetAddedToTheParkingLotsManagedByTheSystem() {
        ParkingLot parkingLot3 = new ParkingLot(5);
        parkingSystem.addParking(parkingLot3);
        int numberOfParkingLots = parkingSystem.getNumberOfParkingLots();
        Assert.assertEquals(3,numberOfParkingLots);
    }

    //*************** U C 9 B
    @Test
    public void givenThatAllParkingLotsAreEmpty_FirstVehicleShouldGetParkedInFirstParkingLot() {
        try {
            parkingSystem.parkVehicle(vehicle1);
            ParkingLot lot = parkingSystem.getParkingLotOInWhichThisVehicleIsParked(vehicle1);
            Assert.assertEquals(lot1,lot);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenThatAllParkingLotsAreEmpty_SecondVehicleShouldGetParkedInSecondParkingLot() {
        try {
            parkingSystem.parkVehicle(vehicle1);
            parkingSystem.parkVehicle(vehicle2);
            ParkingLot lot = parkingSystem.getParkingLotOInWhichThisVehicleIsParked(vehicle2);
            Assert.assertEquals(lot2,lot);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }
}
