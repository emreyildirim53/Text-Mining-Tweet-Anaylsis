package tweetMining;

import java.awt.DisplayMode;
import java.awt.List;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Formatter;
import java.util.Hashtable;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.concurrent.TimeUnit;

import org.antlr.v4.runtime.Token;
import org.jfree.data.function.PowerFunction2D;

import jxl.write.WriteException;
import net.sourceforge.jFuzzyLogic.fcl.FclParser.conclusion_return;
import zemberek.morphology.analysis.WordAnalysis;
import zemberek.morphology.analysis.tr.TurkishMorphology;
import zemberek.normalization.TurkishSpellChecker;
import zemberek.tokenization.TurkishTokenizer;
import zemberek.tokenization.antlr.TurkishLexer;

public class tweetMain {
	static ArrayList<String> positive= new ArrayList<String>();
	static ArrayList<String> negative= new ArrayList<String>();
	static ArrayList<String> neutral= new ArrayList<String>();	
	static ArrayList<ArrayList<String>> hashClasses=new ArrayList<ArrayList<String>> ();
	public static void main(String[] args) throws IOException, WriteException, InterruptedException {
		// TODO Auto-generated method stub	
		//hashClasses.add(positive);
		//hashClasses.add(negative);
		//hashClasses.add(neutral);
		/* Dosya Okuma 
        for(int i=0;i<3;i++){
			for(int j=0;j<4000;j++){    
				readFile("files\\raw_texts\\"+(i+1)+"\\"+j+".txt",hashClasses.get(i));
	        }
			//displayList(hashClasses.get(i));   //  ekrana bas 
		}
		*/
		
		// Sýnýflara ayýrdýk
        //Tek seferlik oluþturduk zaten 
		//createFile("files\\raw_classes\\raw_positive.txt",positive);
		//createFile("files\\raw_classes\\raw_negative.txt",negative);
		//createFile("files\\raw_classes\\raw_neutral.txt",neutral);
       
		//* Tokenizer ve StopWords / Clearing */
        //findWords(positive,"positive"); //StopWordsler temizlendi clear_classes
        //findWords(negative,"negative");
        //findWords(neutral,"neutral");
       
		//Tüm kayýtlarý bir araya getirdik.
        //for(int i=0;i<totalUniqTFWords.size();i++)
		//	create_totalUniqTFWords.add(totalUniqTFWords.get(i)+","+totalUniqTFValue.get(i));		
		//	createFile("files\\uniqTFSpellCheckAndStammer\\New_uniqTF_SpellCheckAndStammer.txt",create_totalUniqTFWords);	
        //  System.out.println("New_uniqTF_TotalSpellCheckAndStammer.txt Oluþturuldu.");
        
        //* Morphology Stammer */
        //morphology(positive,"positive");
        //morphology(negative,"negative");
        //morphology(neutral,"neutral");
        
		//Tüm kayýtlarý bir araya getirdik.
        //for(int i=0;i<totalUniqTFWords.size();i++)
		//	create_totalUniqTFWords.add(totalUniqTFWords.get(i)+","+totalUniqTFValue.get(i));		
		//	createFile("files\\uniqTFSpellCheckAndStammer\\NEW_uniqTF_SpellCheckAndStammer.txt",create_totalUniqTFWords);	
        //   System.out.println("NEW_uniqTF_TotalSpellCheckAndStammer.txt Oluþturuldu.");
           
		//TF-IDF deðerlerini hesaplayan fonksiyon
		//TF_IDF();
		
		//Matual Information deðerlerini hesaplayan fonksiyon
		matualInformation();
        
		//Örneklem Oluþturan Fonksiyon
		//createSample();
		
		//Sýnýflandýrma Algoritmasýný çalýþtýran fonksiyon
		kNearestNeighbors();
	}

