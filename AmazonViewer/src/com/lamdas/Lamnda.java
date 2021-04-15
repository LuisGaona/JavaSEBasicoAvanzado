package com.lamdas;

public class Lamnda {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
      OnOneListener oneListener= new OnOneListener() {
		
		@Override
		public void OnOne(String message) {
			// TODO Auto-generated method stub
			System.out.println("One: "+ message);
		}
	};
	
	OnOneListener oneListener2=(String message)->{
		System.out.println("One Lamnda:)"+message);
	};
	oneListener.OnOne("Sin Lamda:(");
	oneListener2.OnOne("Com Lambda:)");
	
	OnOneListener oneListener3=message->System.out.println("Tu mensaje");
	
	}

}
