package javaapplication20;

/**
 * Representação do Conjunto Triangular
 */
public class Conjunto {

    private String privateLabel;
    private double privateBegin;
    private double privatePeak;
    private double privateEnd;

    public final String getLabel() {
        return privateLabel;
    }

    public final void setLabel(String value) {
        privateLabel = value;
    }

    public final double getBegin() {
        return privateBegin;
    }

    public final void setBegin(double value) {
        privateBegin = value;
    }

    public final double getPeak() {
        return privatePeak;
    }

    public final void setPeak(double value) {
        privatePeak = value;
    }

    public final double getEnd() {
        return privateEnd;
    }

    public final void setEnd(double value) {
        privateEnd = value;
    }

    /**
     * *
     *
     * @param label
     * @param begin
     * @param peak
     * @param end
     */
    public Conjunto(String label, double begin, double peak, double end) {
        setLabel(label);
        setBegin(begin);
        setPeak(peak);
        setEnd(end);
    }

    /**
     * *
     *
     * @param value
     * @return
     */
    public final double getPertinenceDegree(double value) {
        if (value <= getBegin() || value > getEnd()) {
            return 0.0;
        } else if (value > getBegin() && value <= getPeak()) {
            return 1 - (getPeak() - value) / (getPeak() - getBegin());
        } else {
            return (getEnd() - value) / (getEnd() - getPeak());
        }
    }
}