	static void kNearestNeighbors() throws InterruptedException{		
		
		int sumPozPoz=0,sumPozNeg=0,sumPozNeu=0,sumNegPoz=0,sumNegNeg=0,sumNegNeu=0,sumNeuPoz=0,sumNeuNeg=0,sumNeuNeu=0;
		int sumSum=0,sumTrue=0,sumFalse=0;
		double averagePozRecall=0,averageNegRecall=0,averageNeuRecall=0;
		double averagePozPrecision=0,averageNegPrecision=0,averageNeuPrecision=0;
		double averagePozF1=0,averageNegF1=0,averageNeuF1=0;
		
		
		double averageMacroF1=0;
		double averageMacroRecall=0;
		double averageMacroPrecision=0;
		double averageMicroF1=0;
		double averageMicroRecall=0;
		double averageMicroPrecision=0;
		
		// 10 örneklem ve model olduðu için loopa alýndý.
		for(int t=1;t<=10;t++){
			
			System.out.println("----------------Örneklem "+t+" Ýçin Bulunan Deðerler--------------------");
			
			ArrayList<String> sample= new ArrayList<String>();
			ArrayList<String> model= new ArrayList<String>();
			ArrayList<Double> resultValue= new ArrayList<Double>();
			ArrayList<String> resultClass= new ArrayList<String>();
			ArrayList<String> guessSample= new ArrayList<String>();		
			readFile("files\\12-createSample\\"+t+".sample.txt",sample ); // her örneklem sýrayla getirilecek
			readFile("files\\12-createSample\\"+t+".model.txt",model );// her model sýrayla getirilecek
			double macroAveragePrecision=0;
			double macroAverageRecall=0;
			double macroAverageF1=0;		
			double microAveragePrecision=0;
			double microAverageRecall=0;
			double microAverageF1=0;
			ArrayList<String> sampleClasses= new ArrayList<String>();
			for(int i=0;i<sample.size();i++){
				double sumSquare=0;
				ArrayList<ArrayList<String>> sampleValues=new  ArrayList<ArrayList<String>>();
				ArrayList<String> modelClasses= new ArrayList<String>();
				ArrayList<String> record = new ArrayList<String>(Arrays.asList(sample.get(i).split(",")));
				sampleClasses.add(record.get(0));
				for(int j=1;j<record.size();j++){
					ArrayList<String> parse = new ArrayList<String>(Arrays.asList(record.get(j).split("=")));
				    sampleValues.add(parse);
				    sumSquare+=Math.pow(Double.parseDouble(parse.get(1)),2);
				}		
				double sampleNorm=Math.sqrt(sumSquare);
				   resultValue.clear();
				   resultClass.clear();
				for(int j=0;j<model.size();j++){
					sumSquare=0;
					ArrayList<ArrayList<String>> modelValues=new  ArrayList<ArrayList<String>>();
					record = new ArrayList<String>(Arrays.asList(model.get(j).split(",")));	
					modelClasses.add(record.get(0));
					for(int k=1;k<record.size();k++){
						ArrayList<String> parse = new ArrayList<String>(Arrays.asList(record.get(k).split("=")));
						 modelValues.add(parse);
						 sumSquare+=Math.pow(Double.parseDouble(parse.get(1)),2);
					}
				   double modelNorm=Math.sqrt(sumSquare);
				   
				   double sumProduct=0;
				   for(int k=0;k<sampleValues.size();k++){
					   for(int l=0;l<modelValues.size();l++){
						 //  System.out.println(sampleValues.get(k).get(0)+"   ?=  "+modelValues.get(l).get(0));
						   if(sampleValues.get(k).get(0).equals(modelValues.get(l).get(0))){
							  sumProduct +=Double.parseDouble(sampleValues.get(k).get(1))* Double.parseDouble(modelValues.get(l).get(1));
							  break;
						   }					   
					   }
					   
				   }	   
				   double result=sumProduct/(sampleNorm*modelNorm);
				    if(result!=0){
				        resultValue.add(result);
					    resultClass.add(modelClasses.get(j));				
					 }	
				}
				// model setinde hiç olmayan kayýtlar için deðerler sallandý sayýlarý fazla olmadýðýndan dikkate alýnmadý. //alternatif kod
				/*
				if(resultValue.size()==0){  
					resultValue.add(1.0);
					if(i<=320)resultClass.add("neutral");
					if(i<=200)resultClass.add("negative");
					if(i<=73)resultClass.add("positive");					
				}*/
				ArrayList<String> siniflar= new ArrayList<String>();
				siniflar.add("neutral");
				siniflar.add("negative");
				siniflar.add("positive");
				if(resultValue.size()==0){ 
					Random rand = new Random();
					resultValue.add(1.0);
					resultClass.add(siniflar.get(rand.nextInt(3)));									
				}
				
				
				bubbleSortTwoList(resultClass, resultValue);	
				// OLAYLARI GÖRMEK ÝÇÝN Ý FORUNA SINIR KOY VE BU ALTTAKÝ KODLA /*/ EN ALTTAKÝ TÝMEUNÝT VE ÜSTÜNDEKÝNÝ AÇ
				/*
				for(int j=0;j<resultValue.size();j++){
					System.out.println("result: "+resultValue.get(j)+" class:"+resultClass.get(j));
					//String text=resultValue.get(j)+","+resultClass.get(j);
					//dosyaResults.add(text);
				}
			    System.out.println();
			    */
				guessSample.add(resultClass.get(0));
			}
			int pozPoz=0,pozNeg=0,pozNeu=0,negPoz=0,negNeg=0,negNeu=0,neuPoz=0,neuNeg=0,neuNeu=0;
			for(int i=0;i<guessSample.size();i++){		
				if(guessSample.get(i).equals(sampleClasses.get(i))==true && sampleClasses.get(i).equals("positive")==true)
					pozPoz++;
				
				if(sampleClasses.get(i).equals("positive")==true && guessSample.get(i).equals("negative")==true)
					pozNeg++;
				
				if(sampleClasses.get(i).equals("positive")==true && guessSample.get(i).equals("neutral")==true)
					pozNeu++;
				
				if(guessSample.get(i).equals(sampleClasses.get(i))==true && sampleClasses.get(i).equals("negative")==true)
					negNeg++;
	
				if(sampleClasses.get(i).equals("negative")==true && guessSample.get(i).equals("positive")==true)
					negPoz++;
				
				if(sampleClasses.get(i).equals("negative")==true && guessSample.get(i).equals("neutral")==true)
					negNeu++;
				
				if(guessSample.get(i).equals(sampleClasses.get(i))==true && sampleClasses.get(i).equals("neutral")==true)
					neuNeu++;
				
				if(sampleClasses.get(i).equals("neutral")==true && guessSample.get(i).equals("positive")==true)
					neuPoz++;
				
				if(sampleClasses.get(i).equals("neutral")==true && guessSample.get(i).equals("negative")==true)
					neuNeg++;			
			}
			
			
			System.out.println();
			System.out.println("pozPoz: "+pozPoz);
			System.out.println("pozNeg: "+pozNeg);
			System.out.println("pozNeu: "+pozNeu);
			System.out.println("negPoz: "+negPoz);
			System.out.println("negNeg: "+negNeg);
			System.out.println("negNeu: "+negNeu);
			System.out.println("neuPoz: "+neuPoz);
			System.out.println("neuNeg: "+neuNeg);
			System.out.println("neuNeu: "+neuNeu);
			System.out.println("Toplam Kayýt: "+(pozPoz+pozNeg+pozNeu+negPoz+negNeg+negNeu+neuPoz+neuNeg+neuNeu));
			System.out.println("Doðru Bildiklerinin Sayýsý: "+(pozPoz+negNeg+neuNeu));
			System.out.println("Yanlýþ Bildiklerinin Sayýsý: "+(pozNeg+pozNeu+negPoz+negNeu+neuNeg+neuPoz));
			
			double pozRecall=0,negRecall=0,neuRecall=0;
			double pozPrecision=0,negPrecision=0,neuPrecision=0;
			double pozF1=0,negF1=0,neuF1=0;
			
			pozRecall=(double)pozPoz/(double)(pozPoz+pozNeg+pozNeu);
			negRecall=(double)negNeg/(double)(negNeg+negPoz+negNeu);
			neuRecall=(double)neuNeu/(double)(neuNeu+neuNeg+neuPoz);
			
			System.out.println();
			
			System.out.println("pozRecall: "+pozRecall);
			System.out.println("negRecall: "+negRecall);
			System.out.println("neuRecall: "+neuRecall);
			
			pozPrecision=(double)pozPoz/(double)(pozPoz+negPoz+neuPoz);
			negPrecision=(double)negPoz/(double)(pozNeg+negNeg+neuNeg);
			neuPrecision=(double)neuPoz/(double)(pozNeu+neuNeu+negNeu);
			
			System.out.println("pozPrecision: "+pozPrecision);
			System.out.println("negPrecision: "+negPrecision);
			System.out.println("neuPrecision: "+neuPrecision);
			
			pozF1=(double)(2*pozRecall*pozRecall)/(double)(pozRecall+pozPrecision);
			negF1=(double)(2*negRecall*negRecall)/(double)(negRecall+negPrecision);
			neuF1=(double)(2*neuRecall*neuRecall)/(double)(neuRecall+neuPrecision);
			
			System.out.println("pozF1: "+pozF1);
			System.out.println("negF1: "+negF1);
			System.out.println("neuF1: "+neuF1);
			
			
			macroAveragePrecision=(pozPrecision+negPrecision+neuPrecision)/3;
			macroAverageRecall=(pozRecall+negRecall+neuRecall)/3;
			macroAverageF1=(pozF1+negF1+neuF1)/3;
				
			microAverageRecall=(735*pozRecall+1266*negRecall+897*neuRecall)/2898;
			microAveragePrecision=(735*pozPrecision+1266*negPrecision+897*neuPrecision)/2898;
			microAverageF1=(735*pozF1+1266*negF1+897*neuF1)/2898;
			
			
			System.out.println("macroAverage-Precision: "+macroAveragePrecision);
			System.out.println("macroAverage-Recall: "+macroAverageRecall);
			System.out.println("macroAverage-F1: "+macroAverageF1);
			
			
			System.out.println("microAverage-Precision: "+microAveragePrecision);
			System.out.println("microAverage-Recall: "+microAverageRecall);
			System.out.println("microAverage-F1: "+microAverageF1);
			
			
			System.out.println();
			
			// --------------------Son deðerleri bulma----------------------
			
			//--------- Görselin üst kýsmý---------------- 
		    sumPozPoz+=pozPoz;
		    sumPozNeg+=pozNeg;
		    sumPozNeu+=pozNeu;
		    
		    sumNegPoz+=negPoz;
		    sumNegNeg+=negNeg;
		    sumNegNeu+=negNeu;
		    
		    sumNeuPoz+=neuPoz;
		    sumNeuNeg+=neuNeg;
		    sumNeuNeu+=neuNeu;
		    
		    sumSum+=(pozPoz+pozNeg+pozNeu+negPoz+negNeg+negNeu+neuPoz+neuNeg+neuNeu);
		    sumTrue+=(pozPoz+negNeg+neuNeu);
		    sumFalse+=(pozNeg+pozNeu+negPoz+negNeu+neuNeg+neuPoz);
		    //---------------------------------------------------
		    
		    
		    //Recalllar burada toplanýyor
		    averagePozRecall+=pozRecall;
		    averageNegRecall+=negRecall;
		    averageNeuRecall+=neuRecall;
		    
		    //Precisionlar burada toplanýyor
		    averagePozPrecision+=pozPrecision;
		    averageNegPrecision+=negPrecision;
		    averageNeuPrecision+=neuPrecision;
		    
		    // F1 ler burada toplanýyor
		    averagePozF1+=pozF1;
		    averageNegF1+=negF1;
		    averageNeuF1+=neuF1;
		    
		    averageMacroPrecision=macroAveragePrecision;
			averageMacroRecall=macroAverageRecall;
		    averageMacroF1+=macroAverageF1;
			
		    averageMicroPrecision+=microAveragePrecision;
		    averageMicroRecall+=microAverageRecall;
			averageMicroF1+=microAverageF1;
			
		}
		System.out.println();
		System.out.println();
		
		System.out.println("---------------- Örneklemlerin Sonuç Deðerleri --------------------");
		System.out.println();
		System.out.println("pozPoz: "+sumPozPoz);
		System.out.println("pozNeg: "+sumPozNeg);
		System.out.println("pozNeu: "+sumPozNeu);
		System.out.println("negPoz: "+sumNegPoz);
		System.out.println("negNeg: "+sumNegNeg);
		System.out.println("negNeu: "+sumNegNeu);
		System.out.println("neuPoz: "+sumNeuPoz);
		System.out.println("neuNeg: "+sumNeuNeg);
		System.out.println("neuNeu: "+sumNeuNeu);
		System.out.println("Toplam Kayýt: "+sumSum);
		System.out.println("Doðru Bildiklerinin Sayýsý: "+sumTrue);
		System.out.println("Yanlýþ Bildiklerinin Sayýsý: "+sumFalse);
		System.out.println();
		
		System.out.println("averagePozRecall: "+(averagePozRecall/10));
		System.out.println("averageNegRecall: "+(averageNegRecall/10));
		System.out.println("averageNeuRecall: "+(averageNeuRecall/10));
		
		System.out.println("averagePozPrecision: "+(averagePozPrecision/10));
		System.out.println("averageNegPrecision: "+(averageNegPrecision/10));
		System.out.println("averageNeuPrecision: "+(averageNeuPrecision/10));
		
		System.out.println("averagePozF1: "+averagePozF1/10);
		System.out.println("averageNegF1: "+averageNegF1/10);
		System.out.println("averageNeuF1: "+averageNeuF1/10);
		
		
		System.out.println("Ortalama Precision Macro Deðeri: "+(averageMacroPrecision));	
		System.out.println("Ortalama Recall Macro Deðeri: "+(averageMacroRecall));	
		System.out.println("Ortalama F1-Score Macro Deðeri: "+(averageMacroF1/10));
		
		System.out.println("Ortalama Precision Micro Deðeri: "+(averageMicroPrecision/10));	
		System.out.println("Ortalama Recall Micro Deðeri: "+(averageMicroRecall/10));	
		System.out.println("Ortalama F1-Score Micro Deðeri: "+(averageMicroF1/10));
		

		
	}
	
