package Scheduling;

import java.util.*;

public class SRT {
    int[] AWT, ATT, ART;
    public SRT(Source[] source){
        AWT = new int[source.length];
        ATT = new int[source.length];
        ART = new int[source.length];

        for(int i=0; i< source.length; i++) {
            source[i].TQ = source[i].Time;
        }
        ArrayList<Source> new_source = new ArrayList<Source>();
        Source box;
        ArrayList<Source> arr_source = new ArrayList<Source>();
        //  전체 시간    현재 시간   실행 시간    정렬 체크
        int total = 0, run = 0, count = 0, tmp = 0;

        for(int i=0; i<source.length; i++){
            new_source.add(source[i]);
            total += source[i].getService();
        }

        System.out.println("\n<SRT Scheduling>\n");
        System.out.println("간트 차트");
        System.out.print("[ ");
        //---------------간트 차트---------------//
        while(run < total) {
            //준비 큐에 넣기
            if(!new_source.isEmpty() &&run == new_source.get(0).getArrival()){
                arr_source.add(new_source.get(0));
                new_source.remove(0);
                if(tmp == 0) {
                    Collections.sort(arr_source, (a, b) -> a.getService() - b.getService());
                }
            }
            //디스패치(준비 큐에 프로세스가 있는 경우)
            if(!arr_source.isEmpty()){
                box = arr_source.get(0);
                if(box.TQ != 0) {
                    System.out.print("P" + box.getPnum() + " ");
                    run++;
                    box.TQ--;
                    box.Service--;
                    count++;
                    tmp++;
                }
                //실행이 끝난 경우
                if(box.Service == 0){
                    //대기 시간
                    AWT[box.getPnum()] += run-count - box.getArrival();
                    //응답 시간
                    if(box.tmp == 0) {
                        ART[box.getPnum()] = run - count - box.getArrival() + box.RC;
                        box.tmp++;
                    }
                    //반환 시간
                    ATT[box.getPnum()] += run-count - box.getArrival() + count;
                    arr_source.remove(0);
                    count = 0;
                    tmp = 0;
                    Collections.sort(arr_source, (a, b) -> a.getService() - b.getService() );
                }
                //타임 아웃
                if(box.TQ == 0 && box.Service != 0){
                    //대기 시간
                    AWT[box.getPnum()] += run-box.Time - box.getArrival();
                    //응답 시간
                    if(box.tmp == 0){
                        ART[box.getPnum()] = run - count - box.getArrival() + box.RC;
                        box.tmp++;
                    }
                    //반환 시간
                    ATT[box.getPnum()] += run - box.getArrival();
                    arr_source.remove(0);
                    box.TQ = box.Time;
                    box.Arrival = run;
                    arr_source.add(box);
                    count = 0;
                    tmp = 0;
                    Collections.sort(arr_source, (a, b) -> a.getService() - b.getService());
                }
            }
            else{
                System.out.print("- ");
                run++;
                total++;
            }
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