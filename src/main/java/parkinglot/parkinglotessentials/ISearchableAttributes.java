package parkinglot.parkinglotessentials;

import java.util.function.Predicate;

public interface ISearchableAttributes {
    Predicate getFilter();
}
