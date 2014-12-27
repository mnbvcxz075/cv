package cv;



public class Main{



	public static void main (String[] args) {
		UseImage uimg=null;
		try {
			uimg = new UseHSV();
		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

		while(true){
			try {
				uimg.update();
			} catch (Exception e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
		}

	}



}

