package statistics.ntels.Regression;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import Jama.Matrix;
import Jama.QRDecomposition;

public class RegressionAnalysis {

//	private  int N = 0;        // number of 
//    private  int p = 0;        // number of dependent variables
//    private Matrix beta = null;  // regression coefficients
//    private double SSE;         // sum of squared
//    private double SST;         // sum of squared
	
	private static float regressionHigh = 0.1f;
	
	// standard cuting low percent %
	private static float regressionLow = 0.1f;
	
	private static float processedCount=0.f;
	
	
//    public double beta(int j) {
//        return beta.get(j, 0);
//    }
//
//    public double R2() {
//        return 1.0 - SSE/SST;
//    }
//    
//	public static float getRegressionHigh() {
//		return regressionHigh;
//	}



	public static void setRegressionHigh(float regressionHigh) {
		RegressionAnalysis.regressionHigh = regressionHigh;
	}



	public static float getRegressionLow() {
		return regressionLow;
	}



	public static void setRegressionLow(float regressionLow) {
		RegressionAnalysis.regressionLow = regressionLow;
	}



	public static float getProcessedCount() {
		return processedCount;
	}

/*	public double getSimpleRegressionAnalysis(List<? extends Number> xList, List<? extends Number> yList , Number x) throws Exception {
		
		long OriginalCount = yList.stream().count();
		long deleteLowCount = (long) Math.ceil((OriginalCount*regressionHigh));
		long deleteHighCount = (long) Math.ceil((OriginalCount*regressionLow));
		
		
		ArrayList<RegreesionData> resultList =new ArrayList<>();
		
		if(yList.size() != xList.size()) throw new Exception("GET Correlation SIZE Matching Error ");
		
		if(yList.size() < 2) throw new Exception("GET Correlation not enough table size ERROR ");
		
		
		for (int i = 0; i < yList.size(); i++) {
			
			RegreesionData data = new RegreesionData();
			data.setX(xList.get(i));
			data.setY(yList.get(i));
			
			resultList.add(data);
			
		}
		
		resultList = (ArrayList<RegreesionData>) resultList.stream().sorted(comparing(RegreesionData::getY)).collect(toList()); //y_data sorted by accending
		resultList = (ArrayList<RegreesionData>) resultList.stream().limit(OriginalCount-deleteHighCount).skip(deleteLowCount).collect(toList()); //cut data 
		
		
		OptionalDouble avgX = resultList.stream().mapToDouble(RegreesionData::getX).average();
		OptionalDouble avgY = resultList.stream().mapToDouble(RegreesionData::getY).average();
		
		double upper = resultList.stream().mapToDouble(item -> (item.getX() - avgX.getAsDouble()) * (item.getY() - avgY.getAsDouble()) ).sum();
		
		double bottom = resultList.stream().mapToDouble(item -> Math.pow(item.getX() - avgX.getAsDouble() , 2)).sum();
		

		double result_b0 = 0;
		
		
		

		
		processedCount = resultList.size();
		
		OptionalDouble result_b1 ;
		
		if(bottom!=0) {
			
			 result_b1 =OptionalDouble.of(upper/bottom);
		}else {
			 result_b1 =OptionalDouble.of(0);
		}
		
		
		if(avgY.isPresent() && avgX.isPresent() ) {
			
			result_b0 = (avgY.getAsDouble() - result_b1.getAsDouble()*avgX.getAsDouble());
		}
		
			 
				
				
		double result = result_b0 + (result_b1.getAsDouble() * x.doubleValue());
		
		processedCount = resultList.size();	
		
		return result;
		
		
	
	}*/
	
