package statistics.ntels.Regression;

import java.util.Arrays;
import java.util.List;

public class RMain {

	public static void main(String[] args) {
		
		
		RegressionAnalysis regressionAnalysis = new RegressionAnalysis();
		
		regressionAnalysis.setRegressionHigh(0.1f);
		regressionAnalysis.setRegressionLow(0.1f);
		
		
		
		try {
			System.out.println(
					regressionAnalysis.getSimpleRegressionAnalysis(Arrays.asList(151,161,174,178,180,182,185), Arrays.asList(161,174,178,180,182,180,185), 165)		
					);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//System.out.println(regressionAnalysis.getProcessedCount());
		
		List x1 = Arrays.asList(150,160
    			,170,175,178);
    	List x2 = Arrays.asList(1,2
    			,3,4,4);
    	
    	List y3 = Arrays.asList(176,179
    			,180,182,182);
    	

    	
    	double[][] x = { 
        		
        		{  1,  150,120},
                        
        		{  1,  160,130},
                
        		{  1,  170 ,140},
                
        		{  1,  180,150},
                
        		{  1,  190,160 }
                         
        
        			
        	};
    	//Arrays.asList(150,160,170,180,190), Arrays.asList(176,179,180,190,190)
        
        double[] y = {176,179,180,190,190};
        RegressionAnalysis regression = new RegressionAnalysis();
        regression.setRegressionHigh(0.1f);
        regression.setRegressionHigh(0.1f);
        
        
         try {
			System.out.println(regression.getSimpleRegressionAnalysis(x1, y3, 165));
			
			//System.out.println(regression.getProcessedCount());
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        try {
			
		//	System.out.println(regression.getThreeRegressionAnalysis(x1,x2,y3,165,2));
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
     
		
		
		
	}
}
