import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import parkinglot.parkinglotessentials.ParkingLot;
import parkinglot.parkinglotessentials.ParkingLotException;
import parkinglot.parkinglotessentials.ParkingTimeManager;
import parkinglot.parkingsystemessentials.DriverType;
import parkinglot.parkingsystemessentials.ParkedVehicleDetails;
import parkinglot.parkingsystemessentials.ParkingLotSystem;
import parkinglot.parkingsystemessentials.VehicleSize;

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
//        this.lot1 = new parkinglot.parkinglotessentials.ParkingLot(3);
//        this.lot2 = new parkinglot.parkinglotessentials.ParkingLot(5);
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
            when(lot1.getNumberOfVehiclesParked()).thenReturn(0);
            when(lot2.getNumberOfVehiclesParked()).thenReturn(0);
            when(lot1.getParkingCapacity()).thenReturn(2);
            when(lot2.getParkingCapacity()).thenReturn(2);
            ParkedVehicleDetails details = new ParkedVehicleDetails(DriverType.NORMAL, VehicleSize.SMALL);
            parkingSystem.parkVehicle(vehicle1, details);
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
            ParkedVehicleDetails details = new ParkedVehicleDetails(DriverType.NORMAL, VehicleSize.SMALL);
            when(lot1.getNumberOfVehiclesParked()).thenReturn(1);
            when(lot2.getNumberOfVehiclesParked()).thenReturn(0);
            when(lot1.getParkingCapacity()).thenReturn(2);
            when(lot2.getParkingCapacity()).thenReturn(2);
            parkingSystem.parkVehicle(vehicle2, details);
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
            when(lot1.getNumberOfVehiclesParked()).thenReturn(0);
            when(lot2.getNumberOfVehiclesParked()).thenReturn(0);
            when(lot1.getParkingCapacity()).thenReturn(2);
            when(lot2.getParkingCapacity()).thenReturn(2);
            ParkedVehicleDetails details = new ParkedVehicleDetails(DriverType.NORMAL, VehicleSize.SMALL);
            parkingSystem.parkVehicle(vehicle1, details);
            when(lot1.vehicleAlreadyPresent(vehicle1)).thenReturn(true);
            when(lot2.vehicleAlreadyPresent(vehicle1)).thenReturn(false);
            ParkingLot presentLot = parkingSystem.getParkingLotOInWhichThisVehicleIsParked(vehicle1);
            Assert.assertEquals(lot1, presentLot);
            parkingSystem.unParkVehicle(vehicle1);
            verify(lot1).unParkFromParkingLot(vehicle1);
            when(lot1.vehicleAlreadyPresent(vehicle1)).thenReturn(false);
            when(lot2.vehicleAlreadyPresent(vehicle1)).thenReturn(false);
            ParkingLot absentLot = parkingSystem.getParkingLotOInWhichThisVehicleIsParked(vehicle1);
        } catch (ParkingLotException e) {
            e.printStackTrace();
            Assert.assertEquals(ParkingLotException.ExceptionType.NO_SUCH_CAR_PARKED, e.type);
        }
    }

    @Test
    public void givenARequestToUnParkAVehicle_ThatWasNotParked_ShouldGetUnParked() {
        try {
            parkingSystem.unParkVehicle(vehicle1);
            when(lot1.vehicleAlreadyPresent(vehicle1)).thenReturn(false);
            when(lot2.vehicleAlreadyPresent(vehicle1)).thenReturn(false);
            ParkingLot absentLot = parkingSystem.getParkingLotOInWhichThisVehicleIsParked(vehicle1);
        } catch (ParkingLotException e) {
            e.printStackTrace();
            Assert.assertEquals(ParkingLotException.ExceptionType.NO_SUCH_CAR_PARKED, e.type);
        }
    }

    @Test
    public void givenAVehicleWithHandicappedDriver_IfFirstLotHasEmptySlots_TheVehicleShouldGetParkedInTheFirstParkingLot() {
        try {
            when(lot1.getNumberOfVehiclesParked()).thenReturn(1);
            when(lot2.getNumberOfVehiclesParked()).thenReturn(0);
            when(lot1.getParkingCapacity()).thenReturn(2);
            when(lot2.getParkingCapacity()).thenReturn(2);
            ParkedVehicleDetails details = new ParkedVehicleDetails(DriverType.HANDICAPPED, VehicleSize.SMALL);
            parkingSystem.parkVehicle(vehicle2, details);
            when(lot1.vehicleAlreadyPresent(vehicle2)).thenReturn(true);
            when(lot2.vehicleAlreadyPresent(vehicle2)).thenReturn(false);
            ParkingLot presentLot = parkingSystem.getParkingLotOInWhichThisVehicleIsParked(vehicle2);
            Assert.assertEquals(lot1, presentLot);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenALargeVehicle_ShouldGetParkedInTheLotWithMostEmptySlots() {
        try {
            Object largeVehicle = new Object();
            ParkedVehicleDetails details = new ParkedVehicleDetails(DriverType.HANDICAPPED, VehicleSize.LARGE);
            when(lot1.getParkingCapacity()).thenReturn(6);
            when(lot2.getParkingCapacity()).thenReturn(10);
            when(lot1.getNumberOfVehiclesParked()).thenReturn(4);
            when(lot2.getNumberOfVehiclesParked()).thenReturn(2);
            parkingSystem.parkVehicle(largeVehicle, details);
            verify(lot2).parkVehicleInThisLot(largeVehicle);
            when(lot1.vehicleAlreadyPresent(largeVehicle)).thenReturn(false);
            when(lot2.vehicleAlreadyPresent(largeVehicle)).thenReturn(true);
            ParkingLot presentLot = parkingSystem.getParkingLotOInWhichThisVehicleIsParked(largeVehicle);
            Assert.assertEquals(lot2, presentLot);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }
}
