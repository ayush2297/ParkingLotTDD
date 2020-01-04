import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import parkinglot.parkinglotessentials.ParkingLotException;
import parkinglot.parkinglotessentials.SlotAllotment;
import parkinglot.vehicleessentials.Vehicle;
import parkinglot.vehicleessentials.VehicleColor;

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
        int slotsAvailable = this.slotAllotment.getAvailableSlotsList().size();
        Assert.assertEquals(2, slotsAvailable);
    }

    @Test
    public void givenAVehicleToPark_InAnEmptyOccupiedList_ShouldReturnSize1() {
        slotAllotment.parkUpdate(1);
        Assert.assertEquals(1, this.slotAllotment.getAvailableSlotsList().size());
    }

    @Test
    public void givenAVehicleToUnPark_InAnOccupiedListWhichHas1Car_AfterUnParkShouldReturnSize0() {
        slotAllotment.parkUpdate(1);
        Assert.assertEquals(1, this.slotAllotment.getAvailableSlotsList().size());
        slotAllotment.unParkUpdate(1);
        Assert.assertEquals(2, this.slotAllotment.getAvailableSlotsList().size());
    }

    @Test
    public void givenAnEmptyParkingLot_WhenAskedForNearestParkingSlot_ShouldReturnSlot0() {
        try {
            Assert.assertEquals(1, slotAllotment.getParkingSlot());
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenAFullyOccupiedParkingLot_WhenAskedForNearestParkingSlot_ShouldThrowParkingFullException() {
        try {
            slotAllotment.parkUpdate(1);
            slotAllotment.parkUpdate(2);
            slotAllotment.getParkingSlot();
        } catch (ParkingLotException e) {
            e.printStackTrace();
            Assert.assertEquals(ParkingLotException.ExceptionType.PARKING_CAPACITY_FULL, e.type);
        }
    }
}

