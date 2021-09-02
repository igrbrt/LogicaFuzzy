package javaapplication20;

import java.util.ArrayList;
import java.util.List;

public class PrevisaoFuzzy {

    private double[] entradas;
    private Conjunto[] conjuntos;
    private ArrayList<Regra> regras;
    private Par<Double, Double>[] pertinencia;
    private Par<String, String>[] rotulos;
    private String[][] combinacoes;
    private ArrayList<Par<String, Double>> saidas;

    public final double[] getEntradas() {
        return entradas;
    }

    public final Conjunto[] getConjuntos() {
        return conjuntos;
    }

    public void setEntradas(double[] entradas) {
        this.entradas = entradas;
    }

    public final void setConjuntos(Conjunto[] valor) {
        conjuntos = valor;
    }

    public final ArrayList<Regra> getRegras() {
        return regras;
    }

    public final void setRegras(ArrayList<Regra> valor) {
        regras = valor;
    }

    public final Par<Double, Double>[] getPertinencia() {
        return pertinencia;
    }

    public final void setPertinencia(Par<Double, Double>[] valor) {
        pertinencia = valor;
    }

    public final Par<String, String>[] getRotulos() {
        return rotulos;
    }

    public final void setRotulos(Par<String, String>[] value) {
        rotulos = value;
    }

    public final String[][] getCombinacoes() {
        return combinacoes;
    }

    public final void setCombinacoes(String[][] valor) {
        combinacoes = valor;
    }

    private ArrayList<Par<String, Double>> getSaidas() {
        return saidas;
    }

    private void setSaidas(ArrayList<Par<String, Double>> value) {
        saidas = value;
    }

    
    public PrevisaoFuzzy(Conjunto[] conjunto, ArrayList<Regra> regras) {
        setConjuntos(conjunto);
        setRegras(regras);
    }

    public final double doIt(double[] inputs) {

        setEntradas(inputs);

        fuzzification();
        inference();
        return defuzzification();
    }


    private void fuzzification() {

        int tam = getEntradas().length;

        Par<Double, Double>[] a = null;
        a = new Par[tam];
        setPertinencia(a);

        Par<String, String>[] b = null;
        b = new Par[tam];
        setRotulos(b);


        // Calculating inputs' pertinence in each set
        for (int i = 0; i < getEntradas().length; i++) {

            getPertinencia()[i] = new Par<>(new Double("0.0"),new Double("0.0"));
            getRotulos()[i] = new Par<>(new String(),new String());



            // Getting the pairs of sets that is being activated and its pertinence degrees
            for (int j = 0; j < getConjuntos().length; j++) {
                if (getConjuntos()[j].getPertinenceDegree(getEntradas()[i]) > 0.0) {


                    // TA DANDO NULL
                    if (getPertinencia() == null) {
                        System.out.println("NULL ");
                    }

                    if (getPertinencia()[i].getFirst() == 0) {
                        getPertinencia()[i].setFirst(getConjuntos()[j].getPertinenceDegree(getEntradas()[i]));
                        getRotulos()[i].setFirst(getConjuntos()[j].getLabel());
                    } else {
                        getPertinencia()[i].setSecond(getConjuntos()[j].getPertinenceDegree(getEntradas()[i]));
                        getRotulos()[i].setSecond(getConjuntos()[j].getLabel());
                    }
                }
            }
        }

        // Combination of months to define which rules will be activated
        doCombinacoes();
    }