	static void TF_IDF() throws InterruptedException, IOException{
		ArrayList<String> attFreq= new ArrayList<String>();
		ArrayList<String> attributes= new ArrayList<String>();
		readFile("files\\11-TF_IDF_afterMatual_Information\\new_attributes.csv", attFreq);
		ArrayList<ArrayList<String>> lines= new ArrayList<ArrayList<String>>(); // dosyaya yazýlacak olan satýrlar
		ArrayList<String> header= new ArrayList<String>();
		for(int i=0;i<attFreq.size();i++){
			ArrayList<String> record = new ArrayList<String>(Arrays.asList(attFreq.get(i).split(",")));
			attributes.add(record.get(0));
			header.add(record.get(0));
		}	
		header.add("class");//Baþlýðýn sonuna class ekledik
		ArrayList<String> sentences= new ArrayList<String>();
		ArrayList<String> docClass= new ArrayList<String>();
		ArrayList<ArrayList<String>> allDoc= new ArrayList<ArrayList<String>>();
		readFile("files\\12-createSample\\negative-sentence.txt", sentences);// ÖRNEKLEM ÝÇÝN TF IDF Ý OLAN POZÝTÝF/NEGATÝF/NÖTR DOSYALAR OLUÞTURULDU.TF IDF TABLOSU ÝÇÝN BURASI ALL-SENTENCES.TXT OLMALI
		for(int i=0;i<sentences.size();i++){
			ArrayList<String> record = new ArrayList<String>(Arrays.asList(sentences.get(i).split(",")));
			ArrayList<String> docWords= new ArrayList<String>();
			docClass.add(record.get(0));
			for(int j=0;j<record.size();j++){
				if(j>0)docWords.add(record.get(j)); // bir cümledeki kelimeler ayrýþtýrýlýp listede tutuldu. bu listeyi tutan bir liste olmalý yani tüüm cümleleri tutacak
			}
			//System.out.println(docClass.get(i)+" -> "+docWords);
			//TimeUnit.SECONDS.sleep(1); 
			allDoc.add(docWords); // Cümleleri tutan liste		
		}
		// Cümleler allDoc içindeydi þimdi sýrasýyla cümleleri çekelim ve o cümleye deneme amaçlý attributeleri yollayalým
		for(int i=0;i<allDoc.size();i++){
			 //System.out.println(allDoc.get(i)); // get(i) Ýçinde cümle listesi var o zaman listeye atalým
			 ArrayList<String> docWords= allDoc.get(i); // cümlenin kelimeleri bu listede olacak	
			 ArrayList<String> line= new ArrayList<String>();
			 line.add(docClass.get(i));
			 for(int j=0;j<docWords.size();j++){
				 //System.out.println(docWords+"-> "+attributes.get(j));
				 if(attributes.contains(docWords.get(j))){
				 double tfResult=tf(docWords, docWords.get(j));
				 //System.out.println(tfResult);
				 double idfResult=idf(allDoc, docWords.get(j));
				 //System.out.println(idfResult);
				 double tf_idf=tfResult*idfResult;
				 //System.out.println("docWords: "+docWords.get(j)+"tf: "+tfResult+" idf: "+idfResult+" tf-idf: "+tf_idf);
				 //System.out.println("docWords: "+docWords.get(j)+"  ,  tf-idf: "+tf_idf);
				 line.add(docWords.get(j)+"="+tf_idf);
				 }
			 } 
			 if(line.size()>1)
			 lines.add(line);
		}	
		//for(int i=0;i<lines.size();i++)
		//	System.out.println(lines.get(i));
		
		// yukardaki satýra attributes contains eklendi eger attributelerde o kelime yoksa bakma ona dedik
		// bakmadýðý zaman içinde sadece sýnýf deðeri olduðu için line boyutu 1 se onuda ekleme dedik
		
		/*alttaki dosya okuma iþlemi açýksa buranýn kapatýlmasý tavsiye edilir
		 * problem oluþmaz fakat ayný isimde deðiþkenler var onlarýn deðiþtirilmesi lazým
		 * alttaki yazdýrma iþlemi TF-IDF matrisini yazdýrýr 
		 * Burdaki ise tf-idf deðerlerini beraberinde içeren positive-negative-neutral cümleleri yazdýrýr
		 */
		 //positive/negative/nötür sýnýflarý için tf-idf deðerli þekilde yazýldý.
		File fr = new File("files\\12-createSample\\tf-negative-sentence.txt"); // TF-IDF HESABINDA BURADA ALL-SENTENCES.TXT OKUMAK GEREKÝYOR. ÞUAN SAMPLE OLUÞTURUYOR.
	 	FileWriter fw=new FileWriter(fr);    
		for(int i=0;i<lines.size();i++){	  		    	
	    		    	ArrayList<String> line=lines.get(i);	    	
	    		    	for(int k=0;k<line.size();k++){
	    		    		ArrayList<String> record = new ArrayList<String>(Arrays.asList(line.get(k).split(",")));
	    		    		//System.out.println(record);
	    		    		//TimeUnit.SECONDS.sleep(1);
	    		    		for(int l=0;l<record.size();l++){
	    		    			fw.append(record.get(l)+",");
	    		    		}
	    		    		
	    		    	} 
	    		   	if(i!=lines.size()-1)//son satýra boþluk atmasýn diye
	    		    		fw.append(System.getProperty("line.separator"));
		}
	    fw.close(); 
	   	
		 /*
		ArrayList<ArrayList<String>> table= new ArrayList<ArrayList<String>>(); // dosyaya yazýlacak olan satýrlar	
		table.add(header);
		for(int i=0;i<lines.size();i++){
			ArrayList<String> row= new ArrayList<String>();//Tablonun bir satýrý.
			ArrayList<String> doc=allDoc.get(i);
			String text="";
			for(int j=0;j<doc.size();j++){
				text+=doc.get(j)+" ";
			}
			row.add(text);	 
			ArrayList<String> line=lines.get(i);//her bir satýrýn içindeki kelime dizisi.
			for(int j=0;j<line.size();j++){
				ArrayList<String> record = new ArrayList<String>(Arrays.asList(line.get(j).split(",")));// dizi içindeki karakterin attribute deðeri
				for(int k=0;k<attributes.size();k++){									
						 if(attributes.get(k).equalsIgnoreCase(record.get(0)))
							row.add(String.format("%.4s",record.get(1)));
						 else
						    row.add("0.0000");
				}
			}
			row.add(docClass.get(i));
			table.add(row);
		}
		
		//Dosyaya tablo yazdýrýldý.
		
		 File f = new File("files\\11-TF_IDF_afterMatual_Information\\matrixTF_IDF.csv");    
	     try {      
	    	FileWriter fw=new FileWriter(f);  
	    	for(int i=0;i<table.size();i++){
	    		ArrayList<String> row=table.get(i);
	    		for(int j=0;j<row.size();j++){
	    			fw.append(row.get(j)+",");
	    		}
	    		fw.append(System.getProperty("line.separator"));
	    	}    	
	    	fw.close();       
	     } catch (IOException e) { 
	        // TODO: handle exception
	        e.printStackTrace();
	     }
		 */
	}	
	
