import java.time.LocalDateTime;
import java.util.Objects;

public class Slot {

    public Object vehicle;
    public LocalDateTime parkingStartTime;

    public Slot(Object vehicle) {
        this.vehicle = vehicle;
    }

    public Slot(Object vehicle, LocalDateTime parkingStartTime) {
        this.vehicle = vehicle;
        this.parkingStartTime = parkingStartTime;
    }

    public Object getVehicle() {
        return vehicle;
    }

    public void setVehicle(Object vehicle) {
        this.vehicle = vehicle;
    }

    public LocalDateTime getParkingStartTime() {
        return parkingStartTime;
    }

    public void setParkingStartTime(LocalDateTime parkingStartTime) {
        this.parkingStartTime = parkingStartTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Slot slot = (Slot) o;
        return Objects.equals(vehicle, slot.vehicle);
    }

}
