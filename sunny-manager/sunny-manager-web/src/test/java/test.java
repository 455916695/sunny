public class test {
    public class Solution {
        public boolean Find(int target, int [][] array) {
            //从右上角开始查询
            int r = array.length;
            int l = array[0].length;

            int tempL = l;
            int tempR = 0;
            while(target != array[tempR][tempL]) {
                if(target < array[tempR][tempL]){
                    tempL--;
                }else{
                    tempR++;
                }
                if(tempL < 0 || tempR > r) {
                    return false;
                }

            }
            return true;
        }
    }
}
