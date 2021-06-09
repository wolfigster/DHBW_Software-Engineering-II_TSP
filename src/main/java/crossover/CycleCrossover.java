package crossover;

import base.City;
import base.Tour;

import java.util.ArrayList;
import java.util.Collections;

// please delete the file if not needed
// if needed -> please update "Placeholder" with the name of the implemented algorithm
public class CycleCrossover extends Crossover {

    // example how cycle crossover works:
    // https://www.youtube.com/watch?v=85pIA2TYsUs
    public ArrayList<Tour> doCrossover(Tour tour01, Tour tour02) {
        ArrayList<Tour> offspring = new ArrayList<>();

        // start cycle crossover with tour01 as parent01 & with tour02 as parent01
        // to get both possibilities
        Tour offspring01 = doCycleCrossover(tour02, tour01);
        Tour offspring02 = doCycleCrossover(tour01, tour02);

        // add both offsprings to the actual offspring
        Collections.addAll(offspring, offspring01, offspring02);

        return offspring;
    }

    private Tour doCycleCrossover(Tour parent01, Tour parent02) {
        // initialize a tour with null objects to set late cities on a specific index
        // without it would append the city to the tour if the tour isn't long enough
        Tour offspring = new Tour();
        parent01.getCities().forEach(c -> offspring.addCity(null));

        // https://www.rubicite.com/Tutorials/GeneticAlgorithms/CrossoverOperators/CycleCrossoverOperator.aspx#:~:text=Cycle%201%3A%20We%20start%20with,we%20drop%20down%20to%209.
        // https://uwaterloo.ca/scholar/sites/ca.scholar/files/ahilal/files/lecture-6-1.pdf
        // start by taking the first element of parent01
        // "i = 1" - startindex in genetic_algorithms_crossover_operators_01.pdf
        // ".. choose the first element of the offspring equal to be either the first element of the first parent or the first element of the second parent" - genetic_algorithms_crossover_operators_02.pdf
        // city = currParent.getFirstCity() - genetic_algorithms_crossover_operators_03.pdf
        // "produce the first offspring by taking the first city from the first parent" - genetic_algorithms_crossover_operators_04.pdf
        // "It starts with the first gene of the first parent, .." - genetic_algorithms_crossover_operators_05.pdf
        int childIndex = 0;
        City child = parent01.getCity(childIndex);
        offspring.addCity(childIndex, child);

        // check the offspring for null cities
        // while an element is null the crossover is not over
        while (offspring.getCities().contains(null)) {
            // select the index of the child of parent01
            // and get the element of parent02 at this index
            childIndex = parent01.getCities().indexOf(child);
            child = parent02.getCity(childIndex);
            // if child is already in offspring -> cycle with startindex = firstElement is over
            if (offspring.containsCity(child)) {
                // fill empty / null elements with the elements from parent02 at the given indexes
                for (int i = 0; i < offspring.getSize(); i++) {
                    if (offspring.getCity(i) == null) offspring.addCity(i, parent01.getCity(i));
                }
                return offspring;
            }
            offspring.addCity(childIndex, child);
        }
        return offspring;
    }
}