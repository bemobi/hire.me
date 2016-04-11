package com.shortener.infra;

/**
 * Created by isvaldo on 26/03/16.
 */
public class Base62Converter {

    final static String[] elements = {
            "a","b","c","d","e","f","g","h","i","j","k","l","m","n","o",
            "p","q","r","s","t","u","v","w","x","y","z","1","2","3","4",
            "5","6","7","8","9","0","A","B","C","D","E","F","G","H","I",
            "J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X",
            "Y","Z"
    };

    public static String converter ( int base, final long autoIncrementId)
    {
        long decimalNumber = autoIncrementId;

        String tempVal = decimalNumber == 0 ? "0" : "";
        long mod = 0;

        while( decimalNumber != 0 ) {
            mod = decimalNumber % base;
            tempVal = elements[(int)mod] + tempVal;
            decimalNumber = decimalNumber / base;
        }

        return tempVal;
    }

    public static String converter(final long autoIncrementId){
        return converter(62, autoIncrementId);
    }
}
