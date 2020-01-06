package parkinglot.parkinglotessentials;

public class ParkingDetailsDTO {
    public int slotNumber;
    public String numberPlate;
    public String parkingAttendantName;

    public ParkingDetailsDTO(int slotNumber, String numberPlate, String parkingAttendantName) {
        this.slotNumber = slotNumber;
        this.numberPlate = numberPlate;
        this.parkingAttendantName = parkingAttendantName;
    }

    public ParkingDetailsDTO(Slot slot) {
        this.slotNumber = slot.getSlotNumber();
        this.numberPlate = slot.getVehicle().getNumberPlate();
        this.parkingAttendantName = slot.getDetails().getAttendantName();
    }
}
