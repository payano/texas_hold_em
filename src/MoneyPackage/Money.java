package MoneyPackage;

import java.io.Serializable;

/**
 * Created by Arvid Bodin and Johan Svensson on 2015-09-15.
 *
 */
public class Money implements Serializable {
    double moneyValue;
    /**
     * 
     * @param moneyValue 
     */
    public Money(double moneyValue){
        //protect against negative values?
        this.moneyValue = moneyValue;
    }
    /**
     * 
     */
    public Money(){moneyValue = 0;}
    /**
     * 
     * @return 
     */
    public double getMoney(){return this.moneyValue;}
    /**
     * 
     * @param money 
     */
    public void addFunds (double money){
        /*if(money == 0){
            throw new AddZeroFundException("Could not add the amount: " + money + " to wallet.");
        }else*/
        if(money < 0){
            throw new NegativeFundException("Could not add negative amount: " + money + " to wallet.");
        }
        moneyValue += money;
    }
    /**
     * 
     * @param money
     * @return 
     */
    public double withdrawFunds(double money){
        if((this.moneyValue - money) < 0){
            throw new NegativeFundException("Could not withdraw the amount: " + money + " from wallet: " + this.moneyValue + ".");
        }
        this.moneyValue -= money;
        //return how much that has been withdrawn
        return money;
    }
    /**
     * 
     * @return 
     */
    public String toString(){
        return Double.toString(moneyValue);
    }
}
