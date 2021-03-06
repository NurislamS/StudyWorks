package binarynumber;

public class BinaryNumber {
  
  private final boolean[] number;
  
  public BinaryNumber(boolean[] number){
    this.number = new boolean[number.length];
    System.arraycopy(number, 0, this.number, 0, number.length);
  }
  
  public BinaryNumber(int number){
    if(number < 0){
      throw new IllegalArgumentException("The number must be greater 0");
    }
    String binaryNumber = Integer.toString(number, 2);
    this.number = new boolean[binaryNumber.length()];
    int size = 0;
    for(char bit : binaryNumber.toCharArray()){
      this.number[size] = (bit != '0');
      size++;
    }
  }
  
  @Override
  public String toString(){
    StringBuilder str = new StringBuilder(this.number.length);
    for(boolean bit : this.number){
      str.append(bit?'1':'0');
    }
    return str.toString();
  }
  public int getDecimal(){
    return Integer.parseInt(this.toString(), 2);
  }
  
  public boolean getBit(int pos){
    if(pos < 0 || pos >= this.getSize()){//******************************************************
      throw new IllegalArgumentException("Position must be between 0 and " + this.getSize());
    }
    return this.number[pos];
  }
  
  public boolean[] getNumber(){
    boolean[] numb = new boolean[this.number.length];//********************************************
    System.arraycopy(this.number, 0, numb, 0, this.number.length);
    return numb;
  }
  
  public int getSize(){
    return this.number.length;
  }
  
  public BinaryNumber widerTo(int size){
    if(this.getSize() >= size){
      throw new IllegalArgumentException("The number has more or the same number of digits then " + size + " (" + this.getSize());
    }
    boolean[] newNumber = new boolean[size];
    System.arraycopy(this.number, 0, newNumber, size - this.number.length, this.number.length);//*********************************************0 --> size - this.number.length
    return new BinaryNumber(newNumber);
  }
  
  public boolean isZero(){
    boolean flag;
    flag = true;
    for(boolean bit : this.number){
      if(bit){
        flag = false;
      }
    }
    return flag;
  }
  
  public boolean isEven(){
    return !this.number[this.number.length - 1];//*******************************************
  }
  
  public BinaryNumber divideByTwo(){
    boolean[] newNumber = new boolean[this.number.length - 1];//************-1
    //newNumber[0] = false;
    System.arraycopy(this.number, 0, newNumber, 0, this.number.length - 1);//******* 0 --> 1
    return new BinaryNumber(newNumber);
  }
  
  public BinaryNumber multiplyByTwo(){
    boolean[] newNumber = new boolean[this.number.length+1];
    newNumber[newNumber.length - 1] = false; //**************************************************   
    System.arraycopy(this.number, 0, newNumber, 0, this.number.length);
    return new BinaryNumber(newNumber);
  }
  
  public BinaryNumber addNumber(BinaryNumber number){
    boolean[] newNumber;
    if(number.getSize() > this.getSize()){
      newNumber = this.widerTo(number.getSize()).getNumber();
    }
    else if(this.getSize() > number.getSize()){
      number = number.widerTo(this.getSize());//**************************number.widerTo(this.getSize());
      newNumber = this.getNumber();
    }
    else{
      newNumber = this.getNumber();
    }
    //*****************ADDED{
    int temp_size;
    temp_size = newNumber.length + 1;
    newNumber = this.widerTo(temp_size).getNumber();
    number = number.widerTo(temp_size);
    //------------------------------------}
    boolean adding = false;
    for(int pos = number.getSize()-1 ; pos >= 0 ; pos--){
      boolean newBit = (newNumber[pos] != number.getBit(pos));
                                                               //<-|
      boolean newAdding = ((newNumber[pos] && number.getBit(pos)) || (newBit && adding));   //
      newBit = (newBit != adding);                                                                                       //--|
      newNumber[pos] = newBit;
      adding = newAdding;
    }
    BinaryNumber result = new BinaryNumber(newNumber);
    return result;
  }
  
  public BinaryNumber multiplyBy(BinaryNumber b){
    if(b.isZero()){
      return new BinaryNumber(0);
    }
    BinaryNumber z = this.multiplyBy(b.divideByTwo());
    if(b.isEven()){
      return z.multiplyByTwo();
    }
    else{
      return this.addNumber(z.multiplyByTwo());
    }
  }
  
}
