package edu.anadolu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import static edu.anadolu.TurkishNetwork.distance;

@SuppressWarnings("DanglingJavadoc")
public class mtspP{

    static int ROUTE_NUMBER = 5;
    static int HUB_NUMBER = 2;

    static int swapNodesInRoute = 0;
    static int swapHubWithNodeInRoute = 0;
    static int swapNodesBetweenRoutes = 0;
    static int insertNodeInRoute = 0;
    static int insertNodeBetweenRoutes = 0;


    public static void main(String[] args) {

        ArrayList<Integer>[] bestSolution = new ArrayList[ROUTE_NUMBER * HUB_NUMBER];
        ArrayList<Integer>[] rollBack;
        ArrayList<Integer>[] TABLE = new ArrayList[ROUTE_NUMBER * HUB_NUMBER];

        randomlyGenerate(TABLE);

        int newCost = 0;
        int bestCost = 0;

        int counter = 0;
        while (counter != 100000) {
            if (counter == 0) {
                randomlyGenerate(TABLE);
                bestSolution = copyTABLE(TABLE);
                bestCost = totalCost(TABLE);

            } else {
                randomlyGenerate(TABLE);
                newCost = totalCost(TABLE);

                if (newCost < bestCost) {
                    bestSolution = copyTABLE(TABLE);
                    bestCost = newCost;

                }

            }
            counter++;
        }

        printTable(bestSolution);
        System.out.println("************************************************************************************************************************************");
        System.out.println("First Solution: " + bestCost);
        System.out.println("************************************************************************************************************************************");

        int newCOST = 0;
        int count = 0;
        while (count != 5000000) {

            int rnd = (int) (Math.random() * 5);

            switch (rnd) {
                case 0:

                    rollBack = copyTABLE(bestSolution);
                    swapNodesInRoute(bestSolution);
                    newCOST = totalCost(bestSolution);
                    if (newCOST < bestCost) {
                        swapNodesInRoute++;
                        bestCost = newCOST;
                    } else {

                        bestSolution = copyTABLE(rollBack);

                    }

                    break;
                case 1:
                    rollBack = copyTABLE(bestSolution);
                    swapHubWithNodeInRoute(bestSolution);
                    newCOST = totalCost(bestSolution);
                    if (newCOST < bestCost) {
                        swapHubWithNodeInRoute++;
                        bestCost = newCOST;
                    } else {

                        bestSolution = copyTABLE(rollBack);
                    }
                    break;
                case 2:

                    rollBack = copyTABLE(bestSolution);
                    swapNodesBetweenRoutes(bestSolution);
                    newCOST = totalCost(bestSolution);
                    if (newCOST < bestCost) {
                        swapNodesBetweenRoutes++;
                        bestCost = newCOST;
                    } else {

                        bestSolution = copyTABLE(rollBack);
                    }
                    break;
                case 3:
                    rollBack = copyTABLE(bestSolution);
                    insertNodeInRoute(bestSolution);
                    newCOST = totalCost(bestSolution);
                    if (newCOST < bestCost) {
                        insertNodeInRoute++;
                        bestCost = newCOST;
                    } else {

                        bestSolution = copyTABLE(rollBack);
                    }
                    break;
                case 4:
                    rollBack = copyTABLE(bestSolution);

                    insertNodeBetweenRoutes(bestSolution);
                    newCOST = totalCost(bestSolution);
                    if (newCOST < bestCost) {
                        insertNodeBetweenRoutes++;
                        bestCost = newCOST;

                    } else {

                        bestSolution = copyTABLE(rollBack);
                    }
                    break;

            }

            count++;
        }

        printTable(bestSolution);
        System.out.println("************************************************************************************************************************************");
        System.out.println("Best Solution: " + bestCost);
        System.out.println("************************************************************************************************************************************");
        printCounters();

    }

