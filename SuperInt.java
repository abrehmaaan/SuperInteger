import java.util.*;
public class SuperInt implements Cloneable, Comparable<SuperInt>{
   /*

      A template file for you to use.
      
      The only code that is correct and should not be changed is
      
      the default constructor
      
      getMagnitude
      magnitudeToString
      toString
      
      The coding you have to do to obtain full credit is
      
      SuperInt(int n)
      SuperInt(String s)
      clone
      compareMagnitudes
      compareTo
      addMagnitudes
      subtractMagnitudes
      add
      negate
      negateMutate
      addMutate
      subtractMutate
      multiplyMagnitudesByDigitAndShift
      multiplyMagnitudes
      multiply
      multiplyMutate
      equals
      
      Their specifications are given below with their stub code.
      
      There are two extra credit methods which if you do according to spec
      are worth an additional 30 points.
            
      
      The class implements arbitrarily large integers with a
      sign and magnitude representation. 

      When you code the operations, you should consider for each input
      which of the three categories it falls in

      negative, that is, < 0
      positive, that is, > 0
      zero, which is neither positive nor negative 

      For a binary operation, that makes 3 x 3 = 9 distinct cases 
      to consider.  Sometimes the same code can work for more than one 
      case, but it is safer first to consider what you need to do for
      each.

      The magnitude is given by a list of Integer objects, digSeq, holding int 
      values from 0 to 9 to represent a single decimal digit.  The whole list
      represents an unsigned decimal numeral, with two conventions 

      1. the high order decimal digit is the last item of the list and the low
         order digit is the first item on the list, so 7509369 would be given in the
         list  9, 6, 3, 9, 0, 5, 7, where 7 is the last item on the list.
         
      2. there are no extra leading 0's, so the only time the last item
         in the list is 0 is when the magnitude is 0, and in that case the
         last 0 is the only item on the list.         

      The sign is given by a boolean data member, isNeg, which is true when the
      represented value is < 0.  The represented value is >= 0 exactly when isNeg
      is false.
      
      In this scheme the values (first item of the list is on the left)
      
      257601  would be represented by isNeg false and digSeq 1, 0, 6, 7, 5, 2
      -7884863 would be represented by isNeg true and digSeq 3, 6, 8, 4, 8, 8, 7
      0  would be represented by isNeg false and digSeq 0
      
      
      The class invariants are
      
      1. the list is never empty, the items on it are never null, and the items 
         contain values in the range of 0 to 9.
      2. if the last item on the list is 0, then that is the only item on the list
         and isNeg is false.
         
      Every object that observes these invariants represents a unique integer and every 
      integer value has a unique representation in this scheme.
      
      You should make sure that any SuperInt objects you create observe these
      invariants and any mutators you code preserve them.
      
    */
   private static int
      RADIX = 10;
   private static Integer
      zeroInteger = Integer.valueOf(0);

   boolean
   isNeg;  // true if the value is < 0, else false
   
   List<Integer> // although Byte would be big enough, it is more efficient to use Integer
   digSeq;
   
   
   /*
      DO NOT MODIFY THIS.
      
      should create the representation of 0
      for this
   */
   public SuperInt(){
      
      digSeq = new LinkedList<Integer>();
      digSeq.add(zeroInteger);
      // isNeg is initialized to false by default
      
   }
   
   
   /*
      YOU MUST CODE THIS
      
      should create the representation of the
      integer n for this
      
      You need to be careful with the extremal values of int.
      
      The usual technique for peeling of the individual digits of 
      a decimal integer from low order to high is to repeatedly % by 10
      to get the low order digit, and then / by 10 to shift the lower 
      order digit off the number.
      
      However, if n is negative, and n % 10 is not 0, it will be a negative
      value.  This is because
      
      i / j  is the truncation of i/j as a real value
      i % j  if i - (i/j * j)
      
      So for example, take i = -13 and j = 3
      
      -13 / 3 = trunc(-13/3) = trunc(-4.3333...) = -4
      -13 % 3 = -13 - (-4 * 3) = -13 - (-12) = -1
      
      Note also, that if n is Integer.MIN_VALUE, then -n will overflow, so
      simply converting negative values to positive will fail in that case.
      
   */
   public SuperInt(int n){
	   digSeq = new LinkedList<Integer>();
	   if(n==Integer.MIN_VALUE) {
		   isNeg = true;
	   }
	   else {
		  if(n<0) {
	    	  isNeg = true;
	    	  n *= -1;
	      }
	      else if(n>0) {
	    	  isNeg = false;
	      }
	      else {
	    	  digSeq.add(zeroInteger);
	    	  isNeg = false;
	      }
	   }
      while(n > 0) {
    	  digSeq.add(n % 10);
    	  n /= 10;
      }
   }
   
   
   /*
      YOU MUST CODE THIS
      
      checks if s matches the pattern 0|-?[1-9][0-9]* by calling
      
      s.matches("0|-?[1-9][0-9]*")
      
      This is the simplest way to do that test.
      
      if not, throw InputMismatchException with the message
      "Improper String value passed to SuperInt constructor."
      
      otherwise, construct the internal representation of 
      the decimal numeral s amounts to for this object
      
   */   
   public SuperInt(String s) throws InputMismatchException{
	   digSeq = new LinkedList<Integer>();
      if(!s.matches("0|-?[1-9][0-9]*")) {
    	  throw new InputMismatchException("Improper String value passed to SuperInt constructor.");
      }
      else{
    	  if(s.charAt(0)=='-') {
    		  isNeg = true;
    	  }
    	  else {
    		  isNeg = false;
    		  s = "0"+s;
    	  }
    	  for(int i = 1;i<s.length();i++) {
    		  digSeq.add(0,Integer.parseInt(String.valueOf(s.charAt(i))));
    	  }
      }
   }
   
   
   /*
      DO NOT MODIFY THIS.
      
      This is used in the testing and must return the digit sequence
      data member of the SuperInt.
      
   */
   List<Integer> getMagnitude(){
      return digSeq;
   }
   
