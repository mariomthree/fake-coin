import java.text.DecimalFormat;

public class Coin {

    private int position;
    private float weight;

    Coin(int position, float weight) {
        this.weight = weight;
        this.position = position;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public int getPosition() {
        return position;
    }

    public void setPostion(int position) {
        this.position = position;
    }
    
    @Override
    public String toString() {
        DecimalFormat decimalFormat = new DecimalFormat();
        decimalFormat.setMaximumFractionDigits(2);
        return decimalFormat.format(this.weight);
    }
}