	static void createSample() throws InterruptedException, IOException{		
		readFile("files\\12-createSample\\tf-positive-sentence.txt", positive);
		readFile("files\\12-createSample\\tf-negative-sentence.txt", negative);
		readFile("files\\12-createSample\\tf-neutral-sentence.txt", neutral);
		ArrayList<ArrayList<String> > samples= new ArrayList<ArrayList<String> >();
		int sayPositive=0,sayNegative=0,sayNeutral=0;
		for(int i=0;i<10;i++){	
			ArrayList<String> sample= new ArrayList<String>();
			for(int j=((int) (Math.floor(positive.size()*0.1)*i));j<Math.floor(positive.size()*0.1)*(i+1);j++){
				sample.add(positive.get(j));
				sayPositive++;
			}	
			if(i==9)
				for(int j=sayPositive;j<positive.size();j++)
					sample.add(positive.get(j));
			for(int j=((int) (Math.floor(negative.size()*0.1)*i));j<Math.floor(negative.size()*0.1)*(i+1);j++){
				sample.add(negative.get(j));
				sayNegative++;
			}
			if(i==9)
				for(int j=sayNegative;j<negative.size();j++)
					sample.add(negative.get(j));

			for(int j=((int) (Math.floor(neutral.size()*0.1)*i));j<Math.floor(neutral.size()*0.1)*(i+1);j++){
				sample.add(neutral.get(j));
				sayNeutral++;
			}	
			if(i==9)
				for(int j=sayNeutral;j<neutral.size();j++)
					sample.add(neutral.get(j));
			
		    samples.add(sample);	 
		}
      
		for(int i=0;i<samples.size();i++){
			    ArrayList<ArrayList<String> > model= new ArrayList<ArrayList<String> >();
				//Örnekleme Oluþturuldu
				File f = new File("files\\12-createSample\\"+(i+1)+".sample.txt");    
    		     try {      
    		    	FileWriter fw=new FileWriter(f);    	
    		    	ArrayList<String> ornek=samples.get(i);	
    		    	for(int k=0;k<ornek.size();k++){
    		    		ArrayList<String> record = new ArrayList<String>(Arrays.asList(ornek.get(k).split(",")));
    		    		for(int j=0;j<record.size();j++){
    		    			fw.append(record.get(j)+",");
    		    		}
    		    		if(k!=ornek.size()-1)
    		    		fw.append(System.getProperty("line.separator"));
    		    	}    	
    		    	fw.close();       
    		     } catch (IOException e) { 
    		        // TODO: handle exception
    		        e.printStackTrace();
    		     }	
    		     
    		     // Örneklemenin geçmediði Model seti oluþturulcak.
    		    
    		     for(int j=0;j<samples.size();j++)
    		    	 if(i!=j)
    		    		 model.add(samples.get(j)); 		    		 
    		     
    		    File fr = new File("files\\12-createSample\\"+(i+1)+".model.txt"); 
    		 	FileWriter fw=new FileWriter(fr);    
    		    for(int j=0;j<model.size();j++){		  		    	
    	    		    	ArrayList<String> ornek=model.get(j);	    	
    	    		    	for(int k=0;k<ornek.size();k++){
    	    		    		ArrayList<String> record = new ArrayList<String>(Arrays.asList(ornek.get(k).split(",")));
    	    		    		for(int l=0;l<record.size();l++){
    	    		    			fw.append(record.get(l)+",");
    	    		    		}
    	    		    		//if(k!=ornek.size()-1)//son satýra boþluk atmasýn diye
    	    		    		fw.append(System.getProperty("line.separator"));
    	    		    	} 
    	    		    	
    		    	 }
    		    fw.close();
    	}  	
	}
	static void matualInformation(){
		ArrayList<String> attFreq= new ArrayList<String>();
		ArrayList<String> attributes= new ArrayList<String>();
		ArrayList<String> fileWrite= new ArrayList<String>();
		readFile("files\\9-TF_IDF\\attributes.txt", attFreq);
		// Attributeler Okundu. ->attribute
		for(int i=0;i<attFreq.size();i++){
			ArrayList<String> record = new ArrayList<String>(Arrays.asList(attFreq.get(i).split(",")));
			attributes.add(record.get(0));
		}	
		//Sýnýf içeren cümleler okundu ->sentences
		ArrayList<String> sentences= new ArrayList<String>();
		readFile("files\\9-TF_IDF\\all-sentence.txt", sentences);
		
		//Sýnýf deðerleri tutulacak ->docClass
		ArrayList<String> docClass= new ArrayList<String>();
		int posWordNumber=0;
		int negWordNumber=0;
		int neuWordNumber=0;
		int notPosWordNumber=0;
		int notNegWordNumber=0;
		int notNeuWordNumber=0;
		for(int i=0;i<attributes.size();i++){
			posWordNumber=0;
		    negWordNumber=0;
			neuWordNumber=0;
			notPosWordNumber=0;
			notNegWordNumber=0;
			notNeuWordNumber=0;
			for(int j=0;j<sentences.size();j++){
				ArrayList<String> words = new ArrayList<String>(Arrays.asList(sentences.get(j).split(",")));
				docClass.add(words.get(0));
				boolean kntrl=false;
				for(int k=1;k<words.size();k++){ // 0'da sýnýf olduðundan 1'den baþladý.
					if(words.get(k).equalsIgnoreCase(attributes.get(i))){					
						if(docClass.get(j).equals("positive")) posWordNumber++;
						if(docClass.get(j).equals("negative")) negWordNumber++;
						if(docClass.get(j).equals("neutral"))  neuWordNumber++;
						kntrl=true;
					}
				}	
				if(kntrl!=true){
					if(docClass.get(j).equals("positive")) notPosWordNumber++;
					if(docClass.get(j).equals("negative")) notNegWordNumber++;
					if(docClass.get(j).equals("neutral"))  notNeuWordNumber++;	
					kntrl=false;
				}
			}
			String textWrite=attributes.get(i)+","+new BigDecimal( calcMatualValue(posWordNumber,negWordNumber,neuWordNumber,notPosWordNumber,notNegWordNumber,notNeuWordNumber)).setScale(8, RoundingMode.HALF_UP);
			fileWrite.add(textWrite);
			//System.out.println(attributes.get(i)+"-->  Geçtikleri(Positive:"+posWordNumber+", Negative:"+negWordNumber+", Neutral:"+neuWordNumber+")"
				//	+ "  Geçmedikleri(Positive:"+notPosWordNumber+", Negative:"+notNegWordNumber+", Neutral:"+notNeuWordNumber+" CalcMatualValue:"+ new BigDecimal( calcMatualValue(posWordNumber,negWordNumber,neuWordNumber,notPosWordNumber,notNegWordNumber,notNeuWordNumber)).setScale(8, RoundingMode.HALF_UP));		
		}	
		createFile("files\\10-Matual_Information\\attributesInformation.txt",fileWrite);
		
	}
	static double calcMatualValue(int posWordNumber,int negWordNumber,int neuWordNumber,int notPosWordNumber,int notNegWordNumber,int notNeuWordNumber){
		int totalValue=posWordNumber+negWordNumber+neuWordNumber+notPosWordNumber+notNegWordNumber+notNeuWordNumber;
		double posLog=0,negLog=0,neuLog=0,notPosLog=0,notNegLog=0,notNeulog=0;
		
		if((double)(posWordNumber*totalValue)/(double)((posWordNumber+notPosWordNumber)*(posWordNumber+negWordNumber+neuWordNumber))!=0)
		     posLog=Math.log((double)(posWordNumber*totalValue)/(double)((posWordNumber+notPosWordNumber)*(posWordNumber+negWordNumber+neuWordNumber)))/Math.log(2);
		
		if((double)(negWordNumber*totalValue)/(double)((negWordNumber+notNegWordNumber)*(posWordNumber+negWordNumber+neuWordNumber))!=0)
		     negLog=Math.log((double)(negWordNumber*totalValue)/(double)((negWordNumber+notNegWordNumber)*(posWordNumber+negWordNumber+neuWordNumber)))/Math.log(2);	
		
		if((double)((neuWordNumber*totalValue)/(double)((neuWordNumber+notNeuWordNumber)*(posWordNumber+negWordNumber+neuWordNumber)))!=0)
		     neuLog=Math.log((double)(neuWordNumber*totalValue)/(double)((neuWordNumber+notNeuWordNumber)*(posWordNumber+negWordNumber+neuWordNumber)))/Math.log(2);
		
		if((double)(notPosWordNumber*totalValue)/(double)((posWordNumber+notPosWordNumber)*(notPosWordNumber+notNegWordNumber+notNeuWordNumber))!=0)
			 notPosLog=Math.log((double)(notPosWordNumber*totalValue)/(double)((posWordNumber+notPosWordNumber)*(notPosWordNumber+notNegWordNumber+notNeuWordNumber)))/Math.log(2);
			
		if((double)(notNegWordNumber*totalValue)/(double)((negWordNumber+notNegWordNumber)*(notPosWordNumber+notNegWordNumber+notNeuWordNumber))!=0)
			 notNegLog=Math.log((double)(notNegWordNumber*totalValue)/(double)((negWordNumber+notNegWordNumber)*(notPosWordNumber+notNegWordNumber+notNeuWordNumber)))/Math.log(2);		 
			   
		if((double)(notNeuWordNumber*totalValue)/(double)((neuWordNumber+notNeuWordNumber)*(notPosWordNumber+notNegWordNumber+notNeuWordNumber))!=0)
			 notNeulog=Math.log((double)(notNeuWordNumber*totalValue)/(double)((neuWordNumber+notNeuWordNumber)*(notPosWordNumber+notNegWordNumber+notNeuWordNumber)))/Math.log(2);
		
		double result=
				((double)posWordNumber/(double)totalValue)*posLog
		        +((double)negWordNumber/(double)totalValue)*negLog
		        +((double)neuWordNumber/(double)totalValue)*neuLog
		        +((double)notPosWordNumber/(double)totalValue)*notPosLog
				+((double)notNegWordNumber/(double)totalValue)*notNegLog
				+((double)notNeuWordNumber/(double)totalValue)*notNeulog;
		return result;	
	}
	

