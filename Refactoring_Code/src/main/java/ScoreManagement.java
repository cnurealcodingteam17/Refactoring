
import java.util.Scanner;

public class ScoreManagement {

    private static Scanner myScanner;
    private static int[] scores;
    private static String[] studentnames;
    private static int total_student;


    public static void main(String[] args) {
        // TODO Auto-generated method stub

        myScanner = new Scanner(System.in);

        int menu = menu();

        while(true) {
            switch (menu) {
                case 1:
                    input_studentNum_Score();
                    break;
                case 2:
                    totalScore_output();
                    break;
                case 3:
                    search_student();
                    break;
                case 4:
                    System.out.println("프로그램을 종료합니다.");
                    return;
                default : System.out.println("Error");
            }
            menu = menu();
        }
    }

    public static int menu() {
        int answer = 0;
        System.out.println("\n------------------menu(숫자로 입력)------------------");
        System.out.println("1.성적입력(이전기록 초기화)\n2.전체성적확인\n3.검색\n4.종료");
        while(answer < 1 || answer > 4) {
            System.out.print(">입력 : ");
            answer = myScanner.nextInt();
            System.out.println();
        }
        return answer;
    }

    public static void input_studentNum_Score() {
        /*smell code 1 -Extract Method*/
        total_student=0;



        System.out.print(">학생 수를 입력하세요.: ");
        total_student = myScanner.nextInt();

        while(total_student <= 0) {
            System.out.print(">올바른 입력이 아닙니다. 다시 학생 수를 입력하세요.: ");
            total_student = myScanner.nextInt();
        }

        studentInfo();
    }

    public static void studentInfo(){
        int score = 0;
        int numberOfStudent=0;

        scores = new int[total_student];
        studentnames = new String[total_student];

        while(numberOfStudent < total_student) {
            myScanner.nextLine();
            System.out.print("\n>"+ (numberOfStudent+1) + "번째 학생 이름을 입력하시오.: ");
            studentnames[numberOfStudent] = myScanner.nextLine();

            System.out.print(">점수를 입력하시오.: ");
            score = myScanner.nextInt();

            while (score > 100 || score < 0 ) {
                System.out.println("오류 :정상적인 점수가 아닙니다.");
                System.out.print(">다시 점수를 입력하시오.: ");
                score = myScanner.nextInt();
            }

            scores[numberOfStudent++] = score;

        }
    }

    public static void totalScore_output() {

        if(total_student <= 0) {
            System.out.println("Error: 성적을 입력하지 않았습니다.\n\n");
            return ;
        }
        /*smell code 3 - Inline Temp*/

        System.out.println("\n모두" + total_student + "명의 성적이 입력되었습니다.");
        System.out.println("평균은" + calcAverage(scores, total_student)  + "입니다.\n\n");
        System.out.println("최고점은 " + calcMax(scores, total_student) + " 점 입니다.");
        System.out.println("최저점은 " + calcMin(scores, total_student) + " 점 입니다.\n");


        System.out.println("성적순은 다음과 같습니다.");
        selectionSort(studentnames, scores, total_student);

    }

    public static void search_student() {

        if(total_student <= 0) {
            System.out.println("Error: 성적을 입력하지 않았습니다.\n\n");
            return ;
        }

        myScanner.nextLine();
        System.out.print(">학생의 이름을 입력하세요.: ");
        String student_name = myScanner.nextLine();

        int num = foundStudent_arrayNum(student_name);
        if(num == -1) {
            System.out.println("입력한 학생 중에서 찾을 수 없습니다.");
            return ;
        }

        /*smell code 1 - Extract Method ,
        smell code 2 - Extract Variable*/

        printScore(num);

    }

    public static void printScore(int num){
        final boolean highScore = scores[num] >= calcAverage(scores, total_student);
        final boolean lowScore = scores[num] < calcAverage(scores, total_student);
        final boolean equalScore = scores[num] == calcAverage(scores, total_student) ;
        if (highScore) {
            System.out.println(studentnames[num]+" 학생의 성적은 " + scores[num] + "점으로 평균 이상입니다.");
        } else if (lowScore) {
            System.out.println(studentnames[num]+" 학생의 성적은 " + scores[num] + "점으로 평균 미만입니다.");
        } else if (equalScore) {
            System.out.println(studentnames[num]+" 학생의 성적은 " + scores[num] + "점으로 평균 입니다.");
        }
    }


    public static int foundStudent_arrayNum(String studentname){
        for (int i = 0; i < studentnames.length; i++) {
            if (studentnames[i].equals(studentname)){
                return i;
            }
        }
        return -1;
    }


    private static double calcAverage(int[] elements, int aSize) {
        int i = 0;
        int sum = 0;

        while (i < aSize) {
            sum = sum + elements[i++];
        }

        /*smell code 3 - Inline temp*/

        return (double) sum / aSize;


    }


    private static int calcMax(int[] elements, int aSize) {
        int j = 0;
        int max = elements[0];

        while ( j < aSize) {
            if (elements[j] >= max) {
                max = elements[j];
            }
            j++;
        }
        return max;

    }

    private static int calcMin(int[] elements, int aSize) {
        int z = 0;
        int min = elements[0];

        while (z < aSize) {
            if (elements[z] <= min) {
                min = elements[z];
            }
            z++;
        }
        return min;

    }


    private static void selectionSort(String[] students, int[] elements, int aSize) {
        /*smell code 3 - Inline Temp*/

        int maxLoc;
        int maxValue;
        int curLoc;
        String name;

        for (int selectionLoc=0 ;selectionLoc < aSize - 1 ; selectionLoc++) {

            maxLoc = selectionLoc;
            maxValue = elements[maxLoc];
            curLoc = selectionLoc + 1;

            while (curLoc <= aSize - 1) {
                if (elements[curLoc] >= maxValue) {
                    maxLoc = curLoc;
                    maxValue = elements[maxLoc];

                }

                curLoc++;
            }

            name = students[maxLoc];

            elements[maxLoc] = elements[selectionLoc];
            students[maxLoc] = students[selectionLoc];

            elements[selectionLoc] = maxValue;
            students[selectionLoc] = name;
        }

        for(int ranking = 0 ; ranking < aSize ; ranking++) {
            System.out.println((ranking+1) +"등 " + students[ranking] +"학생: " + elements[ranking] + "점");
        }

    }

}