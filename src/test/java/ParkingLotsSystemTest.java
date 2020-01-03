import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;

public class ParkingLotsSystemTest {

    private ParkingLot lot1;
    private ParkingLot lot2;
    private ParkingLotSystem parkingSystem;
    private Object vehicle1;
    private Object vehicle2;

    @Before
    public void setUp() {
        ParkingTimeManager timeManager = new ParkingTimeManager();
        this.lot1 = mock(ParkingLot.class);
        this.lot2 = mock(ParkingLot.class);
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
        Assert.assertEquals(3, numberOfParkingLots);

    }

    //*************** U C 9 B
    @Test
    public void givenThatAllParkingLotsAreEmpty_FirstVehicleShouldGetParkedInFirstParkingLot() {
        try {
            parkingSystem.parkVehicle(vehicle1);
            when(lot1.vehicleAlreadyPresent(vehicle1)).thenReturn(true);
            when(lot2.vehicleAlreadyPresent(vehicle1)).thenReturn(false);
            ParkingLot lot = parkingSystem.getParkingLotOInWhichThisVehicleIsParked(vehicle1);
            Assert.assertEquals(lot1, lot);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void givenThatAllParkingLotsAreEmpty_SecondVehicleShouldGetParkedInSecondParkingLot() {
        try {
            parkingSystem.parkVehicle(vehicle1);
            parkingSystem.parkVehicle(vehicle2);
            Object vehicle3 = new Object();
            Object vehicle4 = new Object();
            parkingSystem.parkVehicle(vehicle3);
            parkingSystem.parkVehicle(vehicle4);
            when(lot1.vehicleAlreadyPresent(vehicle1)).thenReturn(true);
            when(lot2.vehicleAlreadyPresent(vehicle1)).thenReturn(false);
            when(lot1.vehicleAlreadyPresent(vehicle2)).thenReturn(false);
            when(lot2.vehicleAlreadyPresent(vehicle2)).thenReturn(true);
            ParkingLot lot = parkingSystem.getParkingLotOInWhichThisVehicleIsParked(vehicle2);
            Assert.assertEquals(lot2, lot);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenARequestToUnParkAVehicle_ShouldGetUnParked() {
        try {
            parkingSystem.parkVehicle(vehicle1);
            parkingSystem.UnParkVehicle(vehicle1);
            when(lot1.vehicleAlreadyPresent(vehicle1)).thenReturn(false);
            when(lot2.vehicleAlreadyPresent(vehicle1)).thenReturn(false);
            ParkingLot lot = parkingSystem.getParkingLotOInWhichThisVehicleIsParked(vehicle1);
        } catch (ParkingLotException e) {
            e.printStackTrace();
            Assert.assertEquals(ParkingLotException.ExceptionType.NO_SUCH_CAR_PARKED, e.type);
        }
    }

}