	public double getSimpleRegressionAnalysis(List<? extends Number> xList1 , List<? extends Number> yList , Number x1 ) throws Exception {
		
		
		
		long OriginalCount = yList.size();
		long deleteLowCount = (long) Math.ceil((OriginalCount*regressionHigh));
		long deleteHighCount = (long) Math.ceil((OriginalCount*regressionLow));
			
			List<RegreesionData> resultList =new ArrayList<>();
			
			if(yList.size() != xList1.size() ) throw new Exception("SIZE Matching Error ");
			
			if(yList.size() < 2) throw new Exception("GET Correlation not enough table size ERROR ");
			if(xList1.stream().mapToDouble(item -> item.doubleValue()).max().getAsDouble() == xList1.stream().mapToDouble(item -> item.doubleValue()).min().getAsDouble() ) throw new Exception("xlist1 elements same all check data");
			
			for (int i = 0; i < yList.size(); i++) {
				
				RegreesionData data = new RegreesionData();
				data.setX(xList1.get(i));
				data.setY(yList.get(i));
				
				resultList.add(data);
				
			}
			
			
			//cut data 
			resultList = resultList.stream()
			
			.sorted(comparing(RegreesionData::getY))
			.limit(OriginalCount-deleteHighCount)
			.skip(deleteLowCount)
			.collect(toList());
			
			//check table size
			if(resultList.size() <3 ) throw new Exception("list table size is too small /n check setRegressionLow or setRegressionHigh ");
			
			double[][] doubleArry = new double[resultList.size()][2];
			
			for(int i=0; i < resultList.size(); i++) {
				
				doubleArry[i][0] = 1;
				doubleArry[i][1] = resultList.get(i).getX(); 
				
				
			}
			
			double[] y = new double[resultList.size()];
			
			for (int i = 0; i < resultList.size(); i++) {
				
				y[i] = resultList.get(i).getY();
				
			}
			
			Optional<Matrix> result = Optional.ofNullable(getMultipleLinearRegression(doubleArry, y));
	    	
			if(result.isPresent()) {
				
				double beta0 = result.get().get(0, 0);
				double beta1 = result.get().get(1, 0);
				
				
				//beta1 = beta1 * x1.doubleValue();
				
				this.processedCount = resultList.size();
				return (beta0 + beta1*x1.doubleValue()) ;
				
			}else {
				
				return 0;
			}

			
			
			
		
		
		}
	
	
public double getThreeRegressionAnalysis(List<? extends Number> xList1 ,List<? extends Number> xList2 , List<? extends Number> yList , Number x1, Number x2) throws Exception {
		
		long OriginalCount = yList.size();
		long deleteLowCount = (long) Math.ceil((OriginalCount*regressionHigh));
		long deleteHighCount = (long) Math.ceil((OriginalCount*regressionLow));
		
		List<RegreesionDataForThree> resultList =new ArrayList<>();
		
		if( (yList.size() != xList1.size()) && (yList.size() != xList2.size()) && (xList1.size() != xList2.size()) ) throw new Exception("SIZE Matching Error ");
		
		if(yList.size() < 2) throw new Exception("GET Correlation not enough table size ERROR ");
		
		if(xList1.stream().mapToDouble(item -> item.doubleValue()).max().getAsDouble() == xList1.stream().mapToDouble(item -> item.doubleValue()).min().getAsDouble() ) throw new Exception("xlist1 elements same all check data");
		if(xList2.stream().mapToDouble(item -> item.doubleValue()).max().getAsDouble() == xList2.stream().mapToDouble(item -> item.doubleValue()).min().getAsDouble() ) throw new Exception("xlist2 elements same all check data");
		
		
		
		for (int i = 0; i < yList.size(); i++) {
			
			RegreesionDataForThree data = new RegreesionDataForThree();
			data.setX1(xList1.get(i));
			data.setY(yList.get(i));
			data.setX2(xList2.get(i));
			
			resultList.add(data);
			
		}
		
		
		
		System.out.println("before size : "+resultList.size());
		//cut data 
		resultList = resultList.stream()
		
		.sorted(comparing(RegreesionDataForThree::getY))
		.limit(OriginalCount-deleteHighCount)
		.skip(deleteLowCount)
		.collect(toList());
		
		System.out.println("after size : "+resultList.size());
		
		if(resultList.size() <3 ) throw new Exception("list table size is too small /n check setRegressionLow or setRegressionHigh ");
		
		
		double[][] doubleArry = new double[resultList.size()][3];
		
		for(int i=0; i < resultList.size(); i++) {
			
			doubleArry[i][0] = 1;
			doubleArry[i][1] = resultList.get(i).getX1(); 
			doubleArry[i][2] = resultList.get(i).getX2(); 
			
		}
		
		double[] y = new double[resultList.size()];
		
		for (int i = 0; i < resultList.size(); i++) {
			
			y[i] = resultList.get(i).getY();
			
		}
		
		Optional<Matrix> result = Optional.ofNullable(getMultipleLinearRegression(doubleArry, y));
    	
		if(result.isPresent()) {
			
			double beta0 = result.get().get(0, 0);
			double beta1 = result.get().get(1, 0);
			double beta2 = result.get().get(2, 0);
			
			
			
			beta1 = beta1 * x1.doubleValue();
			beta2 = beta2 * x2.doubleValue();
			
			
			this.processedCount = resultList.size();
		
			return (beta0 + beta1 + beta2);
			
		}else {
			
			return 0;
		}
		
	
	
	}
	 	

	public Matrix getMultipleLinearRegression(double[][] x, double[] y) {
	        
		if (x.length != y.length) throw new RuntimeException("dimensions don't agree");
	        
			int N = y.length;
	        int p = x[0].length;

	        Matrix X = new Matrix(x);

	        // create matrix from vector
	        Matrix Y = new Matrix(y, N);

	        // find least squares solution
	        QRDecomposition qr = new QRDecomposition(X);
	        
	      
	        
	        Matrix beta = qr.solve(Y);
	       
	 
	        


	        // mean of y[] values
	        double sum = 0.0;
	        for (int i = 0; i < N; i++)
	            sum += y[i];
	        double mean = sum / N;
	        
	        double SST = 0.0;
	        double SSE = 0.0;
	        // total variation to be accounted for
	        for (int i = 0; i < N; i++) {
	            double dev = y[i] - mean;
	            SST += dev*dev;
	        }

	        // variation not accounted for
	        Matrix residuals = X.times(beta).minus(Y);
	        SSE = residuals.norm2() * residuals.norm2();

	        return beta;
	        

	}


	 	

	

	
}
