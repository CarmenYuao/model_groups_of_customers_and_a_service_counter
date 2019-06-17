import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

public class test {

	public static void main(String[] args) throws FileNotFoundException{
		//System.out.println(args[0]);
		//System.out.println(args[1]);
		int constant_T;
		
		ArrayList<Customer> custlist = new ArrayList<Customer>();
		ArrayList<Integer>  breaks = new ArrayList<Integer>();
		//ArrayList<Integer> waitime_list = new ArrayList<Integer>();
		
		//Scanner scan = new Scanner(System.in);
		//System.out.println("Custom File Name: ");
		//String fileName1 = scan.nextLine();
		
		//String input1 = new Scanner(new File(fileName1)).useDelimiter("\\A").next();
		String input1 = new Scanner(new File(args[0])).useDelimiter("\\A").next();
		input1 = input1.replace("ID-NUMBER:","\n").replace("ARRIVAL-TIME:","\n");
		StringTokenizer strTokens = new StringTokenizer(input1);
		
		
		constant_T=Integer.parseInt(strTokens.nextToken());
		String id1 = strTokens.nextToken();
		String artime1=strTokens.nextToken();
		String[] t1= artime1.split(":");
		int earliest = Integer.parseInt(t1[0]);
		Customer cust1= new Customer(id1,artime1,constant_T);
		custlist.add(cust1);
		
		
		while (strTokens.hasMoreTokens()) {
			
			String id = strTokens.nextToken();
			String artime=strTokens.nextToken();
			Customer cu = new Customer(id,artime,earliest,constant_T);
			custlist.add(cu);
		}
		
		int i = 1;
		while(i<custlist.size()) {
			if(custlist.get(i).arrivaltime>=61200) {
				custlist.get(i).waitime=0;
			}
			else if(custlist.get(i).arrivaltime>=custlist.get(i-1).leftime && custlist.get(i).arrivaltime<61200) {
				custlist.get(i).waitime=0;
				int breakk = custlist.get(i).arrivaltime-custlist.get(i-1).leftime;
				breaks.add(breakk);
				custlist.get(i).leftime=custlist.get(i).arrivaltime+custlist.get(i).waitime+constant_T;
			}
			else if(custlist.get(i).arrivaltime<custlist.get(i-1).leftime) {
				if(custlist.get(i-1).leftime<61200){
					custlist.get(i).waitime=custlist.get(i-1).leftime-custlist.get(i).arrivaltime;
					custlist.get(i).leftime=custlist.get(i).arrivaltime+custlist.get(i).waitime+constant_T;
				}
				else if(custlist.get(i-1).leftime>=61200) {
					custlist.get(i).waitime=61200-custlist.get(i).arrivaltime;
					custlist.get(i).leftime=61200;
				}
			}
			i++;
		}   ///custlist a,w,l
		
		
		
		Queue qq= new Queue();
		qq.add(custlist.get(0));
		int k =1;
		int num_customer_served=0;
		Customer last_customer=null;
		int max_que=1;
		//customer come and left
		while(k<custlist.size()) {
			while( qq.first!=null && k<custlist.size()&&qq.first.customer.leftime<custlist.get(k).arrivaltime) {
				qq.pop();
				num_customer_served++;	
			}
			if(k<custlist.size()&&custlist.get(k).arrivaltime+custlist.get(k).waitime<61200) {
				qq.add(custlist.get(k));
				k++;
				if(qq.total>max_que) {
					max_que=qq.total-1;
				}
			}
			else {
				last_customer=custlist.get(k-1);
				break;
			}
		}
		while(qq.first!=null) {
			if(qq.first.next==null) {
				last_customer=qq.first.customer;
			}
			qq.pop();
			num_customer_served++;
		}
		if(last_customer!=null&&last_customer.leftime<61200) {
			int last_break = 61200-last_customer.leftime;
			breaks.add(last_break);
		}
		
		
		
		//longest break             //before 61200 queue empty   61200-last.leftime   should be included but not yet
		int longest_break=breaks.get(0);
		int total_idle=0;
		int j=0;
		while(j<breaks.size()) {
			total_idle=total_idle+breaks.get(j);
			if(breaks.get(j)>longest_break) {
				longest_break=breaks.get(j);
			}
			j++;
		}
		
		
		
		String fileName="output.txt";
	
		//write to a file
		try{
			FileWriter fileWriter = new FileWriter(fileName);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
		
			
			//Scanner scan2 = new Scanner(System.in);
			//System.out.println("QueriesFile:");
			//String file2 = scan2.nextLine();
			
			ArrayList<String> questions = new ArrayList<String>();/////
			//String input2 = new Scanner(new File(file2)).useDelimiter("\\A").next();
			String input2 = new Scanner(new File(args[1])).useDelimiter("\\A").next();
			StringTokenizer strTokens2 =new StringTokenizer(input2,"\n");
			while(strTokens2.hasMoreTokens()) {
				questions.add(strTokens2.nextToken());
			}
			
			//output
			for(int l = 0; l<questions.size();l++) {
				if(questions.get(l).equals("NUMBER-OF-CUSTOMERS-SERVED")) {
					System.out.println(questions.get(l)+":"+num_customer_served);
					//
					String ss=questions.get(l)+":"+num_customer_served;
					bufferedWriter.write(ss);
					bufferedWriter.newLine();
					//
				}
				else if(questions.get(l).equals("LONGEST-BREAK-LENGTH")) {
					System.out.println(questions.get(l)+":"+longest_break);	
					//
					bufferedWriter.write(questions.get(l)+":"+longest_break);
					bufferedWriter.newLine();
					
				}
				else if(questions.get(l).equals("TOTAL-IDLE-TIME")) {
					System.out.println(questions.get(l)+":"+total_idle);
					//
					bufferedWriter.write(questions.get(l)+":"+total_idle);
					bufferedWriter.newLine();
					
				}
				else if(questions.get(l).equals("MAXIMUM-NUMBER-OF-PEOPLE-IN-QUEUE-AT-ANY-TIME")) {
					System.out.println(questions.get(l)+":"+max_que);
					//
					bufferedWriter.write(questions.get(l)+":"+max_que);
					bufferedWriter.newLine();
				}
				else if(questions.get(l).contains("WAITING-TIME-OF")) {
					String idd=questions.get(l).substring(16);
					int xxwaitime=0;
					for (Customer c:custlist) {
						if (c.id.equals(idd)) {
							xxwaitime=c.waitime;
						}
					}
					System.out.println(questions.get(l)+":"+xxwaitime);
					//
					bufferedWriter.write(questions.get(l)+":"+xxwaitime);
					bufferedWriter.newLine();
				}
					
			}
			bufferedWriter.close();
			
		}
		catch(IOException exk) {
			System.out.println( "Error writing file '" + fileName + "'");
			exk.printStackTrace();
		}
		
		
	}
		
		
}
