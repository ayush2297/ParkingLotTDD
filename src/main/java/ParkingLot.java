public class ParkingLot {
    private Object parkedVehicle;

    public boolean parkTheCar(Object vehicle) {
        this.parkedVehicle = vehicle;
        return true;
    }

    public boolean unParkTheCar(Object vehicle) {
        if (this.parkedVehicle.equals(vehicle)) {
            return true;
        }
        return false;
    }
}
