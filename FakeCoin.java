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
        float number = new Random().nextFloat();
        if(number < 0)
            number *= -1;
        return number;
    }

    public int getRandomNumber(int min, int max) {
        return new Random().nextInt(max - min + 1) + min;
    }

    public float getRandomWithExclusion(float number) {
        float randomNumber = number;
        while (randomNumber == number)
            randomNumber = new Random().nextFloat();
        
        if(randomNumber < 0)
            randomNumber *= -1;
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
    
    private int compareCoins(Coin left, Coin right) {
        int status = 0;
        if (left.getWeight() > right.getWeight())
            status = -1;
        else if (left.getWeight() < right.getWeight())
            status = 1;
        showMessages(left, right, status);
        return status;
    }


    private void showMessages(Coin left, Coin right, int status){
        String operation = "=";
        if(status == 1)
            operation = " < ";
        else
           operation = " > ";
        System.out.println("["+left.toString() +"] "+ operation+" ["+ right.toString()+"]");
    }

    private void showMessages(Coin[] left, Coin[] right, int status){
        String operation = "=";
        if(status == 1)
            operation = " < ";
        else
           operation = " > ";
        System.out.println(Arrays.asList(left).toString() +" "+ operation+" "+ Arrays.asList(right).toString());
    }

    private Coin findFakeCoin(Coin[] coins) {
        int third = coins.length / 3;
        Coin[] groupACoins = Arrays.copyOfRange(coins, 0, third);// A
        Coin[] groupBCoins = Arrays.copyOfRange(coins, third, third * 2);// B
        Coin[] groupCCoins = Arrays.copyOfRange(coins, third * 2, coins.length);// C

        System.out.println("GRUPO 01: "+Arrays.asList(groupACoins).toString() +"\nGRUPO 02: "+ Arrays.asList(groupBCoins).toString() +"\nGRUPO 03: "+Arrays.asList(groupCCoins).toString()+"\n");

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
            if (compareCoins(groupACoins[2], groupCCoins[2]) == -1) {
                return groupACoins[2];
            }
            return groupBCoins[2];
        } else if (result == 0) {
            if (compareCoins(groupACoins[0], groupACoins[1]) == -1) {
                return groupACoins[0];
            } else if (compareCoins(groupACoins[0], groupACoins[1]) == 0) {
                return groupACoins[3];
            }
            return groupACoins[1];
        } else {
            if (compareCoins(groupBCoins[0], groupBCoins[1]) == -1) {
                return groupBCoins[1];
            } else if (compareCoins(groupBCoins[0], groupBCoins[1]) == 0) {
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
            if (compareCoins(groupACoins[0], groupACoins[1]) == -1) {
                return groupACoins[1];
            } else if (compareCoins(groupACoins[0], groupACoins[1]) == 0) {
                return groupACoins[3];
            }
            return groupACoins[0];
        } else if (result == 0) {
            if (compareCoins(groupBCoins[0], groupBCoins[1]) == -1) {
                return groupBCoins[0];
            } else if (compareCoins(groupBCoins[0], groupBCoins[1]) == 0) {
                return groupBCoins[3];
            }
            return groupBCoins[1];
        } else {
            if (compareCoins(groupACoins[2], groupCCoins[2]) == 0) {
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
            if (compareCoins(groupCCoins[0], groupCCoins[1]) == -1) {
                return groupCCoins[1];
            } else if (compareCoins(groupCCoins[0], groupCCoins[1]) == 0) {
                return groupCCoins[2];
            }
            return groupCCoins[0];
        } else if (result == 0) {
            return groupCCoins[3];
        } else {
            if (compareCoins(groupCCoins[0], groupCCoins[1]) == -1) {
                return groupCCoins[0];
            } else if (compareCoins(groupCCoins[0], groupCCoins[1]) == 0) {
                return groupCCoins[2];
            }
            return groupCCoins[1];
        }
    }

    public static void main(String[] args) {
        FakeCoin fc = new FakeCoin();
        Coin[] coins = fc.initializeCoins();

        System.out.println("\n===========\n");
        System.out.println(Arrays.asList(coins).toString() +", ");
        System.out.println("\n===========\n");

        Coin coin = fc.findFakeCoin(coins);
        System.out.println("\n===========\n");
        System.out.println("Fake Coin:\n"+coin.toString());
    }

}
