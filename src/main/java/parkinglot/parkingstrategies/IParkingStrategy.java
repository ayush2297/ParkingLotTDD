package parkinglot.parkingstrategies;

import parkinglot.parkinglotessentials.ParkingLot;
import parkinglot.parkinglotessentials.ParkingLotException;

import java.util.ArrayList;

public interface IParkingStrategy {
    ParkingLot getLot(ArrayList<ParkingLot> listOfLots) throws ParkingLotException;
}