	public static double tf(ArrayList<String> docWords, String attribute) { // 1 cümle ve bir attributes yollamalýyým aslýnda 1 cümle için tüm attributesler
        double result = 0;
        for (String word : docWords)
            if (attribute.equalsIgnoreCase(word))
                result++;
        return result / docWords.size();
    }
	public static double idf(ArrayList<ArrayList<String>> allDoc, String attribute) {
        double n = 0;
        for (ArrayList<String> docWords : allDoc) 
            for (String word : docWords) 
                if (attribute.equalsIgnoreCase(word)) {
                    n++;
                    break;
                }
        if(n==0)return 0;
        else
        return Math.log(allDoc.size() / n);
    }
	public static void readFile(String URL,ArrayList<String> sinif){
	    File file = new File(URL);	
	    try {
	        BufferedReader fis = new BufferedReader(new InputStreamReader(new FileInputStream(file),"ISO-8859-9"));
	        String text;
	        while ((text = fis.readLine()) != null) {
	            sinif.add(text.toLowerCase());
	        }          
	        fis.close(); // dosya okuma iþlemimiz bittikten sonra kapatÄ±yoruz.
	    } catch (FileNotFoundException e) {
	        // TODO: handle exception
	       // System.out.println("Dosya BulunamadÄ±..");
	    } catch (IOException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    }
	}
	public static void displayList(ArrayList<String> displayClass){		
		for(int i=0;i<displayClass.size();i++){
			System.out.println(displayClass.get(i));		
		}		
	}
	public static void createFile(String URL,ArrayList<String> writeClass){
	    File f = new File(URL);    
	    try {      
	    	FileWriter fw=new FileWriter(f);       	 
	    	for(int i=0;i<writeClass.size();i++){
	    		fw.append(writeClass.get(i)+System.getProperty("line.separator")); //<br attýk sonunda
	    	}
	    	fw.close();       
	    } catch (IOException e) { 
	        // TODO: handle exception
	        e.printStackTrace();
	    }
	}
	public static void findWords(ArrayList<String> findWord,String label){	
		ArrayList<String> words= new ArrayList<String>();
		for(int i=0;i<findWord.size();i++){
			String text = new String();
			StringTokenizer tokenizer = new StringTokenizer(findWord.get(i), " .,??!`'^_->£½|&///%:;#=1234567890'\"({[]})@^(*-+/"); 
			while(tokenizer.hasMoreTokens()){
				//System.out.println(tokenizer.nextToken());
				text+=tokenizer.nextToken().trim()+",";
			}
			words.add(text); 
		}
		clearDataFromStopWord(words, label);
		// bir kez oluþturuldu
		//createFile("files\\splitword_classes\\splitword_"+label+".txt",words);
		//System.out.println("splitword_"+label+".txt Oluþturuldu.");
	}
	//Kullanýlmadý. belki kullanýlýr.
	public static ArrayList<String> tokenizer(String input){
		TurkishTokenizer tokenizer = TurkishTokenizer.builder().ignoreTypes(TurkishLexer.Punctuation, TurkishLexer.NewLine, TurkishLexer.SpaceTab).build();
		java.util.List<Token> tokens = tokenizer.tokenize(input);
		ArrayList<String> tokenWords= new ArrayList<String>();
		for (Token token : tokens) {
			tokenWords.add(token.getText());
		} 
		return tokenWords;	
	}
	
