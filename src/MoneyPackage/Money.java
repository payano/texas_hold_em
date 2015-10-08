package MoneyPackage;

/**
 * Created by Arvid Bodin and Johan Svensson on 2015-09-15.
 *
 */
public class Money {
    //Hej Johan
    double moneyValue;
    public Money(double moneyValue){
        //protect against negative values?
        this.moneyValue = moneyValue;
    }
    public Money(){moneyValue = 0;}
    public double getMoney(){return this.moneyValue;}
    public void addFunds (double money){
        if(money == 0){
            throw new AddZeroFundException("Could not add the amount: " + money + " to wallet.");
        }else if(money < 0){
            throw new NegativeFundException("Could not add negative amount: " + money + " to wallet.");
        }
        moneyValue += money;
    }
    public double withdrawFunds(double money){
        if((this.moneyValue - money) < 0){
            throw new NegativeFundException("Could not withdraw the amount: " + money + " from wallet: " + this.moneyValue + ".");
        }
        this.moneyValue -= money;
        //return how much that has been withdrawn
        return money;
    }

}
