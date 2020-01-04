import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import parkinglot.parkinglotessentials.ParkingLot;
import parkinglot.parkinglotessentials.ParkingLotException;
import parkinglot.parkinglotessentials.ParkingTimeManager;
import parkinglot.parkinglotessentials.SlotAllotment;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class ParkingLotTest {

    ParkingLot parkingLot;
    Object vehicle;
    Object vehicle1;
    Object vehicle2;
    Object vehicle3;

    private SlotAllotment mockedSlotAllotment;

    @Before
    public void setup() {
        this.mockedSlotAllotment = mock(SlotAllotment.class);
        this.parkingLot = new ParkingLot(2);
        parkingLot.setSlotAllotment(this.mockedSlotAllotment);
        this.vehicle = new Object();
        this.vehicle1 = new Object();
        this.vehicle2 = new Object();
        this.vehicle3 = new Object();
        parkingLot.setParkingTimeManager(new ParkingTimeManager());
    }

    @Test
    public void givenAVehicle_WhenParkedInParkingLot_ShouldReturnTrue() {
        try {
            when(mockedSlotAllotment.getParkingSlot()).thenReturn(1);
            parkingLot.parkVehicleInThisLot(vehicle);
            int isParked = parkingLot.FindSlotOfThisVehicle(vehicle);
            Assert.assertEquals(0, isParked);
            verify(mockedSlotAllotment).parkUpdate(1);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenAVehicle_WhenUnParked_ShouldReturnTrue() {
        try {
            when(mockedSlotAllotment.getParkingSlot()).thenReturn(1);
            parkingLot.parkVehicleInThisLot(vehicle);
            verify(mockedSlotAllotment).parkUpdate(1);
            parkingLot.unParkFromParkingLot(vehicle);
            verify(mockedSlotAllotment).unParkUpdate(1);
            int thisCarPresentInTheParkingLot = parkingLot.FindSlotOfThisVehicle(vehicle);
            Assert.assertEquals(-1, thisCarPresentInTheParkingLot);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenAVehicle_WhenTriedToUnParkedEvenWhenItWasNotParked_ShouldReturnFalse() {
        try {
            parkingLot.unParkFromParkingLot(vehicle);
        } catch (ParkingLotException e) {
            e.printStackTrace();
            Assert.assertEquals(ParkingLotException.ExceptionType.NO_SUCH_CAR_PARKED, e.type);
        }
    }

    @Test
    public void givenAParkingLotWithSize2_WhenCapacityIsFull_ShouldThrowAnException() {
        try {
            when(mockedSlotAllotment.getParkingSlot()).thenReturn(1);
            parkingLot.parkVehicleInThisLot(vehicle);
            verify(mockedSlotAllotment).parkUpdate(1);
            when(mockedSlotAllotment.getParkingSlot()).thenReturn(2);
            parkingLot.parkVehicleInThisLot(vehicle2);
            verify(mockedSlotAllotment).parkUpdate(2);
            when(mockedSlotAllotment.getParkingSlot()).thenThrow(new ParkingLotException("No parking space available!!",
                    ParkingLotException.ExceptionType.PARKING_CAPACITY_FULL));
            parkingLot.parkVehicleInThisLot(vehicle3);
        } catch (ParkingLotException e) {
            e.printStackTrace();
            Assert.assertEquals(ParkingLotException.ExceptionType.PARKING_CAPACITY_FULL, e.type);
        }
    }

    @Test
    public void givenAVehicle_IfTriedToRePark_ShouldThrowAnException() {
        try {
            when(mockedSlotAllotment.getParkingSlot()).thenReturn(1);
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
            when(mockedSlotAllotment.getParkingSlot()).thenReturn(1);
            parkingLot.parkVehicleInThisLot(vehicle);
            when(mockedSlotAllotment.getParkingSlot()).thenReturn(2);
            parkingLot.parkVehicleInThisLot(vehicle2);
            when(mockedSlotAllotment.getParkingSlot()).thenThrow(new ParkingLotException("No parking space available!!",
                    ParkingLotException.ExceptionType.PARKING_CAPACITY_FULL));
            parkingLot.parkVehicleInThisLot(vehicle3);
        } catch (ParkingLotException e) {
            e.printStackTrace();
            Assert.assertEquals(ParkingLotException.ExceptionType.PARKING_CAPACITY_FULL, e.type);
        }
    }

    @Test
    public void whenParkingCapacityIsFull_AndAllTheObserversAreInformedAboutIt_ShouldReturnTrue() {
        try {
            when(mockedSlotAllotment.getParkingSlot()).thenReturn(1);
            this.parkingLot.parkVehicleInThisLot(vehicle);
            when(mockedSlotAllotment.getParkingSlot()).thenReturn(2);
            parkingLot.parkVehicleInThisLot(vehicle2);
            when(mockedSlotAllotment.getParkingSlot()).thenThrow(new ParkingLotException("No parking space available!!",
                    ParkingLotException.ExceptionType.PARKING_CAPACITY_FULL));
            parkingLot.parkVehicleInThisLot(vehicle3);
        } catch (ParkingLotException e) {
            e.printStackTrace();
            Assert.assertEquals(ParkingLotException.ExceptionType.PARKING_CAPACITY_FULL, e.type);
        }
    }

    //*************** U C 6
    @Test
    public void givenARequestToViewAllAvailableSlots_ShoudlRetunAllAvailableSlots() {
        List<Integer> tempSlotList = new ArrayList<>();
        tempSlotList.add(1);
        tempSlotList.add(2);
        when(mockedSlotAllotment.getAvailableSlotsList()).thenReturn(tempSlotList);
        List availableSlots = this.parkingLot.getAvailableSlots();
        Assert.assertEquals(2, availableSlots.size());
    }

    @Test
    public void givenARequestFromOwnerToParkAtGivenSlot_SystemShouldAllotParkingSlotAccordingly() {
        try {
            parkingLot.parkVehicleAtSpecifiedSlot(2, vehicle);
            int vehicleSlot = parkingLot.FindSlotOfThisVehicle(vehicle) + 1;
            verify(mockedSlotAllotment).parkUpdate(2);
            Assert.assertEquals(2, vehicleSlot);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    // **************** U C 7
    @Test
    public void givenARequestToFindAVehicleWhichIsParked_ShouldReturnSlotNumber() {
        try {
            when(mockedSlotAllotment.getParkingSlot()).thenReturn(1);
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
            when(mockedSlotAllotment.getParkingSlot()).thenReturn(1);
            parkingLot.parkVehicleInThisLot(vehicle);
            verify(mockedSlotAllotment).parkUpdate(1);
            int tempSlot = parkingLot.FindSlotOfThisVehicle(vehicle);
            Assert.assertEquals(currTime, parkingLot.getVehicleTimingDetails(vehicle));
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenAQueryToGetCapacityOfTheLot_ShouldReturnTheCapacityOfTheLot() {
        int parkingCapacity = parkingLot.getParkingCapacity();
        Assert.assertEquals(2, parkingCapacity);
    }

    @Test
    public void givenAQueryToGetCountOfCarsParkedInTheLot_ShouldReturnTheCarsCountFromTheLot() {
        try {
            when(mockedSlotAllotment.getParkingSlot()).thenReturn(1);
            parkingLot.parkVehicleInThisLot(vehicle);
            verify(mockedSlotAllotment).parkUpdate(1);
            int numberOfVehiclesParked = parkingLot.getNumberOfVehiclesParked();
            Assert.assertEquals(1, numberOfVehiclesParked);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }
}

