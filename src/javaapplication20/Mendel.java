package javaapplication20;

import java.util.ArrayList;

public class Mendel {

    private double[] entradas;
    private Conjunto[] conjuntos;
    private ArrayList<Regra> regras;

    public final double[] getEntradas() {
        return this.entradas;
    }

    public final void setEntradas(double[] entradas) {
        this.entradas = entradas;
    }

    public final ArrayList<Regra> getRegras() {
        return this.regras;
    }

    public final void setRegras(ArrayList<Regra> regras) {
        this.regras = regras;
    }

    public final Conjunto[] getConjuntos() {
        return this.conjuntos;
    }

    public final void setConjuntos(Conjunto[] conjuntos) {
        this.conjuntos = conjuntos;
    }

    public Mendel(double[] entradas, Conjunto[] conjuntos) {

        setEntradas(entradas);
        setRegras(new ArrayList<Regra>());
        setConjuntos(conjuntos);
        generateRules();
        removeRegrasIguais();
    }

    /**
     * Gera todas as regras possíveis com as entradas
     */
    public final void generateRules() {
        // Iterating through entries in the determined window (12 months)
        for (int month = 12; month < getEntradas().length; month++) {
            String[] conditions = new String[12];
            double[] degrees = new double[13];

            // Calculating conditions of rule
            for (int i = month - 12, k = 0; i < month; i++, k++) {
                double[] pertinence = new double[getConjuntos().length];
                for (int j = 0; j < getConjuntos().length; j++) {
                    pertinence[j] = getConjuntos()[j].getPertinenceDegree(getEntradas()[i]);
                }

                int maxIndex = 0;
                for (maxIndex = 0; maxIndex < pertinence.length; maxIndex++) {
                    if (pertinence[maxIndex] == maxValue(pertinence)) {
                        break;
                    }
                }

                conditions[k] = getConjuntos()[maxIndex].getLabel();
                degrees[k] = pertinence[maxIndex];
            }

            // Calculating conclusion of rule
            double[] pertinenceOfConclusion = new double[getConjuntos().length];
            for (int j = 0; j < getConjuntos().length; j++) {
                pertinenceOfConclusion[j] = getConjuntos()[j].getPertinenceDegree(getEntradas()[month]);
            }

            int index = 0;
            for (index = 0; index < pertinenceOfConclusion.length; index++) {
                if (pertinenceOfConclusion[index] == maxValue(pertinenceOfConclusion)) {
                    break;
                }
            }

            degrees[12] = pertinenceOfConclusion[index];
            String conclusion = getConjuntos()[index].getLabel();

            // Calculating Degree of rule
            double degree = 1.0;
            for (double value : degrees) {
                degree *= value;
            }

            // Adding rule to generated rules
            this.regras.add(new Regra(conditions, conclusion, degree));
        }
    }

    public double maxValue(double[] vetor) {
        double maior = 0;
        for (int i = 0; i < vetor.length; i++) {
            if (vetor[i] > maior) {
                maior = vetor[i];
            }
        }
        return maior;
    }

    public final void removeRegrasIguais() {
        java.util.Collections.sort(this.regras);

        for (int i = 0; i < getRegras().size() - 1; i++) {
            for (int j = i + 1; j < getRegras().size(); j++) {
                if (getRegras().get(i).compareTo(getRegras().get(j)) == 0) {
                    if (getRegras().get(i).getDegree() >= getRegras().get(j).getDegree()) {
                        getRegras().get(j).setDegree(-1);
                    } else {
                        getRegras().get(i).setDegree(-1);
                        break;
                    }
                }
            }
        }

        for (int i = 0; i < getRegras().size(); i++) {
            if (getRegras().get(i).getDegree() < 0.0) {
                getRegras().remove(getRegras().get(i));
            }
        }
    }
}