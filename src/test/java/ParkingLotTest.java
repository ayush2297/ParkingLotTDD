import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import parkinglot.parkinglotessentials.*;
import parkinglot.parkingsystemessentials.ParkedVehicleDetails;
import parkinglot.vehicleessentials.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class ParkingLotTest {

    ParkingLot parkingLot;
    Vehicle vehicle4;
    Vehicle vehicle1;
    Vehicle vehicle2;
    Vehicle vehicle3;

    private SlotAllotment mockedSlotAllotment;
    private ParkedVehicleDetails vehicleDetails1;
    private ParkedVehicleDetails vehicleDetails2;
    private ParkedVehicleDetails vehicleDetails3;
    private ParkedVehicleDetails vehicleDetails4;

    @Before
    public void setup() {
        this.mockedSlotAllotment = mock(SlotAllotment.class);
        this.parkingLot = new ParkingLot(2);
        parkingLot.setSlotAllotment(this.mockedSlotAllotment);
        this.vehicle1 = new Vehicle("A1",VehicleMake.BMW, VehicleColor.OTHER);
        this.vehicle2 = new Vehicle("A2",VehicleMake.BMW, VehicleColor.WHITE);
        this.vehicle3 = new Vehicle("A3",VehicleMake.BMW, VehicleColor.OTHER);
        this.vehicle4 = new Vehicle("A4",VehicleMake.BMW, VehicleColor.WHITE);
        this.vehicleDetails1 = new ParkedVehicleDetails(vehicle1, DriverType.NORMAL, VehicleSize.SMALL);
        this.vehicleDetails2 = new ParkedVehicleDetails(vehicle2, DriverType.NORMAL, VehicleSize.SMALL);
        this.vehicleDetails3 = new ParkedVehicleDetails(vehicle3, DriverType.NORMAL, VehicleSize.SMALL);
        this.vehicleDetails4 = new ParkedVehicleDetails(vehicle4, DriverType.NORMAL, VehicleSize.SMALL);
        parkingLot.setParkingTimeManager(new ParkingTimeManager());
    }

    @Test
    public void givenAVehicle_WhenParkedInParkingLot_ShouldReturnTrue() {
        try {
            when(mockedSlotAllotment.getParkingSlot()).thenReturn(1);
            parkingLot.parkVehicleInThisLot(vehicleDetails1);
            int isParked = parkingLot.FindSlotOfThisVehicle(vehicle1);
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
            parkingLot.parkVehicleInThisLot(vehicleDetails1);
            verify(mockedSlotAllotment).parkUpdate(1);
            parkingLot.unParkFromParkingLot(vehicle1);
            verify(mockedSlotAllotment).unParkUpdate(1);
            int thisCarPresentInTheParkingLot = parkingLot.FindSlotOfThisVehicle(vehicle1);
            Assert.assertEquals(-1, thisCarPresentInTheParkingLot);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenAVehicle_WhenTriedToUnParkedEvenWhenItWasNotParked_ShouldReturnFalse() {
        try {
            parkingLot.unParkFromParkingLot(vehicle1);
        } catch (ParkingLotException e) {
            e.printStackTrace();
            Assert.assertEquals(ParkingLotException.ExceptionType.NO_SUCH_CAR_PARKED, e.type);
        }
    }

    @Test
    public void givenAParkingLotWithSize2_WhenCapacityIsFull_ShouldThrowAnException() {
        try {
            when(mockedSlotAllotment.getParkingSlot()).thenReturn(1);
            parkingLot.parkVehicleInThisLot(vehicleDetails1);
            verify(mockedSlotAllotment).parkUpdate(1);
            when(mockedSlotAllotment.getParkingSlot()).thenReturn(2);
            parkingLot.parkVehicleInThisLot(vehicleDetails2);
            verify(mockedSlotAllotment).parkUpdate(2);
            when(mockedSlotAllotment.getParkingSlot()).thenThrow(new ParkingLotException("No parking space available!!",
                    ParkingLotException.ExceptionType.PARKING_CAPACITY_FULL));
            parkingLot.parkVehicleInThisLot(vehicleDetails3);
        } catch (ParkingLotException e) {
            e.printStackTrace();
            Assert.assertEquals(ParkingLotException.ExceptionType.PARKING_CAPACITY_FULL, e.type);
        }
    }

    @Test
    public void givenAVehicle_IfTriedToRePark_ShouldThrowAnException() {
        try {
            when(mockedSlotAllotment.getParkingSlot()).thenReturn(1);
            parkingLot.parkVehicleInThisLot(vehicleDetails1);
            parkingLot.parkVehicleInThisLot(vehicleDetails1);
        } catch (ParkingLotException e) {
            e.printStackTrace();
            Assert.assertEquals(ParkingLotException.ExceptionType.CAR_ALREADY_PARKED, e.type);
        }
    }

    @Test
    public void whenParkingCapacityIsFull_AndOwnerIsInformedAboutIt_ShouldReturnTrue() {
        try {
            when(mockedSlotAllotment.getParkingSlot()).thenReturn(1);
            parkingLot.parkVehicleInThisLot(vehicleDetails1);
            when(mockedSlotAllotment.getParkingSlot()).thenReturn(2);
            parkingLot.parkVehicleInThisLot(vehicleDetails2);
            when(mockedSlotAllotment.getParkingSlot()).thenThrow(new ParkingLotException("No parking space available!!",
                    ParkingLotException.ExceptionType.PARKING_CAPACITY_FULL));
            parkingLot.parkVehicleInThisLot(vehicleDetails3);
        } catch (ParkingLotException e) {
            e.printStackTrace();
            Assert.assertEquals(ParkingLotException.ExceptionType.PARKING_CAPACITY_FULL, e.type);
        }
    }

    @Test
    public void whenParkingCapacityIsFull_AndAllTheObserversAreInformedAboutIt_ShouldReturnTrue() {
        try {
            when(mockedSlotAllotment.getParkingSlot()).thenReturn(1);
            this.parkingLot.parkVehicleInThisLot(vehicleDetails1);
            when(mockedSlotAllotment.getParkingSlot()).thenReturn(2);
            parkingLot.parkVehicleInThisLot(vehicleDetails2);
            when(mockedSlotAllotment.getParkingSlot()).thenThrow(new ParkingLotException("No parking space available!!",
                    ParkingLotException.ExceptionType.PARKING_CAPACITY_FULL));
            parkingLot.parkVehicleInThisLot(vehicleDetails3);
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
            parkingLot.parkVehicleAtSpecifiedSlot(2, vehicleDetails1);
            int vehicleSlot = parkingLot.FindSlotOfThisVehicle(vehicle1) + 1;
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
            parkingLot.parkVehicleInThisLot(vehicleDetails1);
            int vehicleSlot = parkingLot.FindSlotOfThisVehicle(vehicle1);
            Assert.assertEquals(0, vehicleSlot);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenARequestToFindAVehicleWhichIsNotParked_ShouldReturnNegative1() {
        int vehicleSlot = parkingLot.FindSlotOfThisVehicle(vehicle1);
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
            parkingLot.parkVehicleInThisLot(vehicleDetails1);
            verify(mockedSlotAllotment).parkUpdate(1);
            int tempSlot = parkingLot.FindSlotOfThisVehicle(vehicle1);
            Assert.assertEquals(currTime, parkingLot.getVehicleTimingDetails(vehicle1));
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
            parkingLot.parkVehicleInThisLot(vehicleDetails1);
            verify(mockedSlotAllotment).parkUpdate(1);
            int numberOfVehiclesParked = parkingLot.getNumberOfVehiclesParked();
            Assert.assertEquals(1, numberOfVehiclesParked);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenAVehicle_IfPresentInTheParkingLot_ShouldReturnTrue() {
        try {
            when(mockedSlotAllotment.getParkingSlot()).thenReturn(1);
            parkingLot.parkVehicleInThisLot(vehicleDetails1);
            boolean vehiclePresent = parkingLot.vehicleAlreadyPresent(vehicle1);
            Assert.assertTrue(vehiclePresent);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenAVehicle_IfNotPresentInTheParkingLot_ShouldReturnFalse() {
        boolean vehiclePresent = parkingLot.vehicleAlreadyPresent(vehicle1);
        Assert.assertFalse(vehiclePresent);
    }

    @Test
    public void givenAQueryToGetSlotsOfAllWhiteVehicles_ShouldReturnListOfWhitetVehicleSlotNumber() {
        try {
            parkingLot.setSlotAllotment(new SlotAllotment(2));
            parkingLot.parkVehicleInThisLot(vehicleDetails2);
            parkingLot.parkVehicleInThisLot(vehicleDetails4);
            List<Integer> slotNumberListOfVehiclesByColor = parkingLot.getSlotNumberListOfVehiclesByColor(VehicleColor.WHITE);
            Assert.assertEquals(2, slotNumberListOfVehiclesByColor.size());
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenARequestToGetSlotsOfAllBlueToyotas_ShouldReturnListOfSimilarVehiclesSlotNumber() {
        try {
            parkingLot.setSlotAllotment(new SlotAllotment(2));
            parkingLot.parkVehicleInThisLot(vehicleDetails2);
            parkingLot.parkVehicleInThisLot(vehicleDetails4);
            List<ParkingDetailsDTO> slotNumberListOfVehiclesBy = parkingLot.getSlotNumberListOfVehiclesByMakeAndColor(VehicleMake.BMW,VehicleColor.WHITE);
            Assert.assertEquals(2, slotNumberListOfVehiclesBy.size());
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

}