    public static void randomlyGenerate(ArrayList<Integer>[] table) {

        ArrayList<Integer> allCities = new ArrayList<>();

        for (int i = 0; i < 81; i++) {

            allCities.add(i, i);

        }

        /**  shuffle cities    */

        Collections.shuffle(allCities);

        int allRoutes = ROUTE_NUMBER * HUB_NUMBER;
        int division = allCities.size() / allRoutes;
        int remainder = allCities.size() % allRoutes;

        int counter = 0;

        for (int i = 0; i < table.length; i++) {

            /** create route */

            ArrayList<Integer> route = new ArrayList<>();

            for (int j = 0; j < division; j++) {

                route.add(j, allCities.get(counter));

                counter++;
            }

            table[i] = route;

        }

        if (remainder != 0) {

            for (int i = 0; i < remainder; i++) {

                table[i].add(allCities.get(allCities.size() - 1 - i));
            }

        }

        /**  select hub      */

        ArrayList<Integer> HUBS = new ArrayList();
        while (true) {

            int rnd = (int) (Math.random() * allCities.size());
            if (!HUBS.contains(rnd)) {
                HUBS.add(rnd);
            }

            if (HUBS.size() == HUB_NUMBER) {

                break;
            }

        }

        for (int i = 0; i < HUBS.size(); i++) {

            for (int j = 0; j < table.length; j++) {

                for (int k = 0; k < table[j].size(); k++) {

                    if (HUBS.get(i).equals(table[j].get(k))) {
                        table[j].remove(k);

                    }

                }

            }

        }

        /** put hubs to table */
        int counterr = 0;
        for (int i = 0; i < table.length; i++) {

            table[i].add(0, HUBS.get(counterr));

            if (i != 0 && (i + 1) % ROUTE_NUMBER == 0) {
                counterr++;
            }

        }

    }

    public static void printTable(ArrayList<Integer>[] table) {
        int counter = 1;
        for (int i = 0; i < table.length; i++) {
            if (i % ROUTE_NUMBER == 0) {
                System.out.println("HUB Number: " + table[i].get(0));
            }
            System.out.print("     Route" + counter + ": ");
            for (int j = 1; j < table[i].size(); j++) {

                System.out.print(table[i].get(j) + " ");
            }
            System.out.println();

            if (counter == ROUTE_NUMBER) {
                counter = 0;
            }
            counter++;
        }
    }

    public static void printCounters() {// bozuk counterlar
        System.out.println("swapNodesInRoute: " + swapNodesInRoute);
        System.out.println("swapHubWithNodeInRoute: " + swapHubWithNodeInRoute);
        System.out.println("swapNodesBetweenRoutes: " + swapNodesBetweenRoutes);
        System.out.println("insertNodeInRoute: " + insertNodeInRoute);
        System.out.println("insertNodeBetweenRoutes: " + insertNodeBetweenRoutes);

    }

    public static int totalCost(ArrayList<Integer>[] table) {
        int cost = 0;

        for (int i = 0; i < table.length; i++) {

            table[i].add(table[i].size(), table[i].get(0));

        }

        for (int i = 0; i < table.length; i++) {
            for (int j = 0; j < table[i].size(); j++) {

                if (j + 1 != table[i].size()) {

                    cost += distance[table[i].get(j)][table[i].get(j + 1)];
                }
            }
        }
        for (int i = 0; i < table.length; i++) {

            table[i].remove(table[i].size() - 1);

        }

        return cost;
    }

    public static ArrayList<Integer>[] copyTABLE(ArrayList<Integer>[] table) {

        ArrayList<Integer>[] copytable = new ArrayList[ROUTE_NUMBER * HUB_NUMBER];

        for (int i = 0; i < table.length; i++) {

            ArrayList<Integer> EachRoute = new ArrayList<>();


            for (int j = 0; j < table[i].size(); j++) {

                int value = table[i].get(j);

                EachRoute.add(j, value);

            }
            copytable[i] = EachRoute;
        }

        return copytable;
    }

    public static int[] generateRandomNumber(int lowerBound, int upperBound, boolean canSame) {
        int[] arr = new int[2];
        if (lowerBound == upperBound) {
            throw new RuntimeException("Alt ve üst sınır aynı olamaz!");
        }
        if (!canSame) {
            while (true) {
                int rnd = (int) (Math.random() * (upperBound - lowerBound) + lowerBound);
                int rnd1 = (int) (Math.random() * (upperBound - lowerBound) + lowerBound);
                if (rnd != rnd1) {
                    arr[0] = rnd;
                    arr[1] = rnd1;
                    break;
                }
            }
        } else {
            arr[0] = (int) (Math.random() * (upperBound - lowerBound) + lowerBound);
            arr[1] = (int) (Math.random() * (upperBound - lowerBound) + lowerBound);
        }
        return arr;
    }

