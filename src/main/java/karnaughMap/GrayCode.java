package karnaughMap;

public class GrayCode {

    static String getGreyCode(int myNum, int numOfBits) {
        if (numOfBits == 1) {
            return String.valueOf(myNum);
        }
        
        if (myNum >= Math.pow(2, (numOfBits - 1))) {
            return "1" + getGreyCode((int) (Math.pow(2, (numOfBits))) - myNum - 1, numOfBits - 1);
        } else {
            return "0" + getGreyCode(myNum, numOfBits - 1);
        }
    }

}
