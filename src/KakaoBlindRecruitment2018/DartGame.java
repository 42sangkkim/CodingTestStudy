package KakaoBlindRecruitment2018;

public class DartGame {
    class Solution {
        public int solution(String dartResult) {
            String tmp = dartResult.replaceAll("[*]", "").replaceAll("#", "");
            String[] scoreList = tmp.split("[A-Z]");

            int len = scoreList.length;
            int[] scores = new int[len];
            for (int i=0; i<len; i++) {
                scores[i] = Integer.parseInt(scoreList[i]);
            }

            tmp = dartResult.replaceAll("[^A-Z]", "");
            char[] bonusStrings = tmp.toCharArray();

            tmp = dartResult.replaceAll("[0-9]", "");
            String[] specialStrings = tmp.split("[A-Z]");




            for (int i=0; i<len; i++) {
                if (bonusStrings[i] == 'D') {
                    scores[i] = scores[i] * scores[i];
                } else if (bonusStrings[i] == 'T') {
                    scores[i] = scores[i] * scores[i] * scores[i];
                }
            }

            for (int i=0; i<len; i++) {
                if (i+1 < specialStrings.length)
                    if (specialStrings[i+1].contains("*")) {
                        scores[i] *= 2;
                        if (i>0)
                            scores[i-1] *= 2;
                    } else if (specialStrings[i+1].contains("#")) {
                        scores[i] *= -1;
                    }
            }

            int answer = 0;
            for (int score : scores) {
                answer += score;
            }

            return answer;
        }
    }
}