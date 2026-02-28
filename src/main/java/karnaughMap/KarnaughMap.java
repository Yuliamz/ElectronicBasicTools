package karnaughMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class KarnaughMap {

    public List<Character> variableUP;
    public List<Character> variableLEFT;
    public ArrayList<String> valuesUP;
    public ArrayList<String> valuesLEFT;
    public String[][] KMap;
    public Map<String, String> mapTruthTable;

    public KarnaughMap(Set<Character> variables, Integer[][] truthTable) {
        variableUP = new ArrayList<>();
        variableLEFT = new ArrayList<>();
        valuesUP = new ArrayList<>();
        valuesLEFT = new ArrayList<>();
        setVariables(variables);
        fillGrayUP();
        fillGrayLEFT();
        KMap = new String[valuesLEFT.size() + 1][valuesUP.size() + 1];
        mapTruthTable = new HashMap<>();
        matrixToMap(truthTable);
        fillKMAP();
        generateKMap();

    }

    public void setVariables(Set<Character> variables) {
        List<Character> listVariables = new ArrayList<>();
        listVariables.addAll(variables);
        int count = listVariables.size();
        if (count % 2 == 0) {
            variableUP = listVariables.subList(0, count / 2);
            variableLEFT = listVariables.subList(count / 2, count);
        } else {
            variableUP = listVariables.subList(0, (count / 2) + 1);
            variableLEFT = listVariables.subList((count / 2) + 1, count);
        }
    }

    public void fillKMAP() {
        for (String[] KMap1 : KMap) {
            for (int j = 0; j < KMap[0].length; j++) {
                KMap1[j] = "0";
            }
        }
        KMap[0][0] = "  ";
        for (int i = 1; i < KMap.length; i++) {
            KMap[i][0] = valuesLEFT.get(i - 1);
        }
        for (int i = 1; i < KMap[0].length; i++) {
            KMap[0][i] = valuesUP.get(i - 1);
        }
    }

    public void generateKMap() {
        for (int i = 1; i < KMap.length; i++) {
            for (int j = 1; j < KMap[0].length; j++) {
                KMap[i][j] = mapTruthTable.get(KMap[0][j].concat(KMap[i][0]));
            }
        }
    }

    public void matrixToMap(Integer[][] truthTable) {
        String row;
        for (Integer[] truthTable1 : truthTable) {
            row = "";
            for (int j = 0; j < truthTable[0].length - 1; j++) {
                row = row.concat(String.valueOf(truthTable1[j]));
            }
            mapTruthTable.put(row, String.valueOf(truthTable1[truthTable[0].length - 1]));
        }
    }

    public void fillGrayUP() {
        int posibilities = (int) Math.pow(2, variableUP.size());
        for (int i = 0; i < posibilities; i++) {
            valuesUP.add(GrayCode.getGreyCode(i, variableUP.size()));
        }
    }

    public void fillGrayLEFT() {
        int posibilities = (int) Math.pow(2, variableLEFT.size());
        for (int i = 0; i < posibilities; i++) {
            valuesLEFT.add(GrayCode.getGreyCode(i, variableLEFT.size()));
        }
    }

    public void showMap() {
        mapTruthTable.entrySet().stream().forEach((entry) -> {
            System.out.println(entry.getKey() + ", " + entry.getValue());
        });
    }

    public void showUP() {
        variableUP.stream().forEach((variableUP1) -> {
            System.out.println(variableUP1);
        });
    }

    public void showLEFT() {
        variableLEFT.stream().forEach((variableLEFT1) -> {
            System.out.println(variableLEFT1);
        });
    }

    public void show() {
        for (String[] KMap1 : KMap) {
            for (int j = 0; j < KMap[0].length; j++) {
                System.out.print(KMap1[j] + " ");
            }
            System.out.println("");
        }
    }

    public List<Character> getVariableUP() {
        return variableUP;
    }

    public List<Character> getVariableLEFT() {
        return variableLEFT;
    }

    public String[][] getKMap() {
        String[][] map = new String[KMap.length - 1][KMap[0].length];
        for (int i = 1; i < KMap.length; i++) {
            System.arraycopy(KMap[i], 0, map[i - 1], 0, KMap[0].length);
        }
        return map;
    }

    public String[] getColumNames() {
        String[] columNames = new String[KMap[0].length];
        System.arraycopy(KMap[0], 0, columNames, 0, columNames.length);
        return columNames;
    }

    public String getVarUP() {
        StringBuilder buf = new StringBuilder("   ");
        for (int i = 0; i < variableUP.size(); i++) {
            buf.append(" ").append(variableUP.get(i));
        }
        return buf.toString();
    }

    public String getVarLEFT() {
        StringBuilder builder = new StringBuilder(" ");
        for (int i = 0; i < variableLEFT.size(); i++) {
            builder.append(variableLEFT.get(i)).append("<br>");
        }
        return builder.toString();
    }

}
