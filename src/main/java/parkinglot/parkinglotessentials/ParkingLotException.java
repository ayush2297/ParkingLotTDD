package parkinglot.parkinglotessentials;

public class ParkingLotException extends Exception {
    public enum ExceptionType {
        CAR_ALREADY_PARKED, NO_SUCH_CAR_PARKED, PARKING_CAPACITY_FULL;
    }
    public ExceptionType type;

    public ParkingLotException(String message, ExceptionType type) {
        super(message);
        this.type = type;
    }
}
