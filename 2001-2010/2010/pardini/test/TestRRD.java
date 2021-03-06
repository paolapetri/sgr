import RoundRobinDB.*;
import java.io.IOException;

public class TestRRD {
    public static void main(String[] args) throws IOException{
    	RecordAttribute[] RS = new RecordAttribute[2];
    	RS[0] = new RecordAttribute("Lettera", 50);
    	RS[1] = new RecordAttribute("Valore", 50);
    	
    	ExampleAggregationFunction aggregationFunction = new ExampleAggregationFunction();
    	DBAggregatedStructure[] DBASs = new DBAggregatedStructure[2];
		DBASs[0] = new DBAggregatedStructure("lettereAggregate", "lettere", 12, 60, aggregationFunction, 24);
		DBASs[1] = new DBAggregatedStructure("lettereAggregate2", "lettereAggregate", 24, 120, aggregationFunction, 7);
    		
    	DBStructure DBS = null;
    	try{DBS = new DBStructure("lettere", 5, 2, 12, DBASs, 2, RS);
    	}catch(IllegalArgumentException e){System.out.println("ERRORE DBS"); return;}	
    
    	RoundRobinDB RRD = null;
    	try{RRD = new RoundRobinDB(DBS);
    	}catch(IOException e){System.out.println("ERRORE RRD"); return;}
    	
    	String[] lettere = {"A","B","C","A","A","C","B"};
    	String[] valori = {"10","5","7","13","2","4","9"};
    	
    	String[] lettere2 = {"B","A","B","C","B","B","C"};
    	String[] valori2 = {"3","18","2","8","6","4","1"};
    	
    	long[] tempiSleep = {0,8,5,8,10,18,15};
    	
    	String[][] insert = new String[2][2];
    	
    	insert[0][0]=lettere[0];
    	insert[0][1]=valori[0];
    	insert[1][0]=lettere2[0];
    	insert[1][1]=valori2[0];
    	RRD.insert(insert);
    	
    	for(int i = 1; i < tempiSleep.length; i++){
    		try{
    				Thread.sleep(tempiSleep[i]*1000);
    		}catch(InterruptedException e){ return;}    	
    		insert[0][0]=lettere[i];
    		insert[0][1]=valori[i];
    		insert[1][0]=lettere2[i];
    		insert[1][1]=valori2[i];
    		RRD.insert(insert);    		
    	}
    	
    	System.out.println("");
    	System.out.println("Last 12 slot of DB lettere");
    	Object[][] select = RRD.select("lettere",12);
    	
    	for(int i = 0; i < select.length; i++){
    		if(i%DBS.getMaxRecordForTimeStep()==0)System.out.println("---------------------------------------");
    		for(int j = 0; j < select[i].length; j++){
    			System.out.print(RS[j].getAttributeName()+": "+(String) select[i][j]+" ");
    		}
    		System.out.println("");
    	}
    	System.out.println("---------------------------------------");
    	
    	System.out.println("");
    	System.out.println("Last 1 slot of DB lettereAggregate");
    	select = RRD.select("lettereAggregate",1);
    	
    	for(int i = 0; i < select.length; i++){
    		if(i%DBS.getMaxRecordForTimeStep()==0)System.out.println("---------------------------------------");
    		for(int j = 0; j < select[i].length; j++){
    			System.out.print(RS[j].getAttributeName()+": "+(String) select[i][j]+" ");
    		}
    		System.out.println("");
    	}
    	System.out.println("---------------------------------------");
    	
    	try{
				Thread.sleep(70*1000);
		}catch(InterruptedException e){ return;}
		
		System.out.println("");
    	System.out.println("Last 12 slot of DB lettere");
    	select = RRD.select("lettere",12);
    	
    	for(int i = 0; i < select.length; i++){
    		if(i%DBS.getMaxRecordForTimeStep()==0)System.out.println("---------------------------------------");
    		for(int j = 0; j < select[i].length; j++){
    			System.out.print(RS[j].getAttributeName()+": "+(String) select[i][j]+" ");
    		}
    		System.out.println("");
    	}
    	System.out.println("---------------------------------------");
		
		System.out.println("");
    	System.out.println("Last 2 slot of DB lettereAggregate");
    	select = RRD.select("lettereAggregate",2);
    	
    	for(int i = 0; i < select.length; i++){
    		if(i%DBS.getMaxRecordForTimeStep()==0)System.out.println("---------------------------------------");
    		for(int j = 0; j < select[i].length; j++){
    			System.out.print(RS[j].getAttributeName()+": "+(String) select[i][j]+" ");
    		}
    		System.out.println("");
    	}
    	System.out.println("---------------------------------------");
    	
    	System.out.println("");
    	System.out.println("Last 1 slot of DB lettereAggregate2");
    	select = RRD.select("lettereAggregate2",1);
    	
    	for(int i = 0; i < select.length; i++){
    		if(i%DBS.getMaxRecordForTimeStep()==0)System.out.println("---------------------------------------");
    		for(int j = 0; j < select[i].length; j++){
    			System.out.print(RS[j].getAttributeName()+": "+(String) select[i][j]+" ");
    		}
    		System.out.println("");
    	}
    	System.out.println("---------------------------------------");
    	
    	RRD.close();
    }  
}