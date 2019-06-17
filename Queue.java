
public class Queue {
	//int number_of_customers_served;   //pop了几个
	//int longest_wait_line;//queue 最长多长
	
	
	public int total =0;
	
	public Node first;
	public Node last;
	
	/*class Node {
		public Node next;
		public Customer customer;
	}*/
	
	
	public Queue add(Customer customer) {
		Node currentlast = last;
		last = new Node();
		last.customer=customer;
		if(total == 0) {
			first=last;
		}
		else {
			currentlast.next=last;
		}
		total++;
		return this;
	}
	
	public Customer pop() {
		if(total==0) {
			return null;
		}
		Customer served = first.customer;
		first=first.next;
		total--;
		if(total==0) {
			last=null;
		}
		return served;
	}

}
