package objects;

import exchanges.Exchange;

public class CoinPair {
    //this class represents a singular coin pairing
    //it is measured in how many units of quotes can be exchanged for 1 unit of base
    //same for every exchange
    public Coin quote;
    public Coin base;
    
    //differs per exchange
    public double ratio;
    public double volume;
    public Exchange parent;
    
    
    public CoinPair(Coin q, Coin b, double ratio, double volume, Exchange parent){
        quote = q;
        base = b;
        this.ratio = ratio;
        this.volume = volume;
        System.out.println("Initialized: " + toString());
        this.parent = parent;
    }
    
    @Override
    public String toString(){
        return base + " - " + quote + " : " + ratio;
    }
    
    public String pairString() {
        return quote.ticker + "/" + base.ticker;
    }

    // Overriding equals() to compare two Complex objects
    @Override
    public boolean equals(Object o) {
 
        // If the object is compared with itself then return true  
        if (o == this) {
            return true;
        }
 
        /* Check if o is an instance of CoinPair or not */
        if (!(o instanceof CoinPair)) {
            return false;
        }
         
        // typecast o to Complex so that we can compare data members 
        CoinPair c = (CoinPair) o;
         
        // Compare the data members and return accordingly 
        return quote.equals(c.quote) && base.equals(c.base);
    }
}
