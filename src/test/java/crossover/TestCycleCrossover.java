package crossover;

/* --- test cases in order 01 to 10 ---
   [01] parent01 and parent02 have same length 280 before crossover
   [02] parent01 not equals null after crossover
   [03] parent02 not equals null after crossover
   [04] parent01 and parent02 have same length 280 after crossover
   [05] child not null
   [06] length child equals length parent01
   [07] child contains no duplicates
   [08] child contains all tour indices from 1 to 280
   [09] child differs from parent01
   [10] child differs from parent02
*/

import base.City;
import base.Tour;
import configuration.Configuration;
import data.InstanceReader;
import data.TSPLIBReader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;

public class TestCycleCrossover {
    private Crossover crossover;
    private Tour parent01;
    private Tour parent02;

    @BeforeEach
    private void init() {
        // initialize crossover and parents
        this.crossover = new CycleCrossover();
        this.parent01 = new Tour();
        this.parent02 = new Tour();

        InstanceReader instanceReader = new InstanceReader(Configuration.instance.dataFilePath);
        instanceReader.open();
        TSPLIBReader tsplibReader = new TSPLIBReader(instanceReader);
        instanceReader.close();

        // generate two random shuffled parents
        ArrayList<City> cities01 = new ArrayList<>(tsplibReader.getCities());
        ArrayList<City> cities02 = new ArrayList<>(tsplibReader.getCities());
        Collections.shuffle(cities01);
        Collections.shuffle(cities02);

        this.parent01.setCities(cities01);
        this.parent02.setCities(cities02);
    }

    @Test
    @DisplayName("[00] Cycle Crossover of genetic_algorithms_crossover_operators_04.pdf")
    public void test() {
        City city01 = new City(1, 288, 149);
        City city02 = new City(2, 288, 129);
        City city03 = new City(3, 270, 133);
        City city04 = new City(4, 256, 141);
        City city05 = new City(5, 256, 157);
        City city06 = new City(6, 246, 157);
        City city07 = new City(7, 236, 169);
        City city08 = new City(8, 228, 169);
        City city09 = new City(9, 228, 161);

        Tour parent01 = new Tour();
        Tour parent02 = new Tour();
        // p1 = [1 2 3 4 5 6 7 8 9], p2 = [4 1 2 8 7 6 9 3 5]
        parent01.setCities(new ArrayList<>(Arrays.asList(city01, city02, city03, city04, city05, city06, city07, city08, city09)));
        parent02.setCities(new ArrayList<>(Arrays.asList(city04, city01, city02, city08, city07, city06, city09, city03, city05)));

        Tour expected01 = new Tour();
        Tour expected02 = new Tour();
        // o1 = [1 2 3 4 7 6 9 8 5], o2 = [4 1 2 8 5 6 7 3 9]
        expected01.setCities(new ArrayList<>(Arrays.asList(city01, city02, city03, city04, city07, city06, city09, city08, city05)));
        expected02.setCities(new ArrayList<>(Arrays.asList(city04, city01, city02, city08, city05, city06, city07, city03, city09)));

        ArrayList<Tour> child = crossover.doCrossover(parent01, parent02);

        Assertions.assertEquals(expected01.getCities(), child.get(0).getCities());
        Assertions.assertEquals(expected02.getCities(), child.get(1).getCities());
    }

    @Test
    @DisplayName("[01] parent01 and parent02 have same length 280 before crossover")
    public void test01() {
        Assertions.assertEquals(280, parent01.getSize());
        Assertions.assertEquals(280, parent02.getSize());
        crossover.doCrossover(parent01, parent02);
    }

    @Test
    @DisplayName("[02] parent01 not equals null after crossover")
    public void test02() {
        crossover.doCrossover(parent01, parent02);
        Assertions.assertNotNull(parent01);
    }

    @Test
    @DisplayName("[03] parent02 not equals null after crossover")
    public void test03() {
        crossover.doCrossover(parent01, parent02);
        Assertions.assertNotNull(parent02);
    }

    @Test
    @DisplayName("[04] parent01 and parent02 have same length 280 after crossover")
    public void test04() {
        crossover.doCrossover(parent01, parent02);
        Assertions.assertEquals(280, parent01.getSize());
        Assertions.assertEquals(280, parent02.getSize());
    }

    @Test
    @DisplayName("[05] child not null")
    public void test05() {
        ArrayList<Tour> child = crossover.doCrossover(parent01, parent02);
        Assertions.assertNotNull(child.get(0));
        Assertions.assertNotNull(child.get(1));
    }

    @Test
    @DisplayName("[06] length child equals length parent01")
    public void test06() {
        ArrayList<Tour> child = crossover.doCrossover(parent01, parent02);
        Assertions.assertEquals(child.get(0).getSize(), parent01.getSize());
        Assertions.assertEquals(child.get(1).getSize(), parent01.getSize());
    }

    @Test
    @DisplayName("[07] child contains no duplicates")
    public void test07() {
        ArrayList<Tour> child = crossover.doCrossover(parent01, parent02);
        ArrayList<City> child01 = child.get(0).getCities();
        ArrayList<City> child02 = child.get(1).getCities();

        // https://stackoverflow.com/a/562906
        Set<City> set01 = new HashSet<>(child01);
        Assertions.assertEquals(set01.size(), child01.size());

        Set<City> set02 = new HashSet<>(child02);
        Assertions.assertEquals(set02.size(), child02.size());
    }

    @Test
    @DisplayName("[08] child contains all tour indices from 1 to 280")
    public void test08() {
        ArrayList<Tour> child = crossover.doCrossover(parent01, parent02);
        ArrayList<City> child01 = child.get(0).getCities();
        ArrayList<City> child02 = child.get(1).getCities();

        // https://stackoverflow.com/a/2784576
        child01.sort(Comparator.comparing(City::getId));
        for (int i = 1; i <= 280; i++) {
            Assertions.assertEquals(i, child01.get(i-1).getId());
        }
        child02.sort(Comparator.comparing(City::getId));
        for (int i = 1; i <= 280; i++) {
            Assertions.assertEquals(i, child02.get(i-1).getId());
        }
    }

    @Test
    @DisplayName("[09] child differs from parent01")
    public void test09() {
        ArrayList<Tour> child = crossover.doCrossover(parent01, parent02);
        ArrayList<City> child01 = child.get(0).getCities();
        ArrayList<City> child02 = child.get(1).getCities();

        Assertions.assertNotEquals(child01, parent01.getCities());
        Assertions.assertNotEquals(child02, parent01.getCities());
    }

    @Test
    @DisplayName("[10] child differs from parent02")
    public void test10() {
        ArrayList<Tour> child = crossover.doCrossover(parent01, parent02);
        ArrayList<City> child01 = child.get(0).getCities();
        ArrayList<City> child02 = child.get(1).getCities();

        Assertions.assertNotEquals(child01, parent02.getCities());
        Assertions.assertNotEquals(child02, parent02.getCities());
    }
}