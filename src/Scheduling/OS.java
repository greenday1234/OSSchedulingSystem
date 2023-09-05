package Scheduling;

import java.io.*;
import java.util.*;

class Source {
    int Pnum;   //아이디
    int Arrival;    //도착 시간
    int Service;    //실행 시간
    int Priority;   //우선순위
    int Time;   //타임슬라이스
    int TQ;    //타임슬라이스 체크
    int RC = 1; //응답 시간
    int tmp = 0;    //응답 시간 체크
    int[] SAVE = new int[6];
    public Source(int Pnum ,int Arrival, int Service, int Priority){
        this.Pnum = Pnum;
        this.Arrival = Arrival;
        this.Service = Service;
        this.Priority = Priority;
        SAVE[0] = Pnum;
        SAVE[1] = Arrival;
        SAVE[2] = Service;
        SAVE[3] = Priority;
        SAVE[4] = TQ;
        SAVE[5] = tmp;
    }
    void setTime(int Time){ this.Time = Time; TQ = Time; }
    public int getPnum(){ return Pnum; }
    public int getArrival(){ return Arrival; }
    public int getService(){ return Service; }
    public int getPriority(){ return Priority; }
    public void Reset(){
        Pnum = SAVE[0];
        Arrival = SAVE[1];
        Service = SAVE[2];
        Priority = SAVE[3];
        TQ = SAVE[4];
        tmp = SAVE[5];
    }
}

public class OS {
    public static void main(String args[]) throws IOException {
        int ProcessCount;
        String str;
        int Pnum;
        int Arrival;
        int Service;
        int Priority;
        int Time;
        Scanner scanner = new Scanner(new File("./Input.txt"));

        ProcessCount = Integer.parseInt(scanner.nextLine());
        Source[] source = new Source[ProcessCount];

        for(int i=0; i<ProcessCount; i++){
            str = scanner.nextLine();
            String[] arr = str.split(" ");
            Pnum = Integer.parseInt(arr[0].replaceAll("[^0-9]", ""));
            Arrival = Integer.parseInt(arr[1]);
            Service = Integer.parseInt(arr[2]);
            Priority = Integer.parseInt(arr[3]);
            source[i] = new Source(Pnum, Arrival, Service, Priority);
        }
        Time = Integer.parseInt(scanner.nextLine());
        for(int i=0; i<ProcessCount; i++){
            source[i].setTime(Time);
        }
        //도착시간 순 정렬
        Arrays.sort(source, (a, b) -> a.getArrival() - b.getArrival());

        FCFS fcfs = new FCFS(source);
        for(int i=0; i< source.length; i++){
            source[i].Reset();
        }

        SJF sjf = new SJF(source);
        for(int i=0; i< source.length; i++){
            source[i].Reset();
        }

        Non_Priority non_priority = new Non_Priority(source);
        for(int i=0; i< source.length; i++){
            source[i].Reset();
        }

        Priority priority = new Priority(source);
        for(int i=0; i< source.length; i++){
            source[i].Reset();
        }

        RR rr = new RR(source);
        for(int i=0; i< source.length; i++){
            source[i].Reset();
        }

        SRT srt = new SRT(source);
        for(int i=0; i< source.length; i++){
            source[i].Reset();
        }

        HRN hrn = new HRN(source);
    }
}