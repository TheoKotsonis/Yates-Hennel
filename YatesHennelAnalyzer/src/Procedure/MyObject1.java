package Procedure;

public class MyObject1 {
	public  int inner_key; 
	public int inner_value; 
	public MyObject1 (int inner_key, int inner_value)
	{
		this.inner_key=inner_key;
		this.inner_value=inner_value;
	}
	public void setKey( int inner_key1)
	{
		inner_key=inner_key1;
	}
	public void setValue( int inner_value1)
	{
		inner_value=inner_value1;
	}
	
	public int getKey() {
		return inner_key;
	}
	public int getValue() {
		return inner_value;
	}
	public int both()
	{
		return inner_key+inner_value;
	}
	@Override
	public String toString() {
		return inner_key +"->"+inner_value;
	}
}


