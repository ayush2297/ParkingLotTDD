package parkinglot.parkingsystemessentials;

import parkinglot.parkinglotessentials.ParkingDetailsDTO;
import parkinglot.parkinglotessentials.ParkingLot;
import parkinglot.parkinglotessentials.ParkingLotException;
import parkinglot.parkinglotessentials.Slot;
import parkinglot.vehicleessentials.Vehicle;
import parkinglot.vehicleessentials.VehicleColor;
import parkinglot.vehicleessentials.VehicleMake;

import java.util.*;

public class ParkingLotSystem {
    private final ArrayList<ParkingLot> lots;

    public ParkingLotSystem(ParkingLot... parkingLot) {
        this.lots = new ArrayList<>(Arrays.asList(parkingLot));
    }

    public void addParking(ParkingLot parkingLot) {
        this.lots.add(parkingLot);
    }

    public int getNumberOfParkingLots() {
        return this.lots.size();
    }

    public void parkVehicle(ParkedVehicleDetails vehicleDetails, ParkingStrategy parkingStrategy) throws ParkingLotException {
        ParkingLot parkingLot = parkingStrategy.getParkingStrategy().getLot(this.getLotsList());
        parkingLot.parkVehicleInThisLot(vehicleDetails);
    }


    public void unParkVehicle(Vehicle vehicle) throws ParkingLotException {
        ParkingLot parkingLotOfThisVehicle = this.getParkingLotOInWhichThisVehicleIsParked(vehicle);
        parkingLotOfThisVehicle.unParkFromParkingLot(vehicle);
    }

    public ParkingLot getParkingLotOInWhichThisVehicleIsParked(Vehicle vehicle) throws ParkingLotException {
        return this.lots.stream().filter(parkingLot -> parkingLot.vehicleAlreadyPresent(vehicle)).findFirst()
                .orElseThrow(() -> {
                    return new ParkingLotException
                            ("vehicleDetails not parked in any lot!", ParkingLotException.ExceptionType.NO_SUCH_CAR_PARKED);
                });
    }

    public ArrayList<ParkingLot> getLotsList() {
        return this.lots;
    }

    public ArrayList<List<Integer>> getSlotNumberListOfVehiclesByColor(VehicleColor vehicleColor) {
        ArrayList<List<Integer>> listOfSlots = new ArrayList<>();
        this.lots.stream().forEach(parkingLot -> listOfSlots.add(parkingLot.getSlotNumberListOfVehiclesByColor(vehicleColor)));
        return listOfSlots;
    }

    public ArrayList<List<ParkingDetailsDTO>> getSlotNumberListOfVehiclesByMakeAndColor(VehicleMake vehicleMake, VehicleColor vehicleColor) {
        ArrayList<List<ParkingDetailsDTO>> listOfSlots = new ArrayList<>();
        this.lots.stream().forEach(parkingLot -> listOfSlots.add(parkingLot.getSlotNumberListOfVehiclesByMakeAndColor(vehicleMake,vehicleColor)));
        return listOfSlots;
    }
}
