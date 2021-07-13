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
        // TODO Auto-generated method stub
        return "Position: "+this.position +"\nWeight: "+this.weight;
    }
}
