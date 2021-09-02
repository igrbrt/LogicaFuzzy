package javaapplication20;

/**
 * Representação de uma regra
 */
public class Regra implements java.lang.Comparable<Regra> {

    private String[] privateConditions;
    private String privateConclusion;
    private double privateDegree;

    public final String[] getConditions() {
        return privateConditions;
    }

    public final void setConditions(String[] value) {
        privateConditions = value;
    }

    public final String getConclusion() {
        return privateConclusion;
    }

    public final void setConclusion(String value) {
        privateConclusion = value;
    }

    public final double getDegree() {
        return privateDegree;
    }

    public final void setDegree(double value) {
        privateDegree = value;
    }


    public Regra(String[] conditions, String conclusion, double degree) {
        setConditions(conditions);
        setConclusion(conclusion);
        setDegree(degree);
    }


    @Override
    public String toString() {
        String toReturn = "SE ";
        for (int i = 0; i < getConditions().length - 1; i++) {
            toReturn += getConditions()[i] + " E ";
        }
        toReturn += getConditions()[getConditions().length - 1];
        toReturn += " ENTÃO " + getConclusion();
        toReturn += " = " + getDegree();

        return toReturn;
    }

    public final boolean equals(Regra rule1) {
        boolean value = true;
        for (int i = 0; i < this.getConditions().length; i++) {
            if (!this.getConditions()[i].equals(rule1.getConditions()[i])) {
                return false;
            }
        }

        return value;
    }

    public final int compareTo(Regra toCompare) {
        for (int i = 0; i < getConditions().length; i++) {
            if (getConditions()[i].compareTo(toCompare.getConditions()[i]) < 0) {
                return -1;
            } else if (getConditions()[i].compareTo(toCompare.getConditions()[i]) > 0) {
                return 1;
            } else if (getConditions()[i].compareTo(toCompare.getConditions()[i]) == 0) {
                continue;
            }
        }

        return 0;
    }
}