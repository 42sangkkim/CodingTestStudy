package KakaoBlindRecruitment2018;

import java.util.ArrayList;

public class NewsClustering {
    class Solution {
        public int solution(String str1, String str2) {
            int len1 = str1.length(), len2 = str2.length();
            String[] strs1 = new String[len1-1], strs2 = new String[len2-1];

            for (int i=0; i<len1-1; i++) {
                strs1[i] = str1.substring(i, i+2);
            }
            for (int i=0; i<len2-1; i++) {
                strs2[i] = str2.substring(i, i+2);
            }

            strs1 = removeInvalid(len1-1, strs1);
            strs2 = removeInvalid(len2-1, strs2);


            len1 = strs1.length;
            len2 = strs2.length;

            String[] com = noSame(len1, strs1, len2, strs2);
            int len = com.length;

            int max = countMax(len, com, len1, strs1, len2, strs2);
            int min = countMin(len, com, len1, strs1, len2, strs2);

            if (max == 0)
                return 65536;

            int answer = min * 65536 / max;
            return answer;
        }

        private String[] removeInvalid(int n, String[] strs) {
            ArrayList<String> resultList = new ArrayList<>();

            String tmp;
            for (int i=0; i<n; i++) {
                tmp = strs[i].toUpperCase();
                if (tmp.replaceAll("[A-Z]", "").isEmpty()) {
                    resultList.add(tmp);
                }
            }
            String[] result = new String[resultList.size()];
            resultList.toArray(result);
            return result;
        }

        private String[] noSame(int len1, String[] strs1, int len2, String[] strs2) {
            ArrayList<String> com = new ArrayList<>();
            for (int i=0; i<len1; i++) {
                String target = strs1[i];
                if (!com.contains(target))
                    com.add(target);
            }
            for (int i=0; i<len2; i++) {
                String target = strs2[i];
                if (!com.contains(target))
                    com.add(target);
            }
            String[] result = new String[com.size()];
            com.toArray(result);
            return result;
        }

        private int countMin(int len, String[] com, int len1, String[] strs1, int len2, String[] strs2) {
            int cnt = 0;
            for (int i=0; i<len; i++) {
                String target = com[i];
                int tmp1 = 0, tmp2 = 0;
                for (int j=0; j<len1; j++) {
                    if (strs1[j].compareTo(target) == 0)
                        tmp1 += 1;
                }
                for (int j=0; j<len2; j++) {
                    if (strs2[j].compareTo(target) == 0)
                        tmp2 += 1;
                }
                cnt += Math.min(tmp1, tmp2);
            }
            return cnt;
        }

        private int countMax(int len, String[] com, int len1, String[] strs1, int len2, String[] strs2) {
            int cnt = 0;
            for (int i=0; i<len; i++) {
                String target = com[i];
                int tmp1 = 0, tmp2 = 0;
                for (int j=0; j<len1; j++) {
                    if (strs1[j].compareTo(target) == 0)
                        tmp1 += 1;
                }
                for (int j=0; j<len2; j++) {
                    if (strs2[j].compareTo(target) == 0)
                        tmp2 += 1;
                }
                cnt += Math.max(tmp1, tmp2);
            }
            return cnt;
        }
    }
}