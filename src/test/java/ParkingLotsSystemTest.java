import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import parkinglot.parkinglotessentials.*;
import parkinglot.parkingsystemessentials.ParkedVehicleDetails;
import parkinglot.parkingsystemessentials.ParkingLotSystem;
import parkinglot.parkingsystemessentials.ParkingStrategy;
import parkinglot.vehicleessentials.Vehicle;
import parkinglot.vehicleessentials.VehicleColor;
import parkinglot.vehicleessentials.VehicleMake;

import java.util.ArrayList;
import java.util.List;
import static org.mockito.Mockito.*;

public class ParkingLotsSystemTest {

    private ParkingLot lot1;
    private ParkingLot lot2;
    private ParkingLotSystem parkingSystem;
    Vehicle vehicle1;
    private ParkedVehicleDetails vehicleDetails1;

    @Before
    public void setUp() {
        ParkingTimeManager timeManager = new ParkingTimeManager();
        this.lot1 = mock(ParkingLot.class);
        this.lot2 = mock(ParkingLot.class);
        this.vehicle1 = new Vehicle("A1", VehicleMake.BMW, VehicleColor.OTHER);
        this.vehicleDetails1 = mock(ParkedVehicleDetails.class);
        this.vehicleDetails1 = mock(ParkedVehicleDetails.class);
        this.vehicleDetails1 = mock(ParkedVehicleDetails.class);
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
            parkingSystem.parkVehicle(vehicleDetails1, ParkingStrategy.NORMAL);
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
            when(lot1.getNumberOfVehiclesParked()).thenReturn(1);
            when(lot2.getNumberOfVehiclesParked()).thenReturn(0);
            when(lot1.getParkingCapacity()).thenReturn(2);
            when(lot2.getParkingCapacity()).thenReturn(2);
            parkingSystem.parkVehicle(vehicleDetails1, ParkingStrategy.NORMAL);
            when(lot1.vehicleAlreadyPresent(vehicle1)).thenReturn(false);
            when(lot2.vehicleAlreadyPresent(vehicle1)).thenReturn(true);
            ParkingLot lot = parkingSystem.getParkingLotOInWhichThisVehicleIsParked(vehicle1);
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
            parkingSystem.parkVehicle(vehicleDetails1, ParkingStrategy.NORMAL);
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
            parkingSystem.parkVehicle(vehicleDetails1, ParkingStrategy.HANDICAP);
            when(lot1.vehicleAlreadyPresent(vehicle1)).thenReturn(true);
            when(lot2.vehicleAlreadyPresent(vehicle1)).thenReturn(false);
            ParkingLot presentLot = parkingSystem.getParkingLotOInWhichThisVehicleIsParked(vehicle1);
            Assert.assertEquals(lot1, presentLot);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenALargeVehicle_ShouldGetParkedInTheLotWithMostEmptySlots() {
        try {
            when(lot1.getParkingCapacity()).thenReturn(6);
            when(lot2.getParkingCapacity()).thenReturn(10);
            when(lot1.getNumberOfVehiclesParked()).thenReturn(4);
            when(lot2.getNumberOfVehiclesParked()).thenReturn(2);
            parkingSystem.parkVehicle(vehicleDetails1, ParkingStrategy.LARGE_VEHICLE);
            verify(lot2).parkVehicleInThisLot(vehicleDetails1);
            when(lot1.vehicleAlreadyPresent(vehicle1)).thenReturn(false);
            when(lot2.vehicleAlreadyPresent(vehicle1)).thenReturn(true);
            ParkingLot presentLot = parkingSystem.getParkingLotOInWhichThisVehicleIsParked(vehicle1);
            Assert.assertEquals(lot2, presentLot);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenAQueryToGetSlotsOfAllWhiteVehiclesInAllLots_ShouldReturnLotWiseListOfWhitVehicleWithSlotNumber() {
        List<Integer> lot1OutputList = new ArrayList<>();
        lot1OutputList.add(2);
        lot1OutputList.add(3);
        lot1OutputList.add(7);
        List<Integer> lot2OutputList = new ArrayList<>();
        lot2OutputList.add(1);
        when(lot1.getSlotNumberListOfVehiclesByColor(VehicleColor.WHITE)).thenReturn(lot1OutputList);
        when(lot2.getSlotNumberListOfVehiclesByColor(VehicleColor.WHITE)).thenReturn(lot2OutputList);
        ArrayList<List<Integer>> slotNumberListOfVehiclesByColor = parkingSystem.getSlotNumberListOfVehiclesByColor(VehicleColor.WHITE);
        Assert.assertEquals(lot1OutputList, slotNumberListOfVehiclesByColor.get(0));
        Assert.assertEquals(lot2OutputList, slotNumberListOfVehiclesByColor.get(1));
    }

    @Test
    public void givenAQueryToGetSlotsOfAllWhiteBMWVehiclesInAllLots_ShouldReturnLotWiseListOfWhitBMWVehicleWithSlotNumber() {
        List<ParkingDetailsDTO> lot1OutputList = new ArrayList<>();
        lot1OutputList.add(new ParkingDetailsDTO(1,"A1","Ramu"));
        lot1OutputList.add(new ParkingDetailsDTO(4,"A2","shamu"));
        lot1OutputList.add(new ParkingDetailsDTO(5,"A3","Ramu"));
        List<ParkingDetailsDTO> lot2OutputList = new ArrayList<>();
        lot2OutputList.add(new ParkingDetailsDTO(3,"b3","kaka"));
        when(lot1.getSlotNumberListOfVehiclesByMakeAndColor(VehicleMake.BMW,VehicleColor.WHITE)).thenReturn(lot1OutputList);
        when(lot2.getSlotNumberListOfVehiclesByMakeAndColor(VehicleMake.BMW,VehicleColor.WHITE)).thenReturn(lot2OutputList);
        ArrayList<List<ParkingDetailsDTO>> slotNumberListOfVehiclesByMakeAndColor = parkingSystem.getSlotNumberListOfVehiclesByMakeAndColor(VehicleMake.BMW,VehicleColor.WHITE);
        Assert.assertEquals(lot1OutputList, slotNumberListOfVehiclesByMakeAndColor.get(0));
        Assert.assertEquals(lot2OutputList, slotNumberListOfVehiclesByMakeAndColor.get(1));
    }
}
