import java.util.Random;
import java.util.Arrays;

public class FakeCoin {

    private Coin[] initializeCoins() {
        Coin[] coins = new Coin[12];
        float fixedWeight = getRandomNumber();
        float fakeWeight = getRandomWithExclusion(fixedWeight);

        for (int position = 0; position < coins.length; position++)
            coins[position] = new Coin(position, fixedWeight);
        coins[getRandomNumber(0, 11)].setWeight(fakeWeight);

        return coins;
    }

    public float getRandomNumber() {
        return new Random().nextFloat();
    }

    public int getRandomNumber(int min, int max) {
        return new Random().nextInt(max - min + 1) + min;
    }

    public float getRandomWithExclusion(float number) {
        float randomNumber = number;
        while (randomNumber == number)
            randomNumber = new Random().nextFloat();
        return randomNumber;
    }

    private int compareCoins(Coin[] left, Coin[] right) {
        if (left.length != right.length)
            System.out.println("unequal piles");

        double leftWeight = 0.0;
        for (Coin aLeft : left)
            leftWeight += aLeft.getWeight();

        double rightWeight = 0.0;
        for (Coin aRight : right)
            rightWeight += aRight.getWeight();
        
        int status = 0;
        if (leftWeight > rightWeight)
            status = -1;
        else if (leftWeight < rightWeight)
            status = 1;
        showMessages(left, right, status);
        return status;
    }

    private void showMessages(Coin[] left, Coin[] right, int status){
        
        String message = "[ ";
        for (Coin coin : right) {
            message += coin.getWeight()+", ";
        }
        message += " ]";

        if(status == -1)
            message += " < ";
        else if(status == 0)
            message += " = ";
        else
            message += " > ";
    
        message += "[ ";
        for (Coin coin : left) {
            message += coin.getWeight()+", ";
        }
        message += " ]";
        System.out.println(message);
    }

    private Coin findFakeCoin(Coin[] coins) {
        int third = coins.length / 3;
        Coin[] groupACoins = Arrays.copyOfRange(coins, 0, third);// A
        Coin[] groupBCoins = Arrays.copyOfRange(coins, third, third * 2);// B
        Coin[] groupCCoins = Arrays.copyOfRange(coins, third * 2, coins.length);// C

        int result = compareCoins(groupACoins, groupBCoins);
        if (result == -1)// inbalence left
            return findFakeCoinInbalanceLeft(groupACoins, groupBCoins, groupCCoins);
        else if (result == 0)// balanced
            return findFakeCoinBalance(groupACoins, groupBCoins, groupCCoins);
        return findFakeCoinInbalanceRight(groupACoins, groupBCoins, groupCCoins);// inbalance right
    }

    private Coin findFakeCoinInbalanceLeft(Coin[] groupACoins, Coin[] groupBCoins, Coin[] groupCCoins) {

        Coin[] firstGroupCoins = { groupBCoins[0], groupBCoins[1], groupBCoins[3], groupACoins[2] };

        Coin[] secondGroupCoins = { groupCCoins[0], groupCCoins[1], groupCCoins[3], groupBCoins[2] };

        int result = compareCoins(firstGroupCoins, secondGroupCoins);
        if (result == -1) {
            if (groupACoins[2].getWeight() > groupCCoins[2].getWeight()) {
                return groupACoins[2];
            }
            return groupBCoins[2];
        } else if (result == 0) {
            if (groupACoins[0].getWeight() > groupACoins[1].getWeight()) {
                return groupACoins[0];
            } else if (groupACoins[0].getWeight() == groupACoins[1].getWeight()) {
                return groupACoins[3];
            }
            return groupACoins[1];
        } else {
            if (groupBCoins[0].getWeight() > groupBCoins[1].getWeight()) {
                return groupBCoins[1];
            } else if (groupBCoins[0].getWeight() == groupBCoins[1].getWeight()) {
                return groupBCoins[3];
            }
            return groupBCoins[0];
        }
    }

    private Coin findFakeCoinInbalanceRight(Coin[] groupACoins, Coin[] groupBCoins, Coin[] groupCCoins) {

        Coin[] firstGroupCoins = { groupCCoins[0], groupCCoins[1], groupCCoins[3], groupACoins[2] };

        Coin[] secondGroupCoins = { groupACoins[0], groupACoins[1], groupACoins[3], groupBCoins[2] };

        int result = compareCoins(firstGroupCoins, secondGroupCoins);
        if (result == -1) {
            if (groupACoins[0].getWeight() > groupACoins[1].getWeight()) {
                return groupACoins[1];
            } else if (groupACoins[0].getWeight() == groupACoins[1].getWeight()) {
                return groupACoins[3];
            }
            return groupACoins[0];
        } else if (result == 0) {
            if (groupBCoins[0].getWeight() > groupBCoins[1].getWeight()) {
                return groupBCoins[0];
            } else if (groupBCoins[0].getWeight() == groupBCoins[1].getWeight()) {
                return groupBCoins[3];
            }
            return groupBCoins[1];
        } else {
            if (groupACoins[2].getWeight() == groupCCoins[2].getWeight()) {
                return groupBCoins[2];
            }
            return groupACoins[2];
        }
    }

    private Coin findFakeCoinBalance(Coin[] groupACoins, Coin[] groupBCoins, Coin[] groupCCoins) {

        Coin[] firstGroupCoins = { groupACoins[0], groupACoins[1], groupACoins[2] };
        Coin[] secondGroupCoins = { groupCCoins[0], groupCCoins[1], groupCCoins[2] };

        int result = compareCoins(firstGroupCoins, secondGroupCoins);
        if (result == -1) {
            if (groupCCoins[0].getWeight() > groupCCoins[1].getWeight()) {
                return groupACoins[1];
            } else if (groupCCoins[0].getWeight() == groupCCoins[1].getWeight()) {
                return groupACoins[2];
            }
            return groupACoins[0];
        } else if (result == 0) {
            return groupCCoins[3];
        } else {
            if (groupCCoins[0].getWeight() > groupCCoins[1].getWeight()) {
                return groupCCoins[0];
            } else if (groupCCoins[0].getWeight() == groupCCoins[1].getWeight()) {
                return groupCCoins[2];
            }
            return groupCCoins[1];
        }
    }

    public static void main(String[] args) {
        FakeCoin fc = new FakeCoin();
        Coin[] coins = fc.initializeCoins();
        
        System.out.println("\n===========\n");
        System.out.println("[");
        for (Coin coin : coins) 
            System.out.println(coin.getWeight()+", ");
        System.out.println("]");
        System.out.println("\n===========\n");

        // Coin[] coins = {
        // new Coin(0,2),
        // new Coin(1,2),
        // new Coin(2,2),
        // new Coin(3,2),
        // new Coin(4,2),
        // new Coin(5,2),
        // new Coin(6,2),
        // new Coin(7,2),
        // new Coin(8,29),
        // new Coin(9,2),
        // new Coin(10,2),
        // new Coin(11,2),
        // };

        Coin coin = fc.findFakeCoin(coins);
        System.out.println("\n===========\n");
        System.out.println("Fake Coin:\n"+coin.toString());
    }

}
