package MoneyPackage;

/**
 * Created by Arvid Bodin and Johan Svensson on 2015-09-15.
 *
 */
public class Money {
    //Hej Johan
    double moneyValue;
    public Money(double moneyValue){
        this.moneyValue = moneyValue;
    }
    public double getMoney(){return this.moneyValue;}
    public void addFunds (double money){
        if(money == 0){
            throw new AddZeroFundException("Could not add the amount: " + money + " to wallet.");
        }else if(money < 0){
            throw new NegativeFundException("Could not add negative amount: " + money + " to wallet.");
        }
        moneyValue += money;
    }
    public void withdrawFunds(double money){
        if((this.moneyValue - money) < 0){
            throw new NegativeFundException("Could not withdraw the amount: " + money + " from wallet: " + this.moneyValue + ".");
        }
        this.moneyValue -= money;
    }

}
