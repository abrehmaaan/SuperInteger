import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class check {

	public static void main(String[] args) {
		int carry = 0;
		List<Integer> A = new LinkedList<Integer>();
		List<Integer> B = new LinkedList<Integer>();
		List<Integer> C = new LinkedList<Integer>();
		A.add(7);
		A.add(8);
		A.add(9);
		A.add(3);
		A.add(6);
		B.add(4);
		B.add(5);
		B.add(3);
		ListIterator<Integer> iterA = A.listIterator();
		  ListIterator<Integer> iterB = B.listIterator();
		  ListIterator<Integer> iterC = C.listIterator(C.size());
	      
	      while (iterA.hasNext()) {
	    	  int n1 = iterA.next();
	    	  int n2 = 0;
	    	  if(iterB.hasNext()) {
	    		  n2 = iterB.next();
	    	  }
	    	  int result = n1+n2+carry;
	    	  if(result<10) {
	    		  C.add(result);
	    		  carry = 0;
	    	  }
	    	  else {
  	    	  C.add(result%10);
  	    	  carry = result/10;
	    	  }
	      }
	      if(carry>0) {
	    	  C.add(carry);
	      }
	      while (iterC.hasNext()) {
	    	  int n = iterC.next();
	    	  System.out.print(n);
	      }
	}

}
