import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ParkingLotTest {

    ParkingLot parkingLot;
    Object vehicle;

    @Before
    public void setup() {
        this.parkingLot = new ParkingLot(2);
        this.vehicle = new Object();
    }

    @Test
    public void givenAVehicle_WhenParkedInParkingLot_ShouldReturnTrue() {
        try {
            parkingLot.parkTheCar(vehicle);
            int isParked = parkingLot.isThisCarPresentInTheParkingLot(vehicle);
            Assert.assertEquals(0,isParked);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void givenAVehicle_WhenUnParked_ShouldReturnTrue() {
        try {
            parkingLot.parkTheCar(vehicle);
            parkingLot.unParkTheCar(vehicle);
            int thisCarPresentInTheParkingLot = parkingLot.isThisCarPresentInTheParkingLot(vehicle);
            Assert.assertEquals(-1,thisCarPresentInTheParkingLot);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenAVehicle_WhenTriedToUnParkedEvenWhenItWasNotParked_ShouldReturnFalse() {
        try {
            parkingLot.parkTheCar(vehicle);
            Object vehicle2 = new Object();
            parkingLot.unParkTheCar(vehicle2);
        } catch (ParkingLotException e) {
            e.printStackTrace();
            Assert.assertEquals(ParkingLotException.ExceptionType.NO_SUCH_CAR_PARKED, e.type);
        }
    }

    @Test
    public void givenAParkingLotWithASize_WhenCapacityIsFull_ShouldThrowAnException() {
        try {
            parkingLot.parkTheCar(vehicle);
            Object vehicle2 = new Object();
            parkingLot.parkTheCar(vehicle2);
            Object vehicle3 = new Object();
            parkingLot.parkTheCar(vehicle3);
        } catch (ParkingLotException e) {
            e.printStackTrace();
            Assert.assertEquals(ParkingLotException.ExceptionType.PARKING_CAPACITY_FULL, e.type);
        }
    }

    @Test
    public void givenAVehicle_IfTriedToRePark_ShouldThrowAnException() {
        try {
            parkingLot.parkTheCar(vehicle);
            parkingLot.parkTheCar(vehicle);
        } catch (ParkingLotException e) {
            e.printStackTrace();
            Assert.assertEquals(ParkingLotException.ExceptionType.CAR_ALREADY_PARKED, e.type);
        }
    }

    @Test
    public void whenParkingCapacityIsFull_AndOwnerIsInformedAboutIt_ShouldReturnTrue() {
        try {
            this.parkingLot.parkTheCar(vehicle);
            Object vehicle2 = new Object();
            parkingLot.parkTheCar(vehicle2);
            Object vehicle3 = new Object();
            parkingLot.parkTheCar(vehicle3);
        } catch (ParkingLotException e) {
            e.printStackTrace();
            Assert.assertEquals(ParkingLotException.ExceptionType.PARKING_CAPACITY_FULL, e.type);
            Assert.assertTrue(ParkingLotObservers.OWNER.isParkingFull);
        }
    }

    @Test
    public void whenParkingCapacityIsFull_AndAllTheObserversAreInformedAboutIt_ShouldReturnTrue() {
        try {
            this.parkingLot.parkTheCar(vehicle);
            Object vehicle2 = new Object();
            parkingLot.parkTheCar(vehicle2);
            Object vehicle3 = new Object();
            parkingLot.parkTheCar(vehicle3);
        } catch (ParkingLotException e) {
            e.printStackTrace();
            Assert.assertEquals(ParkingLotException.ExceptionType.PARKING_CAPACITY_FULL, e.type);
            Assert.assertTrue(ParkingLotObservers.OWNER.isParkingFull);
            Assert.assertTrue(ParkingLotObservers.AIRPORT_SECURITY.isParkingFull);
        }
    }

}
