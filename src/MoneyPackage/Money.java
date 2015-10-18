package MoneyPackage;

import java.io.Serializable;

/**
 * Created by Arvid Bodin(arvidbod@kth.se) and Johan Svensson(johans7@kth.se) on 2015-10-09
 *
 */
public class Money implements Serializable {
    double moneyValue;
    /**
     * Money constructor
     * @param moneyValue adds the moneyValue to the variable moneyValue
     */
    public Money(double moneyValue){
        if(moneyValue <= 0){
            throw new NegativeFundException("can not add a negative number to money");
        }
        this.moneyValue = moneyValue;
    }
    /**
     * Money constructor
     * if no argument is passed, set the moneyValue to zero
     */
    public Money(){moneyValue = 0;}
    /**
     * getMoney returns current money.
     * @return the current money
     */
    public double getMoney(){return this.moneyValue;}
    /**
     * addFunds add funds to moneyValue
     * @param money this is the amount that is added to moneyValue
     */
    public void addFunds(double money){
        if(money < 0){
            throw new NegativeFundException("Could not add negative amount: " + money + " to wallet.");
        }
        moneyValue += money;
    }
    /**
     * withDrawFunds remove money from moneyValue
     * @param money is the amount to be withdrawn.
     * @return the amount that is withdrawn from moneyValue
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
     * toString returns a string with the current moneyValue
     * @return a String of the current value
     */
    public String toString(){
        return Double.toString(moneyValue);
    }
}