	public static void clearDataFromStopWord(ArrayList<String> clearClass,String label){
		ArrayList<String> stopWords= new ArrayList<String>();
		readFile("files\\stop-words\\stop-words-turkish-github.txt",stopWords);
		for(int i=0;i<clearClass.size();i++){
			ArrayList<String> record = new ArrayList<String>(Arrays.asList(clearClass.get(i).split(",")));
			for(int j=0;j<record.size();j++){
				for(int k=0;k<stopWords.size();k++){
					if(stopWords.contains(record.get(j))){					
						record.remove(j);
						j--;
						break;
					}
				}		
			}			
			String text = "";
			for(int j=0;j<record.size();j++){
				text+=record.get(j)+",";				
			}
			//System.out.println(text);
			clearClass.set(i, text);		
		}
		//Oluþturuldu...
		createFile("files\\clear_classes\\New_clear_"+label+".txt",clearClass);
		System.out.println("New_clear_"+label+".txt Oluþturuldu.");
		
		//stammer atýldýktan sonra
		createFile("files\\stammer-and-spellcheck\\NEW_clear_stammer-and-spellcheck_"+label+".txt",clearClass);
		System.out.println("NEW_clear_stammer-and-spellcheck_"+label+".txt Oluþturuldu.");
		uniqTF(clearClass,label); 	
	}
	
