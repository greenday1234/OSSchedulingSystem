package Scheduling;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class Priority {
    int[] AWT, ATT, ART;
    public Priority(Source[] source){
        AWT = new int[source.length];
        ATT = new int[source.length];
        ART = new int[source.length];
        ArrayList<Source> new_source = new ArrayList<Source>();
        Source box;
        ArrayList<Source> arr_source = new ArrayList<Source>();
        Source[] src = new Source[source.length];   //우선순위에 밀려 작업이 남은 프로세스를 넣는 배열
        //  전체 시간    현재 시간   배열 인덱스   실행 시작 값 체크
        int total = 0, run = 0, count = 0, tmp = 0;

        for(int i=0; i<source.length; i++){
            new_source.add(source[i]);
            total += source[i].getService();
        }

        System.out.println("\n<Preemptive Scheduling>\n");
        System.out.println("간트 차트");
        System.out.print("[ ");
        //---------------간트 차트---------------//
        while(run < total) {
            //준비 큐에 넣기
            if(!new_source.isEmpty() &&run == new_source.get(0).getArrival()){
                arr_source.add(new_source.get(0));
                new_source.remove(0);
            }
            //디스패치(준비 큐에 프로세스가 있는 경우)
            if(!arr_source.isEmpty()){
                box = arr_source.get(0);
                //준비 큐에 프로세스가 두개 이상인 경우
                if(arr_source.size() > 1){
                    //도착한 프로세스의 우선순위가 높은 경우
                    if(box.getPriority() > arr_source.get(1).getPriority()){
                        //대기 시간
                        AWT[box.getPnum()] += run-tmp - box.getArrival();
                        //응답 시간
                        ART[box.getPnum()] = run-tmp - box.getArrival() + box.RC;
                        //반환 시간
                        ATT[box.getPnum()] += run - box.getArrival();
                        //현재 올라와 있는 프로세스를 다른 배열로 빼기
                        box.Arrival = run;
                        tmp = 0;
                        box.tmp = 1;
                        src[count++] = box;
                        total -= box.getService();
                        arr_source.remove(0);
                        box = arr_source.get(0);
                    }
                    //도착한 프로세스의 우선순위가 낮은 경우
                    else{
                        //도착한  프로세스를 다른 배열로 빼기
                        total -= arr_source.get(1).getService();
                        src[count++] = arr_source.remove(1);
                    }
                }
                System.out.print("P" + box.getPnum() + " ");
                run++;
                box.Service--;
                tmp++;
                //실행이 끝난 경우
                if(box.Service == 0){
                    //응답 시간
                    ART[box.getPnum()] = run-tmp - box.getArrival() + box.RC;
                    //반환 시간
                    ATT[box.getPnum()] += run - box.getArrival();
                    box.tmp = 1;
                    tmp = 0;
                    arr_source.remove(0);
                }
            }
            //준비 큐에 프로세스가 없는 경우
            else{
                System.out.print("- ");
                run++;
                total++;
            }
        }

        if(count > 1){
            Arrays.sort(src, 0, count-1 ,(a, b) -> a.getPriority() - b.getPriority());
        }

        for(int i=0; i < count; i++){
            //대기 시간
            AWT[src[i].getPnum()] += run - src[i].getArrival();
            //응답 시간
            if(src[i].tmp != 1) {
                ART[src[i].getPnum()] = run - src[i].getArrival() + src[i].RC;
            }
            for(int k=0; k<src[i].getService(); k++) {
                System.out.print("P" + src[i].getPnum() + " ");
                run++;
            }
            //반환 시간
            ATT[src[i].getPnum()] += run - src[i].getArrival();
        }
        System.out.println("]\n");

        //---------------각 프로세스별 대기 시간---------------//
        double awt = 0;
        System.out.println("각 프로세스별 대기 시간");
        for(int i=0; i<AWT.length; i++){
            System.out.println("P" + i + " : " + AWT[i]);
            awt += AWT[i];
        }

        //---------------프로세스 평균 대기 시간---------------//
        System.out.println("\n프로세스 평균 대기 시간");
        System.out.println(awt/ AWT.length);

        //---------------각 프로세스별 응답 시간---------------//
        double art = 0;
        System.out.println("\n각 프로세스별 응답 시간");
        for(int i=0; i< ART.length; i++){
            System.out.println("P" + i + " : " + ART[i]);
            art += ART[i];
        }

        //---------------프로세스 평균 응답 시간---------------//
        System.out.println("\n프로세스 평균 응답 시간");
        System.out.println(art/ ART.length);

        //---------------각 프로세스별 반환 시간---------------//
        double att = 0;
        System.out.println("\n각 프로세스별 반환 시간");
        for(int i=0; i< ATT.length; i++){
            System.out.println("P" + i + " : " + ATT[i]);
            att += ATT[i];
        }

        //---------------프로세스 평균 반환 시간---------------//
        System.out.println("\n프로세스 평균 반환 시간");
        System.out.println(att/ ATT.length);
    }
}