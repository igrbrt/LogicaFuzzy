package javaapplication20;

public class Par<T, U> {

    private T privateFirst;
    private U privateSecond;

    public Par(T first, U second) {
        this.privateFirst = first;
        this.privateSecond = second;
    }
    
    public Par(){}

    /*public Par(Class<T> teste, Class<U> teste2) throws InstantiationException, IllegalAccessException {
     * privateFirst = teste.newInstance();
     * privateSecond = teste2.newInstance();
     * }*/

    public final T getFirst() {
        return privateFirst;
    }

    public final void setFirst(T value) {
        privateFirst = value;
    }

    public final U getSecond() {
        return privateSecond;
    }

    public final void setSecond(U value) {
        privateSecond = value;
    }

    @Override
    public String toString() {
        return "{ " + getFirst().toString() + " ; " + getSecond().toString() + " }";
    }
}