   /*
      DO NOT MODIFY THIS.
      
      Converts the list of digits in a magnitude to a string and
      returns it.
      
      amag is not modified
      
   */
   private static String magnitudeToString(List<Integer> amag){
      StringBuilder bld = new StringBuilder();
      
      ListIterator<Integer> iter = amag.listIterator(amag.size());
      
      while (iter.hasPrevious())
         bld.append(iter.previous().intValue());
      
      return bld.toString();
      
   }
   /*
      DO NOT MODIFY THIS.
      
      Constructs the string numeral for the value represented by
      this.
      
   */
   public String toString(){
      
      StringBuilder str = new StringBuilder();
      
      if (isNeg)
         str.append('-');
      
      str.append(magnitudeToString(digSeq));
      
      return str.toString();
    
   }
   
    
   /*
      YOU MUST CODE THIS
      
   
      makes and returns a copy of this so that there are 
      not common instance specific data members between 
      this and the copy.  They should NOT share the list.
      
      Note, wrapper class objects Integer, Double, etc., are
      immutable.
      
      this is not modified
      
   */
   public SuperInt clone(){
	   SuperInt C = new SuperInt();
	   C.isNeg = isNeg;
	   ListIterator<Integer> iter = digSeq.listIterator(0);
	      
	      while (iter.hasNext())
	         C.digSeq.add(iter.next());
	      
	   return C;
   }
   
   
   /*         
      YOU MUST CODE THIS
       
      
      The magnitude of a integer value n is the absolute
      value of n, |n|.  It is what is given by the list
      data member, so you can accomplish this by comparing
      the lists this and other.
      
      returns 
         a value < 0 if A < B as unsigned integers
         0 if A equals B as unsigned integers
         a value > 0 if A > B as unsigned integers
         
      NEITHER A NOR B SHOULD BE CHANGED.
         
      The way to do this is as follows.
      
      If the lengths of the lists are different, the one
      that is longer has the larger magnitude.
      
      If the lengths are the same, iterate over both lists
      from HIGH order digit to LOW.  Either you reach a position
      where they have different digit values or all are the same
      and you iterate off both lists.  If you iterate off both
      lists, all their digits are the same and they are equal.
      
      If you reach a position where they have different digits,
      the one with the larger digit has the larger magnitude.
         
   */
   private static int compareMagnitudes(List<Integer>A, List<Integer>B){
      if(A.size()>B.size()) {
    	  return 1;
      }
      else if(A.size()<B.size()) {
    	  return -1;
      }
      else {
    	  return magnitudeToString(A).compareTo(magnitudeToString(B));
      }
   }
   
   
   /*
      YOU MUST CODE THIS
      
      
      returns 
         a value < 0 if this < other as signed integers
         0 if this equals other as signed integers
         a value > 0 if this > other as signed integers
         
      Note, once you have coded up compareMagnitudes, then you can
      easily accomplish this by splitting on the following
      nine cases
      
      
      this   other
      <0     <0      compare the two magnitudes and negate the result,
                     because when x < 0 and y < 0, x < y iff
                     |x| > |y|
      <0     0       this < other
      <0     >0      this < other
      0      <0      this > other
      0      0       equal
      0      >0      this < other
      >0     <0      this > other
      >0     0       this > other
      >0     >0      compare the two magnitudes
         
      neither this nor other should be changed
      
      If other is not a SuperInt, return false;
      
   */
   public int compareTo(SuperInt other){
      if(isNeg && other.isNeg) {
    	  return (-1)*compareMagnitudes(digSeq,other.digSeq);
      }
      else if(digSeq.size()==0 || other.digSeq.size()==0) {
    	  if(digSeq.size()<other.digSeq.size()) {
    		  return -1;
    	  }
    	  else if(digSeq.size()>other.digSeq.size()) {
    		  return 1;
    	  }
    	  else {
    		  return 0;
    	  }
      }
      else if(isNeg && other.digSeq.get(other.digSeq.size()-1) == 0) {
    	  return -1;
      }
      else if(isNeg && !other.isNeg) {
    	  return -1;
      }
      else if(digSeq.get(digSeq.size()-1) == 0 && other.isNeg) {
    	  return 1;
      }
      else if(digSeq.get(digSeq.size()-1) == 0 && other.digSeq.get(other.digSeq.size()-1) == 0) {
    	  return 0;
      }
      else if(digSeq.get(digSeq.size()-1) == 0 && !other.isNeg) {
    	  return -1;
      }
      else if(!isNeg && other.isNeg) {
    	  return 1;
      }
      else if(!isNeg && other.digSeq.get(other.digSeq.size()-1) == 0) {
    	  return 1;
      }
      else if(!isNeg && !other.isNeg) {
    	  return compareMagnitudes(digSeq,other.digSeq);
      }
      else {
    	  return -1;
      }
   }
   
   
      /*  
      YOU MUST CODE THIS
             
          
          Create an entirely new list that contains the
          sum of the lists of this and other.
          
          split on
          
          1. A is 0, return a copy of B
          2. B is 0, return a copy of A
          3. neither is 0, so first create a new, empty list.
             then iterate down the A and B lists from
             low order digits to high order, adding the two
             digits and the carry (initially 0) to obtain
             a new digit and a new carry; note you will need
             to correctly handle 
             
             a. A's list is shorter than B's
             b. A's list is longer than B's
             c. the two lists are equal in length
             
             and both the case when the last carry out is 1 or the last 
             carry out is 0.
             
         neither A nor B should be modified
         
      */
   private static List<Integer> addMagnitudes(List<Integer> A, List<Integer> B){
      List<Integer> C;
      if(A.size()==0 || A.get(A.size()-1)==0) {
    	  C = new LinkedList<Integer>(B);
      }
      else if(B.size()==0 || B.get(B.size()-1)==0) {
    	  C = new LinkedList<Integer>(A);
      }
      else {
    	  int carry = 0;
    	  C = new LinkedList<Integer>();
    	  if(A.size()>=B.size()) {
    		  ListIterator<Integer> iterA = A.listIterator(0);
    		  ListIterator<Integer> iterB = B.listIterator(0);
    	      
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
    	  }
    	  else {
    		  ListIterator<Integer> iterA = A.listIterator(0);
    		  ListIterator<Integer> iterB = B.listIterator(0);
    	      
    	      while (iterB.hasNext()) {
    	    	  int n1 = iterB.next();
    	    	  int n2 = 0;
    	    	  if(iterA.hasNext()) {
    	    		  n2 = iterA.next();
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
    	  }
      }
      return C;
   }
      
       
      /*   
          YOU MUST CODE THIS
      
          Assumes the magnitude of A is STRICTLY LARGER than the
          magnitude of B, so you should never call it when that is not true.
          You can use compareMagnitudes to test that.
          
          
          Create an entirely new list that contains the
          list representation of the value of A - the value of B.

          Because A's magnitude is larger than B's magnitude,
          you can iterate from low order digits to high subtracting
          the B digit from the A digit "borrowing" from the next
          higher A digit if necessary (the borrow functions like the
          carry in addition, only you subtract the borrow from the next
          higher digit).
          
          Roughly, the iteration is
          
          initialize borrow to 0;
          
          iterate over A and B from low order digits to high
          
          if (current A digit - borrow - current B digit < 0){
             result digit = current A digit - borrow - current B digit + 10;
             borrow = 1;
          }
          else{
             result digit = current A digit - borrow - current B digit;
             borrow = 0;
          }
             
          Like addition, you need to handle the cases when 
                       
             a. A list is longer than B list
             b. the two lists are equal in length
             
          Since A is strictly larger than B, the A list cannot be shorter than
          the B list, but the B list could be shorter than A list.
          
          Note, if you were to subtract  9999 from 10000, generating
          the digits as you iterate, you would end up with
          
          00001
          
          and you would need to remove the extraneous leading 0's.
          
          
          neither A nor B should be changed
      */
   private static List<Integer> subtractMagnitudes(List<Integer> A, List<Integer> B){
      LinkedList<Integer> C = new LinkedList<Integer>();
      int borrow = 0;
	  ListIterator<Integer> iterA = A.listIterator(0);
	  ListIterator<Integer> iterB = B.listIterator(0);
      
      while (iterA.hasNext()) {
    	  int n1 = iterA.next();
    	  int n2 = 0;
    	  if(iterB.hasNext()) {
    		  n2 = iterB.next();
    	  }
    	  int result;
    	  if (n1 - borrow - n2 < 0){
              result = n1 - borrow - n2 + 10;
              borrow = 1;
           }
           else{
              result = n1 - borrow - n2;
              borrow = 0;
           }
    	  C.add(result);
      }
      while(!C.isEmpty() && C.getLast()==0) {
    	  C.removeLast();
      }
      if(C.isEmpty()) {
    	  C.add(zeroInteger);
      }
      return C;
   }
      
       
   /*
      YOU MUST CODE THIS
      
   
      returns a new object representing
      the signed result of adding this and other 
      
      Again, the easiest way is to split on
      
       
      this   other
      <0     <0      add the two magnitudes
      <0     0       return a copy of this
      <0     >0      
                     use compareMagnitudes to split on
                     |this| < |other|  subtract this's magnitude from other's
                     |this| = |other|  0
                     |this| > |other|  subtract other's magnitude from this's
      0      <0      copy of other
      0      0       copy of other
      0      >0      copy of other
      >0     <0      see splits for <0 >0 above; this case is similar
      >0     0       copy of this
      >0     >0      add the two magnitudes
      
      In all cases, the sign of the result is obvious if you think about
      it.
                 
      neither this nor other should be changed
      
   */
   public SuperInt add(SuperInt other){
      SuperInt C = new SuperInt();
      if(isNeg && other.isNeg) {
    	  C.isNeg=true;
    	  C.digSeq=addMagnitudes(digSeq,other.digSeq);
      }
      else if(isNeg && other.digSeq.size()!=0 && other.digSeq.get(other.digSeq.size()-1)==0) {
    	  C.isNeg=isNeg;
    	  C.digSeq=digSeq;
      }
      else if(isNeg && !other.isNeg) {
    	  if(compareMagnitudes(digSeq,other.digSeq)<0) {
    		  C.isNeg=other.isNeg;
    		  C.digSeq=subtractMagnitudes(other.digSeq,digSeq);
    	  }
    	  else if(compareMagnitudes(digSeq,other.digSeq)==0) {
    		  C.isNeg=false;
    		  C.digSeq.add(zeroInteger);
    	  }
    	  else {
    		  C.isNeg=isNeg;
    		  C.digSeq=subtractMagnitudes(digSeq,other.digSeq);
    	  }
      }
      else if(digSeq.get(digSeq.size()-1)==0 && other.isNeg) {
    	  C.isNeg = other.isNeg;
    	  C.digSeq = other.digSeq;
      }
      else if(digSeq.size()!=0 && other.digSeq.size()!=0 && digSeq.get(digSeq.size()-1)==0 && other.digSeq.get(other.digSeq.size()-1)==0) {
    	  C.isNeg = other.isNeg;
    	  C.digSeq = other.digSeq;
      }
      else if(digSeq.get(digSeq.size()-1)==0 && !other.isNeg) {
    	  C.isNeg = other.isNeg;
    	  C.digSeq = other.digSeq;
      }
      else if(!isNeg && other.isNeg) {
    	  if(compareMagnitudes(digSeq,other.digSeq)<0) {
    		  C.isNeg=other.isNeg;
    		  C.digSeq=subtractMagnitudes(other.digSeq,digSeq);
    	  }
    	  else if(compareMagnitudes(digSeq,other.digSeq)==0) {
    		  C.isNeg=false;
    		  C.digSeq.add(zeroInteger);
    	  }
    	  else {
    		  C.isNeg=isNeg;
    		  C.digSeq=subtractMagnitudes(digSeq,other.digSeq);
    	  }
      }
      else if(!isNeg && other.digSeq.size()!=0 && other.digSeq.get(other.digSeq.size()-1)==0) {
    	  C.isNeg=isNeg;
    	  C.digSeq=digSeq;
      }
      else if(!isNeg && !other.isNeg) {
    	  C.isNeg=false;
    	  C.digSeq=addMagnitudes(digSeq,other.digSeq);
      }
      return C;
   }
   
    
   /*
      YOU MUST CODE THIS
      
   
      returns a new SuperInt object representing the
      negation of this's value
      
      NOTE THE NEGATION OF ZERO IS JUST ZERO.
      
      this should not be changed
     
   */
   public SuperInt negate(){
      SuperInt C = new SuperInt();
      if(digSeq.size()!=0 && digSeq.get(digSeq.size()-1)==0) {
    	  C.isNeg=false;
    	  C.digSeq.add(zeroInteger);
      }
      else {
    	  C.isNeg = !isNeg;
    	  C.digSeq = digSeq;
      }
      return C;
   }
   
   /*
   
      YOU MUST CODE THIS
   
      like the last but this is given the result
      
   For the unary operator negation and the binary operators of add, 
   subtract and multiply if you do either the op or the opMutate version,
   it's not difficult to do the other from it.
   
   Method 1   From mutator to nonmutator

   Implement the mutating version of the operation.
   Implement the clone operation.
   Implement the non-mutating version by cloning the receiver and using the
   mutating version on the clone, then return the clone.

   Method 2  From nonmutator to mutator

   Implement the non-mutating version.
   Implement the mutating version by using the non-mutating version to
   calculate the result into a local variable, and then assign the receiver
   data members the values of the local variable's data members.
   
   */
   
   public void negateMutate(){
	   SuperInt C = negate();
	   isNeg = C.isNeg;
	   digSeq = C.digSeq;
   }
   
   
   /*
      YOU MUST CODE THIS
     
  
      Modifies this to be   this + other
      
      should still work corrrect if this and other are the same object.

   */
   public void addMutate(SuperInt other){
	   SuperInt C = add(other);
	   isNeg = C.isNeg;
	   digSeq = C.digSeq;
   }
   
   
     /*
      YOU MUST CODE THIS
      
     
      returns a new SuperInt object representing the
      the signed result of subtracting other's value
      with this's value
      
      If you do add, and negate, then this method is simple, because
      
      x - y =  x + (-y)
      
      neither this nor other should be changed
   */
   public SuperInt subtract(SuperInt other){
      return this.add(other.negate());
   }
   
   
   // see earlier comments for addMutate

   //   YOU MUST CODE THIS
   // should still work correctly if this and other are the same object
         
   public void subtractMutate(SuperInt other){
	   SuperInt C = subtract(other);
	   isNeg = C.isNeg;
	   digSeq = C.digSeq;
   }
   
   
   //  the next several methods build to the multiply operation
   /*
      
      if you consider a typical multiplication
      
           78913
             462
           -----
          157826
         4734780 
        31565200   
        --------
        36457806        
        
        You will note that the computation can be broken down into two parts
        
        1. a nested iteration where in the outer loop we iterate over the digits of 
           the lower factor (462 in this example), and multiple the higher factor
           by each of the digits and add an increasing number of 0's to obtain 
           the three addends, 157826, 4734780, and 31565200.
           
        2. add the addends to obtain the final product.
        
        This breakdown informs the following decomposition of the multiplication 
        operation into simpler parts as follows.
        
        
     */ 
   private static List<Integer> multiplyMagnitudeByDigitAndShift(List<Integer> list, 
   int digit, int zeros){
      /*  
         list will be the magnitude of the higher factor, 78913 in our example,
         which would be given as the list 3, 1, 9, 8, 7

         digit will be a single digit from the lower factor, 462 in our example,
         so it will be successively 2, 6, and 4.

         zeros will be the number of will be the number of 0's to add at the front
         of the result, and so would successively be 0, 1, and 2
         
         The assumptions are
         
         list is a non empty sequence of Integers following our conventions
         
         digit is an int value in the range from 0 to 9
         
         zeros is a nonnegative integer value
         
         The method should return the magnitude that is obtained by multiplying
         list by the single digit with zeros 0's added at the front(that is, the low
         order of the list).
         
         YOU MUST USE A LISTITERATOR to iterate over list.  You can use the add method
         to add the result digits to the new list and to add the 0's on the front.
         
         You may want to handle the digit values of 0 and 1 as special cases.
         
         
         list must not be modified by the method.
         
         
      */
      List<Integer> result = new LinkedList<Integer>();
      for(int i = 0;i<zeros;i++) {
    	  result.add(0);
      }
      ListIterator<Integer> iter = list.listIterator(0);
      int carry = 0;
      while (iter.hasNext()) {
    	  int n1 = iter.next();
    	  int r = (n1 * digit) + carry;
    	  result.add(r%10);
    	  carry = r/10;
      }
      if(carry>0) {
    	  result.add(carry);
      }
      return result;
   }
         
   
      /*
      
      YOU MUST CODE THIS
         
         Takes two input lists, A and B, and produces an entirely new
         list, call it C, such that C is the magnitude of the product
         of A and B.
         
         you can split it this way.
         
         Simple Cases
         
         A       B
         0       any    C is the list for 0
         any     0      C is the list for 0
         1       any    copy B into C
         any     1      copy A into C
         
         If A and B are none of those, then you know each is >1.  We will 
         calculate the product into a List<Integer> C.
         
         Suppose A's size is a and B's size is b, and that
         a >= b (if not, then swap the two references).  You want to iterate
         over the digits of the shorter list, B, and have A serve as the
         the higher factor.
         
         Initialize a result list C to be 0.
         numZeros = 0
         Iterate of the digits of B; for each digit n{
             temp = multiplyMagnitudeByDigitAndShift(A, n, numZeros);
             C = addMagnitudes(C, temp);
             numZeros++;
         }
         
         return C;
            
         YOU MUST USE A LISTITERATOR FOR B 
         
         neither A nor B should be changed
      */
   static List<Integer> multiplyMagnitudes(List<Integer> A, List<Integer> B){
      LinkedList<Integer> C;
      if(A.get(A.size()-1)==0 || B.get(B.size()-1)==0) {
    	  C = new LinkedList<Integer>();
    	  C.add(zeroInteger);
      }
      else if(A.size()==1 && A.get(0)==1) {
    	  C = new LinkedList<Integer>(B);
      }
      else if(B.size()==1 && B.get(0)==1) {
    	  C = new LinkedList<Integer>(A);
      }
      else {
    	  int zeros = 0;
    	  SuperInt R = new SuperInt();
    	  if(A.size()>=B.size()) {
    		  ListIterator<Integer> iterB = B.listIterator(0);
    		  	
    	      while (iterB.hasNext()) {
    	    	  int n = iterB.next();
    	    	  LinkedList<Integer> Z = new LinkedList<Integer>(multiplyMagnitudeByDigitAndShift(A, n, zeros));
    	    	  zeros++;
    	    	  R.digSeq = addMagnitudes(R.digSeq, Z);
    	      }
    	      
    	  }
    	  else {
    		  ListIterator<Integer> iterA = A.listIterator(0);
    	      
    	      while (iterA.hasNext()) {
    	    	  int n = iterA.next();
    	    	  LinkedList<Integer> Z = new LinkedList<Integer>(multiplyMagnitudeByDigitAndShift(B, n, zeros));
    	    	  zeros++;
    	    	  R.digSeq = addMagnitudes(R.digSeq, Z);
    	      }
    	  }
    	  C = new LinkedList<Integer>(R.digSeq);
      }
      return C;

   }      
         
   /*
      YOU MUST CODE THIS
      
   
      returns a new SuperInt object representing the
      the signed result of multiplying this's value
      with other's value
      
      
      Once you do the multiplyMagnitudes, you can use it
      to create the magnitude of the result. This is because

      |x * y| = |x| * |y|

      The sign is easily calculated from the signs of this
      and other.  If the result represents 0, it is not negative,
      else it's negative if the signs of this and other
      disagree. It's positive if they have the same sign.
      
      neither this nor other should be changed
   */      
   public SuperInt multiply(SuperInt other){
      SuperInt product = new SuperInt();
      product.digSeq = multiplyMagnitudes(digSeq, other.digSeq);
      if(product.digSeq.get(product.digSeq.size()-1)==0 || isNeg==other.isNeg) {
    	  product.isNeg = false;
      }
      else {
    	  product.isNeg = true;
      }
      return product;
   }  

   
   // like the last, but this is modified to hold the result
   // YOU MUST CODE THIS
   // should work if this and other are the same object
         
   public void multiplyMutate(SuperInt other){
	   SuperInt C = multiply(other);
	   isNeg = C.isNeg;
	   digSeq = C.digSeq;
   }
   
    
   /*
      YOU MUST CODE THIS
      
   
      returns true if this and other are both SuperInt objects and
      represent the same value, else false
      
      Note, if Object is NOT a SuperInt object, the method should
      return false.
      
   */
   public boolean equals(Object other){
      if(other instanceof SuperInt) {
    	  SuperInt O = new SuperInt();
    	  O=(SuperInt) other;
    	  if(isNeg!=O.isNeg) {
    		  return false;
    	  }
    	  else {
    		  if(digSeq.equals(O.digSeq)) {
    			  return true;
    		  }
    		  else {
    			  return false;
    		  }
    	  }
      }
      else {
    	  return false;
      }
   }
   /*
   
      for the extra credit; this is difficult to do.  
      It is supposed to calculate the result of this / other 
      into a new SuperInt object.
      
      If other represents 0, then throws the exception with message
      "SuperInt div divisor is 0."
      
   */
   public SuperInt div(SuperInt other)throws IllegalArgumentException{
		  return new SuperInt();
		  
   }
   
   
   // like the last, but this is modified to be the result
   // again, if you do one, it is not hard to use it to do the other;
   public void divMutate(SuperInt m)throws IllegalArgumentException{
	   
   }
	   
     
   public static void main(String[] a)throws Exception{
      
      // tests for constructors
      SuperInt[] examples = {
            new SuperInt(),
            new SuperInt(0),
            new SuperInt(1),
            new SuperInt(-1),
            new SuperInt(Integer.MAX_VALUE),
            new SuperInt(Integer.MIN_VALUE),
            new SuperInt(RADIX),
            new SuperInt(-RADIX),
            new SuperInt(2000000000),
            new SuperInt(-2000000000),  
            new SuperInt("0"),
            new SuperInt("1"),
            new SuperInt("-1"),     
            new SuperInt("1000000000"),
            new SuperInt("-1000000000"),
            new SuperInt("2000000000"),
            new SuperInt("-2000000000"),  
            new SuperInt("10000000001000000000000"),
            new SuperInt("-10000000001000000000000"),
            new SuperInt("20000000002000000000000"),
            new SuperInt("-20000000002000000000000"),  
            new SuperInt("999999999999999999999999999"),
            new SuperInt("999999999999999999999999999999999999"),
            new SuperInt("-900000000000000000999999999"),
            new SuperInt("999999999999000000000000000000000000"),
            new SuperInt("-999999999999999999999999999"),
            new SuperInt("-999999999999999999999999999999999999"),
            new SuperInt("1234567890123456789012345678901234567"),
            new SuperInt("-1234567890123456789012345678901234567")
            
      };
      // tests for add and subtract
      SuperInt[] addExamples = {
            new SuperInt(),
            new SuperInt(1),
            new SuperInt(-1),
            new SuperInt(-2),
            new SuperInt(-3),
            new SuperInt(-4),
            new SuperInt(-5),
            new SuperInt(-6),
            new SuperInt(-7),
            new SuperInt(-8),
            new SuperInt(-9),
            new SuperInt(2),
            new SuperInt(3),
            new SuperInt(4),
            new SuperInt(5),
            new SuperInt(6),
            new SuperInt(7),
            new SuperInt(8),
            new SuperInt(9),
            new SuperInt(-99),
            new SuperInt(-100),
            new SuperInt(-181),
            new SuperInt(99),
            new SuperInt(100),
            new SuperInt(181),
            new SuperInt(15),
            new SuperInt(25),
            new SuperInt(35),
            new SuperInt(45),
            new SuperInt(55),
            new SuperInt(65),
            new SuperInt(75),
            new SuperInt(85),
            new SuperInt(95),
            new SuperInt(0),
            new SuperInt(Integer.MAX_VALUE),
            new SuperInt(Integer.MIN_VALUE),
            new SuperInt("987654321987654321987654321"),
            new SuperInt("123456789123456789123456789"),
      };


      // test cases for multiply
      SuperInt[] multiplyExamples = {
            new SuperInt(),
            new SuperInt(1),
            new SuperInt(-1),
            new SuperInt(2),
            new SuperInt(-2),
            new SuperInt(3),
            new SuperInt(-3),
            new SuperInt(-4),
            new SuperInt(5),
            new SuperInt(-6),
            new SuperInt(7),
            new SuperInt(-8),
            new SuperInt(9),
            new SuperInt(-99),
            new SuperInt(-100),
            new SuperInt(100),
            new SuperInt(15),
            new SuperInt(25),
            new SuperInt(35),
            new SuperInt(-45),
            new SuperInt(55),
            new SuperInt(65),
            new SuperInt(75),
            new SuperInt(85),
            new SuperInt(95),
            new SuperInt(-12),
            new SuperInt(22),
            new SuperInt(32),
            new SuperInt(42),
            new SuperInt(52),
            new SuperInt(62),
            new SuperInt(72),
            new SuperInt(82),
            new SuperInt(92),
            new SuperInt("111222333444555666777888999"),
            new SuperInt("123456789123456789123456789"),
            new SuperInt("-101100110091008762"),
            new SuperInt("0")
      };
      
       
      System.out.println("\nTests for constructors\n");
      for (int i = 0; i < examples.length; i++)
         System.out.println("i = " + i + " " + examples[i].toString());
      
      System.out.println("\nTests for clone\n");
      for (int i = 0; i < examples.length; i++){
         SuperInt temp = examples[i].clone();
         // boolean should be false but the strings the same
         System.out.println("i = " + i + " " + (examples[i] == temp) + " " +
         examples[i].toString() + " " + temp.toString());
      }
     
      System.out.println("\nTests for compareMagnitudes\n");
      for (int i = 0; i < examples.length; i++){
         List<Integer> A = examples[i].getMagnitude();
         System.out.println("i = " + i + " A magnitude is " + magnitudeToString(A));
         for (int j = 0; j < examples.length; j++){
            List<Integer> B = examples[j].getMagnitude();
            int res = compareMagnitudes(A,B);
            System.out.print("(i,j) = (" + i + ", " + j + ") B magnitude is " + 
            magnitudeToString(B)); 
            System.out.println(" result is " + (res == 0? 0 : (res > 0? 1 : -1)));
         }
      }
          
      System.out.println("\nTests for compareTo\n");
      for (int i = 0; i < examples.length; i++){
         SuperInt A = examples[i];
         System.out.println("i = " + i + " A is " + A.toString());
         for (int j = 0; j < examples.length; j++){
            SuperInt B = examples[j];
            int res = A.compareTo(B);
            System.out.print("(i,j) = (" + i + ", " + j + ") B is " + 
            B.toString()); 
            System.out.println(" A.compareTo(B) is " + (res == 0? 0 : (res > 0? 1 : -1)));
         }
      }
        
         
      System.out.println("\nTests for addMagnitudes\n");
      for (int i = 0; i < addExamples.length; i++){
         List<Integer> A = addExamples[i].getMagnitude();
         System.out.println("i = " + i + " A magnitude is " + magnitudeToString(A));
         for (int j = 0; j < addExamples.length; j++){
            List<Integer> 
            B = addExamples[j].getMagnitude(),
            C = addMagnitudes(A, B);
            System.out.println("(i,j) = (" + i + ", " + j + ") B is " + magnitudeToString(B)
            + " A + B is " + magnitudeToString(C));
         }
      }
      
        
      System.out.println("\nTests for subtractMagnitudes\ncompareMagnitudes must be correct"
      + " for this test to work");
      for (int i = 0; i < addExamples.length; i++){
         List<Integer> A = addExamples[i].getMagnitude();
         System.out.println("i = " + i + " A magnitude is " + magnitudeToString(A));
         for (int j = 0; j < addExamples.length; j++){
            List<Integer> 
            Minuend, Subtrahend,
            B = addExamples[j].getMagnitude();
            int cmpRes = compareMagnitudes(A,B);
            System.out.print("(i,j) = (" + i + ", " + j + ") ");
            if (cmpRes == 0)
               System.out.println(" they are equal so subtraction is not performed.");
            else{
               if (cmpRes > 0){ // A > B
                  Minuend = A;
                  Subtrahend = B;
               }
               else{ // B > A
                  Minuend = B;
                  Subtrahend = A;
               }                  
               List<Integer> C = subtractMagnitudes(Minuend, Subtrahend);
               System.out.println("(i,j) = (" + i + ", " + j + ") B is " + 
               magnitudeToString(B)
               + " Greater - Lesser " + magnitudeToString(C));
            }
         }
      }
      System.out.println("\nTests for add\n");
      for (int i = 0; i < addExamples.length; i++){
         SuperInt A = addExamples[i];
         System.out.println("i is " + i + " A is " + A);
         for (int j = 0; j < addExamples.length; j++){
            SuperInt B = addExamples[j];
            System.out.println("(i,j) = (" + i + ", " + j + ") B is " + B 
            + " A + B is " + A.add(B));
         }
      }
				  	  
      System.out.println("\nTests for negate\n");
      for (int i = 0; i < addExamples.length; i++){
         SuperInt A = addExamples[i];
         System.out.println("i is " + i + " negation of " + A + " = " +
         A.negate());
      }
            
              	  
      System.out.println("\nTests for negateMutate\n" +
      "clone must work for this test to work");
      for (int i = 0; i < addExamples.length; i++){
         SuperInt A = addExamples[i].clone();
         System.out.print("i is " + i + " original A is " + A);
         A.negateMutate();
         System.out.println(" After negateMutate it is " + A);
      }
               	  
      System.out.println("\nTests for addMutate\n" +
      "clone must work for this test to work");
      for (int i = 0; i < addExamples.length; i++){
         SuperInt A = addExamples[i].clone();
         System.out.println("i is " + i + " original A is " + A);
         for (int j = 0; j < addExamples.length; j++){
            SuperInt B = addExamples[j];
            System.out.print("j is " + j + " B is " + B);
            A.addMutate(B);
            System.out.println(" After A.addMutate(B), A is " + A);
            A = addExamples[i].clone();
         }
      }
             
				  
      System.out.println("\nTests for subtract\n");
      for (int i = 0; i < addExamples.length; i++){
         SuperInt A = addExamples[i];
         System.out.println("i is " + i + " A is " + A);
         for (int j = 0; j < addExamples.length; j++){
            SuperInt B = addExamples[j];
            System.out.println("(i,j) = (" + i + ", " + j + ") B is " + B +
            " A - B is " +
                  A.subtract(B));
         }
      }     	 
      
      System.out.println("\nTests for subtractMutate\n" +
      "clone must work for this test to work");
      for (int i = 0; i < addExamples.length; i++){
         SuperInt A = addExamples[i].clone();
         System.out.println("i is " + i + " original A is " + A);
         for (int j = 0; j < addExamples.length; j++){
            SuperInt B = addExamples[j];
            System.out.print("j is " + j + " B is " + B);
            A.subtractMutate(B);
            System.out.println(" After A.subtractMutate(B), A is " + A);
            A = addExamples[i].clone();
         }
      }
             
      System.out.println("\nTests for multiplyMagnitudeByDigitAndShift\n");
      for (int i = 0; i < addExamples.length; i++){
         List<Integer> A = addExamples[i].getMagnitude();
         System.out.println("i is " + i + " A is " + magnitudeToString(A));
         
         for (int dig = 0; dig < 10; dig++){
            System.out.println("digit factor is " + dig);
            for (int shift = 0; shift < 9;){
               System.out.println("zeros is " + shift + " result is " +
               magnitudeToString(multiplyMagnitudeByDigitAndShift(A, dig, shift)));
               shift += 4;
            }
         }
      }
      
      
         
      System.out.println("\nTests for mutiplyMagnitudes\n");
      for (int i = 0; i < multiplyExamples.length; i++){
         List<Integer> A = multiplyExamples[i].getMagnitude();
         System.out.println("i = " + i + " A magnitude is " + magnitudeToString(A));
         for (int j = 0; j < multiplyExamples.length; j++){
            List<Integer> 
            B = multiplyExamples[j].getMagnitude(),
            C = multiplyMagnitudes(A, B);
            System.out.println("(i,j) = (" + i + ", " + j + ") B is " + magnitudeToString(B)
            + " A * B is " + magnitudeToString(C));
         }
      }
      
      System.out.println("\nTests for multiply\n");
      for (int i = 0; i < multiplyExamples.length; i++){
         SuperInt A = multiplyExamples[i];
         System.out.println("i is " + i + " A is " + A);
         for (int j = 0; j < multiplyExamples.length; j++){
            SuperInt B =  multiplyExamples[j],
            C = A.multiply(B);
            System.out.println("(i,j) = (" + i + ", " + j + ") B is " +  B + " A * B is "
            + C);
         }
      }
      
      System.out.println("\nTests for multiplyMutate\n" +
      "clone must work for this test to work");
      for (int i = 0; i < multiplyExamples.length; i++){
         SuperInt A = multiplyExamples[i].clone();
         System.out.println("i is " + i + " original A is " + A);
         for (int j = 0; j < multiplyExamples.length; j++){
            SuperInt B = multiplyExamples[j];
            System.out.print("j is " + j + " B is " + B);
            A.multiplyMutate(B);
            System.out.println(" After A.multiplyMutate(B), A is " + A);
            A = multiplyExamples[j].clone();
         }
      }
      
      
      System.out.println("\nTests for equals\n");
      for (int i = 0; i < multiplyExamples.length; i++){
         SuperInt A = multiplyExamples[i];
         System.out.println("i is " + i + " A is " + A);
         for (int j = 0; j < multiplyExamples.length; j++){
            SuperInt B =  multiplyExamples[j];
            boolean isEq = A.equals(B);
            
            System.out.println("(i,j) = (" + i + ", " + j + ") B is " +  B + " A = B is "
            + (isEq? "true" : "false"));
         }
      }
      
      // try equals with some non-SuperInt objects
      SuperInt A = new SuperInt();
      Object X = Double.valueOf((double) 0);
      Integer N = Integer.valueOf(0);
      String S = "0";
      
      System.out.println("A = X " + A.equals(X));
      System.out.println("A = N " + A.equals(N));
      System.out.println("A = S " + A.equals(S));
      
   }
   
   
}