	static ArrayList<String> totalUniqTFWords = new ArrayList<String>();
	static ArrayList<Integer> totalUniqTFValue = new ArrayList<Integer>();
	static ArrayList<String> create_totalUniqTFWords = new ArrayList<String>();
	
	public static void uniqTF(ArrayList<String> uniqClass,String label){		
		ArrayList<String> uniqTFWords = new ArrayList<String>();
		ArrayList<Integer> uniqTFValue = new ArrayList<Integer>();		
		for(int i=0;i<uniqClass.size();i++){
			ArrayList<String> record = new ArrayList<String>(Arrays.asList(uniqClass.get(i).split(",")));
			for(int j=0;j<record.size();j++){
				if(uniqTFWords.contains(record.get(j))){
					uniqTFValue.set(uniqTFWords.indexOf(record.get(j)), uniqTFValue.get(uniqTFWords.indexOf(record.get(j)))+1);
				}else{	
					uniqTFWords.add(record.get(j));
					uniqTFValue.add(1);
				}
			}
			//System.out.println(uniqClass.get(i));			
		}	
		//////////////////////// Sýnýf sýnýf kelimeler ve adetleri \\\\\\\\\\\\\\\\\\\\\\\\\\\
		//bubbleSortTwoList(uniqTFWords,uniqTFValue);
		ArrayList<String> create_uniqTFWords = new ArrayList<String>();
		for(int i=0;i<uniqTFWords.size();i++){
			create_uniqTFWords.add(uniqTFValue.get(i)+","+uniqTFWords.get(i));	
		}
		
		//Oluþturuldu.
		createFile("files\\uniqTF\\NEW_uniqTF_"+label+".txt",create_uniqTFWords);
		System.out.println("NEW_uniqTF_"+label+".txt Oluþturuldu.");
		
		///////////////////////// Total Kýsmý \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
		for(int i=0;i<uniqTFWords.size();i++){
			if(totalUniqTFWords.contains(uniqTFWords.get(i))){
				totalUniqTFValue.set(totalUniqTFWords.indexOf(uniqTFWords.get(i)),uniqTFValue.get(i)+totalUniqTFValue.get(totalUniqTFWords.indexOf(uniqTFWords.get(i))));
			}else{
				totalUniqTFWords.add(uniqTFWords.get(i));
				totalUniqTFValue.add(uniqTFValue.get(i));
			}			
		}
		//bubbleSortTwoList(totalUniqTFWords, totalUniqTFValue);	
		//totaluniq mainde yazýldý orada
	}
	static void morphology(ArrayList<String> morpClasses,String label) throws IOException{
		ArrayList<String> clearData= new ArrayList<String>();
		readFile("files\\clear_classes\\NEW_clear_"+label+".txt",clearData);
		TurkishMorphology morphology = TurkishMorphology.createWithDefaults();
		TurkishSpellChecker spellChecker = new TurkishSpellChecker(morphology);
		TurkishMorphology parser = TurkishMorphology.createWithDefaults();	
		//displayList(clearData);
		for(int i=0;i<clearData.size();i++){
			ArrayList<String> records = new ArrayList<String>(Arrays.asList(clearData.get(i).split(",")));
			for (String word : records) 		
			   spellChecker.suggestForWord(word);
			
			String input = null;
			String text = "";
			for (String word : records) {							 
				if(spellChecker.suggestForWord(word).size()>0)
					input= spellChecker.suggestForWord(word).get(0);
				else continue;
				java.util.List<WordAnalysis> result = parser.analyze(input);
				WordAnalysis analysis = result.get(0);
				text+=analysis.getRoot()+",";
			}
			clearData.set(i, text);	
		}
		createFile("files\\stammer-and-spellcheck\\NEW_stammer-and-spellcheck_"+label+".txt",clearData);
		System.out.println("NEW_stammer-and-spellcheck__"+label+".txt Oluþturuldu.");
		
		uniqTF(clearData, "SpellCheckAndStammer_"+label);
		}
	
	static void bubbleSortTwoList(ArrayList<String> uniqTFWords,ArrayList<Double> uniqTFValue ) {  
        int n = uniqTFValue.size();  
        double temp = 0;
        String temp2="";
         for(int i=0; i < n; i++){  
             for(int j=1; j < (n-i); j++){  
                  if(uniqTFValue.get(j-1) < uniqTFValue.get(j)){                                
                         temp = uniqTFValue.get(j-1);
                         temp2=uniqTFWords.get(j-1);
                         uniqTFValue.set(j-1,uniqTFValue.get(j)); 
                         uniqTFWords.set(j-1,uniqTFWords.get(j));  
                         uniqTFValue.set(j,temp); 
                         uniqTFWords.set(j,temp2); 
                 }                 
             }  
         }  
	 }  
}
