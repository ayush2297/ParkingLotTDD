import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SlotAllotmentTest {

    private SlotAllotment slotAllotment;
    private Object vehicle;

    @Before
    public void setup() {
        this.slotAllotment = new SlotAllotment(2);
        this.vehicle = new Object();
    }

    @Test
    public void givenNoVehiclesParked_ShouldReturnUnoccupiedListSizeAs0() {
        int slotsAvailable = this.slotAllotment.availableParkingSlots.size();
        Assert.assertEquals(2,slotsAvailable);
    }

    @Test
    public void givenAVehicleToPark_InAnEmptyOccupiedList_ShouldReturnSize1() {
        slotAllotment.parkUpdate(1);
        Assert.assertEquals(1, slotAllotment.availableParkingSlots.size());
    }

    @Test
    public void givenAVehicleToUnPark_InAnOccupiedListWhichHas1Car_AfterUnParkShouldReturnSize0() {
        slotAllotment.parkUpdate(1);
        Assert.assertEquals(1, slotAllotment.availableParkingSlots.size());
        slotAllotment.unParkUpdate(1);
        Assert.assertEquals(2, slotAllotment.availableParkingSlots.size());
    }

    @Test
    public void givenAnEmptyParkingLot_WhenAskedForNearestParkingSlot_ShouldReturnSlot0() {
        try {
            Assert.assertEquals(1,slotAllotment.getNearestParkingSlot());
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenAFullyOccupiedParkingLot_WhenAskedForNearestParkingSlot_ShouldThrowParkingFullException() {
        try {
            slotAllotment.parkUpdate(1);
            Object vehicle1 = new Object();
            slotAllotment.parkUpdate(2);
            slotAllotment.getNearestParkingSlot();
        } catch (ParkingLotException e) {
            e.printStackTrace();
            Assert.assertEquals(ParkingLotException.ExceptionType.PARKING_CAPACITY_FULL,e.type);
        }
    }
}
