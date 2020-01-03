import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ParkingLotTest {

    ParkingLot parkingLot;
    Object vehicle;

    @Before
    public void setup() {
        this.parkingLot = new ParkingLot(2);
        this.vehicle = new Object();
        parkingLot.setParkingTimeManager(new ParkingTimeManager());
    }

    @Test
    public void givenAVehicle_WhenParkedInParkingLot_ShouldReturnTrue() {
        try {
            parkingLot.parkVehicleInThisLot(vehicle);
            int isParked = parkingLot.FindSlotOfThisVehicle(vehicle);
            Assert.assertEquals(0, isParked);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenAVehicle_WhenUnParked_ShouldReturnTrue() {
        try {
            parkingLot.parkVehicleInThisLot(vehicle);
            parkingLot.unParkFromParkingLot(vehicle);
            int thisCarPresentInTheParkingLot = parkingLot.FindSlotOfThisVehicle(vehicle);
            Assert.assertEquals(-1, thisCarPresentInTheParkingLot);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenAVehicle_WhenTriedToUnParkedEvenWhenItWasNotParked_ShouldReturnFalse() {
        try {
            parkingLot.parkVehicleInThisLot(vehicle);
            Object vehicle2 = new Object();
            parkingLot.unParkFromParkingLot(vehicle2);
        } catch (ParkingLotException e) {
            e.printStackTrace();
            Assert.assertEquals(ParkingLotException.ExceptionType.NO_SUCH_CAR_PARKED, e.type);
        }
    }

    @Test
    public void givenAParkingLotWithASize_WhenCapacityIsFull_ShouldThrowAnException() {
        try {
            parkingLot.parkVehicleInThisLot(vehicle);
            Object vehicle2 = new Object();
            parkingLot.parkVehicleInThisLot(vehicle2);
            Object vehicle3 = new Object();
            parkingLot.parkVehicleInThisLot(vehicle3);
        } catch (ParkingLotException e) {
            e.printStackTrace();
            Assert.assertEquals(ParkingLotException.ExceptionType.PARKING_CAPACITY_FULL, e.type);
        }
    }

    @Test
    public void givenAVehicle_IfTriedToRePark_ShouldThrowAnException() {
        try {
            parkingLot.parkVehicleInThisLot(vehicle);
            parkingLot.parkVehicleInThisLot(vehicle);
        } catch (ParkingLotException e) {
            e.printStackTrace();
            Assert.assertEquals(ParkingLotException.ExceptionType.CAR_ALREADY_PARKED, e.type);
        }
    }

    @Test
    public void whenParkingCapacityIsFull_AndOwnerIsInformedAboutIt_ShouldReturnTrue() {
        try {
            this.parkingLot.parkVehicleInThisLot(vehicle);
            Object vehicle2 = new Object();
            parkingLot.parkVehicleInThisLot(vehicle2);
            Object vehicle3 = new Object();
            parkingLot.parkVehicleInThisLot(vehicle3);
        } catch (ParkingLotException e) {
            e.printStackTrace();
            Assert.assertEquals(ParkingLotException.ExceptionType.PARKING_CAPACITY_FULL, e.type);
            Assert.assertTrue(ParkingLotObservers.OWNER.isParkingFull);
        }
    }

    @Test
    public void whenParkingCapacityIsFull_AndAllTheObserversAreInformedAboutIt_ShouldReturnTrue() {
        try {
            this.parkingLot.parkVehicleInThisLot(vehicle);
            Object vehicle2 = new Object();
            parkingLot.parkVehicleInThisLot(vehicle2);
            Object vehicle3 = new Object();
            parkingLot.parkVehicleInThisLot(vehicle3);
        } catch (ParkingLotException e) {
            e.printStackTrace();
            Assert.assertEquals(ParkingLotException.ExceptionType.PARKING_CAPACITY_FULL, e.type);
            Assert.assertTrue(ParkingLotObservers.OWNER.isParkingFull);
            Assert.assertTrue(ParkingLotObservers.AIRPORT_SECURITY.isParkingFull);
        }
    }

    //*************** U C 6
    @Test
    public void givenARequestToViewAllAvailableSlots_ShoudlRetunAllAvailableSlots() {
        List availableSlots = this.parkingLot.getAvailableSlots();
        Assert.assertEquals(2, availableSlots.size());
    }

    @Test
    public void givenARequestFromOwnerToParkAtGivenSlot_SystemShouldAllotParkingSlotAccordingly() {
        try {
            parkingLot.parkVehicleAtSpecifiedSlot(2, vehicle);
            int vehicleSlot = parkingLot.FindSlotOfThisVehicle(vehicle)+1;
            Assert.assertEquals(2, vehicleSlot);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    // **************** U C 7
    @Test
    public void givenARequestToFindAVehicleWhichIsParked_ShouldReturnSlotNumber() {
        try {
            parkingLot.parkVehicleInThisLot(vehicle);
            int vehicleSlot = parkingLot.FindSlotOfThisVehicle(vehicle);
            Assert.assertEquals(0, vehicleSlot);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenARequestToFindAVehicleWhichIsNotParked_ShouldReturnNegative1() {
        int vehicleSlot = parkingLot.FindSlotOfThisVehicle(vehicle);
        Assert.assertEquals(-1, vehicleSlot);
    }

    // *************** U C 8
    @Test
    public void givenAVehicleWhenParked_ShouldBeParkedWithParkingStartTime() {
        ParkingTimeManager timeManager = mock(ParkingTimeManager.class);
        parkingLot.setParkingTimeManager(timeManager);
        LocalDateTime currTime = LocalDateTime.now();
        when(timeManager.getCurrentTime()).thenReturn(currTime);
        try {
            parkingLot.parkVehicleInThisLot(vehicle);
            int tempSlot = parkingLot.FindSlotOfThisVehicle(vehicle);
            Assert.assertEquals(currTime, parkingLot.getVehicleTimingDetails(vehicle));
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

}