    /**
     * Realiza a inferência, aplicando as regras possiveis para as entradas
     * Determina e aplica a quais regras serão ativadas
     */
    private void inference() {
        setSaidas(new ArrayList<Par<String, Double>>());

        // Determining which rules will be activated and with which combination of entries
        ArrayList<Par<Integer, Integer>> toActivate = new ArrayList<>();
        for (Integer i = 0; i < getCombinacoes().length; i++) {
            for (Integer j = 0; j < getRegras().size(); j++) {
                if (compare(getRegras().get(j).getConditions(), getCombinacoes()[i])) {
                    toActivate.add(new Par<Integer, Integer>(i, j));
                }
            }
        }

        for (Par<Integer, Integer> activated : toActivate) {
            System.out.println(activated.toString());
            for (String value : getCombinacoes()[activated.getFirst()]) {
                System.out.print(value + "\t");
            }
            System.out.println();
        }
        System.out.println();

        // Activating rules and applying fuzzy operators to get conclusion of rule
        for (Par<Integer, Integer> activated : toActivate) {
            // Calculating pertinence of each set in conditions
            ArrayList<Double> pertinences = new ArrayList<>();
            for (int i = 0; i < getEntradas().length; i++) {
                pertinences.add(getCombinacoes()[activated.getFirst()][i].equals(getRotulos()[i].getFirst()) ? getPertinencia()[i].getFirst() : getPertinencia()[i].getSecond());
                System.out.print(pertinences.get(i) + "\t");
            }
            // Storing label and pertinence of conclusion
            getSaidas().add(new Par<String, Double>(getRegras().get(activated.getSecond()).getConclusion(), minValue(pertinences)));
            //Console.WriteLine(Regras[activated.Second].Conclusion + " = " + pertinences.Min());
        }

        for (Par<String, Double> pair : getSaidas()) {
            System.out.println(pair.toString());
        }
    }

    /**
     * Realiza o processo de defuzzificação das saídas obtidas na inferência
     *
     * @return Saída real do sistema de inferência Fuzzy
     */
    public double minValue(List<Double> lista) {
        double min = Double.MAX_VALUE;
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i) < min) {
                min = lista.get(i);
            }
        }
        return min;
    }

    private double defuzzification() {
        // Defuzzification by the height method
        double output = 0.0;
        double toDivide = 0.0;
        for (Par<String, Double> pair : getSaidas()) {
            output += pair.getSecond() * getSet(pair.getFirst()).getPeak();
            toDivide += pair.getSecond();
        }
        return output / toDivide;
    }

    /**
     * Faz a combinação dos conjuntos possíveis de cada mês
     */
    private void doCombinacoes() {
        setCombinacoes(new String[(int) Math.pow(2, getEntradas().length)][]);
        for (int i = 0; i < getCombinacoes().length; i++) {
            getCombinacoes()[i] = new String[getEntradas().length];
        }

        int flag = getCombinacoes().length / 2;
        for (int i = 0; i < getEntradas().length; i++) {
            boolean first = true;
            for (int j = 0; j < getCombinacoes().length; j += flag) {
                for (int k = j; k < j + flag; k++) {
                    if (first) {
                        getCombinacoes()[k][i] = getRotulos()[i].getFirst();
                    } else {
                        getCombinacoes()[k][i] = getRotulos()[i].getSecond();
                    }
                }

                first = !first;
            }

            flag /= 2;
        }
    }

    /**
     * Retorna uma regra da base de regras que contenha a condição passada como
     * parâmetro
     *
     * @param conditions condição para checagem
     * @return Regra na base de regras com conditions de condição
     */
    private Regra getRule(String[] conditions) {
        for (Regra rule : getRegras()) {
            if (rule.getConditions() == conditions) {
                return rule;
            }
        }

        return null;
    }

    /**
     * Retorna o conjunto de rótulo label
     *
     * @param label rótulo do conjunto a ser encontrado
     * @return conjunto
     */
    private Conjunto getSet(String label) {
        for (Conjunto set : getConjuntos()) {
            if (set.getLabel().equals(label)) {
                return set;
            }
        }
        return null;
    }

    private boolean compare(String[] var1, String[] var2) {
        if (var1.length == var2.length) {
            for (int i = 0; i < var1.length; i++) {
                if (!var1[i].equals(var2[i])) {
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }
}