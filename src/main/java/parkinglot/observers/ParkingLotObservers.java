package parkinglot.observers;

public enum ParkingLotObservers {
    OWNER(false), AIRPORT_SECURITY(false);
    public boolean isParkingFull;

    ParkingLotObservers(boolean isParkingFull) {
        this.isParkingFull = isParkingFull;
    }

}