    public static ArrayList<Integer>[] swapNodesInRoute(ArrayList<Integer>[] table) {

        int selectRoute = (int) (Math.random() * table.length);//rasgele bir route indexi sec

        if (table[selectRoute].size() >= 3) {

            int[] selectNodes = generateRandomNumber(1, table[selectRoute].size(), false);//rasgele iki node indexi sec

            int temp1 = table[selectRoute].get(selectNodes[0]);//Node 1in degeri
            int temp2 = table[selectRoute].get(selectNodes[1]);//Node 2 nin degeri

            table[selectRoute].set(selectNodes[0], temp2);
            table[selectRoute].set(selectNodes[1], temp1);
        }
        return table;

    }

    public static ArrayList<Integer>[] swapHubWithNodeInRoute(ArrayList<Integer>[] table) {

        int[] selectRoute = generateRandomNumber(0, table.length, true);//rasgele bir route indexi sec
        int selectNodeIndex;

        while (true) {
            selectNodeIndex = (int) (Math.random() * table[selectRoute[0]].size());// rasgele bir node indexi sec
            if (selectNodeIndex != 0) {
                break;

            }
        }
        int temp1 = table[selectRoute[0]].get(selectNodeIndex);//Node un degeri
        int temp2 = table[selectRoute[1]].get(0);//Hubin degeri


        for (int i = 0; i < table.length; i++) {

            if (table[i].get(0) == temp2) {
                table[i].set(0, temp1);
            }

        }
        table[selectRoute[0]].set(selectNodeIndex, temp2);
        return table;
    }

    public static ArrayList<Integer>[] swapNodesBetweenRoutes(ArrayList<Integer>[] table) {

        int[] selectRoute = generateRandomNumber(0, table.length, false);//rasgele iki route indexi sec
        if (table[selectRoute[0]].size() >= 2 & table[selectRoute[1]].size() >= 2) {

            int selectNodeIndex1;
            int selectNodeIndex2;
            while (true) {
                selectNodeIndex1 = (int) (Math.random() * table[selectRoute[0]].size());//rasgele bir node indexi sec
                selectNodeIndex2 = (int) (Math.random() * table[selectRoute[1]].size());//rasgele bir node indexi sec
                if (selectNodeIndex1 != 0 && selectNodeIndex2 != 0) {
                    break;
                }
            }
            int temp1 = table[selectRoute[0]].get(selectNodeIndex1);// Node 1in degeri
            int temp2 = table[selectRoute[1]].get(selectNodeIndex2);// Node 2 nin degeri

            table[selectRoute[0]].set(selectNodeIndex1, temp2);
            table[selectRoute[1]].set(selectNodeIndex2, temp1);
        }
        return table;
    }

    public static ArrayList<Integer>[] insertNodeBetweenRoutes(ArrayList<Integer>[] table) {

        if (table.length >= 2) {

            int[] selectRoutes = generateRandomNumber(0, table.length, false);
            if (table[selectRoutes[0]].size() >= 3) {

                int selectNodeIndex1;
                int selectNodeIndex2;
                while (true) {
                    selectNodeIndex1 = (int) (Math.random() * table[selectRoutes[0]].size());//rasgele bir node indexi sec
                    selectNodeIndex2 = (int) (Math.random() * table[selectRoutes[1]].size());//rasgele bir node indexi sec
                    if (selectNodeIndex1 != 0 && selectNodeIndex2 != 0) {
                        if (selectNodeIndex1 != selectNodeIndex2)
                            break;
                    }
                }
                int Node1Value = table[selectRoutes[0]].get(selectNodeIndex1);// Node 1in degeri
                table[selectRoutes[1]].add(selectNodeIndex2 + 1, Node1Value);
                table[selectRoutes[0]].remove(selectNodeIndex1);
            }
        }
        return table;
    }

    public static ArrayList<Integer>[] insertNodeInRoute(ArrayList<Integer>[] table) {
        int selectRoute = (int) (Math.random() * table.length);

        if (table[selectRoute].size() >= 3) {

            int[] selectNodeIndexes = generateRandomNumber(1, table[selectRoute].size(), false); // Indexes of two nodes
            int Node1Value = table[selectRoute].get(selectNodeIndexes[0]);

            if (selectNodeIndexes[0] < selectNodeIndexes[1]) {
                table[selectRoute].add(selectNodeIndexes[1] + 1, Node1Value);
                table[selectRoute].remove(selectNodeIndexes[0]);
            } else {

                table[selectRoute].add(selectNodeIndexes[1] + 1, Node1Value);
                table[selectRoute].remove(selectNodeIndexes[0] + 1);
            }
        }
        return table;
    }
}
