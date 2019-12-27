public enum ParkingLotObserversEnum {
    OWNER(false), AIRPORT_SECURITY(false);
    public boolean isParkingFull;

    ParkingLotObserversEnum(boolean isParkingFull) {
        this.isParkingFull = isParkingFull;
    }
}
