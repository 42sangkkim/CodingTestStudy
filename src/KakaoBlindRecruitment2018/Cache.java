package KakaoBlindRecruitment2018;

public class Cache {

    class Solution {
        public int solution(int cacheSize, String[] cities) {
            if (cacheSize == 0)
                return cities.length * 5;

            String[] cache = new String[cacheSize];
            for (int i=0; i<cacheSize; i++) {
                cache[i] = "";
            }
            int[] ages = new int[cacheSize];

            int answer = 0;

            int len = cities.length;
            for (int i=0; i<len; i++) {
                String city = cities[i].toUpperCase();
                for (int j=0; j<cacheSize; j++) {
                    ages[j] += 1;
                }

                int destination = whereToGo(city, cacheSize, cache, ages);
                if (cache[destination].compareTo(city) == 0) {
                    answer += 1;
                } else {
                    cache[destination] = city;
                    answer += 5;
                }
                ages[destination] = 0;
            }

            return answer;
        }

        private int whereToGo(String target, int len, String[] cache, int[] ages) {
            int result = -1, age = 0, oldest = 0;
            for (int i=0; i<len; i++) {
                String tmp = cache[i];
                if (target.compareTo(tmp)==0 && ages[i] > age) {
                    result = i;
                    age = ages[i];
                } else if (ages[oldest] < ages[i]) {
                    oldest = i;
                }
            }
            return result == -1 ? oldest : result;
        }
    }
}
